import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListJavaFonts extends JFrame
{

    public ListJavaFonts()
    {
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        ArrayList<JLabel> list = new ArrayList<JLabel>();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        int piir = 1;
        for ( int i = 0; i < fonts.length; i++ )
        {
            if (i > (int)(piir*fonts.length/6)) {
                piir++;
                c.gridx ++;
            }
            c.gridy = i - (int)((piir-1)*fonts.length/6);
            list.add(i,new JLabel("(*￣m￣) ヽ(￣ω￣(。。 )ゝ"+": "+fonts[i]));
            list.get(i).setFont(new Font(fonts[i],Font.PLAIN,11));
            add(list.get(i), c);
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        ListJavaFonts aken = new ListJavaFonts();
    }
}