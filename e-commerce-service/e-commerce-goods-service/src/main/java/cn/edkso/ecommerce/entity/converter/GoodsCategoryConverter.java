package cn.edkso.ecommerce.entity.converter;



import cn.edkso.ecommerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-05-14 21:15
 * @description 商品类别枚举属性转换器
 */

public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory, String> {

    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(String code) {
        return GoodsCategory.of(code);
    }
}
