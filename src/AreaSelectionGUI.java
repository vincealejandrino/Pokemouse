import javax.swing.*;
import java.awt.*;

/**
 * The AreaSelectionGUI class represents a graphical user interface
 * for selecting and exploring different areas in a game. Players can choose
 * to explore different areas with varying characteristics.
 *
 */
public class AreaSelectionGUI extends JFrame {

    private Inventory inventory;//The player's inventory, which may contain creatures.
    private GameManager gameManager;//The game manager responsible for managing game states and transitions.
    
/**
    * Constructs an AreaSelectionGUI object with the provided inventory
     * and game manager.
     *
     * @param inventory    The player's inventory.
     * @param gameManager  The game manager responsible for game state management.
     */
    public AreaSelectionGUI(Inventory inventory, GameManager gameManager) {
        this.inventory = inventory;
        this.gameManager = this.gameManager;
        initializeUI();
    }

    /**
     * Initializes the user interface for area selection.
     */
    public void initializeUI() {
        setTitle("Select Area");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 1));


        JButton area1Button = new JButton("Explore Area 1");
        JButton area2Button = new JButton("Explore Area 2");
        JButton area3Button = new JButton("Explore Area 3");

        area1Button.addActionListener(e -> openArea(5, 1, 1));
        area2Button.addActionListener(e -> openArea(3, 3, 2));
        area3Button.addActionListener(e -> openArea(4, 4, 3));

        add(area1Button);
        add(area2Button);
        add(area3Button);

        pack();
        setLocationRelativeTo(null);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            gameManager.display();
        });


        add(backButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

    }

     /**
     * Opens an area exploration interface with the specified dimensions and area level.
     *
     * @param width      The width of the area grid.
     * @param height     The height of the area grid.
     * @param areaLevel  The level of the area to explore.
     */
    public void openArea(int width, int height, int areaLevel) {
        Area area = new Area(width, height, areaLevel, inventory);
        area.setVisible(true);
        dispose();
    }
}
