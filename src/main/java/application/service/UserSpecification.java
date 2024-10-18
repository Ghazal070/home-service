package application.service;

import application.dto.SearchDto;
import application.entity.users.Users;

import java.util.List;

public interface UserSpecification {
    void validate(SearchDto searchDto);
    List<Users> findAllBySearchDto(SearchDto searchDto);
}
