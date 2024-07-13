import java.util.Random;

/**
 * The  Battle class represents a battle between a player's creature and
 * an enemy creature in a game. It provides methods for attacking, swapping creatures,
 * attempting to capture the enemy creature.
 * 
 */
public class Battle {
    private Creature playerCreature;// The player's active creature in the battle.
    private final Creature enemy;//The enemy creature in the battle.
    private final Inventory playerInventory;//The player's inventory containing creatures and items.
    private final Random random = new Random();//A random number generator for generating random events.
    private static final int MAX_ACTIONS = 3;//The maximum number of actions a player can take in a battle.
    private int actionsTaken = 0;//The number of actions taken in the current battle.
    private int lastDamageDealt = 0;//The damage dealt in the last attack.
    private boolean enemyCaught = false;//Indicates whether the enemy creature has been successfully caught by the player.


    /**
     * Constructs a Battle object with the player's creature, enemy creature,
     * and player inventory.
     *
     * @param playerCreature  The player's active creature in the battle.
     * @param enemy           The enemy creature in the battle.
     * @param playerInventory The player's inventory containing creatures and items.
     */

    public Battle(Creature playerCreature, Creature enemy, Inventory playerInventory) {
        this.playerCreature = playerCreature;
        this.enemy = enemy;
        this.playerInventory = playerInventory;
        this.enemy.setHealth(50); // Set enemy's health to 50
    }

    /**
     * Performs an attack action in the battle, dealing damage to the enemy creature.
     * Damage calculation takes into account randomness, player's creature level,
     * and type advantages.
     */

    public void attack() {
        if (actionsTaken >= MAX_ACTIONS) return;
        int baseDamage = random.nextInt(10) + 1;
        int damageMultiplier = playerCreature.getEvolutionLevel();
        int damage = baseDamage * damageMultiplier;
        if (isTypeAdvantage(playerCreature.getType(), enemy.getType())) {
            damage *= 1.5;
        }
        enemy.reduceHealth(damage);
        lastDamageDealt = damage;
        actionsTaken++;
    }

    /**
     * Checks if a type advantage exists between two creature types.
     *
     * @param playerType The type of the player's creature.
     * @param enemyType  The type of the enemy creature.
     * @return  true if there is a type advantage, false otherwise.
     */

    public boolean isTypeAdvantage(CreatureType playerType, CreatureType enemyType) {
        return (playerType == CreatureType.FIRE && enemyType == CreatureType.NATURE) ||
                (playerType == CreatureType.NATURE && enemyType == CreatureType.WATER) ||
                (playerType == CreatureType.WATER && enemyType == CreatureType.FIRE);
    }


    /**
     * Swaps the player's active creature with a new one from the inventory.
     * The action is only allowed if certain conditions are met.
     *
     * @param newActiveCreature The new active creature selected by the player.
     */

    public void swap(Creature newActiveCreature) {
        if (actionsTaken >= MAX_ACTIONS || playerInventory.getCreatures().size() <= 1 || newActiveCreature == playerCreature) {
            return;
        }
        this.playerCreature = newActiveCreature;
        playerInventory.setActiveCreature(newActiveCreature);
        actionsTaken++;
    }

    /**
     * Attempts to capture the enemy creature. Success is determined by a catch chance
     * calculation based on the enemy's health.
     */
    public void tryCapture() {
        if (actionsTaken >= MAX_ACTIONS) return;
        int catchChance = 40 + 50 - enemy.getHealth();
        boolean success = random.nextInt(100) < catchChance;
        if (success) {
            playerInventory.addCreature(enemy);
            enemyCaught = true;
        }
        actionsTaken++;
    }

     /**
     * Ends the battle immediately, allowing the player to flee.
     */
    public void flee() {
        actionsTaken = MAX_ACTIONS;
    }

    /**
     * Gets the player's active creature in the battle.
     *
     * @return The player's active creature.
     */
    public Creature getPlayerCreature() {
        return playerCreature;
    }

    /**
     * Gets the enemy creature in the battle.
     *
     * @return The enemy creature.
     */
    public Creature getEnemy() {
        return enemy;
    }

    /**
     * Gets the damage dealt in the last attack.
     *
     * @return The damage dealt in the last attack.
     */
    public int getLastDamageDealt() {
        return lastDamageDealt;
    }

    /**
     * Checks if the battle is over, which can occur when the enemy's health reaches
     * zero or when the maximum number of actions is taken.
     *
     * @return  true if the battle is over,  false otherwise.
     */

    public boolean isBattleOver() {
        return enemy.getHealth() <= 0 || actionsTaken >= MAX_ACTIONS;
    }

    /**
     * Checks if the enemy creature has been successfully caught by the player.
     *
     * @return true if the enemy is caught,  false otherwise.
     */
    public boolean isEnemyCaught() {
        return enemyCaught;
    }
}
