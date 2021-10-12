/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.whatsapp;

import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.CompareBaModel;
import angsuran.helper.Generatekode;
import angsuran.model.BaModel;
import angsuran.model.Cicilan;
import angsuran.model.Notifikasi;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;


/**
 *
 * @author User
 */
public class ReportGenerator{

    private static final SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

    @SuppressWarnings("unchecked")
    public void CreatePDF() throws FileNotFoundException {
        //SendMailWithAttachment send = new SendMailWithAttachment();
        Set<BaModel> setba = new TreeSet(new CompareBaModel());
        AngsuranDao dao = new AngsuranDaoImplements();
        List<Cicilan> list = dao.getallcicilanjatuhtempo();
        if (!list.isEmpty()) {
            for (Cicilan cil : list) {
                BaModel mod = new BaModel();
                mod.setId_ba(cil.getBa().getId_ba());
                mod.setNama_bu(cil.getBa().getNama_bu());
                mod.setNo_entitas(cil.getBa().getNo_entitas());
                mod.setAlamat_email(cil.getBa().getAlamat_email());
                mod.setNo_ba(cil.getBa().getNo_ba());
                mod.setTanggal_ba(cil.getBa().getTanggal_ba());
                mod.setTotal_tunggakan(cil.getBa().getTotal_tunggakan());
                mod.setTahap_cicilan(cil.getBa().getTahap_cicilan());
                mod.setStatus_tunggakan(cil.getBa().getStatus_tunggakan());
                mod.setH_notifikasi(cil.getBa().getH_notifikasi());
                setba.add(mod);
            }

            for (BaModel ba : setba) {
                double tunggakan = 0d;
                double kekurangan = 0d;
                for (Cicilan cil : list) {
                    if (cil.getBa().getId_ba().equals(ba.getId_ba())) {
                        tunggakan += cil.getTotal();
                        kekurangan += cil.getTotal_kekurangan();
                        ba.getListcicilan().add(cil);        
                    }
                    ba.setTunggakan_total(tunggakan);
                    ba.setKekurangan_total(kekurangan);
                }
                //==================================================================
                Notifikasi not = new Notifikasi();
                not.setBa(dao.getbabyid(ba.getId_ba()));
                not.setKode_surat(new Generatekode().Generatekodesurat());
                not.setTanggal_kirim(new Date());
                ba.setKodesurat(not.getKode_surat());
                dao.Simpan(not);
                //==================================================================
                System.out.println(ba.getNama_bu());
            try {
                SendMailWithAttachment ss = new SendMailWithAttachment();
                String filename = ss.createpdf(ba);
                File ff = new File(filename);
                if (ff.exists()) {
                    try {
                        Desktop dt = Desktop.getDesktop();
                        dt.open(ff);
                    } catch (IOException ex) {
                        Logger.getLogger(SendMailWithAttachment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Gagal");
                }

            } catch (JRException ex) {
                ex.getMessage();
            }

            }

        }else{
            JOptionPane.showMessageDialog(null, "Tidak ada Angsuran jatuh tempo hari ini...!!!");
        }
    }

}
