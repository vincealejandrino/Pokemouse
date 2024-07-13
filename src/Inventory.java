import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The  Inventory class manages the collection of creatures available to the player,
 * including the active creature and provides methods for creature management such as evolution.
 *
 */
public class Inventory {
    private List<Creature> creatures;
    private Creature activeCreature;
    private Evolution evolution;

    /**
     * Constructs a new Inventory instance and initializes the list of creatures and evolution logic.
     */
    public Inventory() {
        this.creatures = new ArrayList<>();
        this.evolution = new Evolution(this); // Initialize evolution logic
    }

    /**
     * Adds a creature to the inventory. If no active creature is set, the added creature becomes the active one.
     *
     * @param creature The creature to be added to the inventory.
     */
    public void addCreature(Creature creature) {
        this.creatures.add(creature);
        if (activeCreature == null) {
            setActiveCreature(creature);
        }
    }

    /**
     * Sets the active creature from the inventory.
     *
     * @param creature The creature to set as the active creature.
     */
    public void setActiveCreature(Creature creature) {
        if (creatures.contains(creature)) {
            this.activeCreature = creature;
        } else {
            System.out.println("Creature not in inventory!");
        }
    }

    /**
     * Retrieves the active creature.
     *
     * @return The active creature.
     */
    public Creature getActiveCreature() {
        return activeCreature;
    }

    /**
     * Retrieves a copy of the list of creatures in the inventory.
     *
     * @return A list of creatures in the inventory.
     */
    public List<Creature> getCreatures() {
        return new ArrayList<>(creatures);
    }

    /**
     * Evolves a creature if it meets the necessary conditions.
     *
     * @param creature1 The first creature for evolution.
     * @param creature2 The second creature for evolution.
     * @return true if evolution is successful, false otherwise.
     */
    public boolean evolveCreature(Creature creature1, Creature creature2) {
        if (creature1 != null && creature2 != null &&
                creature1.getName().equals(creature2.getName()) &&
                creature1.getFamily().equals(creature2.getFamily()) &&
                creature1.getEvolutionLevel() == creature2.getEvolutionLevel()) {

            String evolvedCreatureName = getEvolvedCreatureName(creature1.getName(), creature1.getEvolutionLevel() + 1);
            
            if (evolvedCreatureName != null) {
                Creature evolvedCreature = new Creature(
                        evolvedCreatureName,
                        creature1.getType(),
                        creature1.getFamily(),
                        creature1.getEvolutionLevel() + 1,
                        evolvedCreatureName.toLowerCase() + ".png", 
                        creature1.getMaxHealth() 
                );

                
                removeCreature(creature1);
                removeCreature(creature2);
                addCreature(evolvedCreature);
                return true; 
            }
        }
        return false; 
    }

    /**
     * Removes a creature from the inventory.
     *
     * @param creature The creature to be removed.
     */
    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }


    /**
     * Checks if two creatures are eligible for evolution.
     *
     * @param creature1 The first creature.
     * @param creature2 The second creature.
     * @return true if the creatures are eligible for evolution,false otherwise.
     */

    public boolean canEvolve(Creature creature1, Creature creature2) {
        return creature1 != null && creature2 != null &&
                creature1.getName().equals(creature2.getName()) &&
                creature1.getFamily().equals(creature2.getFamily()) &&
                creature1.getEvolutionLevel() == creature2.getEvolutionLevel() &&
                creature1.getEvolutionLevel() < 3;
    }

    /**
 * Creates an evolved creature based on the provided base creature.
 *
 * @param baseCreature The base creature from which the evolved creature will be created.
 * @return The evolved creature.
 */

    public Creature createEvolvedCreature(Creature baseCreature) {
        String evolvedName = getEvolvedCreatureName(baseCreature.getName(), baseCreature.getEvolutionLevel() + 1);
        String evolvedImagePath = "/" + evolvedName.toLowerCase() + ".png";
        int evolvedMaxHealth = baseCreature.getMaxHealth(); 

        return new Creature(evolvedName, baseCreature.getType(), baseCreature.getFamily(),
                baseCreature.getEvolutionLevel() + 1, evolvedImagePath, evolvedMaxHealth);
    }

    /**
 * Determines the name of the evolved creature based on the base creature's name and evolution level.
 *
 * @param name           The name of the base creature.
 * @param evolutionLevel The evolution level of the base creature.
 * @return The name of the evolved creature.
 */
    public String getEvolvedCreatureName(String name, int evolutionLevel) {

        String evolvedName = "";
        switch (name) {
            case "Strawander":
                evolvedName = evolutionLevel == 2 ? "Strawleon" : "Strawizard";
                break;
            case "Chocowool":
                evolvedName = evolutionLevel == 2 ? "Chocofluff" : "Candaros";
                break;
            case "Parfwit":
                evolvedName = evolutionLevel == 2 ? "Parfure" : "Parfelure";
                break;
            case "Brownisaur":
                evolvedName = evolutionLevel == 2 ? "Chocosaur" : "Fudgasaur";
                break;
            case "Frubat":
                evolvedName = evolutionLevel == 2 ? "Golberry" : "Croberry";
                break;
            case "Malts":
                evolvedName = evolutionLevel == 2 ? "Kirlicake" : "Velvevoir";
                break;
            case "Squirpie":
                evolvedName = evolutionLevel == 2 ? "Tartortle" : "Piestoise";
                break;
            case "Chocolite":
                evolvedName = evolutionLevel == 2 ? "Chocolish" : "Icesundae";
                break;
            case "Oshacone":
                evolvedName = evolutionLevel == 2 ? "Dewice" : "Samurcone";
                break;
        }
        return evolvedName;
    }

    /**
     * Retrieves a list of creatures eligible for evolution, which have not reached the maximum evolution level.
     *
     * @return A list of creatures eligible for evolution.
     */
    public List<Creature> getCreaturesEligibleForEvolution() {
        return creatures.stream()
                .filter(creature -> creature.getEvolutionLevel() < 3)
                .collect(Collectors.toList());
    }
}
