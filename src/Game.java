import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;




/**
 * The Game class represents the main game controller for creature selection and gameplay.
 * It provides methods for starting the game, choosing a starting creature, and creating a graphical user interface.
 *
 */
public class Game {
    private final Inventory inventory;
    private GameManager gameManager;
    private JFrame frame;


    /**
     * Constructs a new game instance with an empty inventory.
     */
    public Game() {
        this.inventory = new Inventory();


    }

    /**
     * Starts the game by displaying the start screen.
     */
    public void start() {

        StartScreen startScreen = new StartScreen(this);
        startScreen.setVisible(true);
    }

    /**
     * Opens a window for the player to choose their starting creature from a list of options.
     */
    public void chooseStartingCreature() {
        SwingUtilities.invokeLater(() -> {

            Map<String, String> creatureFamilies = Map.of(
                    "Strawander", "A FAMILY", "Chocowool", "B FAMILY", "Parfwit", "C FAMILY",
                    "Brownisaur", "D FAMILY", "Frubat", "E FAMILY", "Malts", "F FAMILY",
                    "Squirpie", "G FAMILY", "Chocolite", "H FAMILY", "Oshacone", "I FAMILY"
            );

            String[] creatureNames = {
                    "Strawander", "Chocowool", "Parfwit", "Brownisaur", "Frubat",
                    "Malts", "Squirpie", "Chocolite", "Oshacone"
            };

            CreatureType[] types = {
                    CreatureType.FIRE, CreatureType.FIRE, CreatureType.FIRE,
                    CreatureType.NATURE, CreatureType.NATURE, CreatureType.NATURE,
                    CreatureType.WATER, CreatureType.WATER, CreatureType.WATER
            };


            int rows = 3;
            int cols = 3;
            int imageWidth = 100;
            int imageHeight = 100;


            frame = new JFrame("Choose Your Starter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(rows, cols));


            for (int i = 0; i < creatureNames.length; i++) {
                String creatureName = creatureNames[i];
                CreatureType creatureType = types[i];
                String iconPath = creatureName + ".png";
                String familyName = creatureFamilies.get(creatureName);

                JButton button = new JButton(creatureName);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);


                ImageIcon icon = createScaledIcon(creatureName, imageWidth, imageHeight);

                if (icon != null) {
                    button.setIcon(icon);
                }


                button.addActionListener(e -> {
                    int maxHealth = 100;
                    Creature chosenCreature = new Creature(creatureName, creatureType, familyName, 1, iconPath, maxHealth);
                    inventory.addCreature(chosenCreature);
                    frame.dispose();
                    gameManager = new GameManager(inventory);
                    gameManager.display();
                });


                frame.add(button);
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Creates a scaled ImageIcon from an image file.
     *
     * @param imageName The name of the image file (without extension).
     * @param width     The desired width of the scaled image.
     * @param height    The desired height of the scaled image.
     * @return An ImageIcon representing the scaled image, or null if the image cannot be loaded.
     */
    public ImageIcon createScaledIcon(String imageName, int width, int height) {
        String path = imageName + ".png";
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Icon not found: " + path);
            return null;
        }
        try {
            BufferedImage originalImage = ImageIO.read(imageUrl);
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
