package cn.xanderye.mapper;

import cn.xanderye.entity.Share;
import cn.xanderye.enums.SourceTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2021/1/25.
 *
 * @author 叶振东
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShareMapperTest {
    @Autowired
    private ShareMapper shareMapper;

    @Test
    public void getPasswordByShareIdAndType() {
        Share share = shareMapper.getShareByShareIdAndType("test", null);
        System.out.println(share.toString());
    }

    @Test
    public void insertPassword() {
        Share share = new Share();
        share.setShareId("test");
        share.setPassword("123");
        share.setSourceType(SourceTypeEnum.BAIDU.getType());
        shareMapper.insertShare(share);
    }

    @Test
    public void updatePasswordById() {
        Share share = new Share();
        share.setId(2L);
        share.setPassword(String.valueOf(System.currentTimeMillis()));
        shareMapper.updateShareById(share);
    }
}
