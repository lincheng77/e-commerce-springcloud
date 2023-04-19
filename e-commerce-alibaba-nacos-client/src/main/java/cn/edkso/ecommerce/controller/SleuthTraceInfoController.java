package cn.edkso.ecommerce.controller;

import cn.edkso.ecommerce.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-19 9:33
 * @description
 */

@Slf4j
@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {

    @Autowired
    private SleuthTraceInfoService traceInfoService;

    /**
     * 打印日志跟踪信息
     */
    @GetMapping("/trace-info")
    public void logCurrentTraceInfo(){
        traceInfoService.logCurrentTraceInfo();
    }
}
