package cn.xanderye.service;

import java.io.IOException;

/**
 * Created on 2021/1/26.
 *
 * @author 叶振东
 */
public interface CheckService {

    boolean checkPwd(String url, String shareId, String pwd) throws IOException;
}
