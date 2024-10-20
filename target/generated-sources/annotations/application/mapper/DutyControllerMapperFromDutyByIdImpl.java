package application.mapper;

import application.dto.DutyByIdTitleDto;
import application.entity.Duty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-20T16:31:06+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class DutyControllerMapperFromDutyByIdImpl implements DutyControllerMapperFromDutyById {

    @Override
    public Duty convertDtoToEntity(DutyByIdTitleDto DutyByIdDto) {
        if ( DutyByIdDto == null ) {
            return null;
        }

        Duty.DutyBuilder<?, ?> duty = Duty.builder();

        duty.id( DutyByIdDto.id() );
        duty.title( DutyByIdDto.title() );

        return duty.build();
    }

    @Override
    public DutyByIdTitleDto convertEntityToDto(Duty duty) {
        if ( duty == null ) {
            return null;
        }

        DutyByIdTitleDto.DutyByIdTitleDtoBuilder dutyByIdTitleDto = DutyByIdTitleDto.builder();

        dutyByIdTitleDto.id( duty.getId() );
        dutyByIdTitleDto.title( duty.getTitle() );

        return dutyByIdTitleDto.build();
    }

    @Override
    public List<Duty> convertDtoToEntity(List<DutyByIdTitleDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Duty> list = new ArrayList<Duty>( d.size() );
        for ( DutyByIdTitleDto dutyByIdTitleDto : d ) {
            list.add( convertDtoToEntity( dutyByIdTitleDto ) );
        }

        return list;
    }

    @Override
    public List<DutyByIdTitleDto> convertEntityToDto(List<Duty> e) {
        if ( e == null ) {
            return null;
        }

        List<DutyByIdTitleDto> list = new ArrayList<DutyByIdTitleDto>( e.size() );
        for ( Duty duty : e ) {
            list.add( convertEntityToDto( duty ) );
        }

        return list;
    }
}
