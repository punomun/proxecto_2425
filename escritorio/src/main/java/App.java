
import controlador.ControladorPrincipal;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControladorPrincipal::new);
    }
}
