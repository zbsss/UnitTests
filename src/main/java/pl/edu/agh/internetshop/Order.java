package pl.edu.agh.internetshop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;


public class Order implements Discountable{
    private static final BigDecimal TAX_VALUE = BigDecimal.valueOf(1.23);
	private final UUID id;
    private final List<Product> products;
    private boolean paid;
    private Shipment shipment;
    private ShipmentMethod shipmentMethod;
    private PaymentMethod paymentMethod;
    private BigDecimal discount = BigDecimal.ZERO;
    private final Customer customer;

    public Order(Customer customer) {
        this.customer = customer;
        this.products = new ArrayList<>();
        id = UUID.randomUUID();
        paid = false;
    }

    public Order(Product product, Customer customer) {
        this.customer = customer;
        this.products = new ArrayList<>();
        this.addProduct(product);

        id = UUID.randomUUID();
        paid = false;
    }

    public Order(List<Product> products,Customer customer) {
        if(products == null || products.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.products = new ArrayList<>();
        this.products.addAll(products);

        this.customer = customer;

        id = UUID.randomUUID();
        paid = false;
    }

    public UUID getId() {
        return id;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSent() {
        return shipment != null && shipment.isShipped();
    }

    public boolean isPaid() { return paid; }

    public Shipment getShipment() {
        return shipment;
    }

    public BigDecimal getPrice() {
        return this.products.stream().map(Product::getPriceWithDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getPriceWithTaxes(){
        return getPrice().multiply(TAX_VALUE).setScale(Product.PRICE_PRECISION, Product.ROUND_STRATEGY);
    }

    @Override
    public BigDecimal getPriceWithDiscount(){
        return getPriceWithTaxes().multiply(BigDecimal.ONE.subtract(discount)).setScale(Product.PRICE_PRECISION, Product.ROUND_STRATEGY);
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public ShipmentMethod getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(ShipmentMethod shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public void send() {
        boolean sentSuccessful = getShipmentMethod().send(shipment, shipment.getSenderAddress(), shipment.getRecipientAddress());
        shipment.setShipped(sentSuccessful);
    }

    public void pay(MoneyTransfer moneyTransfer) {
        moneyTransfer.setCommitted(getPaymentMethod().commit(moneyTransfer));
        paid = moneyTransfer.isCommitted();
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void addProduct(Product product){
        if(product == null)
            throw new IllegalArgumentException();
        this.products.add(product);
    }

    @Override
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }

    public Customer getCustomer() {
        return customer;
    }

}
