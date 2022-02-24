package fundacion.controlador;

import basedatos.bdinventario_existencias;
import fundacion.vistas.inventario_existencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class controladorExistencia implements ActionListener {

    inventario_existencia vinvexistencias;
    bdinventario_existencias bdexistencias;

    private int id;
    private final DefaultTableModel dtModel;

    public controladorExistencia(inventario_existencia vista) {
        vinvexistencias = vista;
        bdexistencias = new bdinventario_existencias();

        this.vinvexistencias.exibtnBuscar.addActionListener(this);
        this.vinvexistencias.exibtnGuardar.addActionListener(this);
        this.vinvexistencias.exibtnModificar.addActionListener(this);
        this.vinvexistencias.exibtnEliminar.addActionListener(this);
        this.vinvexistencias.exibtnDescargar.addActionListener(this);
        this.vinvexistencias.btnRegresar.addActionListener(this);

        dtModel = (DefaultTableModel) vinvexistencias.existenciasTabla.getModel();
        bdexistencias.obtenerExistenciasTabla(dtModel);

        vinvexistencias.setLocationRelativeTo(null);
        vinvexistencias.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vinvexistencias.exibtnBuscar == e.getSource()) {
            boolean a = bdexistencias.obtenerAlimentoExistencias(Integer.parseInt(vinvexistencias.codigoExistencia.getText()), vinvexistencias.nombreExistencia);
            if (a) {
                vinvexistencias.exibtnGuardar.setEnabled(true);
            }
        }

        if (vinvexistencias.btnRegresar == e.getSource()) {//Regresar a menu principal
            vinvexistencias.dispose();
        }

        if (vinvexistencias.exibtnGuardar == e.getSource()) {//Guardar
            boolean a = bdexistencias.guardarExistencia(Integer.parseInt(vinvexistencias.codigoExistencia.getText()),
                    "" + vinvexistencias.listaPeriodo.getSelectedItem(),
                    Integer.parseInt(vinvexistencias.exiCantInicial.getText()),
                    Integer.parseInt(vinvexistencias.exiCantSalida.getText()),
                    Integer.parseInt(vinvexistencias.exiCantFisico.getText()),
                    Integer.parseInt(vinvexistencias.exiCantAjuste.getText()),
                    Integer.parseInt(vinvexistencias.exiCantFinal.getText()));

            if (a) {
                limpiarCampos();
                vinvexistencias.exibtnGuardar.setEnabled(false);
            }
        }
        
        
        if (vinvexistencias.exibtnModificar == e.getSource()) {//Actualizar
            boolean a = bdexistencias.modificarExistencia(id,
                    "" + vinvexistencias.listaPeriodo.getSelectedItem(),
                    Integer.parseInt(vinvexistencias.exiCantInicial.getText()),
                    Integer.parseInt(vinvexistencias.exiCantSalida.getText()),
                    Integer.parseInt(vinvexistencias.exiCantFisico.getText()),
                    Integer.parseInt(vinvexistencias.exiCantAjuste.getText()),
                    Integer.parseInt(vinvexistencias.exiCantFinal.getText()));

            if (a) {
                limpiarCampos();
                vinvexistencias.exibtnModificar.setEnabled(false);
                vinvexistencias.exibtnEliminar.setEnabled(false);
                vinvexistencias.exibtnDescargar.setEnabled(false);
            }
        }
        
        if (vinvexistencias.exibtnEliminar == e.getSource()) {//Actualizar
            boolean a = bdexistencias.eliminarExistencia(id);

            if (a) {
                limpiarCampos();
                vinvexistencias.exibtnModificar.setEnabled(false);
                vinvexistencias.exibtnEliminar.setEnabled(false);
                vinvexistencias.exibtnDescargar.setEnabled(false);
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
        vinvexistencias.exibtnGuardar.setEnabled(false);
        bdexistencias.obtenerExistenciasTabla(dtModel);
        vinvexistencias.codigoExistencia.setText("");
        vinvexistencias.nombreExistencia.setText("");
        vinvexistencias.exiCantInicial.setText("");
        vinvexistencias.exiCantSalida.setText("");
        vinvexistencias.exiCantFisico.setText("");
        vinvexistencias.exiCantAjuste.setText("");
        vinvexistencias.exiCantFinal.setText("");
        setId(-1);
    }
}
