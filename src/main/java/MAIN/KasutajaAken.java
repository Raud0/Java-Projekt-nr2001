package MAIN;

import DISPLAY.*;

// crashed for me and wasn't used?
//import sun.tools.tree.ThisExpression;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import java.io.FileWriter;
import java.io.IOException;

public class KasutajaAken extends JFrame implements KeyListener, MouseListener, ActionListener {

    private Image akenIkoon;

    private JPanel pildiPaneel;
    private JPanel suhtlusPaneel;

    private JTextArea tuju;
    private JScrollPane tujuAla;
    private String tujutekst = "                                                                                                    "; //100 karakterit
    public void setTujutekst(String sisend) {
        tuju.removeAll();
        tuju.insert(sisend,0);
        this.tujutekst = sisend;
    }

    public String getTujutekst() {return tujutekst;}

    private String finalWord = "";
    public void setFinalWord(String sisend) {
        this.finalWord = sisend;
    }

    private JLabel siltVastus;
    private JTextArea vastus;
    private JScrollPane vastusAla;
    private String vastusTekst;
    public String getVastusTekst() { return vastusTekst; }
    public void setVastusTekst(String vastusTekst) { this.vastusTekst = vastusTekst; }

    private JLabel siltSisend;
    private JTextArea sisend;
    private JScrollPane sisendAla;
    private String sisendTekst;
    public String getSisendTekst() { return sisendTekst; }

    public void setSisendTekst(String sisendTekst) { this.sisendTekst = sisendTekst; }

    private JButton suhtle;

    private JLabel piltAla;
    private ImageIcon pilt;
    //pildid peaksid olema laiusega 380
    private static final String[] piltFailinimed = {"whale-fall.png","coolboi.jpg","iced.png","bedroom.gif","temple.gif"};

    public static String sisestatu = "";
    public static String getSisestatu(){return sisestatu;}
    public void kuvaVastus(){vastus.setText(finalWord);}

    public GridBagConstraints lahtriPiirangud(int rida, int veerg, int mode, Component komponent){
        GridBagConstraints piirangud = new GridBagConstraints();
        piirangud.gridx = veerg;
        piirangud.gridy = rida;
        piirangud.weightx = 1;
        piirangud.weighty = 1;
        piirangud.fill = GridBagConstraints.VERTICAL;
        piirangud.fill = GridBagConstraints.HORIZONTAL;
        switch (mode) {
            case 0: break;
            case 1: piirangud.weighty = 3;
                    break;
            default: break;
        }
        return piirangud;
    }

    public ImageIcon looPilt(boolean suvapilt, String tee) {
        if (suvapilt) {
            return new ImageIcon(piltFailinimed[(int)(Math.random()* piltFailinimed.length)]);
        } else {
            return new ImageIcon(tee);
        }
    }

    public void muudaPilt(ImageIcon uusPilt) {
        piltAla.setIcon(uusPilt);
        pilt = uusPilt;

        pildiPaneel.setSize(pilt.getIconWidth(),pilt.getIconHeight());
        setMinimumSize(new Dimension(410, suhtlusPaneel.getHeight()+ pildiPaneel.getHeight()));
        setSize(410, suhtlusPaneel.getHeight()+ pildiPaneel.getHeight());

    }

    public KasutajaAken (String algtekst) {
        //preset init.
        setVastusTekst(algtekst);
        Color c = new Color(244,164,164);

        //pilt init.
        pilt = looPilt(true,"");
        piltAla = new JLabel(pilt);

        //paneelid init.
        pildiPaneel = new JPanel();
        pildiPaneel.setLayout(new GridBagLayout());
        pildiPaneel.add(piltAla);
        pildiPaneel.setSize(pilt.getIconWidth(),pilt.getIconHeight());
        pildiPaneel.setBackground(c);
        suhtlusPaneel = new JPanel();
        suhtlusPaneel.setLayout(new GridBagLayout());
        suhtlusPaneel.setSize(410,200);
        suhtlusPaneel.setBackground(c);

        //tuju kast init.
        tuju = new SonaVali(tujutekst,1,1);
        tuju.setEditable(false);
        tujuAla = new JScrollPane(tuju);
        suhtlusPaneel.add(tujuAla, lahtriPiirangud(0,0,1,tuju));

        //programmi kast init.
        siltVastus = new PealKirje(" master: ");
        suhtlusPaneel.add(siltVastus, lahtriPiirangud(1,0,0, siltVastus));
        vastus = new SonaVali(vastusTekst,3,1);
        vastus.setEditable(false);
        vastusAla = new JScrollPane(vastus);
        suhtlusPaneel.add(vastusAla, lahtriPiirangud(2,0,1,vastus));

        //kasutaja kast.
        siltSisend = new PealKirje(" student: ");
        suhtlusPaneel.add(siltSisend, lahtriPiirangud(3,0,0, siltSisend));
        sisend = new SonaVali(sisendTekst,3,1);
        sisendAla = new JScrollPane(sisend);
        suhtlusPaneel.add(sisendAla, lahtriPiirangud(4,0,1,sisend));

        //nupp init.
        suhtle = new JButton(" speak ");
        suhtle.setFont(new Font("MS PGothic",Font.PLAIN,13));
        suhtle.setBackground(new Color(208,239,160));
        suhtlusPaneel.add(suhtle, lahtriPiirangud(5,0,0,suhtle));
        suhtle.addActionListener(this);

        //tervik init.
        add(pildiPaneel,BorderLayout.CENTER);
        add(suhtlusPaneel,BorderLayout.SOUTH);
        setSize(410, suhtlusPaneel.getHeight()+ pildiPaneel.getHeight());
        setResizable(true);
        setMinimumSize(new Dimension(410, suhtlusPaneel.getHeight()+ pildiPaneel.getHeight()));
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);

        //lisa init.
        setTitle("Java Projekt 2001: Zen Master");
        akenIkoon = Toolkit.getDefaultToolkit().getImage("meistriikoon.png");
        setIconImage(akenIkoon);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    //Key Listener
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
        pildiPaneel.setBackground(c);
        suhtlusPaneel.setBackground(c);
    }

    //Mouse Listener
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

    //Action Listener
    public void actionPerformed(ActionEvent e) {
        //System.out.println(sisend.getText());

        //Võta vastu
        try {
            FileWriter fail = new FileWriter("slowandsteady.txt", true);

            String failiSisu = sisend.getText();
            fail.write(failiSisu);

            fail.close();
            System.out.println("done");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        sisestatu = sisend.getText();

        //Taasta algolek
        sisend.setText("");
        requestFocusInWindow();
    }
}