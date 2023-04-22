package cn.edkso.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-22 20:32
 * @description 用户账户余额信息
 */
@ApiModel(description = "用户账户余额信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {

    @ApiModelProperty(value = "用户主键 id")
    private Long userId;

    @ApiModelProperty(value = "用户账户余额")
    private Long balance;
}