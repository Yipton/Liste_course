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

/**
 *
 * @author Yipton
 */
public class M_Articles {

    private Db_mariadb db;
    private int id;
    private String nom;
    private String commentaire;
    private LocalDateTime created_at, updated_at;

    // -------------------------
    // Constructeur "lecture directe"
    // -------------------------
    public M_Articles(Db_mariadb db, int id, String nom, String commentaire) {
        this.db = db;
        this.id = id;
        this.nom = nom;
        this.commentaire = commentaire;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Articles(Db_mariadb db, String nom, String commentaire) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.commentaire = commentaire;

        String sql = "INSERT INTO mcd_articles (nom, commentaire) VALUES (?, ?)";
        db.sqlExec(sql, nom, commentaire);

        ResultSet res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Articles(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;

        String sql = "SELECT * FROM mcd_articles WHERE id= ?";
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
        String sql = "UPDATE mcd_articles SET nom= ? , commentaire = ? WHERE id= ?";
        db.sqlExec(sql, nom, commentaire, id);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_articles WHERE id= ?";
        db.sqlExec(sql, id);
    }

    // -------------------------
    // SELECT MULTI
    // -------------------------
    public static LinkedHashMap<Integer, M_Articles> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {

        LinkedHashMap<Integer, M_Articles> lesArticles = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_articles WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            String nom = res.getString("nom");
            String commentaire = res.getString("commentaire");

            M_Articles unArticle = new M_Articles(db, cle, nom, commentaire);
            lesArticles.put(cle, unArticle);
        }

        res.close();
        return lesArticles;
    }

    public static LinkedHashMap<Integer, M_Articles> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    @Override
    public String toString() {
        return "M_Articles{" + "id=" + id + ", nom='" + nom + "', commentaire='" + commentaire + "'}";
    }

// -------------------------
// TESTS
// -------------------------
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(
                Cl_Connection.url,
                Cl_Connection.login,
                Cl_Connection.password
        );

        System.out.println("=== TEST 1 : SELECT par ID ===");
        M_Articles a1 = new M_Articles(base, 1);
        System.out.println(a1);

        System.out.println("\n=== TEST 2 : INSERT ===");
        M_Articles a2 = new M_Articles(base, "Céréales", "Test insertion");
        System.out.println("Nouvel article créé : " + a2);

        System.out.println("\n=== TEST 3 : UPDATE ===");
        a2.setNom("Céréales modifiées");
        a2.setCommentaire("Commentaire modifié");
        a2.update();
        // Relecture pour vérifier
        M_Articles a2bis = new M_Articles(base, a2.getId());
        System.out.println("Après update : " + a2bis);

        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Articles> tous = M_Articles.getRecords(base);
        System.out.println("Nombre d'articles : " + tous.size());
        for (M_Articles a : tous.values()) {
            System.out.println("  " + a);
        }

        System.out.println("\n=== TEST 5 : getRecords avec filtre (hybride sécurisé) ===");
        String recherche = "Céréales";
        LinkedHashMap<Integer, M_Articles> filtres = M_Articles.getRecords(
                base,
                "nom LIKE ?",
                "%" + recherche + "%"
        );
        System.out.println("Recherche '" + recherche + "' : " + filtres.size() + " résultat(s)");
        for (M_Articles a : filtres.values()) {
            System.out.println("  " + a);
        }

        System.out.println("\n=== TEST 6 : getRecords avec plusieurs critères ===");
        LinkedHashMap<Integer, M_Articles> multi = M_Articles.getRecords(
                base,
                "nom LIKE ? AND id > ?",
                "%a%",
                0
        );
        System.out.println("Articles contenant 'a' avec id > 0 : " + multi.size() + " résultat(s)");
        for (M_Articles a : multi.values()) {
            System.out.println("  " + a);
        }

        System.out.println("\n=== TEST 7 : DELETE ===");
        System.out.println("Suppression de : " + a2);
        a2.delete();
        System.out.println("Article supprimé !");

        System.out.println("\n=== TEST 8 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Articles> apres = M_Articles.getRecords(base);
        System.out.println("Nombre d'articles restants : " + apres.size());
        for (M_Articles a : apres.values()) {
            System.out.println("  " + a);
        }

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS  ===");
    }

}
