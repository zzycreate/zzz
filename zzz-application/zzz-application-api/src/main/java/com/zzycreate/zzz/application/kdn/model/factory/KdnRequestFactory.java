package com.zzycreate.zzz.application.kdn.model.factory;

import com.zzycreate.zzz.application.kdn.model.KdnRequest;
import com.zzycreate.zzz.application.kdn.model.track.KdnTrackQuery;
import com.zzycreate.zzz.utils.JacksonHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
public class KdnRequestFactory {

    private static final String CHARSET = "UTF-8";


    public static KdnRequest build(Object obj, String eBusinessId, String appKey, String requestType, String dataType)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String requestData = JacksonHelper.toJsonString(obj);
        return KdnRequest.builder()
                .RequestData(urlEncoder(requestData))
                .EBusinessID(eBusinessId)
                .RequestType(requestType)
                .DataSign(urlEncoder(encrypt(requestData, appKey)))
                .DataType(dataType)
                .build();
    }

    public static KdnRequest buildTrackQueryRequest(KdnTrackQuery trackQuery, String eBusinessId, String appKey)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return build(trackQuery, eBusinessId, appKey, "8001", "2");
    }


    private static String urlEncoder(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, CHARSET);
    }

    private static String encrypt(String content, String appKey)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        appKey = StringUtils.isNotBlank(appKey) ? appKey : "";
        String md5 = md5(content + appKey);
        return Base64.getEncoder().encodeToString(md5.getBytes());
    }

    private static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(CHARSET));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : result) {
            int val = b & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

}
