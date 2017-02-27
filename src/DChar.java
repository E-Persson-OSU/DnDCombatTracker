import java.util.HashMap;
import java.util.Map;

public class DChar {

    private String name;

    private boolean top;

    /**
     * player = true, mob = false
     */
    private boolean playerOrMob;

    private Map<String, Integer> statusConditions;

    private int health;

    public DChar(String name) {
        this.name = name;
        this.top = false;
        this.statusConditions = new HashMap<String, Integer>();
        this.playerOrMob = true;
        this.health = 0;
    }

    public DChar(String name, int health) {
        this.name = name;
        this.top = false;
        this.statusConditions = new HashMap<String, Integer>();
        this.playerOrMob = false;
        this.health = health;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String toString2() {
        String name = this.name;
        if (this.top) {
            name += "*TOP*";
        }
        return name;
    }

    public boolean top() {
        return this.top;
    }

    public void changeTop() {
        this.top = !this.top;
    }

    public Map<String, Integer> returnStatus() {
        return this.statusConditions;
    }

    public boolean playerOrMob() {
        return this.playerOrMob;
    }

    public int health() {
        return this.health;
    }

    public void changeHealth(int amount) {
        this.health += amount;
    }
}