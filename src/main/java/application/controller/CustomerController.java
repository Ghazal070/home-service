package application.controller;

import application.config.captcha.CaptchaGenerator;
import application.config.captcha.CaptchaTextProducer;
import application.config.captcha.CaptchaUtils;
import application.dto.*;
import application.entity.Invoice;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.PaymentType;
import application.exception.ValidationControllerException;
import application.mapper.OfferMapper;
import application.mapper.OrderMapper;
import application.service.CustomerService;
import cn.apiclub.captcha.Captcha;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CaptchaGenerator captchaGenerator;
    private final OrderMapper orderMapper;
    private final OfferMapper offerMapper;


    @PostMapping("/{customerId}/orders")
    public ResponseEntity<OrderResponseDto> orderSubmit(@RequestBody @Valid OrderSubmissionDto orderSubmissionDto, @PathVariable Integer customerId) {
        try {
            customerService.isCustomerAuthenticated(customerId);
            Order order = customerService.orderSubmit(orderSubmissionDto, customerId);
            OrderResponseDto orderResponseDto = orderMapper.convertEntityToDto(order);
            return ResponseEntity.ok(orderResponseDto);
        } catch (ValidationException e) {
            throw new ValidationControllerException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate/{customerId}")
    public ResponseEntity<String> isCustomerAuthenticated(@PathVariable Integer customerId) {
        try {
            customerService.isCustomerAuthenticated(customerId);
            return ResponseEntity.ok("Customer is Authenticate");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orders/offers")
    public ResponseEntity<Set<OfferResponseDto> > getOffersForOrder(@RequestParam Integer orderId, @RequestParam Integer customerId) {
        try {
            Set<Offer> offers = customerService.getOffersForOrder(orderId, customerId);
            Set<OfferResponseDto> offerResponse = offerMapper.convertEntityToDto(offers);
            return ResponseEntity.ok(offerResponse);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/orders/offers/{offerId}/experts")
    public ResponseEntity<String> chooseExpertForOrder(@PathVariable Integer offerId) {
        try {
            customerService.chooseExpertForOrder(offerId);
            return ResponseEntity.ok("Status change to ComingToLocationWanting");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/orders/status/started/offers/{offerId}")
    public ResponseEntity<String> orderStarted(@PathVariable Integer offerId) {
        try {
            customerService.orderStarted(offerId);
            return ResponseEntity.ok("Status change to orderStarted");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/orders/status/done/offers")
    public ResponseEntity<String> orderDone(@RequestBody @Valid CustomerCommentDto customerCommentDto) {
        try {
            customerService.orderDone(customerCommentDto.getOfferId()
                    ,customerCommentDto.getContent(),customerCommentDto.getScore());
            return ResponseEntity.ok("Status change to orderDone");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paymentType/{paymentType}")

    public void selectPaymentType(@PathVariable String paymentType,HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!(paymentType.equals(String.valueOf(PaymentType.AccountPayment)) || paymentType.equals(String.valueOf(PaymentType.CreditPayment)))) {
                throw new ValidationControllerException(
                        "Payment type not equals account payment or credit payment", HttpStatus.NOT_ACCEPTABLE);
            }
            createCookie(paymentType, response, request);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private static void createCookie(String paymentType, HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("payment-type")) {
                    cookies[i].setValue(paymentType);
                }
            }
        }
        Cookie cookie = new Cookie("payment-type", paymentType);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
    }

    @GetMapping("/captcha")
    public String getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        CaptchaTextProducer textProducer = new CaptchaTextProducer();
        Captcha captcha = captchaGenerator.createCaptcha(100, 50, textProducer);
        HttpSession session = request.getSession(true);
        session.setAttribute(CaptchaUtils.CAPTCHA, textProducer.getAnswer());
        String captchaString = CaptchaUtils.encodeBase64(captcha);
        return captchaString;
    }


    @PostMapping("/payment/credit")
    public ModelAndView paymentByCredit(
            @CookieValue(value = "payment-type", defaultValue = "CreditPayment") String paymentType
            , @RequestParam Integer offerId, @RequestParam Integer customerId) {
        ModelAndView view = new ModelAndView("invoice");
        try {
            if (!paymentType.equals(String.valueOf(PaymentType.CreditPayment))) {
                throw new ValidationControllerException(
                        "Payment type not equal to credit payment", HttpStatus.PRECONDITION_FAILED);
            }
            Invoice invoice = customerService.creditPayment(offerId, customerId, paymentType);
            view.addObject("invoice", invoice);
            return view;
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }

    }


    @GetMapping("/payment/account")
    public ModelAndView paymentByAccount(
            @CookieValue(value = "payment-type", defaultValue = "AccountPayment") String paymentType,
            @RequestParam Integer offerId, @RequestParam Integer customerId, HttpServletResponse response
            , HttpServletRequest request) {
        try {
            ModelAndView view = new ModelAndView("account-payment");
            String captcha = getCaptcha(request, response);
            CardDto cardDto = CardDto.builder().build();
            view.addObject("imageCaptchaSrc", captcha);
            view.addObject("offerId", offerId);
            view.addObject("customerId", customerId);
            view.addObject("cardDto", cardDto);
            if (!paymentType.equals(String.valueOf(PaymentType.AccountPayment))) {
                throw new ValidationControllerException(
                        "Payment type not equal to account payment", HttpStatus.PRECONDITION_FAILED);
            }
            return view;
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }

    }

    @PostMapping("/payment/account")
    public ModelAndView paymentByAccount(
            @CookieValue(value = "payment-type", defaultValue = "AccountPayment") String paymentType
            , @RequestParam Integer offerId, @RequestParam Integer customerId
            , @ModelAttribute CardDto cardDto, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("invoice");
        try {
            if (!paymentType.equals(String.valueOf(PaymentType.AccountPayment))) {
                throw new ValidationControllerException(
                        "Payment type not equal to account payment", HttpStatus.PRECONDITION_FAILED);
            }
            HttpSession session = request.getSession(false);
            CaptchaUtils.checkCaptcha(cardDto.getCaptcha(), session, false);
            Invoice invoice = customerService.accountPayment(offerId, customerId, paymentType, cardDto);
            view.addObject("invoice", invoice);
            view.addObject("offerId", offerId);
            view.addObject("customerId", customerId);
            return view;
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }

}
