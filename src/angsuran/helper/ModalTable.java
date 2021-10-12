/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author diazwiraisy
 */
public class ModalTable  extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
//To change body of generated methods, choose Tools | Templates.
        
        if(value instanceof Date){
            Date date = (Date) value;
            label.setText(new SimpleDateFormat("dd MMMM yyyy").format(date));
         }else if(value instanceof Double){
             Double longdata = (Double) value;
             label.setText(String.format("%,.0f", longdata.doubleValue()));
         }
        return label;
    }
  
}
