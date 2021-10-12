/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *
 * @author User
 */
public class TombolExit extends JButton{
    
    
    private final ImageIcon icon = new ImageIcon(getClass().getResource("/angsuran/image/exit_50px.png"));

    private final Color col = new Color(0, 0, 51);

    @Override
    public Icon getIcon() {
        return icon;
    }


    @Override
    public Color getForeground() {
        return col;
    }
    
    @Override
    public boolean isOpaque() {
        return false;
    }
    
    @Override
    public Dimension getPreferredSize() {
       Dimension dim = new Dimension(153, icon.getIconHeight());
       return dim;
    }

    @Override
    public String getText() {
        return "KELUAR";
    }
    
   
    @Override
    public Dimension getSize(Dimension rv) {
       Dimension dim = new Dimension(153, icon.getIconHeight());
       return dim;
    }

    @Override
    public Border getBorder() {
        return BorderFactory.createEmptyBorder();
    }

    @Override
    public boolean isBorderPainted() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "tombol_exit"; //To change body of generated methods, choose Tools | Templates.
    }

  
    
    
    
   

    
    
    
    
}
