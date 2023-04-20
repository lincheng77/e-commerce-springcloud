package cn.edkso.ecommerce.service;

import cn.edkso.ecommerce.account.AddressInfo;
import cn.edkso.ecommerce.common.TableIds;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-20 11:19
 * @description 用户地址相关服务接口定义
 */
public interface AddressService {

    /**
     * 创建多个用户地址信息
     * @param addressInfo
     * @return
     */
    TableIds createAddressInfo(AddressInfo addressInfo);


    /**
     * 获取当前登录信息地址信息
     * @return
     */
    AddressInfo getCurrentAddressInfo();

    AddressInfo getAddressInfoById(Long id);


    AddressInfo getAddressInfoByTableIds(TableIds tableIds);
}
