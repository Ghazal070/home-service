package application.config.captcha;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.BackgroundProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CaptchaGenerator {

    private final BackgroundProducer backgroundProducer;
    private final WordRenderer wordRenderer = new DefaultWordRenderer(
            Collections.singletonList(Color.WHITE),
            Collections.singletonList(new Font(Font.DIALOG, Font.PLAIN, 36))
    );

    public Captcha createCaptcha(int width, int height, CaptchaTextProducer textProducer) {
        return new Captcha.Builder(width, height)
                .addBackground(backgroundProducer)
                .addText(textProducer, wordRenderer)
                .build();
    }

}
