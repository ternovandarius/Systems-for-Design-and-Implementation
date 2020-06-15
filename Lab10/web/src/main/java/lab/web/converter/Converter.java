package lab.web.converter;

import lab10.lab.core.domain.BaseEntity;
import lab.web.dto.BaseDto;


public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
