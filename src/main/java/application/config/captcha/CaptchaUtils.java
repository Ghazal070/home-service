package application.config.captcha;

import application.exception.ValidationControllerException;
import cn.apiclub.captcha.Captcha;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public abstract class CaptchaUtils {

    public static final String CAPTCHA = "Captcha";

    public static String encodeBase64(Captcha captcha) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "png", outputStream);
            return DatatypeConverter.printBase64Binary(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("error creating captcha: {}", e.getMessage());
        }
        return null;
    }

//    public static void checkCaptcha(@NotNull String captcha, HttpServletRequest request, boolean shouldRemoveCaptcha) {
//        String storedCaptcha = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null){
//            for (int i = 0; i < cookies.length; i++) {
//                if (cookies[i].getName().equals("captcha")){
//                    storedCaptcha=cookies[i].getValue();
//                }
//            }
//        }
//
//        if (storedCaptcha == null) {
//            throw new ValidationControllerException("there is no captcha in server", HttpStatus.PRECONDITION_REQUIRED);
//        }
//        if (!captcha.equals(storedCaptcha)) {
//            throw new ValidationControllerException("wrong captcha", HttpStatus.UNAUTHORIZED);
//        }
//    }

    public static void checkCaptcha(@NotNull String captcha, String captchaNotCookie, boolean shouldRemoveCaptcha) {
        String storedCaptcha = captchaNotCookie;

        if (storedCaptcha == null) {
            throw new ValidationControllerException("there is no captcha in server", HttpStatus.PRECONDITION_REQUIRED);
        }
        if (!captcha.equals(storedCaptcha)) {
            throw new ValidationControllerException("wrong captcha", HttpStatus.UNAUTHORIZED);
        }
    }
}
