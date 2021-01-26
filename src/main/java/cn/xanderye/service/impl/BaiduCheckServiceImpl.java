package cn.xanderye.service.impl;

import cn.xanderye.service.CheckService;
import cn.xanderye.util.Base64Util;
import cn.xanderye.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/1/26.
 *
 * @author 叶振东
 */
public class BaiduCheckServiceImpl implements CheckService {
    @Override
    public boolean checkPwd(String url, String shareId, String pwd) throws IOException {
        HttpUtil.doGet(url, null, new HashMap<>(), null);
        Map<String, String> map = HttpUtil.getCookies();
        String BAIDUID = map.get("BAIDUID");
        String FG = map.get("FG");
        Map<String, Object> cookieMap = new HashMap<>();
        cookieMap.put("BAIDUID", BAIDUID);
        cookieMap.put("FG", FG);
        String verifyUrl = "https://pan.baidu.com/share/verify?";
        verifyUrl += "surl=" + shareId;
        verifyUrl += "&t=" + System.currentTimeMillis();
        verifyUrl += "&channel=chunlei&web=1&app_id=250528";
        verifyUrl += "&bdstoken=null";
        verifyUrl += "&logid=" + Base64Util.encode(BAIDUID + ":FG=" + FG);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Referer", url);
        Map<String, Object> params = new HashMap<>();
        params.put("pwd", pwd);
        String res = HttpUtil.doPost(verifyUrl, headers, cookieMap, params);
        JSONObject jsonObject = JSON.parseObject(res);
        return jsonObject.getInteger("errno") == 0;
    }
}
