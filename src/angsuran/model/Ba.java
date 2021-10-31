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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;

@Data
@Entity
@Table(name = "tabel_ba")
public class Ba implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ba;
	
	private String nama_bu;
	
	private String no_entitas;
        
        private String alamat_email;
	
	private String no_ba;
	
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date tanggal_ba;
	
	private Double total_tunggakan;
        
        private Double total_pembayaran;
        
        private Double total_kekurangan;
	
	private Integer tahap_cicilan;
	
	private String status_tunggakan;
	
	private Integer h_notifikasi;
	
        
        
        
}

