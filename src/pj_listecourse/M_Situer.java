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

public class M_Situer {

    private Db_mariadb db;

    private int idRangement;
    private int idArticle;
    private int ordre;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* =================================================
       Constructeur UNIQUE : lecture + insert optionnel
       ================================================= */
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
            String sql = "INSERT INTO mcd_situer (id_rangement, id_article, ordre, commentaire) VALUES ("
                    + idRangement + ", "
                    + idArticle + ", "
                    + ordre + ", "
                    + "'" + commentaire + "');";
            db.sqlExec(sql);
        }

        // === Lecture systématique ===
        String sql = "SELECT * FROM mcd_situer WHERE id_rangement = " + idRangement
                + " AND id_article = " + idArticle + ";";

        ResultSet res = db.sqlSelect(sql);

        if (!res.next()) {
            res.close();
            throw new SQLException("Aucun enregistrement mcd_situer pour (id_rangement="
                    + idRangement + ", id_article=" + idArticle + ")");
        }

        this.ordre = res.getInt("ordre"); // 0 si NULL
        this.commentaire = res.getString("commentaire");
        this.createdAt = res.getObject("created_at", LocalDateTime.class);
        this.updatedAt = res.getObject("updated_at", LocalDateTime.class);

        res.close();
    }

    /* =======================
       UPDATE
       ======================= */
    public void update() throws SQLException {
        String sql = "UPDATE mcd_situer SET "
                + "ordre = " + ordre + ", "
                + "commentaire = '" + commentaire + "' "
                + "WHERE id_rangement = " + idRangement
                + " AND id_article = " + idArticle + ";";

        db.sqlExec(sql);
    }

    /* =======================
       DELETE
       ======================= */
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_situer WHERE id_rangement = " + idRangement
                + " AND id_article = " + idArticle + ";";
        db.sqlExec(sql);
    }

    /* =======================
       GET RECORDS
       ======================= */
    public static LinkedHashMap<String, M_Situer> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {

        LinkedHashMap<String, M_Situer> lesSituer = new LinkedHashMap<>();
        M_Situer un;

        String sql = "SELECT * FROM mcd_situer WHERE " + clauseWhere
                + " ORDER BY id_rangement, id_article;";

        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int idR = res.getInt("id_rangement");
            int idA = res.getInt("id_article");

            un = new M_Situer(
                    db,
                    idR,
                    idA,
                    res.getInt("ordre"),
                    res.getString("commentaire"),
                    false // lecture uniquement
            );

            lesSituer.put(idR + "-" + idA, un);
        }

        res.close();
        return lesSituer;
    }

    public static LinkedHashMap<String, M_Situer> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1=1");
    }

    /* =======================
       GETTERS / SETTERS
       ======================= */
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

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "M_Situer{"
                + "idRangement=" + idRangement
                + ", idArticle=" + idArticle
                + ", ordre=" + ordre
                + ", commentaire=" + commentaire
                + "}";
    }

    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        // INSERT
        M_Situer situer1 = new M_Situer(
                base,
                3, // idRangement (doit exister)
                12, // idArticle   (doit exister)
                1, // ordre
                "Étagère du haut",
                true
        );
        System.out.println("INSERT OK : " + situer1);

        // LECTURE
        M_Situer situer2 = new M_Situer(
                base,
                3,
                12,
                0,
                "",
                false
        );
        System.out.println("LECTURE OK : " + situer2);

        // UPDATE
        situer2.setOrdre(2);
        situer2.setCommentaire("Deuxième étagère");
        situer2.update();
        System.out.println("UPDATE OK");

        // RELECTURE
        M_Situer situer3 = new M_Situer(
                base,
                3,
                12,
                0,
                "",
                false
        );
        System.out.println("RELECTURE : " + situer3);

        // GET RECORDS
        System.out.println("LISTE COMPLETE :");
        LinkedHashMap<String, M_Situer> lesSituer = M_Situer.getRecords(base);
        for (String cle : lesSituer.keySet()) {
            System.out.println(lesSituer.get(cle));
        }

        // DELETE
        situer3.delete();
        System.out.println("DELETE OK");
    }
}
