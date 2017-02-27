/**
 * The Controller class registers button actions and calls the appropriate model
 * methods.
 *
 * @author E-Persson-OSU
 *
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
            System.out.println("Updated Enter View.");
        } else {
            view.updateMobMenu(model.getMobList());
            view.setInitOrdText(model.finish());
            view.updateHoldList(model.getHoldList());
            view.setTurn(model.turn());
            view.enableMainButtons(model.lengthOfInitOrd(),
                    model.lengthOfHoldOrd(), model.lengthOfMobOrd());
            System.out.println("Updated Main View.");

        }
    }

    /*
     * Process events
     */
    public void processEnterEvent() {
        String name = this.view.getNameFromField();
        int health = this.view.getHealth();
        this.model.enter(name, health);
        this.view.setFocusToName();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processUndoEvent() {
        String name = this.model.undo();
        this.view.undone(name);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processFinishEvent() {
        this.view.finish();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processNextPlayerEvent() {
        this.model.nextPlayer();
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
        this.model.damage(this.view.healthDialog(), this.view.getMobSelected());
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processHealEvent() {
        this.model.nextPlayer();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processAddMobEvent() {
        this.model.nextPlayer();
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processRemoveMobEvent() {
        this.model.nextPlayer();
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
        this.updateViewToMatchModel(this.model, this.view);
    }
}
