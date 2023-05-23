package Repositories;

import Classes.Book;
import Classes.Author;
import Repositories.*;
import java.io.BufferedReader;
import java.sql.*;
import java.util.*;

public final class BookRepository {
    public List<Book> bookList;

    public BookRepository(){
        bookList = new ArrayList<>();
    }

    public void insertBook(Book book) throws SQLException {
        String sql2 = "SELECT * FROM books WHERE title = ?";
        String sql = "INSERT INTO books (title, author, year, genre,copiesInStore) VALUES (?, ?, ?, ?,?)";
        String sqlAuthor = "UPDATE authors set titlesInStore=titlesInStore+1 WHERE name = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmt2 = con.prepareStatement(sql2);
             PreparedStatement pstmtAuthor = con.prepareStatement(sqlAuthor)) {
            pstmt2.setString(1, book.getTitle());
            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                book.setIdBook(rs.getInt("idBook"));
                System.out.println("Cartea exista deja.");
            } else {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setInt(3, book.getYear());
                pstmt.setString(4, book.getGenre());
                pstmt.setInt(5, book.getCopiesInStore());
                pstmt.executeUpdate();

                pstmtAuthor.setString(1,book.getAuthor());
                pstmtAuthor.execute();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    book.setIdBook(generatedId);
                }
            }
        }
    }

    public void insertBookQuery() throws SQLException
    {
        Scanner in = new Scanner(System.in);
        System.out.println("_________ MENIU INTRODUCERE CARTE _________");

        System.out.println("LISTĂ AUTORI:");
        AuthorRepository authorRepo = new AuthorRepository();
        authorRepo.getAuthors();
        System.out.println(authorRepo.authorList);

        System.out.println("Cartea dorită aparține de un autor din lista de mai sus?");
        System.out.println("În cazul în care autorul nu se regăsește în listă, va trebui să îl introduceți.");
        System.out.println("Inserând NU, veți fi oferit un meniu de introducere a datelor pentru autor.");
        System.out.println("Scrieți ANULARE pentru a termina.");
        System.out.println("Introduceți DA / NU / ANULARE");
        String answer = in.nextLine();

        if(answer.equals("DA"))
        {
            insertBook(inputBookDetails());
            System.out.println("Cartea dorită a fost inserată cu succes.");
            System.out.println("Listă actualizată cărți:");
            getBooks();
            System.out.println(bookList);
        }else
        if(answer.equals("NU"))
        {
            System.out.println("_________ MENIU INTRODUCERE AUTOR _________");
            authorRepo.insertAuthor(authorRepo.inputAuthorDetails());
            System.out.println("Autorul dorit a fost inserat cu succes.");
            System.out.println("Listă actualizată autori:");
            authorRepo.getAuthors();
            System.out.println(authorRepo.authorList);

            System.out.println("_________ MENIU SECUNDAR INTRODUCERE CARTE _________");
            insertBook(inputBookDetails());
            System.out.println("Cartea dorită a fost inserată cu succes.");
            System.out.println("Listă actualizată cărți:");
            getBooks();
            System.out.println(bookList);
        }else
        if(answer.equals("ANULARE"))
        {

        }
        else System.out.println("Ati introdus un raspuns indisponibil.");
    }

    public void filteredView()
    {
        Map<Book,Integer> bookCopiesMap = new HashMap<>();
        for(Book book : bookList)
            bookCopiesMap.put(book,book.getCopiesInStore());
        List<Map.Entry<Book,Integer>> sortedBookList = new ArrayList<>(bookCopiesMap.entrySet());
        sortedBookList.sort(Map.Entry.comparingByValue());
        System.out.println("_________ Listă ordonată - Tituri disponibile _________");
        for(Map.Entry<Book,Integer> element : sortedBookList)
        {
            Book book = element.getKey();
            int copiesInStore = element.getValue();
            System.out.println("Titlul cărții: " + book.getTitle() + "    Exemplare în stoc: " + copiesInStore);
        }
    }

    public void deleteBook(Book book) throws SQLException
    {

        if(book.getIdBook() == -1)
            return;

        String sql = "DELETE FROM books WHERE idBook = ?";
        try (Connection con = DatabaseHandler.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1,book.getIdBook());
            int result = pstmt.executeUpdate();
        }
        bookList.remove(book);
    }

    public void getBooks() throws SQLException
    {
        String sql = "SELECT * FROM books";
        Connection con = DatabaseHandler.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        bookList.clear();
        while(rs.next())
        {
            int idBook = rs.getInt("idBook");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int year = rs.getInt("year");
            String genre = rs.getString("genre");
            int copiesInStore = rs.getInt("copiesInStore");

            Book crBook = new Book(idBook,title,author,year,genre,copiesInStore);
            bookList.add(crBook);
        }
    }

    public Book inputBookDetails()
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Introduceți titlul cărții:");
        String title = in.nextLine();

        System.out.println("Introduceți autorul cărții:");
        String author = in.nextLine();

        System.out.println("Introduceți anul cărții:");
        int year = in.nextInt();
        in.nextLine();

        System.out.println("Introduceți genul cărții:");
        String genre = in.nextLine();

        System.out.println("Introduceți numărul de copii în magazin:");
        int copiesInStore = in.nextInt();
        in.nextLine();

        Book book = new Book(-1, title, author, year, genre, copiesInStore);

        return book;
    }


}
