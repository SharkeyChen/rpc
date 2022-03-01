package com.sharkey.rpc.service;

import com.sharkey.rpc.entity.MyTask;
import com.sharkey.rpc.entity.ScheduleRequest;
import com.sharkey.rpc.mapper.ScheduleRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskManagerService {

    @Autowired
    private ScheduleRequestMapper scheduleRequestMapper;

    private final ConcurrentHashMap<MyTask, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public boolean isExist(MyTask task){
        return taskMap.containsKey(task);
    }

    public void addTask(MyTask task){
        if(isExist(task)){
            return ;
        }
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, new CronTrigger(task.getCron()));
        if(future != null)
            taskMap.put(task, future);
    }

    public void stopTask(MyTask task){
        if(!isExist(task)){
            return ;
        }
        ScheduledFuture<?> future = taskMap.get(task);
        future.cancel(true);
        taskMap.remove(task);
    }

    @Scheduled(cron="0 5 0 * * *")
    public void checkWithDataBase(){
        List<ScheduleRequest> requests = scheduleRequestMapper.getALlScheduleRequest();
        Set<MyTask> tasks = new HashSet<>();
        requests.forEach((request) -> {
            tasks.add(new MyTask(request.getRequest(), request.getCron()));
        });
        tasks.forEach(this::addTask);
        List<MyTask> stopList = new ArrayList<>();
        taskMap.forEach((key, value) -> {
           if(!tasks.contains(key)){
               stopList.add(key);
           }
        });
        stopList.forEach(this::stopTask);
    }
}
