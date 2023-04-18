package cn.edkso.ecommerce;

import cn.edkso.ecommerce.vo.LoginUserInfo;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-18 13:05
 * @description 使用 ThreadLocal 去单独存储每一个线程携带的 LoginUserInfo 信息
 * 要及时的清理我们保存到 ThreadLocal 中的用户信息:
 * 1. 保证没有资源泄露
 * 2. 保证线程在重用时, 不会出现数据混乱
 */
public class AccessContext {

    private static final ThreadLocal<LoginUserInfo> loginUserInfoTL = new ThreadLocal<>();

    public static LoginUserInfo getLoginUserInfo() {
        return loginUserInfoTL.get();
    }

    public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        loginUserInfoTL.set(loginUserInfo);
    }

    public static void clearLoginUserInfo(){
        loginUserInfoTL.remove();
    }
}
