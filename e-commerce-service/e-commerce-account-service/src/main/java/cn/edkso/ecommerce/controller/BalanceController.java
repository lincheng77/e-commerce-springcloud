package cn.edkso.ecommerce.controller;

import cn.edkso.ecommerce.account.BalanceInfo;
import cn.edkso.ecommerce.service.BalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-22 20:38
 * @description
 */


@Api(tags = "用户余额服务")
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @ApiOperation(value = "当前用户", notes = "获取当前用户余额信息", httpMethod = "GET")
    @GetMapping("/current-balance")
    public BalanceInfo getCurrentUserBalanceInfo() {
        return balanceService.getCurrentUserBalanceInfo();
    }

    @ApiOperation(value = "扣减", notes = "扣减用于余额", httpMethod = "PUT")
    @PutMapping("/deduct-balance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo) {
        return balanceService.deductBalance(balanceInfo);
    }
}
