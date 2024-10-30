package application.mapper;

import application.dto.OfferResponseDto;
import application.entity.Offer;
import application.entity.Order;
import application.entity.users.Expert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-30T13:29:24+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class OfferMapperImpl implements OfferMapper {

    @Override
    public Offer convertDtoToEntity(OfferResponseDto d) {
        if ( d == null ) {
            return null;
        }

        Offer.OfferBuilder<?, ?> offer = Offer.builder();

        offer.id( d.getId() );
        offer.priceOffer( d.getPriceOffer() );
        if ( d.getDateTimeOffer() != null ) {
            offer.dateTimeOffer( LocalDateTime.parse( d.getDateTimeOffer() ) );
        }
        if ( d.getDateTimeStartWork() != null ) {
            offer.dateTimeStartWork( LocalDateTime.parse( d.getDateTimeStartWork() ) );
        }
        offer.lengthDays( d.getLengthDays() );

        return offer.build();
    }

    @Override
    public List<Offer> convertDtoToEntity(List<OfferResponseDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Offer> list = new ArrayList<Offer>( d.size() );
        for ( OfferResponseDto offerResponseDto : d ) {
            list.add( convertDtoToEntity( offerResponseDto ) );
        }

        return list;
    }

    @Override
    public List<OfferResponseDto> convertEntityToDto(List<Offer> e) {
        if ( e == null ) {
            return null;
        }

        List<OfferResponseDto> list = new ArrayList<OfferResponseDto>( e.size() );
        for ( Offer offer : e ) {
            list.add( convertEntityToDto( offer ) );
        }

        return list;
    }

    @Override
    public OfferResponseDto convertEntityToDto(Offer offer) {
        if ( offer == null ) {
            return null;
        }

        OfferResponseDto.OfferResponseDtoBuilder offerResponseDto = OfferResponseDto.builder();

        offerResponseDto.orderId( offerOrderId( offer ) );
        offerResponseDto.expertId( offerExpertId( offer ) );
        offerResponseDto.id( offer.getId() );
        offerResponseDto.priceOffer( offer.getPriceOffer() );
        if ( offer.getDateTimeOffer() != null ) {
            offerResponseDto.dateTimeOffer( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( offer.getDateTimeOffer() ) );
        }
        if ( offer.getDateTimeStartWork() != null ) {
            offerResponseDto.dateTimeStartWork( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( offer.getDateTimeStartWork() ) );
        }
        offerResponseDto.lengthDays( offer.getLengthDays() );

        return offerResponseDto.build();
    }

    @Override
    public Set<OfferResponseDto> convertEntityToDto(Set<Offer> e) {
        if ( e == null ) {
            return null;
        }

        Set<OfferResponseDto> set = new LinkedHashSet<OfferResponseDto>( Math.max( (int) ( e.size() / .75f ) + 1, 16 ) );
        for ( Offer offer : e ) {
            set.add( convertEntityToDto( offer ) );
        }

        return set;
    }

    private Integer offerOrderId(Offer offer) {
        Order order = offer.getOrder();
        if ( order == null ) {
            return null;
        }
        return order.getId();
    }

    private Integer offerExpertId(Offer offer) {
        Expert expert = offer.getExpert();
        if ( expert == null ) {
            return null;
        }
        return expert.getId();
    }
}
