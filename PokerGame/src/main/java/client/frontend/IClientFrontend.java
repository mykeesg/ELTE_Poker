package client.frontend;

import client.frontend.model.EventSource;
import client.frontend.model.EventSourceT;
import network.GameState;

public interface IClientFrontend {
    public void updateState(GameState newState);

    public EventSource getFold();

    public EventSource getCall();

    public EventSourceT<Integer> getRaise();

    public EventSource getQuit();

    public EventSourceT<String> getChangeName();
}
