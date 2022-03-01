package com.sharkey.rpc.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;

@Getter
@Setter
public class Request {
    private int id;
    private Date time;
    private int uid;
    private String url;
    private String header;
    private String body;
    private RequestType type;
    public String toString(){
        return  String.format("id: %d, uid:%d, url:%s, header:%s, body:%s", id, uid, url, header, body);
    }
}
