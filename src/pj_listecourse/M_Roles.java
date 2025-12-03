/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

/**
 *
 * @author ninis
 */
public class M_Roles {
    private Db_mariadb db;
    private int id;
    private String code, nom, commentaire;
    private LocalDateTime updated_at, created_at;
    
    public M_Roles(Db_mariadb db, int id, String code, String nom, String commentaire, LocalDateTime updated_at, LocalDateTime created_at){
        this.db = db;
        this.code = code;
        this. nom = nom;
        this.commentaire = commentaire;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }
    
    public M_Roles(Db_mariadb db, String code, String nom, String commentaire) throws SQLException{
        this.db = db;
        this.code = code;
        this.nom = nom;
        this.commentaire = commentaire;
        
        String sql = "INSERT INTO mcd_roles (code, nom, commentaire) VALUES ('"+code+"', '"+nom+"', '"+commentaire+"');"; 
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        
        sql = "SELECT * FROM mcd_roles WHERE id = "+id;
        
        res = db.sqlSelect(sql);
        res.first();
        this.id = res.getInt("id");
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        res.close();
    }
    
    public M_Roles(Db_mariadb db, int id) throws SQLException{
        this.db = db;
        this.id = id;
        
        String sql = "SELECT * FROM mcd_roles WHERE id = "+id;
        
        ResultSet res;
        res = db.sqlSelect(sql);
        
        res.first();
         this.code = res.getString("code");
         this.nom = res.getString("nom");
         this.commentaire = res.getString("commentaire");
         this.updated_at = res.getObject("updated_at", LocalDateTime.class);
         this.created_at = res.getObject("created_at", LocalDateTime.class);
         
         res.close();
    }

    public Db_mariadb getDb() {
        return db;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setDb(Db_mariadb db) {
        this.db = db;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    
        public void update() throws SQLException {
        String sql;
        sql = "UPDATE mcd_roles SET code='" + code + "', nom='"+nom+"', commentaire='"+commentaire
                + "' WHERE id=" + id + ";";
        db.sqlExec(sql);
        
        sql = "SELECT * FROM mcd_roles WHERE id = "+id;
        
        ResultSet res;
        res = db.sqlSelect(sql);
        
        res.first();
         this.created_at = res.getObject("created_at", LocalDateTime.class);
         this.updated_at = res.getObject("updated_at", LocalDateTime.class);
         res.close();
    }

    public void delete() throws SQLException {
        String sql;
        sql = "DELETE FROM mcd_roles WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Roles> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Roles> lesRoles;
        lesRoles = new LinkedHashMap();
        M_Roles unRole;

        int cle;
        String nom, code, commentaire;
        LocalDateTime updated_at, created_at;

        String sql;
        sql = "SELECT * FROM mcd_roles WHERE " + clauseWhere + " ORDER BY nom";
        ResultSet res;
        res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            code = res.getString("code");
            nom = res.getString("nom");
            commentaire = res.getString("commentaire");
            updated_at = res.getObject("updated_at", LocalDateTime.class);
            created_at = res.getObject("created_at", LocalDateTime.class);

            unRole = new M_Roles(db, cle, code, nom, commentaire, updated_at, created_at);
            lesRoles.put(cle, unRole);
        }
        res.close();
        return lesRoles;
    }

    public static LinkedHashMap<Integer, M_Roles> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    @Override
    public String toString() {
        return "M_Role{" + "id=" +id + ", code='" + code + "', nom='"+nom+"', commentaire='"+commentaire+"', created_at='"+created_at+"', updated_at='"+updated_at+"'"+'}';
    }
     public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        
        /*M_Roles unRole1 = new M_Roles(base, 1);
        System.out.println(unRole1.toString());*/
        
        /*M_Roles unRole2 = new M_Roles(base, "TEST", "Je suis un test", "Ceci est un test");
        System.out.println(unRole2.toString());*/
        
        /*M_Roles unRole3 = new M_Roles(base, 6);
        unRole3.setCode("TEST_MODIF");
        unRole3.setNom("nom modifié");
        unRole3.setCommentaire("comm modifié");
        unRole3.update();
        System.out.println(unRole3.toString());*/
        
        /*M_Roles unRole4 = new M_Roles(base, 6);
        unRole4.delete();*/
        
        /*LinkedHashMap<Integer, M_Roles> lesRoles = M_Roles.getRecords(base);
        for(int uneCle : lesRoles.keySet()){
            System.out.println(lesRoles.get(uneCle));
        }*/
        
        /*LinkedHashMap<Integer, M_Roles> lesRoles = M_Roles.getRecords(base, "CODE = 'ADM'");
        for(int uneCle : lesRoles.keySet()){
            System.out.println(lesRoles.get(uneCle));
        }*/
     }
}
