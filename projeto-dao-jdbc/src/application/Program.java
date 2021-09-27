
package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;





public class Program {


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        Connection conn = null;
        Statement st = null;
        try{
            conn = DB.getConnection();
            
            // esse comando vai lancar/abrir uma confirmacao que so vai concluir toda a transcao quando todo esse bloco de comando foi cncluido
            conn.setAutoCommit(false);
            st = conn.createStatement();
                
            int rowsAffeted1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            int x = 1;
            /*comando para dar um erro falso
            if(x<2){
                throw new SQLException("Fake error");
            }*/
            int rowsAffeted2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
    
            System.out.println("rown1 " +rowsAffeted1);  
            System.out.println("rown2 " +rowsAffeted2);
            
            
            //o conn.commit vai fechar a transcao aberta la em cima
            conn.commit();
            
        }
        catch(SQLException e){
            try{
                conn.rollback();
            throw new DbException("Transcaion rolled back! Caused by: "+ e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Error trying to rooback"+ e1.getMessage());
            }
        }
        finally{ 
            DB.closeStatement(st);
            DB.closeConnection();
            
            
        }
        
    }   
    
}