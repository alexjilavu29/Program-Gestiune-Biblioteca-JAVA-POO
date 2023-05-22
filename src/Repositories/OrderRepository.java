package Repositories;

import Classes.Order;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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

}
