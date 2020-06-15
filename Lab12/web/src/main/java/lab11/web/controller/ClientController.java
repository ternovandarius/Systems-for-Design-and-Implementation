package lab11.web.controller;

import lab11.lab.core.domain.Client;
import lab11.lab.core.service.RentalService;
import lab11.web.dto.ClientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab11.web.converter.ClientConverter;
import lab11.web.dto.ClientsDto;
import lab11.lab.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@CrossOrigin
@RestController
public class ClientController{
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @Autowired
    private RentalService rentalService;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    Set<ClientDto> getAllClients() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getAllClients - method started");
        return clientConverter.convertModelsToDtos(clientService.getAllClients());
    }

    @RequestMapping(value = "/clients/f/{filter}", method= RequestMethod.GET)
    ClientsDto filterClients(@PathVariable String filter) throws SQLException, IOException, ClassNotFoundException {
        log.trace("filterClients - method started");

        return new ClientsDto(clientConverter.convertModelsToDtos(clientService.filterClientsByName(filter)));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    void addClient(@RequestBody ClientDto clientdto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("addClient - method started");
        clientService.addClient(clientConverter.convertDtoToModel(clientdto));
    }

    @RequestMapping(value= "/clients/{id}", method = RequestMethod.PUT)
    void updateClient(@PathVariable Long id, @RequestBody ClientDto clientdto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateClient - method started");

        clientService.updateClient(clientConverter.convertDtoToModel(clientdto));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteClient - method started");

        rentalService.deleteClient(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method=RequestMethod.GET)
    ClientDto getClient(@PathVariable Long id) throws SQLException, IOException, ClassNotFoundException {
        log.trace("getClient - method started");
        Client m = clientService.findOne(id).get();
        return clientConverter.convertModelToDto(m);

    }
}