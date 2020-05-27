package pl.edu.agh.internetshop;

import java.util.List;

public class AggregateFilter implements OrderFilter {
    private List<OrderFilter> filters;

    public AggregateFilter(List<OrderFilter> filters) {
        this.filters = filters;
    }

    @Override
    public boolean filter(Order order) {
        for (OrderFilter filter :
                filters){
                if(!filter.filter(order))
                    return false;
        }
        return true;
    }
}
