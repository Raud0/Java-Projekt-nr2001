package MAIN;

import DISPLAY.*;
import sun.tools.tree.ThisExpression;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KasutajaAken extends JFrame {

    private Image aken_ikoon;

    private JPanel pildi_paneel;
    private JPanel suhtlus_paneel;

    private JTextArea tuju;
    private JScrollPane tuju_ala;
    private String tujutekst = "";
    public void setTujutekst(String tujutekst) {
        tuju.insert(tujutekst,0);
        this.tujutekst = tujutekst;
    }

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
        suhtlus_paneel.setSize(410,200);

        //pilt init.
        pilt = new ImageIcon(pilt_failinimed[(int)(Math.random()*pilt_failinimed.length)]);
        pilt_ala = new JLabel(pilt);
        pildi_paneel.add(pilt_ala);

        //tuju kast init.
        tuju = new SonaVali(tujutekst,1,1);
        tuju.setEditable(false);
        tuju_ala = new JScrollPane(tuju);
        suhtlus_paneel.add(tuju_ala,lahtripiirangud(0,0,1,tuju));

        //programmi kast init.
        silt_vastus = new PealKirje(" master: ");
        suhtlus_paneel.add(silt_vastus,lahtripiirangud(1,0,0,silt_vastus));
        vastus = new SonaVali(vastus_tekst,3,1);
        vastus.setEditable(false);
        vastus_ala = new JScrollPane(vastus);
        suhtlus_paneel.add(vastus_ala,lahtripiirangud(2,0,1,vastus));

        //kasutaja kast.
        silt_sisend = new PealKirje(" student: ");
        suhtlus_paneel.add(silt_sisend,lahtripiirangud(3,0,0,silt_sisend));
        sisend = new SonaVali(sisend_tekst,3,1);
        sisend_ala = new JScrollPane(sisend);
        suhtlus_paneel.add(sisend_ala,lahtripiirangud(4,0,1,sisend));

        //nupp init.
        suhtle = new JButton(" speak ");
        suhtle.setFont(new Font("MS PGothic",Font.PLAIN,13));
        suhtle.setBackground(new Color(208,239,160));
        suhtlus_paneel.add(suhtle,lahtripiirangud(5,0,0,suhtle));

        //this does not work :):):):):):):):):):):):)
        /**suhtle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(sisend.getText());
                sisend.setText("");

                try(FileWriter fail = new FileWriter("slowandsteady.txt")) {
                    String faili_sisu = sisend.getText();
                    fail.write(faili_sisu);
                }catch (IOException f) {
                    new Exception("I dont fucking know", e).printStackTrace();
                }
            }

        }); */
        //this does work
        /**
        suhtle.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println(sisend.getText());
                try {
                    FileWriter fail = new FileWriter("slowandsteady.txt");
                    String faili_sisu = sisend.getText();
                    fail.write(faili_sisu);
                    fail.close();
                    System.out.println("done");
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
                sisend.setText("");
            }

        });*/



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