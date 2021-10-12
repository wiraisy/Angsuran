/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author User
 */

public class BaModel {

    private Long id_ba;
    
    private String kodesurat;

    private String nama_bu;

    private String no_entitas;

    private String alamat_email;

    private String no_ba;

    private Date tanggal_ba;

    private Double total_tunggakan;

    private Integer tahap_cicilan;

    private String status_tunggakan;

    private Integer h_notifikasi;
    
    private Double tunggakan_total;
    
    private Double kekurangan_total;
    
    private List<Cicilan> listcicilan = new ArrayList<>();
    
    private JRBeanCollectionDataSource cicilandatasource;

    public BaModel() {
    }

    public Long getId_ba() {
        return id_ba;
    }

    public void setId_ba(Long id_ba) {
        this.id_ba = id_ba;
    }

    public String getKodesurat() {
        return kodesurat;
    }

    public void setKodesurat(String kodesurat) {
        this.kodesurat = kodesurat;
    }

    public String getNama_bu() {
        return nama_bu;
    }

    public void setNama_bu(String nama_bu) {
        this.nama_bu = nama_bu;
    }

    public String getNo_entitas() {
        return no_entitas;
    }

    public void setNo_entitas(String no_entitas) {
        this.no_entitas = no_entitas;
    }

    public String getAlamat_email() {
        return alamat_email;
    }

    public void setAlamat_email(String alamat_email) {
        this.alamat_email = alamat_email;
    }

    public String getNo_ba() {
        return no_ba;
    }

    public void setNo_ba(String no_ba) {
        this.no_ba = no_ba;
    }

    public Date getTanggal_ba() {
        return tanggal_ba;
    }

    public void setTanggal_ba(Date tanggal_ba) {
        this.tanggal_ba = tanggal_ba;
    }

    public Double getTotal_tunggakan() {
        return total_tunggakan;
    }

    public void setTotal_tunggakan(Double total_tunggakan) {
        this.total_tunggakan = total_tunggakan;
    }

    public Integer getTahap_cicilan() {
        return tahap_cicilan;
    }

    public void setTahap_cicilan(Integer tahap_cicilan) {
        this.tahap_cicilan = tahap_cicilan;
    }

    public String getStatus_tunggakan() {
        return status_tunggakan;
    }

    public void setStatus_tunggakan(String status_tunggakan) {
        this.status_tunggakan = status_tunggakan;
    }

    public Integer getH_notifikasi() {
        return h_notifikasi;
    }

    public void setH_notifikasi(Integer h_notifikasi) {
        this.h_notifikasi = h_notifikasi;
    }

    public Double getTunggakan_total() {
        return tunggakan_total;
    }

    public void setTunggakan_total(Double tunggakan_total) {
        this.tunggakan_total = tunggakan_total;
    }

    public Double getKekurangan_total() {
        return kekurangan_total;
    }

    public void setKekurangan_total(Double kekurangan_total) {
        this.kekurangan_total = kekurangan_total;
    }

    public List<Cicilan> getListcicilan() {
        return listcicilan;
    }

    public void setListcicilan(List<Cicilan> listcicilan) {
        this.listcicilan = listcicilan;
    }

    public JRBeanCollectionDataSource getCicilandatasource() {
        return new JRBeanCollectionDataSource(listcicilan);
    }


}
