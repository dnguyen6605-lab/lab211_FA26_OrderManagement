
package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable{
    private static final long serialVersionUID = 1L;

    private int orderId;
    private String cusId;
    private String menuId;
    private int numOfTables;
    private LocalDate eventDate;
    private double totalCost;

    public Order() {
    }

    public Order(int orderId, String cusId, String menuId, int numOfTables, LocalDate eventDate, double totalCost) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.menuId = menuId;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
        this.totalCost = totalCost;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", cusId='" + cusId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", numOfTables=" + numOfTables +
                ", eventDate=" + eventDate +
                ", totalCost=" + totalCost +
                '}';
    }
}
