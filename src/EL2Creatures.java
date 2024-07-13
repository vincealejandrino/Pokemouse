import java.util.Random;

/**
 * The EL2Creatures class contains methods for generating random creatures
 * for Evolution Level 2 in the game. Each creature is associated with a name,
 * type, family, evolution level, icon path, and maximum health.
 *
 *
 */
public class EL2Creatures {
    private static final int DEFAULT_MAX_HEALTH = 100;//The default maximum health for creatures.

/**
     * Generates a random creature for Evolution Level 2 based on a random choice.
     *
     * @return A randomly selected creature from EL2 with associated attributes.
     */
    public static Creature getRandomCreature() {
        Random random = new Random();
        int choice = random.nextInt(9) + 1;
        switch (choice) {
            case 1:
                return new Creature("Strawleon", CreatureType.FIRE, "A FAMILY", 2, "/Strawleon.png",DEFAULT_MAX_HEALTH);
            case 2:
                return new Creature("Chocofluff", CreatureType.FIRE, "B FAMILY", 2, "/Chocofluff.png",DEFAULT_MAX_HEALTH);
            case 3:
                return new Creature("Parfure", CreatureType.FIRE, "C FAMILY", 2, "/Parfure.png",DEFAULT_MAX_HEALTH);
            case 4:
                return new Creature("Chocosaur", CreatureType.NATURE, "D FAMILY", 2, "/Chocosaur.png",DEFAULT_MAX_HEALTH);
            case 5:
                return new Creature("Golberry", CreatureType.NATURE, "E FAMILY", 2, "/Golberry.png",DEFAULT_MAX_HEALTH);
            case 6:
                return new Creature("Kirlicake", CreatureType.NATURE, "F FAMILY", 2, "/Kirlicake.png",DEFAULT_MAX_HEALTH);
            case 7:
                return new Creature("Tartortle", CreatureType.WATER, "G FAMILY", 2, "/Tartortle.png",DEFAULT_MAX_HEALTH);
            case 8:
                return new Creature("Chocolish", CreatureType.WATER, "H FAMILY", 2, "/Chocolish.png",DEFAULT_MAX_HEALTH);
            case 9:
                return new Creature("Dewice", CreatureType.WATER, "I FAMILY", 2, "/Dewice.png",DEFAULT_MAX_HEALTH);
            default:
                return null;
        }
    }
}


