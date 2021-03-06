import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * The view component of the DnDCombatTracker.
 *
 * @author E-Persson-OSU
 * @version 0.23
 *
 */
@SuppressWarnings("serial")
public final class DnDView extends JFrame
        implements ActionListener, MouseListener {

    /**
     * JFrame
     */
    private final JFrame ENTER_FRAME;
    private JFrame MAIN_FRAME;

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
     * Scroll Panes
     */
    private final ScrollPane spMobMenu;

    /**
     * Text area for displaying turn order
     */
    //private final JTextArea taHolding;

    /**
     * Lets try a list
     */
    private final JList<String> lHolds, lTurnOrder, lMobMenu;

    /**
     * Buttons
     */
    private final JButton bEnter, bUndo, bNextPlayer, bHoldTurn, bFinish,
            bInsertTurn, bDamage, bHeal, bAddMob, bRemoveMob;

    /**
     * Radio Buttons
     */
    private final JRadioButton rbNPC, rbPlayer, rbMob;

    /**
     * Formatting
     */
    private final NumberFormat healthFormat;

    /**
     * Some final fields
     */
    private final int ENTER_MENU_ROWS = 2, ENTER_MENU_COLM = 1,
            TEXT_FIELD_WIDTH = 15, ENTER_INIT_HEIGHT = 192,
            ENTER_INIT_WIDTH = 512;
    //    private final int VERT_GRIDS_ENTER = 2, HOR_GRIDS_ENTER = 1,
    //            TEXT_AREA_HEIGHT = 20, TEXT_AREA_WIDTH = 20, ENTER_BUTTON_COLM = 2,
    //            ENTER_BUTTON_ROWS = 1, TEXT_FIELD_HEIGHT = 5;
    /**
     * Formatting
     */
    private final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 20);
    private final Font FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 20);
    private final Border BORDER = BorderFactory.createLineBorder(Color.GRAY);

    int dim = Toolkit.getDefaultToolkit().getScreenResolution();
    /**
     * Controller to interpret commands
     */
    private DnDController controller;

    /**
     * Current view: true = enter, false = main.
     */
    private boolean view, enterPlayer, enterNPC, enterMob;

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
        this.tSpecHoldOrder = new JTextField("Add or Hold: ");
        this.tTurns = new JTextField("Turn: 1");
        this.tSpecTurnOrder = new JTextField("Initiative Order: ");

        /*
         * Text areas
         */
        //this.taHolding = new JTextArea();

        /*
         * List
         */
        this.lTurnOrder = new JList<String>();
        this.lHolds = new JList<String>();
        this.lMobMenu = new JList<String>();

        /*
         * Buttons
         */
        //enter window buttons
        this.bEnter = new JButton("Enter");
        this.bUndo = new JButton("Undo");
        this.bFinish = new JButton("Finish");
        this.rbPlayer = new JRadioButton("Player");
        this.rbNPC = new JRadioButton("NPC");
        this.rbMob = new JRadioButton("Mob");

        //main window buttons
        this.bHoldTurn = new JButton("Hold Turn");
        this.bInsertTurn = new JButton("Insert Turn");
        this.bNextPlayer = new JButton("Next Player");
        this.bAddMob = new JButton("Add Mob");
        this.bDamage = new JButton("Damage");
        this.bHeal = new JButton("Heal");
        this.bRemoveMob = new JButton("Remove Mob");

        //variables
        this.enterPlayer = true;
        this.view = true;

        //Set Properties-------------------------------------------------------
        /*
         * Buttons
         */
        this.bUndo.setEnabled(false);
        this.bFinish.setEnabled(false);
        this.rbPlayer.setSelected(true);
        this.bHoldTurn.setFont(this.FONT_HEADER);
        this.bInsertTurn.setFont(this.FONT_HEADER);
        this.bNextPlayer.setFont(this.FONT_HEADER);
        this.bAddMob.setFont(this.FONT_HEADER);
        this.bRemoveMob.setFont(this.FONT_HEADER);
        this.bDamage.setFont(this.FONT_HEADER);
        this.bHeal.setFont(this.FONT_HEADER);

        /*
         * Text Areas
         */
        this.lTurnOrder.setBorder(this.BORDER);
        this.lTurnOrder.setFont(this.FONT_PLAIN);

        this.lHolds.setBorder(this.BORDER);
        this.lHolds.setFont(this.FONT_PLAIN);

        this.lMobMenu.setFont(this.FONT_PLAIN);
        this.lMobMenu.setBorder(this.BORDER);
        /*
         * Text Fields
         */
        this.tHealth.setColumns(this.TEXT_FIELD_WIDTH);
        this.tHealth.setFont(this.FONT_PLAIN);
        this.tHealth.setEnabled(false);

        this.tNames.setColumns(this.TEXT_FIELD_WIDTH);
        this.tNames.setFont(this.FONT_PLAIN);

        this.tSpecName.setEditable(false);
        this.tSpecName.setFont(this.FONT_HEADER);

        this.tSpecHealth.setEditable(false);
        this.tSpecHealth.setFont(this.FONT_HEADER);

        this.tTurns.setEditable(false);
        this.tTurns.setFont(this.FONT_HEADER);

        this.tSpecHoldOrder.setFont(this.FONT_HEADER);
        this.tSpecHoldOrder.setEditable(false);

        this.tSpecTurnOrder.setEditable(false);
        this.tSpecTurnOrder.setFont(this.FONT_HEADER);

        //Add ActionListener
        this.addActionListenerToButtons();

        //Add MouseListener
        this.lHolds.addMouseListener(this);
        this.lMobMenu.addMouseListener(this);
        this.lTurnOrder.addMouseListener(this);

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
        this.spMobMenu = new ScrollPane();

        /*
         * Panels for enter window
         */
        JPanel enterButtons = new JPanel(new GridLayout(1, 3));
        JPanel enterFields = new JPanel(new GridLayout(2, 2));
        JPanel enterRadioButtons = new JPanel(new GridLayout(3, 1));
        JPanel enterTop = new JPanel(new BorderLayout());

        /*
         * Panels for main window
         */
        this.MAIN_LEFT_PANEL = new JPanel(new BorderLayout());
        this.MAIN_RIGHT_PANEL = new JPanel(new GridLayout(3, 1));
        this.MAIN_RIGHT_TOP = new JPanel(new BorderLayout());
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
        enterRadioButtons.add(this.rbPlayer);
        enterRadioButtons.add(this.rbNPC);
        enterRadioButtons.add(this.rbMob);
        enterTop.add(enterRadioButtons, BorderLayout.WEST);
        enterTop.add(enterFields);

        /*
         * Add buttons and fields for main window
         */
        //left
        this.MAIN_LEFT_PANEL.add(this.tSpecTurnOrder, BorderLayout.NORTH);
        this.MAIN_LEFT_PANEL.add(this.lTurnOrder, BorderLayout.CENTER);
        this.MAIN_LEFT_PANEL.add(this.tTurns, BorderLayout.SOUTH);

        //right top
        this.MAIN_RIGHT_TOP_BUTTONS.add(this.bHoldTurn);
        this.MAIN_RIGHT_TOP_BUTTONS.add(this.bInsertTurn);
        this.MAIN_RIGHT_TOP.add(this.tSpecHoldOrder, BorderLayout.NORTH);
        this.MAIN_RIGHT_TOP.add(this.lHolds, BorderLayout.CENTER);
        this.MAIN_RIGHT_TOP.add(this.MAIN_RIGHT_TOP_BUTTONS,
                BorderLayout.SOUTH);
        //right middle
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bAddMob);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bRemoveMob);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bDamage);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bHeal);
        this.spMobMenu.add(this.lMobMenu);
        this.MAIN_RIGHT_MIDDLE.add(this.spMobMenu);
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
        this.ENTER_PANEL.add(enterTop);
        this.ENTER_PANEL.add(enterButtons);

        /*
         * Set up JFrames
         */
        //Set up the enter window
        this.ENTER_FRAME = new JFrame();
        this.ENTER_FRAME.setTitle("Enter Initiative Order");
        this.ENTER_FRAME.add(this.ENTER_PANEL);
        this.ENTER_FRAME.getRootPane().setDefaultButton(this.bEnter);
        this.ENTER_FRAME.pack();
        this.ENTER_FRAME.setSize(this.ENTER_INIT_WIDTH, this.ENTER_INIT_HEIGHT);
        this.ENTER_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.setLocation(this.dim / 2 - this.getSize().width / 2,
                this.dim / 2 - this.getSize().height / 2);
        this.ENTER_FRAME.setVisible(true);
        //Set up main window

    }

    //ACTION PROCESSING--------------------------------------------------------

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
        //System.out.println(source);
        if (source.equals(this.bEnter.getActionCommand())
                && this.tNames.getText().length() > 0) {
            this.controller.processEnterEvent();
        } else if (source.equals(this.bFinish.getActionCommand())) {
            this.controller.processFinishEvent();
        } else if (source.equals(this.bUndo.getActionCommand())) {
            this.controller.processUndoEvent();
        } else if (source.equals(this.bNextPlayer.getActionCommand())) {
            this.controller.processNextPlayerEvent();
        } else if (source.equals(this.bHoldTurn.getActionCommand())) {
            this.controller
                    .processHoldTurnEvent(this.lTurnOrder.getSelectedIndex());
        } else if (source.equals(this.bInsertTurn.getActionCommand())) {
            this.controller
                    .processInsertTurnEvent(this.lHolds.getSelectedIndex());
        } else if (source.equals(this.bDamage.getActionCommand())) {
            this.controller.processDamageEvent();
        } else if (source.equals(this.bHeal.getActionCommand())) {
            this.controller.processHealEvent();
        } else if (source.equals(this.bAddMob.getActionCommand())) {
            this.controller.processAddMobEvent(this.addedMobName(),
                    this.addedMobHealth());
        } else if (source.equals(this.bRemoveMob.getActionCommand())) {
            this.controller
                    .processRemoveMobEvent(this.lMobMenu.getSelectedIndex());
        } else if (source.equals(this.rbPlayer.getActionCommand())) {
            this.enterPlayer = true;
            this.enterNPC = false;
            this.enterMob = false;
            this.tHealth.setEnabled(false);
            this.rbPlayer.setSelected(true);
            this.rbNPC.setSelected(false);
            this.rbMob.setSelected(false);
            this.tNames.grabFocus();
        } else if (source.equals(this.rbNPC.getActionCommand())) {
            this.enterPlayer = false;
            this.enterNPC = true;
            this.enterMob = false;
            this.tHealth.setEnabled(true);
            this.rbPlayer.setSelected(false);
            this.rbNPC.setSelected(true);
            this.rbMob.setSelected(false);
            this.tNames.grabFocus();
        } else if (source.equals(this.rbMob.getActionCommand())) {
            this.enterPlayer = false;
            this.enterNPC = false;
            this.enterMob = true;
            this.tHealth.setEnabled(true);
            this.rbPlayer.setSelected(false);
            this.rbNPC.setSelected(false);
            this.rbMob.setSelected(true);
            this.tNames.grabFocus();
        }
        //Action done being processed
        this.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        String location = event.getSource().toString();
        //System.out.println("Click registered: " + location);
        if (location.equals(this.lHolds.toString())) {
            if (event.getClickCount() == 2) {
                //System.out.println("Registered Double Click: Holds");
                int index = this.lHolds.locationToIndex(event.getPoint());
                this.controller.processDoubleClickHolds(index);
            }
        } else if (location.equals(this.lTurnOrder.toString())) {
            if (event.getClickCount() == 2) {
                //System.out.println("Registered Double Click: Order");
                int index = this.lTurnOrder.locationToIndex(event.getPoint());
                this.controller.processDoubleClickTurnOrder(index);
            }
        } else if (location.equals(this.lMobMenu.toString())) {
            if (event.getClickCount() == 3) {
                int index = this.lMobMenu.locationToIndex(event.getPoint());
                this.controller.processTripleClickMobMenu(index);
            } else if (event.getClickCount() == 2) {
                //System.out.println("Registered Double Click: Mob Menu");
                int index = this.lMobMenu.locationToIndex(event.getPoint());
                this.controller.processDoubleClickMobMenu(index);
            }

        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    //EVENT METHOD HELPERS-----------------------------------------------------
    public void finish() {
        this.view = false;
        this.MAIN_FRAME = new JFrame();
        this.MAIN_FRAME.setTitle("Dungeons & Dragons Initiative Order Tracker");
        this.MAIN_FRAME.add(this.MAIN_PANEL);
        this.MAIN_FRAME.getRootPane().setDefaultButton(this.bNextPlayer);
        this.MAIN_FRAME.pack();
        this.MAIN_FRAME.setSize(768, 1024);
        this.MAIN_FRAME.setResizable(true);
        this.MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.dispose();
        this.MAIN_FRAME.setLocation(this.dim / 2 - this.getSize().width / 2,
                this.dim / 2 - this.getSize().height / 2);
        this.MAIN_FRAME.setVisible(true);

    }

    public String getTurnOrderSelected() {
        return this.lTurnOrder.getSelectedValue();
    }

    public String getHoldSelected() {
        return this.lHolds.getSelectedValue();
    }

    public void setFocusToName() {
        this.tNames.grabFocus();
    }

    public String addedMobName() {
        return JOptionPane.showInputDialog(this.MAIN_FRAME, "Enter Mob Name: ");
    }

    public int addedMobHealth() {
        int amount = Integer.parseInt(JOptionPane
                .showInputDialog(this.MAIN_FRAME, "Enter Mob Health:"));
        return amount;
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
        this.bAddMob.addActionListener(this);
        this.bRemoveMob.addActionListener(this);
        this.bDamage.addActionListener(this);
        this.bHeal.addActionListener(this);
        this.bInsertTurn.addActionListener(this);
        this.rbMob.addActionListener(this);
        this.rbNPC.addActionListener(this);
        this.rbPlayer.addActionListener(this);
    }

    /**
     * Register controller.
     *
     * @param controller
     */
    public void registerObserver(DnDController controller) {
        this.controller = controller;
    }

    /**
     * Pull info.
     */
    public String getNameFromField() {
        // String name = this.tNames.getText();
        //System.out.println("Retrieved: " + name);
        return this.tNames.getText();
    }

    public int getHealth() {
        String health = this.tHealth.getText();
        if (health.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(this.tHealth.getText());
        }
    }

    /**
     * Clears fields
     */
    public void clear() {
        this.tHealth.setValue(null);
        this.tNames.setText("");
    }

    /**
     * Notify of successful undo.
     */
    public void undone(String name) {
        JOptionPane.showMessageDialog(this.ENTER_FRAME,
                name + " was successfully removed.");
    }

    /**
     * Enable undo and finish if InitOrd > 0
     */
    public void enableEnterButtons(Boolean enable) {
        this.bUndo.setEnabled(enable);
        this.bFinish.setEnabled(enable);
    }

    /**
     * Enable HoldTurn if lOrd.size() != 0 && !lOrd.get(0).player()
     */
    public void enableMainButtons(int lOrdSize, int lHoldsSize, int lMobsSize) {
        this.bHoldTurn.setEnabled(lOrdSize > 0);
        this.bInsertTurn.setEnabled(lHoldsSize > 0);
        this.bDamage.setEnabled(lMobsSize > 0);
        this.bHeal.setEnabled(lMobsSize > 0);
        this.bNextPlayer.setEnabled(lOrdSize > 0);
        this.bRemoveMob.setEnabled(lMobsSize > 0);
    }

    /**
     * Set turn number
     */
    public void setTurn(int turn) {
        this.tTurns.setText("Turn: " + turn);
    }

    /**
     * Set initiative order text area text.
     */
    public void setInitOrdText(List<DChar> lOrd) {
        String[] arr = new String[lOrd.size()];
        int pos = 0;
        for (DChar ch : lOrd) {
            arr[pos] = ch.toStringOrder();
            pos++;
        }
        this.lTurnOrder.setListData(arr);
    }

    /**
     * Which view is it in.
     */
    public boolean getView() {
        return this.view;
    }

    /**
     * Set Mob Menu
     */
    public void updateMobMenu(List<DChar> lMob) {
        String[] arr = new String[lMob.size()];
        int pos = 0;
        for (DChar ch : lMob) {
            arr[pos] = ch.toStringMobMenu();
            pos++;
        }
        this.lMobMenu.setListData(arr);
    }

    /**
     * Set hold order
     */
    public void updateHoldList(List<DChar> lHold) {
        String[] arr = new String[lHold.size()];
        int pos = 0;
        for (DChar ch : lHold) {
            arr[pos] = ch.toStringHolds();
            pos++;
        }
        this.lHolds.setListData(arr);
    }

    public int healthDialog(boolean damage) {
        String title = "THIS SHOULDN'T APPEAR!!!";
        if (damage) {
            title = "Damage";
        } else {
            title = "Heal";
        }
        int amount = Integer.parseInt(JOptionPane
                .showInputDialog(this.MAIN_FRAME, "Enter Amount:", title));
        return amount;
    }

    public int getMobSelected() {
        return this.lMobMenu.getSelectedIndex();
    }

    public String enterWhere() {
        String where = "";
        if (this.enterPlayer) {
            where = "player";
        } else if (this.enterNPC) {
            where = "npc";
        } else if (this.enterMob) {
            where = "mob";
        }
        return where;
    }
}
