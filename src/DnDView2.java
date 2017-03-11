import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DnDView2 {

    protected Shell shell;

    protected DnDController thisController;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            DnDView2 window = new DnDView2();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        this.createContents();
        this.shell.open();
        this.shell.layout();
        while (!this.shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Register controller.
     *
     * @param controller
     */
    public void registerObserver(DnDController controller) {
        this.thisController = controller;
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        this.shell = new Shell();
        this.shell.setSize(450, 300);
        this.shell.setText("SWT Application");

        Button btnNewButton = new Button(this.shell, SWT.NONE);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
            private DnDController controller = DnDView2.this.thisController;

            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("New pressed");
                this.controller.processNewEvent();
            }
        });
        btnNewButton.setBounds(10, 10, 202, 116);
        btnNewButton.setText("New Character");

        Button btnNewButton_1 = new Button(this.shell, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
            private DnDController controller = DnDView2.this.thisController;

            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("Existing pressed");
                this.controller.processExistingEvent();
            }
        });
        btnNewButton_1.setBounds(218, 10, 200, 116);
        btnNewButton_1.setText("Existing Character");

        Button btnNewButton_2 = new Button(this.shell, SWT.NONE);
        btnNewButton_2.addSelectionListener(new SelectionAdapter() {
            private DnDController controller = DnDView2.this.thisController;

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                System.out.println("Finish pressed");
                this.controller.processFinishEvent();
            }
        });
        btnNewButton_2.setBounds(10, 132, 408, 102);
        btnNewButton_2.setText("Finish");

    }
}
