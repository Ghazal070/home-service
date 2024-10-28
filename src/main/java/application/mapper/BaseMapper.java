package application.mapper;


import application.dto.UserOrderCountReportDto;
import application.dto.projection.UserOrderCount;
import application.entity.users.Users;

import java.util.List;
import java.util.Optional;

public interface BaseMapper<E,D> {

    E convertDtoToEntity (D d);
    D convertEntityToDto(E e);
    List<E> convertDtoToEntity(List<D> d);
    List<D> convertEntityToDto(List<E> e);
}
