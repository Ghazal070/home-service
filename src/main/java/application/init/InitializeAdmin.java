package application.init;

import application.constants.RoleNames;
import application.entity.users.Admin;
import application.entity.users.Profile;
import application.repository.AdminRepository;
import application.service.PasswordEncoder;
import application.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.boot.archive.scan.spi.AbstractScannerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@ConditionalOnBean(RoleService.class)
public class InitializeAdmin {

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            String rawPassword = "admin123";
            Admin admin = Admin.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .profile(
                            Profile.builder()
                                    .email("admin@admin.com")
                                    .password(passwordEncoder.encode(rawPassword))
                                    .build()
                    )
                    .dateTimeSubmission(LocalDateTime.now())
                    .image(new Byte[]{100, 120})
                    .enabled(true)
                    .roles(Set.of(
                            roleService.findByName(
                                            RoleNames.ADMIN)
                                    .get()))
                    .build();
            repository.save(admin);
        }
    }
}
