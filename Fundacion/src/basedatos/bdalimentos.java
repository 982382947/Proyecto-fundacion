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

public class bdalimentos {

    Conexion c;

    public bdalimentos() {
        c = new Conexion();
    }

    public boolean guardarAlimento(int cod, String tipo, String nomb, String desc) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT COUNT(*) AS num FROM alimento WHERE codigo = ? ";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, cod);
            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            if (rs.getInt("num") == 0) {
                query = "INSERT INTO alimento (`codigo`, `tipo`, `nombre`, `descripcion`) values (?,?,?,?)";

                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, cod);
                preparedStmt.setString(2, tipo);
                preparedStmt.setString(3, nomb);
                preparedStmt.setString(4, desc);
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

    public boolean obtenerAlimentos(DefaultTableModel dftable) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT id, codigo, tipo, nombre, descripcion FROM alimento";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Object[] object = new Object[5];
            dftable = limpiarTabla(dftable);

            while (rs.next()) {
                object[0] = rs.getInt("id");
                object[1] = rs.getInt("codigo");
                object[2] = rs.getString("tipo");
                object[3] = rs.getString("nombre");
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

    public DefaultTableModel limpiarTabla(DefaultTableModel dftable) {
        int tam = dftable.getRowCount();
        for (int i = 0; i < tam; i++) {
            dftable.removeRow(0);
        }
        return dftable;
    }

    public boolean modificarAlimento(int id, int cod, String tipo, String nomb, String desc) {
        Connection conn = c.conectar();
        try {
            String query = "UPDATE alimento SET codigo = ?, tipo = ?, nombre = ?, descripcion = ? WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, cod);
            preparedStmt.setString(2, tipo);
            preparedStmt.setString(3, nomb);
            preparedStmt.setString(4, desc);
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

    public boolean eliminarAlimento(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM alimento WHERE id = ?";

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
}
