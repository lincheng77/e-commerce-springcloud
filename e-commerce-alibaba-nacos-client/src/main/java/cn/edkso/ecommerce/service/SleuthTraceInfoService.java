package cn.edkso.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import brave.Tracer;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-19 9:25
 * @description 使用代码更直观看到Sleuth 生成相关跟踪信息
 */

@Slf4j
@Service
public class SleuthTraceInfoService {

    @Autowired
    private Tracer tracer;


    public void logCurrentTraceInfo(){
        long traceId = tracer.currentSpan().context().traceId();
        long spanId = tracer.currentSpan().context().spanId();

        log.info("Sleuth trace id: [{}]", traceId);
        log.info("Sleuth span id: [{}]", spanId);
    }

}
