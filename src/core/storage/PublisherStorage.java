package core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import core.model.Publisher;
import core.model.Observer;

public class PublisherStorage {
    
    private static PublisherStorage instance;
    private List<Publisher> publishers;
    private List<Observer> observers;
    
    private PublisherStorage() {
        this.publishers = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    public static PublisherStorage getInstance() {
        if (instance == null) {
            instance = new PublisherStorage();
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
    
    public void addPublisher(Publisher publisher) {
        this.publishers.add(publisher);
        notifyObservers();
    }
    
    public Publisher getPublisherByNit(String nit) {
        return publishers.stream()
            .filter(p -> p.getNit().equals(nit))
            .findFirst()
            .orElse(null);
    }
    
    public List<Publisher> getAllPublishers() {
        return new ArrayList<>(publishers.stream()
            .sorted((p1, p2) -> p1.getNit().compareTo(p2.getNit()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsPublisherByNit(String nit) {
        return publishers.stream().anyMatch(p -> p.getNit().equals(nit));
    }
    
    public void clear() {
        this.publishers.clear();
    }
}
