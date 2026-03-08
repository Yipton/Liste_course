/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author Yipton
 */
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Magasins {

    private Db_mariadb db;
    private int id;
    private String nom;
    private String commentaire;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
    public M_Magasins(Db_mariadb db, int id, String nom, String commentaire) {
        this.db = db;
        this.id = id;
        this.nom = nom;
        this.commentaire = commentaire;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Magasins(Db_mariadb db, String nom, String commentaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.commentaire = commentaire;

        String sql = "INSERT INTO mcd_magasins (nom, commentaire) VALUES (?, ?)";
        db.sqlExec(sql, nom, commentaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Magasins(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;

        String sql = "SELECT * FROM mcd_magasins WHERE id = ?";
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
        String sql = "UPDATE mcd_magasins SET nom = ?, commentaire = ? WHERE id = ?";
        db.sqlExec(sql, nom, commentaire, id);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_magasins WHERE id = ?";
        db.sqlExec(sql, id);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Magasins> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<Integer, M_Magasins> lesMagasins = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_magasins WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            String nom = res.getString("nom");
            String commentaire = res.getString("commentaire");

            M_Magasins unMagasin = new M_Magasins(db, cle, nom, commentaire);
            lesMagasins.put(cle, unMagasin);
        }
        res.close();
        return lesMagasins;
    }

    public static LinkedHashMap<Integer, M_Magasins> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Magasins{id=" + id + ", nom='" + nom + "', commentaire='" + commentaire + "'}";
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
        M_Magasins m1 = new M_Magasins(base, 1);
        System.out.println(m1);

        // ===========================
        // TEST 2 : INSERT
        // ===========================
        System.out.println("\n=== TEST 2 : INSERT ===");
        M_Magasins m2 = new M_Magasins(base, "Magasin test", "Test insertion");
        System.out.println("Nouveau magasin créé : " + m2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        m2.setNom("Magasin test modifié");
        m2.setCommentaire("Commentaire modifié");
        m2.update();
        System.out.println("Après update : " + m2);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Magasins> tous = M_Magasins.getRecords(base);
        System.out.println("Nombre de magasins : " + tous.size());
        for (M_Magasins m : tous.values()) {
            System.out.println("  " + m);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        String recherche = "test";
        LinkedHashMap<Integer, M_Magasins> filtres = M_Magasins.getRecords(base, "nom LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtres.size() + " résultat(s)");
        for (M_Magasins m : filtres.values()) {
            System.out.println("  " + m);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + m2);
        m2.delete();
        System.out.println("Magasin supprimé !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Magasins> restants = M_Magasins.getRecords(base);
        System.out.println("Nombre de magasins restants : " + restants.size());
        for (M_Magasins m : restants.values()) {
            System.out.println("  " + m);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}