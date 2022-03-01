package com.sharkey.rpc.mapper;

import com.sharkey.rpc.entity.Request;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;

import java.util.List;

@Mapper
public interface RequestMapper {

    @Select("select * from request")
    List<Request> getAllRequest();

    @Select("select id, uid, time, url, body, header, type from request where id = #{id}")
    @Results({
            @Result(column = "type", property = "type", typeHandler = EnumTypeHandler.class)
    })
    Request getRequestById(@Param("id") int id);

}
