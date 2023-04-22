package com.wyu.snorlax.component;

import com.wyu.snorlax.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author novo
 * @since 2023-04-21
 */
@Component
@Slf4j
public class ValueSmsComponent implements SmsComponent {

    private static final String URL_PATTERN = "https://dxyzm.market.alicloudapi.com/chuangxin/dxjk";

    private final CloseableHttpClient httpClient = createHttpClient();


    @Override
    public void send(String to, String templateId, String value) {
        long beginTime = CommonUtil.getCurrentTimestamp();
        String url = URL_PATTERN;
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> queryParam = new ArrayList<>();
        queryParam.add(new BasicNameValuePair("mobile", to));
        queryParam.add(new BasicNameValuePair("content", "【创信】你的验证码是：5873，3分钟内有效！"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(queryParam, StandardCharsets.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        httpPost.setHeader("Authorization", "APPCODE " + "d6c2552f8a954275bd36dfba31838f7e");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            int code = httpResponse.getStatusLine().getStatusCode();
            long endTime = CommonUtil.getCurrentTimestamp();
            String body = EntityUtils.toString(httpResponse.getEntity());
            if (code == HttpStatus.SC_OK) {
                log.info("发送短信成功");
                log.info("耗时:[{}ms], url:[{}],body:[{}]", endTime - beginTime, url, body);
            } else {
                log.info("发送短信失败:[{}]", body);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
