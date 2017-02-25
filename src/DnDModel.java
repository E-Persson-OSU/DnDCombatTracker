import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * The model. This stores the data for the program.
 *
 * @author Redse
 *
 */
public class DnDModel {
    /**
     * Stores the initiative orders and queue for holding turns
     */
    private Queue<String> initOrd;
    private Set<String> holds;

    /**
     * Turn number
     */
    private int turns;

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
        this.turns = 1;
        this.healthMap = new HashMap<>();
    }

    /**
     * Methods for setting up initiative order.
     */
    /*
     * Add mob
     */
    public void addToOrder(String name, int health) {
        this.healthMap.put(name, health);
        this.initOrd.add(name);
    }

    /*
     * Add player
     */
    public void addToOrder(String name) {
        this.initOrd.add(name);
    }

    public String removeLastNameAdded() {
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

    public void changeHealth(boolean heal, int health) {
        //TODO
    }

    public void holdTurn() {
        //TODO
    }

    public void insertTurn(String name) {
        //TODO
    }

    //Private helper methods

}
