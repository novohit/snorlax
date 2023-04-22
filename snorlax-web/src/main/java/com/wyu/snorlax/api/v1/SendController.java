package com.wyu.snorlax.api.v1;

import com.alibaba.fastjson2.JSONObject;
import com.wyu.snorlax.domain.MessageParam;
import com.wyu.snorlax.domain.SendRequest;
import com.wyu.snorlax.domain.SendResponse;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-04-13
 */
@RestController
@RequestMapping
public class SendController {

    @Autowired
    private SendService sendService;

    @GetMapping("/send")
    public String testSend() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "novo");
        map.put("age", "11");
        MessageParam param = MessageParam.builder()
                .receiver("13211291857,13211291858,13211291859")
                .variables(map)
                .build();
        SendRequest sendRequest = SendRequest.builder()
                .type(ChainType.SEND.name())
                .templateId(2L)
                .messageParam(param)
                .build();
        SendResponse sendResponse = this.sendService.send(sendRequest);
        return JSONObject.toJSONString(sendResponse);
    }
}
