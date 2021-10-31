/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;

import angsuran.model.Userku;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class UserTM extends AbstractTableModel{
    
    private static final String[] Header = {"No","Id","Username","Previllage","Management BA","Management Cicilan","Pembayaran","User","SMTP","Laporan"};
    
    private List<Userku> list = new ArrayList<>();

    public void setList(List<Userku> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return Header.length;
    }

    @Override
    public String getColumnName(int column) {
        return Header[column];
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex += 1;
            case 1:
                return list.get(rowIndex).getIduser();
            case 2:
                return list.get(rowIndex).getUsername();
            case 3:
                return list.get(rowIndex).getPrevillage();
            case 4:
                return list.get(rowIndex).isManagementba();
            case 5:
                return list.get(rowIndex).isManagementcicilan();
            case 6:
                return list.get(rowIndex).isOlahpembayaran();
            case 7:
                return list.get(rowIndex).isManagementuser();
            case 8:
                return list.get(rowIndex).isSmtp();
            case 9:
                return list.get(rowIndex).isLaporan();
            default:
                    return null;
                        
                        
            
        }
    }
    
}
