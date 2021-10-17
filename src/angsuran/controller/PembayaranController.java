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
import angsuran.model.Pembayaran;
import angsuran.tablemodel.PembayaranTM;
import angsuran.view.PembayaranFrame;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class PembayaranController {

    private List<Pembayaran> list;
    private final AngsuranDao dao;
    private final PembayaranTM model;

    public PembayaranController() {
        dao = new AngsuranDaoImplements();
        list = new ArrayList<>();
        model = new PembayaranTM();
    }

    public void LoadAllBa(PembayaranFrame d, String kodecicilan) {
        list = dao.getallpembayaranbykodecicilan(kodecicilan);
        model.setList(list);
        d.getTablepembayaran().setModel(model);
        d.getTablepembayaran().getColumnModel().getColumn(2).setCellRenderer(new ModalTable());
        d.getTablepembayaran().getColumnModel().getColumn(3).setCellRenderer(new ModalTable());
        d.getTablepembayaran().getColumnModel().getColumn(4).setCellRenderer(new ModalTable());
        d.getTablepembayaran().getColumnModel().getColumn(5).setCellRenderer(new ModalTable());
        d.getTablepembayaran().revalidate();
        d.getTablepembayaran().repaint();
    }
    
    
    
    public void klik(PembayaranFrame d, MouseEvent evt){       
        int row = d.getTablepembayaran().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTablepembayaran().getSelectedRow();
            Long id = (Long) d.getTablepembayaran().getModel().getValueAt(indeks, 1);
            Pembayaran p = dao.getPembayaranbyid(id);
            d.setPembayaran(p);
            d.getTanggal().setDate(p.getTanggal_pembayaran());
            d.getPembayarancicilan().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_cicilan()));
            d.getPembayarantunggakan().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_tunggakan_berjalan()));
            d.getTotal().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_cicilan()+p.getPembayaran_tunggakan_berjalan()));            
        }
    }
    
    public void ResetCicilan(PembayaranFrame d) {
        d.getKodecicilan().setText(null);
        d.getTotalcicilan().setText(null);
        d.getTotaltunggakan().setText(null);
        d.getSubtotal().setText(null);
        d.getTotalpembayaran().setText(null);
        d.getTotalkekurangan().setText(null);
    }

    public void ResetPembayaran(PembayaranFrame d) {
        d.getTanggal().setDate(new Date());
        d.getPembayarancicilan().setText(null);
        d.getPembayarantunggakan().setText(null);
        d.getTotal().setText(null);
    }

    public void Enable(PembayaranFrame d, boolean yes) {
        d.getTanggal().setDate(new Date());
        d.getPembayarancicilan().setEnabled(yes);
        d.getPembayarantunggakan().setEnabled(yes);
        d.getTotal().setEnabled(yes);
    }

    
    public void settotal(PembayaranFrame d) {
        double cicilan = HelperUmum.ubahFormatRupiahkeAngka(d.getPembayarancicilan().getText());
        double tagihan = HelperUmum.ubahFormatRupiahkeAngka(d.getPembayarantunggakan().getText());
        double total = cicilan + tagihan;
        d.getTotal().setText(HelperUmum.setDoubletoIDR(total));
       
    }

    private Double totalbayarklik;
    
    public void FindCicilan(PembayaranFrame d, Confirm c, String kodecicilan) {
        totalbayarklik = 0d;
        Cicilan cicilan = dao.getcicilanbykodecicilan(kodecicilan);
        if (cicilan.getKode_cicilan().isEmpty()) {
            c.Warning("cicilan tidak ditemukan");
        } else {
            d.setCicilan(cicilan);
            d.getTotalcicilan().setText(HelperUmum.setDoubletoIDR(cicilan.getNominal_cicilan()));
            d.getTotaltunggakan().setText(HelperUmum.setDoubletoIDR(cicilan.getTagihan_berjalan()));
            d.getSubtotal().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal()));
            //======================================================================================
            List<Pembayaran> listbayar = dao.getallpembayaranbykodecicilan(d.getKodecicilan().getText());
            if(!listbayar.isEmpty()){
                for(Pembayaran bayar:listbayar){
                    totalbayarklik += bayar.getTotal_pembayaran();
                }
            }
            //==================================================================
            d.getTotalpembayaran().setText(HelperUmum.setDoubletoIDR(totalbayarklik));
            d.getTotalkekurangan().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal()-totalbayarklik));            
            LoadAllBa(d, kodecicilan);
            Enable(d, true);
        }

    }

    public void simpan(PembayaranFrame d, Confirm c) {
        if (d.getCicilan().getKode_cicilan().isEmpty()) {
            c.Warning("anda belum memilih cicilan");
            d.getKodecicilan().requestFocus();
        } else {
            Pembayaran p = new Pembayaran();
            p.setCicilan(d.getCicilan());
            p.setTanggal_pembayaran(d.getTanggal().getDate());
            p.setPembayaran_cicilan(HelperUmum.ubahFormatRupiahkeAngka(d.getPembayarancicilan().getText()));
            if (p.getPembayaran_cicilan().toString().isEmpty()) {
                c.Warning("data pembayaran cicilan masih kosong");
                d.getPembayarancicilan().requestFocus();
            }
            p.setPembayaran_tunggakan_berjalan(HelperUmum.ubahFormatRupiahkeAngka(d.getPembayarantunggakan().getText()));
            if (p.getPembayaran_tunggakan_berjalan().toString().isEmpty()) {
                c.Warning("apabila data pembayaran tunggakan tidak ada anda dapat mengisi dengan angka 0");
                d.getPembayarantunggakan().requestFocus();

            }
            p.setTotal_pembayaran(p.getPembayaran_cicilan() + p.getPembayaran_tunggakan_berjalan());
            //======================================================================================================================
            Cicilan co = dao.getcicilanbyid(d.getCicilan().getId_cicilan());
            co.setTotal_kekurangan(co.getTotal_kekurangan() - (p.getPembayaran_cicilan() + p.getPembayaran_tunggakan_berjalan()));
            if(co.getTotal_kekurangan() <= 0d){
                co.setStatus("LUNAS");
            }
            //======================================================================================================================
            try {
                dao.Update(co);
                dao.Simpan(p);
            } catch (HibernateException he) {
                c.Gagal(he);
            }
        }
    }

    private double currentcicilan = 0d;
    private double currenttunggakan = 0d;
   
    public void delete(PembayaranFrame d, Confirm c) {
        currentcicilan = 0d;
        currenttunggakan = 0d;
        if (d.getCicilan().getKode_cicilan().isEmpty()) {
            c.Warning("anda belum memilih cicilan");
            d.getKodecicilan().requestFocus();
        } else {
            Pembayaran p = dao.getPembayaranbyid(d.getPembayaran().getId_pembayaran());
            currentcicilan = p.getPembayaran_cicilan();
            currenttunggakan = p.getPembayaran_tunggakan_berjalan();
            Double total = currentcicilan + currenttunggakan;
            //==================================================================
            Cicilan cil = dao.getcicilanbyid(d.getCicilan().getId_cicilan());
            cil.setTotal_kekurangan(cil.getTotal_kekurangan() + total);
            if(total >= 0d){
                cil.setStatus("BELUM LUNAS");
            }
            try {
                dao.Update(cil);
                dao.Delete(p);
            } catch (HibernateException he) {
                c.Gagal(he);
            }
        }

    }


}
