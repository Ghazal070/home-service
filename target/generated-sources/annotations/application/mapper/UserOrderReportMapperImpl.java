package application.mapper;

import application.dto.UserOrderCountReportDto;
import application.dto.projection.UserOrderCount;
import application.entity.users.Profile;
import application.entity.users.Users;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-21T09:30:15+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class UserOrderReportMapperImpl implements UserOrderReportMapper {

    @Override
    public UserOrderCountReportDto convertEntityToDto(Users users, Optional<UserOrderCount> orderCount) {
        if ( users == null && orderCount == null ) {
            return null;
        }

        UserOrderCountReportDto.UserOrderCountReportDtoBuilder userOrderCountReportDto = UserOrderCountReportDto.builder();

        if ( users != null ) {
            userOrderCountReportDto.userId( users.getId() );
            userOrderCountReportDto.email( usersProfileEmail( users ) );
            userOrderCountReportDto.registerDate( users.getDateTimeSubmission() );
        }
        userOrderCountReportDto.role( getFirstRoleName(users.getRoles()) );
        userOrderCountReportDto.totalRequests( orderCount.map(UserOrderCount::getTotalRequests).orElse(0L) );
        userOrderCountReportDto.doneOrders( orderCount.map(UserOrderCount::getDoneOrders).orElse(0L) );

        return userOrderCountReportDto.build();
    }

    private String usersProfileEmail(Users users) {
        Profile profile = users.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getEmail();
    }
}
