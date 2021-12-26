/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.JTableRender;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Notifikasi;
import angsuran.tablemodel.NotifikasiTM;
import angsuran.view.NotifikasiFrame;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class NotifikasiController {
    
    private AngsuranDao dao = new AngsuranDaoImplements();
    private NotifikasiTM model = new NotifikasiTM();
    private List<Notifikasi> list = new ArrayList<>();
    private final Map<Integer, String> mapval = new HashMap<>();
    private JTableRender jTableRender;
     //=========================== PAGINATION
    public static List<Notifikasi> listPagination = new ArrayList<>();

    private static int no;

    public static List<Notifikasi> getListPagination() {
        return listPagination;
    }

    public static void setListPagination(List<Notifikasi> listPagination) {
        no = 1;
        NotifikasiController.listPagination = listPagination;
    }

    public DefaultPaginationModel getDefPaginModel(int totalItem) {
        DefaultPaginationModel dpm = new DefaultPaginationModel();
        dpm.setTotalItem(totalItem);
        dpm.setPageSize(50);
        return dpm;
    }

    public List<Notifikasi> getListByPagination(List<Notifikasi> listP, int page) {
        int sizePerPage = 50;
        int from = Math.max(0, page * sizePerPage);
        int to = Math.min(listP.size(), (page + 1) * sizePerPage);
        return listP.subList(from, to);
    }

    public void actionPagination(NotifikasiFrame d, int page) {
        NotifikasiTM modelku = new NotifikasiTM();
        d.getTablenotifikasi().setModel(modelku);
        List<Notifikasi> listPerPage = getListByPagination(NotifikasiController.getListPagination(), page);
        modelku = new NotifikasiTM();
        modelku.setList(listPerPage);
        modelku.fireTableDataChanged();
        d.getTablenotifikasi().setModel(modelku);       
        //===================================================
        ModalTable tab = new ModalTable();
        mapval.put(6,"PENDING");
        tab.setMap(mapval);
        for(int a=0; a<=modelku.getColumnCount()-1; a++){
            d.getTablenotifikasi().getColumnModel().getColumn(a).setCellRenderer(tab);
        }
        jTableRender = new JTableRender(d.getTablenotifikasi());        
        d.getTablenotifikasi().revalidate();
        d.getTablenotifikasi().repaint();
    }
    //===================================================================
    
    public void LihatLaporan(NotifikasiFrame d, Confirm c){
        if(d.getNot() == null){
            c.Warning("Anda Belum Memilih Data");
        }else{
            File ff = new File(d.getNot().getPathpdf());
                if (ff.exists()) {
                    try {
                        Desktop dt = Desktop.getDesktop();
                        dt.open(ff);
                    } catch (IOException ex) {
                        Logger.getLogger(NotifikasiController.class.getName()).log(Level.SEVERE, null, ex);
                        c.Gagal(ex);
                    }
                } else {
                    c.Warning("File Kosong");
                }
        }
    }
    
     public void LoadKlik(NotifikasiFrame d, MouseEvent evt) {
        if (list != null) {
            int row = d.getTablenotifikasi().rowAtPoint(evt.getPoint());
            if (row != -1) {
                int indeks = d.getTablenotifikasi().getSelectedRow();
                Long id = (Long) d.getTablenotifikasi().getModel().getValueAt(indeks, 0);
                Notifikasi not = dao.getnotifikasibyid(id);
                d.setNot(not);                
            }
        }

    }
    
   
    
    public void LoadAllNotifikasi(NotifikasiFrame d, String nama_bu, Confirm con) {
        list = new ArrayList<>();
        list = dao.getnotifikasibynamabu(nama_bu, con);
        NotifikasiController.setListPagination(list);
        model.setList(getListByPagination(list, 0));
        model.fireTableDataChanged();
        d.getTablenotifikasi().setModel(model);
        d.getPagination().setModel(getDefPaginModel(list.size()));
        //===================================================
        ModalTable tab = new ModalTable();
        mapval.put(6,"PENDING");
        tab.setMap(mapval);
        for(int a=0; a<=model.getColumnCount()-1; a++){
            d.getTablenotifikasi().getColumnModel().getColumn(a).setCellRenderer(tab);
        }
        jTableRender = new JTableRender(d.getTablenotifikasi());
        d.getTablenotifikasi().revalidate();
        d.getTablenotifikasi().repaint();

    }
    
}
