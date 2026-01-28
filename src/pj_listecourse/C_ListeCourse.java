/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 *
 * @author ninis
 */
public class C_ListeCourse {

    private Db_mariadb baseDD;
    
    private V_Main frm_main;
    private V_Utilisateurs_Admin frm_Util_Admin;
    
    private M_Users utilConnecte = null;
    
    private LinkedHashMap<Integer, M_Users> lesUtils;
    private LinkedHashMap<Integer, M_Roles> lesRoles;
    private LinkedHashMap<Integer, M_Autorisations> lesAutorisations;

    private void connexion() throws Exception {
        baseDD = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
    }

    public void deconnexion() {
        utilConnecte = null;
        frm_main.afficher();
    }

    public void fermeture_connexion() throws SQLException {
        baseDD.closeBase();
    }

    public M_Users demanderConnexion(String login, String mdp) throws SQLException {
        utilConnecte = M_Users.connexion_log(baseDD, login, mdp);
        return utilConnecte;
    }

    public C_ListeCourse() throws Exception {
        connexion();
        frm_main = new V_Main(this);
        frm_main.afficher();
    }
    
        public LinkedHashMap<Integer, M_Autorisations> autorisationRole(int idRole) throws SQLException {
        lesAutorisations = M_Autoriser.getLesAutorisations(baseDD, idRole);
        return lesAutorisations;
    }

    public void aff_V_Admin_Util() throws SQLException {

        lesUtils = M_Users.getRecords(baseDD);
        lesRoles = M_Roles.getRecords(baseDD);

        frm_Util_Admin = new V_Utilisateurs_Admin(frm_main, true, this);
        lesUtils = M_Users.getRecords(baseDD);
        frm_Util_Admin.afficher(lesUtils, lesRoles);
    }

    public void ajouter_util(String nom, String prenom, String mail, String mdp, String commentaire, int idRole) throws SQLException {
        String name = nom +'.'+ prenom;
        M_Users nouvel_utilisateur = new M_Users(baseDD, name, prenom, nom, mail, mdp, commentaire, idRole);
        lesUtils = M_Users.getRecords(baseDD);
        lesRoles = M_Roles.getRecords(baseDD);
        frm_Util_Admin.afficher(lesUtils, lesRoles);
    }

    public void supprimer_util(M_Users unUtil) throws SQLException {
        unUtil.delete();
        lesUtils = M_Users.getRecords(baseDD);
        lesRoles = M_Roles.getRecords(baseDD);
        frm_Util_Admin.afficher(lesUtils, lesRoles);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        C_ListeCourse leControlleur = new C_ListeCourse();
    }

}
