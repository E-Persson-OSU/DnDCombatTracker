import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class DnDView extends JFrame implements ActionListener {

    /**
     * JFrame
     */
    private final JFrame ENTER_FRAME;
    private final JFrame MAIN_FRAME;

    /**
     * JPanels
     */
    private final JPanel ENTER_PANEL;
    //private final JPanel MAIN_PANEL;

    /**
     * Text areas for entering names and health
     */
    private final JTextField tNames, tSpecName, tSpecHealth;
    private final JFormattedTextField tHealth;

    /**
     * Text area for displaying turn order
     */
    private final JTextArea taTurnOrder;

    /**
     * Buttons
     */
    private final JButton bEnter, bUndo, bNextTurn, bModifyHealth, bHoldTurn,
            bFinish;

    /**
     * Formatting
     */
    private final NumberFormat healthFormat;

    /**
     * Some final fields
     */
    private final int VERT_GRIDS_ENTER = 2, HOR_GRIDS_ENTER = 1,
            TEXT_AREA_HEIGHT = 20, TEXT_AREA_WIDTH = 20, ENTER_BUTTON_COLM = 2,
            ENTER_BUTTON_ROWS = 1, ENTER_MENU_ROWS = 2, ENTER_MENU_COLM = 1,
            TEXT_FIELD_WIDTH = 15, TEXT_FIELD_HEIGHT = 5,
            ENTER_INIT_HEIGHT = 192, ENTER_INIT_WIDTH = 512;
    /**
     * Font
     */
    private final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 20);
    private final Font FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 20);
    /**
     * Controller to interpret commands
     */
    private DnDController controller;

    /**
     * No-argument constructor.
     */
    public DnDView() {
        super("Dungeons and Dragons Initiative Order Tracker");

        //Set up GUI Widgets --------------------------------------------------
        /*
         * Text fields
         */
        //Initialize fields
        this.tNames = new JTextField();
        this.tSpecName = new JTextField("Name:");
        this.tSpecHealth = new JTextField("Health: ");
        this.healthFormat = NumberFormat.getIntegerInstance();
        this.tHealth = new JFormattedTextField(this.healthFormat);

        //Set properties
        this.tHealth.setColumns(this.TEXT_FIELD_WIDTH);
        this.tNames.setColumns(this.TEXT_FIELD_WIDTH);
        this.tSpecName.setEditable(false);
        this.tSpecHealth.setEditable(false);
        this.tSpecName.setFont(this.FONT_HEADER);
        this.tSpecHealth.setFont(this.FONT_HEADER);
        this.tHealth.setFont(this.FONT_PLAIN);
        this.tNames.setFont(this.FONT_PLAIN);

        /*
         * Text areas
         */
        this.taTurnOrder = new JTextArea("", this.TEXT_AREA_HEIGHT,
                this.TEXT_AREA_WIDTH);

        /*
         * Buttons
         */
        this.bEnter = new JButton("Enter");
        this.bHoldTurn = new JButton("Hold Turn");
        this.bModifyHealth = new JButton("Modify Health");
        this.bUndo = new JButton("Undo");
        this.bFinish = new JButton("Finish");
        this.bNextTurn = new JButton("Next Player");
        //Add ActionListener
        this.addActionListenerToButtons();

        //JPanels--------------------------------------------------------------
        /*
         * This one is for the enter screen
         */
        this.ENTER_PANEL = new JPanel(
                new GridLayout(this.ENTER_MENU_ROWS, this.ENTER_MENU_COLM));
        this.add(this.ENTER_PANEL);
        /*
         * Buttons for enter screen, Fields
         */
        JPanel enterButtons = new JPanel(new GridLayout(1, 3));
        JPanel enterFields = new JPanel(new GridLayout(2, 2));
        /*
         * Add buttons and fields
         */
        enterButtons.add(this.bEnter);
        enterButtons.add(this.bUndo);
        enterButtons.add(this.bFinish);
        enterFields.add(this.tSpecName);
        enterFields.add(this.tNames);
        enterFields.add(this.tSpecHealth);
        enterFields.add(this.tHealth);

        /*
         * Add panels to enter panel
         */
        this.ENTER_PANEL.add(enterFields);
        this.ENTER_PANEL.add(enterButtons);

        /*
         * Set up JFrames
         */
        //Set up the enter window
        this.ENTER_FRAME = new JFrame();
        this.ENTER_FRAME.add(this.ENTER_PANEL);
        this.ENTER_FRAME.pack();
        this.ENTER_FRAME.setSize(this.ENTER_INIT_WIDTH, this.ENTER_INIT_HEIGHT);
        this.ENTER_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.setVisible(true);
        //Set up main window
        this.MAIN_FRAME = new JFrame();

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to show action is being processed.
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Process action.
         */
        String source = event.getActionCommand();
        if (source.equals(this.bEnter.getActionCommand())) {
            //TODO
        } else if (source.equals(this.bFinish.getActionCommand())) {
            //TODO
            this.ENTER_FRAME.dispose();
        }
        //Action done being processed
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Create buttons.
     */
    private void addActionListenerToButtons() {
        this.bFinish.addActionListener(this);
        //TODO
    }

}
