/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.controller;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.JTableRender;
import angsuran.listener.BCrypt;
import angsuran.listener.Confirm;
import angsuran.model.Userku;
import angsuran.model.UserModel;
import angsuran.tablemodel.UserTM;
import angsuran.view.Login;
import angsuran.view.UserFrame;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Userku
 */
public class LoginController {

    private UserTM tm = new UserTM();
    private List<Userku> list = new ArrayList<>();
    private AngsuranDao dao = new AngsuranDaoImplements();
    private UserModel model;
    private int banyakLogin = 1;
    private final int maxBanyakLogin = 3;
    private JTableRender jTableRender;

    public void setModel(UserModel model) {
        this.model = model;
    }

    public void ProsesReset(Login d) {
        model.processReset();
    }
    
  

    public void ProsesLogin(Login d, Confirm confirm) {
        String Username = d.getUsername().getText();
        String Password = new String(d.getPassword().getPassword());
        if (Username.isEmpty()) {
            confirm.Warning("Username kosong !");
        } else if (Password.isEmpty()) {
            confirm.Warning("Password kosong !");
        } else {
            model.setUsername(Username);
            model.setPassword(Password);
            try {
                if (model.prosesLogin(confirm)) {
                    d.dispose();
                } else {
                    if (banyakLogin >= maxBanyakLogin) {
                        confirm.Warning("Anda telah salah login selama " + maxBanyakLogin + " kali");
                        System.exit(0);
                    }
                    banyakLogin++;
                }
            } catch (HibernateException he) {
                confirm.Gagal(he);
            }
        }

    }

    public void LoadCbPrev(UserFrame d, Confirm c) {
        d.getPrevilage().addItem("");
        d.getPrevilage().addItem("Admin");
        d.getPrevilage().addItem("Keuangan");
        d.getPrevilage().addItem("Penagihan");
      
    }

    public void Reset(UserFrame d) {
        d.getUsername().setText("");
        d.getPassword().setText("");
        d.getPrevilage().setSelectedIndex(0);
        d.getManagementba().setSelected(false);
        d.getManagementcicilan().setSelected(false);
        d.getOlahpembayaran().setSelected(false);
        d.getMuser().setSelected(false);
        d.getMsmtp().setSelected(false);
        d.getLaporan().setSelected(false);
        d.setU(new Userku());
    }
    
    public void Baru(UserFrame d, Boolean yes) {
        d.getUsername().setEnabled(yes);
        d.getPassword().setEnabled(yes);
        d.getPrevilage().setEnabled(yes);
        d.getManagementba().setEnabled(yes);
        d.getManagementcicilan().setEnabled(yes);
        d.getMsmtp().setEnabled(yes);
        d.getOlahpembayaran().setEnabled(yes);
        d.getMuser().setEnabled(yes);
        d.getLaporan().setEnabled(yes);
        d.getTableuser().clearSelection();
    }
    
    public void LoadList(UserFrame d){
        list = dao.getalluser();
        tm.setList(list);
        tm.fireTableDataChanged();
        d.getTableuser().setModel(tm);
        jTableRender = new JTableRender(d.getTableuser());
        d.getTableuser().revalidate();
        d.getTableuser().repaint();
    }
    
    public void klik(UserFrame d, MouseEvent evt){
        int row = d.getTableuser().rowAtPoint(evt.getPoint());
        if (row != -1) {
            int indeks = d.getTableuser().getSelectedRow();
            Long id = (Long) d.getTableuser().getModel().getValueAt(indeks, 1);
            Userku u = dao.getuserbyId(id);
            d.setU(u);
            d.getUsername().setText(u.getUsername());
            d.getPrevilage().setSelectedItem(u.getPrevillage());
            d.getManagementba().setSelected(u.isManagementba());
            d.getManagementcicilan().setSelected(u.isManagementcicilan());
            d.getOlahpembayaran().setSelected(u.isOlahpembayaran());     
            d.getMuser().setSelected(u.isManagementuser());
            d.getMsmtp().setSelected(u.isSmtp());
            d.getLaporan().setSelected(u.isLaporan());
        }
    }
    

