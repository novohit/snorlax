package com.wyu.snorlax.util;

import com.google.common.hash.Hashing;
import com.wyu.snorlax.enums.ChannelType;
import com.wyu.snorlax.enums.MessageType;
import com.wyu.snorlax.model.dto.TaskInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author novo
 * @date 2023-02-21 14:24
 */
@Slf4j
public class CommonUtil {

    /**
     * 获取所有groupIds
     * 例如 sms.notice sms.code
     *
     * @return
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (MessageType messageType : MessageType.values()) {
            for (ChannelType channelType : ChannelType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }

    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = ChannelType.toType(taskInfo.getChannelType()).getCodeEn();
        String messageCodeEn = MessageType.toType(taskInfo.getMsgType()).getCodeEn();
        return channelCodeEn + "." + messageCodeEn;
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     */
    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取验证码随机数
     *
     * @param length
     * @return
     */
    public static String getRandomCode(int length) {

        String sources = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            sb.append(sources.charAt(random.nextInt(9)));
        }
        return sb.toString();
    }


    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }


    /**
     * 生成uuid
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 获取随机长度的串
     *
     * @param length
     * @return
     */
    private static final String ALL_CHAR_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String getStringNumRandom(int length) {
        //生成随机数字和字母,
        Random random = new Random();
        StringBuilder saltString = new StringBuilder(length);
        for (int i = 1; i <= length; ++i) {
            saltString.append(ALL_CHAR_NUM.charAt(random.nextInt(ALL_CHAR_NUM.length())));
        }
        return saltString.toString();
    }


    /**
     * google murmurhash算法
     *
     * @param value
     * @return
     */
    @SuppressWarnings(value = {"all"})
    public static long murmurHash32(String value) {
        long murmurHash32 = Hashing.murmur3_32().hashUnencodedChars(value).padToLong();
        return murmurHash32;
    }

    /**
     * 给长链接拼装一个时间戳前缀，使得同一个长链可以生成不同的短链
     *
     * @param originUrl
     * @return
     */
    public static String addUrlPrefix(String originUrl) {
        return getCurrentTimestamp() + "&" + originUrl;
    }

    /**
     * 去除长链的时间戳前缀
     *
     * @param prefixUrl
     * @return
     */
    public static String removeUrlPrefix(String prefixUrl) {
        return prefixUrl.substring(prefixUrl.indexOf("&") + 1);
    }

    /**
     * 防止hash冲突
     *
     * @param prefixUrl
     * @return
     */
    public static String getNewUrl(String prefixUrl) {
        String timestamp = prefixUrl.substring(0, prefixUrl.indexOf("&"));
        String newTimestamp = String.valueOf(Long.parseLong(timestamp) + 1);
        String newUrl = newTimestamp + prefixUrl.substring(prefixUrl.indexOf("&") + 1);
        return newUrl;
    }


    /**
     * 获取url的logo
     */
    public static String getLogoUrl(String prefixUrl) {
        String originUrl = removeUrlPrefix(prefixUrl);
        URI uri = null;
        try {
            uri = new URI(originUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String host = uri.getHost();
        String HTTPS = "https://";
        String HTTP = "http://";
        String faviconSuffix = "/favicon.ico";
        if (originUrl.contains(HTTPS)) {
            return HTTPS + host + faviconSuffix;
        } else if (originUrl.contains(HTTP)) {
            return HTTP + host + faviconSuffix;
        } else {
            return host + faviconSuffix;
        }
    }
}
