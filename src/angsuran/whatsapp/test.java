/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.whatsapp;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author User
 */
public class test {

    
    public static void contohkirim()throws Exception {
        String filename = "C:\\Users\\User\\Desktop\\SalesOrder-12780-BPJS Cabang Medan.pdf";
        String to = "wiraisy@gmail.com";//Email address of the recipient
        final String user = "bpjskesehatankabanjahe@gmail.com"; //Email address of sender
        final String password = "123_kabanjahe";  //Password of the sender's email
        //final String user = "anarina5586@gmail.com"; //Email address of sender
        //final String password = "Wiraisy5586";  //Password of the sender's email
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
                return new PasswordAuthentication(user,password);    }   });       
        //Compose message     
        Transport transport = session.getTransport();
        try{    
            MimeMessage message = new MimeMessage(session);    
            message.setFrom(new InternetAddress(user));    
            //==================================================================
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            //==================================================================
            message.setSubject("Notifikasi keterlambatan Pembayaran Cicilan BPJS");         

            //Create MimeBodyPart object and set your message text        
            BodyPart messageBodyPart1 = new MimeBodyPart();     
            messageBodyPart1.setText("Notifikasi Pembayaran Cicilan BPJS Kesehatan");          

            //Create new MimeBodyPart object and set DataHandler object to this object        
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();       
            DataSource source = new FileDataSource(filename);    
            messageBodyPart2.setDataHandler(new DataHandler(source));    
            messageBodyPart2.setFileName(filename);             

            //Create Multipart object and add MimeBodyPart objects to this object        
            Multipart multipart = new MimeMultipart();    
            multipart.addBodyPart(messageBodyPart1);     
            multipart.addBodyPart(messageBodyPart2);         
            message.setContent(multipart );     
            //=================================================
            transport.connect("smtp.gmail.com", user, password);
            transport.sendMessage(message, message.getAllRecipients());
            //transport.send(message);      
            System.out.print("message sent");
            //=================UPDATE VARIABEL CICILAN TO SENT BOOLEAN==========
            

        }catch (MessagingException ex) {
            System.out.print(ex.getMessage());
        }finally{
            transport.close();
        }
    }
    
    
    
}
