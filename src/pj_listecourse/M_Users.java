/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author Yipton
 */
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Users {

    private Db_mariadb db;

    private int idUtilisateur, id_role;
    private String name, prenom, nom, email, password, remember_token, commentaire;
    private LocalDateTime email_verified_at, created_at, updated_at;

    // -------------------------
    // Constructeur lecture directe
    // -------------------------
    public M_Users(Db_mariadb db,
            int idUtilisateur,
            String name,
            String prenom,
            String nom,
            String email,
            LocalDateTime email_verified_at,
            String password,
            String remember_token,
            String commentaire,
            LocalDateTime created_at,
            LocalDateTime updated_at,
            int id_role) {

        this.db = db;
        this.idUtilisateur = idUtilisateur;
        this.name = name;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.password = password;
        this.remember_token = remember_token;
        this.commentaire = commentaire;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id_role = id_role;
    }

    // -------------------------
    // Constructeur INSERT
    // -------------------------
    public M_Users(Db_mariadb db,
            String name,
            String prenom,
            String nom,
            String email,
            String password,
            String commentaire,
            int id_role) throws SQLException {

        this.db = db;
        this.name = name;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.commentaire = commentaire;
        this.id_role = id_role;
        this.email_verified_at = null;
        this.updated_at = null;
        this.remember_token = null;

        String sql = "INSERT INTO mcd_users (name, prenom, nom, email, password, commentaire, id_role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.sqlExec(sql, name, prenom, nom, email, password, commentaire, id_role);

        ResultSet res = db.sqlLastId();
        res.first();
        this.idUtilisateur = res.getInt("id");
        res.close();
    }

    // -------------------------
    // Constructeur SELECT par ID
    // -------------------------
    public M_Users(Db_mariadb db, int idUtilisateur) throws SQLException {
        this.db = db;
        this.idUtilisateur = idUtilisateur;

        String sql = "SELECT * FROM mcd_users WHERE id = ?";
        ResultSet res = db.sqlSelect(sql, idUtilisateur);
        res.first();

        this.name = res.getString("name");
        this.prenom = res.getString("prenom");
        this.nom = res.getString("nom");
        this.email = res.getString("email");
        this.email_verified_at = res.getObject("email_verified_at", LocalDateTime.class);
        this.password = res.getString("password");
        this.remember_token = res.getString("remember_token");
        this.commentaire = res.getString("commentaire");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        this.id_role = res.getInt("id_role");

        res.close();
    }

    // -------------------------
    // Getters
    // -------------------------
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public int getId_role() {
        return id_role;
    }

    public String getName() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getEmail_verified_at() {
        return email_verified_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    // -------------------------
    // Setters
    // -------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEmail_verified_at(LocalDateTime email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public void update() throws SQLException {
        String sql = "UPDATE mcd_users SET "
                + "name = ?, "
                + "prenom = ?, "
                + "nom = ?, "
                + "email = ?, "
                + "email_verified_at = ?, "
                + "password = ?, "
                + "remember_token = ?, "
                + "commentaire = ?, "
                + "id_role = ? "
                + "WHERE id = ?";
        db.sqlExec(sql, name, prenom, nom, email, email_verified_at, password, remember_token, commentaire, id_role, idUtilisateur);
    }

    // -------------------------
    // DELETE
    // -------------------------
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_users WHERE id = ?";
        db.sqlExec(sql, idUtilisateur);
    }

    // -------------------------
    // SELECT MULTI (hybride sécurisé)
    // -------------------------
    public static LinkedHashMap<Integer, M_Users> getRecords(
            Db_mariadb db,
            String clauseWhere,
            Object... params
    ) throws SQLException {
        LinkedHashMap<Integer, M_Users> lesUsers = new LinkedHashMap<>();

        String sql = "SELECT * FROM mcd_users WHERE " + clauseWhere + " ORDER BY nom, prenom";
        ResultSet res = db.sqlSelect(sql, params);

        while (res.next()) {
            int cle = res.getInt("id");
            String name = res.getString("name");
            String prenom = res.getString("prenom");
            String nom = res.getString("nom");
            String email = res.getString("email");
            LocalDateTime email_verified_at = res.getObject("email_verified_at", LocalDateTime.class);
            String password = res.getString("password");
            String remember_token = res.getString("remember_token");
            String commentaire = res.getString("commentaire");
            LocalDateTime created_at = res.getObject("created_at", LocalDateTime.class);
            LocalDateTime updated_at = res.getObject("updated_at", LocalDateTime.class);
            int id_role = res.getInt("id_role");

            M_Users unUser = new M_Users(db, cle, name, prenom, nom, email, email_verified_at, password, remember_token, commentaire, created_at, updated_at, id_role);
            lesUsers.put(cle, unUser);
        }

        res.close();
        return lesUsers;
    }

    public static LinkedHashMap<Integer, M_Users> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // -------------------------
    // CONNEXION (sécurisée)
    // -------------------------
    public static M_Users connexion_log(Db_mariadb db, String email, String password) throws SQLException {
        String sql = "SELECT id, password FROM mcd_users WHERE email = ?";
        ResultSet res = db.sqlSelect(sql, email);

        if (!res.next()) {
            res.close();
            return null;
        }

        int idUtilisateur = res.getInt("id");
        String hash = res.getString("password");
        res.close();

        boolean verif = BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
        if (!verif) {
            return null;
        }

        return new M_Users(db, idUtilisateur);
    }

    // -------------------------
    // toString
    // -------------------------
    @Override
    public String toString() {
        return "M_Users{id=" + idUtilisateur
                + ", name='" + name + "'"
                + ", prenom='" + prenom + "'"
                + ", nom='" + nom + "'"
                + ", email='" + email + "'"
                + ", email_verified_at=" + email_verified_at
                + ", commentaire='" + commentaire + "'"
                + ", id_role=" + id_role
                + ", created_at=" + created_at
                + ", updated_at=" + updated_at + "}";
    }

    // -------------------------
    // Tests
    // -------------------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // ===========================
        // TEST 1 : INSERT
        // ===========================
        System.out.println("=== TEST 1 : INSERT ===");
        String emailTest = "test_" + System.currentTimeMillis() + "@mail.fr";
        String hashPwd = BCrypt.withDefaults().hashToString(12, "azerty".toCharArray());

        M_Users u1 = new M_Users(base, "pseudoTest", "Yanis", "Proust", emailTest, hashPwd, "créé depuis main()", 1);
        System.out.println("Nouvel utilisateur : " + u1);

        // ===========================
        // TEST 2 : SELECT par ID
        // ===========================
        System.out.println("\n=== TEST 2 : SELECT par ID ===");
        M_Users u2 = new M_Users(base, u1.getIdUtilisateur());
        System.out.println("Utilisateur lu : " + u2);

        // ===========================
        // TEST 3 : UPDATE
        // ===========================
        System.out.println("\n=== TEST 3 : UPDATE ===");
        u2.setNom("PROUST_MODIF");
        u2.setPrenom("YANIS_MODIF");
        u2.setCommentaire("commentaire modifié");
        u2.update();
        M_Users u3 = new M_Users(base, u1.getIdUtilisateur());
        System.out.println("Après update : " + u3);

        // ===========================
        // TEST 4 : getRecords (tous)
        // ===========================
        System.out.println("\n=== TEST 4 : getRecords (tous) ===");
        LinkedHashMap<Integer, M_Users> tous = M_Users.getRecords(base);
        System.out.println("Nombre d'utilisateurs : " + tous.size());
        int c = 0;
        for (M_Users u : tous.values()) {
            System.out.println("  " + u);
            c++;
            if (c >= 5) {
                break;
            }
        }

        // ===========================
        // TEST 5 : getRecords avec filtre (hybride sécurisé)
        // ===========================
        System.out.println("\n=== TEST 5 : getRecords avec filtre ===");
        String recherche = "test";
        LinkedHashMap<Integer, M_Users> filtres = M_Users.getRecords(base, "email LIKE ?", "%" + recherche + "%");
        System.out.println("Recherche '" + recherche + "' : " + filtres.size() + " résultat(s)");
        for (M_Users u : filtres.values()) {
            System.out.println("  " + u);
        }

        // ===========================
        // TEST 6 : CONNEXION (bcrypt)
        // ===========================
        System.out.println("\n=== TEST 6 : CONNEXION ===");
        M_Users connecte = M_Users.connexion_log(base, emailTest, "azerty");
        if (connecte != null) {
            System.out.println("Connexion réussie : " + connecte);
        } else {
            System.out.println("Connexion échouée");
        }

        // Test avec mauvais mot de passe
        M_Users connecteFail = M_Users.connexion_log(base, emailTest, "mauvais");
        if (connecteFail == null) {
            System.out.println("Connexion avec mauvais mdp échouée (attendu)");
        }

        // ===========================
        // TEST 7 : DELETE
        // ===========================
        System.out.println("\n=== TEST 7 : DELETE ===");
        System.out.println("Suppression de : " + u3);
        u3.delete();
        System.out.println("Utilisateur supprimé !");

        // ===========================
        // TEST 8 : Vérification après suppression
        // ===========================
        System.out.println("\n=== TEST 8 : Vérification après suppression ===");
        LinkedHashMap<Integer, M_Users> restants = M_Users.getRecords(base);
        System.out.println("Nombre d'utilisateurs restants : " + restants.size());

        System.out.println("\n=== TOUS LES TESTS SONT VALIDÉS ===");
    }
}
