package cn.xanderye.service;

import cn.xanderye.entity.Share;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
public interface ShareService {

    /**
     * 获取
     * @param shareId
     * @param type
     * @return cn.xanderye.entity.Password
     * @author XanderYe
     * @date 2021/1/25
     */
    Share getShareByShareIdAndType(String shareId, Integer type);

    /**
     * 保存
     * @param share
     * @return void
     * @author XanderYe
     * @date 2021/1/25
     */
    void saveShare(Share share);

    /**
     * 更新状态
     * @param
     * @return void
     * @author XanderYe
     * @date 2021/1/26
     */
    void changeErrorById(Long id, Boolean error);

    /**
     * 删除
     * @param id
     * @return void
     * @author XanderYe
     * @date 2021/1/26
     */
    void deleteById(Long id);
}
