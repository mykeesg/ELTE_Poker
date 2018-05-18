package client.frontend.components;

import network.PlayerState;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class PlayerView {
    public JPanel panel1;
    private JPanel hand;
    private JLabel buttonLabel;
    private JLabel nameLabel;
    private JLabel moneyLabel;

    public PlayerView(PlayerState player) {
        nameLabel.setText(player.name);
        moneyLabel.setText("$" + player.money);

        String buttonLabelText = " ";

        buttonLabel.setOpaque(true);
        if (player.isDealer) {
            buttonLabel.setBackground(Color.GRAY);
            buttonLabelText += "D ";
        }
        if (player.isBigBlind) {
            buttonLabel.setBackground(Color.RED);
            buttonLabelText += "BB ";
        }
        if (player.isSmallBlind) {
            buttonLabel.setBackground(Color.YELLOW);
            buttonLabelText += "SB ";
        }

        buttonLabel.setText(buttonLabelText);

        Arrays.stream(player.hand).forEach(card ->
                hand.add(new CardView(card))
        );

        IntStream.range(0, 2 - player.hand.length).forEach(i ->
                hand.add(new CardView(null))
        );
    }
}
