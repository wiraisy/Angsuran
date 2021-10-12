/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;

import angsuran.model.Cicilan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class FilterCicilanTM extends AbstractTableModel{

    private final String[] HEADER = {"No", "Nama BU", "No BA","Total Tagihan","Tanggal","Nominal","Tagihan Berjalan","Total","kekurangan","Notifikasi"};
    
    private List<Cicilan> list = new ArrayList<>();

    public void setList(List<Cicilan> list) {
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
                return list.get(rowIndex).getBa().getNama_bu();
            case 2:
                return list.get(rowIndex).getBa().getNo_ba();
            case 3:
                return list.get(rowIndex).getBa().getTotal_tunggakan();
            case 4:
                return list.get(rowIndex).getTanggal_cicilan();
            case 5:
                return list.get(rowIndex).getNominal_cicilan();
            case 6:
                return list.get(rowIndex).getTagihan_berjalan();
            case 7:
                return list.get(rowIndex).getTotal();
            case 8:
                return list.get(rowIndex).getTotal_kekurangan();
            case 9:
                return list.get(rowIndex).getTanggal_notifikasi_terakhir();
            default:
                    return null;
                
        }
    
    }
    
    
}
