package com.wyu.snorlax.component;

import com.wyu.snorlax.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author novo
 * @since 2023-02-22 21:50
 */
@Component
@EnableConfigurationProperties(value = SmsProperties.class)
@Slf4j
public class CodeSmsComponent implements SmsComponent{
    private static final String URL_PATTERN = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";

    private final CloseableHttpClient httpClient = createHttpClient();

    @Autowired
    private SmsProperties smsProperties;

    public void send(String to, String templateId, String value) {
        long beginTime = CommonUtil.getCurrentTimestamp();
        String url = String.format(URL_PATTERN, to, templateId, value);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", "APPCODE " + this.smsProperties.getAppCode());
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
