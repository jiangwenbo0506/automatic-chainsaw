package com.blk.common.convert;

import com.blk.model.entity.MeasureDataHistory;
import com.blk.model.entity.MeasureDataLatest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MeasureDataLatestConvert {


    MeasureDataLatestConvert INSTANCE = Mappers.getMapper(MeasureDataLatestConvert.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    @Mapping(target = "updateTime", expression = "java(new java.util.Date())")
    MeasureDataHistory convert(MeasureDataLatest source);

    List<MeasureDataHistory> convertList(List<MeasureDataLatest> entityAList);
}
