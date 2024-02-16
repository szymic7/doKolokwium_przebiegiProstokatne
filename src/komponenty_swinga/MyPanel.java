package komponenty_swinga;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel implements ActionListener {

    private BufferedImage wave;
    private double freq = 0, amp = 0, fill = 0;
    private int waveImageX;
    private static int negativeX = -400;
    private int previousSum;
    private Timer timer = null;

    public MyPanel() {
        initialize();
    }

    public void initialize() {
        this.setBackground(new Color(168, 168, 168));
        this.setBorder(new LineBorder(Color.BLACK, 2, false));
        this.setSize(new Dimension(400, 300));
        this.setLayout(null);
        this.setFocusable(false);
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setAmp(int amp) {
        this.amp = amp;
    }

    public void setFill(int fill) {
        this.fill = fill / 100.0;
    }

    public void paintWave() {

        wave = new BufferedImage(2 * this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = wave.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // wygładzanie krawędzi

        if(this.freq < 10 || this.freq > 100) this.freq = 20;
        double cycleWidth = 1000.0 / this.freq; // przeskalowany okres, np dla f = 20 => T = 50
        int numCycles = this.getWidth() / (int)cycleWidth; // Liczba cykli na ekranie; dla T = 50 => numCycles = 8

        if(this.amp <= 0 || this.amp > 140 ) this.amp = 100;
        int y = this.getHeight() / 2 - (int)this.amp;

        if(this.fill <= 0 || this.fill > 1.0) this.fill = 0.5;
        int upSignal = (int)(this.fill * (int)cycleWidth);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, this.getHeight() / 2, 2 * this.getWidth(), 1); // oś OX

        g2.setColor(Color.BLUE);

        if(this.fill == 1.0) g2.fillRect(0, y, 2 * this.getWidth(), 2); // przypadek specjalny - wypełnienie 100%
        else {
            for (int i = 0; i < 2 * numCycles; i++) {

                int x = (i * this.getWidth()) / (numCycles); // x zmienia sie o 40

                g2.fillRect(x, y, 2, 2 * (int) this.amp + 2); // linia pionowa
                g2.fillRect(x, y, upSignal + 2, 2); // górna linia pozioma
                x += upSignal;
                g2.fillRect(x, y, 2, 2 * (int) this.amp + 2); // linia pionowa
                g2.fillRect(x, y + 2 * (int) this.amp, (int) cycleWidth - upSignal + 2, 2); // dolna linia pozioma

            }
        }

        g2.dispose();
        this.waveImageX = 0;
        if(timer != null && timer.isRunning()) timer.stop();
        timer = new Timer(30, this);
        timer.start();

    }

    public void zlozenie(MyPanel panel1, MyPanel panel2) {

        if(panel1.freq == panel2.freq) {

            wave = new BufferedImage(2 * this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = wave.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // wygładzanie krawędzi

            this.freq = panel1.freq;
            double cycleWidth = 1000.0 / this.freq; // przeskalowany okres, np dla f = 20 => T = 50

            int signal1 = (int) (panel1.fill * (int)cycleWidth); // signal1 = 25, dla f=20, fill=0.5
            int signal2 = (int) (panel2.fill * (int)cycleWidth);

            int amp1 = (int) panel1.amp;
            int amp2 = (int) panel2.amp;


            g2.setColor(Color.BLACK);
            g2.fillRect(0, this.getHeight() / 2, 2 * this.getWidth(), 1); // oś OX

            g2.setColor(Color.BLUE);

            for (int i = 0; i < 2 * this.getWidth(); i += 2) {

                int x = i;

                int signalValue1 = 0;
                int signalValue2 = 0;

                // wartość y=f(x) dla sygnału 1
                if (x % (int)cycleWidth < signal1) {
                    signalValue1 = amp1;
                } else {
                    signalValue1 = -amp1;
                }

                // wartość y=f(x) dla sygnału 2
                if (x % (int)cycleWidth < signal2) {
                    signalValue2 = amp2;
                } else {
                    signalValue2 = -amp2;
                }

                int sum = signalValue1 + signalValue2;


                if(sum == previousSum) {
                    g2.fillRect(x, this.getHeight() / 2 - sum, 2, 2);
                } else if(sum > previousSum) {
                    g2.fillRect(x, this.getHeight() / 2 - sum, 2, Math.abs(sum - previousSum) + 2);
                } else {
                    g2.fillRect(x, this.getHeight() / 2 - previousSum, 2, Math.abs(sum - previousSum) + 2);
                }

                previousSum = sum;

            }

            g2.dispose();
            this.waveImageX = 0;
            if(timer != null && timer.isRunning()) timer.stop();
            timer = new Timer(30, this);
            timer.start();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (wave != null) {
            g.drawImage(wave, waveImageX, 0, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        waveImageX -= 2;
        if(waveImageX <= negativeX) {
            waveImageX = 0;
        }
        this.repaint();
    }

}