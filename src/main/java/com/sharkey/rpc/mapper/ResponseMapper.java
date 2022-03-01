package com.sharkey.rpc.mapper;

import com.sharkey.rpc.entity.Response;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ResponseMapper {

    @Insert("Insert into response values (#{rid}, #{body}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addResponse(Response response);
}
