package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class bdconductor {

    Conexion c;

    public bdconductor() {
        c = new Conexion();
    }

    public boolean obtenerEmpleadosTabla(DefaultTableModel dftable) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT * FROM empleado";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Object[] object = new Object[7];
            dftable = limpiarTabla(dftable);

            while (rs.next()) {
                object[0] = rs.getInt("id");
                object[1] = rs.getString("nombre");
                object[2] = rs.getString("apellidos");
                object[3] = rs.getString("tipo");
                object[4] = rs.getString("dni");
                object[5] = rs.getString("telefono");
                object[6] = rs.getString("direccion");

                dftable.addRow(object);
            }
            return true;
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

    public boolean guardarEmpleado(String nomb, String apell, String tipo, String dni, String tel, String direc) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT COUNT(*) AS num FROM empleado WHERE dni = ? ";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, dni);
            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            if (rs.getInt("num") == 0) {
                query = "INSERT INTO empleado (`nombre`, `apellidos`, `tipo`, `dni`, `telefono`, `direccion`) values (?,?,?,?,?,?)";

                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, nomb);
                preparedStmt.setString(2, apell);
                preparedStmt.setString(3, tipo);
                preparedStmt.setString(4, dni);
                preparedStmt.setString(5, tel);
                preparedStmt.setString(6, direc);
                preparedStmt.execute();
                JOptionPane.showMessageDialog(null, "Registro guardado");
                return true;
            } else {
                return false;
            }
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

    public boolean modificarEmpleado(int id, String nomb, String apell, String tipo, String dni, String tel, String direc) {
        Connection conn = c.conectar();
        try {
            String query = "UPDATE empleado SET nombre = ?, apellidos = ?, tipo = ?, dni = ?, telefono = ?, direccion = ? WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, nomb);
            preparedStmt.setString(2, apell);
            preparedStmt.setString(3, tipo);
            preparedStmt.setString(4, dni);
            preparedStmt.setString(5, tel);
            preparedStmt.setString(6, direc);
            preparedStmt.setInt(7, id);
            preparedStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro actualizado");
            return true;
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

    public boolean eliminarEmpleado(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM empleado WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
            return true;
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

    public DefaultTableModel limpiarTabla(DefaultTableModel dftable) {
        int tam = dftable.getRowCount();
        for (int i = 0; i < tam; i++) {
            dftable.removeRow(0);
        }
        return dftable;
    }

}
