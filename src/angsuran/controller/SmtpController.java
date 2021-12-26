/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.JTableRender;
import angsuran.helper.ModalTable;
import angsuran.listener.Confirm;
import angsuran.model.SmtpModel;
import angsuran.tablemodel.SmtpTM;
import angsuran.view.SmtpFrame;
import angsuran.whatsapp.SendMailWithAttachment;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.HibernateException;

/**
 *
 * @author User
 */
public class SmtpController {
    
    private SmtpTM model = new SmtpTM();
    private AngsuranDao dao = new  AngsuranDaoImplements();
    private SendMailWithAttachment mail = new SendMailWithAttachment();
    private List<SmtpModel> list = new ArrayList<>();
    private JTableRender jTableRender;
    
    public void LoadTable(SmtpFrame d){
        list = dao.getalllistsmtpmodel();
        model.setList(list);
        d.getTablesmtp().setModel(model);
        for(int a=0; a<=model.getColumnCount()-1; a++){
            d.getTablesmtp().getColumnModel().getColumn(a).setCellRenderer(new ModalTable());
        }
        jTableRender = new JTableRender(d.getTablesmtp());
        d.getTablesmtp().revalidate();
        d.getTablesmtp().repaint();
    }
    
     public void klik(SmtpFrame d, MouseEvent evt) {
        int row = d.getTablesmtp().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTablesmtp().getSelectedRow();
            Long id = (Long) d.getTablesmtp().getModel().getValueAt(indeks, 0);
            SmtpModel models = dao.getSmtpbyId(id);
            d.setModel(models);
            d.getHostname().setText(models.getHostname());
            d.getUsername().setText(models.getUsername());
            d.getPasword().setText(models.getPassword());
            d.getPort().setText(models.getPort());
        }
     }
    
    public void setenable(SmtpFrame d, Boolean yes){
        d.getHostname().setEnabled(yes);
        d.getPasword().setEnabled(yes);
        d.getUsername().setEnabled(yes);
        d.getPort().setEnabled(yes);
    }
    
    public void reset(SmtpFrame d){
        d.getHostname().setText(null);
        d.getPasword().setText(null);
        d.getUsername().setText(null);
        d.getPort().setText(null);
    }
    
    public void Activate(SmtpFrame d, Confirm c){
        if(d.getModel() == null){
            c.Warning("Anda Belum Memilih Data");
        }else{
             List<SmtpModel> listmodel = dao.getalllistsmtpmodel();
             listmodel.stream().map((ku) -> {
                 SmtpModel mm = dao.getSmtpbyId(ku.getIdsmtp());
                 if(Objects.equals(ku.getIdsmtp(), d.getModel().getIdsmtp())){
                     mm.setActiverecord(Boolean.TRUE);                     
                 }else{
                     mm.setActiverecord(Boolean.FALSE);
                 }
                return mm;
            }).forEachOrdered((mm) -> {
                dao.Update(mm);
            });
        }
       
    }
     
    public void simpan(SmtpFrame d, Confirm c){
        if(d.getHostname().getText().isEmpty()){
            d.getHostname().requestFocus();
            c.Warning("Hostname masih kosong");
        }else if(d.getUsername().getText().isEmpty()){
            d.getUsername().requestFocus();
            c.Warning("Username masih kosong");
        }else if(!mail.isValidEmailAddress(d.getUsername().getText())){
            d.getUsername().requestFocusInWindow();
            d.getUsername().selectAll();
            c.Warning("alamat email anda tidak valid");
        }else if(new String(d.getPasword().getPassword()).isEmpty()){
            d.getPasword().requestFocus();
            c.Warning("Password masih kosong");
        }else if(d.getPort().getText().isEmpty()){
            d.getPort().requestFocus();
            c.Warning("Port masih kosong");
        }else{
            SmtpModel m = new SmtpModel();
            m.setHostname(d.getHostname().getText());
            m.setUsername(d.getUsername().getText());
            m.setPassword(new String(d.getPasword().getPassword()));
            m.setPort(d.getPort().getText());
            try{
                if(mail.checkConnection(m.getHostname(), m.getUsername(), m.getPassword(), m.getPort())){
                    dao.Simpan(m);
                    LoadTable(d);
                    c.Berhasil("Koneksi Berhasil, Data Berhasil Disimpan");
                }else{
                    c.Warning("Koneksi Gagal Periksa Data Anda, Data Gagal disimpan");
                }
            }catch(HibernateException he){
                    c.Gagal(he);
            }
            
            
        }
    }
    
    public void update(SmtpFrame d, Confirm c){
      if(d.getModel().getHostname().isEmpty()){
            c.Warning("Anda Belum memilih data");
        }else{
            SmtpModel m = dao.getSmtpbyId(d.getModel().getIdsmtp());
            m.setHostname(d.getHostname().getText());
            m.setUsername(d.getUsername().getText());
            m.setPassword(new String(d.getPasword().getPassword()));
            m.setPort(d.getPort().getText());
            try{
                if(mail.checkConnection(m.getHostname(), m.getUsername(), m.getPassword(), m.getPort())){
                    dao.Update(m);
                    LoadTable(d);
                    c.Berhasil("Koneksi Berhasil, Data berhasil diubah");
                }else{
                    c.Warning("Koneksi Gagal Periksa Data Anda, Data Gagal diubah");
                }
            }catch(HibernateException he){
                    c.Gagal(he);
            }
            
        }
    }
    
     public void hapus(SmtpFrame d, Confirm c){
      if(d.getModel().getHostname().isEmpty()){
            c.Warning("Anda Belum memilih data");
        }else{
            SmtpModel m = dao.getSmtpbyId(d.getModel().getIdsmtp());
            dao.Delete(m);
            LoadTable(d);
            c.Berhasil("Data Berhasil Dihapus");
        }
    }
    
    
}
