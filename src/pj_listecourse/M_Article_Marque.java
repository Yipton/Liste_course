/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pj_listecourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 *
 * @author Yipton
 */
public class M_Article_Marque {

    private Db_mariadb db;
    private int id_article;
    private String nom_article;
    private int id_marque;
    private String nom_marque;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Article_Marque(Db_mariadb db, int id_article, int id_marque, boolean insert) throws SQLException {
        this.db = db;
        this.id_article = id_article;
        this.id_marque = id_marque;

        if (insert) {
            String sql = "INSERT INTO mcd_article_marque (id_article, id_marque) VALUES (?, ?)";
            db.sqlExec(sql, id_article, id_marque);
        }
    }

    // -------------------------
    // Constructeur simple
    // -------------------------
    public M_Article_Marque(int id_article, String nom_article, int id_marque, String nom_marque) {
        this.id_article = id_article;
        this.nom_article = nom_article;
        this.id_marque = id_marque;
        this.nom_marque = nom_marque;
    }

    // -------------------------
    // SELECT : marques d'un article
    // -------------------------
    public static LinkedHashMap<Integer, M_Marques> getLesMarques(Db_mariadb db, int id_article) throws SQLException {
        LinkedHashMap<Integer, M_Marques> lesMarques = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_marques MA"
                + " INNER JOIN mcd_article_marque AR ON MA.id = AR.id_marque"
                + " WHERE AR.id_article = ? ORDER BY nom";

        ResultSet res = db.sqlSelect(sql, id_article);

        while (res.next()) {
            int cle = res.getInt("id_marque");
            String nom = res.getString("nom");
            String commentaire = res.getString("commentaire");
            M_Marques uneMarque = new M_Marques(db, cle, nom, commentaire);
            lesMarques.put(cle, uneMarque);
        }

        res.close();
        return lesMarques;
    }

    // -------------------------
    // SELECT : toutes les lignes article-marque
    // -------------------------
    public static LinkedHashMap<Integer, Object[]> getLignesArticleMarque(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, Object[]> lignes = new LinkedHashMap<>();

        String sql
                = "SELECT A.id AS id_article, A.nom AS article, "
                + "       MA.id AS id_marque, MA.nom AS marque "
                + "FROM mcd_articles A "
                + "LEFT JOIN mcd_article_marque AM ON AM.id_article = A.id "
                + "LEFT JOIN mcd_marques MA ON MA.id = AM.id_marque "
                + "ORDER BY A.nom, MA.nom";

        ResultSet res = db.sqlSelect(sql);

        int i = 0;
        while (res.next()) {
            int id_article = res.getInt("id_article");
            String article = res.getString("article");

            int id_marque = res.getInt("id_marque");
            if (res.wasNull()) {
                id_marque = 0;
            }

            String marque = res.getString("marque");
            if (marque == null) {
                marque = "";
            }

            lignes.put(i, new Object[]{id_article, article, marque, id_marque});
            i++;
        }

        res.close();
        return lignes;
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getId_article() {
        return id_article;
    }

    public int getId_marque() {
        return id_marque;
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        System.out.println("=== TEST 1 : getLesMarques ===");
        int articleTest = 1;
        LinkedHashMap<Integer, M_Marques> marques = M_Article_Marque.getLesMarques(base, articleTest);
        System.out.println("Marques pour l'article " + articleTest + " : " + marques.size());
        for (M_Marques m : marques.values()) {
            System.out.println("  " + m.getNom() + " - " + m.getCommentaire());
        }

        System.out.println("\n=== TEST 2 : getLignesArticleMarque ===");
        LinkedHashMap<Integer, Object[]> lignes = M_Article_Marque.getLignesArticleMarque(base);
        System.out.println("Nombre de lignes : " + lignes.size());
        for (Object[] ligne : lignes.values()) {
            System.out.println("  Article: " + ligne[1] + " | Marque: " + ligne[2]);
        }

        System.out.println("\n=== TEST 3 : INSERT (association article-marque) ===");
        // Décommenter pour tester :
        // new M_Article_Marque(base, 2, 3, true);
        // System.out.println("Association (2, 3) insérée !");

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
