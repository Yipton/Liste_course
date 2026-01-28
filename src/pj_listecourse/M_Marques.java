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
public class M_Marques {
    private Db_mariadb db;
    private int id;
    private String nom;
    private String commentaire;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur "lecture directe"
    // -------------------------
    public M_Marques(Db_mariadb db, int id, String nom, String commentaire) {
        this.db = db;
        this.id = id;
        this.nom = nom;
        this.commentaire = commentaire;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Marques(Db_mariadb db, String nom, String commentaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.commentaire = commentaire;

        String sql = "INSERT INTO mcd_marques (nom, commentaire) VALUES ('"
                + nom + "', '" + commentaire + "');";

        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Marques(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;

        String sql = "SELECT * FROM mcd_marques WHERE id=" + id + ";";
        ResultSet res = db.sqlSelect(sql);
        res.first();

        this.nom = res.getString("nom");
        this.commentaire = res.getString("commentaire");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_marques SET "
                + "nom='" + nom + "', "
                + "commentaire='" + commentaire + "'"
                + " WHERE id=" + id + ";";

        db.sqlExec(sql);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_marques WHERE id=" + id + ";";
        db.sqlExec(sql);
    }

    // -------------------------
    // SELECT MULTI
    // -------------------------
    public static LinkedHashMap<Integer, M_Marques> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {

        LinkedHashMap<Integer, M_Marques> lesArticles = new LinkedHashMap<>();
        M_Marques unArticle;

        int cle;
        String nom, commentaire;

        String sql = "SELECT * FROM mcd_marques WHERE " + clauseWhere + " ORDER BY nom;";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            nom = res.getString("nom");
            commentaire = res.getString("commentaire");

            unArticle = new M_Marques(db, cle, nom, commentaire);
            lesArticles.put(cle, unArticle);
        }
        res.close();
        return lesArticles;
    }

    public static LinkedHashMap<Integer, M_Marques> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    @Override
    public String toString() {
        return "M_Marques{" + "id=" + id + ", nom='" + nom + "', commentaire='" + commentaire + "'}";
    }

    /* Tests */
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(
                Cl_Connection.url,
                Cl_Connection.login,
                Cl_Connection.password
        );

//        M_Marques a1 = new M_Marques(base, 1);
//        System.out.println(a1);
//
//        M_Marques a2 = new M_Marques(base, "Céréales", "Test insertion");
//        System.out.println(a2);
//
//        M_Marques a3 = new M_Marques(base, a2.getId());
//        a3.setNom("Céréales modifiées");
//        a3.setCommentaire("Commentaire modifié");
//        a3.update();
//        System.out.println(a3);
//
//        LinkedHashMap<Integer, M_Marques> lesArticles = M_Marques.getRecords(base);
//        for (int cle : lesArticles.keySet()) {
//            System.out.println(lesArticles.get(cle));
//        }
//
//        a3.delete();
    }
}
