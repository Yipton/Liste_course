/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author ninis
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

    /* =================================================
       Constructeur UNIQUE : lecture + insert optionnel
       ================================================= */
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
            String sql = "INSERT INTO mcd_proposer (id_article, id_marque, id_magasin, localisation, prix, commentaire) VALUES ("
                    + id_article + ", "
                    + id_marque + ", "
                    + id_magasin + ", "
                    + "'" + localisation + "', "
                    + prix + ", "
                    + "'" + commentaire + "');";
            db.sqlExec(sql);
        }
        // === Lecture systématique ===
        String sql = "SELECT * FROM mcd_proposer WHERE id_article = " + id_article + 
                " AND id_marque = " + id_marque+
                " AND id_magasin = " + id_magasin + ";";

        ResultSet res = db.sqlSelect(sql);

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

    /* =================================================
       Constructeur select by ids
       ================================================= */

    public M_Proposer(Db_mariadb db, int id_article, int id_marque, int id_magasin) throws SQLException {
        this.db = db;
        this.id_article = id_article;
        this.id_marque = id_marque;
        this.id_magasin = id_magasin;

        String sql = "SELECT * FROM mcd_proposer WHERE id_article =" + id_article +
                " AND id_marque =" + id_marque + 
                " AND id_magasin =" + id_magasin + ";";

        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();

        this.localisation = res.getString("localisation");
        this.commentaire = res.getString("commentaire");
        this.prix = res.getFloat("prix");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        res.close();
    }

    /* =======================
       UPDATE
       ======================= */
    public void update() throws SQLException {
        String sql = "UPDATE mcd_proposer SET "
                + "localisation = '" + localisation + "', "
                + "prix = " + prix + ", "
                + "commentaire = '" + commentaire + "' "
                + "WHERE id_article = " + id_article
                + " AND id_marque = " + id_marque
                + " AND id_magasin = " + id_magasin + ";";

        db.sqlExec(sql);
    }

    /* =======================
       DELETE
       ======================= */
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_proposer WHERE id_article = " + id_article
                + " AND id_marque = " + id_marque
                + " AND id_magasin = " + id_magasin + ";";
        db.sqlExec(sql);
    }

    /* =======================
       GET RECORDS
       ======================= */
    public static LinkedHashMap<String, M_Proposer> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {

        LinkedHashMap<String, M_Proposer> lesProposer = new LinkedHashMap<>();
        M_Proposer uneProposition;

        String sql = "SELECT * FROM mcd_proposer WHERE " + clauseWhere
                + " ORDER BY id_magasin, id_article, id_marque;";

        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int id_article = res.getInt("id_article");
            int id_marque = res.getInt("id_marque");
            int id_magasin = res.getInt("id_magasin");

            uneProposition = new M_Proposer(
                    db,
                    id_article,
                    id_marque,
                    id_magasin,
                    res.getString("localisation"),
                    res.getFloat("prix"),
                    res.getString("commentaire"),
                    false // lecture uniquement
            );

            lesProposer.put(id_article + "-" + id_marque + "-" + id_magasin, uneProposition);
        }

        res.close();
        return lesProposer;
    }

    public static LinkedHashMap<String, M_Proposer> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1=1");
    }

    /* =======================
       GETTERS / SETTERS
       ======================= */
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

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "M_Proposer{"
                + "id_article=" + id_article
                + ", id_marque=" + id_marque
                + ", id_magasin=" + id_magasin
                + ", localisation=" + localisation
                + ", prix=" + prix
                + ", commentaire=" + commentaire
                + "}";
    }

    public static LinkedHashMap<Integer, Object[]> getLignesOffres(Db_mariadb db, String where) throws SQLException {

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
                + "WHERE " + where + " "
                + "ORDER BY A.nom, MA.nom, MG.nom;";

        ResultSet res = db.sqlSelect(sql);

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
        return getLignesOffres(db, "1=1");
    }
// ---------------- TESTS ----------------
//public static void main(String[] args) throws Exception {
//
//    Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
//
//    // ⚠️ Mettre ici des IDs qui existent VRAIMENT dans la base
//    // Et choisir une combinaison qui n'existe pas encore pour tester l'insert.
//    int idA = 1;        // article existant
//    int idM = 2;        // marque existante
//    int idG = 2;        // magasin existant
//
//    String localisation = "Rayon test";
//    float prix = 1.23;
//    String commentaire = "test proposer";
//
//    System.out.println("=== INSERT + READ ===");
//    M_Proposer p = new M_Proposer(base, idA, idM, idG, localisation, prix, commentaire, true);
//    System.out.println(p);
//
//    System.out.println("\n=== READ (sans insert) ===");
//    M_Proposer p2 = new M_Proposer(base, idA, idM, idG, "IGNORED", 0.0, "IGNORED", false);
//    System.out.println(p2);
//
//    System.out.println("\n=== UPDATE ===");
//    p2.setLocalisation("Rayon modifié");
//    p2.setPrix(9.99);
//    p2.setCommentaire("commentaire modifié");
//    p2.update();
//
//    // Relire pour vérifier la modif
//    M_Proposer p3 = new M_Proposer(base, idA, idM, idG, "IGNORED", 0.0, "IGNORED", false);
//    System.out.println(p3);
//
//    System.out.println("\n=== GET RECORDS (1=1) ===");
//    LinkedHashMap<String, M_Proposer> all = M_Proposer.getRecords(base);
//    System.out.println("Nb proposer = " + all.size());
//    // Affiche juste quelques lignes
//    int c = 0;
//    for (String key : all.keySet()) {
//        System.out.println(key + " -> " + all.get(key));
//        c++;
//        if (c >= 5) break;
//    }
//
//    System.out.println("\n=== GET LIGNES OFFRES (1=1) ===");
//    LinkedHashMap<Integer, Object[]> lignes = M_Proposer.getLignesOffres(base);
//    System.out.println("Nb lignes offres = " + lignes.size());
//    c = 0;
//    for (Integer i : lignes.keySet()) {
//        Object[] row = lignes.get(i);
//        System.out.println(
//            "i=" + i
//            + " | id_article=" + row[0] + " article=" + row[1]
//            + " | id_marque=" + row[2] + " marque=" + row[3]
//            + " | id_magasin=" + row[4] + " magasin=" + row[5]
//            + " | prix=" + row[6]
//            + " | localisation=" + row[7]
//        );
//        c++;
//        if (c >= 5) break;
//    }
//
//    System.out.println("\n=== DELETE ===");
//    p3.delete();
//    System.out.println("Supprimé.");
//
//    System.out.println("\n=== READ après DELETE (doit échouer) ===");
//    try {
//        M_Proposer p4 = new M_Proposer(base, idA, idM, idG, "IGNORED", 0.0, "IGNORED", false);
//        System.out.println(p4); // normalement jamais
//    } catch (SQLException ex) {
//        System.out.println("OK (attendu) : " + ex.getMessage());
//    }
//
//    System.out.println("\n=== FIN TESTS ===");
//}

}
