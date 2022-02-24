package fundacion.controlador;

import basedatos.bdlogin;
import fundacion.vistas.Login;
import fundacion.vistas.MenuPrincipal;
import fundacion.vistas.inventario_compras;
import fundacion.vistas.inventario_existencia;
import fundacion.vistas.vistaAsignar;
import fundacion.vistas.vista_Alimentos;
import fundacion.vistas.vista_Conductor;
import fundacion.vistas.vista_Vehiculo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class controladorPrincipal implements ActionListener {

    private final Login vista;
    MenuPrincipal mPrincipal;
    inventario_compras invcompras;
    vista_Alimentos vAlimentos;
    inventario_existencia vinvexistencias;
    vista_Vehiculo vvehiculo;
    vista_Conductor vconductor;
    vistaAsignar vasignar;
    

    bdlogin bdlog;
    
    controladorCompras contCompras;
    controladorExistencia contExistencia;
    controladorAlimentos conAlimentos;
    controladorVehiculo conVehiculo;
    controladorConductor conConductor;
    controladorAsignar conAsignar;
            

    public controladorPrincipal(Login vista) {
        this.vista = vista;
        mPrincipal = new MenuPrincipal();
        invcompras = new inventario_compras();
        vAlimentos = new vista_Alimentos();
        vinvexistencias = new inventario_existencia();
        vvehiculo = new vista_Vehiculo();
        vconductor = new vista_Conductor();
        vasignar = new vistaAsignar();

        this.vista.lacceder.addActionListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("Inventario");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (vista.lacceder == e.getSource()) {
            bdlog = new bdlogin();
            boolean r = bdlog.Login(vista.logusuario.getText(), vista.logpassword.getText());

            if (r) {
                this.mPrincipal.listainventario.addActionListener(this);
                this.mPrincipal.listaruta.addActionListener(this);
                this.mPrincipal.listaalimentos.addActionListener(this);

                mPrincipal.setLocationRelativeTo(null);
                mPrincipal.setVisible(r);
                vista.dispose();
            }
        }

        if (mPrincipal.listainventario == e.getSource()) {//Menu lista inventario
            if (mPrincipal.listainventario.getSelectedIndex() == 1) {
                contCompras = new controladorCompras(invcompras);
                
            }else if(mPrincipal.listainventario.getSelectedIndex() == 3){
                contExistencia = new controladorExistencia(vinvexistencias);
            }
        }
        
        if (mPrincipal.listaalimentos == e.getSource()) {//Menu lista alimento
            if (mPrincipal.listaalimentos.getSelectedIndex() == 1) {
                conAlimentos = new controladorAlimentos(vAlimentos);
            }
        }
        
        if (mPrincipal.listaruta == e.getSource()) {//Menu lista ruta
            if (mPrincipal.listaruta.getSelectedIndex() == 1) {
                conAsignar = new controladorAsignar(vasignar);
            }
            
            if (mPrincipal.listaruta.getSelectedIndex() == 2) {
                conConductor = new controladorConductor(vconductor);
            }
            
            if (mPrincipal.listaruta.getSelectedIndex() == 3) {
                conVehiculo = new controladorVehiculo(vvehiculo);
            }
        }
        
        invcompras.comprasTabla.addMouseListener(new MouseAdapter() {//evento de seleccion en la tabla inventario compras
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = invcompras.comprasTabla.rowAtPoint(e.getPoint());
                int columna = invcompras.comprasTabla.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    contCompras.setId((int) contCompras.getDtModel().getValueAt(fila, 0));
                    invcompras.comproveedor.setText(contCompras.getDtModel().getValueAt(fila, 1).toString());
                    invcompras.comcomprobante.setText((String) contCompras.getDtModel().getValueAt(fila, 2).toString());
                    invcompras.comdocumento.setText((String) contCompras.getDtModel().getValueAt(fila, 3).toString());
                    invcompras.comalimento.setText((String) contCompras.getDtModel().getValueAt(fila, 4).toString());
                    invcompras.comvalor.setText((String) contCompras.getDtModel().getValueAt(fila, 5).toString());
                    invcompras.comiva.setText((String) contCompras.getDtModel().getValueAt(fila, 6).toString());
                    invcompras.comdescuento.setText((String) contCompras.getDtModel().getValueAt(fila, 7).toString());
                    invcompras.comcantidad.setText((String) contCompras.getDtModel().getValueAt(fila, 8).toString());
                }
            }
        });
        
        
        vAlimentos.alimentosTabla.addMouseListener(new MouseAdapter() {//evento de seleccion en la tabla alimentos
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vAlimentos.alimentosTabla.rowAtPoint(e.getPoint());
                int columna = vAlimentos.alimentosTabla.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    conAlimentos.setId((int) conAlimentos.getDtModel().getValueAt(fila, 0));
                    vAlimentos.codigoAlimento.setText(conAlimentos.getDtModel().getValueAt(fila, 1).toString());
                    vAlimentos.tipoAlimento.setText((String) conAlimentos.getDtModel().getValueAt(fila, 2).toString());
                    vAlimentos.nombreAlimento.setText((String) conAlimentos.getDtModel().getValueAt(fila, 3).toString());
                    vAlimentos.descripcionAlimento.setText((String) conAlimentos.getDtModel().getValueAt(fila, 4).toString());
                }
            }
        });
        
        
        vinvexistencias.existenciasTabla.addMouseListener(new MouseAdapter() {//evento de seleccion en la tabla inventario existencias
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vinvexistencias.existenciasTabla.rowAtPoint(e.getPoint());
                int columna = vinvexistencias.existenciasTabla.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    contExistencia.setId((int) contExistencia.getDtModel().getValueAt(fila, 0));
                    vinvexistencias.codigoExistencia.setText(contExistencia.getDtModel().getValueAt(fila, 1).toString());
                    vinvexistencias.nombreExistencia.setText((String) contExistencia.getDtModel().getValueAt(fila, 2).toString());
                    vinvexistencias.listaPeriodo.setSelectedItem( contExistencia.getDtModel().getValueAt(fila, 3).toString());
                    vinvexistencias.exiCantInicial.setText((String) contExistencia.getDtModel().getValueAt(fila, 4).toString());
                    vinvexistencias.exiCantSalida.setText((String) contExistencia.getDtModel().getValueAt(fila, 5).toString());
                    vinvexistencias.exiCantFisico.setText((String) contExistencia.getDtModel().getValueAt(fila, 6).toString());
                    vinvexistencias.exiCantAjuste.setText((String) contExistencia.getDtModel().getValueAt(fila, 7).toString());
                    vinvexistencias.exiCantFinal.setText((String) contExistencia.getDtModel().getValueAt(fila, 8).toString());
                    vinvexistencias.exibtnModificar.setEnabled(true);
                    vinvexistencias.exibtnEliminar.setEnabled(true);
                    vinvexistencias.exibtnDescargar.setEnabled(true);
                }
            }
        });
        
        
        
        vvehiculo.vehiculoTabla.addMouseListener(new MouseAdapter() {//evento de seleccion en la tabla vehiculo
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vvehiculo.vehiculoTabla.rowAtPoint(e.getPoint());
                int columna = vvehiculo.vehiculoTabla.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    conVehiculo.setId((int) conVehiculo.getDtModel().getValueAt(fila, 0));
                    vvehiculo.vehMarca.setText(conVehiculo.getDtModel().getValueAt(fila, 1).toString());
                    vvehiculo.vehModelo.setText((String) conVehiculo.getDtModel().getValueAt(fila, 2).toString());
                    vvehiculo.vehMatricula.setText( conVehiculo.getDtModel().getValueAt(fila, 3).toString());
                    vvehiculo.vehDescripcion.setText((String) conVehiculo.getDtModel().getValueAt(fila, 4).toString());
                }
            }
        });
        
        
        vconductor.conductorTabla.addMouseListener(new MouseAdapter() {//evento de seleccion en la tabla conductor
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vconductor.conductorTabla.rowAtPoint(e.getPoint());
                int columna = vconductor.conductorTabla.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    conConductor.setId((int) conConductor.getDtModel().getValueAt(fila, 0));
                    vconductor.nombre.setText(conConductor.getDtModel().getValueAt(fila, 1).toString());
                    vconductor.apellidos.setText((String) conConductor.getDtModel().getValueAt(fila, 2).toString());
                    vconductor.listaTipo.setSelectedItem( conConductor.getDtModel().getValueAt(fila, 3).toString());
                    vconductor.ndocumento.setText((String) conConductor.getDtModel().getValueAt(fila, 4).toString());
                    vconductor.telefono.setText((String) conConductor.getDtModel().getValueAt(fila, 5).toString());
                    vconductor.direccion.setText((String) conConductor.getDtModel().getValueAt(fila, 6).toString());
                }
            }
        });
    }
}
