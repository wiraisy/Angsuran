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
public class BaTableModel extends AbstractTableModel{
    
    private final String[] HEADER = {"No","Id","Nama BU","No Entitas","No BA","Tanggal BA", "Total Tunggakan","Tahap Cicilan","email","Pembayaran"};
    
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
                return rowIndex += 1;
            case 1:
                return list.get(rowIndex).getId_ba();
            case 2:
                return list.get(rowIndex).getNama_bu();
            case 3:
                return list.get(rowIndex).getNo_entitas();
            case 4:
                return list.get(rowIndex).getNo_ba();
            case 5:
                return list.get(rowIndex).getTanggal_ba();
            case 6:
                return list.get(rowIndex).getTotal_tunggakan();
            case 7:
                return list.get(rowIndex).getTahap_cicilan();
            case 8:
                return list.get(rowIndex).getAlamat_email();
            case 9:
                return list.get(rowIndex).getTotal_pembayaran();
            default:
                    return null;
                
        }
    }
    
}
