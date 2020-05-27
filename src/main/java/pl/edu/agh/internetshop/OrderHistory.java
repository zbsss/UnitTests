package pl.edu.agh.internetshop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderHistory {
    private List<Order> orders;

    public OrderHistory(List<Order> orders){
        this.orders = orders;
    }

    public List<Order> searchOrders(OrderFilter filter){
        List<Order> filteredOrders  = new ArrayList<>();
        for (Order order : orders){
            if(filter.filter(order))
                filteredOrders.add(order);
        }
        return filteredOrders;
    }

    public void add(Order order){
        this.orders.add(order);
    }

    public void addAll(List<Order> orders){
        this.orders.addAll(orders);
    }

    public List<Order> getOrderHistory(){
        return orders;
    }
}
