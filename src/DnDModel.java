import java.util.LinkedList;
import java.util.List;

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
    //private Queue<String> initOrd, mobs;
    private List<DChar> lOrd, lMob, lDead, lHold;

    /**
     * Turn number
     */
    private int turns;

    /**
     * Tells whether enemies have been inserted.
     */
    private boolean enemyIn;

    /**
     * Public constructor.
     */
    public DnDModel() {
        this.lOrd = new LinkedList<>();
        this.lMob = new LinkedList<>();
        this.lDead = new LinkedList<>();
        this.lHold = new LinkedList<>();
        this.turns = 1;
        this.enemyIn = false;
    }

    //BUTTONS------------------------------------------------------------------
    public void enter(String name, int health) {
        if (health == 0) {
            this.addToOrder(name);
        } else {
            this.addToOrder(name, health);
        }
        for (DChar ch : this.lOrd) {
            System.out.println(ch.toString());
        }
    }

    public String undo() {
        String name = this.removeLastNameAdded();
        return name;
    }

    public List<DChar> finish() {
        for (DChar ch : this.lOrd) {
            System.out.println(ch.toString());
        }
        return this.lOrd;
    }

    public void damage(int dmg, int pos) {
        dmg = dmg * -1;
        this.lMob.get(pos).changeHealth(dmg);
    }

    public void heal(int dmg, int pos) {
        this.lMob.get(pos).changeHealth(dmg);
    }

    public void holdTurn(int pos) {
        this.moveToHold(pos);
    }

    public void insertTurn(int pos) {
        this.moveToInitOrd(pos);
    }

    public List<DChar> nextPlayer() {
        this.lOrd.add(this.lOrd.remove(0));
        if (this.lOrd.get(0).top()) {
            this.incrementTurns();
        }
        return this.lOrd;
    }

    //OTHER--------------------------------------------------------------------
    public int lengthOfInitOrd() {
        return this.lOrd.size();
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
        return this.lOrd.get(0).top();
    }

    public List<DChar> getMobList() {
        for (DChar ch : this.lMob) {
            System.out.println(ch.toString());
        }
        return this.lMob;
    }

    /**
     * Adds player back to top of queue order.
     *
     * @param name
     *            adds
     */

    //Private helper methods
    /**
     * Adds name back to front of initOrd.
     *
     * @param name
     *            the name to add to {@code initOrd}
     */
    private void moveToInitOrd(int pos) {
        this.lOrd.add(0, this.lHold.remove(pos));
    }

    /**
     * Add mob
     */
    private void addToOrder(String name, int health) {
        this.lMob.add(new DChar(name, health));
        if (!this.enemyIn) {
            this.lOrd.add(new DChar("ENEMIES"));
            this.enemyIn = true;
        }
        if (this.lOrd.size() == 1) {
            this.lOrd.get(0).changeTop();
        }
        System.out.println("Added Mob: " + name);
    }

    /**
     * Add player
     */
    private void addToOrder(String name) {
        this.lOrd.add(new DChar(name));
        if (this.lOrd.size() == 1) {
           this.lOrd.get(0).changeTop();
        }
        System.out.println("Added Player: " + name);
    }

    /**
     * Undo helper
     */
    private String removeLastNameAdded() {
        String name = this.lOrd.remove(this.lOrd.size() - 1).toString();
        if (name.equals("ENEMIES")) {
            this.lMob = new LinkedList<>();
            this.enemyIn = false;
        }
        return name;
    }

    /**
     * Moves name from front of initiative order to Hold set.
     */
    private void moveToHold(int pos) {
        DChar toHold = this.lOrd.remove(pos);
        if (toHold.top()) {
            toHold.changeTop();
            this.lOrd.get(0).changeTop();
        }
        this.lHold.add(toHold);
    }
}
