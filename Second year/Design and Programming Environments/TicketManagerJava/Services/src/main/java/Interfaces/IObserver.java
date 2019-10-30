package Interfaces;
import ModelDomain.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void update(List<Event> events) throws RemoteException;
}
