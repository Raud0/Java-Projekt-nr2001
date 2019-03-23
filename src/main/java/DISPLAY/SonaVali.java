package DISPLAY;

import javax.swing.*;
import java.awt.*;

public class SonaVali extends JTextArea {
    public SonaVali(String tekst, int rows, int columns) {
        super(tekst,rows,columns);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setBackground(new Color(191,255,244));
        this.setFont(new Font("MS PGothic",Font.PLAIN,13));
    }
}
