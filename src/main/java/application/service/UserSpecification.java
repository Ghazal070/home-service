package application.service;

import application.dto.SearchDto;
import application.entity.users.Users;

import java.util.List;

public interface UserSpecification<T extends Users> {
    void validate(SearchDto searchDto);
    List findAllBySearchDto(SearchDto searchDto);
}
