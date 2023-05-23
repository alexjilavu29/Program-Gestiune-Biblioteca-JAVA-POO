package Repositories;

import Classes.*;

import java.awt.desktop.SystemEventListener;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Calendar;


public final class OrderRepository {
    public List<Order> orderList;

    public OrderRepository(){
        orderList = new ArrayList<>();
    }

    public void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (idClient, outDate, numberOfBooks,returnDate, completed, pastDueTime) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setInt(1, order.getIdClient());
            pstmt.setDate(2, order.getOutDate());
            pstmt.setInt(3, order.getNumberOfBooks());
            pstmt.setDate(4, order.getReturnDate());
            pstmt.setBoolean(5, order.getCompleted());
            pstmt.setBoolean(6, order.getPastDueTime());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                order.setIdOrder(generatedId);
            }
        }

    }

    public void borrowBookQuery() throws SQLException
    {
        Scanner in = new Scanner(System.in);
        BookRepository bookRepo = new BookRepository();
        bookRepo.getBooks();
        ClientRepository clientRepo = new ClientRepository();
        clientRepo.getClients();

        System.out.println("_________ MENIU ÎMPRUMUTARE CARTE _________");

        String sqlQuery = "SELECT * FROM books WHERE copiesInStore>0";
        String sqlUpdate = "UPDATE books set copiesInStore=copiesInStore-? WHERE idBook = ?";
        String sqlUpdateClient = "UPDATE clients set totalOrders = totalOrders + 1 , activeOrder = 1 WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sqlQuery);
             PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
             PreparedStatement pstmtUpdateClient = con.prepareStatement(sqlUpdateClient)) {
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
                System.out.println(rs.getInt("idBook") + ". " + rs.getString("title") + "Cărți în stoc: " + rs.getInt("copiesInStore"));
                while (rs.next())
                {
                    System.out.println(rs.getInt("idBook") + ". " + rs.getString("title") + " -- Cărți în stoc: " + rs.getInt("copiesInStore"));
                }
                System.out.println("Inserați ID-ul cărții pe care doriți să o împrumutați:");
                int answer = in.nextInt();
                in.nextLine();

                Order order = new Order(inputOrderDetails());

                int okClient = 0;
                Client targetClient = null;
                for (Client element : clientRepo.clientList)
                    if(element.getId() == order.getIdClient())
                    {
                        targetClient = element;
                        okClient = 1;
                    }
                if(okClient==1)
                {
                    if(targetClient.getActiveOrder()==false)
                    {
                        pstmtUpdateClient.setInt(1,targetClient.getId());
                        int ok=0;
                        Book targetBook = null;
                        for (Book element : bookRepo.bookList)
                            if(element.getIdBook() == answer)
                            {
                                targetBook = new Book(element);
                                ok = 1;
                            }
                        if( ok == 1)
                        {
                            if(targetBook.getCopiesInStore()>=order.getNumberOfBooks())
                            {
                                pstmtUpdate.setInt(1,order.getNumberOfBooks());
                                pstmtUpdate.setInt(2,answer);
                                pstmtUpdate.executeUpdate();
                                pstmtUpdateClient.executeUpdate();
                                insertOrder(order);
                                getOrders();
                                System.out.println("Comandă efectuată cu succes.");
                                System.out.println("Listă comenzi:");
                                System.out.println(orderList);
                            }
                            else{
                                System.out.println("Nu sunt suficiente cărți pentru a susține comanda.");
                            }
                        }
                        else {
                            System.out.println("ID-ul introdus nu a fost găsit în lista de cărți.");
                        }


                    }
                    else
                    {
                        System.out.println("Clientul are deja o comandă activă.");
                    }
                }
                else
                    {
                        System.out.println("ID-ul clientului nu a fost găsit în baza de date.");
                    }




            } else {
                System.out.println("Nu există nicio carte disponibilă in acest moment.");
                }
            }


    }

    public void retrieveBookQuery() throws SQLException
    {
        Scanner in = new Scanner(System.in);
        BookRepository bookRepo = new BookRepository();
        bookRepo.getBooks();
        ClientRepository clientRepo = new ClientRepository();
        clientRepo.getClients();

        System.out.println("_________ MENIU ÎNAPOIERE CARTE _________");
        String sqlSearch = "SELECT * FROM orders WHERE idClient = ? AND completed = 0 ";
        String sqlUpdate = "UPDATE orders set completed = 1 WHERE idOrder = ?";
        String sqlUpdateClient = "UPDATE clients set activeOrder = 0 WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtSearch = con.prepareStatement(sqlSearch);
             PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
             PreparedStatement pstmtUpdateClient = con.prepareStatement(sqlUpdateClient)) {
            System.out.println("Inserați ID-ul clientului care dorește să înapoieze comanda:");
            int answer = in.nextInt();
            in.nextLine();
            pstmtSearch.setInt(1,answer);
            ResultSet rs = pstmtSearch.executeQuery();
            if(rs.next())
            {
                System.out.println("Detalii comandă:");
                System.out.println(rs.getInt("idOrder") + ". ID client:" + rs.getInt("idClient") + "        Dată împrumut: " + rs.getDate("outDate"));
                System.out.println("Număr exemplare împrumutate: " + rs.getInt("numberOfBooks") + " Dată limită: " + rs.getDate("returnDate"));
                System.out.println("Comandă cu termen de întoarcere depășit: " + rs.getInt("pastDueTime"));

                pstmtUpdate.setInt(1,rs.getInt("idOrder"));
                pstmtUpdate.executeUpdate();

                pstmtUpdateClient.setInt(1,rs.getInt("idClient"));
                pstmtUpdateClient.executeUpdate();

            } else {
                System.out.println("Clientul introdus nu are o comanda activa sau ID-ul este greșit.");
            }
        }
    }

    public void orderCheckUp() throws SQLException
    {
        String sqlQuery = "SELECT * FROM orders WHERE returnDate<? AND completed = 0";
        String sqlUpdate = "UPDATE orders set pastDueTime = 1 WHERE idOrder = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtQuery = con.prepareStatement(sqlQuery);
             PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate)) {
            Calendar calendar = Calendar.getInstance();
            java.util.Date  dateUtil = calendar.getTime();
            java.sql.Date date = new java.sql.Date(dateUtil.getTime());
            pstmtQuery.setDate(1,date);
            ResultSet rs = pstmtQuery.executeQuery();
            System.out.println("_________ MENIU CHECK UP CLIENȚI RESTNANȚI _________");
            while(rs.next())
            {
                pstmtUpdate.setInt(1,rs.getInt("idOrder"));
                pstmtUpdate.executeUpdate();
                System.out.println("UPDATE: Clientul cu ID-ul " + rs.getString("idClient") +
                        " a întârziat în aducerea comenzii." + "    (Comanda Nr. "+ rs.getInt("idOrder") + ")");
            }
        }
    }

    public void deleteOrder(Order order) throws SQLException
    {

        if(order.getIdOrder() == -1)
            return;

        String sql = "DELETE FROM orders WHERE idOrder = ?";
        try (Connection con = DatabaseHandler.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1,order.getIdOrder());
            int result = pstmt.executeUpdate();
        }
        orderList.remove(order);
    }

    public void getOrders() throws SQLException {
        String sql = "SELECT * FROM orders";
        Connection con = DatabaseHandler.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        orderList.clear();
        while(rs.next())
        {
            int idOrder = rs.getInt("idOrder");
            int idClient = rs.getInt("idClient");
            Date outDate = rs.getDate("outDate");
            int numberOfBooks = rs.getInt("numberOfBooks");
            Date returnDate = rs.getDate("returnDate");
            Boolean completed = rs.getBoolean("completed");
            Boolean pastDueTime = rs.getBoolean("pastDueTime");

            Order crOrder = new Order(idOrder,idClient,outDate,numberOfBooks,returnDate,completed,pastDueTime);
            orderList.add(crOrder);
        }
    }

    public Order inputOrderDetails(){
        Scanner in = new Scanner(System.in);

        System.out.println("Introduceți ID-ul clientului:");
        int idClient = in.nextInt();
        in.nextLine();

        System.out.println("Introduceți termenul de întoarece a cărții (în zile):");
        int dueTime = in.nextInt();
        in.nextLine();

        System.out.println("Introduceți numărul de cărți împrumutate:");
        int numberOfBooks = in.nextInt();
        in.nextLine();

        Calendar calendar = Calendar.getInstance();
        java.util.Date  outDateUtil = calendar.getTime();
        java.sql.Date outDate = new java.sql.Date(outDateUtil.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,dueTime);
        java.util.Date  returnDateUtil = calendar.getTime();
        java.sql.Date returnDate = new java.sql.Date(returnDateUtil.getTime());

        Order order = new Order(-1,idClient,outDate,numberOfBooks,returnDate,false,false);

        return order;
    }

}
