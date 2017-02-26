import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * The view component of the DnDCombatTracker.
 *
 * @author E-Persson-OSU
 *
 */
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
    private final JPanel MAIN_PANEL;
    private final JPanel MAIN_LEFT_PANEL;
    private final JPanel MAIN_RIGHT_PANEL;
    private final JPanel MAIN_RIGHT_TOP, MAIN_RIGHT_MIDDLE, MAIN_RIGHT_BOTTOM;
    private final JPanel MAIN_RIGHT_TOP_BUTTONS;
    private final JPanel MAIN_RIGHT_MIDDLE_BUTTONS;

    /**
     * Text areas for entering names and health
     */
    private final JTextField tNames, tSpecName, tSpecHealth, tTurns,
            tSpecTurnOrder, tSpecHoldOrder;
    private final JFormattedTextField tHealth;

    /**
     * Text area for displaying turn order
     */
    private final JTextArea taTurnOrder, taHolds, taMobMenu;

    /**
     * Buttons
     */
    private final JButton bEnter, bUndo, bNextPlayer, bHoldTurn, bFinish,
            bInsertTurn, bDamage, bHeal, bAddMob, bRemoveMob;

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
     * Formatting
     */
    private final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 20);
    private final Font FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 20);
    private final Border BORDER = BorderFactory.createLineBorder(Color.GRAY);
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
        this.tSpecHoldOrder = new JTextField("Holds: ");
        this.tTurns = new JTextField("Turn: 1");
        this.tSpecTurnOrder = new JTextField("Initiative Order: ");

        //Set properties
        this.tHealth.setColumns(this.TEXT_FIELD_WIDTH);
        this.tNames.setColumns(this.TEXT_FIELD_WIDTH);
        this.tSpecName.setEditable(false);
        this.tSpecHealth.setEditable(false);
        this.tSpecName.setFont(this.FONT_HEADER);
        this.tSpecHealth.setFont(this.FONT_HEADER);
        this.tHealth.setFont(this.FONT_PLAIN);
        this.tNames.setFont(this.FONT_PLAIN);
        this.tTurns.setEditable(false);
        this.tSpecHoldOrder.setEditable(false);
        this.tSpecTurnOrder.setEditable(false);

        /*
         * Text areas
         */
        this.taTurnOrder = new JTextArea(20, 20);
        this.taHolds = new JTextArea();
        this.taMobMenu = new JTextArea();

        /*
         * Buttons
         */
        //enter window buttons
        this.bEnter = new JButton("Enter");
        this.bUndo = new JButton("Undo");
        this.bFinish = new JButton("Finish");

        //main window buttons
        this.bHoldTurn = new JButton("Hold Turn");
        this.bInsertTurn = new JButton("Insert Turn");
        this.bNextPlayer = new JButton("Next Player");
        this.bAddMob = new JButton("Add Mob");
        this.bDamage = new JButton("Damage");
        this.bHeal = new JButton("Heal");
        this.bRemoveMob = new JButton("Remove Mob");

        //set properties
        this.bUndo.setEnabled(false);
        this.bFinish.setEnabled(false);
        this.taTurnOrder.setEditable(false);
        this.taHolds.setEditable(false);
        this.taMobMenu.setEditable(false);
        this.taTurnOrder.setBorder(this.BORDER);

        //Add ActionListener
        this.addActionListenerToButtons();

        //JPanels--------------------------------------------------------------
        /*
         * This one is for the enter screen
         */
        this.ENTER_PANEL = new JPanel(
                new GridLayout(this.ENTER_MENU_ROWS, this.ENTER_MENU_COLM));

        /*
         * Main window
         */
        this.MAIN_PANEL = new JPanel(new GridLayout(1, 2));

        /*
         * Panels for enter window
         */
        JPanel enterButtons = new JPanel(new GridLayout(1, 3));
        JPanel enterFields = new JPanel(new GridLayout(2, 2));

        /*
         * Panels for main window
         */
        this.MAIN_LEFT_PANEL = new JPanel(new GridLayout(3, 1));
        this.MAIN_RIGHT_PANEL = new JPanel(new GridLayout(3, 1));
        this.MAIN_RIGHT_TOP = new JPanel(new GridLayout(3, 1));
        this.MAIN_RIGHT_TOP_BUTTONS = new JPanel(new GridLayout(1, 2));
        this.MAIN_RIGHT_MIDDLE = new JPanel(new GridLayout(2, 1));
        this.MAIN_RIGHT_MIDDLE_BUTTONS = new JPanel(new GridLayout(2, 2));
        this.MAIN_RIGHT_BOTTOM = new JPanel(new BorderLayout());

        //Buttons--------------------------------------------------------------
        /*
         * Add buttons and fields for enter window
         */
        enterButtons.add(this.bEnter);
        enterButtons.add(this.bUndo);
        enterButtons.add(this.bFinish);
        enterFields.add(this.tSpecName);
        enterFields.add(this.tNames);
        enterFields.add(this.tSpecHealth);
        enterFields.add(this.tHealth);

        /*
         * Add buttons and fields for main window
         */
        //left
        this.MAIN_LEFT_PANEL.add(this.tSpecTurnOrder);
        this.MAIN_LEFT_PANEL.add(this.taTurnOrder);
        this.MAIN_LEFT_PANEL.add(this.tTurns);

        //right top
        this.MAIN_RIGHT_TOP_BUTTONS.add(this.bHoldTurn);
        this.MAIN_RIGHT_TOP_BUTTONS.add(this.bInsertTurn);
        this.MAIN_RIGHT_TOP.add(this.tSpecHoldOrder);
        this.MAIN_RIGHT_TOP.add(this.taHolds);
        this.MAIN_RIGHT_TOP.add(this.MAIN_RIGHT_TOP_BUTTONS);
        //right middle
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bAddMob);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bRemoveMob);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bDamage);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bHeal);
        this.MAIN_RIGHT_MIDDLE.add(this.taMobMenu);
        this.MAIN_RIGHT_MIDDLE.add(this.MAIN_RIGHT_MIDDLE_BUTTONS);
        //right bottom
        this.MAIN_RIGHT_BOTTOM.add(this.bNextPlayer);
        //right
        this.MAIN_RIGHT_PANEL.add(this.MAIN_RIGHT_TOP);
        this.MAIN_RIGHT_PANEL.add(this.MAIN_RIGHT_MIDDLE);
        this.MAIN_RIGHT_PANEL.add(this.MAIN_RIGHT_BOTTOM);
        //main
        this.MAIN_PANEL.add(this.MAIN_LEFT_PANEL);
        this.MAIN_PANEL.add(this.MAIN_RIGHT_PANEL);
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
        this.ENTER_FRAME.setTitle("Enter Initiative Order");
        this.ENTER_FRAME.add(this.ENTER_PANEL);
        this.ENTER_FRAME.pack();
        this.ENTER_FRAME.setSize(this.ENTER_INIT_WIDTH, this.ENTER_INIT_HEIGHT);
        this.ENTER_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.setVisible(true);
        //Set up main window
        this.MAIN_FRAME = new JFrame();
        this.MAIN_FRAME.setTitle("Dungeons & Dragons Initiative Order Tracker");
        this.MAIN_FRAME.add(this.MAIN_PANEL);
        this.MAIN_FRAME.pack();
        this.MAIN_FRAME.setSize(768, 1024);
        this.MAIN_FRAME.setResizable(false);
        this.MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.MAIN_FRAME.setVisible(false);

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
            this.controller.processEnterEvent();
        } else if (source.equals(this.bFinish.getActionCommand())) {
            //TODO
            this.ENTER_FRAME.dispose();
            this.MAIN_FRAME.setVisible(true);
        } else if (source.equals(this.bUndo.getActionCommand())) {
            this.controller.processUndoEvent();
        }
        //Action done being processed
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Create buttons.
     */
    private void addActionListenerToButtons() {
        this.bFinish.addActionListener(this);
        this.bEnter.addActionListener(this);
        this.bHoldTurn.addActionListener(this);
        this.bNextPlayer.addActionListener(this);
        this.bUndo.addActionListener(this);
    }

    public void registerObserver(DnDController controller) {
        this.controller = controller;
    }

    /**
     * Pull info.
     */
    @Override
    public String getName() {
        return this.tNames.getText();
    }

    public int getHealth() {
        String health = this.tHealth.getText();
        int healthInt;
        if (health.length() == 0) {
            healthInt = 0;
        } else {
            healthInt = Integer.parseInt(health);
        }
        return healthInt;
    }

    /**
     * Clears fields
     */
    public void clear() {
        this.tHealth.setText("");
        this.tNames.setText("");
    }

    /**
     * Notify of successful undo.
     */
    public void undone(String name) {
        JOptionPane.showMessageDialog(this,
                name + " was successfully removed.");
    }

    /**
     * Enable undo and finish if InitOrd > 0
     */
    public void enableButtons(Boolean enable) {
        this.bUndo.setEnabled(enable);
        this.bFinish.setEnabled(enable);
    }

    /**
     * Set turn number
     */
    public void setTurn(int turn) {
        this.tTurns.setText("Turn: " + turn);
    }
}
