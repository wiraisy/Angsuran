/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.CompareBaModel;
import angsuran.helper.Generatekode;
import angsuran.helper.HelperUmum;
import angsuran.helper.JTableRender;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.BaModel;
import angsuran.model.Cicilan;
import angsuran.model.Notifikasi;
import angsuran.model.SmtpModel;
import angsuran.tablemodel.FilterCicilanTM;
import angsuran.view.MenuUtama;
import angsuran.whatsapp.SendMailWithAttachment;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JTableRender jTableRender;

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
        for(int a=0; a<=modelku.getColumnCount()-1; a++){
            d.getTableFilter().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTableFilter());
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
        for(int a=0; a<=model.getColumnCount()-1; a++){
            d.getTableFilter().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTableFilter());
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
            dd.setVisible(true);          
            HelperUmum.jdialogcenterOnScreen(dd, true);
        }

    }
    
    

    private Boolean result;
    
    public Boolean CreatePDF(MenuUtama d, Confirm c) throws FileNotFoundException {
        Set<BaModel> setba = new TreeSet(new CompareBaModel());
        List<Cicilan> listcicilanpdf = dao.getallcicilanjatuhtempo();
        if (!listcicilanpdf.isEmpty()) {
            for (Cicilan cil : listcicilanpdf) {
                BaModel mod = new BaModel();
                mod.setId_ba(cil.getBa().getId_ba());
                mod.setNama_bu(cil.getBa().getNama_bu());
                mod.setNo_entitas(cil.getBa().getNo_entitas());
                mod.setAlamat_email(cil.getBa().getAlamat_email());
                mod.setNo_ba(cil.getBa().getNo_ba());
                mod.setTanggal_ba(cil.getBa().getTanggal_ba());
                mod.setTotal_tunggakan(cil.getBa().getTotal_tunggakan());
                mod.setTahap_cicilan(cil.getBa().getTahap_cicilan());
                mod.setStatus_tunggakan(cil.getBa().getStatus_tunggakan());
                mod.setH_notifikasi(cil.getBa().getH_notifikasi());
                setba.add(mod);
            }

            for (BaModel ba : setba) {
                double tunggakan = 0d;
                double kekurangan = 0d;
                for (Cicilan cil : listcicilanpdf) {
                    if (cil.getBa().getId_ba().equals(ba.getId_ba())) {
                        tunggakan += cil.getTotal();
                        kekurangan += cil.getTotal_kekurangan();
                        ba.getListcicilan().add(cil);        
                        //======================================================
                        Cicilan co = dao.getcicilanbyid(cil.getId_cicilan());
                        co.setTanggal_notifikasi_terakhir(HelperUmum.getnextdatenotifikasi(cil.getTanggal_notifikasi_terakhir()));
                        co.setSentnotification(Boolean.FALSE);
                        dao.Update(co);
                        //======================================================
                    }
                    ba.setTunggakan_total(tunggakan);
                    ba.setKekurangan_total(kekurangan);
                    ba.setKodesurat(new Generatekode().Generatekodesurat());
                    //==========================================================
                }                
            try {
                SendMailWithAttachment ss = new SendMailWithAttachment();
                String filename = ss.createpdf(ba);
                //==================================================================
                Notifikasi not = new Notifikasi();
                not.setBa(dao.getbabyid(ba.getId_ba()));
                not.setKode_surat(ba.getKodesurat());
                //not.setTanggal_kirim(new Date());              
                not.setPathpdf(filename);
                not.setStatus("PENDING");
                dao.Simpan(not);
                //==================================================================
                result = Boolean.TRUE;
                File ff = new File(filename);
                if (ff.exists()) {
                    try {
                        Desktop dt = Desktop.getDesktop();
                        dt.open(ff);
                    } catch (IOException ex) {
                        Logger.getLogger(SendMailWithAttachment.class.getName()).log(Level.SEVERE, null, ex);
                        c.Gagal(ex);
                    }
                } else {
                    c.Warning("Gagal");
                }

            } catch (JRException ex) {
                c.Gagal(ex);
            }

            }

        }else{
            
            result = Boolean.FALSE;
        }
        return result;
    }
    
    private List<Notifikasi> listnotifikasi = new ArrayList<>();
    private SendMailWithAttachment mail = new SendMailWithAttachment();
    
    public void LoadReport(MenuUtama d, Confirm c){
        try {
            if(CreatePDF(d, c)){
                listnotifikasi = dao.getnotifikasibystatus("PENDING");
                if(!listnotifikasi.isEmpty()){
                    for(Notifikasi not: listnotifikasi){
                        SmtpModel smtp = dao.getactivesmtp();
                        try {
                            mail.sendemail(smtp, not, c);
                        } catch (Exception ex) {
                            Logger.getLogger(MenuUtamaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }                       
                }else{
                    c.Warning("Tidak ada notifikasi pending");
                }
                
            }else{
                 listnotifikasi = dao.getnotifikasibystatus("PENDING");
                if(!listnotifikasi.isEmpty()){
                    for(Notifikasi not: listnotifikasi){
                        SmtpModel smtp = dao.getactivesmtp();
                        try {
                            mail.sendemail(smtp, not, c);
                        } catch (Exception ex) {
                            Logger.getLogger(MenuUtamaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }                       
                }else{
                    c.Warning("Tidak ada notifikasi pending");
                }
                c.Warning("Tidak ada Angsuran jatuh tempo hari ini...!!!");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuUtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tampil(MenuUtama u, Confirm con) {
        u.getBa().setVisible(u.getModel().isManagementba());
        u.getCicilan().setVisible(u.getModel().isManagementcicilan());
        u.getPembayaran().setVisible(u.getModel().isOlahpembayaran());
        u.getUser().setVisible(u.getModel().isManagementuser());
        u.getManagementsmtp().setVisible(u.getModel().isSmtp());
    }

    public void panggil(JFrame j) {
        j.setVisible(true);
    }

}
