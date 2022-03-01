package com.sharkey.rpc.mapper;

import com.sharkey.rpc.entity.ScheduleRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ScheduleRequestMapper {

    @Select("Select * from schedule_request where id=#{id}")
    @Results(id="ScheduleRequestMap", value={
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cron", property = "cron"),
            @Result(column = "rid", property = "request",
                one = @One(
                        select = "com.sharkey.rpc.mapper.RequestMapper.getRequestById",
                        fetchType = FetchType.EAGER
                ))
    })
    ScheduleRequest getScheduleRequestById(@Param("id") int id);


    @Select("select * from schedule_request")
    @ResultMap(value={"ScheduleRequestMap"})
    List<ScheduleRequest> getALlScheduleRequest();
}
