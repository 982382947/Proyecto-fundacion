package fundacion.controlador;

import basedatos.bdvehiculo;
import fundacion.vistas.vista_Vehiculo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class controladorVehiculo implements ActionListener {

    vista_Vehiculo vvehiculo;
    bdvehiculo bdvehiculo;

    private int id;
    private final DefaultTableModel dtModel;

    public controladorVehiculo(vista_Vehiculo vista) {
        vvehiculo = vista;
        bdvehiculo = new bdvehiculo();

        this.vvehiculo.btnGuardar.addActionListener(this);
        this.vvehiculo.btnModificar.addActionListener(this);
        this.vvehiculo.btnEliminar.addActionListener(this);
        this.vvehiculo.btnRegresar.addActionListener(this);

        dtModel = (DefaultTableModel) vvehiculo.vehiculoTabla.getModel();
        bdvehiculo.obtenerVehiculoTabla(dtModel);

        vvehiculo.setLocationRelativeTo(null);
        vvehiculo.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (vvehiculo.btnRegresar == e.getSource()) {//Regresar a menu principal
            vvehiculo.dispose();
        }

        if (vvehiculo.btnGuardar == e.getSource()) {//Guardar
            boolean a = bdvehiculo.guardarVehiculo(
                    vvehiculo.vehMarca.getText(),
                    vvehiculo.vehModelo.getText(),
                    vvehiculo.vehMatricula.getText(),
                    vvehiculo.vehDescripcion.getText());

            if (a) {
                limpiarCampos();
            }else{
                JOptionPane.showMessageDialog(null, "La matricula ya se encuentra registrada");
            }
        }
        
        
        if (vvehiculo.btnModificar == e.getSource()) {//Actualizar
            boolean a = bdvehiculo.modificarVehiculo(id,
                    vvehiculo.vehMarca.getText(),
                    vvehiculo.vehModelo.getText(),
                    vvehiculo.vehMatricula.getText(),
                    vvehiculo.vehDescripcion.getText());

            if (a) {
                limpiarCampos();
            }
        }
        
        if (vvehiculo.btnEliminar == e.getSource()) {//Actualizar
            boolean a = bdvehiculo.eliminarVehiculo(id);

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
        bdvehiculo.obtenerVehiculoTabla(dtModel);
        vvehiculo.vehMarca.setText("");
        vvehiculo.vehModelo.setText("");
        vvehiculo.vehMatricula.setText("");
        vvehiculo.vehDescripcion.setText("");
        setId(-1);
    }
}
