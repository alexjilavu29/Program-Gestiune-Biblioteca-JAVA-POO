package Classes;

import java.sql.*;

public class Client extends Person
{
    String email;
    int totalOrders;
    Date lastDateCheckedIn;
    Boolean activeOrder;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Date getLastDateCheckedIn() {
        return lastDateCheckedIn;
    }

    public void setLastDateCheckedIn(Date lastDateCheckedIn) {
        this.lastDateCheckedIn = lastDateCheckedIn;
    }

    public Boolean getActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(Boolean activeOrder) {
        this.activeOrder = activeOrder;
    }

    public Client() {
        super();
        this.id = -1;
        this.name = "N/A";
        this.email = "N/A";
        this.totalOrders = 0;
        this.lastDateCheckedIn = null;
        this.activeOrder = false;
    }
    public Client(int idClient, String name, String email, int totalOrders, Date lastDateCheckedIn, Boolean activeOrder) {
        this.id = idClient;
        this.name = name;
        this.email = email;
        this.totalOrders = totalOrders;
        this.lastDateCheckedIn = lastDateCheckedIn;
        this.activeOrder = activeOrder;
    }

    @Override
    public String toString() {
        return "Classes.Client{" +
                "idClient=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", totalOrders=" + totalOrders +
                ", lastDateCheckedIn=" + lastDateCheckedIn +
                ", activeOrder=" + activeOrder +
                '}';
    }

}
