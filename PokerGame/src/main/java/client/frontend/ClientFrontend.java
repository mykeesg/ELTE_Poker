package client.frontend;

import client.frontend.components.CardView;
import client.frontend.components.PlayerView;
import client.frontend.model.EventSource;
import client.frontend.model.EventSourceT;
import network.GameState;

import javax.swing.*;
import java.util.Arrays;

public class ClientFrontend implements IClientFrontend {
    private EventSource fold = new EventSource();
    private EventSource call = new EventSource();
    private EventSourceT<Integer> raise = new EventSourceT<>();
    private EventSource quit = new EventSource();

    private JFrame frame = new JFrame("Poker");
    private MainWindow mainWindow = new MainWindow();


    public ClientFrontend() {

    }

    public void startGame() {
        frame.setContentPane(mainWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWindow.fold.addActionListener(event -> fold.invoke(this));
        mainWindow.call.addActionListener(event -> call.invoke(this));
        mainWindow.raise.addActionListener(event -> raise.invoke(this, (int) mainWindow.raiseAmount.getValue()));
        mainWindow.exit.addActionListener(event -> quit.invoke(this));

        frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    public String logIn() {
        LoginWindow dialog = new LoginWindow();
        dialog.pack();
        dialog.setVisible(true);

        String name = dialog.textField1.getText();
        return name;
    }

    @Override
    public void updateState(GameState newState) {
        frame.setTitle("Poker (" + newState.currentPlayer.name + ")");

        mainWindow.potLabel.setText("POT $" + newState.pot);

        mainWindow.opponentsPanel.removeAll();
        newState.opponents.forEach(player ->
                mainWindow.opponentsPanel.add(new PlayerView(player).panel1)
        );
        mainWindow.tableCards.removeAll();
        newState.tableCards.forEach(card ->
                mainWindow.tableCards.add(new CardView(card))
        );
        mainWindow.currentPlayer.removeAll();
        mainWindow.currentPlayer.add(new PlayerView(newState.currentPlayer).panel1);

        mainWindow.fold.setEnabled(newState.canFold);
        mainWindow.call.setEnabled(newState.canCall);
        mainWindow.raise.setEnabled(newState.canRaise);

        frame.revalidate();
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
