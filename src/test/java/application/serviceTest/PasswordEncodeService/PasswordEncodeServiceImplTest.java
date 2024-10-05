package application.serviceTest.PasswordEncodeService;

import application.service.impl.PasswordEncodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PasswordEncodeServiceImplTest {

    @Autowired
    private PasswordEncodeServiceImpl underTest;

    @Test
    public void testEncode() {
        String password = "ghazal";

        String actual = underTest.encode(password);

        assertNotNull(actual);
        assertEquals(password,actual);
    }

    @Test
    public void testIsEqualEncodeDecodePass() {
        String password = "ghazal";
        String encodePassword = underTest.encode(password);

        Boolean actual = underTest.isEqualEncodeDecodePass(password, encodePassword);

        assertTrue(actual);
    }
    @Test
    public void testIsEqualEncodeDecodePass_whenWrongPassword() {
        String password = "ghazal";
        String encodedPassword = underTest.encode(password);

        Boolean actual = underTest.isEqualEncodeDecodePass("wrongpassword", encodedPassword);

        assertFalse(actual);
    }
}
