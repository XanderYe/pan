package cn.xanderye.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created on 2021/1/26.
 *
 * @author 叶振东
 */
public class Base64Util {
    public static String encode(String str) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(str.getBytes());
    }

    public static String decode(String str) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return new String(base64Decoder.decodeBuffer(str));
    }
}