    public void simpan(UserFrame d, Confirm c) {
        Userku u = new Userku();
        if (d.getUsername().getText().isEmpty()) {
            c.Warning("username tidak boleh kosong");
        } else {
            u.setUsername(d.getUsername().getText());
        }
        String encoded = BCrypt.hashpw(new String(d.getPassword().getPassword()), BCrypt.gensalt(12));
        u.setPassword(encoded);
        if (u.getPassword().isEmpty()) {
            c.Warning("password tidak boleh kosong");
        }
        u.setPrevillage(d.getPrevilage().getSelectedItem().toString());
        if (u.getPrevillage().isEmpty()) {
            c.Warning("previlage tidak boleh kosong");
        }
        u.setManagementba(d.getU().isManagementba());
        u.setManagementcicilan(d.getU().isManagementcicilan());
        u.setOlahpembayaran(d.getU().isOlahpembayaran());
        u.setManagementuser(d.getU().isManagementuser());
        u.setSmtp(d.getU().isSmtp());
        u.setLaporan(d.getU().isLaporan());
        if (!u.getUsername().isEmpty() && !u.getPassword().isEmpty() && !u.getPrevillage().isEmpty()) {
            Userku uu = dao.getLoginByUsername(d.getUsername().getText());
            if (uu != null) {
                d.getUsername().requestFocusInWindow();
                d.getUsername().selectAll();
                c.Warning("Username sudah terdaftar silahkan cari username lain");
            } else {
                try {
                    dao.Simpan(u);
                    c.Berhasil("Data berhasil disimpan");
                    Reset(d);
                    Baru(d, Boolean.FALSE);
                    LoadList(d);
                } catch (HibernateException he) {
                    c.Gagal(he);
                }

            }

        }

    }

    public void update(UserFrame d, Confirm c) {
        Userku u = dao.getuserbyId(d.getU().getIduser());
        if (d.getUsername().getText().isEmpty()) {
            c.Warning("username tidak boleh kosong");
        }else{
                u.setUsername(d.getUsername().getText());
        }
        String encoded = BCrypt.hashpw(new String(d.getPassword().getPassword()), BCrypt.gensalt(12));
        u.setPassword(encoded);
        if (u.getPassword().isEmpty()) {
            c.Warning("password tidak boleh kosong");
        }
        u.setPrevillage(d.getPrevilage().getSelectedItem().toString());
        if (u.getPrevillage().isEmpty()) {
            c.Warning("previlage tidak boleh kosong");
        }
        u.setManagementba(d.getU().isManagementba());
        u.setManagementcicilan(d.getU().isManagementcicilan());
        u.setOlahpembayaran(d.getU().isOlahpembayaran());
        u.setManagementuser(d.getU().isManagementuser());
        u.setSmtp(d.getU().isSmtp());
        u.setLaporan(d.getU().isLaporan());
        if(!u.getUsername().isEmpty() && !u.getPassword().isEmpty() && !u.getPrevillage().isEmpty()){
            try{
                dao.Update(u);
                c.Berhasil("Data berhasil diupdate");
                Reset(d);
                Baru(d, Boolean.FALSE);
                LoadList(d);
            }catch(HibernateException he){
                c.Gagal(he);
            }
            
        }
           
    }
    
     public void delete(UserFrame d, Confirm c) {
        Userku u = dao.getuserbyId(d.getU().getIduser());
        if (u != null) {
            try{
                dao.Delete(u);
                c.Berhasil("Data berhasil dihapus");
                Reset(d);
                Baru(d, Boolean.FALSE);
                LoadList(d);
            }catch(HibernateException he){
                c.Gagal(he);
            }
            
        }

    }

}
