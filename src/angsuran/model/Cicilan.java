/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

/**
 *
 * @author User
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Data
@Table
public class Cicilan implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_cicilan;
        
        private String kode_cicilan;
        
         private String tahap_cicilan;
	
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date tanggal_cicilan;
	
	private Double nominal_cicilan;
	
	private Double tagihan_berjalan;
	
	private Double total;
	
	private Double total_kekurangan;
	
	private String status;
	
	@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
        @JoinColumn(name = "cicilan_ba_id")
	private Ba ba;
        @Column(insertable = false, updatable = false)
        private Long cicilan_ba_id;
        
        @Temporal(javax.persistence.TemporalType.DATE)
        private Date tanggal_notifikasi_terakhir;
        
        private Boolean sentnotification;

}
