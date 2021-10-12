/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.dao;

/**
 *
 * @author User
 */
import angsuran.listener.Confirm;
import angsuran.model.Ba;
import angsuran.model.Cicilan;
import angsuran.model.Notifikasi;
import angsuran.model.Pembayaran;
import angsuran.model.Userku;
import angsuran.util.Helper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AngsuranDaoImplements implements AngsuranDao {

    @Override
    public void Simpan(Object ob) {
        Session session = Helper.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            session.save(ob);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void Update(Object ob) {
        Session session = Helper.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            session.update(ob);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void Delete(Object ob) {
        Session session = Helper.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            session.delete(ob);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //=============================== GET BY ID ===================================
    @Override
    public Ba getbabyid(Long id_ba) {
        Session session = Helper.getSessionFactory().openSession();
        Ba ba = new Ba();
        try {
            Transaction trans = session.beginTransaction();
            ba = (Ba) session.get(Ba.class, id_ba);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ba;
    }

    @Override
    public Cicilan getcicilanbyid(Long id) {
        Session session = Helper.getSessionFactory().openSession();
        Cicilan cicilan = new Cicilan();
        try {
            Transaction trans = session.beginTransaction();
            cicilan = (Cicilan) session.get(Cicilan.class, id);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cicilan;
    }

    @Override
    public Cicilan getcicilanbykodecicilan(String kodecicilan) {
        Session session = Helper.getSessionFactory().openSession();
        Cicilan cicilan = new Cicilan();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class);
            c.add(Restrictions.eq("kode_cicilan", kodecicilan));
            cicilan = (Cicilan) c.uniqueResult();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cicilan;
    }

    //========================PEMBAYARAN=============================================
    @Override
    public Pembayaran getPembayaranbyid(Long id_pembayaran) {
        Session session = Helper.getSessionFactory().openSession();
        Pembayaran pembayaran = new Pembayaran();
        try {
            Transaction trans = session.beginTransaction();
            pembayaran = (Pembayaran) session.get(Pembayaran.class, id_pembayaran);
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return pembayaran;
    }

    //============================== BA ========================================
    @Override
    public List<Ba> getallbabyNamanoentitasandnoba(String nama_bu, String no_entitas, String no_ba) {
        Session session = Helper.getSessionFactory().openSession();
        List<Ba> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Ba.class);
            if (nama_bu != null) {
                c.add(Restrictions.ilike("nama_bu", nama_bu, MatchMode.ANYWHERE));
            }
            if (no_entitas != null) {
                c.add(Restrictions.ilike("no_entitas", no_entitas, MatchMode.ANYWHERE));
            }
            if (no_ba != null) {
                c.add(Restrictions.ilike("no_ba", no_ba, MatchMode.ANYWHERE));
            }
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Ba> getallbabystatus(String status) {
        Session session = Helper.getSessionFactory().openSession();
        List<Ba> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            list = session.createCriteria(Ba.class).
                    add(Restrictions.eq("status_tunggakan", status)).list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public Ba getBabynoentitas(String noentitas) {
        Session session = Helper.getSessionFactory().openSession();
        Ba ba = new Ba();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Ba.class);
            c.add(Restrictions.eq("no_entitas", noentitas));
            ba = (Ba) c.uniqueResult();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ba;
    }

    //========================= CICILAN ========================================
    @Override
    public List<Cicilan> getallcicilanbyba(Ba ba) {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            list = session.createCriteria(Cicilan.class).
                    add(Restrictions.eq("ba", ba)).list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Cicilan> getallcicilanbybaandstatus(Ba ba, String status, Date tanggal) {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class);
            c.add(Restrictions.eq("ba", ba));
            c.add(Restrictions.eq("status", status));
            c.add(Restrictions.le("tanggal_notifikasi_terakhir", tanggal));
            
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Cicilan> getallCicilanbystatusandtempo(String status, Integer tempo) {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class, "cicil");
            c.createAlias("cicil.ba", "ba");
            c.add(Restrictions.eq("status", status));
            c.add(Restrictions.eq("ba.h_notifikasi", tempo));
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Cicilan> getallCicilanbystatusandjatuhtempo(String status) {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        //=======================================================================
        Calendar tanggal = Calendar.getInstance();
        tanggal.set(Calendar.DAY_OF_MONTH, 1);
        tanggal.set(Calendar.DATE, tanggal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date tanggalakhir = tanggal.getTime();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class);
            c.add(Restrictions.eq("status", status));
            c.add(Restrictions.le("tanggal_cicilan", tanggalakhir));
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Cicilan> getidmaxidbycicilanid(Long id_ba) {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class);
            c.add(Restrictions.eq("cicilan_ba_id", id_ba));
            c.addOrder(Order.desc("id_cicilan"));
            list = c.setMaxResults(1).list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Cicilan> getallcicilanjatuhtempo() {
        Session session = Helper.getSessionFactory().openSession();
        List<Cicilan> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Cicilan.class);
            c.add(Restrictions.eq("tanggal_notifikasi_terakhir", new Date()));
            c.add(Restrictions.eq("status", "BELUM LUNAS"));
            c.add(Restrictions.eq("sentnotification", Boolean.FALSE));
            c.addOrder(Order.asc("cicilan_ba_id"));
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    //============================= USER =======================================
    @Override
    public Userku getLoginByUsername(String username) {
        Session session = Helper.getSessionFactory().openSession();
        Userku u = new Userku();
        try {
            Transaction t = session.beginTransaction();
            Criteria c = session.createCriteria(Userku.class)
                    .add(Restrictions.like("username", username, MatchMode.EXACT));
            u = (Userku) c.uniqueResult();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return u;
    }

    @Override
    public Userku getuserbyId(Long id) {
        Session session = Helper.getSessionFactory().openSession();
        Userku u = new Userku();
        try {
            Transaction t = session.beginTransaction();
            u = (Userku) session.get(Userku.class, id);
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return u;
    }

    @Override
    public List<Userku> getalluser() {
        Session session = Helper.getSessionFactory().openSession();
        List<Userku> list = new ArrayList<>();
        try {
            Transaction t = session.beginTransaction();
            list = session.createCriteria(Userku.class).list();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    //======================== PEMBAYARAN =====================================
    @Override
    public List<Pembayaran> getallpembayaranbykodecicilan(String kodecicilan) {
        Session session = Helper.getSessionFactory().openSession();
        List<Pembayaran> list = new ArrayList<>();
        try {
            Transaction trans = session.beginTransaction();
            Criteria c = session.createCriteria(Pembayaran.class, "bayar");
            c.createAlias("bayar.cicilan", "baci");
            c.add(Restrictions.eq("baci.kode_cicilan", kodecicilan));
            list = c.list();
            trans.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    //=========================== NOTIFIKASI ===================================
    @Override
    public List<Notifikasi> getnotifikasibyba(Ba ba) {
        Session session = Helper.getSessionFactory().openSession();
        List<Notifikasi> list = new ArrayList<>();
        try {
            Transaction t = session.beginTransaction();
            Criteria c = session.createCriteria(Notifikasi.class, "not");
            c.createAlias("not.ba", "noba");
            c.add(Restrictions.eq("noba.id_ba", ba.getId_ba()));
            list = c.list();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Notifikasi> getallNotifikasibydate(Date tanggalnotifikasi) {
        Session session = Helper.getSessionFactory().openSession();
        List<Notifikasi> list = new ArrayList<>();
        try {
            Transaction t = session.beginTransaction();
            Criteria c = session.createCriteria(Notifikasi.class);
            c.add(Restrictions.eq("tanggal_notifikasi", tanggalnotifikasi));
            list = c.list();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public List<Notifikasi> getnotifikasimaxid() {
        Session session = Helper.getSessionFactory().openSession();
        List<Notifikasi> list = new ArrayList<>();
        try {
            Transaction t = session.beginTransaction();
            Criteria c = session.createCriteria(Notifikasi.class);
            c.addOrder(Order.desc("id_notifikasi"));
            list = c.setMaxResults(1).list();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;

    }

    @Override
    public Notifikasi getnotifikasibyid(Long id) {
        Session session = Helper.getSessionFactory().openSession();
        Notifikasi nf = new Notifikasi();
        try {
            Transaction t = session.beginTransaction();
            nf = (Notifikasi) session.get(Notifikasi.class, id);
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nf;

    }

    @Override
    public List<Notifikasi> getnotifikasibynamabu(String namabu, Confirm con) {
        Session session = Helper.getSessionFactory().openSession();
        List<Notifikasi> list = new ArrayList<>();
        try {
            Transaction t = session.beginTransaction();
            Criteria c = session.createCriteria(Notifikasi.class, "not");
            if (namabu == null) {
                c.addOrder(Order.desc("not.tanggal_kirim"));
            }
            if (namabu != null) {
                c.createAlias("not.ba", "noba");
                c.add(Restrictions.ilike("noba.nama_bu", namabu, MatchMode.ANYWHERE));
                c.addOrder(Order.desc("tanggal_kirim"));
                if (c.list().isEmpty()) {
                    con.Warning("Data Kosong..!!");
                } else {
                    con.Berhasil("Ditemukan " + String.valueOf(c.list().size()) + " Data");
                }
            }
            list = c.list();
            t.commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            he.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

}
