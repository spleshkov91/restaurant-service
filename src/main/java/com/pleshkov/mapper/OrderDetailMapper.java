package com.pleshkov.mapper;

import com.pleshkov.model.dto.OrderDetailDto;
import com.pleshkov.model.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailMapper {
    OrderDetail orderDetailDtoToOrderDetail(OrderDetailDto orderDetailDto);

    OrderDetailDto orderDetailToOrderDetailDto(OrderDetail orderDetail);
}
