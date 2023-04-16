package com.wyu.snorlax.api.v1;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessController;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.enums.SendIDType;
import com.wyu.snorlax.model.MessageTemplate;
import com.wyu.snorlax.model.dto.CustomMessage;
import com.wyu.snorlax.model.vo.Resp;
import com.wyu.snorlax.mq.MQProducer;
import com.wyu.snorlax.repository.MessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-12
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private MessageTemplateRepository repository;

    @Autowired
    private ProcessController processController;

    @Autowired
    private MQProducer mqProducer;

    @GetMapping("/test")
    public Resp test() {
//        List<MessageTemplate> all = this.repository.findAll();
//        System.out.println(all);

        Map<String, String> map = new HashMap<>();
        map.put("name", "novo");
        // 1. 构建责任链上下文所需参数
        SendTaskModel taskModel = SendTaskModel.builder()
                .templateId(1L)
                .messageParamList(Collections.singletonList(MessageParam.builder().receiver("13211291857").variables(map).build()))
                .build();

        // 2. 构建责任链上下文
        ProcessContext context = ProcessContext.builder()
                .chainType(ChainType.SEND)
                .model(taskModel)
                .needBreak(false)
                .response(Resp.success())
                .build();
        processController.process(context);
        return context.getResponse();
    }


    @GetMapping("/add")
    public void add() {
        MessageTemplate template = MessageTemplate.builder()
                .title("短信测试模板")
                .auditor("novo")
                .channelAccountId(1L)
                .creator("novo")
                .modifier("novo")
                .status(1)
                .idType(SendIDType.PHONE.name())
                .sendChannel(ChannelType.SMS.name())
                .msgStatus(10)
                .msgType("通知")
                .templateType("技术方调用")
                .shieldType("不屏蔽")
                .templateContent("{\"url\":\"\",\"content\":\"欢迎你，${name}\"}")
                .build();
        this.repository.save(template);
    }


    @GetMapping("/mq")
    public void mq() {
        CustomMessage message = CustomMessage.builder()
                .accountNo(1111L)
                .bizId("asgasfas-sdfsdf-sdfs")
                .content("hello-world")
                .messageId("ttatst")
                .remark("remark")
                .build();
        this.mqProducer.send("helloworld", message);
    }

}
