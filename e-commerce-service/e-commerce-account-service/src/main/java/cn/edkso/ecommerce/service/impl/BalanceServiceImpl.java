package cn.edkso.ecommerce.service.impl;

import cn.edkso.ecommerce.AccessContext;
import cn.edkso.ecommerce.account.AddressInfo;
import cn.edkso.ecommerce.account.BalanceInfo;
import cn.edkso.ecommerce.common.TableIds;
import cn.edkso.ecommerce.dao.EcommerceAddressDao;
import cn.edkso.ecommerce.dao.EcommerceBalanceDao;
import cn.edkso.ecommerce.entity.EcommerceAddress;
import cn.edkso.ecommerce.entity.EcommerceBalance;
import cn.edkso.ecommerce.service.AddressService;
import cn.edkso.ecommerce.service.BalanceService;
import cn.edkso.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-22 20:32
 * @description 用于余额相关服务接口实现
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private EcommerceBalanceDao balanceDao;

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getId());

        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        if (ecommerceBalance == null) {
            ecommerceBalance = new EcommerceBalance();
            ecommerceBalance.setUserId(loginUserInfo.getId());
            ecommerceBalance.setBalance(0L);
        }

        balanceInfo.setBalance(ecommerceBalance.getBalance());
        return balanceInfo;
    }

    /**
     * 说明： 扣减用户余额的一个基本原则: 扣减额 <= 当前用户余额
     * @param balanceInfo   代表想要扣减的余额
     * @return
     */
    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        if (ecommerceBalance == null) {
            throw new RuntimeException("user balance is not exist!");
        }
        Long deductResult = ecommerceBalance.getBalance() - balanceInfo.getBalance();
        if (deductResult < 0) {
            throw new RuntimeException("user balance is not exist!");
        }

        ecommerceBalance.setBalance(deductResult);
        balanceDao.save(ecommerceBalance);

        balanceInfo.setBalance(deductResult);
        return balanceInfo;
    }
}
