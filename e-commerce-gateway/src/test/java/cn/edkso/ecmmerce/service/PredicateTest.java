package cn.edkso.ecmmerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList(
            "nacos", "authority", "gateway", "ribbon", "geign", "hystrix", "e-commerce"
    );

    /**
     * 方法主要用于参数符不符合规则，返回值是boolean
     */
    @Test
    public void testPredicate() {
        Predicate<String> predicate = s -> s.length() > 5;
        MICRO_SERVICE.stream().filter(predicate).forEach(System.out::println);

    }

    /**
     * and 方法等同于我们的逻辑&&，存在短路特性，需要满足所有条件
     */
    @Test
    public void testPredicateAnd() {
        Predicate<String> letterStartWith = s -> s.startsWith("gate");
        Predicate<String> predicate = s -> s.length() > 5;

        MICRO_SERVICE.stream().filter(predicate.and(letterStartWith)).forEach(System.out::println);
    }

    /**
     * or 等同于我们的逻辑或 ||, 多个条件主要一个满足即可
     */
    @Test
    public void testPredicateOr(){
        Predicate<String> letterStartWith = s -> s.startsWith("gate");
        Predicate<String> predicate = s -> s.length() > 5;

        MICRO_SERVICE.stream().filter(predicate.or(letterStartWith)).forEach(System.out::println);
    }

    /**
     * negate 等同于我们的逻辑非 !
     */
    @Test
    public void testPredicateNegate(){
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(letterStartWith.negate()).forEach(System.out::println);
    }


    /**
     * isEqual 类似于 equals(), 区别在于: 先判断对象是否为 NULL, 不为 NULL 再使用 equals 进行比较
     */
    @Test
    public void testPredicateIsEqual(){
        Predicate<Object> predicate = Predicate.isEqual("gateway");
//        Predicate<String> letterStartWith = s -> predicate.test(s);
        Predicate<String> letterStartWith = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return predicate.test(s);
            }
        };

        MICRO_SERVICE.stream().filter(letterStartWith).forEach(System.out::println);
    }

}
