package Interfaces;

import Domain.Event;

import java.util.List;

public interface IObserver {
    void update(List<Event> events);
}
