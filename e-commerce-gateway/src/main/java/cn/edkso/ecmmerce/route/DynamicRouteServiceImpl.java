package cn.edkso.ecmmerce.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-16 19:27
 * @description 事件推送 Aware: 动态更新路由网关 Service
 */

@Slf4j
@Service
@SuppressWarnings("all")
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /**
     * 写路由定义
     */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 获取路由定义
     */
    private final RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 事件发布
     */
    private ApplicationEventPublisher publisher;

    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter,
                                   RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 完成事件推送句柄的初始化
        this.publisher = applicationEventPublisher;
    }

    /**
     * 增加路由定义
     *
     * @param definition
     * @return
     */
    public String addRouteDefinition(RouteDefinition definition) {
        log.info("gateway add route: [{}]", definition);

        //保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        //发布时间通知给 Gateway，同步新增的路由定义
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }


    /**
     * 更新路由
     *
     * @param definition
     * @return
     */
    public String updateByRouteDefinition(RouteDefinition definition) {

        try {
            log.info("gateway update route: [{}]", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception ex) {
            return "update fail, not find route routeId: " + definition.getId();
        }

        try {
            this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception ex) {
            return "update route fail";
        }
    }

    /**
     * 根据路由 id 删除路由配置
     *
     * @param id
     * @return
     */
    private String deleteById(String id) {
        try {
            log.info("gateway delete route id: [{}]", id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            // 发布事件通知给 gateway 更新路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception e) {
            log.error("gateway delete route fail: [{}]", e.getMessage(), e);
            return "delete fail";
        }
    }


    /**
     * 更新路由
     *
     * @param definitions
     * @return
     */
    public String updateList(List<RouteDefinition> nacosDefinitions) {
        log.info("gateway update route: [{}]", nacosDefinitions);

        //先拿到Gateway中存储的路由定义
        List<RouteDefinition> gatewayDefinitions =
                routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();

        if (!CollectionUtils.isEmpty(gatewayDefinitions)) {
            //清除掉之前所有的“旧的”路由定义
            gatewayDefinitions.forEach(e -> {
                log.info("delete route definition: [{}]", e);
                deleteById(e.getId());
            });
        }

        //把更新的路由定义同步到 gateway中
        nacosDefinitions.forEach(definition -> updateByRouteDefinition(definition));
        return "success";
    }

}
