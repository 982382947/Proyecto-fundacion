package fundacion.controlador;

import basedatos.bdinventario_compras;
import fundacion.modelo.pdf;
import fundacion.vistas.inventario_compras;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class controladorCompras implements ActionListener {

    bdinventario_compras bdinvcompras;
    pdf pdfdocument;

    private int id;
    inventario_compras invcompras;

    private final DefaultTableModel dtModel;

    public controladorCompras(inventario_compras vista) {
        id = -1;
        invcompras = vista;

        pdfdocument = new pdf();
        bdinvcompras = new bdinventario_compras();

        this.invcompras.combtnGuardar.addActionListener(this);
        this.invcompras.combtnModificar.addActionListener(this);
        this.invcompras.combtnEliminar.addActionListener(this);
        this.invcompras.combtnDescargar.addActionListener(this);
        this.invcompras.btnRegresar.addActionListener(this);

        dtModel = (DefaultTableModel) invcompras.comprasTabla.getModel();
        bdinvcompras.obtenerInventarioCompras(dtModel);
        invcompras.setLocationRelativeTo(null);
        invcompras.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (invcompras.combtnGuardar == e.getSource()) {//insertar
            boolean a = this.bdinvcompras.guardarCompra(
                    Integer.parseInt(invcompras.comproveedor.getText()),
                    Integer.parseInt(invcompras.comcomprobante.getText()),
                    Integer.parseInt(invcompras.comdocumento.getText()),
                    invcompras.comalimento.getText(),
                    Float.parseFloat(invcompras.comvalor.getText()),
                    Float.parseFloat(invcompras.comiva.getText()),
                    Float.parseFloat(invcompras.comdescuento.getText()),
                    Integer.parseInt(invcompras.comcantidad.getText()));

            if (a) {
                bdinvcompras.obtenerInventarioCompras(getDtModel());
                invcompras.comproveedor.setText("");
                invcompras.comcomprobante.setText("");
                invcompras.comdocumento.setText("");
                invcompras.comalimento.setText("");
                invcompras.comvalor.setText("");
                invcompras.comiva.setText("");
                invcompras.comdescuento.setText("");
                invcompras.comcantidad.setText("");
                setId(-1);
            }
        }

        if (invcompras.combtnModificar == e.getSource()) {//actualizar
            if (id != -1) {
                boolean a = this.bdinvcompras.modificarCompra(id,
                        Integer.parseInt(invcompras.comproveedor.getText()),
                        Integer.parseInt(invcompras.comcomprobante.getText()),
                        Integer.parseInt(invcompras.comdocumento.getText()),
                        invcompras.comalimento.getText(),
                        Float.parseFloat(invcompras.comvalor.getText()),
                        Float.parseFloat(invcompras.comiva.getText()),
                        Float.parseFloat(invcompras.comdescuento.getText()),
                        Integer.parseInt(invcompras.comcantidad.getText()));

                if (a) {
                    bdinvcompras.obtenerInventarioCompras(getDtModel());

                    invcompras.comproveedor.setText("");
                    invcompras.comcomprobante.setText("");
                    invcompras.comdocumento.setText("");
                    invcompras.comalimento.setText("");
                    invcompras.comvalor.setText("");
                    invcompras.comiva.setText("");
                    invcompras.comdescuento.setText("");
                    invcompras.comcantidad.setText("");

                    setId(-1);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para modificar");
            }
        }

        if (invcompras.combtnEliminar == e.getSource()) {//ELIMINAR
            if (id != -1) {
                boolean a = this.bdinvcompras.eliminarCompra(id);

                if (a) {
                    bdinvcompras.obtenerInventarioCompras(getDtModel());
                    invcompras.comproveedor.setText("");
                    invcompras.comcomprobante.setText("");
                    invcompras.comdocumento.setText("");
                    invcompras.comalimento.setText("");
                    invcompras.comvalor.setText("");
                    invcompras.comiva.setText("");
                    invcompras.comdescuento.setText("");
                    invcompras.comcantidad.setText("");
                    setId(-1);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para eliminar");
            }
        }

        if (invcompras.combtnDescargar == e.getSource()) {//Generar pdf
            if (id != -1) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar documento");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showSaveDialog(new Frame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    boolean a = this.pdfdocument.generarPDF(
                            Integer.parseInt(invcompras.comproveedor.getText()),
                            Integer.parseInt(invcompras.comcomprobante.getText()),
                            Integer.parseInt(invcompras.comdocumento.getText()),
                            invcompras.comalimento.getText(),
                            Float.parseFloat(invcompras.comvalor.getText()),
                            Float.parseFloat(invcompras.comiva.getText()),
                            Float.parseFloat(invcompras.comdescuento.getText()),
                            Integer.parseInt(invcompras.comcantidad.getText()),
                            file.getAbsolutePath());

                    if (a) {
                        bdinvcompras.obtenerInventarioCompras(getDtModel());
                        invcompras.comproveedor.setText("");
                        invcompras.comcomprobante.setText("");
                        invcompras.comdocumento.setText("");
                        invcompras.comalimento.setText("");
                        invcompras.comvalor.setText("");
                        invcompras.comiva.setText("");
                        invcompras.comdescuento.setText("");
                        invcompras.comcantidad.setText("");
                        setId(-1);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para descargar en formato PDF");
            }
        }
        
        if (invcompras.btnRegresar == e.getSource()) {//Regresar a menu principal
            invcompras.dispose();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public DefaultTableModel getDtModel() {
        return dtModel;
    }
}
