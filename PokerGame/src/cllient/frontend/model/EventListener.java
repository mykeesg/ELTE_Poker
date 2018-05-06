package cllient.frontend.model;

@FunctionalInterface
public interface EventListener {
    void handleEvent(Object sender);
}

