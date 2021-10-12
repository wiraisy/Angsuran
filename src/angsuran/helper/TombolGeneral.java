/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author RAKA
 */
public class TombolGeneral {
    

    public TombolGeneral(JButton but) {
        Dimension dim = new Dimension(223,43);
        but.setBackground(new Color(0,153,51));
        but.setForeground(new Color(255, 255, 255));
        but.setFont(new Font("Tahoma", 1, 12));
        but.setSize(dim);
        but.setPreferredSize(dim);
        but.setContentAreaFilled(false);
        but.setBorderPainted(false);
        but.setOpaque(true);
        but.setLayout(new AbsoluteLayout());
        but.setBorder(BorderFactory.createEmptyBorder());
        
        but.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                but.setBackground(new Color(14,14,67));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                 but.setBackground(new Color(14,14,67));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               but.setBackground(new Color(0,153,51));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                 but.setBackground(new Color(14,14,67));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                 but.setBackground(new Color(0,153,51));
            }
        });
    }

}