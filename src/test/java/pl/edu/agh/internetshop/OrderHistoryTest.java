package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class OrderHistoryTest {

    private OrderHistory history;

    private void setUpHistory(){
        Product iPhone = new Product("iPhone", BigDecimal.valueOf(200));
        Product iPad = new Product("iPad", BigDecimal.valueOf(400));
        Product iPod= new Product("iPod", BigDecimal.valueOf(50));
        Product iMac= new Product("iMac", BigDecimal.valueOf(1000));
        Product macBook= new Product("MacBook", BigDecimal.valueOf(800));

        Customer customer1 = new Customer("Jan", "Kowalski");
        Customer customer2 = new Customer("Tomasz", "Tomasiewicz");
        Customer customer3 = new Customer("Adnrzej", "Andrzejewicz");
        Customer customer4 = new Customer("Wojciech", "Wojciechowski");

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(Arrays.asList(iPhone, iPad), customer1));
        orders.add(new Order(Arrays.asList(macBook), customer1));
        orders.add(new Order(Arrays.asList(iPhone, iPad, iMac), customer2));
        orders.add(new Order(Arrays.asList(iPod, iPhone), customer3));
        orders.add(new Order(Arrays.asList(macBook, iMac), customer4));
        orders.add(new Order(Arrays.asList(iPhone, iPad, iMac, macBook, iPod), customer3));

        history = new OrderHistory(orders);
    }

    private void print(List<Order> orders){
        System.out.println("--------------------------------");
        for(Order order : orders){
            StringBuilder builder = new StringBuilder();
            builder.append(order.getCustomer().toString() + " ");
            builder.append(order.getPriceWithDiscount() + " ");
            for(Product product : order.getProducts()){
                builder.append(product.getName()+ " ");
            }
            System.out.println(builder);
        }
        System.out.println("--------------------------------");
    }

    @Test
    public void test(){
        setUpHistory();

        print(history.getOrderHistory());
        OrderFilter nameFilter = new CustomerNameFilter("Jan", "Kowalski");
        print(history.searchOrders(nameFilter));

        OrderFilter productFilter = new ProductNameFilter("iPhone");
        print(history.searchOrders(productFilter));

        OrderFilter aggregateFilter = new AggregateFilter(Arrays.asList(nameFilter, productFilter));
        print(history.searchOrders(aggregateFilter));
    }

    // Todo
    // Proste dodwanie i sprawdzanie czy wszystko jest
    // Dla każdego filtra po jednym teście + jeden dla zaagregowanego
}
