/**
 * Simple GUI application supporting append and undo using a version of the
 * Model-View-Controller (MVC) design pattern including interfaces.
 *
 * @author Bruce W. Weide
 * @author Paolo Bucci
 */
public final class DnDGUI {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private DnDGUI() {
    }

    /**
     * Main program that sets up main application window and starts user
     * interaction.
     *
     * @param args
     *            command-line arguments; not used
     */
    public static void main(String[] args) {
        /*
         * Create instances of the model, view, and controller objects, and
         * initialize them; view needs to know about controller, and controller
         * needs to know about model and view
         */
        DnDModel model = new DnDModel();
        DnDView view = new DnDView();
        DnDController controller = new DnDController(model, view);

        //view.registerObserver(controller);
    }

}
