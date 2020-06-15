package lab9.lab.ui;

import lab9.lab.domain.Client;
import lab9.lab.domain.Movie;
import lab9.lab.domain.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lab9.lab.service.ClientService;
import lab9.lab.service.MovieService;
import lab9.lab.service.RentalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


@Component
public class Console {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RentalService rentalService;

    public void runConsole()
    {
        while(true)
        {
            System.out.println("Input command:\n1.Add a client\n2.Add a movie\n3.Delete a client\n4.Delete a movie\n5.Update a client\n6.Update a movie\n" +
                    "7.Print all clients\n8.Print all movies\n9.Rent movie.\n10.Return movie.\n11.Delete rental.\n12.Update rental.\n" +
                    "13.Print all rentals.\n14.Filter movies by name\n15.Filter clients by name\n16.Get most rented movie\n17.Get most active client\n18.Get client with most returned movies\n" +
                    "19.# of clients without movies - # of movies without clients=? (are there more clients without movies than movies without clients?)\n"
            +"20.Print sorted movies\n21.Print sorted clients\n22.Print sorted rentals\n0.Exit");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            try{
                int selection = Integer.parseInt(bufferRead.readLine());
                switch(selection){
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
                        printAllClients();
                        break;
                    case 8:
                        printAllMovies();
                        break;
                    case 9:
                        rentMovie(bufferRead);
                        break;
                    case 10:
                        returnMovie(bufferRead);
                        break;
                    case 11:
                        deleteRental(bufferRead);
                        break;
                    case 12:
                        updateRental(bufferRead);
                        break;
                    case 13:
                        printAllRentals();
                        break;
                    case 14:
                        filterMoviesByName(bufferRead);
                        break;
                    case 15:
                        filterClientsByName(bufferRead);
                        break;
                    case 16:
                        getMostRentedMovie();
                        break;
                    case 17:
                        getMostActiveClient();
                        break;
                    case 18:
                        getClientWithMostReturned();
                        break;
                    case 19:
                        computeClientsMinusBooks();
                        break;
                    case 20:
                        sortMovies();
                        break;
                    case 21:
                        sortClients();
                        break;
                    case 22:
                        sortRentals();
                        break;
                    case 0:
                        bufferRead.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid command!\n");
                }
            } catch (Exception e) { e.printStackTrace();}

        }
    }

    private void printAllClients() throws IOException, ClassNotFoundException, SQLException {
        Set<Client> clients = clientService.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    private void printAllMovies() throws IOException, ClassNotFoundException, SQLException {
        Set<Movie> movies = movieService.getAllMovies();
        movies.stream().forEach(System.out::println);
    }

    private void sortMovies() throws Exception {
        List<Movie> movies = movieService.getAllMoviesSorted();
        movies.stream().forEach(System.out::println);
    }

    private void sortClients() throws Exception {
        List<Client> clients = clientService.getAllClientsSorted();
        clients.stream().forEach(System.out::println);
    }

    private void sortRentals() throws Exception {
        List<Rental> rentals = rentalService.getAllRentalsSorted();
        rentals.stream().forEach(System.out::println);
    }

    private void addClient(BufferedReader buf)
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

            Client cl = new Client(name, age, serial);
            cl.setId(id);

            clientService.addClient(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMovie(BufferedReader buf)
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

            Movie mv = new Movie(name, year, rating, serial);
            mv.setId(id);

            movieService.addMovie(mv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            rentalService.deleteClient(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            rentalService.deleteMovie(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateClient(BufferedReader buf)
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

            Client cl = new Client(name, age, serial);
            cl.setId(id);

            clientService.updateClient(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMovie(BufferedReader buf)
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

            Movie mv = new Movie(name, year, rating, serial);
            mv.setId(id);

            movieService.updateMovie(mv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printAllRentals() throws IOException, ClassNotFoundException, SQLException {
        Set<Rental> rentals = rentalService.getAllRentals();
        rentals.stream().forEach(System.out::println);
    }

    private void rentMovie(BufferedReader buf)
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

            Rental ren = new Rental(rentalSerial, movieID, clientID);
            ren.setId(id);

            rentalService.addRental(ren);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteRental(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());

            rentalService.deleteRental(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateRental(BufferedReader buf)
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

            Rental ren = new Rental(rentalSerial, movieID, clientID);
            ren.setId(id);

            rentalService.updateRental(ren);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnMovie(BufferedReader buf) {
        try {
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();
            rentalService.returnMovie(rentalSerial);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void filterMoviesByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();
            Set<Movie> movieSet = this.movieService.filterMoviesByName(filter);
            movieSet.forEach(movie -> System.out.println(movie));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterClientsByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();
            Set<Client> clientSet = this.clientService.filterClientsByName(filter);
            clientSet.forEach(client -> System.out.println(client));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void getMostRentedMovie() {
        try {
            this.rentalService.getMostRentedMovie().forEach(s->System.out.println(s));
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void getMostActiveClient() {
        try {
            this.rentalService.getMostActiveClient().forEach(s->System.out.println(s));
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void getClientWithMostReturned() {
        try {
            this.rentalService.getClientWithMostReturned().forEach(s->System.out.println(s));
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void computeClientsMinusBooks() {
        try {
            System.out.println(this.rentalService.computeClientsMinusBooks());
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
