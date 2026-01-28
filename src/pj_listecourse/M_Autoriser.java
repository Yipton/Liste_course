/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.time.LocalDateTime;
/**
 *
 * @author ninis
 */
public class M_Autoriser {

    private Db_mariadb db;
    private int idRole;
    private int idAutorisation;
    private LocalDateTime created_at, updated_at;

    public M_Autoriser(Db_mariadb db) {
        this.db = db;
    }

    public M_Autoriser(Db_mariadb db, int idRole, int idAutorisation, boolean insert) throws SQLException {
        this.db = db;
        this.idRole = idRole;
        this.idAutorisation = idAutorisation;

        if (insert) {
            String sql = "INSERT INTO mcd_autoriser (idRole, idAutorisation) VALUES ("
                    + idRole + ", " + idAutorisation + ")";
            db.sqlExec(sql);
        }
    }

    public static LinkedHashMap<Integer, M_Autorisations> getLesAutorisations(Db_mariadb db, int idRole) throws SQLException {
        LinkedHashMap<Integer, M_Autorisations> lesAutorisations;
        lesAutorisations = new LinkedHashMap();
        M_Autorisations uneAutorisation;

        int cle;
        String code, description;
        String sql;
        sql = "SELECT * FROM mcd_autorisations AN"
                + " INNER JOIN mcd_autoriser AR ON AN.idAutorisation=AR.idAutorisation "
                + " WHERE idRole = " + idRole + " ORDER BY code";
        ResultSet res;
        res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("idAutorisation");
            code = res.getString("code");
            description = res.getString("description");
            uneAutorisation = new M_Autorisations(db, cle, code, description);
            lesAutorisations.put(cle, uneAutorisation);
        }

        return lesAutorisations;
    }

    /*Tests*/
    public static void main(String[] args) throws Exception {
        Db_mariadb base = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        int roleTest = 3;

        LinkedHashMap<Integer, M_Autorisations> droits = M_Autoriser.getLesAutorisations(base, roleTest);

        System.out.println("Autorisations pour le rôle : " + roleTest);
        System.out.println("Nb = " + droits.size());
        System.out.println("---------------------------");

        for (M_Autorisations a : droits.values()) {
            System.out.println(a.getCode() + " - " + a.getDescription());
        }
        /*                     Même chose que :                                           */

//        for (int idAutorisation : droits.keySet()) {
//            M_Autorisation a = droits.get(idAutorisation);
//            System.out.println(a.getCode() + " - " + a.getDescription());
//        }
        // (Optionnel) Test insertion dans la table de mariage :
        // ex : associer idRole=1 avec idAutorisation=3
        // new M_Autoriser(base, 1, 3, true);
        // System.out.println("Association (1,3) insérée dans mcd_autoriser.");
    }
}
