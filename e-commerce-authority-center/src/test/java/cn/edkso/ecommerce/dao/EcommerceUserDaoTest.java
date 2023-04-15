package cn.edkso.ecommerce.dao;

import cn.edkso.ecommerce.entity.EcommerceUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class EcommerceUserDaoTest {

    @Autowired
    private EcommerceUserDao ecommerceUserDao;

    @Test
    public void save(){
        EcommerceUser ecommerceUser = new EcommerceUser();

        ecommerceUser.setUsername("lincheng1");
        ecommerceUser.setPassword("lincheng1");
        ecommerceUser.setExtraInfo("");
        EcommerceUser result = ecommerceUserDao.save(ecommerceUser);

        log.debug(JSON.toJSONString(result));



    }
}