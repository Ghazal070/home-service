package application.mapper;

import application.dto.DutyByIdTitleDto;
import application.entity.Duty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DutyControllerMapperFromDutyById
        extends BaseMapper<Duty, DutyByIdTitleDto> {

    @Override
    Duty convertDtoToEntity(DutyByIdTitleDto DutyByIdDto);

    @Override
    DutyByIdTitleDto convertEntityToDto(Duty duty);

    @Override
    List<Duty> convertDtoToEntity(List<DutyByIdTitleDto> d);

    @Override
    List<DutyByIdTitleDto> convertEntityToDto(List<Duty> e);
}
