/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.JTable;
/**
 *
 * @author User
 */
public class JTableRender extends JTable implements Serializable{
    
    
    public JTableRender(final JTable table) {
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(214, 138, 19));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(25);
        table.setSelectionMode(1);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(255,204,0));
        table.setSelectionForeground(new Color(14, 14, 67));
        table.setBackground(new Color(255,255,255));
        table.setShowVerticalLines(false);
        table.getTableHeader().setReorderingAllowed(false); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setGridColor(new Color(3, 147, 65));
        table.revalidate();
        table.repaint();
                
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g.create();
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
    }

  
       
}
