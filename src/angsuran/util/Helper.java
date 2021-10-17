/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.util;

/**
 *
 * @author User
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.swing.JOptionPane;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author akbarwiraisy
 */
public class Helper {

    private static final SessionFactory sessionFactory;

    static {
        try {
            URL url = HibernateUtil.class.getResource("/hibernate.cfg.xml");
            Configuration configuration = new Configuration().configure(url);

            Properties props;
            try (InputStream is = HibernateUtil.class.getResourceAsStream("/database.properties")) {
                props = new Properties();
                props.load(is);
            }

            configuration.addProperties(props);

      // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = configuration.buildSessionFactory();

        } catch (IOException | HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            JOptionPane.showMessageDialog(null, "Koneksi ke Database gagal");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
