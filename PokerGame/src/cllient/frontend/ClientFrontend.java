package cllient.frontend;

import cllient.frontend.model.EventSource;
import cllient.frontend.model.EventSourceT;
import cllient.frontend.model.GameState;

import javax.swing.*;

public class ClientFrontend implements IClientFrontend {
    private EventSource fold = new EventSource();
    private EventSource call = new EventSource();
    private EventSourceT<Integer> raise = new EventSourceT<>();
    private EventSource quit = new EventSource();

    private JFrame frame = new JFrame("Poker");
    private MainWindow mainWindow = new MainWindow();


    public ClientFrontend() {
        SwingUtilities.invokeLater(() -> {
            frame.setContentPane(mainWindow.panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainWindow.fold.addActionListener(event -> fold.invoke(this));
            mainWindow.call.addActionListener(event -> call.invoke(this));
            mainWindow.raise.addActionListener(event -> raise.invoke(this, 42));
            mainWindow.exit.addActionListener(event -> quit.invoke(this));

            frame.pack();
            frame.setVisible(true);
        });
    }

    @Override
    public void updateState(GameState newState) {
        mainWindow.potLabel.setText("" + newState.pot);
        frame.validate();
        frame.repaint();
    }

    @Override
    public EventSource getFold() {
        return fold;
    }

    @Override
    public EventSource getCall() {
        return call;
    }

    @Override
    public EventSourceT<Integer> getRaise() {
        return raise;
    }

    @Override
    public EventSource getQuit() {
        return quit;
    }
}
