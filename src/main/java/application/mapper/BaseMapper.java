package application.mapper;


import java.util.List;

public interface BaseMapper<E,D> {

    E convertDtoToEntity (D d);
    D convertEntityToDto(E e);
    List<E> convertDtoToEntity(List<D> d);
    List<D> convertEntityToDto(List<E> e);
}
