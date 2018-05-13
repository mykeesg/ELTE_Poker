package client.frontend;

import client.frontend.model.EventSource;
import client.frontend.model.EventSourceT;
import client.frontend.model.GameState;

public interface IClientFrontend {
    public void updateState(GameState newState);

    public EventSource getFold();

    public EventSource getCall();

    public EventSourceT<Integer> getRaise();

    public EventSource getQuit();
}
