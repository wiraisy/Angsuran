/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.view.Login;
import angsuran.view.UserFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Angsuran {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        AngsuranDao dao = new AngsuranDaoImplements();
        if (dao.getalluser().isEmpty()) {
            UserFrame fd = new UserFrame();
            fd.setTitle("Management User");
            fd.setVisible(true);
            JOptionPane.showMessageDialog(fd, "Silahkan Tambah User..!!!");
        } else {
            Login log = new Login();
            log.setSize(957, 474);
            log.setTitle("Menu Login");
            log.setVisible(true);
        }

    }

}
