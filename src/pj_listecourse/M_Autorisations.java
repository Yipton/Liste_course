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

public class M_Autorisations {

    private Db_mariadb db;
    private int idAutorisation;
    private String code, description;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
    public M_Autorisations(Db_mariadb db, int idAutorisation, String code, String description) {
        this.db = db;
        this.idAutorisation = idAutorisation;
        this.code = code;
        this.description = description;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Autorisations(Db_mariadb db, String code, String description) throws SQLException {
        this.db = db;
        this.code = code;
        this.description = description;

        String sql = "INSERT INTO mcd_autorisations (code, description) VALUES (?, ?)";
        db.sqlExec(sql, code, description);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.idAutorisation = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Autorisations(Db_mariadb db, int idAutorisation) throws SQLException {
        this.db = db;
        this.idAutorisation = idAutorisation;

        String sql = "SELECT * FROM mcd_autorisations WHERE idAutorisation = ?";

        ResultSet res;
        res = db.sqlSelect(sql, idAutorisation);
        res.first();

        this.code = res.getString("code");
        this.description = res.getString("description");

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdAutorisation() {
        return idAutorisation;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_autorisations SET code= ?, description= ? WHERE idAutorisation = ?";
        db.sqlExec(sql, code, description, idAutorisation);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_autorisations WHERE idAutorisation= ?";
        db.sqlExec(sql, idAutorisation);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Autorisations> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {
        LinkedHashMap<Integer, M_Autorisations> lesAutorisations = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_autorisations WHERE " + clauseWhere;
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("idAutorisation");
            String code = res.getString("code");
            String description = res.getString("description");

            M_Autorisations uneAutorisation = new M_Autorisations(db, cle, code, description);
            lesAutorisations.put(cle, uneAutorisation);
        }
        res.close();
        return lesAutorisations;
    }

    public static LinkedHashMap<Integer, M_Autorisations> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Autorisation{" + "id=" + idAutorisation + ", code='" + code + "', description='" + description + "'}'";
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
        M_Autorisations auth1 = new M_Autorisations(base, 1);
        System.out.println(auth1);

        // ===========================
        // TEST 2 : INSERT
        // ===========================
        System.out.println("\n=== TEST 2 : INSERT ===");
        M_Autorisations auth2 = new M_Autorisations(base, "TEST_CODE", "Ceci est un test");
        System.out.println("Nouvelle autorisation créée : " + auth2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        auth2.setCode("TEST_MODIFIE");
        auth2.setDescription("Description modifiée");
        auth2.update();
        System.out.println("Après update : " + auth2);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Autorisations> toutes = M_Autorisations.getRecords(base);
        System.out.println("Nombre d'autorisations : " + toutes.size());
        for (M_Autorisations a : toutes.values()) {
            System.out.println("  " + a);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre (hybride sécurisé) ===");
        String recherche = "TEST";
        LinkedHashMap<Integer, M_Autorisations> filtrees = M_Autorisations.getRecords(base, "code LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtrees.size() + " résultat(s)");
        for (M_Autorisations a : filtrees.values()) {
            System.out.println("  " + a);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + auth2);
        auth2.delete();
        System.out.println("Autorisation supprimée !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Autorisations> restantes = M_Autorisations.getRecords(base);
        System.out.println("Nombre d'autorisations restantes : " + restantes.size());
        for (M_Autorisations a : restantes.values()) {
            System.out.println("  " + a);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
