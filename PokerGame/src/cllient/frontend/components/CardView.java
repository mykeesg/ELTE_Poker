package cllient.frontend.components;

import model.Rank;
import model.Suit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardView extends JLabel {

    private BufferedImage image;

    public CardView(model.Card card) {
        super();
        try {
            String path;
            if (card != null) {
                Rank rank = card.getRank();
                Suit suit = card.getSuit();
                // TODO remove
                path = "./src/cllient/frontend/resources/" + ("" + rank.asChar()).replace("T", "10") + suit.toString().substring(0, 1) + ".png";
            } else {
                path = "./src/cllient/frontend/resources/red_back.png";
            }
            System.out.println(path);
            File input = new File(path);
            System.out.println(input.exists());
            image = ImageIO.read(input);
        } catch (IOException ex) {
        }
        setSize(80, 120);
        setIcon(new ImageIcon(image.getScaledInstance(80, 120, java.awt.Image.SCALE_SMOOTH)));
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(image, 0, 0, 80, 120, this); // see javadoc for more info on the parameters
//    }

}