package com.wyu.snorlax.api.v1;

import com.wyu.snorlax.chain.ProcessContext;
import com.wyu.snorlax.chain.ProcessController;
import com.wyu.snorlax.enums.ChainType;
import com.wyu.snorlax.repository.MessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public void test() {
//        List<MessageTemplate> all = this.repository.findAll();
//        System.out.println(all);

        ProcessContext context = new ProcessContext();
        //context.setModel("test");
        context.setChainType(ChainType.SEND);
        processController.process(context);

    }

}
