package cn.xanderye.service;

import java.io.IOException;

/**
 * Created on 2021/1/26.
 *
 * @author XanderYe
 */
public interface CheckService {

    boolean checkPwd(String url, String shareId, String pwd) throws IOException;
}
