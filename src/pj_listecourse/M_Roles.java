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
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

public class M_Roles {

    private Db_mariadb db;
    private int id;
    private String code, nom, commentaire;
    private LocalDateTime updated_at, created_at;

    // -------------------------
    // Constructeur lecture directe (tous les champs)
    // -------------------------
    public M_Roles(Db_mariadb db, int id, String code, String nom, String commentaire, LocalDateTime updated_at, LocalDateTime created_at) {
        this.db = db;
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.commentaire = commentaire;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Roles(Db_mariadb db, String code, String nom, String commentaire) throws SQLException {
        this.db = db;
        this.code = code;
        this.nom = nom;
        this.commentaire = commentaire;

        String sql = "INSERT INTO mcd_roles (code, nom, commentaire) VALUES (?, ?, ?)";
        db.sqlExec(sql, code, nom, commentaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();

        sql = "SELECT * FROM mcd_roles WHERE id = ?";
        res = db.sqlSelect(sql, id);
        res.first();

        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Roles(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;

        String sql = "SELECT * FROM mcd_roles WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, id);

        res.first();
        this.code = res.getString("code");
        this.nom = res.getString("nom");
        this.commentaire = res.getString("commentaire");
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        this.created_at = res.getObject("created_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public Db_mariadb getDb() {
        return db;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setDb(Db_mariadb db) {
        this.db = db;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_roles SET code = ?, nom = ?, commentaire = ? WHERE id = ?";
        db.sqlExec(sql, code, nom, commentaire, id);

        sql = "SELECT * FROM mcd_roles WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, id);

        res.first();
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        res.close();
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_roles WHERE id = ?";
        db.sqlExec(sql, id);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Roles> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {
        LinkedHashMap<Integer, M_Roles> lesRoles = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_roles WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            String code = res.getString("code");
            String nom = res.getString("nom");
            String commentaire = res.getString("commentaire");
            LocalDateTime updated_at = res.getObject("updated_at", LocalDateTime.class);
            LocalDateTime created_at = res.getObject("created_at", LocalDateTime.class);

            M_Roles unRole = new M_Roles(db, cle, code, nom, commentaire, updated_at, created_at);
            lesRoles.put(cle, unRole);
        }
        res.close();
        return lesRoles;
    }

    public static LinkedHashMap<Integer, M_Roles> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Roles{id=" + id + ", code='" + code + "', nom='" + nom + "', commentaire='" + commentaire + "', created_at='" + created_at + "', updated_at='" + updated_at + "'}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // ===========================
        // TEST 1 : SELECT par ID
        // ===========================
        System.out.println("=== TEST 1 : SELECT par ID ===");
        M_Roles role1 = new M_Roles(base, 1);
        System.out.println(role1);

        // ===========================
        // TEST 2 : INSERT
        // ===========================
        System.out.println("\n=== TEST 2 : INSERT ===");
        M_Roles role2 = new M_Roles(base, "TEST", "Je suis un test", "Ceci est un test");
        System.out.println("Nouveau rôle créé : " + role2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        role2.setCode("TEST_MODIF");
        role2.setNom("Nom modifié");
        role2.setCommentaire("Commentaire modifié");
        role2.update();
        System.out.println("Après update : " + role2);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Roles> tousLesRoles = M_Roles.getRecords(base);
        System.out.println("Nombre de rôles : " + tousLesRoles.size());
        for (M_Roles r : tousLesRoles.values()) {
            System.out.println("  " + r);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre (hybride sécurisé) ===");
        String recherche = "TEST";
        LinkedHashMap<Integer, M_Roles> filtres = M_Roles.getRecords(base, "code LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtres.size() + " résultat(s)");
        for (M_Roles r : filtres.values()) {
            System.out.println("  " + r);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + role2);
        role2.delete();
        System.out.println("Rôle supprimé !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Roles> restants = M_Roles.getRecords(base);
        System.out.println("Nombre de rôles restants : " + restants.size());
        for (M_Roles r : restants.values()) {
            System.out.println("  " + r);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
