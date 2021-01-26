package cn.xanderye.service.impl;

import cn.xanderye.service.CheckService;
import cn.xanderye.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/1/26.
 *
 * @author XanderYe
 */
public class TianyiCheckServiceImpl implements CheckService {
    @Override
    public boolean checkPwd(String url, String shareId, String pwd) throws IOException {
        String checkUrl = "https://cloud.189.cn/shareFileByVerifyCode.action";
        Map<String, Object> params = new HashMap<>();
        params.put("accessCode", pwd);
        params.put("shortCode", shareId);
        params.put("noCache", System.currentTimeMillis());
        String res = HttpUtil.doGet(checkUrl, params);
        return !"null".equals(res);
    }
}
