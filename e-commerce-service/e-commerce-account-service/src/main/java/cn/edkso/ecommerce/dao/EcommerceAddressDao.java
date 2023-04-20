package cn.edkso.ecommerce.dao;

import cn.edkso.ecommerce.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-20 11:15
 * @description EcommerceAddressDao Dao 接口定义
 */

public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress, Long> {

    /**
     * 根据用户id 查询地址信息
     * @param userId
     * @return
     */
    List<EcommerceAddress> findAllByUserId(Long userId);
}
