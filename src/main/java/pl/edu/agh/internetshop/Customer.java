package pl.edu.agh.internetshop;

public class Customer {
    private final String firstName;
    private final String lastName;
    private Address address;

    public Customer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return  firstName + ' ' + lastName;
    }
}
