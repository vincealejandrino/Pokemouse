import java.util.Random;

/**
 * The EL1Creatures class contains methods for generating random creatures
 * for Evolution Level 1 in the game. Each creature is associated with a name,
 * type, family, evolution level, icon path, and maximum health.
 *
 *
 */
public class EL1Creatures {

    private static final int DEFAULT_MAX_HEALTH = 100;//The default maximum health for creatures.

     /**
     * Generates a random creature for Evolution Level 1 based on a random choice.
     *
     * @return A randomly selected creature from EL1 with associated attributes.
     */
    public static Creature getRandomCreature() {
        Random random = new Random();
        int choice = random.nextInt(9) + 1;
        switch (choice) {
            case 1:
                return new Creature("Strawander", CreatureType.FIRE, "A FAMILY", 1, "/Strawander.png",  DEFAULT_MAX_HEALTH);
            case 2:
                return new Creature("Chocowool", CreatureType.FIRE, "B FAMILY", 1, "/Chocowool.png", DEFAULT_MAX_HEALTH);
            case 3:
                return new Creature("Parfwit", CreatureType.FIRE, "C FAMILY", 1, "/Parfwit.png",  DEFAULT_MAX_HEALTH);
            case 4:
                return new Creature("Brownisaur", CreatureType.NATURE, "D FAMILY", 1, "/Brownisaur.png", DEFAULT_MAX_HEALTH);
            case 5:
                return new Creature("Frubat", CreatureType.NATURE, "E FAMILY", 1, "/Frubat.png", DEFAULT_MAX_HEALTH);
            case 6:
                return new Creature("Malts", CreatureType.NATURE, "F FAMILY", 1, "/Malts.png", DEFAULT_MAX_HEALTH);
            case 7:
                return new Creature("Squirpie", CreatureType.WATER, "G FAMILY", 1, "/Squirpie.png", DEFAULT_MAX_HEALTH);
            case 8:
                return new Creature("Chocolite", CreatureType.WATER, "H FAMILY", 1, "/Chocolite.png", DEFAULT_MAX_HEALTH);
            case 9:
                return new Creature("Oshacone", CreatureType.WATER, "I FAMILY", 1, "/Oshacone.png", DEFAULT_MAX_HEALTH);
            default:
                return null;
        }
    }
}


