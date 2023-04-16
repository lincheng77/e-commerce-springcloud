# Spring Cloud、Alibaba 微服务架构实战

## 技术栈

* 网关：Spring Cloud Gateway

## ResponseBodyAdvice接口 实现统一响应处理

### 实现作用

实现该接口允许在@ResponseBody或ResponseEntity控制器方法执行**之后**，在使用HttpMessageConverter编写正文**之前**进行**自定义响应**。

### 实现方法

- 注册到RequestMappingHandlerAdapter和ExceptionHandlerExceptionResolver
- @ControllerAdvice进行注释
