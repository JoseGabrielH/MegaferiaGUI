package core.model;

import java.util.ArrayList;

public class PrintedBook extends Book {
    
    private int pages;
    private int copies;

    public PrintedBook(String title, ArrayList<Author> authors, String isbn, String genre, String format, double value, Publisher publisher, int pages, int copies) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.pages = pages;
        this.copies = copies;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        PrintedBook cloned = new PrintedBook(this.title, new ArrayList<>(this.authors), 
                                            this.isbn, this.genre, this.format, 
                                            this.value, this.publisher, this.pages, this.copies);
        return cloned;
    }
    
}
