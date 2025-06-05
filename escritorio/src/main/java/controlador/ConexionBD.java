package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD INSTANCIA = null;
    private Connection conexion = null;

    private ConexionBD(String url, String usuario, String pwd) throws SQLException {
        conexion = DriverManager.getConnection(url, usuario, pwd);
    }

    public static ConexionBD getInstancia(String url, String usuario, String pwd) throws SQLException {
        if (INSTANCIA == null) {
            INSTANCIA = new ConexionBD(url, usuario, pwd);
        }
        return INSTANCIA;
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public void cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            INSTANCIA = null;
        }
    }
}
