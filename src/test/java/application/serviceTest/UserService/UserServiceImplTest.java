package application.serviceTest.UserService;

import application.dto.UserChangePasswordDto;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.config.jwt.JwtService;
import jakarta.validation.ValidationException;
import application.repository.UserRepository;
import application.service.PasswordEncoder;
import application.service.impl.UserServiceImpl;
import application.util.AuthHolder;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final String tempDir = "src/main/resources/images";
    @Mock
    private AuthHolder authHolder;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository repository;

    private Validator validator;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserServiceImpl<>(validator, repository, authHolder, passwordEncoder, authenticationManager, jwtService);
        new File(tempDir).mkdirs();
    }


    @Test
    void testConvertByteToImageSuccessfully() throws IOException {
        Path imagePath = Path.of("src/test/resources/less300.jpg");
        byte[] imageData = Files.readAllBytes(imagePath);
        Byte[] imageDataWrapper = new Byte[imageData.length];
        for (int i = 0; i < imageData.length; i++) {
            imageDataWrapper[i] = imageData[i];
        }
        Expert expert = Expert.builder().id(100).firstName("testUser").image(imageDataWrapper).build();
        given(repository.findById(100)).willReturn(Optional.of(expert));

        assertDoesNotThrow(() -> underTest.convertByteToImage(expert.getId()));

        File createdFile = new File(tempDir + "/" + expert.getFirstName() + ".jpg");
        assertTrue(createdFile.exists());

        BufferedImage savedImage = ImageIO.read(createdFile);
        assertNotNull(savedImage, "Saved image should not be null");
    }


    @Test
    void testConvertByteToImageWithNullData() {
        Expert expert = Expert.builder().id(100).firstName("testUser").build();
        given(repository.findById(100)).willReturn(Optional.of(expert));

        assertThatThrownBy(() -> underTest.convertByteToImage(expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("image is null");
    }

    @Test
    void testConvertByteToImageWithEmptyData() {
        Byte[] emptyData = new Byte[0];
        Expert expert = Expert.builder().id(100).firstName("testUser").image(emptyData).build();
        given(repository.findById(100)).willReturn(Optional.of(expert));
        assertThatThrownBy(() -> underTest.convertByteToImage(expert.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("image is null");
    }

    @Test
    void testLoginSuccessfully() {
        String email = "user@example.com";
        String password = "password";
        Profile profile = mock(Profile.class);
        UserLoginProjection userLoginProjection = mock(UserLoginProjection.class);
        given(repository.login(email, password)).willReturn(Optional.of(userLoginProjection));
        given(userLoginProjection.getId()).willReturn(1);
        given(userLoginProjection.getProfile()).willReturn(profile);
        given(profile.getEmail()).willReturn(email);

        String actual = underTest.login(email, password);

        assertNotNull(actual);
        assertEquals(userLoginProjection, actual);
    }

    @Test
    void testLoginFailEmailIsEmpty() {
        String email = "";
        String password = "password";

        assertThatThrownBy(() -> underTest.login(email, password))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email or Password must not be empty");

    }

    @Test
    void testLoginNotFoundUser() {
        String email = "user@example.com";
        String password = "password";
        UserLoginProjection userLoginProjection = mock(UserLoginProjection.class);
        given(repository.login(email, password)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.login(email, password))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid login credentials");
    }

    @Test
    void testUpdatePasswordSuccessfully() {
        String email = "user@example.com";
        String newPassword = "PASS5678";
        String oldPassword = "pass1234";
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();
        Users users = Users.builder().id(1).profile(Profile.builder().email("user@example.com").build()).build();
        given(repository.findById(1)).willReturn(Optional.of(users));
        given(passwordEncoder.isEqualEncodeDecodePass(userChangePasswordDto.getOldPassword()
                , userChangePasswordDto.getOldPassword())).willReturn(true);
        given(passwordEncoder.encode(userChangePasswordDto.getNewPassword())).willReturn(newPassword);
        given(repository.updatePassword(email, newPassword)).willReturn(1);

        Boolean actual = underTest.updatePassword(userChangePasswordDto,users.getId());

        assertTrue(actual);
        verify(repository).findById(1);
        verify(passwordEncoder).encode(userChangePasswordDto.getNewPassword());
        verify(passwordEncoder).isEqualEncodeDecodePass(userChangePasswordDto.getOldPassword()
                , userChangePasswordDto.getOldPassword());
        verify(repository).updatePassword(email, newPassword);
    }

    @Test
    void testUpdatePasswordNullUserChangePassword() {
        Users users = Users.builder().id(1).build();
        UserChangePasswordDto userChangePasswordDto = null;

        assertThatThrownBy(() -> underTest.updatePassword(userChangePasswordDto,users.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("UserChangePassword is null");
    }

    @Test
    void testUpdatePasswordNullLoginUser() {
        String newPassword = "PASS5678";
        String oldPassword = "pass1234";
        Users users = Users.builder().id(1).build();
        given(repository.findById(1)).willReturn(Optional.empty());
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();

        assertThatThrownBy(() -> underTest.updatePassword(userChangePasswordDto,users.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("user from token is null");
    }

    @Test
    void testUpdatePasswordIsEqualEncodeDecodePassFalse() {
        String newPassword = "PASS5678";
        String oldPassword = "pass1234";
        Users users = Users.builder().id(1).build();
        given(repository.findById(1)).willReturn(Optional.of(users));
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();
        given(passwordEncoder.isEqualEncodeDecodePass(userChangePasswordDto.getOldPassword()
                , userChangePasswordDto.getOldPassword())).willReturn(false);


        assertThatThrownBy(() -> underTest.updatePassword(userChangePasswordDto,users.getId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("old password is not correct");
    }


    @Test
    void testContainByUniqField() {
        String email = "user@example.com";
        given(repository.containByUniqField(email)).willReturn(true);

        Boolean actual = underTest.containByUniqField(email);

        assertTrue(actual);
    }

    @Test
    void testNotContainByUniqField() {
        String email = "user@example.com";
        given(repository.containByUniqField(email)).willReturn(false);

        Boolean actual = underTest.containByUniqField(email);

        assertFalse(actual);
    }
}