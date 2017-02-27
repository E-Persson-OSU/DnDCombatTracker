import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * The model. This stores the data for the program.
 *
 * @author E-Persson-OSU
 *
 */
public class DnDModel {
    /**
     * Stores the initiative orders and queue for holding turns
     */
    private Queue<String> initOrd, mobs;
    private Set<String> holds, dead;

    /**
     * Turn number
     */
    private int turns;

    /**
     * Top of the round name.
     */
    private String topOfTheRound;

    /**
     * Map to hold additional info, such as health.
     */
    private Map<String, Integer> healthMap;

    /**
     * Public constructor.
     */
    public DnDModel() {
        this.initOrd = new LinkedList<>();
        this.holds = new HashSet<>();
        this.dead = new HashSet<>();
        this.mobs = new LinkedList<>();
        this.turns = 1;
        this.healthMap = new HashMap<>();
        this.topOfTheRound = "";
    }

    /*
     * Buttons
     */
    public void enter(String name, int health) {
        if (health == 0) {
            this.addToOrder(name);
        } else {
            this.addToOrder(name, health);
        }
    }

    public String undo() {
        String name = this.removeLastNameAdded();
        return name;
    }

    public Queue<String> finish() {
        return this.initOrd;
    }

    public int lengthOfInitOrd() {
        return this.initOrd.size();
    }

    public Queue<String> nextPlayer() {
        this.initOrd.add(this.initOrd.remove());
        if (this.initOrd.peek().equals(this.topOfTheRound)) {
            this.incrementTurns();
        }
        return this.initOrd;
    }

    /**
     * Methods for setting up initiative order.
     */
    /*
     * Add mob
     */
    private void addToOrder(String name, int health) {
        this.healthMap.put(name, health);
        this.mobs.add(name);
        if (this.topOfTheRound.length() == 0) {
            this.topOfTheRound = name;
        }
        if (!this.initOrd.contains("HOSTILES")) {
            this.initOrd.add("HOSTILES");
        }
        //Test statement
        //        System.out.println("Mob: " + name + ", Health: " + health
        //                + " added successfully!");
        //        System.out.println("Queue contains: ");
        //        for (String str : this.initOrd) {
        //            System.out.println(str);
        //        }

    }

    /*
     * Add player
     */
    private void addToOrder(String name) {
        this.initOrd.add(name);
        if (this.topOfTheRound.length() == 0) {
            this.topOfTheRound = name;
        }
        //        System.out.println("Player: " + name + " added successfully!");
        //        System.out.println("Queue contains: ");
        //        for (String str : this.initOrd) {
        //            System.out.println(str);
        //        }
    }

    private String removeLastNameAdded() {
        for (int i = 0; i < this.initOrd.size() - 1; i++) {
            this.initOrd.add(this.initOrd.remove());
        }
        String last = this.initOrd.remove();
        if (this.healthMap.containsKey(last)) {
            this.healthMap.remove(last);
        }
        return last;
    }

    /**
     * Moves name from front of initiative order to Hold set.
     */
    public void moveToHold() {
        String name = this.initOrd.remove();
        if (name.equals(this.topOfTheRound)) {
            this.topOfTheRound = this.initOrd.peek();
        }
        this.holds.add(name);
    }

    /**
     * Running the game
     *
     * @return
     */
    public int turn() {
        return this.turns;
    }

    public void incrementTurns() {
        this.turns++;
    }

    public boolean topOfTheRound() {
        return this.initOrd.peek().equals(this.topOfTheRound);
    }

    public Map<String, Integer> getMobMap() {
        return this.healthMap;
    }

    public Queue<String> getMobs() {
        Queue<String> temp = new LinkedList<>();
        temp.addAll(this.mobs);
        return temp;
    }

    /**
     * Changes health of a mob.
     *
     * @param heal
     *            if heal = true, heal; else damage
     * @param health
     *            amount of damage to deal.
     */
    public void changeHealth(boolean heal, int health, String name) {
        if (heal) {
            int currentHealth = this.healthMap.get(name);
            currentHealth += health;
            this.healthMap.put(name, currentHealth);
        } else {
            int currentHealth = this.healthMap.get(name);
            currentHealth -= health;
            if (currentHealth <= 0) {
                this.healthMap.remove(name);
                this.dead.add(name);
                name = "*DEAD* " + name;
                this.healthMap.put(name, 0);

            } else {
                this.healthMap.put(name, currentHealth);
            }
        }
    }

    public void holdTurn() {
        String name = this.initOrd.remove();
        if (name.equals(this.topOfTheRound)) {
            this.topOfTheRound = this.initOrd.peek();
        }
    }

    /**
     * Adds player back to top of queue order.
     *
     * @param name
     *            adds
     */
    public void insertTurn(String name) {
        moveToInitOrd(name, this.holds, this.initOrd);
    }

    //Private helper methods
    /**
     * Adds name back to front of initOrd.
     *
     * @param name
     *            the name to add to {@code initOrd}
     */
    private static void moveToInitOrd(String name, Set<String> holds,
            Queue<String> initOrd) {
        if (holds.remove(name)) {
            initOrd.add(name);
            while (initOrd.peek().equals(name)) {
                initOrd.add(initOrd.remove());
            }
        }
    }
}
