package com.rafee.residenthub.mapper;

import com.rafee.residenthub.dto.response.BroadcastResponse;
import com.rafee.residenthub.entity.Broadcast;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BroadcastMapper {

    BroadcastResponse toDTO(Broadcast broadcast);

    List<BroadcastResponse> toListDTO(List<Broadcast> broadcastList);

}
