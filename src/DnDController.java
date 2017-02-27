import java.util.LinkedList;
import java.util.Queue;

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
            view.enableButtons(model.lengthOfInitOrd() > 0);
            System.out.println("Updated Enter View.");
        } else {
            Queue<String> initOrd = new LinkedList<>();
            for (String str : model.finish()) {
                initOrd.add(str);
            }
            view.updateMobMenu(model.getMobMap(), model.getMobs());
            view.setInitOrdText(initOrd);
            view.setTurn(model.turn());
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
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processUndoEvent() {
        String name = this.model.undo();
        this.view.undone(name);
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processFinishEvent() {
        this.updateViewToMatchModel(this.model, this.view);
    }

    public void processNextPlayerEvent() {
        this.model.nextPlayer();
        this.updateViewToMatchModel(this.model, this.view);
    }

}
