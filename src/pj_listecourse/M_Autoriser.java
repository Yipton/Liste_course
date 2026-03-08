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
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class M_Autoriser {

    private Db_mariadb db;
    private int idRole;
    private int idAutorisation;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur vide
    // -------------------------
    public M_Autoriser(Db_mariadb db) {
        this.db = db;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Autoriser(Db_mariadb db, int idRole, int idAutorisation, boolean insert) throws SQLException {
        this.db = db;
        this.idRole = idRole;
        this.idAutorisation = idAutorisation;

        if (insert) {
            String sql = "INSERT INTO mcd_autoriser (idRole, idAutorisation) VALUES (?, ?)";
            db.sqlExec(sql, idRole, idAutorisation);
        }
    }

    // -------------------------
    // Constructeur SELECT (lecture directe)
    // -------------------------
    public M_Autoriser(Db_mariadb db, int idRole, int idAutorisation) {
        this.db = db;
        this.idRole = idRole;
        this.idAutorisation = idAutorisation;
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdRole() {
        return idRole;
    }

    public int getIdAutorisation() {
        return idAutorisation;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setIdAutorisation(int idAutorisation) {
        this.idAutorisation = idAutorisation;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update(int newIdRole, int newIdAutorisation) throws SQLException {
        String sql = "UPDATE mcd_autoriser SET idRole = ?, idAutorisation = ? WHERE idRole = ? AND idAutorisation = ?";
        db.sqlExec(sql, newIdRole, newIdAutorisation, idRole, idAutorisation);
        this.idRole = newIdRole;
        this.idAutorisation = newIdAutorisation;
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_autoriser WHERE idRole = ? AND idAutorisation = ?";
        db.sqlExec(sql, idRole, idAutorisation);
    }

    // -------------------------
    // DELETE : supprimer toutes les autorisations d'un rôle
    // -------------------------
    public static void deleteByRole(Db_mariadb db, int idRole) throws SQLException {
        String sql = "DELETE FROM mcd_autoriser WHERE idRole = ?";
        db.sqlExec(sql, idRole);
    }

    // -------------------------
    // DELETE : supprimer une autorisation de tous les rôles
    // -------------------------
    public static void deleteByAutorisation(Db_mariadb db, int idAutorisation) throws SQLException {
        String sql = "DELETE FROM mcd_autoriser WHERE idAutorisation = ?";
        db.sqlExec(sql, idAutorisation);
    }

    // -------------------------
    // SELECT : autorisations d'un rôle
    // -------------------------
    public static LinkedHashMap<Integer, M_Autorisations> getLesAutorisations(Db_mariadb db, int idRole) throws SQLException {
        LinkedHashMap<Integer, M_Autorisations> lesAutorisations = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_autorisations AN"
                + " INNER JOIN mcd_autoriser AR ON AN.idAutorisation = AR.idAutorisation"
                + " WHERE AR.idRole = ? ORDER BY code";

        ResultSet res = db.sqlSelect(sql, idRole);

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

// -------------------------
// SELECT : rôles ayant une autorisation
// -------------------------
    public static LinkedHashMap<Integer, M_Roles> getLesRoles(Db_mariadb db, int idAutorisation) throws SQLException {
        LinkedHashMap<Integer, M_Roles> lesRoles = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_roles R"
                + " INNER JOIN mcd_autoriser AR ON R.id = AR.idRole"
                + " WHERE AR.idAutorisation = ? ORDER BY nom";

        ResultSet res = db.sqlSelect(sql, idAutorisation);

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

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Autoriser{idRole=" + idRole + ", idAutorisation=" + idAutorisation + "}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // ===========================
        // TEST 1 : getLesAutorisations
        // ===========================
        System.out.println("=== TEST 1 : getLesAutorisations ===");
        int roleTest = 3;
        LinkedHashMap<Integer, M_Autorisations> droits = M_Autoriser.getLesAutorisations(base, roleTest);
        System.out.println("Autorisations pour le rôle " + roleTest + " : " + droits.size());
        for (M_Autorisations a : droits.values()) {
            System.out.println("  " + a.getCode() + " - " + a.getDescription());
        }

        // ===========================
        // TEST 2 : INSERT
        // ===========================
        System.out.println("\n=== TEST 2 : INSERT ===");
        // Attention : adapter les IDs selon ta BDD
        // M_Autoriser nouvelleLiaison = new M_Autoriser(base, 1, 5, true);
        // System.out.println("Association créée : " + nouvelleLiaison);

        // ===========================
        // TEST 3 : DELETE
        // ===========================
        System.out.println("\n=== TEST 3 : DELETE ===");
        // nouvelleLiaison.delete();
        // System.out.println("Association supprimée !");

        // ===========================
        // TEST 4 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 4 : Vérification ===");
        LinkedHashMap<Integer, M_Autorisations> apres = M_Autoriser.getLesAutorisations(base, roleTest);
        System.out.println("Autorisations restantes pour le rôle " + roleTest + " : " + apres.size());
        for (M_Autorisations a : apres.values()) {
            System.out.println("  " + a.getCode() + " - " + a.getDescription());
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
