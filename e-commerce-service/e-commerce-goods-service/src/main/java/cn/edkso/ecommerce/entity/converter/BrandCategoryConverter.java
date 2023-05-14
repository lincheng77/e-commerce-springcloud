package cn.edkso.ecommerce.entity.converter;

import cn.edkso.ecommerce.constant.BrandCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-05-14 21:17
 * @description 品牌分类枚举属性转换器
 */

@Getter
@AllArgsConstructor
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {


    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}
