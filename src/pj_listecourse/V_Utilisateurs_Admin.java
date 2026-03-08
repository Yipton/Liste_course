/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author Yipton
 */
public class V_Utilisateurs_Admin extends javax.swing.JDialog {

    private C_ListeCourse leControl;

    private LinkedHashMap<Integer, M_Users> lesUtils;
    private LinkedHashMap<Integer, M_Roles> lesRoles;

    private DefaultTableModel dmDefaultTableModel;

    private M_Users unUtilisateur;
    private M_Roles unRole;

    private Object[] lesClesRoles;

    private String nom, prenom, mail, role, mdp, commentaire;
    private int idUtilisateur, idRole;
    private char modeGlobal;
    private LocalDateTime email_verified_at;

    public V_Utilisateurs_Admin(java.awt.Frame parent, boolean modal, C_ListeCourse leControleur) {
        super(parent, modal);
        leControl = leControleur;
        initComponents();
    }

    public void afficher(LinkedHashMap<Integer, M_Users> lesUtils, LinkedHashMap<Integer, M_Roles> lesRoles) {
        this.lesUtils = lesUtils;
        this.lesRoles = lesRoles;

        int compteur = 0;

        this.setSize(650, 550);
        this.setLocationRelativeTo(null);

        dmDefaultTableModel = (DefaultTableModel) tb_utils.getModel();
        dmDefaultTableModel.setRowCount(lesUtils.size());

        for (int uneCle : lesUtils.keySet()) {
            M_Users unUser = lesUtils.get(uneCle);
            tb_utils.setValueAt(unUser.getIdUtilisateur(), compteur, 0);
            tb_utils.setValueAt(unUser.getNom(), compteur, 1);
            tb_utils.setValueAt(unUser.getPrenom(), compteur, 2);
            compteur++;
        }

        cb_role.removeAllItems();

        for (int uneCle : lesRoles.keySet()) {
            unRole = lesRoles.get(uneCle);
            cb_role.addItem(unRole.getNom());
        }
        cb_role.setSelectedIndex(-1);
        lesClesRoles = lesRoles.keySet().toArray();

        this.setVisible(true);
    }

    public M_Users recuperer_unUtilisateur() {
        int row = tb_utils.getSelectedRow();
        if (row == -1) {
            return null;
        }

        Integer id = (Integer) dmDefaultTableModel.getValueAt(row, 0);
        return lesUtils.get(id);
    }

    public void details() {
        unUtilisateur = recuperer_unUtilisateur();
        if (unUtilisateur != null) {
            tf_id.setText(String.valueOf(unUtilisateur.getIdUtilisateur()));
            tf_nom.setText(unUtilisateur.getNom());
            tf_prenom.setText(unUtilisateur.getPrenom());
            tf_mail.setText(unUtilisateur.getEmail());
            tf_mdp.setText(unUtilisateur.getPassword());
            ta_commentaire.setText(unUtilisateur.getCommentaire());

            unRole = lesRoles.get(unUtilisateur.getId_role());
            String leRole = unRole.getNom();
            cb_role.setSelectedItem(leRole);
        }
    }

    public void remise_a_zero() {

        tf_id.setText("");
        tf_nom.setText("");
        tf_prenom.setText("");
        tf_mail.setText("");
        tf_mdp.setText("");
        ta_commentaire.setText("");
        cb_role.setSelectedIndex(-1);

        tf_id.setVisible(true);
        lb_id.setVisible(true);
        bt_ajouter.setEnabled(true);
        bt_supprimer.setEnabled(true);
        bt_modifier.setEnabled(true);
        bt_enregistrer.setEnabled(false);
        bt_generer.setEnabled(false);
        bt_annuler.setEnabled(false);
        tf_mail.setEnabled(false);
        tf_mdp.setEnabled(false);
        tf_nom.setEnabled(false);
        tf_prenom.setEnabled(false);
        cb_role.setEnabled(false);
        ta_commentaire.setEnabled(false);
        tb_utils.setEnabled(true);
        tb_utils.setRowSelectionAllowed(true);
        tb_utils.setColumnSelectionAllowed(false);

    }

