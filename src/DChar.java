/**
 * Class made to contain character information for DnDCombatTracker.
 *
 * @author E-Persson-OSU
 * @version 0.23
 *
 */
public class DChar {

    private String name;

    /**
     * PC, NPC, or Mob
     */
    private String type;

    private int initiative;

    private int maxHP;

    private int HP;

    private int tempHP;

    boolean top;

    //private List<String> conditions;

    public DChar(String name, String type, int initiative, int maxHP, int HP,
            int tempHP) {
        this.name = name;
        this.type = type;
        this.top = false;
        this.initiative = initiative;
        this.maxHP = maxHP;
        this.HP = HP;
        this.tempHP = tempHP;
    }

    @Override
    public String toString() {
        return this.name;
    }

    //    public String toStringMobMenu() {
    //        String name = this.name;
    //        if (this.health <= 0) {
    //            name = name + ", *DEAD*";
    //        } else {
    //            name = name + ", " + this.health;
    //        }
    //        return name;
    //    }
    //
    public String toStringOrder() {
        String name = this.name;
        if (this.top) {
            name += " *TOP*";
        }
        return name;
    }

    public String toStringHolds() {
        return this.name;
    }

    public String toStringMob() {
        String name = this.name;
        if (this.HP + this.tempHP == 0) {
            name += "*DEAD*";
        } else if (this.tempHP == 0 && this.HP > 0) {
            name = name + ", (" + this.tempHP + ")" + this.HP + "/"
                    + this.maxHP;
        } else {
            name = name + ", " + this.HP + "/" + this.maxHP;
        }
        //        for (String cond : this.conditions) {
        //            name += " *" + cond + "* ";
        //        }
        return name;
    }

    public String toStringNPC() {
        String name = this.name;
        if (this.HP + this.tempHP == 0) {
            name += "*DEAD*";
        } else if (this.tempHP == 0 && this.HP > 0) {
            name = name + ", (" + this.tempHP + ")" + this.HP + "/"
                    + this.maxHP;
        } else {
            name = name + ", " + this.HP + "/" + this.maxHP;
        }
        //        for (String cond : this.conditions) {
        //            name += " *" + cond + "* ";
        //        }
        return name;
    }

    public boolean top() {
        return this.top;
    }

    public void changeTop() {
        this.top = !this.top;
    }

    //    public List returnStatus() {
    //       return this.conditions;
    //    }

    public String type() {
        return this.type;
    }

    public int health() {
        return this.HP;
    }

    public void changeHealth(int amount) {
        this.tempHP += amount;
        if (this.tempHP < 0) {
            this.HP += this.tempHP;
            this.tempHP = 0;
        }
        if (this.HP < 0) {
            this.HP = 0;
        }
    }

    /*
     * Save Data
     */
    public void saveData() {
        //TODO
    }

}
