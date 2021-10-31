/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.HelperUmum;
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Pembayaran;
import angsuran.view.LaporanFrame;
import angsuran.view.MenuUtama;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class LaporanController {

    private SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
    private AngsuranDao dao = new AngsuranDaoImplements();
    private List<Ba> listba = new ArrayList<>();

    public void LoadCB(LaporanFrame d) {
        d.getStatus().removeAllItems();
        d.getStatus().addItem("");
        d.getStatus().addItem("LUNAS");
        d.getStatus().addItem("BELUM LUNAS");
        //=================================
        d.getJenislaporan().removeAllItems();
        d.getJenislaporan().addItem("");
        d.getJenislaporan().addItem("LAPORAN STATUS BA");
        d.getJenislaporan().addItem("LAPORAN PEMBAYARAN");
    }

    private String awal;
    private String akhir;
    private String status;
    private Double totaltagihan;
    private Double totalpembayaran;
    private Double totalkekurangan;

    public void Laporanstatusba(LaporanFrame d, Confirm c) {
         totaltagihan = 0d;
         totalpembayaran = 0d;
         totalkekurangan = 0d;
         listba = dao.getallbabyawalakhirdanstatus(d.getAwal(), d.getAkhir(), d.getStatusvalue());
        if (listba.isEmpty()) {
            c.Warning("Data Ba Kosong");
        } else {
             try {
                 if (d.getAwal() == null && d.getAkhir() == null) {
                     awal = "All";
                     akhir = "All";
                 }
                 if (d.getAwal() != null && d.getAkhir() != null) {
                     awal = f.format(d.getAwal());
                     akhir = f.format(d.getAkhir());
                 }
                 if (d.getStatusvalue() == null) {
                     status = "All";
                 }
                 if (d.getStatusvalue() != null) {
                     status = d.getStatusvalue();
                 }
                 for(Ba baa: listba){
                     totaltagihan += baa.getTotal_tunggakan();
                     totalpembayaran += baa.getTotal_pembayaran();
                     totalkekurangan += baa.getTotal_kekurangan();
                 }  
                 Map<String, Object> parameter = new HashMap<>();
                 parameter.put("image", this.getClass().getResourceAsStream("/angsuran/report/background_jasper.png"));
                 parameter.put("awal", awal);
                 parameter.put("akhir", akhir);
                 parameter.put("status", status);
                 parameter.put("terbilangtagihan", HelperUmum.angkaToTerbilang(totaltagihan.longValue()) + " Rupiah");
                 parameter.put("terbilangpembayaran", HelperUmum.angkaToTerbilang(totalpembayaran.longValue()) + " Rupiah");
                 parameter.put("terbilangkekurangan", HelperUmum.angkaToTerbilang(totalkekurangan.longValue()) + " Rupiah");
                 parameter.put("jumlahba", listba.size());
                 JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/angsuran/report/StatusBA.jasper"),
                         parameter, new JRBeanCollectionDataSource(listba));
                 JasperViewer viewer = new JasperViewer(print, false);
                 JDialog dd = new JDialog();
                 dd.setContentPane(viewer.getContentPane());
                 dd.setSize(viewer.getSize());
                 dd.setTitle("Print Status BA");
                 dd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                 dd.setModal(true);
                 dd.pack();
                 dd.setVisible(true);
                 HelperUmum.jdialogcenterOnScreen(dd, true);
             } catch (JRException ex) {
                 Logger.getLogger(LaporanController.class.getName()).log(Level.SEVERE, null, ex);
             }

        }
    }
    
    private List<Cicilan> listcicilan = new ArrayList<>();
    private List<Pembayaran> listbayar = new ArrayList<>();
    
    public void LaporanPembayaran(LaporanFrame d, Confirm c){
        Ba ba = dao.getBabynoentitas(d.getNoentitas().getText());
        if(ba == null){
            c.Warning("Ba tidak ditemukan");
        }else{
            listcicilan = dao.getallcicilanbyba(ba);
            if(listcicilan.isEmpty()){
                c.Warning("Cicilan Kosong");
            }else{
                for(Cicilan cil:listcicilan){
                    List<Pembayaran> yarlist = dao.getallpembayaranbykodecicilan(cil.getKode_cicilan());
                    for(Pembayaran bay: yarlist){
                        listbayar.add(bay);
                    }
                    
                }
                if(listbayar.isEmpty()){
                    c.Warning("Belum Ada Aktifitas Pembayaran Cicilan");
                }else{
                    try {
                        Map<String, Object> parameter = new HashMap<>();
                        parameter.put("image", this.getClass().getResourceAsStream("/angsuran/report/background_jasper.png"));
                        parameter.put("listcicilan", listcicilan);
                        parameter.put("listpembayaran", listbayar);
                        parameter.put("tanggalba", f.format(ba.getTanggal_ba()));
                        parameter.put("namabu", ba.getNama_bu());
                        parameter.put("noentitas", ba.getNo_entitas());
                        parameter.put("noba", ba.getNo_ba());
                        parameter.put("total_tunggakan", ba.getTotal_tunggakan());                        
                        parameter.put("total_pembayaran", ba.getTotal_pembayaran());                        
                        parameter.put("total_kekurangan", ba.getTotal_kekurangan());                        
                        parameter.put("tahap_cicilan", ba.getTahap_cicilan());
                        parameter.put("status_tunggakan", ba.getStatus_tunggakan());
                        JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/angsuran/report/PembayaranReport.jasper"),
                                parameter, new JREmptyDataSource());
                        JasperViewer viewer = new JasperViewer(print, false);
                        JDialog dd = new JDialog();
                        dd.setContentPane(viewer.getContentPane());
                        dd.setSize(viewer.getSize());
                        dd.setTitle("Laporan Pembayaran");
                        dd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        dd.setModal(true);
                        dd.pack();
                        dd.setVisible(true);
                        HelperUmum.jdialogcenterOnScreen(dd, true);
                    } catch (JRException ex) {
                        Logger.getLogger(LaporanController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public void generatelaporan(LaporanFrame d, Confirm c){
        switch(d.getJenislaporan().getSelectedIndex()){
            case 0:
                c.Warning("Anda Belum Memilih Jenis Laporan");
                break;
            case 1:
                Laporanstatusba(d, c);
                break;
            case 2:
                if(d.getNoentitas().getText().isEmpty()){
                    c.Warning("Silahkan isi no entitas");
                    d.getNoentitas().requestFocus();
                }else{
                    LaporanPembayaran(d, c);
                }
                break;
        }
    }

}
