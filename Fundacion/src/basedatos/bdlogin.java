
package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class bdlogin {
    Conexion c;
    
    public bdlogin(){
        c = new Conexion();
    }
    
    public boolean Login(String usuario, String passw){
        Connection conn = c.conectar();
        try {
            String query = "SELECT COUNT(*) AS num FROM usuario WHERE user = ? AND password = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, usuario);
            preparedStmt.setString(2, passw);
            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            return rs.getInt("num") > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al hacer la consulta");
        } finally {
            try {
                  conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(bdlogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
