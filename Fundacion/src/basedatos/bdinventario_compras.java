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

public class bdinventario_compras {

    Conexion c;

    public bdinventario_compras() {
        c = new Conexion();
    }

    public boolean guardarCompra(int pr, int com, int doc, String ali, float val, float iva, float des, int cant) {
        Connection conn = c.conectar();
        try {
            String query = "INSERT INTO inventario_compras (`proveedor`, `comprobante`, `documento`, `alimento`, `valor`, `iva`, `descuento`, `cantidad`) "
                    + "values (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, pr);
            preparedStmt.setInt(2, com);
            preparedStmt.setInt(3, doc);
            preparedStmt.setString(4, ali);
            preparedStmt.setFloat(5, val);
            preparedStmt.setFloat(6, iva);
            preparedStmt.setFloat(7, des);
            preparedStmt.setInt(8, cant);
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

    public boolean obtenerInventarioCompras(DefaultTableModel dftable) {
        Connection conn = c.conectar();
        float totalbruto = 0;
        float iva = 0;
        float descuento = 0;
        try {
            String query = "SELECT id, proveedor, comprobante, documento, alimento, valor, iva, descuento, cantidad FROM inventario_compras";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Object[] object = new Object[10];
            dftable = limpiarTabla(dftable);

            while (rs.next()) {
                object[0] = rs.getInt("id");
                object[1] = rs.getInt("proveedor");
                object[2] = rs.getInt("comprobante");
                object[3] = rs.getInt("documento");
                object[4] = rs.getString("alimento");
                object[5] = rs.getFloat("valor");
                object[6] = rs.getFloat("iva");
                object[7] = rs.getFloat("descuento");
                object[8] = rs.getInt("cantidad");
                totalbruto = rs.getFloat("valor") * rs.getInt("cantidad");
                iva = rs.getFloat("iva") / 100;
                descuento = rs.getFloat("descuento") / 100;
                totalbruto = (totalbruto + (iva * totalbruto));
                object[9] = totalbruto - (descuento * totalbruto);

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

    public boolean modificarCompra(int id, int pr, int com, int doc, String ali, float val, float iva, float des, int cant) {
        Connection conn = c.conectar();
        try {
            String query = "UPDATE inventario_compras SET proveedor = ?, comprobante = ?, documento = ?, alimento = ?, valor = ?, iva = ?, descuento = ?, cantidad = ? "
                    + "WHERE id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, pr);
            preparedStmt.setInt(2, com);
            preparedStmt.setInt(3, doc);
            preparedStmt.setString(4, ali);
            preparedStmt.setFloat(5, val);
            preparedStmt.setFloat(6, iva);
            preparedStmt.setFloat(7, des);
            preparedStmt.setInt(8, cant);
            preparedStmt.setInt(9, id);
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

    public boolean eliminarCompra(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM inventario_compras WHERE id = ?";

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
