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

public class M_Utiliser {

    private Db_mariadb db;

    private int idBeneficiaire;
    private int idRangement;
    private int ordre;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // -------------------------
    // Constructeur lecture + insert optionnel
    // -------------------------
    public M_Utiliser(
            Db_mariadb db,
            int idBeneficiaire,
            int idRangement,
            int ordre,
            String commentaire,
            boolean insert
    ) throws SQLException {

        this.db = db;
        this.idBeneficiaire = idBeneficiaire;
        this.idRangement = idRangement;
        this.ordre = ordre;
        this.commentaire = commentaire;

        if (insert) {
            String sql = "INSERT INTO mcd_utiliser (id_beneficiaire, id_rangement, ordre, commentaire) VALUES (?, ?, ?, ?)";
            db.sqlExec(sql, idBeneficiaire, idRangement, ordre, commentaire);
        }

        // Lecture systématique
        String sql = "SELECT * FROM mcd_utiliser WHERE id_beneficiaire = ? AND id_rangement = ?";
        ResultSet res = db.sqlSelect(sql, idBeneficiaire, idRangement);

        if (!res.next()) {
            res.close();
            throw new SQLException("Aucun enregistrement mcd_utiliser pour ("
                    + idBeneficiaire + ", " + idRangement + ")");
        }

        this.ordre = res.getInt("ordre");
        this.commentaire = res.getString("commentaire");
        this.createdAt = res.getObject("created_at", LocalDateTime.class);
        this.updatedAt = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Constructeur SELECT par IDs
    // -------------------------
    public M_Utiliser(Db_mariadb db, int idBeneficiaire, int idRangement) throws SQLException {
        this.db = db;
        this.idBeneficiaire = idBeneficiaire;
        this.idRangement = idRangement;

        String sql = "SELECT * FROM mcd_utiliser WHERE id_beneficiaire = ? AND id_rangement = ?";
        ResultSet res = db.sqlSelect(sql, idBeneficiaire, idRangement);
        res.first();

        this.ordre = res.getInt("ordre");
        this.commentaire = res.getString("commentaire");
        this.createdAt = res.getObject("created_at", LocalDateTime.class);
        this.updatedAt = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdBeneficiaire() {
        return idBeneficiaire;
    }

    public int getIdRangement() {
        return idRangement;
    }

    public int getOrdre() {
        return ordre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_utiliser SET ordre = ?, commentaire = ? WHERE id_beneficiaire = ? AND id_rangement = ?";
        db.sqlExec(sql, ordre, commentaire, idBeneficiaire, idRangement);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_utiliser WHERE id_beneficiaire = ? AND id_rangement = ?";
        db.sqlExec(sql, idBeneficiaire, idRangement);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<String, M_Utiliser> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<String, M_Utiliser> lesUtiliser = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_utiliser WHERE " + clauseWhere
                + " ORDER BY id_beneficiaire, id_rangement";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int idB = res.getInt("id_beneficiaire");
            int idR = res.getInt("id_rangement");

            M_Utiliser un = new M_Utiliser(
                    db,
                    idB,
                    idR,
                    res.getInt("ordre"),
                    res.getString("commentaire"),
                    false
            );

            lesUtiliser.put(idB + "-" + idR, un);
        }

        res.close();
        return lesUtiliser;
    }

    public static LinkedHashMap<String, M_Utiliser> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Utiliser{idBeneficiaire=" + idBeneficiaire
                + ", idRangement=" + idRangement
                + ", ordre=" + ordre
                + ", commentaire='" + commentaire + "'}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // IDs existants pour les FK
        int idB = 26;
        int idR = 3;

        // ===========================
        // TEST 1 : INSERT + READ
        // ===========================
        System.out.println("=== TEST 1 : INSERT + READ ===");
        M_Utiliser u1 = new M_Utiliser(base, idB, idR, 1, "Cuisine", true);
        System.out.println("Nouvelle utilisation : " + u1);

        // ===========================
        // TEST 2 : READ (sans insert)
        // ===========================
        System.out.println("\n=== TEST 2 : READ (sans insert) ===");
        M_Utiliser u2 = new M_Utiliser(base, idB, idR);
        System.out.println("Utilisation lue : " + u2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        u2.setOrdre(2);
        u2.setCommentaire("Placard haut");
        u2.update();
        M_Utiliser u3 = new M_Utiliser(base, idB, idR);
        System.out.println("Après update : " + u3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<String, M_Utiliser> tous = M_Utiliser.getRecords(base);
        System.out.println("Nombre d'utilisations : " + tous.size());
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
        LinkedHashMap<String, M_Utiliser> filtres = M_Utiliser.getRecords(base, "ordre > ?", 0);
        System.out.println("Utilisations avec ordre > 0 : " + filtres.size() + " résultat(s)");
        c = 0;
        for (String key : filtres.keySet()) {
            System.out.println("  " + key + " -> " + filtres.get(key));
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + u3);
        u3.delete();
        System.out.println("Utilisation supprimée !");

        // ===========================
        // TEST 7 : Vérification après DELETE
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après DELETE ===");
        try {
            M_Utiliser u4 = new M_Utiliser(base, idB, idR);
            System.out.println(u4);
        } catch (SQLException ex) {
            System.out.println("OK (attendu) : " + ex.getMessage());
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
