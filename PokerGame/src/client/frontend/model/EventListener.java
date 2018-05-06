package client.frontend.model;

@FunctionalInterface
public interface EventListener {
    void handleEvent(Object sender);
}

