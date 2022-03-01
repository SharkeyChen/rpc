package com.sharkey.rpc.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Response {
    private int id;

    private int rid;

    private Date time;

    private String body;
}
