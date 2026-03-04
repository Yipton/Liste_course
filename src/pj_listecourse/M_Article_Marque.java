/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 *
 * @author ninis
 */
public class M_Article_Marque {

    private Db_mariadb db;
    private int id_article;
    private String nom_article;
    private int id_marque;
    private String nom_marque;;
    private LocalDateTime created_at, updated_at;

    public M_Article_Marque(Db_mariadb db, int id_article, int id_marque, boolean insert) throws SQLException {
        this.db = db;
        this.id_article = id_article;
        this.id_marque = id_marque;

        if (insert) {
            String sql = "INSERT INTO mcd_article_marque (id_article, id_marque) VALUES ("
                    + id_article + ", " + id_marque + ")";
            db.sqlExec(sql);
        }
    }

    public static LinkedHashMap<Integer, M_Marques> getLesMarques(Db_mariadb db, int id_article) throws SQLException {
        LinkedHashMap<Integer, M_Marques> lesMarques;
        lesMarques = new LinkedHashMap();
        M_Marques uneMarque;

        int cle;
        String nom, commentaire;
        String sql;
        sql = "SELECT * FROM mcd_marques MA"
                + " INNER JOIN mcd_article_marque AR ON MA.id=AR.id_marque "
                + " WHERE id_article = " + id_article + " ORDER BY nom";
        ResultSet res;
        res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id_marque");
            nom = res.getString("nom");
            commentaire = res.getString("commentaire");
            uneMarque = new M_Marques(db, cle, nom, commentaire);
            lesMarques.put(cle, uneMarque);
        }

        return lesMarques;
    }

    public static LinkedHashMap<Integer, Object[]> getLignesArticleMarque(Db_mariadb db) throws SQLException {

        LinkedHashMap<Integer, Object[]> lignes = new LinkedHashMap<>();

        String sql
                = "SELECT A.id AS id_article, A.nom AS article, "
                + "       MA.id AS id_marque, MA.nom AS marque "
                + "FROM mcd_articles A "
                + "LEFT JOIN mcd_article_marque AM ON AM.id_article = A.id "
                + "LEFT JOIN mcd_marques MA ON MA.id = AM.id_marque "
                + "ORDER BY A.nom, MA.nom;";

        ResultSet res = db.sqlSelect(sql);

        int i = 0; // clé technique (car plusieurs lignes peuvent avoir le même id_article)
        while (res.next()) {

            int id_article = res.getInt("id_article");
            String article = res.getString("article");

            // MA peut être NULL si aucune marque
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

    public M_Article_Marque(int id_article, String nom_article,
            int id_marque, String nom_marque) {
        this.id_article = id_article;
        this.nom_article = nom_article;
        this.id_marque = id_marque;
        this.nom_marque = nom_marque;
    }

    public int getId_article() {
        return id_article;
    }

    public int getId_marque() {
        return id_marque;
    }
    
    

    /*Tests*/
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        int articleTest = 1;

        LinkedHashMap<Integer, M_Marques> marques = M_Article_Marque.getLesMarques(base, articleTest);

        System.out.println("Marques pour l'article : " + articleTest);
        System.out.println("Nb = " + marques.size());
        System.out.println("---------------------------");

        for (M_Marques a : marques.values()) {
            System.out.println(a.getNom()+ " - " + a.getCommentaire());
        }
        /*                     Même chose que :                                           */

//        for (int id_marque : marques.keySet()) {
//            M_Marques a = marques.get(id_marque);
//            System.out.println(a.getNom() + " - " + a.getCommentaire());
//        }
        // (Optionnel) Test insertion dans la table de mariage :
        // ex : associer id_article=2 avec id_marque=3
//         new M_Article_Marque(base, 2, 3, true);
//         System.out.println("Association (2,3) insérée dans mcd_article_marque.");
    }
}
