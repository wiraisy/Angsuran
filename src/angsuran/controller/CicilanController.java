/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.Generatekode;
import angsuran.helper.HelperUmum;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Pembayaran;
import angsuran.tablemodel.CicilanTM;
import angsuran.view.CicilanFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class CicilanController {

    private AngsuranDao dao;
    private CicilanTM model = new CicilanTM();
    private List<Cicilan> list = new ArrayList<>();
    private final Generatekode kode = new Generatekode();

    public CicilanController() {
        dao = new AngsuranDaoImplements();
    }

    public void EnableCicilan(CicilanFrame d, Boolean yes) {
        d.getTanggalcicilan().setEnabled(yes);
        d.getNominalcicilan().setEnabled(yes);
        d.getTagihanberjalan().setEnabled(yes);
        d.getTahapancicilan().setEnabled(yes);
        d.getTanggalnotifikasi().setEnabled(yes);
    }

    public void ResetBa(CicilanFrame d) {
        d.getNoba().setText(null);
        d.getNamaba().setText(null);
        d.getTotaltunggakan().setText(null);
        d.getKodecicilan().setText(null);
        //===========================================
        d.getTablecicilan().setModel(new CicilanTM());
        d.getTablecicilan().revalidate();
        d.getTablecicilan().repaint();
       
    }

    public void ResetCicilan(CicilanFrame d) {
        d.getTanggalcicilan().setDate(new Date());
        d.getNominalcicilan().setText(null);
        d.getTagihanberjalan().setText(null);
        d.getTotalcicilan().setText(null);
        d.getKekurangancicilan().setText(null);
        d.getStatuscicilan().setText(null);
        d.getTahapancicilan().setText(null);
        d.getKodecicilan().setText(kode.Generatekodecicilan(d.getBa(), d));
        d.getTanggalnotifikasi().setDate(new Date());
    }

    public void loadcicilan(CicilanFrame d, Ba ba) {
        list = dao.getallcicilanbyba(ba);
        model.setList(list);
        d.getTablecicilan().setModel(model);
        d.getTablecicilan().getColumnModel().getColumn(4).setCellRenderer(new ModalTable());
        d.getTablecicilan().getColumnModel().getColumn(5).setCellRenderer(new ModalTable());
        d.getTablecicilan().getColumnModel().getColumn(6).setCellRenderer(new ModalTable());
        d.getTablecicilan().getColumnModel().getColumn(7).setCellRenderer(new ModalTable());
        d.getTablecicilan().getColumnModel().getColumn(8).setCellRenderer(new ModalTable());
        d.getTablecicilan().getColumnModel().getColumn(9).setCellRenderer(new ModalTable());
        d.getTablecicilan().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                Double kekurangan = (Double) table.getModel().getValueAt(row, 8);
                if (kekurangan > 0d) {
                    setBackground(new Color(255, 255, 102));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }

                return this;
            }
        });

        d.getTablecicilan().revalidate();
        d.getTablecicilan().repaint();
    }

    public void LoadKlik(CicilanFrame d, MouseEvent evt) {
        if (list != null) {
            int row = d.getTablecicilan().rowAtPoint(evt.getPoint());
            if (row != -1) {
                int indeks = d.getTablecicilan().getSelectedRow();
                Long id = (Long) d.getTablecicilan().getModel().getValueAt(indeks, 1);
                Cicilan cicilan = dao.getcicilanbyid(id);
                d.setCicilan(cicilan);
                d.getTanggalcicilan().setDate(cicilan.getTanggal_cicilan());
                d.getKodecicilan().setText(cicilan.getKode_cicilan());
                d.getNominalcicilan().setText(HelperUmum.setDoubletoIDR(cicilan.getNominal_cicilan()));
                d.getTagihanberjalan().setText(HelperUmum.setDoubletoIDR(cicilan.getTagihan_berjalan()));
                d.getTotalcicilan().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal()));
                d.getStatuscicilan().setText(cicilan.getStatus());
                d.getTahapancicilan().setText(cicilan.getTahap_cicilan());
                d.getKekurangancicilan().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal_kekurangan()));
                d.getTanggalnotifikasi().setDate(cicilan.getTanggal_notifikasi_terakhir());
                
            }
        }

    }

   
    
    public void findba(CicilanFrame d, Confirm confirm, String noentitas) {
        Ba ba = dao.getBabynoentitas(noentitas);
        if (ba == null) {
            d.getNoba().requestFocusInWindow();
            d.getNoba().selectAll();
            confirm.Warning("data ba dengan no entitas: " + noentitas + " tidak ditemukan");        
        } else {
                d.setBa(ba);
                d.getNamaba().setText(ba.getNama_bu());
                d.getTotaltunggakan().setText(HelperUmum.setDoubletoIDR(ba.getTotal_tunggakan()));
                d.getKodecicilan().setText(kode.Generatekodecicilan(ba, confirm));
                loadcicilan(d, ba);
        }
    }
    
    public void jumlahnominaltagihan(CicilanFrame d, Confirm confirm) {
        if(d.getNominalcicilan().getText().isEmpty()){
            d.getNominalcicilan().requestFocus();
        }else{
            Double nominalcicilan = HelperUmum.ubahFormatRupiahkeAngka(d.getNominalcicilan().getText());
            Double tagihanberjalan = HelperUmum.ubahFormatRupiahkeAngka(d.getTagihanberjalan().getText());
            Double total = nominalcicilan + tagihanberjalan;
            d.getTotalcicilan().setText(HelperUmum.setDoubletoIDR(total));
            d.getKekurangancicilan().setText(HelperUmum.setDoubletoIDR(total));
            d.getStatuscicilan().setText("BELUM LUNAS");
        }
    }

    

    public void simpan(CicilanFrame d, Confirm confirm) {
        if (d.getBa().getNama_bu().isEmpty()) {
            confirm.Warning("anda belum memilih ba");
        } else {
            Cicilan cl = new Cicilan();
            cl.setBa(d.getBa());
            cl.setKode_cicilan(d.getKodecicilan().getText());
            cl.setNominal_cicilan(HelperUmum.ubahFormatRupiahkeAngka(d.getNominalcicilan().getText()));
            cl.setTanggal_cicilan(d.getTanggalcicilan().getDate());
            if (cl.getNominal_cicilan() == null) {
                d.getNominalcicilan().requestFocus();
                confirm.Warning("nominal cicilan tidak boleh kosong");
            }
            cl.setTagihan_berjalan(HelperUmum.ubahFormatRupiahkeAngka(d.getTagihanberjalan().getText()));
            if (cl.getTagihan_berjalan() == null) {
                cl.setTagihan_berjalan(0d);
            }
            cl.setTotal(cl.getNominal_cicilan() + cl.getTagihan_berjalan());
            cl.setTahap_cicilan(d.getTahapancicilan().getText());
            cl.setTotal_kekurangan(cl.getTotal());
            cl.setStatus("BELUM LUNAS");
            cl.setTanggal_notifikasi_terakhir(HelperUmum.getdatenotifikasi(cl.getTanggal_cicilan(), d.getBa().getH_notifikasi()));
            cl.setSentnotification(Boolean.FALSE);
            try {
                Ba ba = dao.getbabyid(d.getBa().getId_ba());
                ba.setTotal_tunggakan(ba.getTotal_tunggakan() + cl.getTagihan_berjalan());
                //==============================================================
                List<Cicilan> listcil = dao.getallcicilanbybaandstatus(ba, "BELUM LUNAS", cl.getTanggal_notifikasi_terakhir());
                if(!listcil.isEmpty()){
                    for(Cicilan cilo: listcil){
                        Cicilan cila = dao.getcicilanbyid(cilo.getId_cicilan());
                        cila.setTanggal_notifikasi_terakhir(cl.getTanggal_notifikasi_terakhir());
                        dao.Update(cila);
                    }
                }               
                //==============================================================
                dao.Update(ba);
                dao.Simpan(cl);
                //==============================================================
                loadcicilan(d, d.getBa());
                ResetCicilan(d);
                EnableCicilan(d, Boolean.FALSE);
                confirm.Berhasil("data berhasil disimpan");
            } catch (HibernateException he) {
                confirm.Gagal(he);
            }

        }

    }
    
    private Double totalbayar;
    private Double totalcurrent;

    public void ubah(CicilanFrame d, Confirm confirm) {
        totalcurrent = 0d;
        totalbayar = 0d;
        if (d.getBa().getNama_bu().isEmpty()) {
            confirm.Warning("anda belum memilih ba");
        } else {           
            if (!d.getCicilan().getKode_cicilan().isEmpty()) {
                Cicilan cl = dao.getcicilanbyid(d.getCicilan().getId_cicilan());   
                totalcurrent = cl.getTagihan_berjalan();
                cl.setBa(d.getBa());
                cl.setTahap_cicilan(d.getTahapancicilan().getText());
                cl.setNominal_cicilan(HelperUmum.ubahFormatRupiahkeAngka(d.getNominalcicilan().getText()));
                cl.setTanggal_cicilan(d.getTanggalcicilan().getDate());
                cl.setTagihan_berjalan(HelperUmum.ubahFormatRupiahkeAngka(d.getTagihanberjalan().getText()));
                cl.setTotal(cl.getNominal_cicilan() + cl.getTagihan_berjalan());
                cl.setTanggal_notifikasi_terakhir(d.getTanggalnotifikasi().getDate());
                cl.setSentnotification(Boolean.FALSE);
                List<Pembayaran> listbayar = dao.getallpembayaranbykodecicilan(cl.getKode_cicilan());
                if (!listbayar.isEmpty()) {
                    for (Pembayaran pp : listbayar) {
                        totalbayar += pp.getTotal_pembayaran();
                    }
                }
                cl.setTotal_kekurangan(cl.getTotal() - totalbayar);
                //==============================================================
                 Ba ba = dao.getbabyid(d.getBa().getId_ba());
                 ba.setTotal_tunggakan(ba.getTotal_tunggakan()-totalcurrent+cl.getTagihan_berjalan());
                //==============================================================
                dao.Update(ba);
                try {
                    dao.Update(cl);
                    loadcicilan(d, d.getBa());
                    ResetCicilan(d);
                    confirm.Berhasil("data berhasil diubah");
                } catch (HibernateException he) {
                    confirm.Gagal(he);
                }
            } else {
                confirm.Warning("anda belum memilih cicilan");
            }

        }

    }

    public void hapus(CicilanFrame d, Confirm confirm) {
        if (d.getBa().getNama_bu().isEmpty()) {
            confirm.Warning("anda belum memilih ba");
        } else {
            Cicilan cl = dao.getcicilanbyid(d.getCicilan().getId_cicilan());
            if (!cl.getKode_cicilan().isEmpty()) {
                d.setCicilan(cl);
                cl.setBa(d.getBa());
                List<Pembayaran> listpembayaran = dao.getallpembayaranbykodecicilan(cl.getKode_cicilan());
                if(!listpembayaran.isEmpty()){
                    for(Pembayaran pem:listpembayaran){
                        Pembayaran pe = dao.getPembayaranbyid(pem.getId_pembayaran());
                        dao.Delete(pe);
                    }
                }
                try {
                    dao.Delete(cl);
                    loadcicilan(d, d.getBa());
                    ResetCicilan(d);
                    confirm.Berhasil("data berhasil dihapus");
                } catch (HibernateException he) {
                    confirm.Gagal(he);
                }
            } else {
                confirm.Warning("anda belum memilih cicilan");
            }

        }

    }

}
