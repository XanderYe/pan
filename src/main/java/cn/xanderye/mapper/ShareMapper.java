package cn.xanderye.mapper;

import cn.xanderye.entity.Share;
import org.apache.ibatis.annotations.Param;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
public interface ShareMapper {

    /**
     * 查询正确的密码
     * @param shareId
     * @return cn.xanderye.entity.Password
     * @author XanderYe
     * @date 2021/1/25
     */
    Share getShareByShareIdAndType(@Param("shareId") String shareId, @Param("sourceType") Integer sourceType);

    /**
     * 查询所有密码
     * @param shareId
     * @return cn.xanderye.entity.Password
     * @author XanderYe
     * @date 2021/1/25
     */
    Share getAllShareByShareId(@Param("shareId") String shareId);

    /**
     * 存储密码
     * @param share
     * @return void
     * @author XanderYe
     * @date 2021/1/25
     */
    void insertShare(Share share);

    /**
     * 根据id更新密码
     * @param share
     * @return void
     * @author XanderYe
     * @date 2021/1/25
     */
    void updateShareById(@Param("share") Share share);

    /**
     * 根据shareId更新密码
     * @param share
     * @return void
     * @author XanderYe
     * @date 2021/1/25
     */
    void updateErrorById(@Param("share") Share share);

    /**
     * 删除
     * @param id
     * @return void
     * @author XanderYe
     * @date 2021/1/26
     */
    void deleteById(Long id);
}
