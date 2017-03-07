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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
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
    private final JFrame ENTER_FORM;
    private JFrame MAIN_FRAME;

    /**
     * JPanels
     */
    //Enter Frame
    private final JPanel ENTER_PANEL;
    //Enter Form
    private final JPanel ENTER_FORM_MAIN, ENTER_FORM_TEXT,
            ENTER_FORM_BUTTONS_TOP, ENTER_FORM_INIT_PANEL,
            ENTER_FORM_BUTTONS_BOTTOM;
    //Main Frame
    private final JPanel MAIN_PANEL;
    private final JPanel MAIN_LEFT_PANEL;
    private final JPanel MAIN_RIGHT_PANEL;
    private final JPanel MAIN_RIGHT_TOP, MAIN_RIGHT_MIDDLE, MAIN_RIGHT_BOTTOM;
    private final JPanel MAIN_RIGHT_TOP_BUTTONS;
    private final JPanel MAIN_RIGHT_MIDDLE_BUTTONS;

    /**
     * JTabbedPanel
     */
    private final JTabbedPane tbMobNPCHold;

    /**
     * Text Fields
     */
    //Main frame
    private final JTextField tTurns, tSpecTurnOrder, tSpecHoldOrder, tSpecMob,
            tSpecNPC;
    //Enter Form
    private final JTextField tNames, tSpecName, tSpecHP, tHP, tSpecTempHP,
            tTempHP, tSpecMaxHP, tMaxHP, tSpecInitRoll, tInitRoll;

    /**
     * Scroll Panes
     */
    private final ScrollPane spMobMenu;

    /**
     * JLists to contain Holds, Turn Order, Mobs, and NPCs
     */
    private final JList<String> lHolds, lTurnOrder, lMobMenu, lNPC;

    /**
     * Buttons
     */
    //Enter Frame
    private final JButton bNew, bExisting, bFinish;
    //Enter Form
    private final JButton bAdd, bAddAndSave, bClear, bRoll;
    //Main Frame
    private final JButton bNext, bHoldTurn, bInsertTurn, bDamageMob, bHealMob,
            bAddMob, bRemoveMob, bDamageNPC, bHealNPC, bAddNPC, bRemoveNPC;

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
    private final int ENTER_MENU_ROWS = 1, ENTER_MENU_COLM = 3,
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
        //Enter Form
        this.tSpecName = new JTextField("Name:");
        this.tNames = new JTextField();
        this.tSpecHP = new JTextField("Health: ");
        this.healthFormat = NumberFormat.getIntegerInstance();
        this.tHP = new JTextField();
        this.tSpecMaxHP = new JTextField("Max HP");
        this.tMaxHP = new JTextField();
        this.tSpecTempHP = new JTextField("Temp HP");
        this.tTempHP = new JTextField();
        this.tSpecInitRoll = new JTextField("Initiative:");
        this.tInitRoll = new JTextField();

        //Main Frame
        this.tSpecHoldOrder = new JTextField("Hold: ");
        this.tSpecMob = new JTextField("Mobs: ");
        this.tTurns = new JTextField("Turn: 1");
        this.tSpecTurnOrder = new JTextField("Initiative Order: ");
        this.tSpecNPC = new JTextField("NPC: ");

        /*
         * List
         */
        this.lTurnOrder = new JList<String>();
        this.lHolds = new JList<String>();
        this.lMobMenu = new JList<String>();
        this.lNPC = new JList<String>();

        /*
         * Buttons
         */
        //enter window buttons
        this.bNew = new JButton("New");
        this.bExisting = new JButton("Existing");
        this.bFinish = new JButton("Finish");

        //Enter Form Buttons
        this.bAdd = new JButton("Add");
        this.bAddAndSave = new JButton("Save and Add");
        this.bClear = new JButton("Clear");
        this.rbPlayer = new JRadioButton("Player");
        this.rbNPC = new JRadioButton("NPC");
        this.rbMob = new JRadioButton("Mob");
        this.bRoll = new JButton("Roll");

        //main window buttons
        this.bHoldTurn = new JButton("Hold Turn");
        this.bInsertTurn = new JButton("Insert Turn");
        this.bNext = new JButton("Next Player");
        this.bAddMob = new JButton("Add");
        this.bRemoveMob = new JButton("Remove");
        this.bDamageMob = new JButton("Damage");
        this.bHealMob = new JButton("Heal");
        this.bAddNPC = new JButton("Add");
        this.bRemoveNPC = new JButton("Remove");
        this.bHealNPC = new JButton("Heal");
        this.bDamageNPC = new JButton("Damage");

        //variables
        this.enterPlayer = true;
        this.view = true;

        //Set Properties-------------------------------------------------------
        /*
         * Buttons
         */
        //Enter Menu
        this.bNew.setEnabled(true);
        this.bExisting.setEnabled(true);
        this.bFinish.setEnabled(true);
        //Enter Form
        this.rbPlayer.setSelected(true);
        //Main Form
        this.bHoldTurn.setFont(this.FONT_HEADER);
        this.bInsertTurn.setFont(this.FONT_HEADER);
        this.bNext.setFont(this.FONT_HEADER);
        this.bAddMob.setFont(this.FONT_HEADER);
        this.bRemoveMob.setFont(this.FONT_HEADER);
        this.bDamageMob.setFont(this.FONT_HEADER);
        this.bHealMob.setFont(this.FONT_HEADER);

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
        //        this.tHP.setColumns(this.TEXT_FIELD_WIDTH);
        this.tHP.setFont(this.FONT_PLAIN);
        this.tHP.setEnabled(false);

        //        this.tNames.setColumns(this.TEXT_FIELD_WIDTH);
        this.tNames.setFont(this.FONT_PLAIN);

        this.tSpecName.setEditable(false);
        this.tSpecName.setFont(this.FONT_HEADER);

        this.tSpecHP.setEditable(false);
        this.tSpecHP.setFont(this.FONT_HEADER);

        //        this.tMaxHP.setColumns(this.TEXT_FIELD_WIDTH);
        this.tMaxHP.setFont(this.FONT_PLAIN);
        this.tMaxHP.setEnabled(false);

        this.tSpecMaxHP.setEditable(false);
        this.tSpecMaxHP.setFont(this.FONT_HEADER);

        //        this.tTempHP.setColumns(this.TEXT_FIELD_WIDTH);
        this.tTempHP.setFont(this.FONT_PLAIN);
        this.tTempHP.setEnabled(false);

        this.tSpecTempHP.setEditable(false);
        this.tSpecTempHP.setFont(this.FONT_HEADER);

        //        this.tInitRoll.setColumns(this.TEXT_FIELD_WIDTH);
        this.tInitRoll.setFont(this.FONT_PLAIN);
        this.tInitRoll.setEnabled(false);

        this.tSpecInitRoll.setEditable(false);
        this.tSpecInitRoll.setFont(this.FONT_HEADER);

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
         * Tabbed Pane
         */
        this.tbMobNPCHold = new JTabbedPane();
        /*
         * Main window
         */
        this.MAIN_PANEL = new JPanel(new GridLayout(1, 2));
        this.spMobMenu = new ScrollPane();

        /*
         * Panels for enter window
         */
        JPanel enterButtons = new JPanel(new GridLayout(1, 3));

        /*
         * Panels for enter form
         */
        this.ENTER_FORM_BUTTONS_TOP = new JPanel(new GridLayout(1, 3));
        this.ENTER_FORM_BUTTONS_BOTTOM = new JPanel(new GridLayout(1, 3));
        this.ENTER_FORM_INIT_PANEL = new JPanel(new GridLayout(1, 2));
        this.ENTER_FORM_MAIN = new JPanel(new BorderLayout());
        this.ENTER_FORM_TEXT = new JPanel(new GridLayout(5, 2));

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

        //Adding----------------------------------------------------------------

        /*
         * Add buttons for enter window
         */
        enterButtons.add(this.bNew);
        enterButtons.add(this.bExisting);
        enterButtons.add(this.bFinish);

        /*
         * Add buttons and fields for enter form
         */
        this.ENTER_FORM_BUTTONS_TOP.add(this.rbPlayer);
        this.ENTER_FORM_BUTTONS_TOP.add(this.rbNPC);
        this.ENTER_FORM_BUTTONS_TOP.add(this.rbMob);
        this.ENTER_FORM_TEXT.add(this.tSpecName);
        this.ENTER_FORM_TEXT.add(this.tNames);
        this.ENTER_FORM_TEXT.add(this.tSpecHP);
        this.ENTER_FORM_TEXT.add(this.tHP);
        this.ENTER_FORM_TEXT.add(this.tSpecTempHP);
        this.ENTER_FORM_TEXT.add(this.tTempHP);
        this.ENTER_FORM_TEXT.add(this.tSpecMaxHP);
        this.ENTER_FORM_TEXT.add(this.tMaxHP);
        this.ENTER_FORM_TEXT.add(this.tSpecInitRoll);
        this.ENTER_FORM_INIT_PANEL.add(this.tInitRoll);
        this.ENTER_FORM_INIT_PANEL.add(this.bRoll);
        this.ENTER_FORM_TEXT.add(this.ENTER_FORM_INIT_PANEL);
        this.ENTER_FORM_BUTTONS_BOTTOM.add(this.bAdd);
        this.ENTER_FORM_BUTTONS_BOTTOM.add(this.bAddAndSave);
        this.ENTER_FORM_BUTTONS_BOTTOM.add(this.bClear);
        this.ENTER_FORM_MAIN.add(this.ENTER_FORM_BUTTONS_TOP,
                BorderLayout.NORTH);
        this.ENTER_FORM_MAIN.add(this.ENTER_FORM_TEXT);
        this.ENTER_FORM_MAIN.add(this.ENTER_FORM_BUTTONS_BOTTOM,
                BorderLayout.SOUTH);

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
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bDamageMob);
        this.MAIN_RIGHT_MIDDLE_BUTTONS.add(this.bHealMob);
        this.spMobMenu.add(this.lMobMenu);
        this.MAIN_RIGHT_MIDDLE.add(this.spMobMenu);
        this.MAIN_RIGHT_MIDDLE.add(this.MAIN_RIGHT_MIDDLE_BUTTONS);
        //right bottom
        this.MAIN_RIGHT_BOTTOM.add(this.bNext);
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
        this.ENTER_PANEL.add(enterButtons);

        /*
         * Set up JFrames
         */
        //Set up the enter window
        this.ENTER_FRAME = new JFrame();
        this.ENTER_FRAME.setTitle("Enter Combatants");
        this.ENTER_FRAME.add(this.ENTER_PANEL);
        this.ENTER_FRAME.getRootPane().setDefaultButton(this.bNew);
        this.ENTER_FRAME.pack();
        this.ENTER_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.setLocation(this.dim / 2 - this.getSize().width / 2,
                this.dim / 2 - this.getSize().height / 2);
        this.ENTER_FRAME.setVisible(true);
        //Set up enter form
        this.ENTER_FORM = new JFrame();
        this.ENTER_FORM.setTitle("Create Combatant");
        this.ENTER_FORM.add(this.ENTER_FORM_MAIN);
        this.ENTER_FORM.getRootPane().setDefaultButton(this.bAddAndSave);
        this.ENTER_FORM.pack();
        this.ENTER_FORM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FORM.setLocation(this.dim / 2 - this.getSize().width / 2,
                this.dim / 2 - this.getSize().height / 2);
        this.ENTER_FORM.setVisible(false);

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
        //        if (source.equals(this.bNew.getActionCommand())) {
        //            //TODO
        //            this.controller.processNewEvent();
        //        } else if (source.equals(this.bFinish.getActionCommand())) {
        //            //TODO
        //            this.controller.processFinishEvent();
        //        } else if (source.equals(this.bExisting.getActionCommand())) {
        //            //TODO
        //            this.controller.processExistingEvent();
        //        } else if (source.equals(this.bNext.getActionCommand())) {
        //            //TODO
        //            this.controller.processNextEvent();
        //        } else if (source.equals(this.bHoldTurn.getActionCommand())) {
        //            this.controller
        //                    .processHoldTurnEvent(this.lTurnOrder.getSelectedIndex());
        //        } else if (source.equals(this.bInsertTurn.getActionCommand())) {
        //            this.controller
        //                    .processInsertTurnEvent(this.lHolds.getSelectedIndex());
        //        } else if (source.equals(this.bDamageMob.getActionCommand())) {
        //            this.controller.processDamageEvent();
        //        } else if (source.equals(this.bHealMob.getActionCommand())) {
        //            this.controller.processHealEvent();
        //        } else if (source.equals(this.bAddMob.getActionCommand())) {
        //            this.controller.processAddMobEvent(this.addedMobName(),
        //                    this.addedMobHealMobth());
        //        } else if (source.equals(this.bRemoveMob.getActionCommand())) {
        //            this.controller
        //                    .processRemoveMobEvent(this.lMobMenu.getSelectedIndex());
        //        } else if (source.equals(this.rbPlayer.getActionCommand())) {
        //            this.enterPlayer = true;
        //            this.enterNPC = false;
        //            this.enterMob = false;
        //            this.tHP.setEnabled(false);
        //            this.rbPlayer.setSelected(true);
        //            this.rbNPC.setSelected(false);
        //            this.rbMob.setSelected(false);
        //            this.tNames.grabFocus();
        //        } else if (source.equals(this.rbNPC.getActionCommand())) {
        //            this.enterPlayer = false;
        //            this.enterNPC = true;
        //            this.enterMob = false;
        //            this.tHP.setEnabled(true);
        //            this.rbPlayer.setSelected(false);
        //            this.rbNPC.setSelected(true);
        //            this.rbMob.setSelected(false);
        //            this.tNames.grabFocus();
        //        } else if (source.equals(this.rbMob.getActionCommand())) {
        //            this.enterPlayer = false;
        //            this.enterNPC = false;
        //            this.enterMob = true;
        //            this.tHP.setEnabled(true);
        //            this.rbPlayer.setSelected(false);
        //            this.rbNPC.setSelected(false);
        //            this.rbMob.setSelected(true);
        //            this.tNames.grabFocus();
        //        }
        if (source.equals(this.bNew.getActionCommand())) {
            this.newAction();
            this.controller.processNewEvent();
        } else if (source.equals(this.bExisting.getActionCommand())) {
            this.existingAction();
        } else if (source.equals(this.bFinish.getActionCommand())) {
            this.finishAction();
        } else if (source.equals(this.bAdd.getActionCommand())) {
            this.addAction();
        } else if (source.equals(this.bAddAndSave.getActionCommand())) {
            this.addAndSaveAction();
        } else if (source.equals(this.bClear.getActionCommand())) {
            this.clearAction();
        } else if (source.equals(this.bRoll.getActionCommand())) {
            this.rollAction();
        } else if (source.equals(this.rbMob.getActionCommand())) {
            this.mobAction();
        } else if (source.equals(this.rbNPC.getActionCommand())) {
            this.npcAction();
        } else if (source.equals(this.rbPlayer.getActionCommand())) {
            this.playerAction();
        } else if (source.equals(this.bNext.getActionCommand())) {
            this.nextAction();
        } else if (source.equals(this.bHoldTurn.getActionCommand())) {
            this.holdTurnAction();
        } else if (source.equals(this.bInsertTurn.getActionCommand())) {
            this.insertTurnAction();
        } else if (source.equals(this.bAddMob.getActionCommand())) {
            this.addMobAction();
        } else if (source.equals(this.bRemoveMob.getActionCommand())) {
            this.removeMobAction();
        } else if (source.equals(this.bHealMob.getActionCommand())) {
            this.healMobAction();
        } else if (source.equals(this.bDamageMob.getActionCommand())) {
            this.damageMobAction();
        } else if (source.equals(this.bAddNPC.getActionCommand())) {
            this.addNPCAction();
        } else if (source.equals(this.bRemoveNPC.getActionCommand())) {
            this.removeNPCAction();
        } else if (source.equals(this.bDamageNPC.getActionCommand())) {
            this.damageNPCAction();
        } else if (source.equals(this.bHealNPC.getActionCommand())) {
            this.healNPCAction();
        } else {
            System.out.println("ERROR: ACTION NOT IMPLEMENTED");
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
    //    public void finish() {
    //        this.view = false;
    //        this.MAIN_FRAME = new JFrame();
    //        this.MAIN_FRAME.setTitle("Dungeons & Dragons Initiative Order Tracker");
    //        this.MAIN_FRAME.add(this.MAIN_PANEL);
    //        this.MAIN_FRAME.getRootPane().setDefaultButton(this.bNext);
    //        this.MAIN_FRAME.pack();
    //        this.MAIN_FRAME.setSize(768, 1024);
    //        this.MAIN_FRAME.setResizable(true);
    //        this.MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //        this.ENTER_FRAME.dispose();
    //        this.MAIN_FRAME.setLocation(this.dim / 2 - this.getSize().width / 2,
    //                this.dim / 2 - this.getSize().height / 2);
    //        this.MAIN_FRAME.setVisible(true);
    //
    //    }
    //
    //    public String getTurnOrderSelected() {
    //        return this.lTurnOrder.getSelectedValue();
    //    }
    //
    //    public String getHoldSelected() {
    //        return this.lHolds.getSelectedValue();
    //    }
    //
    //    public void setFocusToName() {
    //        this.tNames.grabFocus();
    //    }
    //
    //    public String addedMobName() {
    //        return JOptionPane.showInputDialog(this.MAIN_FRAME, "Enter Mob Name: ");
    //    }
    //
    //    public int addedMobHealMobth() {
    //        int amount = Integer.parseInt(JOptionPane
    //                .showInputDialog(this.MAIN_FRAME, "Enter Mob Health:"));
    //        return amount;
    //    }
    //ACTION METHODS-----------------------------------------------------------
    public void newAction() {
        //Opens ENTER_FORM and allows user to fill in the fields
        this.rbPlayer.setSelected(true);
        this.tNames.setEnabled(true);
        this.tHP.setEnabled(false);
        this.tMaxHP.setEnabled(false);
        this.tTempHP.setEnabled(false);
        this.tInitRoll.setEnabled(true);
        this.bRoll.setEnabled(false);
        this.ENTER_FORM.setVisible(true);
    }

    public void existingAction() {
        //TODO
    }

    public void finishAction() {
        this.view = false;
        this.MAIN_FRAME = new JFrame();
        this.MAIN_FRAME.setTitle("Dungeons & Dragons Initiative Order Tracker");
        this.MAIN_FRAME.add(this.MAIN_PANEL);
        this.MAIN_FRAME.getRootPane().setDefaultButton(this.bNext);
        this.MAIN_FRAME.pack();
        this.MAIN_FRAME.setSize(768, 1024);
        this.MAIN_FRAME.setResizable(true);
        this.MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ENTER_FRAME.dispose();
        this.MAIN_FRAME.setLocation(this.dim / 2 - this.getSize().width / 2,
                this.dim / 2 - this.getSize().height / 2);
        this.MAIN_FRAME.setVisible(true);
    }

    public void addAction() {

    }

    public void addAndSaveAction() {

    }

    public void clearAction() {

    }

    public void rollAction() {

    }

    public void mobAction() {

    }

    public void npcAction() {

    }

    public void playerAction() {

    }

    public void nextAction() {

    }

    public void holdTurnAction() {

    }

    public void insertTurnAction() {

    }

    public void addMobAction() {

    }

    public void removeMobAction() {

    }

    public void healMobAction() {

    }

    public void damageMobAction() {

    }

    public void addNPCAction() {

    }

    public void removeNPCAction() {

    }

    public void damageNPCAction() {

    }

    public void healNPCAction() {

    }

    /**
     * Create buttons.
     */
    private void addActionListenerToButtons() {
        //Enter Menu
        this.bFinish.addActionListener(this);
        this.bNew.addActionListener(this);
        this.bExisting.addActionListener(this);
        //Enter Form
        this.rbMob.addActionListener(this);
        this.rbNPC.addActionListener(this);
        this.rbPlayer.addActionListener(this);
        this.bRoll.addActionListener(this);
        this.bAdd.addActionListener(this);

        this.bHoldTurn.addActionListener(this);
        this.bNext.addActionListener(this);

        this.bAddMob.addActionListener(this);
        this.bRemoveMob.addActionListener(this);
        this.bDamageMob.addActionListener(this);
        this.bHealMob.addActionListener(this);
        this.bInsertTurn.addActionListener(this);

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

    public int getHP() {
        String health = this.tHP.getText();
        if (health.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(this.tHP.getText());
        }
    }

    /**
     * Clears fields
     */
    public void clear() {
        //TODO
    }

    /**
     * Enable undo and finish if InitOrd > 0
     */
    public void enableEnterButtons(Boolean enable) {
        this.bExisting.setEnabled(enable);
        this.bFinish.setEnabled(enable);
    }

    /**
     * Enable HoldTurn if lOrd.size() != 0 && !lOrd.get(0).player()
     */
    public void enableMainButtons(int lOrdSize, int lHoldsSize, int lMobsSize) {
        this.bHoldTurn.setEnabled(lOrdSize > 0);
        this.bInsertTurn.setEnabled(lHoldsSize > 0);
        this.bDamageMob.setEnabled(lMobsSize > 0);
        this.bHealMob.setEnabled(lMobsSize > 0);
        this.bNext.setEnabled(lOrdSize > 0);
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
            arr[pos] = ch.toStringMob();
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
