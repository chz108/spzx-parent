package com.atguigu.spzx.model.entity.h5;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
@Data
@Schema(description = "收藏实体类")
public class UserCollect extends BaseEntity {
    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "skuid")
    private Long skuId;
}
