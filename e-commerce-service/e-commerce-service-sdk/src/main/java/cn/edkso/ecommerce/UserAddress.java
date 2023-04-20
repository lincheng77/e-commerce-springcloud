package cn.edkso.ecommerce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-19 21:08
 * @description
 */

@ApiModel(description = "用户地址信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "详细的地址")
    private String addressDetail;
}
