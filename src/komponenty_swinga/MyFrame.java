package komponenty_swinga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    private JPanel jPanel;
    private MyPanel panel1, panel2, panel3;
    private JLabel f1Label, f2Label, a1Label, a2Label, wyp1Label, wyp2Label, przebieg1, przebieg2, przebieg3;
    private JTextField f1, f2, a1, a2, wyp1, wyp2;
    private JButton rysuj1, rysuj2;
    private boolean wave1painted = false, wave2painted = false;
    public static final Font font = new Font("Arial", Font.BOLD, 16);


    public MyFrame() {
        initialize();
    }

    public void initialize() {

        // JFrame
        this.setTitle("Przebiegi prostokątne");
        this.setSize(1200, 1000);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setFocusable(false);
        jPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(jPanel);

        // panel1 - pierwszy przebieg
        panel1 = new MyPanel();
        panel1.setBounds(150, 200, panel1.getWidth(), panel1.getHeight());
        panel1.setFocusable(false);
        jPanel.add(panel1);

        // panel2 - drugi przebieg
        panel2 = new MyPanel();
        panel2.setBounds(650, 200, panel2.getWidth(), panel2.getHeight());
        panel2.setFocusable(false);
        jPanel.add(panel2);

        // panel3 - złożenie obu drgań
        panel3 = new MyPanel();
        panel3.setBounds(400, 600, panel3.getWidth(), panel3.getHeight());
        panel3.setFocusable(false);
        jPanel.add(panel3);


        // f1Label - częstotliwość 1 (JLabel)
        f1Label = new JLabel("Częstotliwość (10÷100):");
        f1Label.setBounds(200, 20, 150, 30);
        jPanel.add(f1Label);

        // a1Label - amplituda 1 (JLabel)
        a1Label = new JLabel("Amplituda (max. 140):");
        a1Label.setBounds(200, 60, 150, 30);
        jPanel.add(a1Label);

        // wyp1Label - wypełnienie 1 (JLabel)
        wyp1Label = new JLabel("Wypełnienie (0÷100):");
        wyp1Label.setBounds(200, 100, 150, 30);
        jPanel.add(wyp1Label);

        // f1 - częstotliwość 1 (JTextField)
        f1 = new JTextField();
        f1.setBounds(350, 20, 80, 30);
        jPanel.add(f1);

        // a1 - amplituda 1 (JTextField)
        a1 = new JTextField();
        a1.setBounds(350, 60, 80, 30);
        jPanel.add(a1);

        // wyp1 - wypełnienie 1 (JTextField)
        wyp1 = new JTextField();
        wyp1.setBounds(350, 100, 80, 30);
        jPanel.add(wyp1);

        // rysuj1 - JButton
        rysuj1 = new JButton("Rysuj");
        rysuj1.setBounds(450, 50, 80, 50);
        rysuj1.addActionListener(this);
        jPanel.add(rysuj1);

        // przebieg1 - JLabel
        przebieg1 = new JLabel("Pierwszy przebieg:");
        przebieg1.setFont(font);
        przebieg1.setBounds(280, 150, 150, 50);
        jPanel.add(przebieg1);


        // f2Label - częstotliwość 2 (JLabel)
        f2Label = new JLabel("Częstotliwość (10÷100):");
        f2Label.setBounds(700, 20, 150, 30);
        jPanel.add(f2Label);

        // a2Label - amplituda 2 (JLabel)
        a2Label = new JLabel("Amplituda (max. 140):");
        a2Label.setBounds(700, 60, 150, 30);
        jPanel.add(a2Label);

        // wyp2Label - wypełnienie 2 (JLabel)
        wyp2Label = new JLabel("Wypełnienie (0÷100):");
        wyp2Label.setBounds(700, 100, 150, 30);
        jPanel.add(wyp2Label);

        // f2 - częstotliwość 2 (JTextField)
        f2 = new JTextField();
        f2.setBounds(850, 20, 80, 30);
        jPanel.add(f2);

        // a2 - amplituda 2 (JTextField)
        a2 = new JTextField();
        a2.setBounds(850, 60, 80, 30);
        jPanel.add(a2);

        // wyp2 - wypełnienie 2 (JTextField)
        wyp2 = new JTextField();
        wyp2.setBounds(850, 100, 80, 30);
        jPanel.add(wyp2);

        // rysuj2 - JButton
        rysuj2 = new JButton("Rysuj");
        rysuj2.setBounds(950, 50, 80, 50);
        rysuj2.addActionListener(this);
        jPanel.add(rysuj2);

        // przebieg2 - JLabel
        przebieg2 = new JLabel("Drugi przebieg:");
        przebieg2.setFont(font);
        przebieg2.setBounds(780, 150, 150, 50);
        jPanel.add(przebieg2);


        // przebieg3 - JLabel
        przebieg3 = new JLabel("Złożenie dwóch przbiegów:");
        przebieg3.setFont(font);
        przebieg3.setBounds(500, 550, 250, 50);
        jPanel.add(przebieg3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == rysuj1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    panel1.setFreq(Integer.parseInt(f1.getText()));
                    panel1.setAmp(Integer.parseInt(a1.getText()));
                    panel1.setFill(Integer.parseInt(wyp1.getText()));

                    panel1.paintWave();
                }
            }).start();

            wave1painted = true;
            if(wave2painted) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        panel3.zlozenie(panel1, panel2);
                    }
                }).start();
            }

        } else if(e.getSource() == rysuj2) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    panel2.setFreq(Integer.parseInt(f2.getText()));
                    panel2.setAmp(Integer.parseInt(a2.getText()));
                    panel2.setFill(Integer.parseInt(wyp2.getText()));

                    panel2.paintWave();
                }
            }).start();

            wave2painted = true;
            if(wave1painted) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        panel3.zlozenie(panel1, panel2);
                    }
                }).start();
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame().setVisible(true);
            }
        });
    }

}