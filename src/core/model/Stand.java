package core.model;

import java.util.ArrayList;

public class Stand implements Cloneable {
    
    private long id;
    private double price;
    private ArrayList<Publisher> publishers;

    public Stand(long id, double price) {
        this.id = id;
        this.price = price;
        this.publishers = new ArrayList<>();
    }
    
    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }
    
    public int getPublisherQuantity() {
        return this.publishers.size();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Stand cloned = new Stand(this.id, this.price);
        cloned.publishers = new ArrayList<>(this.publishers);
        return cloned;
    }
    
}
