/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

public class M_Proposer {

    private Db_mariadb db;

    private int id_article;
    private int id_marque;
    private int id_magasin;

    private String localisation;
    private float prix;
    private String commentaire;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    // -------------------------
    // Constructeur lecture + insert optionnel
    // -------------------------
    public M_Proposer(
            Db_mariadb db,
            int id_article,
            int id_marque,
            int id_magasin,
            String localisation,
            float prix,
            String commentaire,
            boolean insert
    ) throws SQLException {

        this.db = db;
        this.id_article = id_article;
        this.id_marque = id_marque;
        this.id_magasin = id_magasin;
        this.localisation = localisation;
        this.prix = prix;
        this.commentaire = commentaire;

        if (insert) {
            String sql = "INSERT INTO mcd_proposer (id_article, id_marque, id_magasin, localisation, prix, commentaire) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            db.sqlExec(sql, id_article, id_marque, id_magasin, localisation, prix, commentaire);
        }

        // Lecture systématique
        String sql = "SELECT * FROM mcd_proposer WHERE id_article = ? AND id_marque = ? AND id_magasin = ?";
        ResultSet res = db.sqlSelect(sql, id_article, id_marque, id_magasin);

        if (!res.next()) {
            res.close();
            throw new SQLException("Aucun enregistrement mcd_proposer pour (id_article="
                    + id_article + ", id_marque=" + id_marque + ", id_magasin=" + id_magasin + ")");
        }

        this.localisation = res.getString("localisation");
        this.prix = res.getFloat("prix");
        this.commentaire = res.getString("commentaire");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Constructeur SELECT par IDs
    // -------------------------
    public M_Proposer(Db_mariadb db, int id_article, int id_marque, int id_magasin) throws SQLException {
        this.db = db;
        this.id_article = id_article;
        this.id_marque = id_marque;
        this.id_magasin = id_magasin;

        String sql = "SELECT * FROM mcd_proposer WHERE id_article = ? AND id_marque = ? AND id_magasin = ?";
        ResultSet res = db.sqlSelect(sql, id_article, id_marque, id_magasin);
        res.first();

        this.localisation = res.getString("localisation");
        this.commentaire = res.getString("commentaire");
        this.prix = res.getFloat("prix");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdArticle() {
        return id_article;
    }

    public int getIdMarque() {
        return id_marque;
    }

    public int getIdMagasin() {
        return id_magasin;
    }

    public String getLocalisation() {
        return localisation;
    }

    public float getPrix() {
        return prix;
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
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_proposer SET "
                + "localisation = ?, "
                + "prix = ?, "
                + "commentaire = ? "
                + "WHERE id_article = ? AND id_marque = ? AND id_magasin = ?";
        db.sqlExec(sql, localisation, prix, commentaire, id_article, id_marque, id_magasin);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_proposer WHERE id_article = ? AND id_marque = ? AND id_magasin = ?";
        db.sqlExec(sql, id_article, id_marque, id_magasin);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<String, M_Proposer> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<String, M_Proposer> lesProposer = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_proposer WHERE " + clauseWhere
                + " ORDER BY id_magasin, id_article, id_marque";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int id_article = res.getInt("id_article");
            int id_marque = res.getInt("id_marque");
            int id_magasin = res.getInt("id_magasin");

            M_Proposer uneProposition = new M_Proposer(
                    db,
                    id_article,
                    id_marque,
                    id_magasin,
                    res.getString("localisation"),
                    res.getFloat("prix"),
                    res.getString("commentaire"),
                    false
            );

            lesProposer.put(id_article + "-" + id_marque + "-" + id_magasin, uneProposition);
        }

        res.close();
        return lesProposer;
    }

    public static LinkedHashMap<String, M_Proposer> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // GET LIGNES OFFRES (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, Object[]> getLignesOffres(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<Integer, Object[]> lignes = new LinkedHashMap<>();

        String sql
                = "SELECT P.id_article, A.nom AS article, "
                + "       P.id_marque, MA.nom AS marque, "
                + "       P.id_magasin, MG.nom AS magasin, "
                + "       P.prix, P.localisation "
                + "FROM mcd_proposer P "
                + "INNER JOIN mcd_articles A ON A.id = P.id_article "
                + "INNER JOIN mcd_marques MA ON MA.id = P.id_marque "
                + "INNER JOIN mcd_magasins MG ON MG.id = P.id_magasin "
                + "WHERE " + clauseWhere + " "
                + "ORDER BY A.nom, MA.nom, MG.nom";

        ResultSet res = db.sqlSelect(sql, params);

        int i = 0;
        while (res.next()) {
            lignes.put(i, new Object[]{
                res.getInt("id_article"),
                res.getString("article"),
                res.getInt("id_marque"),
                res.getString("marque"),
                res.getInt("id_magasin"),
                res.getString("magasin"),
                res.getFloat("prix"),
                res.getString("localisation")
            });
            i++;
        }
        res.close();
        return lignes;
    }

