/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author RAKA
 */
public class TombolDetail {

    private final ImageIcon icon = new ImageIcon(getClass().getResource("/Com/DeskPanel/Image/Iconku/icons8_Report_Card_50px.png"));

    public TombolDetail(JButton but) {

        but.setIcon(icon);
        but.setText("DETAIL");
        but.setToolTipText("Tombol Ini Digunakan Untuk Melihat Detail");
        //======================================================================
        but.setBackground(new Color(255,204,0));
        but.setForeground(new Color(0, 0, 0));
        but.setFont(new Font("Tahoma", 1, 11));
        but.setSize(130, 46);
        but.setContentAreaFilled(false);
        but.setBorderPainted(false);
        but.setOpaque(true);
        but.setLayout(new AbsoluteLayout());
        
        but.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                but.setBackground(new Color(255,102,0));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                but.setBackground(new Color(255,102,0));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                but.setBackground(new Color(255,204,0));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                but.setBackground(new Color(255,102,0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                but.setBackground(new Color(255,204,0));
            }
        });
    }

}
