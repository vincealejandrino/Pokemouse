import javax.swing.*;
import java.awt.*;

/**
 * The  GameManager class manages the game's main menu and navigation between different game features.
 * It allows players to view their inventory, explore different areas, and evolve creatures.
 *
 */

public class GameManager {
    private final Inventory inventory;
    private JFrame mainFrame;
    private Evolution evolution;

    /**
     * Constructs a new GameManager instance with the provided inventory and initializes the Evolution class.
     *
     * @param inventory The game's inventory.
     */
    public GameManager(Inventory inventory) {
        this.inventory = inventory;
        this.evolution = new Evolution(inventory); 
        initializeMainMenu();
    }

    /**
     * Displays the main game menu or reopens it if it is already visible.
     */
    public void display() {
        if (mainFrame != null) {
            mainFrame.setVisible(true);
        } else {
            initializeMainMenu();
        }
    }

     /**
     * Initializes the main game menu with buttons for accessing inventory, exploring areas, and evolving creatures.
     */
    public void initializeMainMenu() {
        mainFrame = new JFrame("Game Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(e -> openInventoryGUI());

        JButton exploreAreaButton = new JButton("Explore an Area");
        exploreAreaButton.addActionListener(e -> openAreaSelectionGUI());

        JButton evolutionButton = new JButton("Evolve Creatures");
        evolutionButton.addActionListener(e -> openEvolution());

        JButton exitButton = new JButton("Exit Game");
        exitButton.addActionListener(e -> exitGame());

        mainFrame.add(viewInventoryButton);
        mainFrame.add(exploreAreaButton);
        mainFrame.add(evolutionButton);
        mainFrame.add(exitButton);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Opens the inventory graphical user interface.
     */
    public void openInventoryGUI() {
        InventoryGUI inventoryGUI = new InventoryGUI(inventory, this);
        inventoryGUI.setVisible(true);
    }

    /**
     * Opens the area selection graphical user interface for exploring different game areas.
     */
    public void openAreaSelectionGUI() {
        AreaSelectionGUI areaSelectionGUI = new AreaSelectionGUI(inventory, this);
        areaSelectionGUI.setVisible(true);
    }

    /**
     * Opens the evolution graphical user interface for evolving creatures.
     */
    public void openEvolution() {
        EvolutionGUI evolutionGUI = new EvolutionGUI(inventory, this, evolvedCreatures -> {

            if (!evolvedCreatures.isEmpty()) {
                Creature evolvedCreature = evolvedCreatures.get(0);

                JOptionPane.showMessageDialog(mainFrame, "Creature evolved into: " + evolvedCreature.getName());
            }
        });
        evolutionGUI.setVisible(true);
    }


    /**
     * Exits the game after displaying a confirmation dialog.
     */
    public void exitGame() {
        int answer = JOptionPane.showConfirmDialog(
                mainFrame,
                "Are you sure you want to exit the game?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION
        );
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
