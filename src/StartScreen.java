import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The StartScreen class represents the initial screen of the game where players can start the game.
 * It provides a welcome message, a game logo, and a start button to begin the game.
 */
public class StartScreen extends JFrame {
    private JButton startButton;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color PASTEL_RED = new Color(255, 192, 203);
    

    /**
     * Constructs a StartScreen object.
     *
     * @param game The Game instance associated with the start screen.
     */
    public StartScreen(Game game) {
        setTitle("Welcome to the Game!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(PASTEL_RED);
        setLayout(new GridBagLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;


        JLabel logoLabel = new JLabel(createScaledIcon("src/Pokemon.png", WINDOW_WIDTH * 3 / 4, WINDOW_HEIGHT / 2));
        gbc.insets = new Insets(10, 10, 10, 10);
        add(logoLabel, gbc);


        startButton = new JButton(createScaledIcon("src/play.png", 100, 100));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> game.chooseStartingCreature());
        gbc.insets = new Insets(20, 10, 10, 10);
        add(startButton, gbc);


    }

    /**
     * Creates a scaled ImageIcon from an image file.
     *
     * @param path   The file path to the image.
     * @param width  The desired width of the scaled image.
     * @param height The desired height of the scaled image.
     * @return An ImageIcon containing the scaled image.
     */
    public ImageIcon createScaledIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

     /**
     * The main method to launch the game's start screen.
     *
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game gameInstance = new Game();
            StartScreen startScreen = new StartScreen(gameInstance);
            startScreen.setVisible(true);
        });
    }
}
