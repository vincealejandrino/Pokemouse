import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * The InventoryGUI class provides a graphical user interface for managing a player's creature inventory.
 * Players can view their creatures, set an active creature, and see the image of the selected creature.
 */
public class InventoryGUI extends JFrame {
    private Inventory inventory;
    private GameManager gameManager;
    private JComboBox<Creature> creatureComboBox;
    private JLabel activeCreatureLabel;
    private JLabel creatureImageLabel; // Label to display the image of the creature

    /**
     * Constructs an InventoryGUI object.
     *
     * @param inventory   The player's inventory containing creatures.
     * @param gameManager The GameManager associated with the game.
     */
    public InventoryGUI(Inventory inventory, GameManager gameManager) {
        this.inventory = inventory;
        this.gameManager = gameManager;
        initializeUI();
    }

     /**
     * Initializes the user interface components for the inventory GUI.
     */
    public void initializeUI() {
        setTitle("Inventory");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        creatureComboBox = new JComboBox<>();
        creatureComboBox.setRenderer(new CreatureListCellRenderer()); // Custom renderer to show images in the combo box
        updateCreatureComboBox();

        creatureComboBox.addActionListener(this::onCreatureSelected); // Add action listener to handle creature selection

        JButton setActiveButton = new JButton("Set Active");
        setActiveButton.addActionListener(this::setActiveCreature);

        activeCreatureLabel = new JLabel("Active Creature: None");
        creatureImageLabel = new JLabel(); // Initialize the label for creature image
        updateActiveCreatureLabel();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(creatureComboBox, BorderLayout.NORTH);
        topPanel.add(creatureImageLabel, BorderLayout.CENTER); // Add the image label to the top panel

        add(topPanel, BorderLayout.NORTH);
        add(setActiveButton, BorderLayout.CENTER);
        add(activeCreatureLabel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            if (gameManager != null) {
                gameManager.display();
            }
        });
        add(backButton, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates the creature combo box with the creatures from the inventory.
     */
    public void updateCreatureComboBox() {
        creatureComboBox.removeAllItems();
        List<Creature> creatures = inventory.getCreatures();
        for (Creature creature : creatures) {
            creatureComboBox.addItem(creature);
        }
    }

    /**
     * Sets the selected creature as the active creature when the "Set Active" button is clicked.
     *
     * @param event The action event associated with the button click.
     */
    public void setActiveCreature(ActionEvent event) {
        Creature selectedCreature = (Creature) creatureComboBox.getSelectedItem();
        if (selectedCreature != null) {
            inventory.setActiveCreature(selectedCreature);
            updateActiveCreatureLabel();
        }
    }

    /**
     * Updates the active creature label with the name of the active creature.
     */
    public void updateActiveCreatureLabel() {
        Creature activeCreature = inventory.getActiveCreature();
        if (activeCreature != null) {
            activeCreatureLabel.setText("Active Creature: " + activeCreature.getName());
            
            creatureImageLabel.setIcon(new ImageIcon(getClass().getResource(activeCreature.getIconPath())));
        }
    }

    /**
     * Handles the selection of a creature from the combo box.
     *
     * @param event The action event associated with the combo box selection.
     */
    public void onCreatureSelected(ActionEvent event) {
        Creature selectedCreature = (Creature) creatureComboBox.getSelectedItem();
        if (selectedCreature != null) {
            
            creatureImageLabel.setIcon(new ImageIcon(getClass().getResource(selectedCreature.getIconPath())));
        }
    }

    /**
     * Custom cell renderer to display creature images in the combo box.
     */
    public class CreatureListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Creature) {
                Creature creature = (Creature) value;
                setText(creature.getName());
                setIcon(new ImageIcon(getClass().getResource(creature.getIconPath())));
            }
            return this;
        }
    }
}
