package application.annotation;


import application.entity.enumeration.OrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderStatusValidator implements ConstraintValidator<application.annotation.ValidOrderStatus, String> {

    @Override
    public void initialize(application.annotation.ValidOrderStatus constraintAnnotation) {

    }

    @Override
    public boolean isValid(String orderStatus, ConstraintValidatorContext context) {
        if (orderStatus == null) {
            return true;
        }

        try {
            OrderStatus status = OrderStatus.valueOf(orderStatus);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}