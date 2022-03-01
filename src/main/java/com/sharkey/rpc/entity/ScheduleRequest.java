package com.sharkey.rpc.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequest {
    private int id;

    private String cron;

    private Request request;
}
