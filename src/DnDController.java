import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The Controller class registers button actions and calls the appropriate model
 * methods.
 *
 * @author E-Persson-OSU
 * @version 0.23
 */
public class DnDController {

    /**
     * The DND model.
     */
    private final DnDModel model;

    /**
     * The DND view.
     */
    private final DnDView view;

    public DnDController(DnDModel model, DnDView view) {
        this.view = view;
        this.model = model;
    }

    //    /*
    //     * private controller methods
    //     */
    //    private void activateButtons() {
    //        //TODO
    //    }

    /**
     * Refreshes view after each event.
     *
     * @param model
     *            the DnD model.
     * @param view
     *            the DnD view.
     */
    private void updateViewToMatchModel(DnDModel model, DnDView view) {
        if (this.view.getView()) {
            view.clear();
            view.enableEnterButtons(model.lengthOfInitOrd() > 0);
            //System.out.println("Updated Enter View.");
        } else {
            view.updateMobMenu(model.getMobList());
            view.setInitOrdText(model.finish());
            view.updateHoldList(model.getHoldList());
            view.setTurn(model.turn());
            view.enableMainButtons(model.lengthOfInitOrd(),
                    model.lengthOfHoldOrd(), model.lengthOfMobOrd());
            //System.out.println("Updated Main View.");

        }
    }

    /*
     * Process events
     */
    public void processNewEvent() {
        this.view.newAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processExistingEvent() {
        this.view.existingAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processFinishEvent() {
        this.view.finishAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processAddEvent() {
        this.view.addAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processAddAndSaveEvent() {
        this.view.newAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processClearEvent() {
        this.view.clearAction();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processNextEvent() {
        this.model.next();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processHoldTurnEvent(int pos) {
        this.model.holdTurn(pos);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processInsertTurnEvent(int pos) {
        this.model.insertTurn(pos);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processDamageEvent() {
        this.model.damage(this.view.healthDialog(true),
                this.view.getMobSelected());
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processHealEvent() {
        this.model.heal(this.view.healthDialog(false),
                this.view.getMobSelected());
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processAddMobEvent(String name, int health) {
        //TODO
        //this.model.addMob(name, health);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processRemoveMobEvent(int pos) {
        //TODO
        //this.model.removeMob(pos);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processDoubleClickHolds(int pos) {
        this.model.insertTurn(pos);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processDoubleClickTurnOrder(int pos) {
        this.model.holdTurn(pos);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processDoubleClickMobMenu(int pos) {
        this.model.damage(this.view.healthDialog(true),
                this.view.getMobSelected());
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processTripleClickMobMenu(int pos) {
        this.model.heal(this.view.healthDialog(false),
                this.view.getMobSelected());
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void loadCharacter(File character, int initiative) {
        try {
            Scanner in = new Scanner(character);
            String name = in.nextLine();
            String type = in.nextLine();
            int maxHP = in.nextInt();
            int HP = in.nextInt();
            int tempHP = in.nextInt();
            this.model.enter(name, type, initiative, maxHP, HP, tempHP,
                    new LinkedList<String>());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void addCharacter(String name, String type, int initiative,
            int maxHP, int HP, int tempHP) {
        this.model.enter(name, type, initiative, maxHP, HP, tempHP,
                new LinkedList<String>());
    }
}
