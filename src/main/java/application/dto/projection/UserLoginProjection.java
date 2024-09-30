package application.dto.projection;

import application.entity.users.Profile;

public interface UserLoginProjection {

    Integer getId();
    Profile getProfile();

}
