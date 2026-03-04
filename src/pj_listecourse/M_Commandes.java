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
    private int idCommande, quantite, id_article, id_liste, id_marque, id_magasin;
    private String commentaire;
    private LocalDateTime created_at, updated_at;
    private float prix_unitaire;
    
//Lecture
    public M_Commandes(Db_mariadb db, int idCommande, int quantite, String commentaire, int id_article,
            int id_liste, int id_marque, int id_magasin, float prix_unitaire) {
        this.db = db;
        this.idCommande = idCommande;
        this.quantite = quantite;
        this.commentaire = commentaire;
        this.id_article = id_article;
        this.id_liste = id_liste;
        this.id_marque = id_marque;
        this.id_magasin = id_magasin;
        this.prix_unitaire = prix_unitaire;
    }
//Insert
    public M_Commandes(Db_mariadb db, int quantite, String commentaire, int id_article,
            int id_liste, int id_marque, int id_magasin, float prix_unitaire) throws SQLException {
        this.db = db;
        this.quantite = quantite;
        this.commentaire = commentaire;
        this.id_article = id_article;
        this.id_liste = id_liste;
        this.id_marque = id_marque;
        this.id_magasin = id_magasin;
        this.prix_unitaire = prix_unitaire;

        String sql = "INSERT INTO mcd_commandes (quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix_unitaire) "
                + "VALUES (" + quantite + ", '" + commentaire + "', "
                + id_article + ", " + id_liste + ", " + id_marque + ", "
                + id_magasin + ", " + prix_unitaire + ");";

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
        this.commentaire = res.getString("commentaire");
        this.id_article = res.getInt("id_article");
        this.id_liste = res.getInt("id_liste");
        this.id_marque = res.getInt("id_marque");
        this.id_magasin = res.getInt("id_magasin");
        this.prix_unitaire = res.getFloat("prix_unitaire");
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

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public int getId_magasin() {
        return id_magasin;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public float getPrix_unitaire() {
        return prix_unitaire;
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

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setId_magasin(int id_magasin) {
        this.id_magasin = id_magasin;
    }

    public void setPrix_unitaire(float prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public void update() throws SQLException {
        String sql = "UPDATE mcd_commandes SET "
                + "quantite=" + quantite + ", "
                + "commentaire='" + commentaire + "', "
                + "id_article=" + id_article + ", "
                + "id_liste=" + id_liste + ", "
                + "id_marque=" + id_marque + ", "
                + "id_magasin=" + id_magasin + ", "
                + "prix_unitaire=" + prix_unitaire + " "
                + "WHERE id=" + idCommande + ";";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_commandes WHERE id='" + idCommande + "';";
        db.sqlExec(sql);
    }

    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Commandes> lesCommandes = new LinkedHashMap();
        M_Commandes uneCommande;

        int cle, quantite, id_article, id_liste, id_marque, id_magasin;
        String commentaire;
        float prix_unitaire;

        String sql = "SELECT * FROM mcd_commandes WHERE " + clauseWhere + ";";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            quantite = res.getInt("quantite");
            commentaire = res.getString("commentaire");
            id_article = res.getInt("id_article");
            id_liste = res.getInt("id_liste");
            id_marque = res.getInt("id_marque");
            id_magasin = res.getInt("id_magasin");
            prix_unitaire = res.getFloat("prix_unitaire");

            uneCommande = new M_Commandes(db, cle, quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix_unitaire);
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
        return "M_Commandes{" + "db=" + db + ", idCommande=" + idCommande + ", quantite=" + quantite + ", id_article=" + id_article + ", id_liste=" + id_liste + ", id_marque=" + id_marque + ", id_magasin=" + id_magasin + ", commentaire=" + commentaire + ", created_at=" + created_at + ", updated_at=" + updated_at + ", prix_unitaire=" + prix_unitaire + '}';
    }

    /*Tests*/
//    public static void main(String[] args) throws Exception {
//
//        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
//
//        // 1) Récupérer un triplet EXISTANT dans mcd_proposer (obligatoire pour la FK composite)
//        int idArticle = 0, idMarque = 0, idMagasin = 0;
//        float prixUnitaire = 0f;
//
//        ResultSet rp = base.sqlSelect("SELECT id_article, id_marque, id_magasin, prix "
//                + "FROM mcd_proposer "
//                + "WHERE prix IS NOT NULL "
//                + "LIMIT 1;");
//        if (!rp.first()) {
//            rp.close();
//            throw new Exception("❌ Aucun enregistrement dans mcd_proposer (avec prix). Ajoute au moins une proposition.");
//        }
//        idArticle = rp.getInt("id_article");
//        idMarque = rp.getInt("id_marque");
//        idMagasin = rp.getInt("id_magasin");
//        prixUnitaire = rp.getFloat("prix");
//        rp.close();
//
//        // 2) Récupérer une liste EXISTANTE
//        int idListe = 0;
//        ResultSet rl = base.sqlSelect("SELECT id FROM mcd_listes LIMIT 1;");
//        if (!rl.first()) {
//            rl.close();
//            throw new Exception("❌ Aucune liste dans mcd_listes. Crée une liste avant de tester les commandes.");
//        }
//        idListe = rl.getInt("id");
//        rl.close();
//
//        System.out.println("Triplet proposer trouvé : article=" + idArticle + ", marque=" + idMarque + ", magasin=" + idMagasin + ", prix=" + prixUnitaire);
//        System.out.println("Liste trouvée : idListe=" + idListe);
//
//        System.out.println("\n=== CREATE ===");
//        M_Commandes c1 = new M_Commandes(base, 2, "test commande", idArticle, idListe, idMarque, idMagasin, prixUnitaire);
//        System.out.println(c1);
//
//        System.out.println("\n=== READ ===");
//        M_Commandes c2 = new M_Commandes(base, c1.getIdCommande());
//        System.out.println(c2);
//
//        System.out.println("\n=== UPDATE ===");
//        c2.setQuantite(5);
//        c2.setCommentaire("test commande modifiée");
//        c2.setPrix_unitaire(prixUnitaire + 0.10f); // juste pour voir la modif
//        c2.update();
//
//        M_Commandes c3 = new M_Commandes(base, c1.getIdCommande());
//        System.out.println(c3);
//
//        System.out.println("\n=== DELETE ===");
//        c3.delete();
//        System.out.println("Commande supprimée");
//
//        System.out.println("\n=== LISTE DES COMMANDES ===");
//        LinkedHashMap<Integer, M_Commandes> lesCmd = M_Commandes.getRecords(base);
//        for (int cle : lesCmd.keySet()) {
//            System.out.println(lesCmd.get(cle));
//        }
//
//        System.out.println("\n✅ Tests M_Commandes OK");
//    }
    /*End Tests*/
}
