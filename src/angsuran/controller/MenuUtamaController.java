/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.HelperUmum;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Cicilan;
import angsuran.tablemodel.FilterCicilanTM;
import angsuran.view.MenuUtama;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class MenuUtamaController {

    private AngsuranDao dao;
    private FilterCicilanTM model;
    private List<Cicilan> list = new ArrayList<>();
    private static SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

    public MenuUtamaController() {
        dao = new AngsuranDaoImplements();
        model = new FilterCicilanTM();
    }

    //=========================== PAGINATION
    public static List<Cicilan> listPagination = new ArrayList<>();

    private static int no;

    public static List<Cicilan> getListPagination() {
        return listPagination;
    }

    public static void setListPagination(List<Cicilan> listPagination) {
        no = 1;
        MenuUtamaController.listPagination = listPagination;
    }

    public DefaultPaginationModel getDefPaginModel(int totalItem) {
        DefaultPaginationModel dpm = new DefaultPaginationModel();
        dpm.setTotalItem(totalItem);
        dpm.setPageSize(50);
        return dpm;
    }

    public List<Cicilan> getListByPagination(List<Cicilan> listP, int page) {
        int sizePerPage = 50;
        int from = Math.max(0, page * sizePerPage);
        int to = Math.min(listP.size(), (page + 1) * sizePerPage);
        return listP.subList(from, to);
    }

    public void actionPagination(MenuUtama d, int page) {
        FilterCicilanTM modelku = new FilterCicilanTM();
        d.getTableFilter().setModel(modelku);
        List<Cicilan> listPerPage = getListByPagination(MenuUtamaController.getListPagination(), page);
        modelku = new FilterCicilanTM();
        modelku.setList(listPerPage);
        modelku.fireTableDataChanged();
        d.getTableFilter().setModel(modelku);
        d.getTableFilter().getColumnModel().getColumn(3).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(4).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(5).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(6).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(7).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(8).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(9).setCellRenderer(new ModalTable());
        d.getTableFilter().revalidate();
        d.getTableFilter().repaint();
    }
    //===================================================================

    public void LoadAllFilter(MenuUtama d) {
        list = new ArrayList<>();
        list = dao.getallCicilanbystatusandjatuhtempo("BELUM LUNAS");
        MenuUtamaController.setListPagination(list);
        model.setList(getListByPagination(list, 0));
        model.fireTableDataChanged();
        d.getTableFilter().setModel(model);
        d.getTableFilter().getColumnModel().getColumn(3).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(4).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(5).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(6).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(7).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(8).setCellRenderer(new ModalTable());
        d.getTableFilter().getColumnModel().getColumn(9).setCellRenderer(new ModalTable());
        d.getPagination().setModel(getDefPaginModel(list.size()));
        d.getTableFilter().revalidate();
        d.getTableFilter().repaint();

    }

    public void createreport(MenuUtama d, Confirm c) throws JRException {      
        if (list.isEmpty()) {
            c.Warning("Data Kosong...!!");
        } else {
               Double total_tunggakan = 0d;
               Double total_kekurangan = 0d;
            for (Cicilan cil : list) {
                total_tunggakan += cil.getTotal();
                total_kekurangan += cil.getTotal_kekurangan();
            }
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("image", this.getClass().getResourceAsStream("/angsuran/report/background_jasper.png"));
            parameter.put("total_tagihan", total_tunggakan);
            parameter.put("total_kekurangan", total_kekurangan);
            parameter.put("terbilang", HelperUmum.angkaToTerbilang(total_kekurangan.longValue()) + " Rupiah");
            JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/angsuran/report/Angsuranku.jasper"),
                    parameter, new JRBeanCollectionDataSource(list));
            JasperViewer viewer = new JasperViewer(print, false);
            JDialog dd = new JDialog();
            dd.setContentPane(viewer.getContentPane());
            dd.setSize(viewer.getSize());
            dd.setTitle("Print Tagihan Bulan Ini");
            dd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dd.setModal(true);
            dd.pack();
            HelperUmum.setlogodialog(dd);
            dd.setVisible(true);          
            HelperUmum.jdialogcenterOnScreen(dd, true);
        }

    }

    public void tampil(MenuUtama u, Confirm con) {
        u.getBa().setVisible(u.getModel().isManagementba());
        u.getCicilan().setVisible(u.getModel().isManagementcicilan());
        u.getPembayaran().setVisible(u.getModel().isOlahpembayaran());
        u.getUser().setVisible(u.getModel().isManagementuser());
    }

    public void panggil(JFrame j) {
        j.setVisible(true);
    }

}
