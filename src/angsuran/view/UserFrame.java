/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angsuran.view;

import angsuran.controller.LoginController;
import angsuran.helper.HelperUmum;
import angsuran.helper.TombolCrud;
import angsuran.helper.TombolGeneral;
import angsuran.listener.Confirm;
import angsuran.model.Userku;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Userku
 */
public class UserFrame extends javax.swing.JFrame implements Confirm {

    private final LoginController lc;

    private Userku u = new Userku();

    public Userku getU() {
        return u;
    }

    public void setU(Userku u) {
        this.u = u;
    }

    //========================================================================
    public JCheckBox getManagementba() {
        return managementba;
    }

    public JCheckBox getManagementcicilan() {
        return managementcicilan;
    }

    public JCheckBox getOlahpembayaran() {
        return olahpembayaran;
    }

    public JCheckBox getMuser() {
        return muser;
    }

    public JCheckBox getMsmtp() {
        return msmtp;
    }

    public JCheckBox getLaporan() {
        return laporan;
    }
    
    
    
    
    
    //=======================================================================

    public JPasswordField getPassword() {
        return password;
    }

    public JComboBox<String> getPrevilage() {
        return previlage;
    }

    public JTable getTableuser() {
        return tableuser;
    }

    public JTextField getUsername() {
        return username;
    }

