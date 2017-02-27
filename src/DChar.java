import java.util.HashMap;
import java.util.Map;

public class DChar {

    private String name;

    private boolean top;

    /**
     * player = true, mob = false
     */
    private boolean player;

    private Map<String, Integer> statusConditions;

    private int health;

    public DChar(String name) {
        this.name = name;
        this.top = false;
        this.statusConditions = new HashMap<String, Integer>();
        this.player = true;
        this.health = 0;
    }

    public DChar(String name, int health) {
        this.name = name;
        this.top = false;
        this.statusConditions = new HashMap<String, Integer>();
        this.player = false;
        this.health = health;
    }

    @Override
    public String toString() {
        String name = this.name;
        if (!this.player) {
            if (this.health <= 0) {
                name = name + ", *DEAD*";
            } else {
                name = name + ", " + this.health;
            }
        }
        return name;
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

    public boolean player() {
        return this.player;
    }

    public int health() {
        return this.health;
    }

    public void changeHealth(int amount) {
        this.health += amount;
    }
}
