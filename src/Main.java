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
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner in = new Scanner(System.in);

        BookRepository bookRepo = new BookRepository();
        ClientRepository clientRepo = new ClientRepository();
        AuthorRepository authorRepo = new AuthorRepository();
        OrderRepository orderRepo = new OrderRepository();

        bookRepo.getBooks();
        clientRepo.getClients();
        authorRepo.getAuthors();
        orderRepo.getOrders();

        System.out.println(bookRepo.bookList);
        System.out.println(clientRepo.clientList);
        System.out.println(authorRepo.authorList);
        System.out.println(orderRepo.orderList);


        System.out.println("");
        System.out.println("_________ Pagina principală _________");
        System.out.println("");
        System.out.println("    1. Opțiuni Cărți");
        System.out.println("        - Adăugare cărți în baza de date");
        System.out.println("        - Ordonare cărți după numărul de copii disponibile");
        System.out.println("");
        System.out.println("    2. Opțiuni Clienți");
        System.out.println("        - Inserare client în baza de date");
        System.out.println("        - Ștergere client din baza de date");
        System.out.println("        - Realizare Check In Client");
        System.out.println("        - Pagină de Profil Client");
        System.out.println("");
        System.out.println("    3. Opțiuni Comenzi");
        System.out.println("        - Împrumutare cărți");
        System.out.println("        - Readăugare cărți în stoc");
        System.out.println("        - Realizare Check Up clienți restanți");
        System.out.println("");
        System.out.println("            | Introduceți tasta 1 / 2 / 3 pentru a selecta una din opțiuni:");
        int choice1 = in.nextInt();
        in.nextLine();
        if(choice1==1)
        {
            System.out.flush();
            System.out.println("_________ Pagina principală -> Cărți _________");
            System.out.println("");
            System.out.println("    1. Adăugare cărți în baza de date");
            System.out.println("    2. Ordonare cărți după numărul de copii disponibile");
            System.out.println("");
            System.out.println("            | Introduceți tasta 1 / 2 pentru a selecta una din opțiuni:");
            int choice1_1 = in.nextInt();
            in.nextLine();

            if(choice1_1 == 1)
            {
                System.out.flush();
                bookRepo.insertBookQuery();
            }else if(choice1_1 == 2)
            {
                System.out.flush();
                bookRepo.filteredView();
            }
            else System.out.println("Alegerea introdusă nu este disponibilă.");
        }else
        if(choice1==2)
        {
            System.out.flush();
            System.out.println("_________ Pagina principală -> Clienți _________");
            System.out.println("");
            System.out.println("    1. Inserare client în baza de date");
            System.out.println("    2. Ștergere client din baza de date");
            System.out.println("    3. Realizare Check In Client");
            System.out.println("    4. Pagină de Profil Client");
            System.out.println("");
            System.out.println("            | Introduceți tasta 1 / 2 / 3 / 4 pentru a selecta una din opțiuni:");
            int choice1_2 = in.nextInt();
            in.nextLine();

            if(choice1_2 == 1)
            {
                System.out.flush();
                clientRepo.insertClientQuery();
            }else if(choice1_2 == 2)
            {
                System.out.flush();
                clientRepo.deleteClientQuery();
            }else if(choice1_2 == 3)
            {
                System.out.flush();
                clientRepo.clientCheckIn();
            }else if(choice1_2 == 4)
            {
                System.out.flush();
                clientRepo.profilePageQuery();
            }
            else System.out.println("Alegerea introdusă nu este disponibilă.");
        }else if(choice1==3)
        {
            System.out.flush();
            System.out.println("_________ Pagina principală -> Comenzi _________");
            System.out.println("");
            System.out.println("    1. Împrumutare cărți");
            System.out.println("    2. Readăugare cărți în stoc");
            System.out.println("    3. Realizare Check Up clienți restanți");
            System.out.println("");
            System.out.println("            | Introduceți tasta 1 / 2 / 3 pentru a selecta una din opțiuni:");
            int choice1_3 = in.nextInt();
            in.nextLine();

            if(choice1_3 == 1)
            {
                System.out.flush();
                orderRepo.borrowBookQuery();
            }else if(choice1_3 == 2)
            {
                System.out.flush();
                orderRepo.retrieveBookQuery();
            }else if(choice1_3 == 3)
            {
                System.out.flush();
                orderRepo.orderCheckUp();
            }
            else System.out.println("Alegerea introdusă nu este disponibilă.");
        }else System.out.println("Alegerea introdusă nu este disponibilă.");


    }


}