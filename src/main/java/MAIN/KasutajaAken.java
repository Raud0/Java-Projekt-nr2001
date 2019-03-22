package MAIN;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class KasutajaAken extends JFrame {

    //private String sisu;

    private Image aken_ikoon;

    private JPanel pildi_paneel;
    private JPanel suhtlus_paneel;

    private JLabel silt_vastus;
    private JTextArea vastus;
    private JScrollPane vastus_ala;
    private String vastus_tekst;
    public String getVastus_tekst() { return vastus_tekst; }
    public void setVastus_tekst(String vastus_tekst) { this.vastus_tekst = vastus_tekst; }

    private JLabel silt_sisend;
    private JTextArea sisend;
    private JScrollPane sisend_ala;
    private String sisend_tekst;
    public String getSisend_tekst() { return sisend_tekst; }

    public void setSisend_tekst(String sisend_tekst) { this.sisend_tekst = sisend_tekst; }

    private JButton suhtle;

    private JLabel pilt_ala;
    private ImageIcon pilt;
    //pildid peaksid olema laiusega 380
    private static final String[] pilt_failinimed = {"whale-fall.png","coolboi.jpg","iced.png","bedroom.gif","temple.gif"};

    /**public String getSisu() {
        return sisu;
    }*/

    public GridBagConstraints lahtripiirangud(int rida, int veerg,int mode, Component komponent){
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

    public KasutajaAken (String algtekst) {
        setVastus_tekst(algtekst);

        //paneelid init.
        pildi_paneel = new JPanel();
        pildi_paneel.setLayout(new GridBagLayout());
        suhtlus_paneel = new JPanel();
        suhtlus_paneel.setLayout(new GridBagLayout());
        suhtlus_paneel.setSize(410,220);

        //pilt init.
        pilt = new ImageIcon(pilt_failinimed[(int)(Math.random()*pilt_failinimed.length)]);
        pilt_ala = new JLabel(pilt);
        pildi_paneel.add(pilt_ala);

        //programmi kast init.
        silt_vastus = new JLabel(" Master: ");
        suhtlus_paneel.add(silt_vastus,lahtripiirangud(0,0,0,silt_vastus));
        vastus = new JTextArea(vastus_tekst,3,1);
        vastus.setLineWrap(true);
        vastus.setWrapStyleWord(true);
        vastus.setEditable(false);
        vastus.setBackground(new Color(191,255,244));
        vastus_ala = new JScrollPane(vastus);

        suhtlus_paneel.add(vastus_ala,lahtripiirangud(1,0,1,vastus));

        //kasutaja kast.
        silt_sisend = new JLabel(" Student: ");
        suhtlus_paneel.add(silt_sisend,lahtripiirangud(2,0,0,silt_sisend));
        sisend = new JTextArea(sisend_tekst,3,1);
        sisend.setLineWrap(true);
        sisend.setWrapStyleWord(true);
        sisend.setBackground(new Color(191,255,244));
        sisend_ala = new JScrollPane(sisend);

        suhtlus_paneel.add(sisend_ala,lahtripiirangud(3,0,1,sisend));

        //nupp init.
        suhtle = new JButton(" Speak ");
        suhtle.setBackground(new Color(208,239,160));
        suhtlus_paneel.add(suhtle,lahtripiirangud(4,0,0,suhtle));
        suhtle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(sisend.getText());
                //sisu = sisend.getText();
            }
        });

        //tervik init.
        pildi_paneel.setSize(pilt.getIconWidth(),pilt.getIconHeight());
        add(pildi_paneel,BorderLayout.BEFORE_FIRST_LINE);
        add(suhtlus_paneel, BorderLayout.AFTER_LAST_LINE);
        setSize(410,suhtlus_paneel.getHeight()+pildi_paneel.getHeight());
        setResizable(false);
        //lisa init.
        setTitle("Java Projekt 2001: Zen Master");
        setBackground(new Color(244,164,164));
        aken_ikoon = Toolkit.getDefaultToolkit().getImage("meistriikoon.png");
        setIconImage(aken_ikoon);

        setVisible(true);
    }

    /*public static void main(String[] args) {
        MAIN.KasutajaAken programm = new MAIN.KasutajaAken();
    }*/

}