package application.service;

import application.dto.ResponseSearchOrderDto;
import application.dto.SearchDto;
import application.dto.SearchOrderDto;
import application.entity.Order;
import application.entity.users.Users;

import java.util.List;

public interface OrderSpecification {

    List<ResponseSearchOrderDto> findAllBySearchOrderDto(SearchOrderDto searchOrderDto);
}
