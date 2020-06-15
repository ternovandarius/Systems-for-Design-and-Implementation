package lab11.web.controller;

import lab11.lab.core.domain.Movie;
import lab11.lab.core.service.RentalService;
import lab11.web.dto.MovieDto;
import lab11.lab.core.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab11.web.converter.MovieConverter;
import lab11.web.dto.MoviesDto;
import lab11.lab.core.service.MovieService;
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
public class MovieController{
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private RentalService rentalService;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    Set<MovieDto> getAllMovies() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getAllMovies - method started");
        return movieConverter.convertModelsToDtos(movieService.getAllMovies());
    }

    @RequestMapping(value = "/movies/f/{filter}", method= RequestMethod.GET)
    MoviesDto filterMovies(@PathVariable String filter) throws SQLException, IOException, ClassNotFoundException {
        log.trace("filterMovies - method started");

        return new MoviesDto(movieConverter.convertModelsToDtos(movieService.filterMoviesByName(filter)));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.PUT)
    void addMovie(@RequestBody MovieDto moviedto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("addMovie - method started");
        movieService.addMovie(movieConverter.convertDtoToModel(moviedto));
    }

    @RequestMapping(value= "/movies/{id}", method = RequestMethod.PUT)
    void updateMovie(@PathVariable Long id, @RequestBody MovieDto moviedto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateMovie - method started");

        movieService.updateMovie(movieConverter.convertDtoToModel(moviedto));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteMovie - method started");

        rentalService.deleteMovie(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method=RequestMethod.GET)
    MovieDto getMovie(@PathVariable Long id) throws SQLException, IOException, ClassNotFoundException {
        log.trace("getMovie - method started");
        Movie m = movieService.findOne(id).get();
        return movieConverter.convertModelToDto(m);

    }
}