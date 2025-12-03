/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pj_listecourse;

/**
 *
 * @author ninis
 */
public class C_ListeCourse {
    
    private Db_mariadb baseDD;
    private V_Main frm_main;
    
    private void connexion() throws Exception{
        baseDD = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
    }
    
    public C_ListeCourse() throws Exception{
        connexion();
        frm_main = new V_Main();
        frm_main.afficher();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
       C_ListeCourse leControlleur = new C_ListeCourse();
    }
    
}
