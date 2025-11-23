package core.model;

import java.util.ArrayList;

public class Publisher implements Cloneable {
    
    private final String nit;
    private String name;
    private String address;
    private Manager manager;
    private ArrayList<Book> books;
    private ArrayList<Stand> stands;

    public Publisher(String nit, String name, String address, Manager manager) {
        this.nit = nit;
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.books = new ArrayList<>();
        this.stands = new ArrayList<>();
        
        this.manager.setPublisher(this);
    }

    public String getNit() {
        return nit;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Manager getManager() {
        return manager;
    }
    
    public int getStandQuantity() {
        return this.stands.size();
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }
    
    public void addStand(Stand stand) {
        this.stands.add(stand);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Publisher cloned = new Publisher(this.nit, this.name, this.address, this.manager);
        cloned.books = new ArrayList<>(this.books);
        cloned.stands = new ArrayList<>(this.stands);
        return cloned;
    }
    
}
