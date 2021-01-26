package cn.xanderye.enums;

import lombok.Getter;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
@Getter
public enum SourceTypeEnum {

    BAIDU(0, "百度云"),
    LANZOU(1, "蓝奏云"),
    TIANYI(2, "天翼云");

    private int type;
    private String name;

    SourceTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
