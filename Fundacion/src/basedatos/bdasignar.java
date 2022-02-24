package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class bdasignar {

    Conexion c;

    public bdasignar() {
        c = new Conexion();
    }

    public boolean obtenerEmpleadoList(JList dlistm, String tipo) {
        Connection conn = c.conectar();
        String form;
        Vector v = new Vector();
        try {
            String query = "SELECT dni, nombre, apellidos FROM empleado WHERE tipo = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, tipo);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                form = rs.getString("dni") + " : " + rs.getString("nombre") + " " + rs.getString("apellidos");
                v.add(form);

            }
            dlistm.setListData(v);
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

    public boolean obtenerVehiculoList(JList dlistm) {
        Connection conn = c.conectar();
        String form;
        Vector v = new Vector();
        try {
            String query = "SELECT matricula, marca, modelo FROM vehiculo";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                form = rs.getString("matricula") + " : " + rs.getString("marca") + " " + rs.getString("modelo");
                v.add(form);

            }
            dlistm.setListData(v);
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

    public boolean obtenerRutaList(JList dlistm) {
        Connection conn = c.conectar();
        String form;
        Vector v = new Vector();
        try {
            String query = "SELECT id, lugar FROM ruta";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                form = rs.getString("id") + " : " + rs.getString("lugar");
                v.add(form);

            }
            dlistm.setListData(v);
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

    public boolean obtenerRutaAsignadaList(JList dlistm) {
        Connection conn = c.conectar();
        String form;
        Vector v = new Vector();
        try {
            String query = "SELECT r.id, ru.lugar FROM ruta_asignada r INNER JOIN ruta ru ON r.fk_ruta = ru.id";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();

            dlistm.removeAll();
            while (rs.next()) {
                form = rs.getString("id") + " : " + rs.getString("lugar");
                v.add(form);
            }
            dlistm.setListData(v);
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

    public boolean obtenerInfoRuta(int id) {
        Connection conn = c.conectar();
        String form = "";
        try {
            String query = "SELECT ra.hora_salida, ra.tiempo, emp.nombre, emp.apellidos, emp.dni, emp.telefono, "
                    + "emp2.nombre AS nomb, emp2.apellidos AS apell, emp2.dni AS d, emp2.telefono AS tel, veh.matricula, veh.marca, veh.modelo, ru.lugar "
                    + "FROM ruta_asignada ra INNER JOIN empleado emp ON (ra.fk_conductor = emp.dni) "
                    + "INNER JOIN empleado emp2 ON (ra.fk_asistente = emp2.dni) "
                    + "INNER JOIN vehiculo veh ON (ra.fk_vehiculo = veh.matricula) "
                    + "INNER JOIN ruta ru ON (ra.fk_ruta = ru.id) WHERE ra.id = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                form = "RUTA"
                        + "\nLugar: "+rs.getString("lugar").toUpperCase()
                        + "\nHora salida: "+ rs.getString("hora_salida").toUpperCase()
                        + "\nTiempo(hrs): "+ rs.getInt("tiempo")
                        + "\n\nVEHICULO"
                        + "\nMarca: "+ rs.getString("marca").toUpperCase()
                        + "\nModelo: "+ rs.getString("modelo").toUpperCase()
                        + "\nMatricula: "+ rs.getString("matricula").toUpperCase()
                        + "\n\nCONDUCTOR"
                        + "\nDni: "+ rs.getString("dni").toUpperCase()
                        + "\nNombre: "+ rs.getString("nombre").toUpperCase()+" "+ rs.getString("apellidos").toUpperCase()
                        + "\nTelefono: "+ rs.getString("telefono").toUpperCase()
                        + "\n\nASISTENTE"
                        + "\nDni: "+ rs.getString("d").toUpperCase()
                        + "\nNombre: "+ rs.getString("nomb").toUpperCase()+" "+ rs.getString("apell").toUpperCase()
                        + "\nTelefono: "+ rs.getString("tel").toUpperCase();
            }
            
            JOptionPane.showMessageDialog(null, form);
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

    public boolean guardarRuta(String h_salida, int tiemp, String coductor, String asist, String veh, int ruta) {
        Connection conn = c.conectar();
        try {
            String query = "INSERT INTO ruta_asignada (`hora_salida`, `tiempo`, `fk_conductor`, `fk_asistente`, `fk_vehiculo`, `fk_ruta`) values (?,?,?,?,?,?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, h_salida);
            preparedStmt.setInt(2, tiemp);
            preparedStmt.setString(3, coductor);
            preparedStmt.setString(4, asist);
            preparedStmt.setString(5, veh);
            preparedStmt.setInt(6, ruta);
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

    public boolean eliminarRutaAsignada(int id) {
        Connection conn = c.conectar();
        try {
            String query = "DELETE FROM ruta_asignada WHERE id = ?";

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
