package cn.xanderye.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created on 2021/1/25.
 *
 * @author XanderYe
 */
@Data
@Accessors(chain = true)
public class Share {
    private Long id;

    private String shareId;

    private Integer sourceType;

    private String password;

    private Boolean error;

    private String url;
}
