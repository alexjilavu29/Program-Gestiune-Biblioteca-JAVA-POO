import Classes.Author;
import Classes.Book;
import Classes.Client;
import Classes.Order;
import Repositories.AuthorRepository;
import Repositories.BookRepository;
import Repositories.ClientRepository;
import Repositories.OrderRepository;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {


        BookRepository bookRepo = new BookRepository();
        bookRepo.getBooks();

        System.out.println(bookRepo.bookList);

        Book b1= new Book(-1,"Test","Testuleanu",2023,"scary",2);

        bookRepo.insertBook(b1);


        bookRepo.getBooks();
        System.out.println(bookRepo.bookList);

        bookRepo.deleteBook(b1);

        bookRepo.getBooks();
        System.out.println(bookRepo.bookList);

        AuthorRepository authorRepo = new AuthorRepository();
        authorRepo.getAuthors();
        System.out.println(authorRepo.authorList);

        Author a1 = new Author(-1,"Testuleanu",2023,20,0);

        authorRepo.insertAuthor(a1);

        authorRepo.getAuthors();
        System.out.println(authorRepo.authorList);

        authorRepo.deleteAuthor(a1);

        authorRepo.getAuthors();
        System.out.println(authorRepo.authorList);


        ClientRepository clientRepo = new ClientRepository();
        clientRepo.getClients();
        System.out.println(clientRepo.clientList);

        Client c1 = new Client(-1,"Testulescu","testulescu@gmail.com",3, Date.valueOf("2023-05-22"),false);

        clientRepo.insertClient(c1);

        clientRepo.getClients();
        System.out.println(clientRepo.clientList);

        clientRepo.deleteClient(c1);

        clientRepo.getClients();
        System.out.println(clientRepo.clientList);


        OrderRepository orderRepo = new OrderRepository();
        orderRepo.getOrders();
        System.out.println(orderRepo.orderList);

        Order o1 = new Order(-1,2,Date.valueOf("2023-03-04"),3,Date.valueOf("2023-05-04"),false,false);

        orderRepo.insertOrder(o1);

        orderRepo.getOrders();
        System.out.println(orderRepo.orderList);

        orderRepo.deleteOrder(o1);

        orderRepo.getOrders();
        System.out.println(orderRepo.orderList);
    }

}