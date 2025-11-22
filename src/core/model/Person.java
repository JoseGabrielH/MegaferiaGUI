package core.model;

public abstract class Person implements Cloneable {
    
    protected final long id;
    protected String firstname;
    protected String lastname;

    public Person(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getFullname() {
        return firstname + " " + lastname;
    }
    
    @Override
    public abstract Object clone() throws CloneNotSupportedException;
}
