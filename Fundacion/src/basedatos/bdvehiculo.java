package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class bdvehiculo {

    Conexion c;

    public bdvehiculo() {
        c = new Conexion();
    }

    public boolean obtenerVehiculoTabla(DefaultTableModel dftable) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT id, marca, modelo, matricula, descripcion FROM vehiculo";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Object[] object = new Object[5];
            dftable = limpiarTabla(dftable);

            while (rs.next()) {
                object[0] = rs.getInt("id");
                object[1] = rs.getString("marca");
                object[2] = rs.getString("modelo");
                object[3] = rs.getString("matricula");
                object[4] = rs.getString("descripcion");
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

    public boolean guardarVehiculo(String marca, String modelo, String matricula, String descripcion) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT COUNT(*) AS num FROM vehiculo WHERE matricula = ? ";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, matricula);
            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            if (rs.getInt("num") == 0) {
                query = "INSERT INTO vehiculo (`marca`, `modelo`, `matricula`, `descripcion`) values (?,?,?,?)";

                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, marca);
                preparedStmt.setString(2, modelo);
                preparedStmt.setString(3, matricula);
                preparedStmt.setString(4, descripcion);
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

    public boolean modificarVehiculo(int id, String marca, String modelo, String matricula, String descripcion) {
        Connection conn = c.conectar();
        try {
            String query = "UPDATE vehiculo SET marca = ?, modelo = ?, matricula = ?, descripcion = ? WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, marca);
            preparedStmt.setString(2, modelo);
            preparedStmt.setString(3, matricula);
            preparedStmt.setString(4, descripcion);
            preparedStmt.setInt(5, id);
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

    public boolean eliminarVehiculo(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM vehiculo WHERE id = ?";

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
