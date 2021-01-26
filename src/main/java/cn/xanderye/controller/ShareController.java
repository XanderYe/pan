package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.config.ShareCache;
import cn.xanderye.entity.Share;
import cn.xanderye.enums.ErrorCodeEnum;
import cn.xanderye.exception.BusinessException;
import cn.xanderye.service.impl.BaiduCheckServiceImpl;
import cn.xanderye.service.CheckService;
import cn.xanderye.service.impl.LanzouCheckServiceImpl;
import cn.xanderye.service.ShareService;
import cn.xanderye.service.impl.TianyiCheckServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
@RestController
@RequestMapping("share")
public class ShareController {
    @Autowired
    private ShareService shareService;

    @GetMapping("getPassword")
    public ResultBean getPassword(String shareId, Integer type) {
        if (StringUtils.isEmpty(shareId)) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_EMPTY);
        }
        Share share = shareService.getShareByShareIdAndType(shareId, type);
        ResultBean resultBean = new ResultBean();
        if (share != null && share.getPassword() != null) {
            resultBean.setData(share);
        } else {
            resultBean.setCode(2);
        }
        return resultBean;
    }

    @PostMapping("savePassword")
    public ResultBean savePassword(@RequestBody Share share) {
        if (share.getShareId() == null || share.getPassword() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_EMPTY);
        }
        CheckService checkService;
        switch (share.getSourceType()) {
            case 0: {
                checkService = new BaiduCheckServiceImpl();
            }
            break;
            case 1: {
                checkService = new LanzouCheckServiceImpl();
            }
            break;
            case 2: {
                checkService = new TianyiCheckServiceImpl();
            }
            break;
            default: {
                checkService = null;
            }
        }
        // 是否识别云盘
        if (checkService == null) {
            throw new BusinessException(ErrorCodeEnum.UNKNOWN_PAN_ERROR);
        }
        boolean check;
        try {
            check = checkService.checkPwd(share.getUrl(), share.getShareId(), share.getPassword());
        } catch (IOException exception) {
            check = false;
        }
        // 密码是否正确
        if (!check) {
            throw new BusinessException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        share.setError(false);
        shareService.saveShare(share);
        return new ResultBean();
    }
}
