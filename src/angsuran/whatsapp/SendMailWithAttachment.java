/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.whatsapp;

/**
 *
 * @author User
 */
import angsuran.dao.AngsuranDao;
import angsuran.dao.AngsuranDaoImplements;
import angsuran.helper.HelperUmum;
import angsuran.model.BaModel;
import angsuran.model.Cicilan;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class SendMailWithAttachment {

    private static SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
    private AngsuranDao dao = new AngsuranDaoImplements();

    private String filename;

    public void createreport(BaModel ba) throws JRException {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("tanggal", f.format(new Date()));
        parameter.put("kodesurat", ba.getKodesurat());
        parameter.put("namabu", ba.getNama_bu());
        parameter.put("image", this.getClass().getResourceAsStream("/angsuran/report/background_jasper.png"));
        parameter.put("total_tagihan", ba.getTunggakan_total());
        parameter.put("total_kekurangan", ba.getKekurangan_total());
        parameter.put("terbilang", HelperUmum.angkaToTerbilang(ba.getKekurangan_total().longValue()) + " Rupiah");
        JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/angsuran/report/Tagihan.jasper"),
                parameter, new JRBeanCollectionDataSource(ba.getListcicilan()));
        JasperViewer viewer = new JasperViewer(print, false);
        JDialog dd = new JDialog();
        dd.setContentPane(viewer.getContentPane());
        dd.setSize(viewer.getSize());
        dd.setTitle("Print Nota");
        dd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dd.setModal(true);
        dd.pack();
        dd.setVisible(true);
    }

    public String createpdf(BaModel ba) throws JRException, FileNotFoundException {
        File file = new File("E:\\Notifikasi-BPJS");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Berhasil");
                //c.Berhasil("Directory is created!");
            } else {
                System.out.println("Gagal");
                // c.Warning("Failed to create directory!");
            }
        }
        try {
            filename = "E:\\Notifikasi-BPJS\\" + ba.getKodesurat() + ".pdf";
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("tanggal", f.format(new Date()));
            parameter.put("kodesurat", ba.getKodesurat());
            parameter.put("namabu", ba.getNama_bu());
            parameter.put("image", this.getClass().getResourceAsStream("/angsuran/report/background_jasper.png"));
            parameter.put("total_tagihan", ba.getTunggakan_total());
            parameter.put("total_kekurangan", ba.getKekurangan_total());
            parameter.put("terbilang", HelperUmum.angkaToTerbilang(ba.getKekurangan_total().longValue()) + " Rupiah");
            JasperPrint print = JasperFillManager.fillReport(this.getClass().getResourceAsStream("/angsuran/report/Tagihan.jasper"),
                    parameter, new JRBeanCollectionDataSource(ba.getListcicilan()));
            //=========================================================================================================================================
            JasperExportManager.exportReportToPdfFile(print, filename);
            try {
                //=========================================================================================================================================
                sendemail(ba, filename);
                //===========================================================================================================================================
            } catch (Exception ex) {
                Logger.getLogger(SendMailWithAttachment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JRException ex) {
            ex.getMessage();
        }
        return filename;
    }

    private Boolean yes;

    public Boolean sendemail(BaModel mod, String Filename) throws Exception {
        yes = false;
        String to = mod.getAlamat_email();//Email address of the recipient
        //final String user = "anarina5586@gmail.com"; //Email address of sender
        //final String password = "Wiraisy5586";  //Password of the sender's email
        final String user = "bpjskesehatankabanjahe@gmail.com"; //Email address of sender
        final String password = "123_kabanjahe";  //Password of the sender's email
        //Get the session object      
        Properties properties = System.getProperties();
        //Here pass your smtp server url
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        //Compose message     
        Transport transport = session.getTransport();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            //==================================================================
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //==================================================================
            message.setSubject("Notifikasi keterlambatan Pembayaran Cicilan BPJS");

            //Create MimeBodyPart object and set your message text        
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText
            ("Bersama ini kami sampaikan informasi pembayaran iuran Jaminan Kesehatan Nasional (JKN) dengan rincian yag telah kami lampirkan pada alamat email ini."
                    + "Apabila terdapat perbedaan jumlah tagihan, mohon agar segera menghubungi kantor Cabang BPJS Kesehatan setempat untuk dilakukan rekonsiliasi.");

            //Create new MimeBodyPart object and set DataHandler object to this object        
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            //Create Multipart object and add MimeBodyPart objects to this object        
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);
            //=================================================
            transport.connect("smtp.gmail.com", user, password);
            transport.sendMessage(message, message.getAllRecipients());
            yes = Boolean.TRUE;
            //transport.send(message);      
            //==================================================================
            for(Cicilan cil:mod.getListcicilan()){
                Cicilan co = dao.getcicilanbyid(cil.getId_cicilan());
                co.setTanggal_notifikasi_terakhir(HelperUmum.getnextdatenotifikasi(cil.getTanggal_notifikasi_terakhir()));
                co.setSentnotification(Boolean.FALSE);
                dao.Update(co);
            }
            System.out.print("message sent");
            //=================UPDATE VARIABEL CICILAN TO SENT BOOLEAN==========
        } catch (MessagingException ex) {
            yes = Boolean.FALSE;
            System.out.print(ex.getMessage());
        } finally {
            transport.close();
        }
        return yes;
    }

}
