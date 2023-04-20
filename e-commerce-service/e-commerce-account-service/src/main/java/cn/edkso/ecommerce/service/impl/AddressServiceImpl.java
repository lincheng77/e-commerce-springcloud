package cn.edkso.ecommerce.service.impl;

import cn.edkso.ecommerce.AccessContext;
import cn.edkso.ecommerce.account.AddressInfo;
import cn.edkso.ecommerce.common.TableIds;
import cn.edkso.ecommerce.dao.EcommerceAddressDao;
import cn.edkso.ecommerce.entity.EcommerceAddress;
import cn.edkso.ecommerce.service.AddressService;
import cn.edkso.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-20 13:06
 * @description
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {

    @Autowired
    private EcommerceAddressDao addressDao;

    /**
     * 存储多个地址信息
     *
     * @param addressInfo
     * @return
     */
    @Override
    public TableIds createAddressInfo(AddressInfo addressInfo) {
        //不能直接从参数中获取用户的id信息
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        //将传递的参数转换成实体对象
        List<AddressInfo.AddressItem> addressItems = addressInfo.getAddressItems();
        List<EcommerceAddress> ecommerceAddresses = addressItems.stream().map(element ->
                EcommerceAddress.ofUserIdAndAddressItem(loginUserInfo.getId(), element)
        ).collect(Collectors.toList());

        //保存到数据表并把返回记录的id 给调用方
        List<EcommerceAddress> saveResults = addressDao.saveAll(ecommerceAddresses);
        List<Long> ids = saveResults.stream()
                .map(EcommerceAddress::getId)
                .collect(Collectors.toList());
        log.info("create address info: [{}], [{}]", loginUserInfo.getId(), JSON.toJSONString(ids));

        List<TableIds.Id> tableIds = ids.stream().map(
                TableIds.Id::new
        ).collect(Collectors.toList());

        return new TableIds(tableIds);
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 根据 userId查询到用户的地址信息，再实现转换
        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllByUserId(loginUserInfo.getId());

        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(), addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {
        EcommerceAddress ecommerceAddress = addressDao.findById(id).orElse(null);

        if (ecommerceAddress == null) {
            throw new RuntimeException("address is not exits");
        }

        AddressInfo addressInfo = new AddressInfo(ecommerceAddress.getUserId(), Collections.singletonList(ecommerceAddress.toAddressItem()));
        return addressInfo;
    }

    @Override
    public AddressInfo getAddressInfoByTableIds(TableIds tableIds) {
        List<Long> ids = tableIds.getIds().stream().map(TableIds.Id::getId).collect(Collectors.toList());

        List<EcommerceAddress> ecommerceAddressList = addressDao.findAllById(ids);
        if (CollectionUtils.isEmpty(ecommerceAddressList)){
            return new AddressInfo(-1L, Collections.emptyList());
        }

        List<AddressInfo.AddressItem> addressItemList = ecommerceAddressList.stream().map(EcommerceAddress::toAddressItem).collect(Collectors.toList());
        AddressInfo addressInfo = new AddressInfo(ecommerceAddressList.get(0).getUserId(), addressItemList);

        return addressInfo;
    }
}
