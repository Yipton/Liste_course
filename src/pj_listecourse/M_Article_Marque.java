/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 *
 * @author ninis
 */
public class M_Article_Marque {
    
    private Db_mariadb db;
    private int id_article;
    private int id_marque;
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

    /*Tests*/
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
//        int articleTest = 1;
//
//        LinkedHashMap<Integer, M_Marques> marques = M_Article_Marque.getLesMarques(base, articleTest);
//
//        System.out.println("Marques pour l'article : " + articleTest);
//        System.out.println("Nb = " + marques.size());
//        System.out.println("---------------------------");
//
//        for (M_Marques a : marques.values()) {
//            System.out.println(a.getNom()+ " - " + a.getCommentaire());
//        }
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
