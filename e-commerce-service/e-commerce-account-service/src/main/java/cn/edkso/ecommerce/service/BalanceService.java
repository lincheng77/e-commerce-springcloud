package cn.edkso.ecommerce.service;

import cn.edkso.ecommerce.account.BalanceInfo;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-22 20:31
 * @description 用于余额相关的服务接口定义
 */
public interface BalanceService {


    /**
     *  获取当前用户余额信息
     * @return
     */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     *  扣减用户余额
     * @param balanceInfo   代表想要扣减的余额
     * @return
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
