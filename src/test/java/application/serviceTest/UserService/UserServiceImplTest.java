package application.serviceTest.UserService;

import application.dto.UserChangePassword;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.exception.ValidationException;
import application.repository.UserRepository;
import application.service.PasswordEncodeService;
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
    private PasswordEncodeService passwordEncodeService;
    @Mock
    private UserRepository repository;

    private Validator validator;

    @InjectMocks
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserServiceImpl<>(validator, repository, authHolder, passwordEncodeService);
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
        String firstName = "testUser";

        assertDoesNotThrow(() -> underTest.convertByteToImage(imageDataWrapper, firstName));

        File createdFile = new File(tempDir + "/" + firstName + ".jpg");
        assertTrue(createdFile.exists(), "Image file should exist after conversion");

        BufferedImage savedImage = ImageIO.read(createdFile);
        assertNotNull(savedImage, "Saved image should not be null");
    }

    @Test
    void testConvertByteToImageWithNullData() {
        assertThatThrownBy(() -> underTest.convertByteToImage(null, "testUser"))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("image is null");
    }

    @Test
    void testConvertByteToImageWithEmptyData() {
        Byte[] emptyData = new Byte[0];
        assertThatThrownBy(() -> underTest.convertByteToImage(emptyData, "testUser"))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("image is null");
    }

    //todo png test
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

        UserLoginProjection actual = underTest.login(email, password);

        assertNotNull(actual);
        assertEquals(userLoginProjection, actual);
//        assertEquals(1, authHolder.getTokenId());
//        assertEquals(email, authHolder.getTokenName());
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
        UserChangePassword userChangePassword =UserChangePassword.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();
        Users users = Users.builder().id(1).build();
        given(authHolder.getTokenId()).willReturn(1);
        given(authHolder.getTokenName()).willReturn(email);
        given(repository.findById(1)).willReturn(Optional.of(users));
        given(passwordEncodeService.isEqualEncodeDecodePass(userChangePassword.getOldPassword()
                ,userChangePassword.getOldPassword())).willReturn(true);
        given(passwordEncodeService.encode(userChangePassword.getNewPassword())).willReturn(newPassword);
        given(repository.updatePassword(email,newPassword)).willReturn(1);

        Boolean actual = underTest.updatePassword(userChangePassword);

        assertTrue(actual);
        verify(repository).findById(1);
        verify(passwordEncodeService).encode(userChangePassword.getNewPassword());
        verify(passwordEncodeService).isEqualEncodeDecodePass(userChangePassword.getOldPassword()
                ,userChangePassword.getOldPassword());
        verify(authHolder).getTokenId();
        verify(authHolder).getTokenName();
        verify(repository).updatePassword(email,newPassword);
    }

    @Test
    void testUpdatePasswordNullUserChangePassword() {
        UserChangePassword userChangePassword = null;

        assertThatThrownBy(() -> underTest.updatePassword(userChangePassword))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("UserChangePassword is null");
    }

    @Test
    void testUpdatePasswordNullLoginUser() {
        String newPassword = "PASS5678";
        String oldPassword = "pass1234";
        UserChangePassword userChangePassword =UserChangePassword.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();
        given(authHolder.getTokenId()).willReturn(null);

        assertThatThrownBy(() -> underTest.updatePassword(userChangePassword))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("user from token is null");
    }

    @Test
    void testUpdatePasswordIsEqualEncodeDecodePassFalse() {
        String newPassword = "PASS5678";
        String oldPassword = "pass1234";
        UserChangePassword userChangePassword =UserChangePassword.builder().newPassword(newPassword)
                .oldPassword(oldPassword).build();
        Users users = Users.builder().id(1).build();
        given(authHolder.getTokenId()).willReturn(1);
        given(repository.findById(1)).willReturn(Optional.of(users));
        given(passwordEncodeService.isEqualEncodeDecodePass(userChangePassword.getOldPassword()
                ,userChangePassword.getOldPassword())).willReturn(false);


        assertThatThrownBy(() -> underTest.updatePassword(userChangePassword))
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