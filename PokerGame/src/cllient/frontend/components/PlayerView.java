package cllient.frontend.components;

import cllient.frontend.model.PlayerState;

import javax.swing.*;
import java.util.Arrays;

public class PlayerView {
    public JPanel panel1;
    private JPanel hand;
    private JLabel buttonLabel;
    private JLabel nameLabel;
    private JLabel moneyLabel;

    public PlayerView(PlayerState player) {
        nameLabel.setText(player.name);
        moneyLabel.setText("$" + player.money);
        buttonLabel.setText("");
        if (player.isDealer) {
            buttonLabel.setText("D");
        }
        if (player.isBigBlind) {
            buttonLabel.setText("BB");
        }
        if (player.isSmallBlind) {
            buttonLabel.setText("SB");
        }

        Arrays.stream(player.hand).forEach(card ->
                hand.add(new CardView(card))
        );
    }
}
