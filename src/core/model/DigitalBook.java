/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model;

import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class DigitalBook extends Book {
    
    private boolean hasHyperlink;
    private String hyperlink;

    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hasHyperlink = false;
        this.hyperlink = null;
    }
    
    public DigitalBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, String hyperlink) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.hasHyperlink = true;
        this.hyperlink = hyperlink;
    }

    public boolean hasHyperlink() {
        return hasHyperlink;
    }
    
    public String getHyperlink() {
        return hyperlink;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        DigitalBook cloned;
        if (this.hasHyperlink) {
            cloned = new DigitalBook(this.title, new ArrayList<>(this.authors), 
                                    this.isbn, this.genre, this.format, 
                                    this.value, this.publisher, this.hyperlink);
        } else {
            cloned = new DigitalBook(this.title, new ArrayList<>(this.authors), 
                                    this.isbn, this.genre, this.format, 
                                    this.value, this.publisher);
        }
        return cloned;
    }
    
}
