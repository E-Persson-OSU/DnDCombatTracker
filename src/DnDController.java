/**
 * The Controller class registers button actions and calls the appropriate model
 * methods.
 *
 * @author Redse
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

    /*
     * private controller methods
     */
    private void activateButtons() {
        //TODO
    }

    /**
     * Refreshes view after each event.
     *
     * @param model
     *            the DnD model.
     * @param view
     *            the DnD view.
     */
    private void updateViewToMatchModel(DnDModel model, DnDView view) {
        //TODO
    }

    /*
     * Process events
     */

}
