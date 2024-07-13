import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

/**
 * The BattleGUI class represents a graphical user interface for
 * conducting battles between a player's creature and an enemy creature in a game.
 * It allows the player to take actions such as attacking, capturing, and swapping
 * creatures during the battle.
 *
 */
public class BattleGUI extends JFrame {
    private final Battle battle;//The battle instance that manages the current battle state.
    private final Inventory playerInventory; // The player's inventory containing creatures.
    private final JLabel playerCreatureLabel;//JLabel displaying information about the player's creature.
    private final JLabel enemyCreatureLabel;//JLabel displaying information about the enemy creature.
    private final JLabel battleInfoLabel;//JLabel displaying battle-related information.
    private final JProgressBar playerHealthBar;//JProgressBar representing the health of the player's creature.
    private final JProgressBar enemyHealthBar;//JProgressBar representing the health of the enemy creature.
    private final JLabel enemyImageLabel;// JLabel displaying the image of the enemy creature.
    private final JLabel playerImageLabel;//JLabel displaying the image of the player's creature.
    private JComboBox<Creature> creatureSwapDropdown;//JComboBox for selecting a creature to swap during battle.

    /**
     * Constructs a BattleGUI object for conducting a battle.
     *
     * @param playerCreature  The player's creature in the battle.
     * @param enemy           The enemy creature in the battle.
     * @param playerInventory The player's inventory containing creatures and items.
     */
    public BattleGUI(Creature playerCreature, Creature enemy, Inventory playerInventory) {
        this.battle = new Battle(playerCreature, enemy, playerInventory);
        this.playerInventory = playerInventory; // Initialize playerInventory
        this.playerCreatureLabel = new JLabel();
        this.enemyCreatureLabel = new JLabel();
        this.battleInfoLabel = new JLabel();
        this.playerHealthBar = new JProgressBar(0, playerCreature.getMaxHealth());
        this.enemyHealthBar = new JProgressBar(0, enemy.getMaxHealth());
        this.enemyImageLabel = new JLabel();
        this.playerImageLabel = new JLabel();
        creatureSwapDropdown = new JComboBox<>();
        initializeUI();
    }

    /**
     * Initializes the user interface components for the battle.
     */
    public void initializeUI() {
        setTitle("Battle");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateCreatureSwapDropdown();
        // Setup health bars
        playerHealthBar.setStringPainted(true);
        enemyHealthBar.setStringPainted(true);

        updateBattleInfo(); // Update labels and health bars

        // Button panel ito
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(createActionButton("Attack", e -> battle.attack()));
        buttonPanel.add(createActionButton("Catch", e -> battle.tryCapture()));
        buttonPanel.add(createActionButton("Flee", e -> battle.flee()));

        // Info panel ito
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(playerCreatureLabel);
        infoPanel.add(playerHealthBar);
        infoPanel.add(playerImageLabel);
        infoPanel.add(enemyCreatureLabel);
        infoPanel.add(enemyHealthBar);
        infoPanel.add(enemyImageLabel);
        infoPanel.add(battleInfoLabel);

        JPanel swapPanel = new JPanel();
        swapPanel.add(new JLabel("Swap to:"));
        swapPanel.add(creatureSwapDropdown);
        swapPanel.add(createActionButton("Confirm Swap", e -> confirmSwap()));

        add(swapPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates a JButton for battle actions with the specified title and action listener.
     *
     * @param title  The title of the button.
     * @param action The action listener for the button.
     * @return The created JButton.
     */

    public JButton createActionButton(String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(e -> {
            action.actionPerformed(e);
            updateBattleInfo();
            if (battle.isBattleOver()) {
                JOptionPane.showMessageDialog(this, getEndBattleMessage(), "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the battle screen
            }
        });
        return button;
    }


     /**
     * Swaps the player's active creature with one from the inventory.
     */
    public void swapCreatures() {
        List<Creature> creatures = playerInventory.getCreatures();
        // para maassume that the playerInventory has a method getCreatures na mag return a List<Creature>
        Creature newActive = creatures.stream()
                .filter(creature -> !creature.equals(battle.getPlayerCreature()))
                .findFirst()
                .orElse(null);
        if (newActive != null) {
            battle.swap(newActive);
        }
    }

     /**
     * Updates the information displayed in the battle UI.
     */
    public void updateBattleInfo() {
        Creature playerCreature = battle.getPlayerCreature();
        Creature enemy = battle.getEnemy();

        // Update creature information and health bars
        playerCreatureLabel.setText("Player: " + playerCreature.getName() + ", Type: " + playerCreature.getType() + ", EL: " + playerCreature.getEvolutionLevel());
        playerHealthBar.setValue(playerCreature.getHealth());
        playerImageLabel.setIcon(loadImageIcon(getIconPathForCreature(playerCreature)));

        enemyCreatureLabel.setText("Enemy: " + enemy.getName() + ", Health: " + enemy.getHealth() + ", Type: " + enemy.getType() + ", EL: " + enemy.getEvolutionLevel());
        enemyHealthBar.setValue(enemy.getHealth());
        enemyImageLabel.setIcon(loadImageIcon(getIconPathForCreature(enemy)));

        if (battle.getLastDamageDealt() > 0) {
            battleInfoLabel.setText("Last Damage Dealt: " + battle.getLastDamageDealt());
        }
    }

    /**
     * Updates the creature selection dropdown with available creatures for swapping.
     */
    public void updateCreatureSwapDropdown() {
        creatureSwapDropdown.removeAllItems();
        for (Creature creature : playerInventory.getCreatures()) {
            if (!creature.equals(battle.getPlayerCreature())) {
                creatureSwapDropdown.addItem(creature);
            }
        }
    }

    /**
     * Confirms the creature swap action selected by the player.
     */

    public void confirmSwap() {
        Creature selectedCreature = (Creature) creatureSwapDropdown.getSelectedItem();
        if (selectedCreature != null) {
            battle.swap(selectedCreature);
            updateBattleInfo();
        }
    }

    /**
     * Loads an image and creates a scaled ImageIcon.
     *
     * @param path The path to the image file.
     * @return The ImageIcon created from the loaded image.
     */
    public ImageIcon loadImageIcon(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            BufferedImage image = ImageIO.read(is);
            Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * Generates the file path for an icon representing a creature.
     *
     * @param creature The creature for which to get the icon path.
     * @return The file path for the creature's icon.
     */
    public String getIconPathForCreature(Creature creature) {
        
        return "/" + creature.getName() + ".png";
    }

     /**
     * Gets a message indicating the result of the battle.
     *
     * @return  message indicating the battle result.
     */
    public String getEndBattleMessage() {
        if (battle.isEnemyCaught()) {
            return "Enemy caught successfully!";
        } else if (battle.getEnemy().getHealth() <= 0) {
            return "Enemy defeated!";
        } else if (battle.isBattleOver()) {
            return "BYE BYE!";
        }
        return "Battle ended.";
    }
}
