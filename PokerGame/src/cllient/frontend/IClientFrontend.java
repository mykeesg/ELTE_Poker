package cllient.frontend;

import cllient.frontend.model.EventSource;
import cllient.frontend.model.EventSourceT;
import cllient.frontend.model.GameState;

public interface IClientFrontend {
    public void updateState(GameState newState);

    public EventSource getFold();

    public EventSource getCall();

    public EventSourceT<Integer> getRaise();

    public EventSource getQuit();
}
