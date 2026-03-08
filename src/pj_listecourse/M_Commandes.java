/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author Yipton
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Commandes {

    private Db_mariadb db;
    private int idCommande, quantite, id_article, id_liste, id_marque, id_magasin;
    private String commentaire;
    private LocalDateTime created_at, updated_at;
    private float prix_unitaire;

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
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

    // -------------------------
    // Constructeur INSERT
    // -------------------------
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
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.sqlExec(sql, quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix_unitaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.idCommande = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Commandes(Db_mariadb db, int idCommande) throws SQLException {
        this.db = db;
        this.idCommande = idCommande;

        String sql = "SELECT * FROM mcd_commandes WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, idCommande);
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

    // -------------------------
    // Getters
    // -------------------------
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

    public int getId_magasin() {
        return id_magasin;
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

    public float getPrix_unitaire() {
        return prix_unitaire;
    }

    // -------------------------
    // Setters
    // -------------------------
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

    public void setId_magasin(int id_magasin) {
        this.id_magasin = id_magasin;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setPrix_unitaire(float prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_commandes SET "
                + "quantite = ?, "
                + "commentaire = ?, "
                + "id_article = ?, "
                + "id_liste = ?, "
                + "id_marque = ?, "
                + "id_magasin = ?, "
                + "prix_unitaire = ? "
                + "WHERE id = ?";
        db.sqlExec(sql, quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix_unitaire, idCommande);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_commandes WHERE id = ?";
        db.sqlExec(sql, idCommande);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Commandes> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {
        LinkedHashMap<Integer, M_Commandes> lesCommandes = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_commandes WHERE " + clauseWhere;
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            int quantite = res.getInt("quantite");
            String commentaire = res.getString("commentaire");
            int id_article = res.getInt("id_article");
            int id_liste = res.getInt("id_liste");
            int id_marque = res.getInt("id_marque");
            int id_magasin = res.getInt("id_magasin");
            float prix_unitaire = res.getFloat("prix_unitaire");

            M_Commandes uneCommande = new M_Commandes(db, cle, quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix_unitaire);
            lesCommandes.put(cle, uneCommande);
        }
        res.close();
        return lesCommandes;
    }

    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Commandes{idCommande=" + idCommande
                + ", quantite=" + quantite
                + ", id_article=" + id_article
                + ", id_liste=" + id_liste
                + ", id_marque=" + id_marque
                + ", id_magasin=" + id_magasin
                + ", commentaire='" + commentaire + "'"
                + ", prix_unitaire=" + prix_unitaire
                + ", created_at=" + created_at
                + ", updated_at=" + updated_at + "}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // ===========================
        // Récupérer un triplet EXISTANT dans mcd_proposer
        // ===========================
        int idArticle = 0, idMarque = 0, idMagasin = 0;
        float prixUnitaire = 0f;

        ResultSet rp = base.sqlSelect("SELECT id_article, id_marque, id_magasin, prix "
                + "FROM mcd_proposer WHERE prix IS NOT NULL LIMIT 1");
        if (!rp.first()) {
            rp.close();
            throw new Exception("Aucun enregistrement dans mcd_proposer");
        }
        idArticle = rp.getInt("id_article");
        idMarque = rp.getInt("id_marque");
        idMagasin = rp.getInt("id_magasin");
        prixUnitaire = rp.getFloat("prix");
        rp.close();

        // ===========================
        // Récupérer une liste EXISTANTE
        // ===========================
        int idListe = 0;
        ResultSet rl = base.sqlSelect("SELECT id FROM mcd_listes LIMIT 1");
        if (!rl.first()) {
            rl.close();
            throw new Exception("Aucune liste dans mcd_listes");
        }
        idListe = rl.getInt("id");
        rl.close();

        System.out.println("=== DONNÉES DE TEST ===");
        System.out.println("Triplet proposer : article=" + idArticle + ", marque=" + idMarque + ", magasin=" + idMagasin + ", prix=" + prixUnitaire);
        System.out.println("Liste : idListe=" + idListe);

        // ===========================
        // TEST 1 : INSERT
        // ===========================
        System.out.println("\n=== TEST 1 : INSERT ===");
        M_Commandes c1 = new M_Commandes(base, 2, "test commande", idArticle, idListe, idMarque, idMagasin, prixUnitaire);
        System.out.println("Nouvelle commande : " + c1);

        // ===========================
        // TEST 2 : SELECT par ID
        // ===========================
        System.out.println("\n=== TEST 2 : SELECT par ID ===");
        M_Commandes c2 = new M_Commandes(base, c1.getIdCommande());
        System.out.println("Commande lue : " + c2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        c2.setQuantite(5);
        c2.setCommentaire("test commande modifiée");
        c2.setPrix_unitaire(prixUnitaire + 0.10f);
        c2.update();
        M_Commandes c3 = new M_Commandes(base, c1.getIdCommande());
        System.out.println("Après update : " + c3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Commandes> toutes = M_Commandes.getRecords(base);
        System.out.println("Nombre de commandes : " + toutes.size());
        for (M_Commandes c : toutes.values()) {
            System.out.println("  " + c);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        LinkedHashMap<Integer, M_Commandes> filtrees = M_Commandes.getRecords(base, "quantite > ?", 1);
        System.out.println("Commandes avec quantité > 1 : " + filtrees.size() + " résultat(s)");
        for (M_Commandes c : filtrees.values()) {
            System.out.println("  " + c);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + c3);
        c3.delete();
        System.out.println("Commande supprimée !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Commandes> restantes = M_Commandes.getRecords(base);
        System.out.println("Nombre de commandes restantes : " + restantes.size());
        for (M_Commandes c : restantes.values()) {
            System.out.println("  " + c);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
