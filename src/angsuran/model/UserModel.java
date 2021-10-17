/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.model;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.listener.BCrypt;
import angsuran.listener.Confirm;
import angsuran.listener.LoginListener;





/**
 *
 * @author diazwiraisy
 */

public class UserModel {
    
    private LoginListener listener;
    
    private AngsuranDao dao = new AngsuranDaoImplements();
    
    //============

    private String Username;
    private String Password;
    private String previllage;
    private boolean managementba = Boolean.FALSE;
    private boolean managementcicilan = Boolean.FALSE;
    private boolean olahpembayaran = Boolean.FALSE;   
    private boolean managementuser = Boolean.FALSE;   
    private boolean smtp = Boolean.FALSE;

    public UserModel() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
        fireChange();
    }

    public String getPassword() {
        return Password;
    }
    
    public void setPassword(String Password) {
        this.Password = Password;
        fireChange();
    }

    public String getPrevillage() {
        return previllage;
    }

    public void setPrevillage(String previllage) {
        this.previllage = previllage;
        fireChange();
    }

    public boolean isManagementba() {
        return managementba;
    }

    public void setManagementba(boolean managementba) {
        this.managementba = managementba;
        fireChange();
    }

    public boolean isManagementcicilan() {
        return managementcicilan;
    }

    public void setManagementcicilan(boolean managementcicilan) {
        this.managementcicilan = managementcicilan;
        fireChange();
    }

    public boolean isOlahpembayaran() {
        return olahpembayaran;
    }

    public void setOlahpembayaran(boolean olahpembayaran) {
        this.olahpembayaran = olahpembayaran;
        fireChange();
    }    

    public boolean isManagementuser() {
        return managementuser;
    }

    public void setManagementuser(boolean managementuser) {
        this.managementuser = managementuser;
        fireChange();
    }

    public boolean isSmtp() {
        return smtp;
    }

    public void setSmtp(boolean smtp) {
        this.smtp = smtp;
        fireChange();
    }
    
    
    
    

    public LoginListener getListener() {
        return listener;
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    public AngsuranDao getDao() {
        return dao;
    }

    public void setDao(AngsuranDao dao) {
        this.dao = dao;

    }
    
    public void fireChange(){
        if(this.listener != null){
            this.listener.OnChange(this);
        }
    }

    public void processReset(){
        setPassword("");
        setUsername("");
        setManagementba(false);
        setManagementcicilan(false);
        setPrevillage("");
        setOlahpembayaran(false);
        setManagementuser(false);
        setSmtp(false);
    }
    
    
    
    public Boolean prosesLogin(Confirm confirm){
        boolean match = false;
        Userku u = dao.getLoginByUsername(Username);
        if (u != null) {
            match = BCrypt.checkpw(Password, u.getPassword());
            if(match == true){
                setManagementba(u.isManagementba());
                setManagementcicilan(u.isManagementcicilan());
                setOlahpembayaran(u.isOlahpembayaran());
                setManagementuser(u.isManagementuser());
                setSmtp(u.isSmtp());
                confirm.Berhasil("Selamat Datang "+u.getUsername());              
            }else{
                match = false;
                confirm.Warning("Password anda salah");
            }
        }else{
            match = false;
            confirm.Warning("Username tidak ditemukan");
        }
        return match;
    }

}
