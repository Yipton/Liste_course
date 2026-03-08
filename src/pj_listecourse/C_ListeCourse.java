/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pj_listecourse;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 *
 * @author Yipton
 */
public class C_ListeCourse {

    private Db_mariadb baseDD;

    private V_Main frm_main;
    private V_Utilisateurs_Admin frm_Util_Admin;
    private V_Listes_Ben frm_Listes_Ben;

    private M_Users utilConnecte = null;

    private LinkedHashMap<Integer, M_Users> lesUtils;
    private LinkedHashMap<Integer, M_Roles> lesRoles;
    private LinkedHashMap<Integer, M_Autorisations> lesAutorisations;
    private LinkedHashMap<Integer, M_Listes> lesListes;
    private LinkedHashMap<Integer, Object[]> lesPropositions;

    private int id_liste_courante;

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

    //------------ UTILISATEURS -------------------------------------------------------------------------------------------------------------
    //
    public void ajouter_util(String nom, String prenom, String mail, String mdp, String commentaire, int idRole) throws SQLException {
        String name = nom + '.' + prenom;

        new M_Users(baseDD, name, prenom, nom, mail, mdp, commentaire, idRole);

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

    public void update_util(int idUtilisateur, String nom, String prenom, String mail, String mdp, String commentaire, int idRole, LocalDateTime email_verified_at) throws SQLException {
        M_Users unUtil = new M_Users(baseDD, idUtilisateur, nom, prenom, nom, mail, email_verified_at, mdp, null, commentaire, null, null, idRole);
        unUtil.update();
        lesUtils = M_Users.getRecords(baseDD);
        lesRoles = M_Roles.getRecords(baseDD);
        frm_Util_Admin.afficher(lesUtils, lesRoles);
    }

    //------------ LISTES -------------------------------------------------------------------------------------------------------------
    //
    public void aff_V_Listes_Ben(String mode) throws SQLException {
        if (utilConnecte.getId_role() == 3) {
            lesListes = M_Listes.getRecords(baseDD, "id_aidant = " + utilConnecte.getIdUtilisateur());
        } else if (utilConnecte.getId_role() == 4) {
            lesListes = M_Listes.getRecords(baseDD, "id_beneficiaire = " + utilConnecte.getIdUtilisateur());
        } else {
            lesListes = null;
        }
        lesPropositions = M_Proposer.getLignesOffres(baseDD);
        if (frm_Listes_Ben == null || !frm_Listes_Ben.isDisplayable()) {
            frm_Listes_Ben = new V_Listes_Ben(frm_main, true, this);
        }

        frm_Listes_Ben.afficher(mode, lesListes, lesPropositions);
    }

    public int ajouter_liste(String nom, LocalDate date_souhaitee, String commentaire) throws SQLException {

        LinkedHashMap<Integer, M_Users> lesAidants = M_Users.getRecords(baseDD, "id_role = 3");

        Object[] cles = lesAidants.keySet().toArray();
        int id_aidant = (int) cles[new Random().nextInt(cles.length)];

        M_Listes newListe = new M_Listes(baseDD, nom, date_souhaitee, commentaire, id_aidant, utilConnecte.getIdUtilisateur());

        return newListe.getIdListe();
    }

    public void ajouter_commande(int quantite, String commentaire, int id_article, int id_liste, int id_marque, int id_magasin) throws SQLException {
        M_Proposer uneProposition = new M_Proposer(baseDD, id_article, id_marque, id_magasin);
        float prix = uneProposition.getPrix();
        new M_Commandes(baseDD, quantite, commentaire, id_article, id_liste, id_marque, id_magasin, prix);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        C_ListeCourse leControlleur = new C_ListeCourse();
    }

}
