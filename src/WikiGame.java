import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;


public class WikiGame implements ActionListener {
    private JFrame mainFrame;
    private JPanel NorthPanel;
    private JPanel SouthPanel;
    private JPanel SuperNorthPanel;
    private JLabel headerLabel;
    private JLabel textLabel1;
    private JLabel textLabel2;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel statusControlPanel;
    private JTextArea outputArea;
    private JPanel textPanel1;
    private JPanel textPanel2;
    private JScrollPane scroll;
    //    private JPanel hold= new JPanel();
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextField ta;
    private JTextField tb;
    private int WIDTH=800;
    private int HEIGHT=700;
    String blankArea = "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n";



    public WikiGame() {
        prepareGUI();
    }

    public static void main(String[] args) {
        WikiGame wikiGame = new WikiGame();
        wikiGame.showEventDemo();
    }

    public String HtmlRead(String url1 , String url2) {
        try {
            System.out.println("test1");

            URL newURL;
            String depth = null;

            boolean Break = false;

            ArrayList<String> path = new ArrayList<>();
            ArrayList<String> output = new ArrayList<>();
            String outputString = new String("");
            ArrayList<String> queue = new ArrayList<>();
            queue.add(url1);

            int i = 0;
            while(queue.get(i) != null) {
                System.out.println(queue.size());
                System.out.println("test2");

                newURL = new URL(queue.get(i));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(newURL.openStream())
                );
                String line;

                Pattern pat = Pattern.compile("a href=");
                Matcher lineMatcher;
                String subline;
                String subline2;
                while ( (line = reader.readLine()) != null ) {
                    if (queue.get(0) != null) {
                        System.out.println("good");
                    }
                    System.out.println("test3");
                    System.out.println(line);

                        lineMatcher = pat.matcher(line);
                        if (lineMatcher.find() == true) {
                            System.out.println("test4");
                            System.out.println(queue.get(0));
                            System.out.println(line);

                            subline = line.substring(line.indexOf("a href=\"https:") + 8);
                            System.out.println(subline);
                            subline = subline.substring(0, subline.indexOf("\""));
                            System.out.println(subline);
                            queue.add(subline);

                            subline2 = line.substring(line.indexOf("\">") + 2, subline.indexOf("<"));
                            path.add(subline2);

                            if (subline == url2) {
                                System.out.println("test7");
                                depth = String.format("%d", i);
                                reader.close();
                                System.out.println("break");
                                queue.clear();
                            }
                        }

                }
                System.out.println(queue.size());
                queue.remove(0);
                i++;

            }

            output.add(depth);
            output.addAll(path);
            for(int z = 0; z < output.size(); z++) {
                outputString = outputString + output.get(z) + "\n";
            }
            return outputString;

        } catch(Exception ex) {
            System.out.println(ex);

        }


        return null;
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        NorthPanel = new JPanel();
        SouthPanel = new JPanel();
        NorthPanel.setLayout(new BorderLayout());
        SuperNorthPanel = new JPanel();

        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);

        textPanel1 = new JPanel();
        textPanel1.setLayout(new BorderLayout());
        textPanel2 = new JPanel();
        textPanel2.setLayout(new BorderLayout());
        SouthPanel = new JPanel();
        SouthPanel.setLayout(new GridLayout(1,1));
        SuperNorthPanel = new JPanel();
        SuperNorthPanel.setLayout(new GridLayout(2, 1));

        /* change layout to: url and search term boxes on the top left,
        submit and cancel buttons on top right, results at the bottom */

        ta = new JTextField("https://en.wikipedia.org/wiki/URL"); /* create new text area */
        ta.setBounds(50, 5, WIDTH-100, HEIGHT-25);

        textPanel1.add(ta, BorderLayout.CENTER);

        tb = new JTextField("https://en.wikipedia.org/wiki/World_Wide_Web"); /* create new text area */
        tb.setBounds(50, 35, WIDTH-100, HEIGHT-25);
        textPanel2.add(tb, BorderLayout.CENTER);

        outputArea = new JTextArea(blankArea); /* create new text area */
        // outputArea.setBounds(50, 100, WIDTH-100, HEIGHT-25);
        //  outputArea.setBounds(0, 0, WIDTH, HEIGHT);
        // SouthPanel.setBounds(0,0,1000,1000);
        outputArea.setWrapStyleWord(true);
        outputArea.setLineWrap(true);
        scroll = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        outputArea.add(scroll, BorderLayout.EAST);
        SouthPanel.add(scroll);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("status", JLabel.CENTER);
        textLabel1 = new JLabel("Page 1");
        textLabel2 = new JLabel("Page 2");

        statusLabel.setSize(350, 100);
        textLabel1.setSize(350, 25);
        textLabel2.setSize(350, 25);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        statusControlPanel = new JPanel();
        statusControlPanel.add(controlPanel, BorderLayout.NORTH);
        statusControlPanel.add(statusLabel, BorderLayout.SOUTH);
        textPanel1.add(textLabel1, BorderLayout.NORTH);
        textPanel2.add(textLabel2, BorderLayout.NORTH);
        SuperNorthPanel.add(textPanel1);
        SuperNorthPanel.add(textPanel2);
        NorthPanel.add(SuperNorthPanel,BorderLayout.NORTH);
        NorthPanel.add(statusControlPanel,BorderLayout.CENTER);
        NorthPanel.add(SouthPanel,BorderLayout.SOUTH);
        mainFrame.add(NorthPanel,BorderLayout.NORTH);


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);
        //mainFrame.add(headerLabel);
        mainFrame.setVisible(true);
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked."); /* run code here */
                outputArea.setText(HtmlRead(ta.getText(), tb.getText()) + blankArea);
                outputArea.scrollRectToVisible(outputArea.getVisibleRect());

                ta.setText("https://www.milton.edu/");
                tb.setText("");
            } else {
                statusLabel.setText("Cancel Button clicked."); /* delete all other code */
                outputArea.setText(blankArea);
            }


        }
    }
}