package pl.edu.agh.internetshop;

public class ProductNameFilter  implements OrderFilter{
    private String productName;

    public ProductNameFilter(String productName){
        this.productName = productName;
    }

    @Override
    public boolean filter(Order order) {
        for (Product product : order.getProducts()) {
            if (product.getName().equals(productName))
                return true;
        }
        return false;
    }
}
