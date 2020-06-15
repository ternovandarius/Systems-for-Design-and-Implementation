package lab10.lab.core.service;


import lab10.lab.core.repository.DBRentalRepo;
import lab10.lab.core.domain.Client;
import lab10.lab.core.domain.Movie;
import lab10.lab.core.domain.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService {
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);

    @Autowired
    private DBRentalRepo rentalRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ClientService clientService;



    /**
     * adds a rental
     *
     * @param rental a rental
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void addRental(Rental rental) throws Exception {
        log.trace("addRental - method entered: rental= {} "+ rental);
        if (!movieService.findOne(rental.getMovieID()).isPresent()) {
            log.trace("addRental - exception encountered");
            throw new Exception("Invalid movie ID!");
        }
        if(!clientService.findOne(rental.getClientID()).isPresent()) {
            log.trace("addRental - exception encountered");
            throw new Exception("Invalid client ID!");
        }
        if(rental.getRentalSerialNumber().isEmpty() || rental.getClientID()<0 || rental.getMovieID()<0)
            throw new Exception("Rental did not pass validation!");
        rentalRepository.save(rental);
        log.trace("addRental - method finished");
    }

    /**
     * updates a rental
     *
     * @param rental a rental
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void updateRental(Rental rental) throws Exception {
        log.trace("updateRental - method entered: rental = {} " + rental);
        if (!movieService.findOne(rental.getMovieID()).isPresent()) {
            log.trace("updateRental - exception encountered");
            throw new Exception("Invalid movie ID!");
        }
        if(!clientService.findOne(rental.getClientID()).isPresent()) {
            log.trace("updateRental - exception encountered");
            throw new Exception("Invalid client ID!");
        }
        if(rental.getRentalSerialNumber().isEmpty() || rental.getClientID()<0 || rental.getMovieID()<0)
            throw new Exception("Rental did not pass validation!");
        rentalRepository.findById(rental.getId()).ifPresent(s->{
            s.setClientID(rental.getClientID());
            s.setMovieID(rental.getMovieID());
            s.setRentalSerialNumber(rental.getRentalSerialNumber());
            s.setReturned(rental.isReturned());
            log.debug("updateRental - updated s {} ", s);
        });
        log.trace("updateRental - method finished");
    }

    /**
     * deletes a rental
     *
     * @param id of a rental
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void deleteRental(long id) throws Exception {
        log.trace("deleteRental - method entered: id = " + Long.toString(id));
        if (!rentalRepository.findById(id).isPresent()) {
            log.trace("deleteRental - exception encountered");
            throw new Exception("Invalid rental ID!");
        }
        Rental toDelete = rentalRepository.getOne(id);
        rentalRepository.delete(toDelete);
        log.trace("deleteRental - method finished");
    }

    /**
     * deletes clients with given id from rentalRepository
     *  we don't have to validate again the given entity because it is validated when it is removed
     *  from its lab9.lab.lab.core.repository(cascade deleting is called in console after deleting the given client)
     *
     * @param id id of a rental
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //pre:give an id
        //post:if entities is not empty and entities contains Rental objects,remove clients with given id from rentals
        log.trace("deleteClient - method entered: id = " + Long.toString(id));
        this.clientService.deleteClient(id);
        List<Rental> entities= this.rentalRepository.findAll();
        if (!entities.isEmpty()){
            entities.removeIf(s->s.getClientID()==id);
        }
        this.rentalRepository.deleteAll();
        this.rentalRepository.saveAll(entities);
        log.trace("deleteClient - method finished");
    }


    /**
     * deletes movies with given id from rentalRepository
     *  we don't have to validate again the given entity because it is validated when it is removed
     *  from its lab9.lab.lab.core.repository(cascade deleting is called in console after deleting the given client)
     *
     * @param id id of a rental
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //pre:give an id
        //post:if entities is not empty and entities contains Rental objects,remove movies with given id from rentals
        log.trace("deleteMovie - method entered: id = " + Long.toString(id));
        this.movieService.deleteMovie(id);
        List<Rental> entities = this.rentalRepository.findAll();
        if (!entities.isEmpty()){
            entities.removeIf(s->s.getMovieID()==id);
        }
        this.rentalRepository.deleteAll();
        this.rentalRepository.saveAll(entities);
        log.trace("deleteMovie - method finished");
    }

    /**
     * returns a set of all rentals
     *
     * @return returns set of all rentals
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public Set<Rental> getAllRentals() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getAllRentals - method started");
        Iterable<Rental> rentals = rentalRepository.findAll();
        log.trace("getAllRentals - method to return");
        return StreamSupport.stream(rentals.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * pre:receives a string
     * post:updates the movies with given serial number
     *
     * @param s serial number
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    @Transactional
    public void returnMovie(String s) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("returnMovie - method entered: s = " + s);
        Iterable<Rental> rentals = rentalRepository.findAll();
        Set<Rental> filteredRentals= new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental -> !rental.getRentalSerialNumber().equals(s));
        Rental ren = filteredRentals.iterator().next();
        ren.setReturned(true);
        rentalRepository.findById(ren.getId()).ifPresent(r->{
            r.setReturned(true);
            log.debug("returnMovie - rental updated r{}", r);
        });
        log.trace("returnMovie - method finished");
    }

    /**
     *
     *
     * @return list of most rented movies
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public Movie getMostRentedMovie() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getMostRentedMovie - method started");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);

        List<Long> resultFinal=result.stream().map(s->s.getMovieID()).collect(Collectors.toList());

        long maxNumberofOcc = result.stream()
                .collect(Collectors.groupingBy(Rental::getMovieID, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(Long.valueOf(-1));

        resultFinal.removeIf(rental -> Collections.frequency(resultFinal, rental)!=(int)(maxNumberofOcc));
        List<Long> listWithoutDuplicates = resultFinal.stream().distinct().collect(Collectors.toList());

        Movie res = new Movie();
        res.setId(listWithoutDuplicates.get(0));

        log.trace("getMostRentedMovie - method to return");
        return res;
    }
    /**
     *
     * @return list of most active clients
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getMostActiveClient() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getMostActiveClient - method started");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);

        log.trace("getMostActiveClient - method to return");
        return getBestClientIDFromList(result);
    }

    /**
     *
     * @return list of clients with most returned movies
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getClientWithMostReturned() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getClientWithMostReturned - method started");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);
        result.removeIf(s->s.isReturned()==false);

        log.trace("getClientWithMostReturned - method to return");
        return getBestClientIDFromList(result);
    }

    /**
     *
     * @param result a list of rentals
     *
     * @return returns clientIDs with most appearances in given list;if list in empty we return an empty list
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getBestClientIDFromList(List<Rental> result) throws IOException, ClassNotFoundException {
        log.trace("getBestClientIDFromList - method entered: result = " + result.toString());
        List<Long> resultFinal = result.stream().map(s -> s.getClientID()).collect(Collectors.toList());

        long maxNumberofOcc = result.stream()
                .collect(Collectors.groupingBy(Rental::getClientID, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(Long.valueOf(-1));

        resultFinal.removeIf(rental -> Collections.frequency(resultFinal, rental) != (int) (maxNumberofOcc));
        List<Long> listWithoutDuplicates = resultFinal.stream().distinct().collect(Collectors.toList());

        log.trace("getBestClientIDFromList - method to return");
        return listWithoutDuplicates;
    }

    /**
     *
     * @return # of clients without movies - # of movies without clients=? (are there more clients without movies than movies without clients?)
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public int computeClientsMinusBooks() throws IOException, ClassNotFoundException, SQLException {
        log.trace("computeClientsMinusBooks - method started");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);
        List<Long> resultOfClients = result.stream().map(s -> s.getClientID()).collect(Collectors.toList());
        List<Long> resultOfMovies = result.stream().map(s -> s.getMovieID()).collect(Collectors.toList());


        Iterable<Client> clients = clientService.getAllClients();
        List<Client> listOfClients = new ArrayList<Client>();
        clients.forEach(listOfClients::add);
        List<Long> listOfClients1 = listOfClients.stream().map(s -> s.getId()).collect(Collectors.toList());

        Iterable<Movie> movies = movieService.getAllMovies();
        List<Movie> listOfMovies = new ArrayList<Movie>();
        movies.forEach(listOfMovies::add);
        List<Long> listOfMovies1 = listOfMovies.stream().map(s -> s.getId()).collect(Collectors.toList());

        listOfClients1.removeAll(resultOfClients);
        listOfMovies1.removeAll(resultOfMovies);

        log.trace("computeClientsMinusBooks - method to return");
        return listOfClients1.size()-listOfMovies1.size();
    }


    public Optional<Rental> findOne(Long id)
    {
        return rentalRepository.findById(id);
    }

}