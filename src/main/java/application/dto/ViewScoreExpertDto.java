package application.dto;

import application.entity.enumeration.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewScoreExpertDto implements Serializable {


    private Integer orderId;
    private Integer offerId;

    private Integer score;
}
