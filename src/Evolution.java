import java.util.Optional;

/**
 * The Evolution class manages the evolution of creatures in the game. It provides
 * methods to check if two creatures can evolve, perform the evolution, and update the inventory.
 *
 */
public class Evolution {
    private Inventory inventory;//The inventory used to manage creatures.

     /**
     * Constructs an Evolution instance with the specified inventory.
     *
     * @param inventory The inventory used to manage creatures.
     */
    public Evolution(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Attempts to evolve two creatures and adds the evolved creature to the inventory if successful.
     *
     * @param creature1 The first creature for evolution.
     * @param creature2 The second creature for evolution.
     * @return An Optional containing the evolved creature if evolution is successful; otherwise, an empty Optional.
     */
    public Optional<Creature> evolveCreatures(Creature creature1, Creature creature2) {
        // Check if creatures can evolve
        if (canEvolve(creature1, creature2)) {
            String evolvedName = calculateEvolvedName(creature1.getFamily(), creature1.getEvolutionLevel() + 1);
            Creature evolvedCreature = new Creature(
                    evolvedName,
                    creature1.getType(),
                    creature1.getFamily(),
                    creature1.getEvolutionLevel() + 1,
                    determineImagePath(creature1.getFamily(), creature1.getEvolutionLevel() + 1),
                    calculateEvolvedMaxHealth(creature1.getEvolutionLevel() + 1)
            );

            inventory.removeCreature(creature1);
            inventory.removeCreature(creature2);
            inventory.addCreature(evolvedCreature);
            return Optional.of(evolvedCreature);
        }
        return Optional.empty();
    }

    /**
     * Checks if two creatures can evolve based on their properties.
     *
     * @param creature1 The first creature.
     * @param creature2 The second creature.
     * @return true if the creatures can evolve; otherwise,  false.
     */
    public boolean canEvolve(Creature creature1, Creature creature2) {

        return creature1 != null && creature2 != null &&
                creature1.getId() != creature2.getId() &&
                creature1.getFamily().equals(creature2.getFamily()) &&
                creature1.getEvolutionLevel() == creature2.getEvolutionLevel() &&
                creature1.getEvolutionLevel() < 3;
    }

    /**
     * Calculates the name of the evolved creature based on its family and evolution level.
     *
     * @param family         The family of the creature.
     * @param evolutionLevel The evolution level of the creature.
     * @return The name of the evolved creature.
     */
    public String calculateEvolvedName(String family, int evolutionLevel) {

        return family + "EvolvedLv" + evolutionLevel;
    }

    /**
     * Determines the file path to the image of the evolved creature based on its family and evolution level.
     *
     * @param family         The family of the creature.
     * @param evolutionLevel The evolution level of the creature.
     * @return The file path to the image of the evolved creature.
     */
    public String determineImagePath(String family, int evolutionLevel) {

        return "src/" + family.toLowerCase() + "_evolution_" + evolutionLevel + ".png";
    }

    /**
     * Calculates the maximum health of the evolved creature based on its evolution level.
     *
     * @param evolutionLevel The evolution level of the creature.
     * @return The maximum health of the evolved creature.
     */
    public int calculateEvolvedMaxHealth(int evolutionLevel) {

        return 100 + (evolutionLevel - 1) * 20;
    }
}
