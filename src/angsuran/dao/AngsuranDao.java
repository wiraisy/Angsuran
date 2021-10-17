/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.dao;

/**
 *
 * @author User
 */
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Notifikasi;
import angsuran.model.Pembayaran;
import angsuran.model.SmtpModel;
import angsuran.model.Userku;
import java.util.Date;
import java.util.List;


public interface AngsuranDao {
	
	public void Simpan(Object ob);
	
	public void Update(Object ob);
	
	public void Delete(Object ob);
        
        //======================================================================
        
        public Pembayaran getPembayaranbyid(Long id);
	
	public Ba getbabyid(Long id);
	
	public Cicilan getcicilanbyid(Long id);
        
        //======================================================================
        
        public List<Ba> getallbabyNamanoentitasandnoba(String nama_bu, String no_entitas, String no_ba);
	
	public List<Ba> getallbabystatus(String status);
        
        public Ba getBabynoentitas(String noentitas);
        
        //======================================================================
	
	public List<Cicilan> getallcicilanbyba(Ba ba);
        
        public List<Cicilan> getallcicilanbybaandstatus(Ba ba, String status, Date tanggal);
	
	public List<Cicilan> getallCicilanbystatusandtempo(String status, Integer tempo);
        
        public List<Cicilan> getallCicilanbystatusandjatuhtempo(String status);
        
        public List<Cicilan> getidmaxidbycicilanid(Long id_ba);
        
        public Cicilan getcicilanbykodecicilan(String kodecicilan);
        
        public List<Cicilan> getallcicilanjatuhtempo();
        //======================================================================
        
        public Userku getuserbyId (Long id);
        
        public Userku getLoginByUsername (String username);
        
        public List<Userku> getalluser();
        
        //====================== PEMBAYARAN ====================================
        
        public List<Pembayaran> getallpembayaranbykodecicilan(String kodecicilan);
        
        //======================NOTIFIKASI======================================
        
        public List<Notifikasi> getnotifikasibyba(Ba ba);
        
        public List<Notifikasi> getnotifikasibynamabu(String namabu, Confirm con);
        
        public List<Notifikasi> getallNotifikasibydate(Date tanggalnotifikasi);
        
        public List<Notifikasi> getnotifikasimaxid();
        
        public Notifikasi getnotifikasibyid(Long id);
        
         public List<Notifikasi> getnotifikasibystatus(String status);
        
        //======================================================================
        
        public SmtpModel getSmtpbyId(Long id);
        
        public List<SmtpModel> getalllistsmtpmodel();
        
        public List<SmtpModel> getalllistsmtpmodelbyactive(Boolean active);
        
        public SmtpModel getactivesmtp();
        

         
        
        
	
	

}

