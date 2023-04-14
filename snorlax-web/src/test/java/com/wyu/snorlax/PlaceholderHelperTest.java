package com.wyu.snorlax;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessController;
import com.wyu.snorlax.chain.SendTaskModel;
import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.model.vo.Resp;
import com.wyu.snorlax.processor.AssembleProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceholderHelperTest {

    @Autowired
    private ProcessController processController;

    @Test
    public void testParse() {
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
    }
}
