package Interfaces;

import Domain.Event;
import Exceptions.BasketException;
import Utils.EventChangeEvent;

public interface IClient {
    void increaseNumberOfParticipants(Event event) throws BasketException;

}
