package application.dto.projection;

import application.entity.users.Profile;

import java.time.LocalDateTime;

public interface OfferProjection {

    Integer getOrder();

    Integer getExpert();

    Integer getPriceOffer();

    LocalDateTime getDateTimeOffer();

    LocalDateTime getDateTimeStartWork();

    Integer getLengthDays();

}
