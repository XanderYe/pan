package cn.xanderye.service.impl;

import cn.xanderye.service.CheckService;
import cn.xanderye.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/1/26.
 *
 * @author 叶振东
 */
public class LanzouCheckServiceImpl implements CheckService {
    @Override
    public boolean checkPwd(String url, String shareId, String pwd) throws IOException {
        String html = HttpUtil.doGet(url, null, new HashMap<>(), null);
        Map<String, Object> params = new HashMap<>();
        String secret = StringUtils.substringBetween(html, "var pgs;", "pgs").trim();
        String[] secrets = secret.split(";");
        String ts = secrets[0].split("=")[1];
        String t = StringUtils.substringBetween(ts, " '", "'");
        String ks = secrets[1].split("=")[1];
        String k = StringUtils.substringBetween(ks, " '", "'");
        params.put("lx", StringUtils.substringBetween(html, "'lx':", ","));
        params.put("fid", StringUtils.substringBetween(html, "'fid':", ","));
        params.put("pg", StringUtils.substringBetween(html, "pgs =", ";"));
        params.put("rep", StringUtils.substringBetween(html, "'rep':'", "',"));
        params.put("uid", StringUtils.substringBetween(html, "'uid':'", "',"));
        params.put("t", t);
        params.put("k", k);
        params.put("up", StringUtils.substringBetween(html, "'up':", ","));
        params.put("ls", StringUtils.substringBetween(html, "'ls':", ","));
        params.put("pwd", pwd);
        String checkUrl = url.substring(0, url.lastIndexOf("/")) + "/filemoreajax.php";
        String res = HttpUtil.doPost(checkUrl, null, new HashMap<>(), params);
        JSONObject jsonObject = JSON.parseObject(res);
        return "sucess".equals(jsonObject.getString("info"));
    }
}
