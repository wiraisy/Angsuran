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
import angsuran.listener.Confirm;
import angsuran.model.BaModel;
import angsuran.model.Notifikasi;
import angsuran.model.SmtpModel;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
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
        } catch (JRException ex) {
            ex.getMessage();
        }
        return filename;
    }


    public void sendemail(SmtpModel model, Notifikasi not, Confirm c) throws Exception {
        MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
        socketFactory.setTrustAllHosts(true);
        //Get the session object      
        Properties properties = System.getProperties();
        //Here pass your smtp server url
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.port", model.getPort());
        properties.put("mail.smtp.socketFactory.port", model.getPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.ssl.socketFactory", socketFactory);
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(model.getUsername(), model.getPassword());
            }
        });
        //Compose message     
        Transport transport = session.getTransport();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(model.getUsername()));
            //==================================================================
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(not.getBa().getAlamat_email()));
            //==================================================================
            message.setSubject("Notifikasi keterlambatan Pembayaran Cicilan BPJS");
            //Create MimeBodyPart object and set your message text        
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText
            ("Bersama ini kami sampaikan informasi pembayaran iuran Jaminan Kesehatan Nasional (JKN) dengan rincian yag telah kami lampirkan pada alamat email ini."
                    + "Apabila terdapat perbedaan jumlah tagihan, mohon agar segera menghubungi kantor Cabang BPJS Kesehatan setempat untuk dilakukan rekonsiliasi.");
            //Create new MimeBodyPart object and set DataHandler object to this objeodel.getct        
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(not.getPathpdf());
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(not.getPathpdf());
            //Create Multipart object and add MimeBodyPart objects to this object        
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);
            //=================================================
            transport.connect(model.getHostname(), model.getUsername(), model.getPassword());            
            transport.sendMessage(message, message.getAllRecipients());
            if(transport.isConnected()){
                c.Berhasil("Email Terkirim");
                //==============================================================
                Notifikasi nota = dao.getnotifikasibyid(not.getId_notifikasi());
                nota.setTanggal_kirim(new Date());
                nota.setStatus("TERKIRIM");
                dao.Update(nota);
                //==============================================================
            }else{
                c.Warning("Gagal");
            }
            //=================UPDATE VARIABEL CICILAN TO SENT BOOLEAN==========
        } catch (MessagingException ex) {
            c.Gagal(ex);
        } finally {
            transport.close();
        }
    }
    
    public String CheckInternet(){
        String says = null;
        try {
         URL url = new URL("http://www.google.com");
         URLConnection connection = url.openConnection();
         connection.connect();
         says = "Internet is connected";
      } catch (MalformedURLException e) {
        says = "Internet is not connected";
      } catch (IOException e) {
        says = "Internet is not connected";
      }
        return says;
    }
    
    public boolean checkConnection(String hostname, String username, String password, String port){
        boolean result = false;
        try {
            MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
            socketFactory.setTrustAllHosts(true);
            Properties properties = System.getProperties();
            //properties.put("mail.smtp.host", hostname);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.debug", "true");
            properties.put("mail.transport.protocol", "smtps");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.socketFactory.port", port);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.ssl.socketFactory", socketFactory);
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Transport transport = session.getTransport("smtps");
            transport.connect(hostname, username, password);
            result = transport.isConnected();
            transport.close();
        } catch (MessagingException | GeneralSecurityException ex) {
            Logger.getLogger(SendMailWithAttachment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
    public void connect() throws MailcapParseException {
        if (!isConnected()) {
            try {
                transport = getSession().getTransport(getProtocol());
                setHandler(null);
                connectPopBeforeSmtp();
                transport.connect(getHost(), getPort(), getUser(), getPassword());
                if (getHandler() != null) {
                    getHandler().prepareConnection(transport);
                }
            } catch (NoSuchProviderException e) {
                transport = null;
                throw new MailException("Could not find a provider of " + getProtocol() + " protocol", e);
            } catch (MessagingException me) {
                transport = null;
                throw new MailException("Could not connect to the transport", me);
            }
        }
}
     * @param email
     * @return 
*/
    
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
