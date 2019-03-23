package DISPLAY;

import javax.swing.*;
import java.awt.*;

public class PealKirje extends JLabel {
    public PealKirje(String tekst) {
        super(tekst);
        Font font = new Font("MS PGothic",Font.PLAIN,13);
        this.setFont(font);
    }
}
