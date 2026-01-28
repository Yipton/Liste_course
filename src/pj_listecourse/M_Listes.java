/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

/**
 *
 * @author niniss
 */
public class M_Listes {

    private Db_mariadb db;

    private int idListe;
    private String nom;
    private LocalDate dateAchat;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int idAidant;   // nullable côté SQL
    private int idBeneficiaire; // NOT NULL côté SQL

    /* =======================
       Constructeur "lecture"
       ======================= */
    public M_Listes(Db_mariadb db, int idListe, String nom, LocalDate dateAchat, String commentaire,
                    LocalDateTime createdAt, LocalDateTime updatedAt, Integer idAidant, int idBeneficiaire) {
        this.db = db;
        this.idListe = idListe;
        this.nom = nom;
        this.dateAchat = dateAchat;
        this.commentaire = commentaire;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idAidant = idAidant;
        this.idBeneficiaire = idBeneficiaire;
    }

    /* =======================
       Constructeur INSERT
       ======================= */
    public M_Listes(Db_mariadb db, String nom, LocalDate dateAchat, String commentaire,
                    Integer idAidant, int idBeneficiaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.dateAchat = dateAchat;
        this.commentaire = commentaire;
        this.idAidant = idAidant;
        this.idBeneficiaire = idBeneficiaire;

  
        String sql = "INSERT INTO mcd_listes(nom, date_achat, commentaire, id_aidant, id_beneficiaire) VALUES ("
                + "'" + nom + "', "
                + "'" + dateAchat + "', "
                + "'" + commentaire + "', "
                + idAidant + ", "
                + idBeneficiaire + ");";

        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        res.first();
        this.idListe = res.getInt("id");
        res.close();
    }

    /* =======================
       Constructeur par ID
       ======================= */
    public M_Listes(Db_mariadb db, int idListe) throws SQLException {
        this.db = db;
        this.idListe = idListe;

        String sql = "SELECT * FROM mcd_listes WHERE id = " + idListe + ";";
        ResultSet res = db.sqlSelect(sql);
        res.first();

        this.nom = res.getString("nom");
        this.dateAchat = res.getObject("date_achat", LocalDate.class);
        this.commentaire = res.getString("commentaire");
        this.createdAt = res.getObject("created_at", LocalDateTime.class);
        this.updatedAt = res.getObject("updated_at", LocalDateTime.class);
        this.idAidant = res.getInt("id_aidant");
        this.idBeneficiaire = res.getInt("id_beneficiaire");

        res.close();
    }

    /* =======================
       UPDATE
       ======================= */
    public void update() throws SQLException {
        String sql = "UPDATE mcd_listes SET "
                + "nom = '" + nom + "', "
                + "date_achat = '" + dateAchat + "', "
                + "commentaire = '" + commentaire + "', "
                + "id_aidant = " + idAidant + ", "
                + "id_beneficiaire = " + idBeneficiaire
                + " WHERE id = " + idListe + ";";

        db.sqlExec(sql);
    }

    /* =======================
       DELETE
       ======================= */
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_listes WHERE id = " + idListe + ";";
        db.sqlExec(sql);
    }

    /* =======================
       GET RECORDS
       ======================= */
    public static LinkedHashMap<Integer, M_Listes> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {

        LinkedHashMap<Integer, M_Listes> lesListes = new LinkedHashMap<>();
        M_Listes uneListe;

        String sql = "SELECT * FROM mcd_listes WHERE " + clauseWhere + " ORDER BY nom;";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int cle = res.getInt("id");

            uneListe = new M_Listes(
                    db,
                    cle,
                    res.getString("nom"),
                    res.getObject("date_achat", LocalDate.class),
                    res.getString("commentaire"),
                    res.getObject("created_at", LocalDateTime.class),
                    res.getObject("updated_at", LocalDateTime.class),
                    res.getInt("id_aidant"),
                    res.getInt("id_beneficiaire")
            );

            lesListes.put(cle, uneListe);
        }

        res.close();
        return lesListes;
    }

    public static LinkedHashMap<Integer, M_Listes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1=1");
    }

    /* =======================
       GETTERS / SETTERS
       ======================= */

    public int getIdListe() { return idListe; }
    public String getNom() { return nom; }
    public LocalDate getDateAchat() { return dateAchat; }
    public String getCommentaire() { return commentaire; }
    public Integer getIdAidant() { return idAidant; }
    public int getIdBeneficiaire() { return idBeneficiaire; }

    public void setNom(String nom) { this.nom = nom; }
    public void setDateAchat(LocalDate dateAchat) { this.dateAchat = dateAchat; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setIdAidant(Integer idAidant) { this.idAidant = idAidant; }
    public void setIdBeneficiaire(int idBeneficiaire) { this.idBeneficiaire = idBeneficiaire; }

    @Override
    public String toString() {
        return "M_Listes{"
                + "idListe=" + idListe
                + ", nom=" + nom
                + ", dateAchat=" + dateAchat
                + ", commentaire=" + commentaire
                + ", idAidant=" + idAidant
                + ", idBeneficiaire=" + idBeneficiaire
                + "}";
    }

    /* =======================
       TESTS
       ======================= */
    public static void main(String[] args) throws Exception {
        Db_mariadb db = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // INSERT
//         M_Listes listeTest = new M_Listes(db, "Courses", LocalDate.now(), "Lidl", 15, 26);
//         System.out.println(listeTest);

        // LECTURE
//         M_Listes listeTestl2 = new M_Listes(db, 4);
//         System.out.println(listeTestl2);

        // UPDATE
//         listeTestl2.setCommentaire("Leclerc");
//         listeTestl2.update();

        // GET RECORDS
//         LinkedHashMap<Integer, M_Listes> listes = M_Listes.getRecords(db);
//         for (int cle : listes.keySet()) {
//             System.out.println(listes.get(cle));
//         }
//        listeTestl2.delete();
        
    }
}
