/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;


import angsuran.model.SmtpModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class SmtpTM extends AbstractTableModel{

    private final String[] HEADER = {"Id","Hostname","Username","Port","Active"};
    
    private List<SmtpModel> list = new ArrayList<>();

    public void setList(List<SmtpModel> list) {
        this.list = list;
    }
            
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return HEADER.length;
    }

    @Override
    public String getColumnName(int column) {
        return HEADER[column];
    }

   @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return list.get(rowIndex).getIdsmtp();
            case 1:
                return list.get(rowIndex).getHostname();
            case 2:
                return list.get(rowIndex).getUsername();
            case 3:
                return list.get(rowIndex).getPort();
            case 4:
                return list.get(rowIndex).getActiverecord();
            default:
                    return null;
                
        }
    
    }
}
