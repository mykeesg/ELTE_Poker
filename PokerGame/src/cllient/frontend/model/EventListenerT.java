package cllient.frontend.model;

@FunctionalInterface
public interface EventListenerT<T> {
    void handleEvent(Object sender, T payload);
}
