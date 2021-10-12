/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.helper;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Notifikasi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class Generatekode {
    
    private final AngsuranDao dao = new AngsuranDaoImplements();
 
    public String Generatekodecicilan(Ba bab, Confirm confirm){ 
        String nama = null;
        List<Cicilan> list = dao.getidmaxidbycicilanid(bab.getId_ba());
        if(!list.isEmpty()){
            try {
                for(Cicilan cil:list){
                    String Nipa = cil.getKode_cicilan();
                    String[] sub = Nipa.split("-");
                    int K = Integer.parseInt(sub[1]);
                    nama = String.valueOf(sub[0])+"-"+String.valueOf(K+1);         
                }                           
            } catch (NumberFormatException ex) {
                confirm.Gagal(ex);
            }
        }else{          
              nama = bab.getNo_entitas()+"-"+"1";
        }
        
        return nama;
    }
    
     public String Generatekodesurat(){ 
        String nama = null;
        SimpleDateFormat f = new SimpleDateFormat("MMYY");
        List<Notifikasi> list = dao.getnotifikasimaxid();
        if(!list.isEmpty()){
            try {
                for(Notifikasi not:list){
                    String Nipa = not.getKode_surat();
                    String[] sub = Nipa.split("-");
                    int K = Integer.parseInt(sub[0])+1;
                    nama = K +"-"+"1.03"+"-"+f.format(new Date())+"-S";         
                }                           
            } catch (NumberFormatException ex) {
                ex.getMessage();
            }
        }else{          
              nama = "1-1.03-"+f.format(new Date())+"-S";
        }
        
        return nama;
    }
    
}
