import java.util.Random;

/**
 * The EL3Creatures class contains methods for generating random creatures
 * for Evolution Level 3 in the game. Each creature is associated with a name,
 * type, family, evolution level, icon path, and maximum health.
 *
 *
 */
public class EL3Creatures {

    private static final int DEFAULT_MAX_HEALTH = 100;//The default maximum health for creatures.


    /**
     * Generates a random creature for Evolution Level 3 based on a random choice.
     *
     * @return A randomly selected creature from EL3 with associated attributes.
     */
    public static Creature getRandomCreature() {
        Random random = new Random();
        int choice = random.nextInt(9) + 1;
        switch (choice) {
            case 1:
                return new Creature("Strawizard", CreatureType.FIRE, "A FAMILY", 3, "/Strawizard.png",DEFAULT_MAX_HEALTH);
            case 2:
                return new Creature("Candaros", CreatureType.FIRE, "B FAMILY", 3, "/Candaros.png",DEFAULT_MAX_HEALTH);
            case 3:
                return new Creature("Parfelure", CreatureType.FIRE, "C FAMILY", 3, "/Parfelure.png",DEFAULT_MAX_HEALTH);
            case 4:
                return new Creature("Fudgasaur", CreatureType.NATURE, "D FAMILY", 3, "/Fudgasaur.png",DEFAULT_MAX_HEALTH);
            case 5:
                return new Creature("Croberry", CreatureType.NATURE, "E FAMILY", 3, "/Croberry.png",DEFAULT_MAX_HEALTH);
            case 6:
                return new Creature("Velvevoir", CreatureType.NATURE, "F FAMILY", 3, "/Velvevoir.png",DEFAULT_MAX_HEALTH);
            case 7:
                return new Creature("Piestoise", CreatureType.WATER, "G FAMILY", 3, "/Piestoise.png",DEFAULT_MAX_HEALTH);
            case 8:
                return new Creature("Icesundae", CreatureType.WATER, "H FAMILY", 3, "/Icesundae.png",DEFAULT_MAX_HEALTH);
            case 9:
                return new Creature("Samurcone", CreatureType.WATER, "I FAMILY", 3, "/Samurcone.png",DEFAULT_MAX_HEALTH);
            default:
                return null;
        }
    }
}
