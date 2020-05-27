package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.MediaSize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.internetshop.util.CustomAssertions.assertBigDecimalCompareValue;

import java.math.BigDecimal;


public class ProductTest {

	
    private static final String NAME = "Mr. Sparkle";
    private static final BigDecimal PRICE = BigDecimal.valueOf(1);

	@Test
    public void testProductName() throws Exception{
        //given
    	
        // when
        Product product = new Product(NAME, PRICE);
        
        // then
        assertEquals(NAME, product.getName());
    }
    
    @Test
    public void testProductPrice() throws Exception{
        //given
    	
        // when
        Product product = new Product(NAME, PRICE);
        
        // then
        assertBigDecimalCompareValue(product.getPrice(), PRICE);
    }

    @Test
    public void priceWithMaxDiscount(){
        //given
        Product product = new Product(NAME, PRICE);

        //when
        product.setDiscount(BigDecimal.ONE);

        //when then
        assertBigDecimalCompareValue(BigDecimal.ZERO, product.getPriceWithDiscount());
    }

    @Test
    public void priceWithMinDiscount(){
        //given
        Product product = new Product(NAME, PRICE);

        //when
        product.setDiscount(BigDecimal.ZERO);

        // then
        assertBigDecimalCompareValue(BigDecimal.ONE, product.getPriceWithDiscount());
    }

    @Test
    public void discountWithNoRounding(){
        // given
        Product product = new Product(NAME, PRICE); // 1 PLN

        // when
        product.setDiscount(BigDecimal.valueOf(0.25));

        // then
        assertBigDecimalCompareValue(product.getPriceWithDiscount(), BigDecimal.valueOf(0.75)); // 0.75 PLN
    }

    @Test
    public void discountWithRoundDown(){
        // given
        Product product = new Product(NAME, BigDecimal.valueOf(0.01)); // 0.01 PLN

        // when
        product.setDiscount(BigDecimal.valueOf(0.5));

        // then
        assertBigDecimalCompareValue(product.getPriceWithDiscount(), BigDecimal.valueOf(0.01)); // 0.01 PLN
    }

    @Test
    public void discountWithRoundUp(){
        // given
        Product product = new Product(NAME, BigDecimal.valueOf(0.39)); // 0.39 PLN

        // when
        product.setDiscount(BigDecimal.valueOf(0.5));

        // then
        assertBigDecimalCompareValue(product.getPriceWithDiscount(), BigDecimal.valueOf(0.2)); // 0.2 PLN
    }
}