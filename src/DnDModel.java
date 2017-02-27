import java.util.LinkedList;
import java.util.List;

/**
 * The model. This stores the data for the program.
 *
 * @author E-Persson-OSU
 * @version 0.23
 */
public class DnDModel {

    /**
     * Stores the initiative orders and queue for holding turns
     */
    //private List<DChar> lDead;
    private List<DChar> lOrd, lMob, lHold;

    /**
     * Turn number
     */
    private int turns;

    /**
     * Tells whether enemies have been inserted.
     */
    private boolean enemyIn;

    /**
     * Enemy DCHar
     */
    private DChar enemies;

    /**
     * Public constructor.
     */
    public DnDModel() {
        this.lOrd = new LinkedList<>();
        this.lMob = new LinkedList<>();
        //this.lDead = new LinkedList<>();
        this.lHold = new LinkedList<>();
        this.turns = 1;
        this.enemyIn = false;
        this.enemies = new DChar("ENEMIES");
    }

    //BUTTONS------------------------------------------------------------------
    public void enter(String name, int health, String type) {
        if (type.equals("player")) {
            this.addToOrder(name);
        } else if (type.equals("mob")) {
            this.addToOrder(name, health);
        } else if (type.equals("npc")) {
            this.addToOrder(name, health, type);
        }
        //        for (DChar ch : this.lOrd) {
        //            System.out.println(ch.toString());
        //        }
    }

    public String undo() {
        String name = this.removeLastNameAdded();
        return name;
    }

    public List<DChar> finish() {
        //        for (DChar ch : this.lOrd) {
        //            System.out.println(ch.toString());
        //        }
        return this.lOrd;
    }

    public void damage(int dmg, int pos) {
        dmg = dmg * -1;
        if (pos < 0) {
            pos = 0;
        }
        this.lMob.get(pos).changeHealth(dmg);
    }

    public void heal(int dmg, int pos) {
        if (pos < 0) {
            pos = 0;
        }
        this.lMob.get(pos).changeHealth(dmg);
    }

    public void holdTurn(int pos) {
        if (pos < 0) {
            pos = 0;
        }
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

    public void addMob(String name, int health) {
        this.addToOrder(name, health);
    }

    public void removeMob(int pos) {
        if (pos < 0) {
            pos = 0;
        }
        this.lMob.remove(pos);
        if (this.lMob.size() == 0) {
            this.lMob = new LinkedList<>();
            this.lOrd.remove(this.enemies);
            this.enemyIn = false;
        }
    }

    //OTHER--------------------------------------------------------------------
    public int lengthOfInitOrd() {
        return this.lOrd.size();
    }

    public int lengthOfHoldOrd() {
        return this.lHold.size();
    }

    public int lengthOfMobOrd() {
        return this.lMob.size();
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
        return this.lMob;
    }

    public List<DChar> getHoldList() {
        return this.lHold;
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
        if (pos < 0) {
            pos = 0;
        }
        this.lOrd.add(0, this.lHold.remove(pos));
        if (this.lOrd.size() == 1) {
            this.lOrd.get(0).changeTop();
        }
    }

    /*
     * Add Mob Methods
     */

    /**
     * Add mob
     */
    private void addToOrder(String name, int health) {
        this.lMob.add(new DChar(name, health));
        if (!this.enemyIn) {
            this.lOrd.add(this.enemies);
            this.enemyIn = true;
        }
        if (this.lOrd.size() == 1) {
            this.lOrd.get(0).changeTop();
        }
        //System.out.println("Added Mob: " + name);
    }

    /**
     * Add player
     */
    private void addToOrder(String name) {
        this.lOrd.add(new DChar(name));
        if (this.lOrd.size() == 1) {
            this.lOrd.get(0).changeTop();
        }
        //System.out.println("Added Player: " + name);
    }

    /**
     * Add npc
     */
    private void addToOrder(String name, int health, String type) {
        DChar npc = new DChar(name, health, type);
        this.lMob.add(npc);
        this.lOrd.add(npc);
        if (this.lOrd.size() == 1) {
            this.lOrd.get(0).changeTop();
        }
        //System.out.println("Added NPC: " + name);
    }

    /**
     * Undo helper
     */
    private String removeLastNameAdded() {
        DChar removed = this.lOrd.remove(this.lOrd.size() - 1);
        if (removed.equals(this.enemies)) {
            this.lMob = new LinkedList<>();
            this.enemyIn = false;
        }
        return this.enemies.toString();
    }

    /**
     * Moves name from front of initiative order to Hold set.
     */
    private void moveToHold(int pos) {
        DChar toHold = this.lOrd.remove(pos);
        if (toHold.top()) {
            toHold.changeTop();
            if (this.lOrd.size() > 0) {
                this.lOrd.get(0).changeTop();
            }
        }
        this.lHold.add(toHold);
    }
}
