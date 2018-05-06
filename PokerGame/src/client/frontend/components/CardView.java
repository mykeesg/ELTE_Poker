package client.frontend.components;

import model.Card;
import model.Deck;
import model.Rank;
import model.Suit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CardView extends JLabel {

    private static Map<Card, Image> images = new HashMap<>();
    private static Image backImage;

    private static Image loadAndResizeImage(String path) {
        File input = new File(path);
        Image scaledImage = null;
        try {
            BufferedImage image = ImageIO.read(input);
            scaledImage = image.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Could not find card image at " + path);
            e.printStackTrace();
        }
        return scaledImage;
    }

    static {
        Deck deck = new Deck();
        Stream.generate(deck::getTopCard).limit(52).forEach(card -> {
            String path;
            Rank rank = card.getRank();
            Suit suit = card.getSuit();
            path = "./src/client/frontend/resources/" + ("" + rank.asChar()).replace("T", "10") + suit.toString().substring(0, 1) + ".png";
            Image image = loadAndResizeImage(path);
            images.put(card, image);
        });

        String path = "./src/client/frontend/resources/red_back.png";
        backImage = loadAndResizeImage(path);
    }

    public CardView(model.Card card) {
        super();
        setSize(80, 120);
        if (card != null) {
            setIcon(new ImageIcon(images.get(card)));
        } else {
            setIcon(new ImageIcon(backImage));
        }
    }
}
