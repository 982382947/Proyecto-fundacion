package fundacion;

import fundacion.controlador.controladorPrincipal;
import fundacion.vistas.Login;

public class Fundacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Login vista = new Login();
        controladorPrincipal controlador = new controladorPrincipal(vista);
        controlador.iniciarVista();
    }
}
