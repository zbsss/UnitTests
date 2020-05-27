package pl.edu.agh.internetshop;


public class CustomerNameFilter implements OrderFilter{
    private final String firstName;
    private final String lastName;

    public CustomerNameFilter(String firstName, String  lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean filter(Order order) {
        String firstName = order.getCustomer().getFirstName();
        String lastName = order.getCustomer().getLastName();

        return this.firstName.equals(firstName) && this.lastName.equals(lastName);
    }
}
