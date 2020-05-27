package pl.edu.agh.internetshop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static pl.edu.agh.internetshop.util.CustomAssertions.assertBigDecimalCompareValue;

public class OrderTest {
	private Customer CUSTOMER = mock(Customer.class);

	private Order getOrderWithMockedProduct() {
		Product product = mock(Product.class);
		return new Order(Collections.singletonList(product), CUSTOMER);
	}

	@Test
	public void testGetProductThroughOrder() {
		// given
		Product expectedProduct = mock(Product.class);
		Order order = new Order(Collections.singletonList(expectedProduct), CUSTOMER);

		// when
		List<Product> actualProduct = order.getProducts();

		// then
		assertSame(expectedProduct, actualProduct.get(0));
	}

	@Test
	public void testSetShipment() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();
		Shipment expectedShipment = mock(Shipment.class);

		// when
		order.setShipment(expectedShipment);

		// then
		assertSame(expectedShipment, order.getShipment());
	}

	@Test
	public void testShipmentWithoutSetting() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();

		// when

		// then
		assertNull(order.getShipment());
	}

	@Test
	public void testGetPrice() throws Exception {
		// given
		BigDecimal expectedProductPrice = BigDecimal.valueOf(1000);
		Product product = mock(Product.class);
		given(product.getPriceWithDiscount()).willReturn(expectedProductPrice);
		Order order = new Order(product, CUSTOMER);

		// when
		BigDecimal actualProductPrice = order.getPrice();

		// then
		assertBigDecimalCompareValue(expectedProductPrice, actualProductPrice);
	}

	private Order getOrderWithCertainProductPrice(double productPriceValue) {
		BigDecimal productPrice = BigDecimal.valueOf(productPriceValue);
		Product product = mock(Product.class);
		given(product.getPriceWithDiscount()).willReturn(productPrice);
		return new Order(product, CUSTOMER);
	}

	@Test
	public void testPriceWithTaxesWithoutRoundUp() {
		// given

		// when
		Order order = getOrderWithCertainProductPrice(2); // 2 PLN

		// then
		assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(2.46)); // 2.46 PLN
	}

	@Test
	public void testPriceWithTaxesWithRoundDown() {
		// given

		// when
		Order order = getOrderWithCertainProductPrice(0.01); // 0.01 PLN

		// then
		assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(0.01)); // 0.01 PLN
																							
	}

	@Test
	public void testPriceWithTaxesWithRoundUp() {
		// given

		// when
		Order order = getOrderWithCertainProductPrice(0.03); // 0.03 PLN

		// then
		assertBigDecimalCompareValue(order.getPriceWithTaxes(), BigDecimal.valueOf(0.04)); // 0.04 PLN
																							
	}

	@Test
	public void testSetShipmentMethod() {
		// given
		Order order = getOrderWithMockedProduct();
		ShipmentMethod surface = mock(SurfaceMailBus.class);

		// when
		order.setShipmentMethod(surface);

		// then
		assertSame(surface, order.getShipmentMethod());
	}

	@Test
	public void testSending() {
		// given
		Order order = getOrderWithMockedProduct();
		SurfaceMailBus surface = mock(SurfaceMailBus.class);
		Shipment shipment = mock(Shipment.class);
		given(shipment.isShipped()).willReturn(true);

		// when
		order.setShipmentMethod(surface);
		order.setShipment(shipment);
		order.send();

		// then
		assertTrue(order.isSent());
	}

	@Test
	public void testIsSentWithoutSending() {
		// given
		Order order = getOrderWithMockedProduct();
		Shipment shipment = mock(Shipment.class);
		given(shipment.isShipped()).willReturn(true);

		// when

		// then
		assertFalse(order.isSent());
	}

	@Test
	public void testWhetherIdExists() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();

		// when

		// then
		assertNotNull(order.getId());
	}

	@Test
	public void testSetPaymentMethod() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();
		PaymentMethod paymentMethod = mock(MoneyTransferPaymentTransaction.class);

		// when
		order.setPaymentMethod(paymentMethod);

		// then
		assertSame(paymentMethod, order.getPaymentMethod());
	}

	@Test
	public void testPaying() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();
		PaymentMethod paymentMethod = mock(MoneyTransferPaymentTransaction.class);
		given(paymentMethod.commit(any(MoneyTransfer.class))).willReturn(true);
		MoneyTransfer moneyTransfer = mock(MoneyTransfer.class);
		given(moneyTransfer.isCommitted()).willReturn(true);

		// when
		order.setPaymentMethod(paymentMethod);
		order.pay(moneyTransfer);

		// then
		assertTrue(order.isPaid());
	}

	@Test
	public void testIsPaidWithoutPaying() throws Exception {
		// given
		Order order = getOrderWithMockedProduct();

		// when

		// then
		assertFalse(order.isPaid());
	}

	@Test
	public void productsListIsNull(){
		//given
		List<Product> products = null;

		//when then
		assertThrows(IllegalArgumentException.class, () -> new Order(products, CUSTOMER));
	}

	@Test
	public void productsListIsEmpty(){
		//given
		List<Product> products = new ArrayList<>();

		// when then
		assertThrows(IllegalArgumentException.class, ()->new Order(products, CUSTOMER));
	}

	@Test
	public void getMultipleProductsThroughOrder(){
		//given
		Product product1 = mock(Product.class);
		Product product2 = mock(Product.class);
		Order order = new Order(Arrays.asList(product1, product2), CUSTOMER);

		//when
		List<Product> productList = order.getProducts();

		//then
		assertEquals(product1, productList.get(0));
		assertEquals(product2, productList.get(1));
		assertEquals(2, productList.size());
	}


	@Test
	public void addNullProductToOrder(){
		//given
		Order order = new Order(CUSTOMER);

		//when
		assertThrows(IllegalArgumentException.class, ()->order.addProduct(null));
	}

	//todo

	@Test
	public void testMaxDiscount(){
		// given
		Order order = getOrderWithCertainProductPrice(1);

		// when
		order.setDiscount(BigDecimal.ONE);

		// then
		assertBigDecimalCompareValue(BigDecimal.ZERO, order.getPriceWithDiscount());
	}

	@Test
	public void testMinDiscount(){
		// given
		Order order = getOrderWithCertainProductPrice(1);

		//when
		order.setDiscount(BigDecimal.ZERO);

		//then
		assertBigDecimalCompareValue(order.getPriceWithTaxes(), order.getPriceWithDiscount());
	}

	@Test
	public void discountWithNoRounding(){
		// given
		Order order = getOrderWithCertainProductPrice(2); // 2 PLN

		// when
		order.setDiscount(BigDecimal.valueOf(0.5));

		// then
		assertBigDecimalCompareValue(order.getPriceWithDiscount(), BigDecimal.valueOf(1.23)); // 1.23 PLN
	}

	@Test
	public void discountWithRoundDown(){
		// given
		Order order = getOrderWithCertainProductPrice(0.01); // 0.01 PLN

		// when
		order.setDiscount(BigDecimal.valueOf(0.5));

		// then
		assertBigDecimalCompareValue(order.getPriceWithDiscount(), BigDecimal.valueOf(0.01)); // 0.01 PLN
	}

	@Test
	public void discountWithRoundUp(){
		// given
		Order order = getOrderWithCertainProductPrice(0.03); // 0.03 PLN

		// when
		order.setDiscount(BigDecimal.valueOf(0.25));

		// then
		assertBigDecimalCompareValue(order.getPriceWithDiscount(), BigDecimal.valueOf(0.03)); // 0.03 PLN
	}

}
