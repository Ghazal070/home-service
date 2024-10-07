package application.mapper;

import application.dto.OfferResponseDto;
import application.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OfferMapper extends BaseMapper<Offer, OfferResponseDto> {

    @Mappings({
            @Mapping(target = "orderId", source = "Order.id"),
            @Mapping(target = "expertId", source = "Expert.id")
    }
    )
    OfferResponseDto convertEntityToDto(Offer offer);

    @Mappings({
            @Mapping(target = "orderId", source = "Order.id"),
            @Mapping(target = "expertId", source = "Expert.id")
    }
    )
    Set<OfferResponseDto> convertEntityToDto(Set<Offer> e);
}
