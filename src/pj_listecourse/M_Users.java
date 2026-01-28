/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Users {

    private Db_mariadb db;

    // Champs BDD
    private int idUtilisateur, id_role;
    private String name, prenom, nom, email, password, remember_token, commentaire;
    private LocalDateTime email_verified_at, created_at, updated_at;

    // ---------------- CONSTRUCTEURS ----------------
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

    /**
     * Constructeur "INSERT"
     */
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
        this.remember_token = remember_token;
        this.commentaire = commentaire;
        this.id_role = id_role;

        this.email_verified_at = null;
        this.updated_at = null;

        String sql = "INSERT INTO mcd_users (name, prenom, nom, email, password, commentaire, id_role) "
                + "VALUES ("
                + "'" + name + "', "
                + "'" + prenom + "', "
                + "'" + nom + "', "
                + "'" + email + "', "
                + "'" + password + "', "
                + (commentaire == null ? "NULL" : "'" + commentaire + "'") + ", "
                + id_role
                + ")";

        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        res.first();
        this.idUtilisateur = res.getInt("id");
        res.close();
    }

    /**
     * Constructeur "SELECT by id"
     */
    public M_Users(Db_mariadb db, int idUtilisateur) throws SQLException {
        this.db = db;
        this.idUtilisateur = idUtilisateur;

        String sql = "SELECT * FROM mcd_users WHERE id = " + idUtilisateur;
        ResultSet res = db.sqlSelect(sql);
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

    // ---------------- GETTERS ----------------
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

    // ---------------- SETTERS ----------------
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

    // ---------------- CRUD ----------------
    public void update() throws SQLException {
        String emailVerifiedSql = (email_verified_at == null)
                ? "NULL"
                : "'" + email_verified_at.toString().replace('T', ' ') + "'";

        String updatedAtSql = (updated_at == null)
                ? "NULL"
                : "'" + updated_at.toString().replace('T', ' ') + "'";

        String sql = "UPDATE mcd_users SET "
                + "name = '" + name + "', "
                + "prenom = '" + prenom + "', "
                + "nom = '" + nom + "', "
                + "email = '" + email + "', "
                + "email_verified_at = " + emailVerifiedSql + ", "
                + "password = '" + password + "', "
                + "remember_token = " + (remember_token == null ? "NULL" : "'" + remember_token + "'") + ", "
                + "commentaire = " + (commentaire == null ? "NULL" : "'" + commentaire + "'") + ", "
                + "updated_at = " + updatedAtSql + ", "
                + "id_role = " + id_role + " "
                + "WHERE id = " + idUtilisateur;

        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_users WHERE id = " + idUtilisateur;
        db.sqlExec(sql);
    }

    // ---------------- GETRECORDS ----------------
    public static LinkedHashMap<Integer, M_Users> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Users> lesUsers = new LinkedHashMap<>();
        M_Users unUser;

        int cle, id_role;
        String name, prenom, nom, email, password, remember_token, commentaire;
        LocalDateTime email_verified_at, created_at, updated_at;

        String sql = "SELECT * FROM mcd_users WHERE " + clauseWhere + " ORDER BY nom, prenom";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            name = res.getString("name");
            prenom = res.getString("prenom");
            nom = res.getString("nom");
            email = res.getString("email");
            email_verified_at = res.getObject("email_verified_at", LocalDateTime.class);
            password = res.getString("password");
            remember_token = res.getString("remember_token");
            commentaire = res.getString("commentaire");
            created_at = res.getObject("created_at", LocalDateTime.class);
            updated_at = res.getObject("updated_at", LocalDateTime.class);
            id_role = res.getInt("id_role");

            unUser = new M_Users(db, cle, name, prenom, nom, email, email_verified_at, password, remember_token, commentaire, created_at, updated_at, id_role);
            lesUsers.put(cle, unUser);
        }

        res.close();
        return lesUsers;
    }

    public static LinkedHashMap<Integer, M_Users> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    // ---------------- AUTH ----------------
    public static M_Users connexion_log(Db_mariadb db, String email, String password) throws SQLException {
        String sql = "SELECT id, password FROM mcd_users WHERE email = '" + email + "'";
        ResultSet res = db.sqlSelect(sql);

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

    // ---------------- TO STRING ----------------
    @Override
    public String toString() {
        return "M_User{"
                + "id=" + idUtilisateur
                + ", name='" + name + '\''
                + ", prenom='" + prenom + '\''
                + ", nom='" + nom + '\''
                + ", email='" + email + '\''
                + ", email_verified_at=" + email_verified_at
                + ", password='" + password + '\''
                + ", remember_token='" + remember_token + '\''
                + ", commentaire='" + commentaire + '\''
                + ", created_at=" + created_at
                + ", updated_at=" + updated_at
                + ", id_role=" + id_role
                + '}';
    }

    // ---------------- TESTS ----------------
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

        // 0) bcrypt simple (pour vérifier la lib)
//        String hash = BCrypt.withDefaults().hashToString(12, "pergaixj".toCharArray());
//        System.out.println("HASH=" + hash);
//        System.out.println("VERIFY=" + BCrypt.verifyer().verify("pergaixj".toCharArray(), hash).verified);

        // 1) INSERT (crée un user)
//        String emailTest = "test_" + System.currentTimeMillis() + "@mail.fr";
//        String hashPwd = BCrypt.withDefaults().hashToString(12, "azerty".toCharArray());
//
//        M_User u = new M_User(base,
//                "pseudoTest",
//                "Yanis",
//                "Proust",
//                emailTest,
//                hashPwd,
//                null,
//                "créé depuis main()",
//                1
//        );
//        System.out.println("\n[INSERT OK] id=" + u.getIdUtilisateur() + " email=" + u.getEmail());
//
//        // 2) SELECT BY ID
//        M_User u2 = new M_User(base, u.getIdUtilisateur());
//        System.out.println("\n[SELECT OK]\n" + u2);
//
//        // 3) UPDATE
//        u2.setCommentaire("commentaire modifié");
//        u2.setNom("PROUST_MODIF");
//        u2.setPrenom("YANIS_MODIF");
//        u2.setName("pseudo_modif");
//        u2.update();
//        System.out.println("\n[UPDATE OK]");
//
//        M_User u3 = new M_User(base, u.getIdUtilisateur());
//        System.out.println("\n[RE-SELECT AFTER UPDATE]\n" + u3);

        // 4) GETRECORDS
//        LinkedHashMap<Integer, M_User> liste = M_User.getRecords(base);
//        System.out.println("\n[GETRECORDS FILTRE] size=" + liste.size());
//        for (int cle : liste.keySet()) {
//            System.out.println(" - " + liste.get(cle));
//        }

        // 5) CONNEXION (bcrypt check)
//        M_User connecte = M_User.connexion_log(base, emailTest, "azerty");
//        System.out.println("\n[CONNEXION OK ?] " + (connecte != null));
//        if (connecte != null) {
//            System.out.println(connecte);
//        }
//
        // 6) DELETE
        // u3.delete();
        // System.out.println("\n[DELETE OK]");
    }
}
