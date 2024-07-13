import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/*
 * The Area class represents a graphical user interface for exploring
 * an area in a game. It allows players to move within a grid-based area, encounter
 * creatures, and engage in battles.
 *
 */

public class Area extends JFrame {
    private final int width; //The width of the area grid.
    private final int height;//The height of the area grid
    private int xPosition = 0;//The current x-coordinate of the player's position in the area.
    private int yPosition = 0;//The current y-coordinate of the player's position in the area.
    private final JButton[][] buttons;//A 2D array of buttons representing the grid of the area.
    private final Random random = new Random();//A random number generator for generating random events.
    private final Inventory inventory;//The player's inventory containing creatures.
    private final int areaLevel; //The level of the area, which may affect creature encounters.
    private ImageIcon playerIcon; //The ImageIcon representing the player character.
    private static final Color PASTEL_RED = new Color(255, 192, 203);//A constant color representing pastel red.

    private ImageIcon upIcon = createScaledIcon("/uparrow.png", 30, 30);//ImageIcon for the up arrow button.
    private ImageIcon downIcon = createScaledIcon("/downarrow.png", 30, 30);//ImageIcon for the down arrow button.
    private ImageIcon leftIcon = createScaledIcon("/leftarrow.png", 30, 30);//ImageIcon for the left arrow button.
    private ImageIcon rightIcon = createScaledIcon("/rightarrow.png", 30, 30);//ImageIcon for the right arrow button.

    /**
     * Constructs an Area object with the specified dimensions, area level,
     * and player inventory.
     *
     * @param width      The width of the area grid.
     * @param height     The height of the area grid.
     * @param areaLevel  The level of the area.
     * @param inventory  The player's inventory.
     */
    public Area(int width, int height, int areaLevel, Inventory inventory) {
        this.width = width;
        this.height = height;
        this.areaLevel = areaLevel;
        this.inventory = inventory;
        this.buttons = new JButton[height][width];
        this.playerIcon = createScaledIcon("/player.png", 50, 50);
        initializeUI();
    }

    /**
     * This function initializes the user interface of the area exploration game.
     */

   public void initializeUI() {
        setTitle("Area Exploration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(height, width));
        gridPanel.setBackground(PASTEL_RED);

        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JButton button = new JButton();
                button.setEnabled(false);
                gridPanel.add(button);
                buttons[y][x] = button;
            }
        }
        updatePlayerPosition();



        JPanel controlPanel = new JPanel(new GridLayout(1, 5));
        controlPanel.setBackground(PASTEL_RED);
        controlPanel.add(createButton("", createScaledIcon("/uparrow.png", 30, 30), 0, -1));
        controlPanel.add(createButton("", createScaledIcon("/leftarrow.png", 30, 30), -1, 0));
        controlPanel.add(createButton("", createScaledIcon("/rightarrow.png", 30, 30), 1, 0));
        controlPanel.add(createButton("", createScaledIcon("/downarrow.png", 30, 30), 0, 1));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> dispose());
        controlPanel.add(exitButton);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates a scaled ImageIcon from a file path.
     *
     * @param path   The path to the image file.
     * @param width  The width to scale the image to.
     * @param height The height to scale the image to.
     * @return An ImageIcon representing the scaled image.
     */

    public ImageIcon createScaledIcon(String path, int width, int height) {
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


    /**
     * Creates a button with a label, icon, and associated movement action.
     *
     * @param label The label for the button.
     * @param icon  The ImageIcon for the button.
     * @param dx    The change in x-coordinate on button click.
     * @param dy    The change in y-coordinate on button click.
     * @return The created JButton.
     */

   public JButton createButton(String label, ImageIcon icon, int dx, int dy) {
        JButton button = new JButton(label, icon);
        button.addActionListener((ActionEvent e) -> movePlayer(dx, dy));
        return button;
    }


    /**
     * Moves the player's position within the area and handles creature encounters.
     *
     * @param dx The change in x-coordinate of the player's position.
     * @param dy The change in y-coordinate of the player's position.
     */

    public void movePlayer(int dx, int dy) {
        int newX = xPosition + dx;
        int newY = yPosition + dy;
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            xPosition = newX;
            yPosition = newY;
            updatePlayerPosition();
            if (encounterCreature()) {
                encounterAndBattle();
            }
        }
    }

    /**
     * Updates the player's position on the grid.
     */

    public void updatePlayerPosition() {

        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setIcon(null);
            }
        }

        buttons[yPosition][xPosition].setIcon(playerIcon);
    }

    /**
     * Determines if the player encounters a creature based on a random chance.
     *
     * @return true if the player encounters a creature, {@code false} otherwise.
     */
    public boolean encounterCreature() {

        return random.nextInt(100) < 40;
    }

    /**
     * Initiates a creature encounter and battle sequence.
     */
    public void encounterAndBattle() {
        if (encounterCreature()) {
            Creature enemyCreature = getRandomCreatureForArea(areaLevel);
            BattleGUI battleGUI = new BattleGUI(inventory.getActiveCreature(), enemyCreature, inventory);
            battleGUI.setVisible(true);

        }
    }


    /**
     * Gets a random creature suitable for the current area level.
     *
     * @param areaLevel The level of the area.
     * @return A random creature for the area.
     */
    public Creature getRandomCreatureForArea(int areaLevel) {
            Random random = new Random();
            switch (areaLevel) {
                case 1:

                    return EL1Creatures.getRandomCreature();
                case 2:

                    return EL2Creatures.getRandomCreature();
                case 3:

                    return EL3Creatures.getRandomCreature();
                default:
                    return null;
            }
        }
    }

