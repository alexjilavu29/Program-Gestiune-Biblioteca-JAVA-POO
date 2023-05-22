package Repositories;

import Classes.Book;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public final class BookRepository {
    public List<Book> bookList;

    public BookRepository(){
        bookList = new ArrayList<>();
    }

    public void insertBook(Book book) throws SQLException {
        String sql2 = "SELECT * FROM books WHERE title = ?";
        String sql = "INSERT INTO books (title, author, year, genre,copiesInStore) VALUES (?, ?, ?, ?,?)";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmt2 = con.prepareStatement(sql2)) {
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

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    book.setIdBook(generatedId);
                }
            }
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

    public void getBooks() throws SQLException {
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

}
