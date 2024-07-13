import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


/**
 * The Creature class represents a creature in a game. It encapsulates
 * properties such as name, type, family, evolution level, health, maximum health,
 * and an icon path for displaying the creature's information and image.
 *
 */
public class Creature {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long id; // Unique identifier for each creature
    private final String name;//The name of the creature.
    private final CreatureType type;//type of the cretaure
    private final String family;//family to whih the creature belongs
    private final int evolutionLevel;//The evolution level of the creature.
    private int health;//The current health of the creature.
    private final int maxHealth;  //The maximum health that the creature can have.
    private final String iconPath;//The file path to the icon representing the creature.

    /**
     * Constructs a Creature object with the specified attributes.
     *
     * @param name         The name of the creature.
     * @param type         The type of the creature (e.g., FIRE, WATER, NATURE).
     * @param family       The family to which the creature belongs.
     * @param evolutionLevel The evolution level of the creature.
     * @param iconPath     The file path to the icon representing the creature.
     * @param maxHealth    The maximum health that the creature can have.
     */
    public Creature(String name, CreatureType type, String family, int evolutionLevel, String iconPath, int maxHealth) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.evolutionLevel = evolutionLevel;
        this.iconPath = iconPath;
        this.health = maxHealth; // Initialize health to maxHealth
        this.maxHealth = maxHealth; // Set maxHealth
        this.id = ID_GENERATOR.getAndIncrement();
    }


/**
     * Gets the maximum health that the creature can have.
     *
     * @return The maximum health of the creature.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the current health of the creature.
     *
     * @return The current health of the creature.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the creature to the specified value.
     *
     * @param health The new health value for the creature.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retrieves the unique identifier for the creature.
     *
     * @return the unique ID of the creature.
     */
    public long getId() {
        return id;
    }
    /**
     * Reduces the health of the creature by the specified amount (damage).
     * If the health drops below zero, it is set to zero.
     *
     * @param damage The amount of damage to be subtracted from the health.
     */
    public void reduceHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return id == creature.id;
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gets the name of the creature.
     *
     * @return The name of the creature.
     */
    public String getName() {
        return name;
    }

     /**
     * Gets the type of the creature.
     *
     * @return The type of the creature.
     */
    public CreatureType getType() {
        return type;
    }

    /**
     * Gets the family to which the creature belongs.
     *
     * @return The family of the creature.
     */
    public String getFamily() {
        return family;
    }

    /**
     * Gets the evolution level of the creature.
     *
     * @return The evolution level of the creature.
     */
    public int getEvolutionLevel() {
        return evolutionLevel;
    }

    /**
     * Gets the file path to the icon representing the creature.
     *
     * @return The icon path of the creature.
     */

    public String getIconPath() {
        return iconPath.startsWith("/") ? iconPath.substring(1) : iconPath;
    }

  /**
     * Returns a string representation of the creature.
     *
     * @return A string containing the creature's name, type, family, and evolution level.
     */
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Family: " + family + ", Evolution Level: " + evolutionLevel;
    }
}
