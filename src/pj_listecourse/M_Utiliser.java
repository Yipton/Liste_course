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
import pj_listecourse.Cl_Connection;
import pj_listecourse.Db_mariadb;

public class M_Utiliser {

    private Db_mariadb db;

    private int idBeneficiaire;
    private int idRangement;
    private int ordre;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* =================================================
       Constructeur UNIQUE : lecture + insert optionnel
       ================================================= */
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
            String sql = "INSERT INTO mcd_utiliser (id_beneficiaire, id_rangement, ordre, commentaire) VALUES ("
                    + idBeneficiaire + ", "
                    + idRangement + ", "
                    + ordre + ", "
                    + "'" + commentaire + "');";

            db.sqlExec(sql);
        }

        // === Lecture systématique ===
        String sql = "SELECT * FROM mcd_utiliser WHERE id_beneficiaire = " + idBeneficiaire
                + " AND id_rangement = " + idRangement + ";";

        ResultSet res = db.sqlSelect(sql);

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

    /* =======================
       UPDATE
       ======================= */
    public void update() throws SQLException {
        String sql = "UPDATE mcd_utiliser SET "
                + "ordre = " + ordre + ", "
                + "commentaire = '" + commentaire + "' "
                + "WHERE id_beneficiaire = " + idBeneficiaire
                + " AND id_rangement = " + idRangement + ";";

        db.sqlExec(sql);
    }

    /* =======================
       DELETE
       ======================= */
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_utiliser WHERE id_beneficiaire = " + idBeneficiaire
                + " AND id_rangement = " + idRangement + ";";
        db.sqlExec(sql);
    }

    /* =======================
       GET RECORDS
       ======================= */
    public static LinkedHashMap<String, M_Utiliser> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {

        LinkedHashMap<String, M_Utiliser> lesUtiliser = new LinkedHashMap<>();
        M_Utiliser un;

        String sql = "SELECT * FROM mcd_utiliser WHERE " + clauseWhere
                + " ORDER BY id_beneficiaire, id_rangement;";

        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int idB = res.getInt("id_beneficiaire");
            int idR = res.getInt("id_rangement");

            un = new M_Utiliser(
                    db,
                    idB,
                    idR,
                    res.getInt("ordre"),
                    res.getString("commentaire"),
                    false // ⚠️ lecture uniquement
            );

            lesUtiliser.put(idB + "-" + idR, un);
        }

        res.close();
        return lesUtiliser;
    }

    public static LinkedHashMap<String, M_Utiliser> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1=1");
    }

    /* =======================
       GETTERS / SETTERS
       ======================= */
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

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "M_Utiliser{"
                + "idBeneficiaire=" + idBeneficiaire
                + ", idRangement=" + idRangement
                + ", ordre=" + ordre
                + ", commentaire=" + commentaire
                + "}";
    }

    // ---------------- TESTS ----------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // INSERT
        M_Utiliser utiliser1 = new M_Utiliser(
                base,
                26, // idBeneficiaire (doit exister dans mcd_users)
                3, // idRangement   (doit exister dans mcd_rangements)
                1, // ordre
                "Cuisine",
                true
        );
        System.out.println("INSERT OK : " + utiliser1);

        // LECTURE (sans insert)
        M_Utiliser utiliser2 = new M_Utiliser(
                base,
                26,
                3,
                0,
                "",
                false
        );
        System.out.println("LECTURE OK : " + utiliser2);

        // UPDATE
        utiliser2.setOrdre(2);
        utiliser2.setCommentaire("Placard haut");
        utiliser2.update();
        System.out.println("UPDATE OK");

        // RELECTURE
        M_Utiliser utiliser3 = new M_Utiliser(
                base,
                26,
                3,
                0,
                "",
                false
        );
        System.out.println("RELECTURE : " + utiliser3);

        // GET RECORDS
        System.out.println("LISTE COMPLETE :");
        LinkedHashMap<String, M_Utiliser> lesUtiliser = M_Utiliser.getRecords(base);
        for (String cle : lesUtiliser.keySet()) {
            System.out.println(lesUtiliser.get(cle));
        }

        // DELETE
        utiliser3.delete();
        System.out.println("DELETE OK");

    }
}
