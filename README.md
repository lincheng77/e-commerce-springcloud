# Spring Cloud、Alibaba 微服务架构实战

## 技术栈

* 网关：Spring Cloud Gateway

## ResponseBodyAdvice接口 实现统一响应处理

### 实现作用

实现该接口允许在@ResponseBody或ResponseEntity控制器方法执行**之后**，在使用HttpMessageConverter编写正文**之前**进行**自定义响应**。

### 实现方法

- 注册到RequestMappingHandlerAdapter和ExceptionHandlerExceptionResolver
- @ControllerAdvice进行注释

## Spring Cloud Gateway

### 为什么要使用网关

在微服务架构中，通常一个系统会被拆分为多个微服务，面对这么多微服务客户端应该如何去调用呢？如果没有其他更优方法，我们只能记录每个微服务对应的地址，分别去调用，但是这样会有很多的问题和潜在因素。

1. 客户端多次请求不同的微服务，会增加客户端代码和配置的复杂性，维护成本比价高。
2. 认证复杂，每个微服务可能存在不同的认证方式，客户端去调用，要去适配不同的认证存在跨域的请求，调用链有一定的相对复杂性（防火墙/浏览器不友好的协议）。
3. 难以重构，随着项目的迭代，可能需要重新划分微服务

为了解决上面的问题，微服务引入了 **网关** 的概念，网关为微服务架构的系统提供简单、有效且统一的API路由管理，作为系统的统一入口，提供内部服务的路由中转，给客户端提供统一的服务，可以实现一些和业务没有耦合的公用逻辑，主要功能包含认证、鉴权、路由转发、安全策略、防刷、流量控制、监控日志等。

### 网关在微服务中的作用

* 网关是微服务工程架构下的唯一入口（客户端）
* Gateway提供了统一的路由方式，基于Filter链的方式提供了网关的基本功能

![image.png](assets/image2.png)

### 网关对比

* **Zuul 1.0** : Netflix开源的网关，使用Java开发，基于Servlet架构构建，便于二次开发。因为基于Servlet内部延迟严重(基于阻塞)，并发场景不友好，一个线程只能处理一次连接请求。
* **Zuul 2.0** : 采用Netty实现异步非阻塞编程模型，一个CPU一个线程，能够处理所有的请求和响应，请求响应的生命周期通过事件和回调进行处理，减少线程数量，开销较小
* **Nginx+lua** : 性能要比上面的强很多，使用Nginx的反向代码和负载均衡实现对API服务器的负载均衡以及高可用，lua作为一款脚本语言，可以编写一些简单的逻辑，但是无法嵌入到微服务架构中
* **Kong** : 基于OpenResty（Nginx + Lua模块）编写的高可用、易扩展的，性能高效且稳定，支持多个可用插件（限流、鉴权）等，开箱即可用，只支持HTTP协议，且二次开发扩展难，缺乏更易用的管理和配置方式
* **GateWay** : 是Spring Cloud的一个全新的API网关项目，替换Zuul开发的网关服务，基于Spring5.0 + SpringBoot2.0 + WebFlux（基于性能的Reactor模式响应式通信框架Netty，异步阻塞模型）等技术开发（支持WebSocket），性能高于Zuul

### Gateway三大核心概念

* Route（路由）：是构建网关的基本模块，由ID、URL、一系列的断言和过滤器组成
* Predicate（断言）：可以匹配HTTP请求中所有的内容（请求头、参数等等），请求与断言相匹配则通过当前断言
* Filter（过滤器）：包含全局和局部过滤器，可以在请求 被路由处理前后 对请求进行更改

  ![image.png](assets/image.png?t=1681791723076)

### Gateway过滤器

* 全局过滤器：作用域所有的路由，不需要单独配置，通常用来实现统一化的处理的业务需求
* 局部过滤器：
  1. 需要实现GatewayFilter、Ordered
  2. 加入到过滤器工厂，并将工厂注册到Spring容器中
  3. 在配置文件中进行配置，如果不配置则不起用此过滤器规则（路由规则）

### Gatway常用的三种配置方式

* 在代码中注入RouteLocatorBean，并手工编写配置路由定义
* 在application.yml、boostrap.yml等配置文件中配置spring.cloud.gateway
* 通过配置中心（Nacos实现动态路由配置）
