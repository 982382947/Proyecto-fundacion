
package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final String usuario;
    private final String passw;
    
    public Conexion(){
        usuario = "root";
        passw = "";
    }
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("ok1\n");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/fundacion_inventario?useTimezone=true&serverTimezone=UTC", usuario, passw);
            System.out.println("ok2\n");
            return conexion;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
        }
        return null;
    }
}