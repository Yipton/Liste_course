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

public class M_Rangements {

    private Db_mariadb db;
    private int id;
    private String nom;
    private String commentaire;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
    public M_Rangements(Db_mariadb db, int id, String nom, String commentaire) {
        this.db = db;
        this.id = id;
        this.nom = nom;
        this.commentaire = commentaire;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Rangements(Db_mariadb db, String nom, String commentaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.commentaire = commentaire;

        String sql = "INSERT INTO mcd_rangements (nom, commentaire) VALUES (?, ?)";
        db.sqlExec(sql, nom, commentaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Rangements(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;

        String sql = "SELECT * FROM mcd_rangements WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, id);
        res.first();

        this.nom = res.getString("nom");
        this.commentaire = res.getString("commentaire");

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
        String sql = "UPDATE mcd_rangements SET nom = ?, commentaire = ? WHERE id = ?";
        db.sqlExec(sql, nom, commentaire, id);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_rangements WHERE id = ?";
        db.sqlExec(sql, id);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Rangements> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<Integer, M_Rangements> lesRangements = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_rangements WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            String nom = res.getString("nom");
            String commentaire = res.getString("commentaire");

            M_Rangements unRangement = new M_Rangements(db, cle, nom, commentaire);
            lesRangements.put(cle, unRangement);
        }
        res.close();
        return lesRangements;
    }

    public static LinkedHashMap<Integer, M_Rangements> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Rangements{id=" + id + ", nom='" + nom + "', commentaire='" + commentaire + "'}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(
                Cl_Connection.url,
                Cl_Connection.login,
                Cl_Connection.password
        );

        // ===========================
        // TEST 1 : SELECT par ID
        // ===========================
        System.out.println("=== TEST 1 : SELECT par ID ===");
        M_Rangements r1 = new M_Rangements(base, 1);
        System.out.println(r1);

        // ===========================
        // TEST 2 : INSERT
        // ===========================
        System.out.println("\n=== TEST 2 : INSERT ===");
        M_Rangements r2 = new M_Rangements(base, "Rangement test", "Test insertion");
        System.out.println("Nouveau rangement créé : " + r2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        r2.setNom("Rangement test modifié");
        r2.setCommentaire("Commentaire modifié");
        r2.update();
        System.out.println("Après update : " + r2);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Rangements> tous = M_Rangements.getRecords(base);
        System.out.println("Nombre de rangements : " + tous.size());
        for (M_Rangements r : tous.values()) {
            System.out.println("  " + r);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        String recherche = "test";
        LinkedHashMap<Integer, M_Rangements> filtres = M_Rangements.getRecords(base, "nom LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtres.size() + " résultat(s)");
        for (M_Rangements r : filtres.values()) {
            System.out.println("  " + r);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + r2);
        r2.delete();
        System.out.println("Rangement supprimé !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Rangements> restants = M_Rangements.getRecords(base);
        System.out.println("Nombre de rangements restants : " + restants.size());
        for (M_Rangements r : restants.values()) {
            System.out.println("  " + r);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}