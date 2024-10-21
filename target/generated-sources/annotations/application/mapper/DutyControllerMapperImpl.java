package application.mapper;

import application.dto.DutyResponseChildrenDto;
import application.dto.DutyResponseControllerDto;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T14:22:10+0330",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class DutyControllerMapperImpl implements DutyControllerMapper {

    @Override
    public DutyResponseControllerDto convertEntityToDto(DutyResponseChildrenDto dutyService) {
        if ( dutyService == null ) {
            return null;
        }

        DutyResponseControllerDto.DutyResponseControllerDtoBuilder dutyResponseControllerDto = DutyResponseControllerDto.builder();

        dutyResponseControllerDto.title( dutyService.getTitle() );
        dutyResponseControllerDto.subDuty( dutyResponseChildrenDtoSetToDutyResponseControllerDtoSet( dutyService.getSubDuty() ) );

        return dutyResponseControllerDto.build();
    }

    @Override
    public List<DutyResponseControllerDto> convertEntityToDto(List<DutyResponseChildrenDto> dutyServiceList) {
        if ( dutyServiceList == null ) {
            return null;
        }

        List<DutyResponseControllerDto> list = new ArrayList<DutyResponseControllerDto>( dutyServiceList.size() );
        for ( DutyResponseChildrenDto dutyResponseChildrenDto : dutyServiceList ) {
            list.add( convertEntityToDto( dutyResponseChildrenDto ) );
        }

        return list;
    }

    @Override
    public List<DutyResponseChildrenDto> convertDtoToEntity(List<DutyResponseControllerDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DutyResponseChildrenDto> list = new ArrayList<DutyResponseChildrenDto>( dtoList.size() );
        for ( DutyResponseControllerDto dutyResponseControllerDto : dtoList ) {
            list.add( convertDtoToEntity( dutyResponseControllerDto ) );
        }

        return list;
    }

    @Override
    public DutyResponseChildrenDto convertDtoToEntity(DutyResponseControllerDto dto) {
        if ( dto == null ) {
            return null;
        }

        DutyResponseChildrenDto.DutyResponseChildrenDtoBuilder dutyResponseChildrenDto = DutyResponseChildrenDto.builder();

        dutyResponseChildrenDto.title( dto.getTitle() );
        dutyResponseChildrenDto.subDuty( dutyResponseControllerDtoSetToDutyResponseChildrenDtoSet( dto.getSubDuty() ) );

        return dutyResponseChildrenDto.build();
    }

    protected Set<DutyResponseControllerDto> dutyResponseChildrenDtoSetToDutyResponseControllerDtoSet(Set<DutyResponseChildrenDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<DutyResponseControllerDto> set1 = new LinkedHashSet<DutyResponseControllerDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DutyResponseChildrenDto dutyResponseChildrenDto : set ) {
            set1.add( convertEntityToDto( dutyResponseChildrenDto ) );
        }

        return set1;
    }

    protected Set<DutyResponseChildrenDto> dutyResponseControllerDtoSetToDutyResponseChildrenDtoSet(Set<DutyResponseControllerDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<DutyResponseChildrenDto> set1 = new LinkedHashSet<DutyResponseChildrenDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DutyResponseControllerDto dutyResponseControllerDto : set ) {
            set1.add( convertDtoToEntity( dutyResponseControllerDto ) );
        }

        return set1;
    }
}
