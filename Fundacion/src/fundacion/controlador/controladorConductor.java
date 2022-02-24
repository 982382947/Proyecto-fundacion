package fundacion.controlador;

import basedatos.bdconductor;
import fundacion.vistas.vista_Conductor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class controladorConductor implements ActionListener {

    vista_Conductor vConductor;
    bdconductor bdconductor;

    private int id;
    private final DefaultTableModel dtModel;

    public controladorConductor(vista_Conductor vista) {
        vConductor = vista;
        bdconductor = new bdconductor();

        this.vConductor.btnGuardar.addActionListener(this);
        this.vConductor.btnModificar.addActionListener(this);
        this.vConductor.btnEliminar.addActionListener(this);
        this.vConductor.btnRegresar.addActionListener(this);

        dtModel = (DefaultTableModel) vConductor.conductorTabla.getModel();
        bdconductor.obtenerEmpleadosTabla(dtModel);

        vConductor.setLocationRelativeTo(null);
        vConductor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vConductor.btnRegresar == e.getSource()) {//Regresar a menu principal
            vConductor.dispose();
        }

        if (vConductor.btnGuardar == e.getSource()) {//Guardar
            boolean a = bdconductor.guardarEmpleado(
                    vConductor.nombre.getText(),
                    vConductor.apellidos.getText(),
                    "" + vConductor.listaTipo.getSelectedItem(),
                    vConductor.ndocumento.getText(),
                    vConductor.telefono.getText(),
                    vConductor.direccion.getText());

            if (a) {
                limpiarCampos();
            }else{
                JOptionPane.showMessageDialog(null, "Este DNI ya se encuentra registrado");
            }
        }

        if (vConductor.btnModificar == e.getSource()) {//Actualizar
            boolean a = bdconductor.modificarEmpleado(id, 
                    vConductor.nombre.getText(),
                    vConductor.apellidos.getText(),
                    "" + vConductor.listaTipo.getSelectedItem(),
                    vConductor.ndocumento.getText(),
                    vConductor.telefono.getText(),
                    vConductor.direccion.getText());

            if (a) {
                limpiarCampos();
            }
        }

        if (vConductor.btnEliminar == e.getSource()) {//Actualizar
            boolean a = bdconductor.eliminarEmpleado(id);

            if (a) {
                limpiarCampos();
            }
        }
    }

    public DefaultTableModel getDtModel() {
        return dtModel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void limpiarCampos() {
        bdconductor.obtenerEmpleadosTabla(dtModel);
        vConductor.nombre.setText("");
        vConductor.apellidos.setText("");
        vConductor.ndocumento.setText("");
        vConductor.telefono.setText("");
        vConductor.direccion.setText("");
        setId(-1);
    }
}
