package Repositories;

import Classes.Client;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public final class ClientRepository {
    public List<Client> clientList;

    public ClientRepository(){
        clientList = new ArrayList<>();
    }

    public void insertClient(Client client) throws SQLException {
        String sql2 = "SELECT * FROM clients WHERE name = ?";
        String sql = "INSERT INTO clients (name, email, totalOrders,lastDateCheckedIn, activeOrder) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmt2 = con.prepareStatement(sql2)) {
            pstmt2.setString(1, client.getName());
            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                client.setId(rs.getInt("idClient"));
                System.out.println("Clientul exista deja.");
            } else {
                pstmt.setString(1, client.getName());
                pstmt.setString(2, client.getEmail());
                pstmt.setInt(3, client.getTotalOrders());
                pstmt.setDate(4, client.getLastDateCheckedIn());
                pstmt.setBoolean(5, client.getActiveOrder());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    client.setId(generatedId);
                }
            }
        }
    }

    public void deleteClient(Client client) throws SQLException
    {

        if(client.getId() == -1)
            return;

        String sql = "DELETE FROM clients WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1,client.getId());
            int result = pstmt.executeUpdate();
        }
        clientList.remove(client);
    }

    public void getClients() throws SQLException {
        String sql = "SELECT * FROM clients";
        Connection con = DatabaseHandler.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);
        clientList.clear();
        while(rs.next())
        {
            int idClient = rs.getInt("idClient");
            String name = rs.getString("name");
            String email = rs.getString("email");
            int totalOrders = rs.getInt("totalOrders");
            Date lastDateCheckedIn = rs.getDate("lastDateCheckedIn");
            Boolean activeOrder = rs.getBoolean("activeOrder");

            Client crClient = new Client(idClient,name,email,totalOrders,lastDateCheckedIn,activeOrder);
            clientList.add(crClient);
        }
    }

}
