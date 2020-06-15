package lab.web.converter;

import lab10.lab.core.domain.Rental;
import lab.web.dto.RentalDto;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto>{
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .rentalSerialNumber(dto.getRentalSerialNumber())
                .movieID(dto.getMovieID())
                .clientID(dto.getClientID())
                .returned(dto.isReturned())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .rentalSerialNumber(rental.getRentalSerialNumber())
                .movieID(rental.getMovieID())
                .clientID(rental.getClientID())
                .returned(rental.isReturned())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}