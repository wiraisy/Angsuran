/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author akbarwiraisy
 */
public class DeskPanelDasar extends JPanel{
    
    private static Image image;

    public DeskPanelDasar() {
        image = new ImageIcon(getClass().getResource("/angsuran/image/background3.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D gd = (Graphics2D) g.create();

        gd.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        gd.dispose();
    }
    
    
    
    
    
    
}
