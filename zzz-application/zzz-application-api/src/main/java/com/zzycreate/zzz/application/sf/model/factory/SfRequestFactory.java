package com.zzycreate.zzz.application.sf.model.factory;

import com.zzycreate.zzz.application.sf.model.SfBody;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
public class SfRequestFactory {

    private static final String TRACK_REQUEST = "<Request service='RouteService' lang='zh-CN'>\n" +
            "<Head>{{0}}</Head>\n" +
            "<Body>\n" +
            "<RouteRequest\n" +
            "tracking_type='2'\n" +
            "method_type='1'\n" +
            "tracking_number='{{1}}'\n" +
            "check_phoneNo='{{2}}'/>\n" +
            "</Body>\n" +
            "</Request>";

    public static SfBody buildTrackRequestBody(String trackingNumber, String phoneNo, String sfCode, String checkword)
            throws NoSuchAlgorithmException {
        String xml = buildTrackRequestXml(trackingNumber, phoneNo, sfCode);
        String verifyCode = buildTrackRequestVerifyCode(xml, checkword);
        return SfBody.builder().xml(xml).verifyCode(verifyCode).build();
    }

    public static String buildTrackRequestXml(String trackingNumber, String phoneNo, String sfCode) {
        return TRACK_REQUEST.replace("{{0}}", sfCode).replace("{{1}}", trackingNumber).replace("{{2}}", phoneNo);
    }

    public static String buildTrackRequestVerifyCode(String xml, String checkword) throws NoSuchAlgorithmException {
        return verifyCode(xml, checkword);
    }

    public static String verifyCode(String xml, String checkword) throws NoSuchAlgorithmException {
        return base64(md5(xml + checkword));
    }

    private static byte[] md5(String encryptStr) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(encryptStr.getBytes(StandardCharsets.UTF_8));
        return md5.digest();
    }

    private static String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
