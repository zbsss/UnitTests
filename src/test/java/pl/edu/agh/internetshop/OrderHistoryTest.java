package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class OrderHistoryTest {
    private List<Product> PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product("iPhone", BigDecimal.valueOf(200)),
            new Product("iPad", BigDecimal.valueOf(400)),
            new Product("iPod", BigDecimal.valueOf(50)),
            new Product("iMac", BigDecimal.valueOf(1000)),
            new Product("MacBook", BigDecimal.valueOf(800))
    ));
    private List<Customer> CUSTOMERS = new ArrayList<>(Arrays.asList(
            new Customer("Jan", "Kowalski"),
            new Customer("Tomasz", "Tomasiewicz"),
            new Customer("Adnrzej", "Andrzejewicz"),
            new Customer("Wojciech", "Wojciechowski")
    ));

    private OrderHistory getOrderHistory(){
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(Arrays.asList(PRODUCTS.get(0), PRODUCTS.get(1)), CUSTOMERS.get(0)));
        orders.add(new Order(Arrays.asList(PRODUCTS.get(4)), CUSTOMERS.get(0)));
        orders.add(new Order(Arrays.asList(PRODUCTS.get(0), PRODUCTS.get(1), PRODUCTS.get(3)), CUSTOMERS.get(1)));
        orders.add(new Order(Arrays.asList(PRODUCTS.get(2), PRODUCTS.get(0)), CUSTOMERS.get(2)));
        orders.add(new Order(Arrays.asList(PRODUCTS.get(4), PRODUCTS.get(3)), CUSTOMERS.get(3)));
        orders.add(new Order(Arrays.asList(PRODUCTS.get(0), PRODUCTS.get(1), PRODUCTS.get(3), PRODUCTS.get(4), PRODUCTS.get(2)), CUSTOMERS.get(2)));

        return new OrderHistory(orders);
    }


    @Test
    public void addOrderToHistory(){
        // given
        Order order = mock(Order.class);
        OrderHistory history = new OrderHistory(Arrays.asList(order));

        // when
        List<Order> orderList = history.getOrderHistory();

        // then
        assertEquals(1, orderList.size());
        assertSame(order, orderList.get(0));
    }

    @Test
    public void searchByCustomerName(){
        // given
        OrderHistory history = getOrderHistory();
        OrderFilter filter = new CustomerNameFilter("Tomasz", "Tomasiewicz");

        // when
        List<Order> result = history.findOrders(filter);

        // then
        assertEquals(1, result.size());
        assertSame(CUSTOMERS.get(1), result.get(0).getCustomer());
    }

    @Test
    public void searchByProductName(){
        // given
        OrderHistory history = getOrderHistory();
        OrderFilter filter = new ProductNameFilter("iPod");

        // when
        List<Order> result = history.findOrders(filter);

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(history.getOrderHistory().get(3)));
        assertTrue(result.contains(history.getOrderHistory().get(5)));
    }

    @Test
    public void aggregateSearch(){
        // given
        OrderHistory history = getOrderHistory();
        OrderFilter filter1 = new ProductNameFilter("MacBook");
        OrderFilter filter2 = new CustomerNameFilter("Jan", "Kowalski");
        OrderFilter aggregateFilter = new AggregateFilter(Arrays.asList(filter1, filter2));

        // when
        List<Order> result = history.findOrders(aggregateFilter);

        // then
        assertEquals(1, result.size());
        assertSame(history.getOrderHistory().get(1), result.get(0));
    }

}
