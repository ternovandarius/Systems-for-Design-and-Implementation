package lab11.web.converter;

import lab11.lab.core.domain.BaseEntity;
import lab11.web.dto.BaseDto;


public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
