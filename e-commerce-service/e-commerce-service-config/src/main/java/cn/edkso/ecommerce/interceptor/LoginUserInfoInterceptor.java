package cn.edkso.ecommerce.interceptor;

import cn.edkso.ecommerce.AccessContext;
import cn.edkso.ecommerce.constant.CommonConstant;
import cn.edkso.ecommerce.util.TokenParseUtil;
import cn.edkso.ecommerce.vo.LoginUserInfo;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-18 13:08
 * @description
 */

@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //先尝试从http header 里拿到token
        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception e) {
            log.error("parse login user info error: [{}]", e.getMessage(), e);
        }

        //如果程序走到这里，说明header中没有token信息
        if (loginUserInfo == null) {
            throw new RuntimeException("can not parse current login user");
        }

        //设置当前请求上下文，把用户信息填充进去
        log.info("set login user info :[{}]", request.getRequestURI());
        AccessContext.setLoginUserInfo(loginUserInfo);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在请求完全结束后调用，常用语清理资源等工作
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (AccessContext.getLoginUserInfo() != null){
            AccessContext.clearLoginUserInfo();
        }
    }
}
