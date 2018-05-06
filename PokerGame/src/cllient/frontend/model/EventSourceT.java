package cllient.frontend.model;

import java.util.ArrayList;
import java.util.List;

public class EventSourceT<T> {
    private List<EventListenerT<T>> listeners = new ArrayList<>();

    public void addEventListener(EventListenerT<T> listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListenerT<T> listener) {
        listeners.remove(listener);
    }

    public void invoke(Object sender, T payload) {
        listeners.forEach(listener -> {
            if (listener != null) {
                listener.handleEvent(sender, payload);
            }
        });
    }
}
