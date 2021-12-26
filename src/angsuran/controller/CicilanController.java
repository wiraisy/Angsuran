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
import angsuran.helper.JTableRender;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Pembayaran;
import angsuran.tablemodel.CariBUTM;
import angsuran.tablemodel.CicilanTM;
import angsuran.view.CicilanFrame;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class CicilanController {

    private AngsuranDao dao = new AngsuranDaoImplements();
    private CicilanTM model = new CicilanTM();
    private List<Cicilan> list = new ArrayList<>();
    private JTableRender jTableRender;
    private final Generatekode kode = new Generatekode();
    private Map<Integer, String> map = new HashMap<>();
    //=========================================================================
     
    private CariBUTM catm = new CariBUTM();
    private List<Ba> listba = new ArrayList<>();
    
    
   //=========================== PAGINATION
    public static List<Ba> listPagination = new ArrayList<>();

    private static int no;

    public static List<Ba> getListPagination() {
        return listPagination;
    }

    public static void setListPagination(List<Ba> listPagination) {
        no = 1;
        CicilanController.listPagination = listPagination;
    }

    public DefaultPaginationModel getDefPaginModel(int totalItem) {
        DefaultPaginationModel dpm = new DefaultPaginationModel();
        dpm.setTotalItem(totalItem);
        dpm.setPageSize(50);
        return dpm;
    }

    public List<Ba> getListByPagination(List<Ba> listP, int page) {
        int sizePerPage = 50;
        int from = Math.max(0, page * sizePerPage);
        int to = Math.min(listP.size(), (page + 1) * sizePerPage);
        return listP.subList(from, to);
    }

    
    public void actionPagination(CicilanFrame d, int page) {
        CariBUTM modelku = new CariBUTM();
        d.getTabelbucari().setModel(modelku);
        List<Ba> listPerPage = getListByPagination(BaController.getListPagination(), page);
        modelku = new CariBUTM();
        modelku.setList(listPerPage);
        modelku.fireTableDataChanged();
        d.getTabelbucari().setModel(modelku);
        map.put(5,"BELUM LUNAS");
        ModalTable mtab = new ModalTable();
        mtab.setMap(map);
        for(int a=0; a<=modelku.getColumnCount()-1; a++){
            d.getTabelbucari().getColumnModel().getColumn(a).setCellRenderer(mtab);
        }
        jTableRender = new JTableRender(d.getTabelbucari());
        d.getTabelbucari().revalidate();
        d.getTabelbucari().repaint();
    }
    //===================================================================

    public void LoadAllBA(CicilanFrame d, String nama_bu) {
        listba = new ArrayList<>();
        listba = dao.getallbabyNamanoentitasandnoba(nama_bu, null, null);
        CicilanController.setListPagination(listba);
        catm.setList(getListByPagination(listba, 0));
        catm.fireTableDataChanged();
        d.getTabelbucari().setModel(catm);
        map.put(5,"BELUM LUNAS");
        ModalTable mtab = new ModalTable();
        mtab.setMap(map);
        for(int a=0; a<=catm.getColumnCount()-1; a++){
            d.getTabelbucari().getColumnModel().getColumn(a).setCellRenderer(mtab);
        }
        jTableRender = new JTableRender(d.getTabelbucari());
        d.getPaginationbu().setModel(getDefPaginModel(listba.size()));
        d.getTabelbucari().revalidate();
        d.getTabelbucari().repaint();

    }
    
    public void LoadKlikCariba(CicilanFrame d,Confirm c, MouseEvent evt){
        int row = d.getTabelbucari().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTabelbucari().getSelectedRow();
            String noentitas = (String) d.getTabelbucari().getModel().getValueAt(indeks, 2);
            Ba ba = dao.getBabynoentitas(noentitas);
            d.setBa(ba);
            d.getNoba().setText(noentitas);
            d.getNamaba().setText(ba.getNama_bu());
            d.getTotaltunggakan().setText(HelperUmum.setDoubletoIDR(ba.getTotal_tunggakan()));
            d.getTotalpembayaran().setText(HelperUmum.setDoubletoIDR(ba.getTotal_pembayaran()));
            d.getTotalkekurangan().setText(HelperUmum.setDoubletoIDR(ba.getTotal_kekurangan()));
            d.getKodecicilan().setText(kode.Generatekodecicilan(ba, c));
            //==================================================================
            loadcicilan(d, ba);
        }
    }
    //=========================================================================

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

    public double loadallnominalcicilan(Ba ba) {
        Double total = 0d;
        List<Cicilan> listt = dao.getallcicilanbyba(ba);
        if (!list.isEmpty()) {
            for (Cicilan cil : listt) {
                total += cil.getNominal_cicilan();
            }
        }
        return total;
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
        model.fireTableDataChanged();
        d.getTablecicilan().setModel(model);
        ModalTable tab = new ModalTable();
        tab.setSpecial(8);
        for(int a=0; a<=model.getColumnCount()-1; a++){
            d.getTablecicilan().getColumnModel().getColumn(a).setCellRenderer(tab);
        }
        jTableRender = new JTableRender(d.getTablecicilan());
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
            d.getTotalpembayaran().setText(HelperUmum.setDoubletoIDR(ba.getTotal_pembayaran()));
            d.getTotalkekurangan().setText(HelperUmum.setDoubletoIDR(ba.getTotal_kekurangan()));
            d.getKodecicilan().setText(kode.Generatekodecicilan(ba, confirm));
            loadcicilan(d, ba);
        }
    }

    public void jumlahnominaltagihan(CicilanFrame d, Confirm confirm) {
        if (d.getNominalcicilan().getText().isEmpty()) {
            d.getNominalcicilan().requestFocus();
        } else {
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
                if (loadallnominalcicilan(ba) > ba.getTotal_kekurangan()) {
                    confirm.Warning("Nominal cicilan melebihi sisa tunggakan");
                    d.getNominalcicilan().requestFocusInWindow();
                    d.getNominalcicilan().selectAll();
                } else {
                    ba.setTotal_tunggakan(ba.getTotal_tunggakan() + cl.getTagihan_berjalan());
                    //==============================================================
                    List<Cicilan> listcil = dao.getallcicilanbybaandstatus(ba, "BELUM LUNAS", cl.getTanggal_notifikasi_terakhir());
                    if (!listcil.isEmpty()) {
                        for (Cicilan cilo : listcil) {
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
                }

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
                ba.setTotal_tunggakan(ba.getTotal_tunggakan() - totalcurrent + cl.getTagihan_berjalan());
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
                if (!listpembayaran.isEmpty()) {
                    for (Pembayaran pem : listpembayaran) {
                        Ba ba = dao.getBabynoentitas(cl.getBa().getNo_entitas());
                        ba.setTotal_kekurangan(ba.getTotal_kekurangan() + pem.getPembayaran_cicilan());
                        ba.setTotal_pembayaran(ba.getTotal_pembayaran() - pem.getPembayaran_cicilan());
                        dao.Update(ba);
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
