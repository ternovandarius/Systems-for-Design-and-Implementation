package lab.client;

import lab10.lab.core.domain.Client;
import lab10.lab.core.domain.Movie;
import lab10.lab.core.domain.Rental;
import lab.web.converter.ClientConverter;
import lab.web.converter.MovieConverter;
import lab.web.converter.RentalConverter;
import lab.web.dto.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientApp {

    public static final String URL = "http://localhost:8080/api/";

    static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(
                    "lab.client.config"
            );

    public static RestTemplate restTemplate = context.getBean(RestTemplate.class);

    public static void main(String[] args) {

        while(true) {
            System.out.println("Input command:\n1.Add a client\n2.Add a movie\n3.Delete a client\n4.Delete a movie\n5.Update a client\n6.Update a movie\n" +
                    "7.Print all clients\n8.Print all movies\n9.Rent movie.\n10.Delete rental.\n11.Update rental.\n" +
                    "12.Print all rentals.\n13.Filter movies by name\n14.Filter clients by name\n0.Exit");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            try {
                int selection = Integer.parseInt(bufferRead.readLine());
                switch (selection) {
                    case 1:
                        addClient(bufferRead);
                        break;
                    case 2:
                        addMovie(bufferRead);
                        break;
                    case 3:
                        deleteClient(bufferRead);
                        break;
                    case 4:
                        deleteMovie(bufferRead);
                        break;
                    case 5:
                        updateClient(bufferRead);
                        break;
                    case 6:
                        updateMovie(bufferRead);
                        break;
                    case 7:
                        printAllClients(restTemplate);
                        break;
                    case 8:
                        printAllMovies(restTemplate);
                        break;
                    case 9:
                        rentMovie(bufferRead);
                        break;
                    case 10:
                        deleteRental(bufferRead);
                        break;
                    case 11:
                        updateRental(bufferRead);
                        break;
                    case 12:
                        printAllRentals(restTemplate);
                        break;
                    case 13:
                        filterMoviesByName(bufferRead);
                        break;
                    case 14:
                        filterClientsByName(bufferRead);
                        break;
                    case 0:
                        bufferRead.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid command!\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void getMostRentedMovie() {
        MovieDto moviedto = restTemplate.getForObject(URL + "movies/stat/", MovieDto.class);
        System.out.println(moviedto.getId());
    }

    private static void printAllClients(RestTemplate restTemplate){
        ClientsDto allClients = restTemplate.getForObject(URL + "clients", ClientsDto.class);
        System.out.println(allClients);
    }

    private static void printAllMovies(RestTemplate restTemplate){
        MoviesDto allMovies = restTemplate.getForObject(URL + "movies", MoviesDto.class);
        System.out.println(allMovies);
    }

    private static void printAllRentals(RestTemplate restTemplate){
        RentalsDto allRentals = restTemplate.getForObject(URL + "rentals", RentalsDto.class);
        System.out.println(allRentals);
    }

    private static void addClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client name:");
            String name = buf.readLine();
            System.out.println("Insert client age: ");
            int age = Integer.parseInt(buf.readLine());
            System.out.println("Insert client serial number: ");
            String serial = buf.readLine();

            restTemplate.put(URL + "clients", new ClientDto(serial, name, age), ClientDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie name:");
            String name = buf.readLine();
            System.out.println("Insert movie year: ");
            int year = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert movie rating: ");
            int rating = Integer.parseInt(buf.readLine());

            restTemplate.put(URL + "movies", new MovieDto(serial, name, year, rating), MovieDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void rentMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();
            System.out.println("Insert movie ID: ");
            long movieID = Long.valueOf(buf.readLine());
            System.out.println("Insert client ID: ");
            long clientID = Long.valueOf(buf.readLine());

            restTemplate.put(URL + "rentals", new RentalDto(rentalSerial, movieID, clientID, false), RentalDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            restTemplate.delete(URL + "clients/{id}", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            restTemplate.delete(URL + "movies/{id}", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteRental(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());

            restTemplate.delete(URL + "rentals/{id}", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client name:");
            String name = buf.readLine();
            System.out.println("Insert client age: ");
            int age = Integer.parseInt(buf.readLine());
            System.out.println("Insert client serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            ClientDto clientdto = restTemplate.getForObject(URL + "clients/{id}", ClientDto.class, id);
            ClientConverter conv = new ClientConverter();
            Client c  = conv.convertDtoToModel(clientdto);
            c.setName(name);
            c.setAge(age);
            c.setSerialNumber(serial);
            restTemplate.put(URL + "clients/{id}", c, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie name:");
            String name = buf.readLine();
            System.out.println("Insert movie year: ");
            int year = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert movie rating: ");
            int rating = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            MovieDto moviedto = restTemplate.getForObject(URL + "movies/{id}", MovieDto.class, id);
            MovieConverter conv = new MovieConverter();
            Movie m = conv.convertDtoToModel(moviedto);
            m.setName(name);
            m.setYear(year);
            m.setSerialNumber(serial);
            m.setRating(rating);
            restTemplate.put(URL + "movies/{id}", m, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateRental(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();
            System.out.println("Insert movie ID: ");
            long movieID = Long.valueOf(buf.readLine());
            System.out.println("Insert client ID: ");
            long clientID = Long.valueOf(buf.readLine());
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());
            System.out.println("Is returned? y/n");
            String ret = buf.readLine();


            RentalDto rentaldto = restTemplate.getForObject(URL + "rentals/{id}", RentalDto.class, id);
            RentalConverter conv = new RentalConverter();
            Rental r = conv.convertDtoToModel(rentaldto);
            r.setRentalSerialNumber(rentalSerial);
            r.setMovieID(movieID);
            r.setClientID(clientID);
            if(ret.equalsIgnoreCase("y"))
                r.setReturned(true);
            restTemplate.put(URL + "rentals/{id}", r, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void returnMovie(BufferedReader buf) {
//        try {
//            System.out.println("Insert rental id: ");
//            long id = Long.valueOf(buf.readLine());
//            RentalDto rentaldto = restTemplate.getForObject(URL + "rentals/{id}", RentalDto.class, id);
//            RentalConverter conv = new RentalConverter();
//            Rental r = conv.convertDtoToModel(rentaldto);
//            r.setReturned(true);
//            restTemplate.put(URL + "rentals/{id}", r, id);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    private static void filterMoviesByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();
            MoviesDto allMovies = restTemplate.getForObject(URL + "movies/f/{filter}", MoviesDto.class, filter);
            System.out.println(allMovies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void filterClientsByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();
            ClientsDto allClients = restTemplate.getForObject(URL + "clients/f/{filter}", ClientsDto.class, filter);
            System.out.println(allClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sortMovies()
    {
        try{
            MoviesDto allMovies = restTemplate.getForObject(URL + "movies/s", MoviesDto.class);
            System.out.println(allMovies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sortClients()
    {
        try{
            ClientsDto allClients = restTemplate.getForObject(URL + "clients/s", ClientsDto.class);
            System.out.println(allClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sortRentals()
    {
        try{
            RentalsDto allRentals = restTemplate.getForObject(URL + "rentals/s", RentalsDto.class);
            System.out.println(allRentals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
