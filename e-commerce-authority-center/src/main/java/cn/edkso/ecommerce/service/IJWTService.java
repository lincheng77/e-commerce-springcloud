package cn.edkso.ecommerce.service;

import cn.edkso.ecommerce.vo.UsernameAndPassword;

/**
 * JWT 相关服务接口定义
 */
public interface IJWTService {

    /**
     * 生成 JWT Token， 使用默认的超时时间
     * @param username
     * @param password
     * @return
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * 生成 JWT Token， 使用传入的超时时间，单位：天
     * @param username
     * @param password
     * @param expire
     * @return
     */
    String generateToken(String username, String password, int expire) throws Exception;

    /**
     * 注册用户并生成Token返回
     * @param usernameAndPassword
     * @return
     */
    String registryUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;

}
