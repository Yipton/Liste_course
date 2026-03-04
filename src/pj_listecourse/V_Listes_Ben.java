/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pj_listecourse;

import java.awt.CardLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ninis
 */
public class V_Listes_Ben extends javax.swing.JDialog {

    private String nom, commentaire;
    private int quantite, id_liste_courante;
    private float prix, prix_total = 0;
    private LocalDate date_souhaitee;
    private Date date;
    private C_ListeCourse leControl;
    private DefaultTableModel dmDefaultTableModel;
    private LinkedHashMap<Integer, M_Listes> lesListes;
    private LinkedHashMap<Integer, Object[]> lesPropositions;

    public V_Listes_Ben(java.awt.Frame parent, boolean modal, C_ListeCourse leControleur) {
        super(parent, modal);
        leControl = leControleur;
        initComponents();
    }

    public void afficher(String mode,
            LinkedHashMap<Integer, M_Listes> lesListes,
            LinkedHashMap<Integer, Object[]> lesPropositions) {

        CardLayout cl = (CardLayout) pn_parent.getLayout();
        cl.show(pn_parent, mode);
        setTitle(mode);

        this.lesListes = lesListes;
        this.lesPropositions = lesPropositions;

        switch (mode) {
            case "read_lists":
                remplir_tableau_listes();
                break;

            case "ajouter_articles":
                cacher_colonne(0);
                cacher_colonne(2);
                cacher_colonne(4);
                remplir_tableau_articles();
                bt_voir_panier.setVisible(false);
                lb_prix_total.setVisible(false);
                break;
        }

        setSize(650, 650);
        setLocationRelativeTo(null);

        pn_parent.revalidate();
        pn_parent.repaint();

        setVisible(true);
    }

    private void cacher_colonne(int index) {
        tb_articles.getColumnModel().getColumn(index).setMinWidth(0);
        tb_articles.getColumnModel().getColumn(index).setMaxWidth(0);
        tb_articles.getColumnModel().getColumn(index).setWidth(0);
    }

    public void remplir_tableau_listes() {
        dmDefaultTableModel = (DefaultTableModel) tb_listes.getModel();
        dmDefaultTableModel.setRowCount(lesListes.size());

        int compteur = 0;

        for (int uneCle : lesListes.keySet()) {
            M_Listes uneListe = lesListes.get(uneCle);
            tb_listes.setValueAt(uneListe.getIdListe(), compteur, 0);
            tb_listes.setValueAt(uneListe.getNom(), compteur, 1);
            tb_listes.setValueAt(uneListe.getDateAchat(), compteur, 2);
            compteur++;
        }
    }

    public void remplir_tableau_articles() {
        dmDefaultTableModel = (DefaultTableModel) tb_articles.getModel();
        dmDefaultTableModel.setRowCount(lesPropositions.size());

        int compteur = 0;

        for (Integer uneCle : lesPropositions.keySet()) {

            Object[] row = lesPropositions.get(uneCle);

            tb_articles.setValueAt(row[0], compteur, 0); // Id_article
            tb_articles.setValueAt(row[1], compteur, 1); // Article
            tb_articles.setValueAt(row[2], compteur, 2); // Id_marque
            tb_articles.setValueAt(row[3], compteur, 3); // Marque
            tb_articles.setValueAt(row[4], compteur, 4); // Id_magasin
            tb_articles.setValueAt(row[5], compteur, 5); // Magasin
            tb_articles.setValueAt(row[6], compteur, 6); // Prix

            compteur++;
        }
    }

    public int get_id_liste_selectionnee() {
        int result = 0;

        int row = tb_listes.getSelectedRow();
        if (row >= 0) {
            Object val = tb_listes.getValueAt(row, 0);
            if (val != null) {
                try {
                    result = Integer.parseInt(val.toString());
                } catch (Exception e) {
                    result = 0;
                }
            }
        }

        return result;
    }

