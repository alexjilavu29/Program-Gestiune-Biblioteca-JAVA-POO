package Repositories;

import Classes.Client;
import Support.AuditLogs;
import Support.DatabaseHandler;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Calendar;

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
             PreparedStatement pstmt2 = con.prepareStatement(sql2))
        {
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
                Calendar calendarAudit = Calendar.getInstance();
                java.util.Date  dateUtilAudit = calendarAudit.getTime();
                AuditLogs auditLogs = new AuditLogs();
                auditLogs.insertAudit("Inserare client efectuată.",dateUtilAudit);
            }
        }
    }

    public void insertClientQuery() throws SQLException {
        Scanner in = new Scanner(System.in);
        String sqlSearch = "SELECT * FROM clients WHERE name = ?";
        String sqlInsert = "INSERT INTO clients (name, email, totalOrders,lastDateCheckedIn, activeOrder) VALUES (?, ?, 0, ?, 0)";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtSearch = con.prepareStatement(sqlSearch);
             PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS))
        {
            System.out.println("_________ MENIU INSERARE CLIENT _________");
            System.out.println("Inserați numele clientului: ");
            String name = in.nextLine();
            pstmtSearch.setString(1,name);
            ResultSet rs = pstmtSearch.executeQuery();
            int ok = 1;
            if(rs.next()) {
                System.out.println("Numele inserat există deja în baza de date. Doriți să îl inserați oricum?");
                System.out.println("Scrieți DA / NU :");
                String answer = in.nextLine();
                if(answer.equals("DA"))
                {
                    ok = 1;
                }
                else
                    if(answer.equals("NU"))
                        ok=0;
                    else
                    {System.out.println("Răspunsul introdus este indisponibil.");
                    ok=0;
                    }
            }
            if(ok==1)
            {
                System.out.println("Inserați email-ul clientului: ");
                String email = in.nextLine();
                Calendar calendar = Calendar.getInstance();
                java.util.Date  dateUtil = calendar.getTime();
                java.sql.Date date = new java.sql.Date(dateUtil.getTime());

                pstmtInsert.setString(1, name);
                pstmtInsert.setString(2, email);
                pstmtInsert.setDate(3, date);
                pstmtInsert.executeUpdate();
                System.out.println("Clientul a fost inserat cu succes.");

                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("ID-ul clientului nou este : " + generatedId);
                }
                Calendar calendarAudit = Calendar.getInstance();
                java.util.Date  dateUtilAudit = calendarAudit.getTime();
                AuditLogs auditLogs = new AuditLogs();
                auditLogs.insertAudit("Inserare client efectuată.",dateUtilAudit);
            }


        }
    }

    public void deleteClientQuery() throws SQLException{
        Scanner in = new Scanner(System.in);
        String sqlSearch = "SELECT * FROM clients WHERE idClient = ?";
        String sqlDelete = "DELETE FROM clients WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtSearch = con.prepareStatement(sqlSearch);
             PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete))
        {
            System.out.println("_________ MENIU ȘTERGERE CLIENT _________");
            System.out.println("Inserați ID-ul clientului: ");
            int idClient = in.nextInt();
            in.nextLine();
            pstmtSearch.setInt(1,idClient);
            ResultSet rs = pstmtSearch.executeQuery();
            if(rs.next()) {
                System.out.println("Numele clientului: " + rs.getString("name"));
                System.out.println("Email-ul clientului: " + rs.getString("email"));
                System.out.println("Doriți să ștergeți acest client din baza de date?");
                System.out.println("Scrieți DA / NU :");
                String answer = in.nextLine();
                if(answer.equals("DA"))
                {
                    pstmtDelete.setInt(1,idClient);
                    pstmtDelete.executeUpdate();
                    System.out.println("Clientul a fost șters cu succes!");
                    Calendar calendarAudit = Calendar.getInstance();
                    java.util.Date  dateUtilAudit = calendarAudit.getTime();
                    AuditLogs auditLogs = new AuditLogs();
                    auditLogs.insertAudit("Ștergere client efectuată.",dateUtilAudit);
                }
                else
                if(answer.equals("NU"))
                {
                }
                else
                System.out.println("Răspunsul introdus este indisponibil.");
            }
            else System.out.println("Clientul nu există în baza de date sau ID-ul introdus este greșit.");
        }
    }

    public void clientCheckIn() throws SQLException{
        Scanner in = new Scanner(System.in);
        String sqlSearch = "SELECT * FROM clients WHERE idClient = ?";
        String sqlUpdate = "UPDATE clients set lastDateCheckedIn = ? WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtSearch = con.prepareStatement(sqlSearch);
             PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate)) {
            System.out.println("_________ MENIU CHECK IN CLIENT _________");
            System.out.println("Inserați ID-ul clientului: ");
            int idClient = in.nextInt();
            in.nextLine();
            pstmtSearch.setInt(1, idClient);
            ResultSet rs = pstmtSearch.executeQuery();
            if(rs.next()) {
                System.out.println("Bună ziua, " + rs.getString("name") + "!");
                Calendar calendar = Calendar.getInstance();
                java.util.Date  dateUtil = calendar.getTime();
                java.sql.Date date = new java.sql.Date(dateUtil.getTime());
                pstmtUpdate.setDate(1, date);
                pstmtUpdate.setInt(2, idClient);
                pstmtUpdate.executeUpdate();
                Calendar calendarAudit = Calendar.getInstance();
                java.util.Date  dateUtilAudit = calendarAudit.getTime();
                AuditLogs auditLogs = new AuditLogs();
                auditLogs.insertAudit("Check In client efectuat.",dateUtilAudit);
            }
            else
                System.out.println("Clientul nu există în baza de date sau ID-ul este greșit.");
        }
    }

    public void profilePageQuery() throws SQLException{
        Scanner in = new Scanner(System.in);

        System.out.println("_________ MENIU PAGINĂ DE PROFIL CLIENT _________");
        String sqlSearch = "SELECT * from clients WHERE idClient = ?";
        String sqlUpdateName = "UPDATE clients set name = ? WHERE idClient = ?";
        String sqlUpdateEmail = "UPDATE clients set email = ? WHERE idClient = ?";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmtSearch = con.prepareStatement(sqlSearch);
             PreparedStatement pstmtUpdateName = con.prepareStatement(sqlUpdateName);
             PreparedStatement pstmtUpdateEmail = con.prepareStatement(sqlUpdateEmail)) {
            System.out.println("Inserați ID-ul clientului:");
            int answer = in.nextInt();
            in.nextLine();
            pstmtSearch.setInt(1,answer);
            ResultSet rs = pstmtSearch.executeQuery();
            if(rs.next())
            {
                System.out.println("_________ PAGINĂ DE PROFIL CLIENT " + rs.getInt("idClient") + " _________");
                System.out.println("Nume complet: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Număr total de comenzi efectuate: " + rs.getInt("totalOrders"));
                System.out.println("Ultima vizită: " + rs.getDate("lastDateCheckedIn"));
                System.out.println("Comandă activă: " + (rs.getBoolean("activeOrder") ? "DA" : "NU"));

                System.out.println("");
                Calendar calendarAudit = Calendar.getInstance();
                java.util.Date  dateUtilAudit = calendarAudit.getTime();
                AuditLogs auditLogs = new AuditLogs();
                auditLogs.insertAudit("Afișare pagină client efectuată.",dateUtilAudit);
                System.out.println("Doriți să modificați datele de utilizator?");
                System.out.println("Introduceți NUME pentru a modifica numele, EMAIL pentru a modifica email-ul sau ANULARE pentru a termina.");
                String answerModify = in.nextLine();
                if(answerModify.equals("NUME")) {
                    System.out.println("Introduceți un nou nume de client: ");
                    String name = in.nextLine();
                    pstmtUpdateName.setString(1,name);
                    pstmtUpdateName.setInt(2,answer);
                    pstmtUpdateName.executeUpdate();
                    Calendar calendarAudit2 = Calendar.getInstance();
                    java.util.Date  dateUtilAudit2 = calendarAudit2.getTime();
                    auditLogs.insertAudit("Modificare nume client efectuată.",dateUtilAudit2);
                }
                else
                    if(answerModify.equals("EMAIL"))
                    {
                        System.out.println("Introduceți un nou email de client: ");
                        String email = in.nextLine();
                        pstmtUpdateEmail.setString(1,email);
                        pstmtUpdateEmail.setInt(2,answer);
                        pstmtUpdateEmail.executeUpdate();
                        Calendar calendarAudit2 = Calendar.getInstance();
                        java.util.Date  dateUtilAudit2 = calendarAudit2.getTime();
                        auditLogs.insertAudit("Modificare email client efectuată.",dateUtilAudit2);
                    }
                    else if(answerModify.equals("ANULARE"))
                    {

                    }else
                    System.out.println("Alegerea introdusă nu este disponibilă.");

            } else {
                System.out.println("Clientul introdus nu apare în baza de date sau ID-ul este greșit.");
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
        Calendar calendarAudit = Calendar.getInstance();
        java.util.Date  dateUtilAudit = calendarAudit.getTime();
        AuditLogs auditLogs = new AuditLogs();
        auditLogs.insertAudit("Ștergere client efectuată.",dateUtilAudit);
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
