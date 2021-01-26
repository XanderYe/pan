package cn.xanderye.service.impl;

import cn.xanderye.entity.Share;
import cn.xanderye.mapper.ShareMapper;
import cn.xanderye.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private ShareMapper shareMapper;

    @Override
    public Share getShareByShareIdAndType(String shareId, Integer sourceType) {
        return shareMapper.getShareByShareIdAndType(shareId, sourceType);
    }

    @Override
    public void saveShare(Share share) {
        Share find = shareMapper.getAllShareByShareId(share.getShareId());
        if (null == find) {
            shareMapper.insertShare(share);
        } else {
            share.setId(find.getId());
            shareMapper.updateShareById(share);
        }
    }

    @Override
    public void changeErrorById(Long id, Boolean error) {
        Share share = new Share();
        share.setId(id);
        share.setError(true);
        shareMapper.updateErrorById(share);
    }

    @Override
    public void deleteById(Long id) {
        shareMapper.deleteById(id);
    }
}
