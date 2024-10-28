package application.mapper;


import application.dto.UserOrderCountReportDto;
import application.dto.projection.UserOrderCount;
import application.entity.Role;
import application.entity.users.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserOrderReportMapper {

    @Mappings({
            @Mapping(target = "userId", source = "users.id"),
            @Mapping(target = "role",  expression = "java(getFirstRoleName(users.getRoles()))"),
            @Mapping(target = "email",  source = "users.profile.email"),
            @Mapping(target = "registerDate", source = "users.dateTimeSubmission"),
            @Mapping(target = "totalRequests", expression = "java(orderCount.map(UserOrderCount::getTotalRequests).orElse(0L))"),
            @Mapping(target = "doneOrders", expression = "java(orderCount.map(UserOrderCount::getDoneOrders).orElse(0L))")
    }
    )
    UserOrderCountReportDto convertEntityToDto(Users users, Optional<UserOrderCount> orderCount);

//    @Mappings({
//            @Mapping(target = "userId", source = "users.id"),
//            @Mapping(target = "role",  expression = "java(getFirstRoleName(users.getRoles()))"),
//            @Mapping(target = "email",  source = "users.profile.email"),
//            @Mapping(target = "registerDate", source = "users.dateTimeSubmission"),
//            @Mapping(target = "totalRequests", expression = "java(orderCount.map(UserOrderCount::getTotalRequests).orElse(0L))"),
//            @Mapping(target = "doneOrders", expression = "java(orderCount.map(UserOrderCount::getDoneOrders).orElse(0L))")
//    }
//    )
//    List<UserOrderCountReportDto> convertEntityToDto(List<Users> users, UserOrderCount orderCount);

    default String getFirstRoleName(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .findFirst()
                .orElse(null);
    }

}
