package com.qbk.config;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: quboka
 * @Date: 2018/10/29 00:07
 * @Description: 实体类转换接口
 */
@Mapper
public interface ToVoMapper {
    ToVoMapper MAPPER = Mappers.getMapper(ToVoMapper.class);

//    @Mapping(source = "name", target = "username")
//    TestVo testToTestVo(TestInfo testInfo);
}
