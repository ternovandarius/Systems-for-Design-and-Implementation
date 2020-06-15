package lab10.lab.core.service;

import lab10.lab.core.repository.DBMovieRepo;
import lab10.lab.core.domain.Movie;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private DBMovieRepo movieRepository;

    @Transactional
    public void addMovie(Movie movie) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a movie
        log.trace("addMovie - method entered: movie={}", movie);
        movieRepository.save(movie);
        log.trace("addMovie - method finished");
    }

    public Set<Movie> getAllMovies() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all movies
        log.trace("getAllMovies - method started");
        Iterable<Movie> movies = movieRepository.findAll();

        log.trace("getAllMovies - method to return");
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }

    public Movie filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receies a string
        //post:removes all movies that doens't have the given serial number
        log.trace("filterMoviesBySerial - method entered: s = " + s);
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies= new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getSerialNumber().equals(s));

        log.trace("filterMoviesBySerial - method to return");
        return filteredMovies.iterator().next();
    }

    public Optional<Movie> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        log.trace("findOne - method entered: id = "+ Long.toString(id));
        return this.movieRepository.findById(id);
    }

    public Set<Movie> filterMoviesByName(String s) throws IOException, ClassNotFoundException, SQLException {
        log.trace("filterMoviesByName - method entered: s = " + s);
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies = new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getName().contains(s));
        log.trace("filterMoviesByName - method to return");
        return filteredMovies;
    }

    @Transactional
    public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a movie
        log.trace("deleteMovie - method entered: id = " + Long.toString(id));
        Movie toDelete = movieRepository.getOne(id);
        movieRepository.delete(toDelete);
        log.trace("deleteMovie - method finished");
    }

    @Transactional
    public void updateMovie(Movie movie) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a movie
        log.trace("updateMovie - method entered: movie = {}", movie);
        movieRepository.findById(movie.getId()).ifPresent(s->{
            s.setId(movie.getId());
            s.setName(movie.getName());
            s.setRating(movie.getRating());
            s.setSerialNumber(movie.getSerialNumber());
            s.setYear(movie.getYear());
            log.debug("updateMovie - movie updated: m{} ", s);
        });
        log.trace("updateMovie - method finished");
    }

    public List<Movie> getAllMoviesSorted() throws Exception {
        //returns a set of all movies
        log.trace("getAllMoviesSorted - method started");

        Iterable<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC, "year"));
        log.debug("getAllMoviesSorted - method to return");
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toList());
    }

}