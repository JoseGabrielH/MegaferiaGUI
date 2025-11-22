package core.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import core.model.Stand;
import core.model.Observer;

public class StandStorage {
    
    private static StandStorage instance;
    private List<Stand> stands;
    private List<Observer> observers;
    
    private StandStorage() {
        this.stands = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    public static StandStorage getInstance() {
        if (instance == null) {
            instance = new StandStorage();
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
    
    public void addStand(Stand stand) {
        this.stands.add(stand);
        notifyObservers();
    }
    
    public Stand getStandById(long id) {
        for (Stand stand : stands) {
            if (stand.getId() == id) {
                return stand;
            }
        }
        return null;
    }
    
    public List<Stand> getAllStands() {
        return new ArrayList<>(stands.stream()
            .sorted((s1, s2) -> Long.compare(s1.getId(), s2.getId()))
            .collect(Collectors.toList()));
    }
    
    public boolean existsStandById(long id) {
        return stands.stream().anyMatch(s -> s.getId() == id);
    }
    
    public void clear() {
        this.stands.clear();
    }
}
