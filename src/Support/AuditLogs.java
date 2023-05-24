package Support;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class AuditLogs
{
    public void insertAudit(String description, Date date) throws SQLException{
        String sql = "INSERT INTO logs (description,dateTime) VALUES (?, ?)";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, description);
                Timestamp timestamp = new Timestamp(date.getTime());
                pstmt.setTimestamp(2, timestamp);
                pstmt.executeUpdate();
        }
}

    public void selectAudit30() throws SQLException{
        String sql = "SELECT * FROM logs where dateTime > ? ";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,-30);
            java.util.Date  dateUtil = calendar.getTime();
            Timestamp timestamp = new Timestamp(dateUtil.getTime());
            pstmt.setTimestamp(1, timestamp);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                System.out.println(rs.getInt("idLog")+ ". " + rs.getString("description") + "  -- DateTime: " +rs.getTimestamp("dateTime"));

        }
    }

    public void selectAuditAll() throws SQLException{
        String sql = "SELECT * FROM logs";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                System.out.println(rs.getInt("idLog")+ ". " + rs.getString("description") + "  -- DateTime: " +rs.getTimestamp("dateTime"));

        }
    }

    public void selectAuditSession(Date dateTime) throws SQLException{
        String sql = "SELECT * FROM logs where dateTime > ? ";
        try (Connection con = DatabaseHandler.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            Timestamp timestamp = new Timestamp(dateTime.getTime());
            pstmt.setTimestamp(1, timestamp);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                System.out.println(rs.getInt("idLog")+ ". " + rs.getString("description") + "  -- DateTime: " +rs.getTimestamp("dateTime"));

        }
    }
}
