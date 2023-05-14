package cn.edkso.ecommerce.entity.converter;

import cn.edkso.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-05-14 21:15
 * @description 商品状态枚举属性转换器
 */
public class GoodsStatusConverter implements AttributeConverter<GoodsStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    @Override
    public GoodsStatus convertToEntityAttribute(Integer status) {
        return GoodsStatus.of(status);
    }
}
