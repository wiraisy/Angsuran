/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
 * @author Wiraisy
 */
@Data
@Entity
@Table
public class Pembayaran implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_pembayaran;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tanggal_pembayaran;
    
    private Double pembayaran_cicilan;
    
    private Double pembayaran_tunggakan_berjalan;
    
    private Double total_pembayaran;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "pembayaran_cicilan_id")
    private Cicilan cicilan;    
}
