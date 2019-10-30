package Interfaces;

import Exceptions.BasketException;
import ModelDomain.Event;

public interface IClient {
    void increaseNumberOfParticipants(Event event) throws BasketException;

}