    public int[] get_ids_proposition_selectionnee() {
        int[] result;
        int idArticle, idMarque, idMagasin;
        int row = tb_articles.getSelectedRow();

        // si aucune ligne sélectionnée
        if (row == -1) {
            result = null;
        } else {
            prix = (float) tb_articles.getValueAt(row, 6);

            idArticle = (int) tb_articles.getValueAt(row, 0);
            idMarque = (int) tb_articles.getValueAt(row, 2);
            idMagasin = (int) tb_articles.getValueAt(row, 4);

            result = new int[]{idArticle, idMarque, idMagasin};
        }
        return result;
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
        pn_parent = new javax.swing.JPanel();
        pn_panier = new javax.swing.JPanel();
        lb_title_panier = new javax.swing.JLabel();
        pn_read_lists = new javax.swing.JPanel();
        lb_title_read_listes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_listes = new javax.swing.JTable();
        bt_modifier = new javax.swing.JButton();
        bt_details = new javax.swing.JButton();
        bt_supprimer = new javax.swing.JButton();
        pn_detail_liste = new javax.swing.JPanel();
        lb_detail_liste = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lb_nom_liste1 = new javax.swing.JLabel();
        tf_nom_liste1 = new javax.swing.JTextField();
        lb_date_souhaitee1 = new javax.swing.JLabel();
        dc_date_souhaitee1 = new com.toedter.calendar.JDateChooser();
        bt_modifier_liste = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pn_create_list = new javax.swing.JPanel();
        lb_title_create_list = new javax.swing.JLabel();
        lb_nom_liste = new javax.swing.JLabel();
        tf_nom_liste = new javax.swing.JTextField();
        lb_nom_liste_exemple = new javax.swing.JLabel();
        lb_date_souhaitee = new javax.swing.JLabel();
        dc_date_souhaitee = new com.toedter.calendar.JDateChooser();
        lb_commentaire = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_commentaire = new javax.swing.JTextArea();
        bt_valider = new javax.swing.JButton();
        pn__ajouter_articles = new javax.swing.JPanel();
        lb_title_ajouter_articles = new javax.swing.JLabel();
        lb_rechercher_article = new javax.swing.JLabel();
        tf_rechercher_article = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_articles = new javax.swing.JTable();
        lb_quantite = new javax.swing.JLabel();
        tf_quantite = new javax.swing.JTextField();
        bt_ajouter = new javax.swing.JButton();
        bt_voir_panier = new javax.swing.JButton();
        lb_prix_total = new javax.swing.JLabel();
        lb_commentaire_add_article = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ta_commentaire_add_article = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pn_parent.setMaximumSize(new java.awt.Dimension(650, 250));
        pn_parent.setLayout(new java.awt.CardLayout());

        pn_panier.setName("panier"); // NOI18N
        pn_panier.setPreferredSize(new java.awt.Dimension(650, 550));

        lb_title_panier.setText("Détails du panier");

        javax.swing.GroupLayout pn_panierLayout = new javax.swing.GroupLayout(pn_panier);
        pn_panier.setLayout(pn_panierLayout);
        pn_panierLayout.setHorizontalGroup(
            pn_panierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_panierLayout.createSequentialGroup()
                .addGap(517, 517, 517)
                .addComponent(lb_title_panier, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_panierLayout.setVerticalGroup(
            pn_panierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_panierLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lb_title_panier)
                .addContainerGap(520, Short.MAX_VALUE))
        );

        pn_parent.add(pn_panier, "panier");

        pn_read_lists.setName(""); // NOI18N
        pn_read_lists.setPreferredSize(new java.awt.Dimension(650, 550));

        lb_title_read_listes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lb_title_read_listes.setText("Voir les listes de courses");

        tb_listes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nom", "Date souhaitée"
            }
        ));
        tb_listes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tb_listes);

        bt_modifier.setText("Modifier");

        bt_details.setText("Détails");
        bt_details.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_detailsActionPerformed(evt);
            }
        });

        bt_supprimer.setText("Supprimer");

        javax.swing.GroupLayout pn_read_listsLayout = new javax.swing.GroupLayout(pn_read_lists);
        pn_read_lists.setLayout(pn_read_listsLayout);
        pn_read_listsLayout.setHorizontalGroup(
            pn_read_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_read_listsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bt_details)
                .addGap(18, 18, 18)
                .addComponent(bt_modifier)
                .addGap(18, 18, 18)
                .addComponent(bt_supprimer)
                .addGap(159, 159, 159))
            .addGroup(pn_read_listsLayout.createSequentialGroup()
                .addGroup(pn_read_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_read_listsLayout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(lb_title_read_listes, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_read_listsLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        pn_read_listsLayout.setVerticalGroup(
            pn_read_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_read_listsLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lb_title_read_listes)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addGroup(pn_read_listsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_modifier)
                    .addComponent(bt_details)
                    .addComponent(bt_supprimer))
                .addGap(98, 98, 98))
        );

        pn_parent.add(pn_read_lists, "read_lists");
        pn_read_lists.getAccessibleContext().setAccessibleName("");

        pn_detail_liste.setName(""); // NOI18N
        pn_detail_liste.setPreferredSize(new java.awt.Dimension(650, 550));

        lb_detail_liste.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lb_detail_liste.setText("Détails d'une liste de courses");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Article", "Marque", "Quantité", "Détails"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        lb_nom_liste1.setText("Nom de la liste de course :");

        tf_nom_liste1.setToolTipText("");

        lb_date_souhaitee1.setText("Date de livraison souhaitée :");

        bt_modifier_liste.setText("jButton1");

        jButton2.setText("jButton1");

        javax.swing.GroupLayout pn_detail_listeLayout = new javax.swing.GroupLayout(pn_detail_liste);
        pn_detail_liste.setLayout(pn_detail_listeLayout);
        pn_detail_listeLayout.setHorizontalGroup(
            pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_detail_listeLayout.createSequentialGroup()
                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_detail_listeLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(lb_detail_liste))
                    .addGroup(pn_detail_listeLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_detail_listeLayout.createSequentialGroup()
                                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_nom_liste1)
                                    .addComponent(lb_date_souhaitee1))
                                .addGap(22, 22, 22)
                                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_nom_liste1)
                                    .addComponent(dc_date_souhaitee1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pn_detail_listeLayout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(bt_modifier_liste)
                        .addGap(59, 59, 59)
                        .addComponent(jButton2)))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        pn_detail_listeLayout.setVerticalGroup(
            pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_detail_listeLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(lb_detail_liste)
                .addGap(27, 27, 27)
                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nom_liste1)
                    .addComponent(tf_nom_liste1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_date_souhaitee1)
                    .addComponent(dc_date_souhaitee1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pn_detail_listeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_modifier_liste)
                    .addComponent(jButton2))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pn_parent.add(pn_detail_liste, "detail_liste");

        pn_create_list.setName(""); // NOI18N
        pn_create_list.setPreferredSize(new java.awt.Dimension(650, 550));

        lb_title_create_list.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lb_title_create_list.setText("Créer une nouvelle liste de courses");

        lb_nom_liste.setText("Nom de la liste de course :");

        tf_nom_liste.setToolTipText("");

        lb_nom_liste_exemple.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        lb_nom_liste_exemple.setText("Exemple : Courses de la semaine");

        lb_date_souhaitee.setText("Date de livraison souhaitée :");

        lb_commentaire.setText("Commentaire :");

        ta_commentaire.setColumns(20);
        ta_commentaire.setRows(5);
        jScrollPane2.setViewportView(ta_commentaire);

        bt_valider.setText("Valider");
        bt_valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_validerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_create_listLayout = new javax.swing.GroupLayout(pn_create_list);
        pn_create_list.setLayout(pn_create_listLayout);
        pn_create_listLayout.setHorizontalGroup(
            pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_create_listLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_create_listLayout.createSequentialGroup()
                        .addComponent(lb_date_souhaitee, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_date_souhaitee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pn_create_listLayout.createSequentialGroup()
                        .addComponent(lb_nom_liste, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_nom_liste, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_nom_liste_exemple, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lb_title_create_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_create_listLayout.createSequentialGroup()
                        .addComponent(lb_commentaire, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_valider)
                            .addComponent(jScrollPane2))))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        pn_create_listLayout.setVerticalGroup(
            pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_create_listLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lb_title_create_list)
                .addGap(36, 36, 36)
                .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nom_liste)
                    .addComponent(tf_nom_liste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_nom_liste_exemple)
                .addGap(18, 18, 18)
                .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_date_souhaitee)
                    .addComponent(dc_date_souhaitee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(pn_create_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_commentaire)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(bt_valider)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        pn_parent.add(pn_create_list, "create_list");

        lb_title_ajouter_articles.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lb_title_ajouter_articles.setText("Ajouter des articles");

        lb_rechercher_article.setText("Rechercher un article :");

        tf_rechercher_article.setToolTipText("");

        tb_articles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id_article", "Article", "Id_marque", "Marque", "Id_magasin", "Magasin", "Prix"
            }
        ));
        tb_articles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tb_articles);

        lb_quantite.setText("Quantité :");

        tf_quantite.setToolTipText("");

        bt_ajouter.setText("Ajouter");
        bt_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ajouterActionPerformed(evt);
            }
        });

        bt_voir_panier.setText("Voir le panier");

        lb_prix_total.setText("Prix total :");

        lb_commentaire_add_article.setText("Commentaire :");

        ta_commentaire_add_article.setColumns(20);
        ta_commentaire_add_article.setRows(5);
        jScrollPane5.setViewportView(ta_commentaire_add_article);

        javax.swing.GroupLayout pn__ajouter_articlesLayout = new javax.swing.GroupLayout(pn__ajouter_articles);
        pn__ajouter_articles.setLayout(pn__ajouter_articlesLayout);
        pn__ajouter_articlesLayout.setHorizontalGroup(
            pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn__ajouter_articlesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_title_ajouter_articles, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
            .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                        .addComponent(lb_rechercher_article, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_rechercher_article, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                        .addComponent(lb_quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                                .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                                        .addComponent(tf_quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lb_commentaire_add_article, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(bt_ajouter)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                                .addComponent(lb_prix_total, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bt_voir_panier, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        pn__ajouter_articlesLayout.setVerticalGroup(
            pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lb_title_ajouter_articles)
                .addGap(34, 34, 34)
                .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_rechercher_article)
                    .addComponent(tf_rechercher_article, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn__ajouter_articlesLayout.createSequentialGroup()
                        .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_quantite)
                            .addComponent(tf_quantite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_commentaire_add_article))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_ajouter))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(pn__ajouter_articlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_prix_total, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_voir_panier))
                .addGap(23, 23, 23))
        );

        pn_parent.add(pn__ajouter_articles, "ajouter_articles");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pn_parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(pn_parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_validerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_validerActionPerformed
        nom = tf_nom_liste.getText();

        date = dc_date_souhaitee.getDate();
        date_souhaitee = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        commentaire = ta_commentaire.getText();

        try {
            id_liste_courante = leControl.ajouter_liste(nom, date_souhaitee, commentaire);
            leControl.aff_V_Listes_Ben("ajouter_articles");
        } catch (SQLException ex) {
            Logger.getLogger(V_Listes_Ben.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_validerActionPerformed

    private void bt_detailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_detailsActionPerformed
        try {
            //Récupérer la liste
            leControl.aff_V_Listes_Ben("detail_liste");
        } catch (SQLException ex) {
            Logger.getLogger(V_Listes_Ben.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_detailsActionPerformed

    private void bt_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ajouterActionPerformed
        boolean ok = true;
        String msg = "";

        // 1) Quantité
        try {
            quantite = Integer.parseInt(tf_quantite.getText());
            if (quantite <= 0) {
                ok = false;
                msg = "Quantité invalide.";
            }
        } catch (Exception e) {
            ok = false;
            msg = "Quantité invalide.";
        }

        // 2) Récupérer l'offre sélectionnée (id_article, id_marque, id_magasin)
        int[] ids = null;
        if (ok) {
            ids = get_ids_proposition_selectionnee();
            if (ids == null) {
                ok = false;
                msg = "Sélectionne une offre (article / marque / magasin).";
            }
        }

        // 3) Vérifier la liste courante
        if (ok) {
            if (id_liste_courante <= 0) {
                ok = false;
                msg = "Aucune liste courante.";
            }
        }

        // 4) Appel contrôleur
        if (ok) {
            try {
                int id_article = ids[0];
                int id_marque = ids[1];
                int id_magasin = ids[2];
                commentaire = ta_commentaire_add_article.getText();

                leControl.ajouter_commande(quantite, commentaire, id_article, id_liste_courante, id_marque, id_magasin);

                op_message.showMessageDialog(this, "Article(s) ajouté(s) au panier !");

                tf_quantite.setText("");
                ta_commentaire_add_article.setText("");
                lb_prix_total.setVisible(true);
                prix_total += prix * quantite;
                lb_prix_total.setText("Prix total : " + prix_total + " €");
                bt_voir_panier.setVisible(true);

            } catch (SQLException ex) {
                ok = false;
                msg = "Erreur SQL : " + ex.getMessage();
                ex.printStackTrace();
            } catch (Exception ex) {
                ok = false;
                msg = "Erreur : " + ex.getClass().getSimpleName() + " - " + ex.getMessage();
                ex.printStackTrace();
            }
        }

        // 5) Affichage d'erreur
        if (!ok) {
            op_message.showMessageDialog(this, msg);
        }

    }//GEN-LAST:event_bt_ajouterActionPerformed

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
            java.util.logging.Logger.getLogger(V_Listes_Ben.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(V_Listes_Ben.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(V_Listes_Ben.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(V_Listes_Ben.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                V_Listes_Ben dialog = new V_Listes_Ben(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton bt_details;
    private javax.swing.JButton bt_modifier;
    private javax.swing.JButton bt_modifier_liste;
    private javax.swing.JButton bt_supprimer;
    private javax.swing.JButton bt_valider;
    private javax.swing.JButton bt_voir_panier;
    private com.toedter.calendar.JDateChooser dc_date_souhaitee;
    private com.toedter.calendar.JDateChooser dc_date_souhaitee1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb_commentaire;
    private javax.swing.JLabel lb_commentaire_add_article;
    private javax.swing.JLabel lb_date_souhaitee;
    private javax.swing.JLabel lb_date_souhaitee1;
    private javax.swing.JLabel lb_detail_liste;
    private javax.swing.JLabel lb_nom_liste;
    private javax.swing.JLabel lb_nom_liste1;
    private javax.swing.JLabel lb_nom_liste_exemple;
    private javax.swing.JLabel lb_prix_total;
    private javax.swing.JLabel lb_quantite;
    private javax.swing.JLabel lb_rechercher_article;
    private javax.swing.JLabel lb_title_ajouter_articles;
    private javax.swing.JLabel lb_title_create_list;
    private javax.swing.JLabel lb_title_panier;
    private javax.swing.JLabel lb_title_read_listes;
    private javax.swing.JOptionPane op_message;
    private javax.swing.JPanel pn__ajouter_articles;
    private javax.swing.JPanel pn_create_list;
    private javax.swing.JPanel pn_detail_liste;
    private javax.swing.JPanel pn_panier;
    private javax.swing.JPanel pn_parent;
    private javax.swing.JPanel pn_read_lists;
    private javax.swing.JTextArea ta_commentaire;
    private javax.swing.JTextArea ta_commentaire_add_article;
    private javax.swing.JTable tb_articles;
    private javax.swing.JTable tb_listes;
    private javax.swing.JTextField tf_nom_liste;
    private javax.swing.JTextField tf_nom_liste1;
    private javax.swing.JTextField tf_quantite;
    private javax.swing.JTextField tf_rechercher_article;
    // End of variables declaration//GEN-END:variables
}
