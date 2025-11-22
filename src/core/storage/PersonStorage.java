/*
 * Almacenamiento de Personas (Authors, Managers, Narrators)
 */
package core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import core.model.Author;
import core.model.Manager;
import core.model.Narrator;
import core.model.Observer;
import core.model.Person;

/**
 *
 * @author edangulo
 */
public class PersonStorage {
    
    private static PersonStorage instance;
    private List<Author> authors;
    private List<Manager> managers;
    private List<Narrator> narrators;
    private List<Observer> observers;
    
    private PersonStorage() {
        this.authors = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.narrators = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    public static PersonStorage getInstance() {
        if (instance == null) {
            instance = new PersonStorage();
        }
        return instance;
    }
    
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
    
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    
    // Authors
    public void addAuthor(Author author) {
        this.authors.add(author);
        notifyObservers();
    }
    
    public Author getAuthorById(long id) {
        return authors.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.stream()
            .sorted((a1, a2) -> Long.compare(a1.getId(), a2.getId()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsAuthorById(long id) {
        return authors.stream().anyMatch(a -> a.getId() == id);
    }
    
    // Managers
    public void addManager(Manager manager) {
        this.managers.add(manager);
        notifyObservers();
    }
    
    public Manager getManagerById(long id) {
        return managers.stream()
            .filter(m -> m.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers.stream()
            .sorted((m1, m2) -> Long.compare(m1.getId(), m2.getId()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsManagerById(long id) {
        return managers.stream().anyMatch(m -> m.getId() == id);
    }
    
    // Narrators
    public void addNarrator(Narrator narrator) {
        this.narrators.add(narrator);
        notifyObservers();
    }
    
    public Narrator getNarratorById(long id) {
        return narrators.stream()
            .filter(n -> n.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public List<Narrator> getAllNarrators() {
        return new ArrayList<>(narrators.stream()
            .sorted((n1, n2) -> Long.compare(n1.getId(), n2.getId()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsNarratorById(long id) {
        return narrators.stream().anyMatch(n -> n.getId() == id);
    }
    
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        persons.addAll(authors);
        persons.addAll(managers);
        persons.addAll(narrators);
        return persons.stream()
            .sorted((p1, p2) -> Long.compare(p1.getId(), p2.getId()))
            .collect(Collectors.toList());
    }
    
    public void clear() {
        this.authors.clear();
        this.managers.clear();
        this.narrators.clear();
    }
}
