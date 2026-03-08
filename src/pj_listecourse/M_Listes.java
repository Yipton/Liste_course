/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author Yipton
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

public class M_Listes {

    private Db_mariadb db;

    private int idListe;
    private String nom;
    private LocalDate dateAchat;
    private String commentaire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer idAidant;       // nullable côté SQL
    private int idBeneficiaire;     // NOT NULL côté SQL

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
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

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Listes(Db_mariadb db, String nom, LocalDate dateAchat, String commentaire,
            Integer idAidant, int idBeneficiaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.dateAchat = dateAchat;
        this.commentaire = commentaire;
        this.idAidant = idAidant;
        this.idBeneficiaire = idBeneficiaire;

        String sql = "INSERT INTO mcd_listes (nom, date_achat, commentaire, id_aidant, id_beneficiaire) "
                + "VALUES (?, ?, ?, ?, ?)";
        db.sqlExec(sql, nom, dateAchat, commentaire, idAidant, idBeneficiaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.idListe = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Listes(Db_mariadb db, int idListe) throws SQLException {
        this.db = db;
        this.idListe = idListe;

        String sql = "SELECT * FROM mcd_listes WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, idListe);
        res.first();

        this.nom = res.getString("nom");
        this.dateAchat = res.getObject("date_achat", LocalDate.class);
        this.commentaire = res.getString("commentaire");
        this.createdAt = res.getObject("created_at", LocalDateTime.class);
        this.updatedAt = res.getObject("updated_at", LocalDateTime.class);
        this.idAidant = res.getInt("id_aidant");
        if (res.wasNull()) {
            this.idAidant = null;
        }
        this.idBeneficiaire = res.getInt("id_beneficiaire");

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdListe() {
        return idListe;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
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

    public Integer getIdAidant() {
        return idAidant;
    }

    public int getIdBeneficiaire() {
        return idBeneficiaire;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setIdAidant(Integer idAidant) {
        this.idAidant = idAidant;
    }

    public void setIdBeneficiaire(int idBeneficiaire) {
        this.idBeneficiaire = idBeneficiaire;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_listes SET "
                + "nom = ?, "
                + "date_achat = ?, "
                + "commentaire = ?, "
                + "id_aidant = ?, "
                + "id_beneficiaire = ? "
                + "WHERE id = ?";
        db.sqlExec(sql, nom, dateAchat, commentaire, idAidant, idBeneficiaire, idListe);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_listes WHERE id = ?";
        db.sqlExec(sql, idListe);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Listes> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<Integer, M_Listes> lesListes = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_listes WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");

            Integer idAidant = res.getInt("id_aidant");
            if (res.wasNull()) {
                idAidant = null;
            }

            M_Listes uneListe = new M_Listes(
                    db,
                    cle,
                    res.getString("nom"),
                    res.getObject("date_achat", LocalDate.class),
                    res.getString("commentaire"),
                    res.getObject("created_at", LocalDateTime.class),
                    res.getObject("updated_at", LocalDateTime.class),
                    idAidant,
                    res.getInt("id_beneficiaire")
            );

            lesListes.put(cle, uneListe);
        }

        res.close();
        return lesListes;
    }

    public static LinkedHashMap<Integer, M_Listes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Listes{idListe=" + idListe
                + ", nom='" + nom + "'"
                + ", dateAchat=" + dateAchat
                + ", commentaire='" + commentaire + "'"
                + ", idAidant=" + idAidant
                + ", idBeneficiaire=" + idBeneficiaire
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // ===========================
        // Récupérer des IDs existants pour les FK
        // ===========================
        int idBeneficiaire = 0;
        Integer idAidant = null;

        ResultSet rb = base.sqlSelect("SELECT id FROM mcd_users LIMIT 1");
        if (!rb.first()) {
            rb.close();
            throw new Exception("Aucun utilisateur dans mcd_users");
        }
        idBeneficiaire = rb.getInt("id");
        rb.close();

        ResultSet ra = base.sqlSelect("SELECT id FROM mcd_users LIMIT 1 OFFSET 1");
        if (ra.first()) {
            idAidant = ra.getInt("id");
        }
        ra.close();

        System.out.println("=== DONNÉES DE TEST ===");
        System.out.println("idBeneficiaire : " + idBeneficiaire);
        System.out.println("idAidant : " + idAidant);

        // ===========================
        // TEST 1 : INSERT
        // ===========================
        System.out.println("\n=== TEST 1 : INSERT ===");
        M_Listes liste1 = new M_Listes(base, "Courses test", LocalDate.now(), "Commentaire test", idAidant, idBeneficiaire);
        System.out.println("Nouvelle liste : " + liste1);

        // ===========================
        // TEST 2 : SELECT par ID
        // ===========================
        System.out.println("\n=== TEST 2 : SELECT par ID ===");
        M_Listes liste2 = new M_Listes(base, liste1.getIdListe());
        System.out.println("Liste lue : " + liste2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        liste2.setNom("Courses modifiées");
        liste2.setCommentaire("Commentaire modifié");
        liste2.setDateAchat(LocalDate.now().plusDays(1));
        liste2.update();
        M_Listes liste3 = new M_Listes(base, liste1.getIdListe());
        System.out.println("Après update : " + liste3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Listes> toutes = M_Listes.getRecords(base);
        System.out.println("Nombre de listes : " + toutes.size());
        for (M_Listes l : toutes.values()) {
            System.out.println("  " + l);
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        String recherche = "test";
        LinkedHashMap<Integer, M_Listes> filtrees = M_Listes.getRecords(base, "nom LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtrees.size() + " résultat(s)");
        for (M_Listes l : filtrees.values()) {
            System.out.println("  " + l);
        }

        // ===========================
        // TEST 6 : DELETE
        // ===========================
        System.out.println("\n=== TEST 6 : DELETE ===");
        System.out.println("Suppression de : " + liste3);
        liste3.delete();
        System.out.println("Liste supprimée !");

        // ===========================
        // TEST 7 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 7 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Listes> restantes = M_Listes.getRecords(base);
        System.out.println("Nombre de listes restantes : " + restantes.size());
        for (M_Listes l : restantes.values()) {
            System.out.println("  " + l);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
