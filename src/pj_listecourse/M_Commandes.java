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
public class M_Commandes {

    private Db_mariadb db;
    private int idCommande, quantite, id_article, id_liste, id_marque;
    private String marque, commentaire;
    private LocalDateTime created_at, updated_at;

    public M_Commandes(Db_mariadb db, int idCommande, int quantite, String marque, String commentaire, int id_article, int id_liste, int id_marque) {
        this.db = db;
        this.idCommande = idCommande;
        this.quantite = quantite;
        this.marque = marque;
        this.commentaire = commentaire;
        this.id_article = id_article;
        this.id_liste = id_liste;
        this.id_marque = id_marque;
    }

    public M_Commandes(Db_mariadb db, int quantite, String marque, String commentaire, int id_article, int id_liste, int id_marque) throws SQLException {
        this.db = db;
        this.quantite = quantite;
        this.marque = marque;
        this.commentaire = commentaire;
        this.id_article = id_article;
        this.id_liste = id_liste;
        this.id_marque = id_marque;

        String sql = "INSERT INTO mcd_commandes (quantite, marque, commentaire, id_article, id_liste, id_marque) "
                + "VALUES (" + quantite + ", '" + marque + "', '" + commentaire + "', '" + id_article + "', '" + id_liste + "', '" + id_marque + "')";
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.idCommande = res.getInt("id");
        res.close();
    }

    public M_Commandes(Db_mariadb db, int idCommande) throws SQLException {
        this.db = db;
        this.idCommande = idCommande;

        String sql = "SELECT * FROM mcd_commandes WHERE id ='" + idCommande + "';";

        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();

        this.quantite = res.getInt("quantite");
        this.marque = res.getString("marque");
        this.commentaire = res.getString("commentaire");
        this.id_article = res.getInt("id_article");
        this.id_liste = res.getInt("id_liste");
        this.id_marque = res.getInt("id_marque");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        res.close();
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getId_article() {
        return id_article;
    }

    public int getId_liste() {
        return id_liste;
    }

    public int getId_marque() {
        return id_marque;
    }

    public String getMarque() {
        return marque;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public void setId_liste(int id_liste) {
        this.id_liste = id_liste;
    }

    public void setId_marque(int id_marque) {
        this.id_marque = id_marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void update() throws SQLException {
        String sql = "UPDATE mcd_commandes SET quantite=" + quantite + ", marque='" + marque + "', commentaire='" + commentaire + "', id_article=" + id_article + ", id_liste=" + id_liste + ", id_marque=" + id_marque + " WHERE id = '" + idCommande + "';";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_commandes WHERE id='" + idCommande + "';";
        db.sqlExec(sql);
    }

    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Commandes> lesCommandes = new LinkedHashMap();
        M_Commandes uneCommande;

        int cle, quantite, id_article, id_liste, id_marque;
        String marque, commentaire;

        String sql = "SELECT * FROM mcd_commandes WHERE " + clauseWhere + ";";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            quantite = res.getInt("quantite");
            marque = res.getString("marque");
            commentaire = res.getString("commentaire");
            id_article = res.getInt("id_article");
            id_liste = res.getInt("id_liste");
            id_marque = res.getInt("id_marque");

            uneCommande = new M_Commandes(db, cle, quantite, marque, commentaire, id_article, id_liste, id_marque);
            lesCommandes.put(cle, uneCommande);
        }
        res.close();
        return lesCommandes;
    }

    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    @Override
    public String toString() {
        return "M_Commandes{" + "idCommande=" + idCommande + ", quantite=" + quantite + ", id_article=" + id_article + ", id_liste=" + id_liste + ", id_marque=" + id_marque + ", marque=" + marque + ", commentaire=" + commentaire + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }


    /*Tests*/
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

// IDs EXISTANTS dans la BDD
        int idArticle = 1; // Pâtes
        int idMarque = 2; // Barilla
        int idListe = 1; // une liste existante (à adapter si besoin)

        System.out.println("=== CREATE ===");
        M_Commandes c1 = new M_Commandes(base, 2, "Barilla", "pâtes x2", idArticle, idListe, idMarque);
        System.out.println(c1);

        System.out.println("\n=== READ ===");
        M_Commandes c2 = new M_Commandes(base, c1.getIdCommande());
        System.out.println(c2);

        System.out.println("\n=== UPDATE ===");
        c2.setQuantite(5);
        c2.setCommentaire("pâtes Barilla x5");
        c2.update();

        M_Commandes c3 = new M_Commandes(base, c1.getIdCommande());
        System.out.println(c3);

        System.out.println("\n=== DELETE ===");
        c3.delete();
        System.out.println("Commande supprimée");

        System.out.println("\n=== LISTE DES COMMANDES ===");
        LinkedHashMap<Integer, M_Commandes> lesCmd = M_Commandes.getRecords(base);
        for (int cle : lesCmd.keySet()) {
            System.out.println(lesCmd.get(cle));
        }

        System.out.println("\n✅ Tests M_Commandes OK");
    }

}
