package core.model;

public class Manager extends Person {
    
    private Publisher publisher;

    public Manager(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Manager cloned = new Manager(this.id, this.firstname, this.lastname);
        cloned.publisher = this.publisher;
        return cloned;
    }
    
}
