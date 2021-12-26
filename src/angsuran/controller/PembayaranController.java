/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.HelperUmum;
import angsuran.helper.JTableRender;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Pembayaran;
import angsuran.tablemodel.CariCicilanTM;
import angsuran.tablemodel.PembayaranTM;
import angsuran.view.PembayaranFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class PembayaranController {

    private List<Pembayaran> list;
    private final AngsuranDao dao;
    private final PembayaranTM model;
    private final DefaultListModel modelba;
    private List<Ba> listba;
    private List<Cicilan> listcicilan;
    private CariCicilanTM modelcicilan;
    private Double totalbayarklik;

    public PembayaranController() {
        dao = new AngsuranDaoImplements();
        list = new ArrayList<>();
        listba = new ArrayList<>();
        listcicilan = new ArrayList<>();
        model = new PembayaranTM();
        modelba = new DefaultListModel();
        modelcicilan = new CariCicilanTM();
    }

    //=========================== CARI CICILAN =================================
    public void LoadKategori(PembayaranFrame d) {
        d.getKategori().removeAllItems();
        String[] nama = {null, "Nama Bu", "No Entitas", "No BA"};
        for (String a : nama) {
            d.getKategori().addItem(a);
        }
    }

    public void PilihKategori(PembayaranFrame d, int kategori) {
        switch (kategori) {
            case 0:
                d.getCariba().setEnabled(false);
                break;
            case 1:
                d.getCariba().setEnabled(true);
                d.getCariba().requestFocus();
                break;
            case 2:
                d.getCariba().setEnabled(true);
                d.getCariba().requestFocus();
                break;
            case 3:
                d.getCariba().setEnabled(true);
                d.getCariba().requestFocus();
                break;
            default:
                d.getCariba().setEnabled(false);
                break;
        }
    }

    public void LoadlistBa(PembayaranFrame d, int kategori, String cari, Confirm c) {
        listba = new ArrayList<>();
        switch (kategori) {
            case 0:
                break;
            case 1:
                listba = dao.getallbabyNamanoentitasandnoba(cari, null, null);
                break;
            case 2:
                listba = dao.getallbabyNamanoentitasandnoba(null, cari, null);
                break;
            case 3:
                listba = dao.getallbabyNamanoentitasandnoba(null, null, cari);
                break;
            default:
                break;
        }
        if (listba.isEmpty()) {
            d.getCariba().requestFocus();
            c.Warning("Data tidak Ditemukan");
        } else {
            Iterator<Ba> itj = listba.iterator();
            while (itj.hasNext()) {
                Ba ba = itj.next();
                modelba.addElement(ba.getNama_bu());
            }
            d.getListba().removeAll();
            d.getListba().setModel(modelba);
            d.getListba().revalidate();
            d.getListba().repaint();
        }

    }
    
    private JTableRender jTableRender;

    public void KlikList(PembayaranFrame d, Confirm c) {
        listcicilan = new ArrayList<>();
        modelcicilan = new CariCicilanTM();
        if (listba.isEmpty()) {
            d.getCariba().requestFocus();
            c.Warning("Silahkan memilih kategori dan mengisi field pencaarian..!!");
        } else {
            Ba ba = dao.getBabynamabu(d.getListba().getSelectedValue());
            listcicilan = dao.getallcicilanbyba(ba);
            if (listcicilan.isEmpty()) {
                d.getTabelcicilan().setModel(new CariCicilanTM());
                d.getTabelcicilan().revalidate();
                d.getTabelcicilan().repaint();
                c.Warning("Belum ada data cicilan untuk bu " + ba.getNama_bu() + " , Silahkan isi data cicilan");

            } else {
                modelcicilan.setList(listcicilan);
                d.getTabelcicilan().setModel(modelcicilan);
                ModalTable tab = new ModalTable();
                tab.setSpecial(6);
                for (int a = 0; a <= modelcicilan.getColumnCount() - 1; a++) {
                    d.getTabelcicilan().getColumnModel().getColumn(a).setCellRenderer(tab);
                }
                jTableRender = new JTableRender(d.getTabelcicilan());
                d.getTabelcicilan().revalidate();
                d.getTabelcicilan().repaint();
            }

        }

    }

    public void KlikListcicilan(PembayaranFrame d, Confirm c, MouseEvent evt) {
        if (listcicilan.isEmpty()) {
            c.Warning("Data cicilan kosong");
        } else {
            int row = d.getTabelcicilan().rowAtPoint(evt.getPoint());
            if (row != -1) {
                int indeks = d.getTabelcicilan().getSelectedRow();
                String kodecicilan = (String) d.getTabelcicilan().getModel().getValueAt(indeks, 1);
                Cicilan cicilan = dao.getcicilanbykodecicilan(kodecicilan);
                d.setCicilan(cicilan);
                d.getKodecicilan().setText(kodecicilan);
                d.getTotalcicilan().setText(HelperUmum.setDoubletoIDR(cicilan.getNominal_cicilan()));
                d.getTotaltunggakan().setText(HelperUmum.setDoubletoIDR(cicilan.getTagihan_berjalan()));
                d.getSubtotal().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal()));
                //======================================================================================
                List<Pembayaran> listbayar = dao.getallpembayaranbykodecicilan(d.getKodecicilan().getText());
                totalbayarklik = 0d;
                if (!listbayar.isEmpty()) {
                    for (Pembayaran bayar : listbayar) {
                        totalbayarklik += bayar.getTotal_pembayaran();
                    }
                }
                //==================================================================
                d.getTotalpembayaran().setText(HelperUmum.setDoubletoIDR(totalbayarklik));
                d.getTotalkekurangan().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal() - totalbayarklik));
                LoadAllBa(d, kodecicilan);
                Enable(d, true);

            }
        }
    }

    //==========================================================================
    public void LoadAllBa(PembayaranFrame d, String kodecicilan) {
        list = dao.getallpembayaranbykodecicilan(kodecicilan);
        model.setList(list);
        d.getTablepembayaran().setModel(model);
        for (int a = 0; a <= model.getColumnCount() - 1; a++) {
            d.getTablepembayaran().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTablepembayaran());
        d.getTablepembayaran().revalidate();
        d.getTablepembayaran().repaint();
    }

    public void klik(PembayaranFrame d, MouseEvent evt) {
        int row = d.getTablepembayaran().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTablepembayaran().getSelectedRow();
            Long id = (Long) d.getTablepembayaran().getModel().getValueAt(indeks, 1);
            Pembayaran p = dao.getPembayaranbyid(id);
            d.setPembayaran(p);
            d.getTanggal().setDate(p.getTanggal_pembayaran());
            d.getPembayarancicilan().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_cicilan()));
            d.getPembayarantunggakan().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_tunggakan_berjalan()));
            d.getTotal().setText(HelperUmum.setDoubletoIDR(p.getPembayaran_cicilan() + p.getPembayaran_tunggakan_berjalan()));
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
            if (!listbayar.isEmpty()) {
                for (Pembayaran bayar : listbayar) {
                    totalbayarklik += bayar.getTotal_pembayaran();
                }
            }
            //==================================================================
            d.getTotalpembayaran().setText(HelperUmum.setDoubletoIDR(totalbayarklik));
            d.getTotalkekurangan().setText(HelperUmum.setDoubletoIDR(cicilan.getTotal() - totalbayarklik));
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
            if (co.getTotal_kekurangan() <= 0d) {
                co.setStatus("LUNAS");
            } else {
                co.setStatus("BELUM LUNAS");
            }
            //======================================================================================================================
            Ba ba = dao.getbabyid(co.getBa().getId_ba());
            ba.setTotal_pembayaran(ba.getTotal_pembayaran() + p.getPembayaran_cicilan());
            ba.setTotal_kekurangan(ba.getTotal_tunggakan() - ba.getTotal_pembayaran());
            if (Objects.equals(ba.getTotal_pembayaran(), ba.getTotal_tunggakan())) {
                ba.setStatus_tunggakan("LUNAS");
            } else {
                ba.setStatus_tunggakan("BELUM LUNAS");
            }

            //======================================================================================================================
            try {
                dao.Update(ba);
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
            if (total >= 0d) {
                cil.setStatus("BELUM LUNAS");
            }
            //==================================================================
            Ba ba = dao.getbabyid(cil.getBa().getId_ba());
            ba.setTotal_pembayaran(ba.getTotal_pembayaran() - p.getPembayaran_cicilan());
            ba.setTotal_kekurangan(ba.getTotal_kekurangan() + p.getPembayaran_cicilan());
            if (!Objects.equals(ba.getTotal_pembayaran(), ba.getTotal_tunggakan())) {
                ba.setStatus_tunggakan("BELUM LUNAS");
            }
            try {
                dao.Update(ba);
                dao.Update(cil);
                dao.Delete(p);
            } catch (HibernateException he) {
                c.Gagal(he);
            }
        }

    }

}
