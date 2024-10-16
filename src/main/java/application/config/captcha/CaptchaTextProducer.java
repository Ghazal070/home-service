package application.config.captcha;

import cn.apiclub.captcha.text.producer.TextProducer;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

public class CaptchaTextProducer implements TextProducer {

    private final String text;
    @Getter
    private String answer;

    public CaptchaTextProducer() {
        this.answer = RandomStringUtils.randomNumeric(5);
        while (this.answer.contains("0")) {
            this.answer = RandomStringUtils.randomNumeric(5);
        }
        this.text = answer;
    }

    @Override
    public String getText() {
        return text;
    }


}
