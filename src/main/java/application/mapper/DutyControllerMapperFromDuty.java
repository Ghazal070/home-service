package application.mapper;

import application.dto.DutyResponseChildrenDto;
import application.dto.DutyResponseControllerDto;
import application.entity.Duty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DutyControllerMapperFromDuty
        extends BaseMapper<Duty, DutyResponseControllerDto> {

    @Override
    Duty convertDtoToEntity(DutyResponseControllerDto dutyResponseChildrenDto);

    @Override
    DutyResponseControllerDto convertEntityToDto(Duty duty);

    @Override
    List<Duty> convertDtoToEntity(List<DutyResponseControllerDto> d);

    @Override
    List<DutyResponseControllerDto> convertEntityToDto(List<Duty> e);
}
