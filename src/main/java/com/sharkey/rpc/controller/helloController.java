package com.sharkey.rpc.controller;

import com.sharkey.rpc.mapper.ScheduleRequestMapper;
import com.sharkey.rpc.service.TaskManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class helloController {

    @Autowired
    private TaskManagerService taskManagerService;


    @GetMapping("/hello")
    public String hello(){
        taskManagerService.checkWithDataBase();
        return "1";
    }


}
