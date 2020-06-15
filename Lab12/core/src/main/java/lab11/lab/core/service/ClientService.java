package lab11.lab.core.service;


import lab11.lab.core.repository.DBClientRepo;
import lab11.lab.core.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private DBClientRepo clientRepository;

    @Transactional
    public void addClient(Client client) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a client
        log.trace("addClient - method entered: client={}", client);
        clientRepository.save(client);
        log.trace("addClient - method finished");
    }


    public Set<Client> getAllClients() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all clients
        log.trace("getAllClients - method started");
        Iterable<Client> clients = clientRepository.findAll();
        log.trace("getAllClients - method to return");
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    public Client filterClientsBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receives a string
        //post:deletes all clients that don't have the given serial number
        log.trace("filterClientsBySerial - method entered: string= " + s);
        Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients= new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getSerialNumber().equals(s));

        log.trace("filterClientsBySerial - method to return");
        return filteredClients.iterator().next();
    }

    public Optional<Client> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        log.trace("findOne - method entered: id = " + Long.toString(id));
        return this.clientRepository.findById(id);
    }

    public Set<Client> filterClientsByName(String s) throws IOException, ClassNotFoundException, SQLException {
        log.trace("filterClientsByName - method entered: s = " + s);

        Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients = new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getName().contains(s));

        log.trace("filterClientsByName - method to return");
        return filteredClients;
    }

    @Transactional
    public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a client
        log.trace("deleteClient - method entered: id = " + Long.toString(id));
        Client toDelete = clientRepository.getOne(id);
        clientRepository.delete(toDelete);
        log.trace("deleteClient - method finished");
    }

    @Transactional
    public void updateClient(Client client) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a client
        log.trace("updateClient - method entered: client={}", client);
        clientRepository.findById(client.getId()).ifPresent(s->{ s.setName(client.getName()); s.setAge(client.getAge()); s.setSerialNumber(client.getSerialNumber()); s.setId(client.getId()); log.debug("updateClient - updated: s={}", s);});
        log.trace("updateClient - method finished");
    }

    public List<Client> getAllClientsSorted() throws Exception {
        //returns a set of all movies
        log.trace("getAllClientsSorted - method started");

        Iterable<Client> clients = clientRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        log.debug("getAllClientsSorted - method to return");
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toList());
    }
}