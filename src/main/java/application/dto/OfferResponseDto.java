package application.dto;

import application.entity.Order;
import application.entity.users.Expert;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OfferResponseDto implements Serializable {

    private Integer orderId;

    private Integer expertId;

    private Integer priceOffer;

    private String dateTimeOffer;

    private String dateTimeStartWork;
    private Integer lengthDays;

}
