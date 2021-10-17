/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author User
 */


@Entity
@Table
@Data
public class Notifikasi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_notifikasi;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ba_notifikasi")
    private Ba ba;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tanggal_kirim;
    
    private String kode_surat;
    
    private String pathpdf;
    
    private String status;
    
    
    
    
}
