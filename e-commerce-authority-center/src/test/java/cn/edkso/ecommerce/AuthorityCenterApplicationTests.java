package cn.edkso.ecommerce;


import cn.edkso.ecommerce.util.TokenParseUtil;
import cn.edkso.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 验证授权中心环境可用性
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorityCenterApplicationTests {

    @Test
    public void test(){

    }

    @Test
    public void testAuthentication() throws Exception {
        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken("eyJhbGciOiJSUzI1NiJ9.eyJlLWNvbW1lcmNlLXVzZXIiOiJ7XCJpZFwiOjExLFwidXNlcm5hbWVcIjpcImxpbjExXCJ9IiwianRpIjoiYmIwNzQ2MGYtMzdjYi00ZGFiLTg4MjktZTRkMmYxZDEyY2FlIiwiZXhwIjoxNjgxNDg4MDAwfQ.Nm9O6-kOAArxnPei0iD4MJXo7e5Or1bQ9i6G9lClRiPrjzlsgERsrKtwmt00c8_Fk_Ul98yKEe6aDoTnVOgntmLASoZ1rcM_jdV5PZrrGROL2ANz6xvkOW337K5MPKmOY-NqWyu9FEBZAH-q1QpXl6aKgZxW1Rtdh97EQFO_2NuAr8XTs8ALzajfLoMHa2hIDoFHMRDIl4uAmcYQ6zs5tusmDviHt_b5wWVzTkrL8eREs7GZ0xZNlOrhP8UT7ckjfxvljDafQRvp-uuwikEwsvstbGahBJlpZmMcluBtj_4PXbsxvMRV5-rxCe66YKoGFC1SaPgNkiKxK5dyAPp5IA");

        log.info("loginUserInfo : [{}]", JSON.toJSONString(loginUserInfo));
    }
}
