package core.model;

import java.util.ArrayList;

public class Narrator extends Person {
    
    private ArrayList<Audiobook> books;

    public Narrator(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
        this.books = new ArrayList<>();
    }
    
    public int getBookQuantity() {
        return this.books.size();
    }
    
    public void addBook(Audiobook book) {
        this.books.add(book);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Narrator cloned = new Narrator(this.id, this.firstname, this.lastname);
        cloned.books = new ArrayList<>(this.books);
        return cloned;
    }
    
}
