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
import angsuran.model.Notifikasi;
import angsuran.model.Pembayaran;
import angsuran.tablemodel.BaTableModel;
import angsuran.view.BAFrame;
import com.stripbandunk.jwidget.model.DefaultPaginationModel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class BaController {

    private List<Ba> list = new ArrayList<>();
    private AngsuranDao dao;
    private BaTableModel model;
    private JTableRender jTableRender;

    public BaController() {
        dao = new AngsuranDaoImplements();
        model = new BaTableModel();
    }

    //=========================== PAGINATION
    public static List<Ba> listPagination = new ArrayList<>();

    private static int no;

    public static List<Ba> getListPagination() {
        return listPagination;
    }

    public static void setListPagination(List<Ba> listPagination) {
        no = 1;
        BaController.listPagination = listPagination;
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

    public void actionPagination(BAFrame d, int page) {
        BaTableModel modelku = new BaTableModel();
        d.getTableba().setModel(modelku);
        List<Ba> listPerPage = getListByPagination(BaController.getListPagination(), page);
        modelku = new BaTableModel();
        modelku.setList(listPerPage);
        modelku.fireTableDataChanged();
        d.getTableba().setModel(modelku);
        for(int a=0; a<=modelku.getColumnCount()-1; a++){
            d.getTableba().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTableba());
        d.getTableba().revalidate();
        d.getTableba().repaint();
    }
    //===================================================================

    public void LoadAllBA(BAFrame d, String nama_bu, String no_entitas, String no_ba) {
        list = new ArrayList<>();
        list = dao.getallbabyNamanoentitasandnoba(nama_bu, no_entitas, no_ba);
        BaController.setListPagination(list);
        model.setList(getListByPagination(list, 0));
        model.fireTableDataChanged();
        d.getTableba().setModel(model);
         for(int a=0; a<=model.getColumnCount()-1; a++){
            d.getTableba().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTableba());
        d.getPaginba().setModel(getDefPaginModel(list.size()));
        d.getTableba().revalidate();
        d.getTableba().repaint();

    }

    public void LoadSearch(BAFrame d, Confirm co) {
        String cari = d.getCariba().getText();
        switch (d.getKategori().getSelectedIndex()) {
            case 0:
                co.Warning("Anda Belum Memilih Kategori");
                d.getCariba().requestFocus();
                break;
            case 1:
                LoadAllBA(d, cari, null, null);
                d.getCariba().selectAll();
                break;
            case 2:
                LoadAllBA(d, null, cari, null);
                d.getCariba().selectAll();
                break;
            case 3:
                LoadAllBA(d, null, null, cari);
                d.getCariba().selectAll();
                break;

        }
    }

    public void loaddefaulttable(BAFrame d) {
        LoadAllBA(d, null, null, null);
        d.getKategori().setSelectedIndex(0);
        d.getCariba().setText(null);
    }

    public void klik(BAFrame d, MouseEvent evt) {
        int row = d.getTableba().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTableba().getSelectedRow();
            Long id = (Long) d.getTableba().getModel().getValueAt(indeks, 1);
            Ba ba = dao.getbabyid(id);
            d.setBa(ba);
            d.getNamabu().setText(ba.getNama_bu());
            d.getNoentitas().setText(ba.getNo_entitas());
            d.getNoba().setText(ba.getNo_ba());
            d.getTanggalba().setDate(ba.getTanggal_ba());
            d.getTotaltunggakan().setText(String.format("%,.0f", ba.getTotal_tunggakan()));
            d.getTahapcicilan().setText(ba.getTahap_cicilan().toString());
            d.getHnotifikasi().setText(ba.getH_notifikasi().toString());
            d.getEmail().setText(ba.getAlamat_email());
        }

    }

    public void setenable(BAFrame d, Boolean yes) {
        d.getNamabu().setEditable(yes);
        d.getNoentitas().setEditable(yes);
        d.getNoba().setEditable(yes);
        d.getTanggalba().setEnabled(yes);
        d.getTotaltunggakan().setEditable(yes);
        d.getTahapcicilan().setEditable(yes);
        d.getHnotifikasi().setEditable(yes);
        d.getEmail().setEditable(yes);
    }

    public void reset(BAFrame d) {
        d.getNamabu().setText(null);
        d.getNoentitas().setText(null);
        d.getNoba().setText(null);
        d.getTanggalba().setDate(new Date());
        d.getTotaltunggakan().setText(null);
        d.getTahapcicilan().setText(null);
        d.getHnotifikasi().setText(null);
        d.getEmail().setText(null);
        d.setBa(new Ba());
    }

    public void simpan(BAFrame d, Confirm confirm) {
        Ba ba = new Ba();
        ba.setNama_bu(d.getNamabu().getText());
        ba.setNo_entitas(d.getNoentitas().getText());
        ba.setNo_ba(d.getNoba().getText());
        ba.setTanggal_ba(d.getTanggalba().getDate());
        ba.setTotal_tunggakan(HelperUmum.ubahFormatRupiahkeAngka(d.getTotaltunggakan().getText()));
        ba.setTotal_pembayaran(0d);
        ba.setTotal_kekurangan(ba.getTotal_tunggakan());
        ba.setTahap_cicilan(Integer.valueOf(d.getTahapcicilan().getText()));
        ba.setAlamat_email(d.getEmail().getText());
        ba.setH_notifikasi(Integer.valueOf(d.getHnotifikasi().getText()));
        ba.setStatus_tunggakan("BELUM LUNAS");
        try {
            dao.Simpan(ba);
            loaddefaulttable(d);
            confirm.Berhasil("data berhasil disimpan");
        } catch (HibernateException he) {
            confirm.Gagal(he);
        }
    }

    
    public void update(BAFrame d, Confirm confirm) {
        if (d.getBa() == null) {
            confirm.Warning("Anda Belum memilih data !!");
        } else {
            Ba ba = dao.getbabyid(d.getBa().getId_ba());
            List<Cicilan> listcicilan = dao.getallcicilanbyba(ba);
                if(listcicilan.isEmpty()){
                    ba.setNama_bu(d.getNamabu().getText());
                    ba.setNo_entitas(d.getNoentitas().getText());
                    ba.setNo_ba(d.getNoba().getText());
                    ba.setTanggal_ba(d.getTanggalba().getDate());
                    ba.setTotal_tunggakan(HelperUmum.ubahFormatRupiahkeAngka(d.getTotaltunggakan().getText()));
                    ba.setTahap_cicilan(Integer.valueOf(d.getTahapcicilan().getText()));
                    ba.setAlamat_email(d.getEmail().getText());
                    ba.setH_notifikasi(Integer.valueOf(d.getHnotifikasi().getText()));
                    try {
                        dao.Update(ba);
                        loaddefaulttable(d);
                        confirm.Berhasil("data berhasil diupdate");
                    } catch (HibernateException he) {
                        confirm.Gagal(he);
                    }
                }else{
                    ba.setNama_bu(d.getNamabu().getText());
                    ba.setNo_entitas(d.getNoentitas().getText());
                    ba.setNo_ba(d.getNoba().getText());
                    ba.setTanggal_ba(d.getTanggalba().getDate());
                    ba.setTahap_cicilan(Integer.valueOf(d.getTahapcicilan().getText()));
                    ba.setAlamat_email(d.getEmail().getText());
                    ba.setH_notifikasi(Integer.valueOf(d.getHnotifikasi().getText()));
                    try {
                        dao.Update(ba);
                        loaddefaulttable(d);
                        confirm.Berhasil("tidak dapat mengupdate data total tunggakan karena data karena ada data cicilan terkait namun data lain berhasil diupdate");
                    } catch (HibernateException he) {
                        confirm.Gagal(he);
                    }
                }   
        }
    }

    public void hapus(BAFrame d, Confirm confirm) {
        if (d.getBa().getNama_bu().isEmpty()) {
            confirm.Warning("anda belum memilih data");
        } else {
            int a = JOptionPane.showConfirmDialog(d, "Anda Yakin ingin menghapus data ini dan seluruh data yg terkait?", "Warning", JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.YES_OPTION) {
                try {
                    Ba ba = dao.getbabyid(d.getBa().getId_ba());
                    //======================================================
                    List<Notifikasi> listnotifikasi = dao.getnotifikasibyba(ba);
                    if (!listnotifikasi.isEmpty()) {
                        for (Notifikasi nf : listnotifikasi) {
                            Notifikasi not = dao.getnotifikasibyid(nf.getId_notifikasi());
                            dao.Delete(not);
                        }
                    }
                    //======================================================
                    List<Cicilan> listcicilan = dao.getallcicilanbyba(ba);
                    if (!listcicilan.isEmpty()) {
                        for (Cicilan cil : listcicilan) {
                            Cicilan ca = dao.getcicilanbyid(cil.getId_cicilan());
                            List<Pembayaran> listpembayaran = dao.getallpembayaranbykodecicilan(ca.getKode_cicilan());
                            if (!listpembayaran.isEmpty()) {
                                for (Pembayaran pb : listpembayaran) {
                                    Pembayaran pp = dao.getPembayaranbyid(pb.getId_pembayaran());
                                    dao.Delete(pp);
                                }
                            }

                            dao.Delete(ca);
                        }
                    }

                    dao.Delete(ba);
                    loaddefaulttable(d);
                    confirm.Berhasil("Data Ba dengan data cicilan serta pembayaran yang terkait Berhasil dihapus..!!!");
                } catch (HibernateException he) {
                    confirm.Gagal(he);
                }
            } else {
                d.getTableba().clearSelection();
                d.setBa(new Ba());
            }
        }
    }

    public void loadkategori(BAFrame d) {
        d.getKategori().addItem(null);
        d.getKategori().addItem("Nama BU");
        d.getKategori().addItem("No Entitas");
        d.getKategori().addItem("No BA");

    }

}
