package lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab10.lab.core.domain.Rental;
import lab10.lab.core.service.RentalService;
import lab.web.converter.RentalConverter;
import lab.web.dto.RentalDto;
import lab.web.dto.RentalsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class RentalController{
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    RentalsDto getAllRentals() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getAllRentals - method started");
        return new RentalsDto(rentalConverter.convertModelsToDtos(rentalService.getAllRentals()));
    }


    @RequestMapping(value = "/rentals", method = RequestMethod.PUT)
    void addRental(@RequestBody RentalDto rentaldto) throws Exception {
        log.trace("addRental - method started");
        rentalService.addRental(rentalConverter.convertDtoToModel(rentaldto));
    }

    @RequestMapping(value= "/rentals/{id}", method = RequestMethod.PUT)
    void updateRental(@PathVariable Long id, @RequestBody RentalDto rentaldto) throws Exception {
        log.trace("updateRental - method started");

        rentalService.updateRental(rentalConverter.convertDtoToModel(rentaldto));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id) throws Exception {
        log.trace("deleteRental - method started");

        rentalService.deleteRental(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{id}", method=RequestMethod.GET)
    RentalDto getRental(@PathVariable Long id)
    {
        log.trace("getRental - method started");
        Rental m = rentalService.findOne(id).get();
        return rentalConverter.convertModelToDto(m);

    }
}