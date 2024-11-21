package application.init;

import application.annotation.SecurityRole;
import application.annotation.SequrityAuthority;
import application.constants.AuthorityNames;
import application.constants.RoleNames;
import org.springframework.stereotype.Component;

@Component
@SequrityAuthority(
        name = {
                AuthorityNames.ADMIN,
                AuthorityNames.Expert,
                AuthorityNames.CUSTOMER
        }
)
@SecurityRole(
        roles = {
                @SecurityRole.Role(
                        name = RoleNames.ADMIN,
                        authorities = {
                                @SequrityAuthority(
                                        name = {
                                                AuthorityNames.ADMIN
                                        }
                                )
                        }
                ),
                @SecurityRole.Role(
                        name = RoleNames.EXPERT,
                        authorities = {
                                @SequrityAuthority(
                                        name = {
                                                AuthorityNames.Expert
                                        }
                                )
                        }
                ),
                @SecurityRole.Role(
                        name = RoleNames.CUSTOMER,
                        authorities = {
                                @SequrityAuthority(
                                        name = {
                                                AuthorityNames.CUSTOMER
                                        }
                                )
                        }
                )
        }
)
public class RoleAutoDeployer {
}
