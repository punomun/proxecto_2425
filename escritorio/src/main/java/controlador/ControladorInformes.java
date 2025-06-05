package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import vista.dialog.DialogInformes;

public class ControladorInformes {

    private ConexionBD conexionBD;
    private DialogInformes dialogo;
    private JFileChooser jfc;

    private final Properties propiedades;

    public final int CONSULTA_BASICA_ARTISTAS = 1;
    public final int CONSULTA_BASICA_EVENTOS = 2;
    public final int CONSULTA_EVENTOS_EN_RANGO = 3;
    public final int CONSULTA_EVENTOS_DE_ARTISTA = 4;

    public ControladorInformes(Properties propiedades) {
        this.propiedades = propiedades;
    }

    private void conectarBD() {
        try {
            this.conexionBD = ConexionBD.getInstancia(propiedades.getProperty("bd.url"), propiedades.getProperty("bd.user"), propiedades.getProperty("bd.pwd"));
        } catch (Exception e) {
            this.conexionBD = null;
        }
    }

    public void setDialogo(DialogInformes dialogo) {
        this.dialogo = dialogo;
    }

    public void generarInforme(int consulta) {
        conectarBD();
        if (conexionBD == null) {
            JOptionPane.showMessageDialog(dialogo, "No se ha podido conectar a la base de datos");
            return;
        }
        Connection conn = conexionBD.getConexion();
        if (conn == null) {
            JOptionPane.showMessageDialog(dialogo, "No se ha podido conectar a la base de datos");
            return;
        }

        jfc = new JFileChooser();

        StringBuilder rutaInforme = new StringBuilder("plantillas_informes/");
        Map<String, Object> mapa = new HashMap<>();

        switch (consulta) {
            case CONSULTA_BASICA_ARTISTAS -> {
                rutaInforme.append("informe_basico_artistas.jrxml");
            }
            case CONSULTA_BASICA_EVENTOS -> {
                rutaInforme.append("informe_basico_eventos.jrxml");
            }
            case CONSULTA_EVENTOS_EN_RANGO -> {
                Date fechaIni = dialogo.getJdcFechaIni().getDate();
                Date fechaFin = dialogo.getJdcFechaFin().getDate();
                if (fechaIni == null || fechaFin == null) {
                    JOptionPane.showMessageDialog(dialogo, "Se deben seleccionar dos fechas");
                    return;
                }
                rutaInforme.append("eventos_rango.jrxml");
                mapa.put("FECHA_INI", fechaIni);
                mapa.put("FECHA_FIN", fechaFin);
            }
            default -> {
                rutaInforme.append("eventos_de_artista.jrxml");
                try {
                    if (dialogo.getTxtFldInformeId().getText().isBlank()) {
                        JOptionPane.showMessageDialog(dialogo, "Falta el ID");
                        return;
                    }
                    int idArtista = Integer.parseInt(dialogo.getTxtFldInformeId().getText());
                    mapa.put("ID_ARTISTA", idArtista);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(dialogo, "El ID debe ser numérico");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al generar el informe");
                }
            }
        }

        if (jfc.showOpenDialog(dialogo) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File fichero = jfc.getSelectedFile().getName().endsWith(".pdf") ? jfc.getSelectedFile() : new File(jfc.getSelectedFile() + ".pdf");

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(rutaInforme.toString())) {
            JasperReport jr = JasperCompileManager.compileReport(is);
            JasperPrint jp = JasperFillManager.fillReport(jr, mapa, conn);

            if (jp.getPages() == null || jp.getPages().size() < 1) {
                JOptionPane.showMessageDialog(dialogo, "No existen datos para los parámetros introducidos");
                dialogo.getLblGenerando().setVisible(false);
                return;
            }

            JasperExportManager.exportReportToPdfFile(jp, fichero.getAbsolutePath());
            dialogo.getLblGenerando().setText("Informe generado correctamente");
            Desktop.getDesktop().open(fichero);
        } catch (Exception e) {
            dialogo.getLblGenerando().setVisible(false);
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al generar el informe");
        }
    }
}