    public void ihm(char mode, LinkedHashMap<Integer, M_Users> lesUtils) throws SQLException {
        modeGlobal = mode;

        switch (mode) {
            case 'C':
                bt_enregistrer.setVisible(false);
                bt_annuler.setVisible(false);
                break;

            case 'A':
                tf_id.setVisible(false);
                lb_id.setVisible(false);
                tf_nom.setText("");
                tf_prenom.setText("");
                tf_mail.setText("");
                tf_mdp.setText("");
                ta_commentaire.setText("");
                cb_role.setSelectedIndex(-1);

                bt_ajouter.setEnabled(false);
                bt_supprimer.setEnabled(false);
                tb_utils.setEnabled(false);
                bt_enregistrer.setEnabled(true);
                bt_generer.setEnabled(true);
                bt_annuler.setEnabled(true);
                bt_modifier.setEnabled(false);
                tf_mail.setEnabled(true);
                tf_mdp.setEnabled(true);
                tf_nom.setEnabled(true);
                tf_prenom.setEnabled(true);
                cb_role.setEnabled(true);
                ta_commentaire.setEnabled(true);
                // désactiver la sélection du tableau
                tb_utils.clearSelection();
                tb_utils.setRowSelectionAllowed(false);
                tb_utils.setColumnSelectionAllowed(false);
                tb_utils.setEnabled(false);
                break;

            case 'U':
                bt_ajouter.setEnabled(false);
                bt_supprimer.setEnabled(false);
                tb_utils.setEnabled(false);
                bt_enregistrer.setEnabled(true);
                bt_generer.setEnabled(true);
                bt_annuler.setEnabled(true);
                tf_mail.setEnabled(true);
                tf_mdp.setEnabled(true);
                tf_nom.setEnabled(true);
                tf_prenom.setEnabled(true);
                cb_role.setEnabled(true);
                ta_commentaire.setEnabled(true);
                tb_utils.setEnabled(false);
                break;

            case 'D':
                M_Users lUtil = recuperer_unUtilisateur();
                leControl.supprimer_util(lUtil);
                remise_a_zero();
                break;

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        op_message = new javax.swing.JOptionPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_utils = new javax.swing.JTable();
        bt_modifier = new javax.swing.JButton();
        bt_supprimer = new javax.swing.JButton();
        bt_ajouter = new javax.swing.JButton();
        tf_id = new javax.swing.JTextField();
        tf_nom = new javax.swing.JTextField();
        tf_prenom = new javax.swing.JTextField();
        bt_generer = new javax.swing.JButton();
        lb_mail = new javax.swing.JLabel();
        tf_mail = new javax.swing.JTextField();
        bt_enregistrer = new javax.swing.JButton();
        bt_annuler = new javax.swing.JButton();
        cb_role = new javax.swing.JComboBox<>();
        lb_role = new javax.swing.JLabel();
        lb_id = new javax.swing.JLabel();
        lb_nom = new javax.swing.JLabel();
        lb_prenom = new javax.swing.JLabel();
        lb_mdp = new javax.swing.JLabel();
        lb_commentaire = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_commentaire = new javax.swing.JTextArea();
        tf_mdp = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Application Liste de courses | Gestions des utilisateurs");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_utils.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nom", "Prénom"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_utils.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tb_utils.getTableHeader().setReorderingAllowed(false);
        tb_utils.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_utilsMouseClicked(evt);
            }
        });
        tb_utils.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tb_utilsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tb_utils);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 27, 358, 226));

        bt_modifier.setText("Modifier");
        bt_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_modifierActionPerformed(evt);
            }
        });
        getContentPane().add(bt_modifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 90, 85, -1));

        bt_supprimer.setText("Supprimer");
        bt_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_supprimerActionPerformed(evt);
            }
        });
        getContentPane().add(bt_supprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 125, -1, -1));

        bt_ajouter.setText("Ajouter");
        bt_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ajouterActionPerformed(evt);
            }
        });
        getContentPane().add(bt_ajouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 160, 85, -1));

        tf_id.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_id.setEnabled(false);
        getContentPane().add(tf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 265, 208, -1));

        tf_nom.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_nom.setEnabled(false);
        getContentPane().add(tf_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 311, 208, -1));

        tf_prenom.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_prenom.setEnabled(false);
        getContentPane().add(tf_prenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 351, 208, -1));

        bt_generer.setText("Générer");
        bt_generer.setEnabled(false);
        getContentPane().add(bt_generer, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 295, -1, -1));

        lb_mail.setText("Mail :");
        getContentPane().add(lb_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 400, 37, -1));

        tf_mail.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tf_mail.setEnabled(false);
        getContentPane().add(tf_mail, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 397, 208, -1));

        bt_enregistrer.setText("Enregistrer");
        bt_enregistrer.setEnabled(false);
        bt_enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_enregistrerActionPerformed(evt);
            }
        });
        getContentPane().add(bt_enregistrer, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 456, -1, -1));

        bt_annuler.setText("Annuler");
        bt_annuler.setEnabled(false);
        bt_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_annulerActionPerformed(evt);
            }
        });
        getContentPane().add(bt_annuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 456, -1, -1));

        cb_role.setEnabled(false);
        getContentPane().add(cb_role, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 324, 154, -1));

        lb_role.setText("Role :");
        getContentPane().add(lb_role, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 327, 77, -1));

        lb_id.setText("Id :");
        getContentPane().add(lb_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 270, 37, -1));

        lb_nom.setText("Nom :");
        getContentPane().add(lb_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 308, 37, -1));

        lb_prenom.setText("Prénom :");
        getContentPane().add(lb_prenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 354, 57, -1));

        lb_mdp.setText("Mot de passe :");
        getContentPane().add(lb_mdp, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 268, -1, -1));

        lb_commentaire.setText("Commentaire :");
        getContentPane().add(lb_commentaire, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 374, 88, -1));

        ta_commentaire.setColumns(20);
        ta_commentaire.setRows(5);
        ta_commentaire.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ta_commentaire.setEnabled(false);
        jScrollPane2.setViewportView(ta_commentaire);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 196, -1));

        tf_mdp.setText("jPasswordField1");
        getContentPane().add(tf_mdp, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 150, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb_utilsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_utilsMouseClicked
        details();
    }//GEN-LAST:event_tb_utilsMouseClicked

    private void tb_utilsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_utilsKeyReleased
        details();
    }//GEN-LAST:event_tb_utilsKeyReleased

    private void bt_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_supprimerActionPerformed
        if (tb_utils.getSelectedRowCount() != 1) {
            op_message.showMessageDialog(this, "Sélectionner 1 utilisateur !", "Sélection obligatoire", op_message.WARNING_MESSAGE);
        } else {

            int result = op_message.showConfirmDialog(op_message, "Voulez vous confirmer la suppression de l'utilisateur " + tb_utils.getValueAt(tb_utils.getSelectedRow(), 0) + " ?");
            if (result == op_message.YES_OPTION) {
                try {
                    this.ihm('D', lesUtils);
                } catch (SQLException ex) {
                    Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            System.out.println(op_message.getInputValue());

        }

    }//GEN-LAST:event_bt_supprimerActionPerformed

    private void bt_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ajouterActionPerformed
        try {
            this.ihm('A', lesUtils);
        } catch (SQLException ex) {
            Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(Level.SEVERE, null, ex);

        }
    }//GEN-LAST:event_bt_ajouterActionPerformed

    private void bt_enregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_enregistrerActionPerformed
        nom = tf_nom.getText();
        prenom = tf_prenom.getText();
        mail = tf_mail.getText();
        commentaire = tf_mdp.getText();
        mdp = BCrypt.withDefaults().hashToString(12, tf_mdp.getText().toCharArray());
        if (cb_role.getSelectedIndex() != -1) {
            idRole = (int) lesClesRoles[cb_role.getSelectedIndex()];
        }
        if (modeGlobal == 'A') {
            try {
                remise_a_zero();
                leControl.ajouter_util(nom, prenom, mail, mdp, commentaire, idRole);
            } catch (SQLException ex) {
                Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (modeGlobal == 'U') {
            try {
                idUtilisateur = Integer.valueOf(tf_id.getText());
                email_verified_at = unUtilisateur.getEmail_verified_at();
                remise_a_zero();
                leControl.update_util(idUtilisateur, nom, prenom, mail, mdp, commentaire, idRole, email_verified_at);
            } catch (SQLException ex) {
                Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_enregistrerActionPerformed

    private void bt_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_annulerActionPerformed
        remise_a_zero();
    }//GEN-LAST:event_bt_annulerActionPerformed

    private void bt_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_modifierActionPerformed
        if (tb_utils.getSelectedRowCount() != 1) {
            op_message.showMessageDialog(this, "Sélectionner 1 utilisateur !", "Sélection obligatoire", op_message.WARNING_MESSAGE);
        } else {
            try {
                this.ihm('U', lesUtils);
                modeGlobal = 'U';
            } catch (SQLException ex) {
                Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_modifierActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(V_Utilisateurs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                V_Utilisateurs_Admin dialog = new V_Utilisateurs_Admin(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_ajouter;
    private javax.swing.JButton bt_annuler;
    private javax.swing.JButton bt_enregistrer;
    private javax.swing.JButton bt_generer;
    private javax.swing.JButton bt_modifier;
    private javax.swing.JButton bt_supprimer;
    private javax.swing.JComboBox<String> cb_role;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_commentaire;
    private javax.swing.JLabel lb_id;
    private javax.swing.JLabel lb_mail;
    private javax.swing.JLabel lb_mdp;
    private javax.swing.JLabel lb_nom;
    private javax.swing.JLabel lb_prenom;
    private javax.swing.JLabel lb_role;
    private javax.swing.JOptionPane op_message;
    private javax.swing.JTextArea ta_commentaire;
    private javax.swing.JTable tb_utils;
    private javax.swing.JTextField tf_id;
    private javax.swing.JTextField tf_mail;
    private javax.swing.JPasswordField tf_mdp;
    private javax.swing.JTextField tf_nom;
    private javax.swing.JTextField tf_prenom;
    // End of variables declaration//GEN-END:variables
}
