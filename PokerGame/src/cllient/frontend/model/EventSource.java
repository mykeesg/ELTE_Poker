package cllient.frontend.model;

import java.util.ArrayList;
import java.util.List;

public class EventSource {
    private List<EventListener> listeners = new ArrayList<>();

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }

    public void invoke(Object sender) {
        listeners.forEach(listener -> {
            if (listener != null) {
                listener.handleEvent(sender);
            }
        });
    }
}