    public UserFrame() {
        initComponents();
        lc = new LoginController();
        new TombolCrud(baru);
        new TombolCrud(simpan);
        new TombolCrud(update);
        new TombolCrud(delete);
        new TombolCrud(reset);
        new TombolGeneral(keluar);
        HelperUmum.setUpResolution(this);
        HelperUmum.setlogoframe(this);
        lc.LoadList(this);
        lc.LoadCbPrev(this, this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deskPanelDasar1 = new angsuran.helper.DeskPanelDasar();
        logoBpjs1 = new angsuran.helper.LogoBpjs();
        transparant1 = new angsuran.helper.Transparant();
        jLabel2 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        managementba = new javax.swing.JCheckBox();
        managementcicilan = new javax.swing.JCheckBox();
        olahpembayaran = new javax.swing.JCheckBox();
        previlage = new javax.swing.JComboBox<>();
        password = new javax.swing.JPasswordField();
        muser = new javax.swing.JCheckBox();
        msmtp = new javax.swing.JCheckBox();
        laporan = new javax.swing.JCheckBox();
        baru = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        transparant2 = new angsuran.helper.Transparant();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableuser = new javax.swing.JTable();
        keluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout logoBpjs1Layout = new javax.swing.GroupLayout(logoBpjs1);
        logoBpjs1.setLayout(logoBpjs1Layout);
        logoBpjs1Layout.setHorizontalGroup(
            logoBpjs1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        logoBpjs1Layout.setVerticalGroup(
            logoBpjs1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );

        transparant1.setBackground(new java.awt.Color(42, 21, 112));
        transparant1.setForeground(new java.awt.Color(42, 21, 112));

        jLabel2.setBackground(new java.awt.Color(0, 0, 51));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 51));
        jLabel2.setText("Username");

        username.setBackground(new java.awt.Color(255, 255, 255));
        username.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        username.setForeground(new java.awt.Color(0, 0, 51));
        username.setBorder(null);
        username.setCaretColor(new java.awt.Color(0, 0, 51));

        jLabel3.setBackground(new java.awt.Color(0, 0, 51));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 51));
        jLabel3.setText("Password");

        jLabel4.setBackground(new java.awt.Color(0, 0, 51));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 51));
        jLabel4.setText("Previlage");

        managementba.setText("Management BA");
        managementba.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                managementbaItemStateChanged(evt);
            }
        });

        managementcicilan.setText("Management Cicilan");
        managementcicilan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                managementcicilanItemStateChanged(evt);
            }
        });

        olahpembayaran.setText("Olah Pembayaran");
        olahpembayaran.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                olahpembayaranItemStateChanged(evt);
            }
        });

        previlage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                previlageItemStateChanged(evt);
            }
        });

        password.setBackground(new java.awt.Color(255, 255, 255));
        password.setForeground(new java.awt.Color(0, 0, 51));
        password.setBorder(null);
        password.setCaretColor(new java.awt.Color(0, 0, 51));

        muser.setText("User");
        muser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                muserItemStateChanged(evt);
            }
        });

        msmtp.setText("Management Smtp");
        msmtp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                msmtpItemStateChanged(evt);
            }
        });

        laporan.setText("Laporan");
        laporan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                laporanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout transparant1Layout = new javax.swing.GroupLayout(transparant1);
        transparant1.setLayout(transparant1Layout);
        transparant1Layout.setHorizontalGroup(
            transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transparant1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transparant1Layout.createSequentialGroup()
                        .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(transparant1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(transparant1Layout.createSequentialGroup()
                                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(previlage, 0, 234, Short.MAX_VALUE)
                                    .addComponent(password))))
                        .addGap(205, 205, 205))
                    .addGroup(transparant1Layout.createSequentialGroup()
                        .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(managementba, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(managementcicilan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(olahpembayaran))
                        .addGap(18, 18, 18)
                        .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(muser)
                            .addComponent(msmtp, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(laporan))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        transparant1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {laporan, managementba, managementcicilan, msmtp, muser, olahpembayaran});

        transparant1Layout.setVerticalGroup(
            transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transparant1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(previlage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(managementba)
                    .addComponent(muser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(managementcicilan)
                    .addComponent(msmtp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(transparant1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(olahpembayaran)
                    .addComponent(laporan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transparant1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {laporan, managementba, managementcicilan, msmtp, muser, olahpembayaran});

        baru.setText("New");
        baru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baruActionPerformed(evt);
            }
        });

        simpan.setText("Save");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        transparant2.setBackground(new java.awt.Color(42, 21, 112));
        transparant2.setForeground(new java.awt.Color(42, 21, 112));

        tableuser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableuser.setColumnSelectionAllowed(true);
        tableuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableuserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableuser);
        tableuser.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout transparant2Layout = new javax.swing.GroupLayout(transparant2);
        transparant2.setLayout(transparant2Layout);
        transparant2Layout.setHorizontalGroup(
            transparant2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transparant2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
                .addContainerGap())
        );
        transparant2Layout.setVerticalGroup(
            transparant2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transparant2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );

        keluar.setText("KELUAR");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout deskPanelDasar1Layout = new javax.swing.GroupLayout(deskPanelDasar1);
        deskPanelDasar1.setLayout(deskPanelDasar1Layout);
        deskPanelDasar1Layout.setHorizontalGroup(
            deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logoBpjs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                        .addComponent(baru, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(transparant1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                        .addGap(0, 797, Short.MAX_VALUE)
                        .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(transparant2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        deskPanelDasar1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {delete, reset, simpan, update});

        deskPanelDasar1Layout.setVerticalGroup(
            deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                .addGroup(deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logoBpjs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(deskPanelDasar1Layout.createSequentialGroup()
                        .addComponent(transparant1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(deskPanelDasar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(baru, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(transparant2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        deskPanelDasar1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {baru, delete, reset, simpan, update});

        getContentPane().add(deskPanelDasar1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void managementbaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_managementbaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setManagementba(Boolean.TRUE);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            u.setManagementba(Boolean.FALSE);
        }
    }//GEN-LAST:event_managementbaItemStateChanged

    private void managementcicilanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_managementcicilanItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setManagementcicilan(Boolean.TRUE);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
           u.setManagementcicilan(Boolean.FALSE);
        }
    }//GEN-LAST:event_managementcicilanItemStateChanged

    private void olahpembayaranItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_olahpembayaranItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setOlahpembayaran(Boolean.TRUE);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
           u.setOlahpembayaran(Boolean.FALSE);
        }
    }//GEN-LAST:event_olahpembayaranItemStateChanged

    private void previlageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_previlageItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setPrevillage(previlage.getSelectedItem().toString());
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            u.setPrevillage(previlage.getSelectedItem().toString());
        }
    }//GEN-LAST:event_previlageItemStateChanged

    private void baruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baruActionPerformed
        lc.Reset(this);
        lc.Baru(this, true);
        lc.LoadList(this);
    }//GEN-LAST:event_baruActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        lc.simpan(this, this);
    }//GEN-LAST:event_simpanActionPerformed

    private void tableuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableuserMouseClicked
        lc.klik(this, evt);
        lc.Baru(this, true);
    }//GEN-LAST:event_tableuserMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        lc.update(this, this);
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       lc.delete(this, this);
    }//GEN-LAST:event_deleteActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
       lc.Reset(this);
    }//GEN-LAST:event_resetActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       lc.Baru(this, false);
    }//GEN-LAST:event_formWindowOpened

    private void muserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_muserItemStateChanged
       if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setManagementuser(Boolean.TRUE);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            u.setManagementuser(Boolean.FALSE);
        }
    }//GEN-LAST:event_muserItemStateChanged

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        dispose();
    }//GEN-LAST:event_keluarActionPerformed

    private void msmtpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_msmtpItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            u.setSmtp(Boolean.TRUE);
        } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            u.setSmtp(Boolean.FALSE);
        }
    }//GEN-LAST:event_msmtpItemStateChanged

    private void laporanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_laporanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_laporanItemStateChanged

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton baru;
    private javax.swing.JButton delete;
    private angsuran.helper.DeskPanelDasar deskPanelDasar1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keluar;
    private javax.swing.JCheckBox laporan;
    private angsuran.helper.LogoBpjs logoBpjs1;
    private javax.swing.JCheckBox managementba;
    private javax.swing.JCheckBox managementcicilan;
    private javax.swing.JCheckBox msmtp;
    private javax.swing.JCheckBox muser;
    private javax.swing.JCheckBox olahpembayaran;
    private javax.swing.JPasswordField password;
    private javax.swing.JComboBox<String> previlage;
    private javax.swing.JButton reset;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tableuser;
    private angsuran.helper.Transparant transparant1;
    private angsuran.helper.Transparant transparant2;
    private javax.swing.JButton update;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    @Override
    public void Berhasil(String pesan) {
        JOptionPane.showMessageDialog(this, pesan);
    }

    @Override
    public void Warning(String pesan) {
        JOptionPane.showMessageDialog(this, pesan);
    }

    @Override
    public void Gagal(Throwable t) {
        JOptionPane.showMessageDialog(this, t.getMessage());
    }
}
