package memoryManager;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ControlPanel extends Frame {
    private static final int PADDING_TOP = 30;
    private static final int X_COL_1 = 5;
    private static final int X_COL_2 = 75;
    private static final int X_COL_3 = 145;
    private static final int X_COL_4 = 215;
    private static final int X_COL_5 = 290;
    private static final int X_COL_6 = 390;

    Kernel kernel;
    Button runButton = new Button("run");
    Button stepButton = new Button("step");
    Button resetButton = new Button("reset");
    Button exitButton = new Button("exit");
    List<Button> pagesButtons = new ArrayList<>();
    List<Label> labels = new ArrayList<>();
    Label statusValueLabel = new Label("STOP", Label.LEFT);
    Label timeValueLabel = new Label("0", Label.LEFT);
    Label instructionValueLabel = new Label("NONE", Label.LEFT);
    Label addressValueLabel = new Label("NULL", Label.LEFT);
    Label pageFaultValueLabel = new Label("NO", Label.LEFT);
    Label virtualPageValueLabel = new Label("x", Label.LEFT);
    Label physicalPageValueLabel = new Label("0", Label.LEFT);
    Label RValueLabel = new Label("0", Label.LEFT);
    Label MValueLabel = new Label("0", Label.LEFT);
    Label inMemTimeValueLabel = new Label("0", Label.LEFT);
    Label lastTouchTimeValueLabel = new Label("0", Label.LEFT);
    Label lowValueLabel = new Label("0", Label.LEFT);
    Label highValueLabel = new Label("0", Label.LEFT);
    Label pageCounterValueLabel = new Label("0", Label.LEFT);

    public ControlPanel(String title) {
        super(title);
        for (int i = 0; i < 64; i++) {
            pagesButtons.add(new Button("page " + (i)));
            labels.add(new Label(null, Label.CENTER));
        }
    }

    private void createButton(Button runButton, int x, int y, int width, int height) {
        runButton.setForeground(Color.blue);
        runButton.setBackground(Color.lightGray);
        runButton.setBounds(x, y, width, height);
        add(runButton);
    }

    public void init(Kernel useKernel, String commands, String config) {
        kernel = useKernel;
        kernel.setControlPanel(this);
        setLayout(null);
        setBackground(Color.white);
        setForeground(Color.black);
        setSize(660, 600);
        setFont(new Font("Courier", Font.PLAIN, 12));

        createButton(runButton, X_COL_1, PADDING_TOP, 70, 15);
        createButton(stepButton, X_COL_2, PADDING_TOP, 70, 15);
        createButton(resetButton, X_COL_3, PADDING_TOP, 70, 15);
        createButton(exitButton, X_COL_4, PADDING_TOP, 70, 15);

        for (int i = 0; i < pagesButtons.size(); i++) {
            Button button = (Button) pagesButtons.get(i);
            if (i < 32) {
                button.setBounds(X_COL_1, (i + 2) * 15 + PADDING_TOP, 70, 15);
            } else {
                button.setBounds(X_COL_3, ((i % 32) + 2) * 15 + PADDING_TOP, 70, 15);
            }
            button.setForeground(Color.magenta);
            button.setBackground(Color.lightGray);
            add(button);
        }

        statusValueLabel.setBounds(X_COL_6, PADDING_TOP, 100, 15);
        add(statusValueLabel);

        timeValueLabel.setBounds(X_COL_6, 15 + PADDING_TOP, 100, 15);
        add(timeValueLabel);

        instructionValueLabel.setBounds(X_COL_6, 45 + PADDING_TOP, 100, 15);
        add(instructionValueLabel);

        addressValueLabel.setBounds(X_COL_6, 60 + PADDING_TOP, 230, 15);
        add(addressValueLabel);

        pageFaultValueLabel.setBounds(X_COL_6, 90 + PADDING_TOP, 100, 15);
        add(pageFaultValueLabel);

        virtualPageValueLabel.setBounds(X_COL_6, 120 + PADDING_TOP, 200, 15);
        add(virtualPageValueLabel);

        physicalPageValueLabel.setBounds(X_COL_6, 135 + PADDING_TOP, 200, 15);
        add(physicalPageValueLabel);

        RValueLabel.setBounds(X_COL_6, 150 + PADDING_TOP, 200, 15);
        add(RValueLabel);

        MValueLabel.setBounds(X_COL_6, 165 + PADDING_TOP, 200, 15);
        add(MValueLabel);

        inMemTimeValueLabel.setBounds(X_COL_6, 180 + PADDING_TOP, 200, 15);
        add(inMemTimeValueLabel);

        lastTouchTimeValueLabel.setBounds(X_COL_6, 195 + PADDING_TOP, 200, 15);
        add(lastTouchTimeValueLabel);

        lowValueLabel.setBounds(X_COL_6, 210 + PADDING_TOP, 230, 15);
        add(lowValueLabel);

        highValueLabel.setBounds(X_COL_6, 225 + PADDING_TOP, 230, 15);
        add(highValueLabel);

        pageCounterValueLabel.setBounds(X_COL_6, 240 + PADDING_TOP, 230, 15);
        add(pageCounterValueLabel);

        Label virtualOneLabel = new Label("virtual", Label.CENTER);
        virtualOneLabel.setBounds(X_COL_1, 15 + PADDING_TOP, 70, 15);
        add(virtualOneLabel);

        Label virtualTwoLabel = new Label("virtual", Label.CENTER);
        virtualTwoLabel.setBounds(X_COL_3, 15 + PADDING_TOP, 70, 15);
        add(virtualTwoLabel);

        Label physicalOneLabel = new Label("physical", Label.CENTER);
        physicalOneLabel.setBounds(X_COL_2, 15 + PADDING_TOP, 70, 15);
        add(physicalOneLabel);

        Label physicalTwoLabel = new Label("physical", Label.CENTER);
        physicalTwoLabel.setBounds(X_COL_4, 15 + PADDING_TOP, 70, 15);
        add(physicalTwoLabel);

        Label statusLabel = new Label("status: ", Label.LEFT);
        statusLabel.setBounds(X_COL_5, PADDING_TOP, 65, 15);
        add(statusLabel);

        Label timeLabel = new Label("time: ", Label.LEFT);
        timeLabel.setBounds(X_COL_5, 15 + PADDING_TOP, 50, 15);
        add(timeLabel);

        Label instructionLabel = new Label("instruction: ", Label.LEFT);
        instructionLabel.setBounds(X_COL_5, 45 + PADDING_TOP, 100, 15);
        add(instructionLabel);

        Label addressLabel = new Label("address: ", Label.LEFT);
        addressLabel.setBounds(X_COL_5, 60 + PADDING_TOP, 85, 15);
        add(addressLabel);

        Label pageFaultLabel = new Label("page fault: ", Label.LEFT);
        pageFaultLabel.setBounds(X_COL_5, 90 + PADDING_TOP, 100, 15);
        add(pageFaultLabel);

        Label virtualPageLabel = new Label("virtual page: ", Label.LEFT);
        virtualPageLabel.setBounds(X_COL_5, 120 + PADDING_TOP, 110, 15);
        add(virtualPageLabel);

        Label physicalPageLabel = new Label("physical page: ", Label.LEFT);
        physicalPageLabel.setBounds(X_COL_5, 135 + PADDING_TOP, 110, 15);
        add(physicalPageLabel);

        Label RLabel = new Label("R: ", Label.LEFT);
        RLabel.setBounds(X_COL_5, 150 + PADDING_TOP, 110, 15);
        add(RLabel);

        Label MLabel = new Label("M: ", Label.LEFT);
        MLabel.setBounds(X_COL_5, 165 + PADDING_TOP, 110, 15);
        add(MLabel);

        Label inMemTimeLabel = new Label("inMemTime: ", Label.LEFT);
        inMemTimeLabel.setBounds(X_COL_5, 180 + PADDING_TOP, 110, 15);
        add(inMemTimeLabel);

        Label lastTouchTimeLabel = new Label("lastTouchTime: ", Label.LEFT);
        lastTouchTimeLabel.setBounds(X_COL_5, 195 + PADDING_TOP, 110, 15);
        add(lastTouchTimeLabel);

        Label lowLabel = new Label("low: ", Label.LEFT);
        lowLabel.setBounds(X_COL_5, 210 + PADDING_TOP, 110, 15);
        add(lowLabel);

        Label highLabel = new Label("high: ", Label.LEFT);
        highLabel.setBounds(X_COL_5, 225 + PADDING_TOP, 110, 15);
        add(highLabel);

        Label pageCounterLabel = new Label("page counter: ", Label.LEFT);
        pageCounterLabel.setBounds(X_COL_5, 240 + PADDING_TOP, 110, 15);
        add(pageCounterLabel);

        for (int i = 0; i < labels.size(); i++) {
            Label label = (Label) labels.get(i);
            if (i < 32) {
                label.setBounds(X_COL_2, (i + 2) * 15 + PADDING_TOP, 60, 15);
            } else {
                label.setBounds(X_COL_4, ((i % 32) + 2) * 15 + PADDING_TOP, 60, 15);
            }
            label.setForeground(Color.red);
            label.setFont(new Font("Courier", Font.PLAIN, 10));
            add(label);
        }

        kernel.init(commands, config);

        setVisible(true);
    }

    public void paintPage(Page page) {
        virtualPageValueLabel.setText(Integer.toString(page.id));
        physicalPageValueLabel.setText(Integer.toString(page.physical));
        RValueLabel.setText(Integer.toString(page.R));
        MValueLabel.setText(Integer.toString(page.M));
        inMemTimeValueLabel.setText(Integer.toString(page.inMemTime));
        lastTouchTimeValueLabel.setText(Integer.toString(page.lastTouchTime));
        lowValueLabel.setText(Long.toString(page.low, Kernel.addressradix));
        highValueLabel.setText(Long.toString(page.high, Kernel.addressradix));
        pageCounterValueLabel.setText(Long.toString(page.page_counter));
    }

    public void setStatus(String status) {
        statusValueLabel.setText(status);
    }

    public void addPhysicalPage(int pageNum, int physicalPage) {
        if (physicalPage >= 0 && physicalPage < 64) {
            ((Label) labels.get(physicalPage)).setText("page " + pageNum);
        }
    }

    public void removePhysicalPage(int physicalPage) {
        if (physicalPage >= 0 && physicalPage < 64) {
            ((Label) labels.get(physicalPage)).setText(null);
        }
    }

    public boolean action(Event e, Object arg) {
        if (e.target == runButton) {
            setStatus("RUN");
            runButton.setEnabled(false);
            stepButton.setEnabled(false);
            resetButton.setEnabled(false);
            kernel.run();
            setStatus("STOP");
            resetButton.setEnabled(true);
            return true;
        } else if (e.target == stepButton) {
            setStatus("STEP");
            kernel.step();
            if (kernel.runcycles == kernel.runs) {
                stepButton.setEnabled(false);
                runButton.setEnabled(false);
            }
            setStatus("STOP");
            return true;
        } else if (e.target == resetButton) {
            kernel.reset();
            runButton.setEnabled(true);
            stepButton.setEnabled(true);
            return true;
        } else if (e.target == exitButton) {
            System.exit(0);
            return true;
        } else if (pagesButtons.contains(e.target)) {
            kernel.getPage(pagesButtons.indexOf(e.target));
            return true;
        } else {
            return false;
        }
    }
}