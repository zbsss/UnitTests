package pl.edu.agh.internetshop;

import java.math.BigDecimal;

public class OrderPriceFilter implements OrderFilter{
    private final BigDecimal price;

    public OrderPriceFilter(BigDecimal price){
        this.price = price;
    }

    @Override
    public boolean filter(Order order) {
        return order.getPriceWithDiscount().equals(price);
    }
}
