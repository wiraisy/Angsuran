/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;

import angsuran.model.Ba;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class CariBUTM extends AbstractTableModel{
    
    private final String[] HEADER = {"Id","Nama BU","No Entitas","Tanggal BA", "Total Tunggakan", "Status"};
    
    private List<Ba> list = new ArrayList<>();

    public void setList(List<Ba> list) {
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
                return list.get(rowIndex).getId_ba();
            case 1:
                return list.get(rowIndex).getNama_bu();
            case 2:
                return list.get(rowIndex).getNo_entitas();
            case 3:
                return list.get(rowIndex).getTanggal_ba();
            case 4:
                return list.get(rowIndex).getTotal_tunggakan();
            case 5:
                return list.get(rowIndex).getStatus_tunggakan();
            default:
                    return null;
                
        }
    }
    
}

