package cn.edkso.ecommerce.controller;


import cn.edkso.ecommerce.annotation.IgnoreResponseAdvice;
import cn.edkso.ecommerce.service.IJWTService;
import cn.edkso.ecommerce.vo.JwtToken;
import cn.edkso.ecommerce.vo.UsernameAndPassword;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("authority")
public class AuthorityController {

    @Autowired
    private IJWTService jwTService;


    /**
     * 从授权中心获取 Token (其实就是登录功能), 且返回信息中没有统一响应的包装
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    @IgnoreResponseAdvice //代码做了统一响应处理，使用该注解忽略
    @RequestMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to get token with param: [{}]", JSON.toJSONString(usernameAndPassword));
        JwtToken token = new JwtToken(
                jwTService.generateToken(
                        usernameAndPassword.getUsername(),
                        usernameAndPassword.getPassword()));
        return token;
    }

    @IgnoreResponseAdvice //代码做了统一响应处理，使用该注解忽略
    @RequestMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("register user with param: [{}]", JSON.toJSONString(usernameAndPassword));

        return new JwtToken(
                jwTService.registryUserAndGenerateToken(usernameAndPassword)
        );
    }
}
