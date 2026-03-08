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

public class M_Situer {

    private Db_mariadb db;

    private int idRangement;
    private int idArticle;
    private int ordre;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // -------------------------
    // Constructeur lecture + insert optionnel
    // -------------------------
    public M_Situer(
            Db_mariadb db,
            int idRangement,
            int idArticle,
            int ordre,
            String commentaire,
            boolean insert
    ) throws SQLException {

        this.db = db;
        this.idRangement = idRangement;
        this.idArticle = idArticle;
        this.ordre = ordre;
        this.commentaire = commentaire;

        if (insert) {
            String sql = "INSERT INTO mcd_situer (id_rangement, id_article, ordre, commentaire) VALUES (?, ?, ?, ?)";
            db.sqlExec(sql, idRangement, idArticle, ordre, commentaire);
        }

        // Lecture systématique
        String sql = "SELECT * FROM mcd_situer WHERE id_rangement = ? AND id_article = ?";
        ResultSet res = db.sqlSelect(sql, idRangement, idArticle);

        if (!res.next()) {
            res.close();
            throw new SQLException("Aucun enregistrement mcd_situer pour (id_rangement="
                    + idRangement + ", id_article=" + idArticle + ")");
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
    public M_Situer(Db_mariadb db, int idRangement, int idArticle) throws SQLException {
        this.db = db;
        this.idRangement = idRangement;
        this.idArticle = idArticle;

        String sql = "SELECT * FROM mcd_situer WHERE id_rangement = ? AND id_article = ?";
        ResultSet res = db.sqlSelect(sql, idRangement, idArticle);
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
    public int getIdRangement() {
        return idRangement;
    }

    public int getIdArticle() {
        return idArticle;
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
        String sql = "UPDATE mcd_situer SET ordre = ?, commentaire = ? WHERE id_rangement = ? AND id_article = ?";
        db.sqlExec(sql, ordre, commentaire, idRangement, idArticle);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_situer WHERE id_rangement = ? AND id_article = ?";
        db.sqlExec(sql, idRangement, idArticle);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<String, M_Situer> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<String, M_Situer> lesSituer = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_situer WHERE " + clauseWhere
                + " ORDER BY id_rangement, id_article";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int idR = res.getInt("id_rangement");
            int idA = res.getInt("id_article");

            M_Situer un = new M_Situer(
                    db,
                    idR,
                    idA,
                    res.getInt("ordre"),
                    res.getString("commentaire"),
                    false
            );

            lesSituer.put(idR + "-" + idA, un);
        }

        res.close();
        return lesSituer;
    }

    public static LinkedHashMap<String, M_Situer> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Situer{idRangement=" + idRangement
                + ", idArticle=" + idArticle
                + ", ordre=" + ordre
                + ", commentaire='" + commentaire + "'}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // IDs existants pour les FK
        int idR = 3;
        int idA = 12;

        // ===========================
        // TEST 1 : INSERT + READ
        // ===========================
        System.out.println("=== TEST 1 : INSERT + READ ===");
        M_Situer s1 = new M_Situer(base, idR, idA, 1, "Étagère du haut", true);
        System.out.println("Nouvelle situation : " + s1);

        // ===========================
        // TEST 2 : READ (sans insert)
        // ===========================
        System.out.println("\n=== TEST 2 : READ (sans insert) ===");
        M_Situer s2 = new M_Situer(base, idR, idA);
        System.out.println("Situation lue : " + s2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        s2.setOrdre(2);
        s2.setCommentaire("Deuxième étagère");
        s2.update();
        M_Situer s3 = new M_Situer(base, idR, idA);
        System.out.println("Après update : " + s3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<String, M_Situer> tous = M_Situer.getRecords(base);
        System.out.println("Nombre de situations : " + tous.size());
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
        LinkedHashMap<String, M_Situer> filtres = M_Situer.getRecords(base, "ordre > ?", 0);
        System.out.println("Situations avec ordre > 0 : " + filtres.size() + " résultat(s)");
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
        System.out.println("Suppression de : " + s3);
        s3.delete();
        System.out.println("Situation supprimée !");

        // ===========================
        // TEST 7 : Vérification après DELETE
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après DELETE ===");
        try {
            M_Situer s4 = new M_Situer(base, idR, idA);
            System.out.println(s4);
        } catch (SQLException ex) {
            System.out.println("OK (attendu) : " + ex.getMessage());
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
