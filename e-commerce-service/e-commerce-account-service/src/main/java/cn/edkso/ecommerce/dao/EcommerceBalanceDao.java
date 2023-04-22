package cn.edkso.ecommerce.dao;

import cn.edkso.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-22 20:30
 * @description EcommerceBalance Dao 接口定义
 */
public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance, Long> {

    /**
     * 根据 userId 查询 EcommerceBalance 对象
     * @param userId
     * @return
     */
    EcommerceBalance findByUserId(Long userId);
}
