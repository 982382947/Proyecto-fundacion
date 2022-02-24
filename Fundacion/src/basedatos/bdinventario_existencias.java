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

public class bdinventario_existencias {

    Conexion c;

    public bdinventario_existencias() {
        c = new Conexion();
    }
    
    public boolean obtenerExistenciasTabla(DefaultTableModel dftable) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT i.id, a.nombre, i.periodo, i.cant_inicial, i.cant_salida, i.cant_fisico, i.cant_ajuste, i.cant_final, i.fk_codigo_alimento FROM inventario_existencias i"
                    + " INNER JOIN alimento a ON a.codigo = i.fk_codigo_alimento";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Object[] object = new Object[9];
            dftable = limpiarTabla(dftable);
            
            while (rs.next()) {
                object[0] = rs.getInt("i.id");
                object[1] = rs.getInt("i.fk_codigo_alimento");
                object[2] = rs.getString("a.nombre");
                object[3] = rs.getString("i.periodo");
                object[4] = rs.getInt("i.cant_inicial");
                object[5] = rs.getInt("i.cant_salida");
                object[6] = rs.getInt("i.cant_fisico");
                object[7] = rs.getInt("i.cant_ajuste");
                object[8] = rs.getInt("i.cant_final");
                
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

    public boolean obtenerAlimentoExistencias(int cod, JTextField nomb) {
        Connection conn = c.conectar();
        try {
            String query = "SELECT nombre FROM alimento WHERE codigo = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, cod);
            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            nomb.setText(rs.getString("nombre"));
            
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
    
    
    public boolean guardarExistencia(int cod, String periodo, int cant_inicial, int cant_salida, int cant_fisico, int cant_ajuste, int cant_final) {
        Connection conn = c.conectar();
        try {
            String query = "INSERT INTO inventario_existencias (`periodo`, `cant_inicial`, `cant_salida`, `cant_fisico`, `cant_ajuste`, `cant_final`, `fk_codigo_alimento`)"
                    + " values (?,?,?,?,?,?,?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, periodo);
            preparedStmt.setInt(2, cant_inicial);
            preparedStmt.setInt(3, cant_salida);
            preparedStmt.setInt(4, cant_fisico);
            preparedStmt.setInt(5, cant_ajuste);
            preparedStmt.setInt(6, cant_final);
            preparedStmt.setInt(7, cod);
            preparedStmt.execute();
            JOptionPane.showMessageDialog(null, "Registro guardado");
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
    
    public boolean modificarExistencia(int id, String periodo, int cant_inicial, int cant_salida, int cant_fisico, int cant_ajuste, int cant_final) {
        Connection conn = c.conectar();
        try {
            String query = "UPDATE inventario_existencias SET periodo = ?, cant_inicial = ?, cant_salida = ?, cant_fisico = ?, cant_ajuste = ?, cant_final = ? WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, periodo);
            preparedStmt.setInt(2, cant_inicial);
            preparedStmt.setInt(3, cant_salida);
            preparedStmt.setInt(4, cant_fisico);
            preparedStmt.setInt(5, cant_ajuste);
            preparedStmt.setInt(6, cant_final);
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
    
    
    public boolean eliminarExistencia(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM inventario_existencias WHERE id = ?";

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
