/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.tablemodel;


import angsuran.model.Notifikasi;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class NotifikasiTM extends AbstractTableModel{

    private final String[] HEADER = {"No","Nama BU","No Entitas","Tanggal Kirim","Kode Surat","Email"};
    
    private List<Notifikasi> list = new ArrayList<>();

    public void setList(List<Notifikasi> list) {
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
                return list.get(rowIndex).getBa().getNo_entitas();
            case 3:
                return list.get(rowIndex).getTanggal_kirim();
            case 4:
                return list.get(rowIndex).getKode_surat();
            case 5:
                return list.get(rowIndex).getBa().getAlamat_email();
            default:
                    return null;
                
        }
    }
    
}
