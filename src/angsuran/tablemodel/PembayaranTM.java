/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;

import angsuran.model.Pembayaran;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class PembayaranTM extends AbstractTableModel{
    
    private final String[] HEADER = {"No","Id","Tanggal","Ciilan","Tunggakan Berjalan","Total"};
    
    private List<Pembayaran> list = new ArrayList<>();

    public void setList(List<Pembayaran> list) {
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
                return list.get(rowIndex).getId_pembayaran();
            case 2:
                return list.get(rowIndex).getTanggal_pembayaran();
            case 3:
                return list.get(rowIndex).getPembayaran_cicilan();
            case 4:
                return list.get(rowIndex).getPembayaran_tunggakan_berjalan();
            case 5:
                return list.get(rowIndex).getTotal_pembayaran();
            default:
                    return null;
                
        }
    
    }
    
    
}
