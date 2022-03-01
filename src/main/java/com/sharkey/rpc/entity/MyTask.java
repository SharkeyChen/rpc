package com.sharkey.rpc.entity;

import com.sharkey.rpc.mapper.ResponseMapper;
import com.sharkey.rpc.util.OkHttpUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Slf4j
@Getter
public class MyTask implements Runnable{

    private String url;

    private int id;

    private int uid;

    private String body;

    private String[] headers;

    private RequestType type;

    private String cron;

    public MyTask(){}

//    @Autowired
//    private ResponseMapper responseMapper;

    public MyTask(Request request, String cron){
        this.url = request.getUrl();

        this.id = request.getId();

        this.uid = request.getUid();

        this.body = request.getBody();

        this.headers = request.getHeader().split("<=>");

        this.type = request.getType();

        this.cron = cron;
    }

    @Override
    public void run() {
        String ret = null;
        if(this.type == RequestType.GET)    ret = OkHttpUtil.doGet(this.url, this.headers, null);
        else if(this.type == RequestType.POST) ret = OkHttpUtil.doPost(this.url, this.headers, this.body);
//        Response response = new Response();
//        response.setRid(this.id);
//        response.setBody(ret);
//        response.setTime(new Date());
//
//        responseMapper.addResponse(response);
    }

    @Override
    public int hashCode(){
        return String.format("{%d, %d}", this.id, this.uid).hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof MyTask)){
            return false;
        }
        MyTask ob = (MyTask) o;
        return this.hashCode() == ob.hashCode();
    }
}
