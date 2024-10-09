package application.mapper;

import application.dto.DutyResponseChildrenDto;
import application.dto.DutyResponseControllerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DutyControllerMapper
        extends BaseMapper<DutyResponseChildrenDto, DutyResponseControllerDto> {

@Override
    DutyResponseControllerDto convertEntityToDto(DutyResponseChildrenDto dutyService);

    @Override
    List<DutyResponseControllerDto> convertEntityToDto(List<DutyResponseChildrenDto> dutyServiceList);

    @Override
    List<DutyResponseChildrenDto> convertDtoToEntity(List<DutyResponseControllerDto> dtoList);

    @Override
    DutyResponseChildrenDto convertDtoToEntity(DutyResponseControllerDto dto);
}
