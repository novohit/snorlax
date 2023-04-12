package com.wyu.snorlax.api.v1;

import com.wyu.snorlax.model.MessageTemplate;
import com.wyu.snorlax.repository.MessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-12
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private MessageTemplateRepository repository;

    @GetMapping("/test")
    public void test() {
        List<MessageTemplate> all = this.repository.findAll();
        System.out.println(all);
    }

}
