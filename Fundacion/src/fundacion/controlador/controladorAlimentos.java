package fundacion.controlador;

import basedatos.bdalimentos;
import fundacion.modelo.pdf;
import fundacion.vistas.vista_Alimentos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class controladorAlimentos implements ActionListener {

    bdalimentos bdalimentos;
    pdf pdfdocument;

    private int id;
    vista_Alimentos vAlimentos;

    private final DefaultTableModel dtModel;

    public controladorAlimentos(vista_Alimentos vista) {
        id = -1;
        vAlimentos = vista;

        //pdfdocument = new pdf();
        bdalimentos = new bdalimentos();

        this.vAlimentos.alimbtnGuardar.addActionListener(this);
        this.vAlimentos.alimbtnModificar.addActionListener(this);
        this.vAlimentos.alimbtnEliminar.addActionListener(this);
        this.vAlimentos.alimbtnDescargar.addActionListener(this);
        this.vAlimentos.btnRegresar.addActionListener(this);

        dtModel = (DefaultTableModel) vAlimentos.alimentosTabla.getModel();
        bdalimentos.obtenerAlimentos(dtModel);
        vAlimentos.setLocationRelativeTo(null);
        vAlimentos.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vAlimentos.btnRegresar == e.getSource()) {//Regresar a menu principal
            vAlimentos.dispose();
        }

        if (vAlimentos.alimbtnGuardar == e.getSource()) {//insertar
            boolean a = this.bdalimentos.guardarAlimento(
                    Integer.parseInt(vAlimentos.codigoAlimento.getText()),
                    vAlimentos.tipoAlimento.getText(),
                    vAlimentos.nombreAlimento.getText(),
                    vAlimentos.descripcionAlimento.getText());

            if (a) {
                limpiarCampos();
            }else{
                JOptionPane.showMessageDialog(null, "Este codigo de alimento ya se encuentra registrado");
            }
        }

        if (vAlimentos.alimbtnModificar == e.getSource()) {//actualizar
            if (id != -1) {
                boolean a = this.bdalimentos.modificarAlimento(id,
                        Integer.parseInt(vAlimentos.codigoAlimento.getText()),
                        vAlimentos.tipoAlimento.getText(),
                        vAlimentos.nombreAlimento.getText(),
                        vAlimentos.descripcionAlimento.getText());

                if (a) {
                    limpiarCampos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para modificar");
            }
        }

        if (vAlimentos.alimbtnEliminar == e.getSource()) {//ELIMINAR
            if (id != -1) {
                boolean a = this.bdalimentos.eliminarAlimento(id);

                if (a) {
                    limpiarCampos();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
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
        bdalimentos.obtenerAlimentos(getDtModel());
        vAlimentos.codigoAlimento.setText("");
        vAlimentos.tipoAlimento.setText("");
        vAlimentos.nombreAlimento.setText("");
        vAlimentos.descripcionAlimento.setText("");
        setId(-1);
    }
}
