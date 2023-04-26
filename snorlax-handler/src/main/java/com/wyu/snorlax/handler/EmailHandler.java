package com.wyu.snorlax.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.sun.mail.util.MailSSLSocketFactory;
import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.model.ChannelAccount;
import com.wyu.snorlax.model.bo.EmailContent;
import com.wyu.snorlax.model.bo.SmsContent;
import com.wyu.snorlax.model.dto.TaskInfo;
import com.wyu.snorlax.repository.ChannelAccountRepository;
import com.wyu.snorlax.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * @author novo
 * @since 2023-04-26
 */
@Service
@Slf4j
public class EmailHandler extends BaseHandler {

    @Autowired
    private ChannelAccountRepository accountRepository;

    public EmailHandler() {
        this.channelType = ChannelType.EMAIL.name();
    }

    @Override
    public void handle(TaskInfo taskInfo) {
        EmailContent content = (EmailContent) taskInfo.getContent();
        log.info("send email {}", content);
        Long channelAccountId = taskInfo.getChannelAccountId();
        this.accountRepository.findById(channelAccountId).ifPresentOrElse(
                channelAccount -> {
                    MailAccount account = JSON.parseObject(channelAccount.getConfig(), MailAccount.class);
                    log.info("{}", account);
                    try {
                        MailSSLSocketFactory sf = new MailSSLSocketFactory();
                        sf.setTrustAllHosts(true);
                        account.setAuth(account.isAuth())
                                .setStarttlsEnable(account.isStarttlsEnable())
                                .setSslEnable(account.isSslEnable())
                                .setCustomProperty("mail.smtp.ssl.socketFactory", sf);
                        account.setTimeout(25000).setConnectionTimeout(25000);
                    } catch (Exception e) {
                        log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
                    }

                    try {
                        long start = CommonUtil.getCurrentTimestamp();
                        String messageId = MailUtil.send(account, taskInfo.getReceiver(), content.getTitle(), content.getContent(), true);
                        log.info("发送耗时:{}ms", CommonUtil.getCurrentTimestamp() - start);
                        log.info("messageId:{}", messageId);
                    } catch (Exception e) {
                        log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
                    }
                },
                () -> log.error("account is null"));
    }
}
