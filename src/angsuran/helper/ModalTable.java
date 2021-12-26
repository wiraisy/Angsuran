/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author diazwiraisy
 */
public class ModalTable  extends DefaultTableCellRenderer
{
    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
   //===================
    private int special = 0;
    private Map<Integer, String> map = new HashMap<>();
    

    public void setSpecial(int special) {
        this.special = special;
    }

    public int getSpecial() {
        return special;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }
    
    
    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        label.setHorizontalAlignment(JLabel.CENTER);
        if(value instanceof Date){
            Date date = (Date) value;
            label.setText(new SimpleDateFormat("dd MMMM yyyy").format(date));
         }else if(value instanceof Double){
             Double longdata = (Double) value;
             label.setText(String.format("%,.0f", longdata));
         }
        if(getSpecial() != 0){
              Double kekurangan = (Double) table.getModel().getValueAt(row, getSpecial());
              if (kekurangan > 0d) {
                    setBackground(new Color(255, 255, 102));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
         }
        if(!getMap().isEmpty()){
            map.forEach((keys,values)-> {
                setKey(keys);
                setValue(values);
            });
            String status = (String) table.getModel().getValueAt(row, getKey());
              if (status.equals(getValue())) {
                    setBackground(new Color(255, 255, 102));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
        }
        
        return label;
    }
  
}
