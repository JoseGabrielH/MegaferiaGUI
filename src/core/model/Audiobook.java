package core.model;

import java.util.ArrayList;

public class Audiobook extends Book {
    
    private int duration;
    private Narrator narrador;

    public Audiobook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, int duration, Narrator narrator) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.duration = duration;
        this.narrador = narrator;
        
        this.narrador.addBook(this);
    }

    public int getDuration() {
        return duration;
    }

    public Narrator getNarrador() {
        return narrador;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Audiobook cloned = new Audiobook(this.title, new ArrayList<>(this.authors), 
                                        this.isbn, this.genre, this.format, 
                                        this.value, this.publisher, this.duration, this.narrador);
        return cloned;
    }
    
}
