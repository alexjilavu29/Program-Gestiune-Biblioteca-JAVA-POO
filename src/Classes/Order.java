package Classes;

import java.sql.Date;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Order {
    int idOrder;
    int idClient;
    Date outDate;
    int numberOfBooks;
    Date returnDate;
    Boolean completed;
    Boolean pastDueTime;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getPastDueTime() {
        return pastDueTime;
    }

    public void setPastDueTime(Boolean pastDueTime) {
        this.pastDueTime = pastDueTime;
    }

    public Order() {
        this.idOrder = -1;
        this.idClient = -1;
        this.outDate = null;
        this.numberOfBooks = 0;
        this.returnDate = null;
        this.completed = false;
        this.pastDueTime = false;
    }
    public Order(int idOrder, int idClient, Date outDate, int numberOfBooks, Date returnDate, Boolean completed, Boolean pastDueTime) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.outDate = outDate;
        this.numberOfBooks = numberOfBooks;
        this.returnDate = returnDate;
        this.completed = completed;
        this.pastDueTime = pastDueTime;
    }

    public Order(Order o) {
        this.idOrder = o.idOrder;
        this.idClient = o.idClient;
        this.outDate = o.outDate;
        this.numberOfBooks = o.numberOfBooks;
        this.returnDate = o.returnDate;
        this.completed = o.completed;
        this.pastDueTime = o.pastDueTime;
    }

    @Override
    public String toString() {
        return "Classes.Order{" +
                "idOrder=" + idOrder +
                ", idClient=" + idClient +
                ", outDate=" + outDate +
                ", numberOfBooks=" + numberOfBooks +
                ", returnDate=" + returnDate +
                ", completed=" + completed +
                ", pastDueTime=" + pastDueTime +
                '}';
    }
}
