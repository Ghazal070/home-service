package application.serviceTest.SignupService;

import application.dto.UserSignupRequestDto;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import jakarta.validation.ValidationException;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.service.impl.SignupServiceImpl;
import application.util.AuthHolder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SignupServiceImplTest {

    @Mock
    private ExpertService expertService;

    @Mock
    private CustomerService customerService;

    @Mock
    private PasswordEncodeService passwordEncodeService;

    @Mock
    private AuthHolder authHolder;

    @Mock
    private ExpertFactory expertFactory;

    @Mock
    private CustomerFactory customerFactory;

    @Mock
    private Validator validator;

    @InjectMocks
    private SignupServiceImpl underTest;


    @BeforeEach
    void setUp() {
        this.underTest = new SignupServiceImpl(expertService, customerService, passwordEncodeService, authHolder, expertFactory, customerFactory, validator);
    }

    @Test
    public void testSignupCustomerSuccessfully() {
        UserSignupRequestDto userSignupRequestDto = null;
        try {
            userSignupRequestDto = UserSignupRequestDto.builder().email("customer@example.com")
                    .password("ghazal99")
                    .role("Customer")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Customer customer = Customer.builder().id(100).profile(Profile.builder()
                .email(userSignupRequestDto.getEmail())
                .password(userSignupRequestDto.getPassword())
                .build()).build();
        given(customerService.containByUniqField(userSignupRequestDto.getEmail())).willReturn(false);
        given(validator.validate(userSignupRequestDto)).willReturn(Set.of());
        given(passwordEncodeService.encode(userSignupRequestDto.getPassword())).willReturn("pass1234");
        given(customerFactory.createUser(any(UserSignupRequestDto.class))).willReturn(customer);
        given(customerService.save(any(Customer.class))).willReturn(customer);

        Users actual = underTest.signup(userSignupRequestDto);

        assertNotNull(actual);
        verify(customerService).containByUniqField(userSignupRequestDto.getEmail());
        verify(passwordEncodeService).encode(userSignupRequestDto.getPassword());
        verify(customerFactory).createUser(userSignupRequestDto);
        verify(customerService).save(any(Customer.class));
        assertEquals(userSignupRequestDto.getEmail(), actual.getProfile().getEmail());

    }

    @Test
    public void testSignupExpertSuccessfully() {
        //done email @ but i haveQuestion
        UserSignupRequestDto userSignupRequestDto = null;
        try {
            userSignupRequestDto = UserSignupRequestDto.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("Expert")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Expert expert = Expert.builder().id(100).profile(Profile.builder()
                .email(userSignupRequestDto.getEmail())
                .password(userSignupRequestDto.getPassword())
                .build()).build();
        given(expertService.containByUniqField(userSignupRequestDto.getEmail())).willReturn(false);
        given(validator.validate(userSignupRequestDto)).willReturn(Set.of());
        given(passwordEncodeService.encode(userSignupRequestDto.getPassword())).willReturn("pass1234");
        given(expertFactory.createUser(any(UserSignupRequestDto.class))).willReturn(expert);
        given(expertService.save(any(Expert.class))).willReturn(expert);

        Users actual = underTest.signup(userSignupRequestDto);

        assertNotNull(actual);
        assertEquals(userSignupRequestDto.getEmail(), actual.getProfile().getEmail());
        verify(expertService).containByUniqField(userSignupRequestDto.getEmail());
        verify(passwordEncodeService).encode(userSignupRequestDto.getPassword());
        verify(expertFactory).createUser(userSignupRequestDto);
        verify(expertService).save(any(Expert.class));
        verify(validator).validate(userSignupRequestDto);


    }

    @Test
    public void testSignupInvalidRoleRole() {
        UserSignupRequestDto userSignupRequestDto;
        try {
            userSignupRequestDto = UserSignupRequestDto.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("InvalidRole")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        given(validator.validate(userSignupRequestDto)).willReturn(Set.of());

        assertThatThrownBy(() -> underTest.signup(userSignupRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only Expert or Customer can sign up");

        verify(validator).validate(userSignupRequestDto);

    }

    @Test
    public void testSignupDuplicateEmail() {
        UserSignupRequestDto userSignupRequestDto;
        try {
            userSignupRequestDto = UserSignupRequestDto.builder().email("expert@example.com")
                    .password("ghazal99")
                    .role("Customer")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        given(validator.validate(userSignupRequestDto)).willReturn(Set.of());
        given(customerService.containByUniqField(userSignupRequestDto.getEmail())).willReturn(true);

        assertThatThrownBy(() -> underTest.signup(userSignupRequestDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email must be unique");
        verify(customerService).containByUniqField(userSignupRequestDto.getEmail());
        verify(validator).validate(userSignupRequestDto);


    }

    @Test
    public void testNotValidateEmail() {
        UserSignupRequestDto userSignupRequestDto;
        try {
            userSignupRequestDto = UserSignupRequestDto.builder().email("customerexample.com")
                    .password("ghaza99")
                    .role("Customer")
                    .password("pass1234")
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Set<ConstraintViolation<UserSignupRequestDto>> violations = new HashSet<>();
        ConstraintViolation<UserSignupRequestDto> violation = mock(ConstraintViolation.class);
        given(violation.getMessage()).willReturn("Please follow this pattern: gh@to.com");
        violations.add(violation);
        given(validator.validate(userSignupRequestDto)).willReturn(violations);

        assertThatThrownBy(() -> underTest.signup(userSignupRequestDto))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Please follow this pattern: gh@to.com");
    }
}