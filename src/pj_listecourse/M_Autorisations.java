/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import org.mariadb.jdbc.client.result.ResultSetMetaData;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 *
 * @author ninis
 */
public class M_Autorisations {

    private Db_mariadb db;
    private int idAutorisation;
    private String code, description;
    private LocalDateTime created_at, updated_at;

    public M_Autorisations(Db_mariadb db, int idAutorisation, String code, String description) {
        this.db = db;
        this.idAutorisation = idAutorisation;
        this.code = code;
        this.description = description;
    }

    public M_Autorisations(Db_mariadb db, String code, String description) throws SQLException {
        this.db = db;
        this.code = code;
        this.description = description;

        String sql = "INSERT INTO mcd_autorisations (code, description) VALUES ('" + code + "', '" + description + "')";
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.idAutorisation = res.getInt("id");
        res.close();
    }

    public M_Autorisations(Db_mariadb db, int idAutorisation) throws SQLException {
        this.db = db;
        this.idAutorisation = idAutorisation;

        String sql = "SELECT * FROM mcd_autorisations WHERE idAutorisation ='" + idAutorisation + "';";

        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();

        this.code = res.getString("code");
        this.description = res.getString("description");

        res.close();
    }

    public int getIdAutorisation() {
        return idAutorisation;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void update() throws SQLException {
        String sql = "UPDATE mcd_autorisations SET code='" + code + "', description='" + description + "' WHERE idAutorisation = '" + idAutorisation + "';";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_autorisations WHERE idAutorisation='" + idAutorisation + "';";
        db.sqlExec(sql);
    }

    public static LinkedHashMap<Integer, M_Autorisations> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Autorisations> lesAutorisations = new LinkedHashMap();
        M_Autorisations uneAutorisation;

        int cle;
        String code, description;

        String sql = "SELECT * FROM mcd_autorisations WHERE " + clauseWhere + ";";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("idAutorisation");
            code = res.getString("code");
            description = res.getString("description");

            uneAutorisation = new M_Autorisations(db, cle, code, description);
            lesAutorisations.put(cle, uneAutorisation);
        }
        res.close();
        return lesAutorisations;
    }

    public static LinkedHashMap<Integer, M_Autorisations> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    @Override
    public String toString() {
        return "M_Autorisation{" + "id=" + idAutorisation + ", code='" + code + "', description='" + description + "'}'";
    }

    /*Tests*/
    public static void main(String[] args) throws Exception {

        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);

//        M_Autorisation uneAutorisation1 = new M_Autorisation(base, 1);
//        System.out.println(uneAutorisation1.toString());
//
//        M_Autorisation uneAutorisation2 = new M_Autorisation(base, "test", "ceci est un test");
//        System.out.println(uneAutorisation2.toString());
//
//        M_Autorisation uneAutorisation3 = new M_Autorisation(base, 17);
//        uneAutorisation3.setCode("TEST_MODIF");
//        uneAutorisation3.setDescription("description modifiée");
//        uneAutorisation3.update();
//        System.out.println(uneAutorisation3.toString());
//
//        M_Autorisation uneAutorisation4 = new M_Autorisation(base, 17);
//        uneAutorisation4.delete();
//
//        LinkedHashMap<Integer, M_Autorisation> lesAutorisations = M_Autorisation.getRecords(base);
//        for(int uneCle : lesAutorisations.keySet()){
//            System.out.println(lesAutorisations.get(uneCle));
//        }
//
//        LinkedHashMap<Integer, M_Autorisation> lesRoles = M_Autorisation.getRecords(base, "code = 'mi_Mon_compte'");
//        for (int uneCle : lesRoles.keySet()) {
//            System.out.println(lesRoles.get(uneCle));
//        }
    }
}
