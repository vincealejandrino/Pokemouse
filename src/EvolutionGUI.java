import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;


/**
 * The EvolutionGUI class represents the graphical user interface for evolving creatures
 * in the game. It allows the player to select two creatures for evolution and trigger the evolution process.
 *
 */
public class EvolutionGUI extends JFrame {
    private Inventory inventory;
    private GameManager gameManager;
    private JComboBox<Creature> creature1ComboBox;
    private JComboBox<Creature> creature2ComboBox;
    private JButton evolveButton;
    private JLabel resultLabel;

    private Consumer<List<Creature>> onEvolveSelected;//A consumer to handle the result of the evolution, typically used to refresh the game state.
    

    /**
     * Constructs an  EvolutionGUI instance with the specified inventory, game manager,
     * and a consumer to handle the result of evolution.
     *
     * @param inventory      The inventory used to manage creatures.
     * @param gameManager    The game manager responsible for controlling the game flow.
     * @param onEvolveSelected A consumer to handle the result of the evolution, typically used
     *                        to refresh the game state.
     */
    public EvolutionGUI(Inventory inventory, GameManager gameManager, Consumer<List<Creature>> onEvolveSelected) {
        this.inventory = inventory;
        this.gameManager = gameManager;
        this.onEvolveSelected = onEvolveSelected;

        initializeUI();
    }

    /**
     * Initializes the graphical user interface for creature evolution.
     */
    public void initializeUI() {
        setTitle("Evolve Creatures");
        setLayout(new FlowLayout());

        creature1ComboBox = new JComboBox<>();
        creature2ComboBox = new JComboBox<>();
        updateCreatureComboBoxes();

        evolveButton = new JButton("Evolve");
        evolveButton.addActionListener(e -> performEvolution());

        resultLabel = new JLabel();

        add(creature1ComboBox);
        add(creature2ComboBox);
        add(evolveButton);
        add(resultLabel);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates the creature combo boxes with eligible creatures that can participate in evolution.
     */
    public void updateCreatureComboBoxes() {
        creature1ComboBox.removeAllItems();
        creature2ComboBox.removeAllItems();
        List<Creature> eligibleCreatures = inventory.getCreaturesEligibleForEvolution();
        for (Creature creature : eligibleCreatures) {
            creature1ComboBox.addItem(creature);
            creature2ComboBox.addItem(creature);
        }
    }

    /**
     * Initiates the evolution process when the evolve button is clicked.
     */
    public void performEvolution() {
        Creature creature1 = (Creature) creature1ComboBox.getSelectedItem();
        Creature creature2 = (Creature) creature2ComboBox.getSelectedItem();

        if (creature1 != null && creature2 != null && !creature1.equals(creature2)) {
            boolean success = inventory.evolveCreature(creature1, creature2);
            if (success) {
                resultLabel.setText("Evolution successful!");
                
            } else {
                resultLabel.setText("Evolution failed.");
            }
            updateCreatureComboBoxes(); 
        } else {
            resultLabel.setText("Select two different creatures for evolution.");
        }
    }

}

