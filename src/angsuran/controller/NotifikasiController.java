/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Notifikasi;
import angsuran.tablemodel.NotifikasiTM;
import angsuran.view.NotifikasiFrame;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class NotifikasiController {
    
    private AngsuranDao dao = new AngsuranDaoImplements();
    private NotifikasiTM model = new NotifikasiTM();
    private List<Notifikasi> list = new ArrayList<>();
    
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
        d.getTablenotifikasi().getColumnModel().getColumn(3).setCellRenderer(new ModalTable());
        d.getTablenotifikasi().revalidate();
        d.getTablenotifikasi().repaint();
    }
    //===================================================================
    
    public void LoadAllNotifikasi(NotifikasiFrame d, String nama_bu, Confirm con) {
        list = new ArrayList<>();
        list = dao.getnotifikasibynamabu(nama_bu, con);
        NotifikasiController.setListPagination(list);
        model.setList(getListByPagination(list, 0));
        model.fireTableDataChanged();
        d.getTablenotifikasi().setModel(model);
        d.getTablenotifikasi().getColumnModel().getColumn(3).setCellRenderer(new ModalTable());
        d.getPagination().setModel(getDefPaginModel(list.size()));
        d.getTablenotifikasi().revalidate();
        d.getTablenotifikasi().repaint();

    }
    
}
