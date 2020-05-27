package pl.edu.agh.internetshop;

import java.math.BigDecimal;

public class Product implements Discountable{
	
	public static final int PRICE_PRECISION = 2;
	public static final int ROUND_STRATEGY = BigDecimal.ROUND_HALF_UP;
	
    private final String name;
    private final BigDecimal price;
    private BigDecimal discount = BigDecimal.ZERO;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.price.setScale(PRICE_PRECISION, ROUND_STRATEGY);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public BigDecimal getDiscount() {
        return this.discount;
    }

    @Override
    public BigDecimal getPriceWithDiscount() {
        return getPrice().multiply(BigDecimal.ONE.subtract(discount)).setScale(Product.PRICE_PRECISION, Product.ROUND_STRATEGY);
    }
}