    public static LinkedHashMap<Integer, Object[]> getLignesOffres(Db_mariadb db) throws SQLException {
        return getLignesOffres(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Proposer{id_article=" + id_article
                + ", id_marque=" + id_marque
                + ", id_magasin=" + id_magasin
                + ", localisation='" + localisation + "'"
                + ", prix=" + prix
                + ", commentaire='" + commentaire + "'}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // IDs existants pour les FK
        int idA = 1;
        int idM = 2;
        int idG = 2;

        String localisation = "Rayon test";
        float prix = 1.23f;
        String commentaire = "test proposer";

        // ===========================
        // TEST 1 : INSERT + READ
        // ===========================
        System.out.println("=== TEST 1 : INSERT + READ ===");
        M_Proposer p = new M_Proposer(base, idA, idM, idG, localisation, prix, commentaire, true);
        System.out.println("Nouvelle proposition : " + p);

        // ===========================
        // TEST 2 : READ (sans insert)
        // ===========================
        System.out.println("\n=== TEST 2 : READ (sans insert) ===");
        M_Proposer p2 = new M_Proposer(base, idA, idM, idG);
        System.out.println("Proposition lue : " + p2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        p2.setLocalisation("Rayon modifié");
        p2.setPrix(9.99f);
        p2.setCommentaire("commentaire modifié");
        p2.update();
        M_Proposer p3 = new M_Proposer(base, idA, idM, idG);
        System.out.println("Après update : " + p3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<String, M_Proposer> tous = M_Proposer.getRecords(base);
        System.out.println("Nombre de propositions : " + tous.size());
        int c = 0;
        for (String key : tous.keySet()) {
            System.out.println("  " + key + " -> " + tous.get(key));
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        LinkedHashMap<String, M_Proposer> filtrees = M_Proposer.getRecords(base, "prix > ?", 1.0f);
        System.out.println("Propositions avec prix > 1.0 : " + filtrees.size() + " résultat(s)");
        c = 0;
        for (String key : filtrees.keySet()) {
            System.out.println("  " + key + " -> " + filtrees.get(key));
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 6 : getLignesOffres (tous)
        // ===========================
        System.out.println("\n=== TEST 6 : getLignesOffres (tous) ===");
        LinkedHashMap<Integer, Object[]> lignes = M_Proposer.getLignesOffres(base);
        System.out.println("Nombre de lignes offres : " + lignes.size());
        c = 0;
        for (Integer i : lignes.keySet()) {
            Object[] row = lignes.get(i);
            System.out.println("  article=" + row[1] + " | marque=" + row[3] + " | magasin=" + row[5] + " | prix=" + row[6]);
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 7 : getLignesOffres avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 7 : getLignesOffres avec filtre ===");
        LinkedHashMap<Integer, Object[]> lignesFiltrees = M_Proposer.getLignesOffres(base, "P.prix > ?", 2.0f);
        System.out.println("Lignes avec prix > 2.0 : " + lignesFiltrees.size() + " résultat(s)");
        c = 0;
        for (Integer i : lignesFiltrees.keySet()) {
            Object[] row = lignesFiltrees.get(i);
            System.out.println("  article=" + row[1] + " | marque=" + row[3] + " | magasin=" + row[5] + " | prix=" + row[6]);
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 8 : DELETE
        // ===========================
        System.out.println("\n=== TEST 8 : DELETE ===");
        System.out.println("Suppression de : " + p3);
        p3.delete();
        System.out.println("Proposition supprimée !");

        // ===========================
        // TEST 9 : Vérification après DELETE
        // ===========================
        System.out.println("\n=== TEST 9 : Vérification après DELETE ===");
        try {
            M_Proposer p4 = new M_Proposer(base, idA, idM, idG);
            System.out.println(p4);
        } catch (SQLException ex) {
            System.out.println("OK (attendu) : " + ex.getMessage());
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
