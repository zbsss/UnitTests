package pl.edu.agh.internetshop;

import java.math.BigDecimal;

public interface Discountable {
    void setDiscount(BigDecimal discount);
    BigDecimal getDiscount();
    BigDecimal getPriceWithDiscount();
}
