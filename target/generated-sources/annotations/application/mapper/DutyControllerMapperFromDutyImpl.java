package application.mapper;

import application.dto.DutyResponseControllerDto;
import application.entity.Duty;
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
public class DutyControllerMapperFromDutyImpl implements DutyControllerMapperFromDuty {

    @Override
    public Duty convertDtoToEntity(DutyResponseControllerDto dutyResponseChildrenDto) {
        if ( dutyResponseChildrenDto == null ) {
            return null;
        }

        Duty.DutyBuilder<?, ?> duty = Duty.builder();

        duty.title( dutyResponseChildrenDto.getTitle() );
        duty.subDuty( dutyResponseControllerDtoSetToDutySet( dutyResponseChildrenDto.getSubDuty() ) );

        return duty.build();
    }

    @Override
    public DutyResponseControllerDto convertEntityToDto(Duty duty) {
        if ( duty == null ) {
            return null;
        }

        DutyResponseControllerDto.DutyResponseControllerDtoBuilder dutyResponseControllerDto = DutyResponseControllerDto.builder();

        dutyResponseControllerDto.title( duty.getTitle() );
        dutyResponseControllerDto.subDuty( dutySetToDutyResponseControllerDtoSet( duty.getSubDuty() ) );

        return dutyResponseControllerDto.build();
    }

    @Override
    public List<Duty> convertDtoToEntity(List<DutyResponseControllerDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Duty> list = new ArrayList<Duty>( d.size() );
        for ( DutyResponseControllerDto dutyResponseControllerDto : d ) {
            list.add( convertDtoToEntity( dutyResponseControllerDto ) );
        }

        return list;
    }

    @Override
    public List<DutyResponseControllerDto> convertEntityToDto(List<Duty> e) {
        if ( e == null ) {
            return null;
        }

        List<DutyResponseControllerDto> list = new ArrayList<DutyResponseControllerDto>( e.size() );
        for ( Duty duty : e ) {
            list.add( convertEntityToDto( duty ) );
        }

        return list;
    }

    protected Set<Duty> dutyResponseControllerDtoSetToDutySet(Set<DutyResponseControllerDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Duty> set1 = new LinkedHashSet<Duty>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DutyResponseControllerDto dutyResponseControllerDto : set ) {
            set1.add( convertDtoToEntity( dutyResponseControllerDto ) );
        }

        return set1;
    }

    protected Set<DutyResponseControllerDto> dutySetToDutyResponseControllerDtoSet(Set<Duty> set) {
        if ( set == null ) {
            return null;
        }

        Set<DutyResponseControllerDto> set1 = new LinkedHashSet<DutyResponseControllerDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Duty duty : set ) {
            set1.add( convertEntityToDto( duty ) );
        }

        return set1;
    }
}
