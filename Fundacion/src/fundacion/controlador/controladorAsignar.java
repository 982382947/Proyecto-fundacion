package fundacion.controlador;

import basedatos.bdasignar;
import basedatos.bdlogin;
import fundacion.vistas.vistaAsignar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class controladorAsignar implements ActionListener {

    vistaAsignar vasignar;
    bdasignar bdasignar;
    bdlogin bdlogin;

    private int id;

    public controladorAsignar(vistaAsignar vista) {
        vasignar = vista;
        bdasignar = new bdasignar();

        this.vasignar.btnCrear.addActionListener(this);
        this.vasignar.btnDetalles.addActionListener(this);
        this.vasignar.btnEliminar.addActionListener(this);
        this.vasignar.btnRegresar.addActionListener(this);
        this.vasignar.btnLogin.addActionListener(this);

        bdasignar.obtenerEmpleadoList(vasignar.listaConductor, "Conductor");
        bdasignar.obtenerEmpleadoList(vasignar.listaAyudante, "Asistente");
        bdasignar.obtenerVehiculoList(vasignar.listaVehiculo);
        bdasignar.obtenerRutaList(vasignar.listaRuta);
        bdasignar.obtenerRutaAsignadaList(vasignar.listaRutaAsignada);

        vasignar.setLocationRelativeTo(null);
        vasignar.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (vasignar.btnRegresar == e.getSource()) {//Regresar a menu principal
            vasignar.dispose();
        }

        if (vasignar.btnLogin == e.getSource()) {
            bdlogin = new bdlogin();
            boolean r = bdlogin.Login(vasignar.Usuario.getText(), vasignar.Password.getText());

            if (r) {
                vasignar.btnCrear.setEnabled(true);
                vasignar.btnEliminar.setEnabled(true);
                vasignar.Usuario.setText("");
                vasignar.Password.setText("");
                vasignar.btnLogin.setEnabled(false);
            } else {
                vasignar.Password.setText("");
                JOptionPane.showMessageDialog(null, "El usuario o contraseña es incorrecto");
            }
        }

        if (vasignar.btnCrear == e.getSource()) {//Guardar
            if(vasignar.listaConductor.getSelectedValue() != null && vasignar.listaAyudante.getSelectedValue() != null 
                    && vasignar.listaVehiculo.getSelectedValue() != null && vasignar.listaRuta.getSelectedValue() != null){
                String t = (String)vasignar.listaTiempo.getSelectedItem();
                String []v = t.split(" ");
                boolean a = bdasignar.guardarRuta((String)vasignar.listaHora.getSelectedItem(),
                        Integer.parseInt(v[0]),
                        sacarId(vasignar.listaConductor.getSelectedValue()),
                        sacarId(vasignar.listaAyudante.getSelectedValue()),
                        sacarId(vasignar.listaVehiculo.getSelectedValue()),
                        Integer.parseInt(sacarId(vasignar.listaRuta.getSelectedValue())));
                
                if(a){
                     bdasignar.obtenerRutaAsignadaList(vasignar.listaRutaAsignada);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Falta informacion por seleccionar");
            }
        }
        
        
        if (vasignar.btnDetalles == e.getSource()) {//Detalles
            if(vasignar.listaRutaAsignada.getSelectedValue() != null){
                boolean a = bdasignar.obtenerInfoRuta(Integer.parseInt(sacarId(vasignar.listaRutaAsignada.getSelectedValue())));
                
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta");
            }
        }
        
        if (vasignar.btnEliminar == e.getSource()) {//Actualizar
            if(vasignar.listaRutaAsignada.getSelectedValue() != null){
                boolean a = bdasignar.eliminarRutaAsignada(Integer.parseInt(sacarId(vasignar.listaRutaAsignada.getSelectedValue())));
                if(a){
                     bdasignar.obtenerRutaAsignadaList(vasignar.listaRutaAsignada);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar una ruta");
            }
        }
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String sacarId(String format){
        String []p = format.split(":");
        return p[0].trim();
    }

}
