package controlador;

import controlador.restclient.ArtistaClient;
import controlador.restclient.EventoClient;
import controlador.restclient.ActuacionClient;
import controlador.restclient.UsuarioClient;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Artista;
import modelo.Evento;
import modelo.Usuario;
import vista.panel.PanelDeInicio;
import vista.panel.PanelPrincipal;
import vista.Ventana;
import vista.dialog.DialogActuacion;
import vista.dialog.DialogArtista;
import vista.dialog.DialogEvento;
import vista.dialog.DialogInformes;
import vista.dialog.DialogOpciones;
import vista.dialog.DialogResultadoGet;
import vista.dialog.DialogUsuario;

public class ControladorPrincipal {

    private Ventana ventana;

    private PanelDeInicio panelInicio;
    private PanelPrincipal panelPpal;

    private DialogOpciones dialogOpciones;

    private ConexionRest conexion;

    private ArtistaClient artistaClient;
    private EventoClient eventoClient;
    private ActuacionClient actuacionClient;
    private UsuarioClient usuarioClient;

    private Properties propiedades;
    private final String RUTA_CONFIG = "configuracion.properties";

    public final int OPCION_ARTISTA = 1;
    public final int OPCION_EVENTO = 2;
    public final int OPCION_ACTUACION = 3;
    public final int OPCION_USUARIO = 4;

    public ControladorPrincipal() {
        iniciarApp();
    }

    private void iniciarApp() {
        ventana = new Ventana(this);
        panelInicio = new PanelDeInicio();
        panelPpal = new PanelPrincipal(this);
        ventana.setContentPane(panelInicio);

        conexion = new ConexionRest();
        artistaClient = new ArtistaClient(conexion);
        eventoClient = new EventoClient(conexion);
        actuacionClient = new ActuacionClient(conexion);
        usuarioClient = new UsuarioClient(conexion);

        panelPpal.getLblMetodos().setVisible(false);
        panelPpal.getBtnDel().setVisible(false);
        panelPpal.getBtnGet().setVisible(false);
        panelPpal.getBtnPost().setVisible(false);
        panelPpal.getBtnPut().setVisible(false);

        cargarLafs();

        conectar();
        SwingUtilities.invokeLater(() -> {
            ventana.setVisible(true);
        });
    }

    private void cargarLafs() {
        JMenu menuLaf = ventana.getMenuLookAndFeel();
        ButtonGroup bg = new ButtonGroup();
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(info.getName());
            bg.add(item);

            if ("Windows".equals(info.getName()) && System.getProperty("os.name").toLowerCase().contains("win")) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                        SwingUtilities.updateComponentTreeUI(ventana);
                        item.setSelected(true);

                        ventana.pack();
                        ventana.revalidate();
                        ventana.repaint();
                    } catch (Exception e) {
                    }
                });
            }

            item.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(ventana);

                    ventana.pack();
                    ventana.revalidate();
                    ventana.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ventana, "No se ha podido cambiar", null, JOptionPane.WARNING_MESSAGE);
                }
            });
            menuLaf.add(item);
        }
    }

    private void conectar() {
        try (InputStream is = new FileInputStream(RUTA_CONFIG)) {

            propiedades = new Properties();
            propiedades.load(is);

            String url = propiedades.getProperty("rest.url");
            int port = Integer.parseInt(propiedades.getProperty("rest.port"));

            try (Socket socket = new Socket(url, port)) {
                ConexionRest.urlRest = "http://" + url + ":" + port;

                SwingUtilities.updateComponentTreeUI(ventana);
                ventana.setContentPane(panelPpal);
                ventana.revalidate();
                ventana.repaint();
                ventana.pack();
            }
        } catch (Exception e) {
            StringBuilder fallo = new StringBuilder("<html><body style='width:100%; text-align: justify;'>");
            fallo.append("<p style='text-align: center'>La conexión ha fallado.</p>");
            fallo.append("<ol>");
            fallo.append("<li>Comprueba que el servicio está activo.</li>");
            fallo.append("<li>Comprueba los parámetros de conexión.</li>");
            fallo.append("<li>Inténtalo de nuevo.</li>");
            fallo.append("</ol></body></html>");
            panelInicio.getLblConexion().setText(fallo.toString());
            ventana.setContentPane(panelInicio);
            ventana.revalidate();
            ventana.repaint();
            ventana.pack();
        }
    }

    public void mostrarMetodos() {
        if (!panelPpal.getLblMetodos().isVisible()) {
            panelPpal.getLblMetodos().setVisible(true);
        }

        if (!panelPpal.getBtnDel().isVisible()) {
            panelPpal.getBtnDel().setVisible(true);
        }

        if (!panelPpal.getBtnGet().isVisible()) {
            panelPpal.getBtnGet().setVisible(true);
        }

        if (!panelPpal.getBtnPost().isVisible()) {
            panelPpal.getBtnPost().setVisible(true);
        }

        if (!panelPpal.getBtnPut().isVisible()) {
            panelPpal.getBtnPut().setVisible(true);
        }
        ventana.pack();
    }

    public JDialog lanzarDialog(int tabla, String metodo) {
        JDialog nuevoDialogo;
        switch (tabla) {
            case OPCION_ARTISTA ->
                nuevoDialogo = new DialogArtista(ventana, false, metodo, this);
            case OPCION_EVENTO ->
                nuevoDialogo = new DialogEvento(ventana, false, metodo, this);
            case OPCION_ACTUACION -> {
                if ("PUT".equals(metodo)) {
                    JOptionPane.showMessageDialog(ventana, "No se pueden modificar las actuaciones");
                    return null;
                }
                nuevoDialogo = new DialogActuacion(ventana, false, metodo, this);
            }
            default ->
                nuevoDialogo = new DialogUsuario(ventana, false, metodo, this);
        }
        nuevoDialogo.setVisible(true);
        return nuevoDialogo;
    }

    public void get(JDialog dialogo, int tabla) {
        switch (tabla) {
            case OPCION_ARTISTA ->
                getArtista((DialogArtista) dialogo);
            case OPCION_EVENTO ->
                getEvento((DialogEvento) dialogo);
            case OPCION_ACTUACION ->
                getActuacion((DialogActuacion) dialogo);
            case OPCION_USUARIO ->
                getUsuario((DialogUsuario) dialogo);
        }
    }

    public void post(JDialog dialogo, int tabla) {
        switch (tabla) {
            case OPCION_ARTISTA ->
                postArtista((DialogArtista) dialogo);
            case OPCION_EVENTO ->
                postEvento((DialogEvento) dialogo);
            case OPCION_ACTUACION ->
                postActuacion((DialogActuacion) dialogo);
            case OPCION_USUARIO ->
                postUsuario((DialogUsuario) dialogo);
        }
    }

    public void put(JDialog dialogo, int tabla) {
        switch (tabla) {
            case OPCION_ARTISTA ->
                putArtista((DialogArtista) dialogo);
            case OPCION_EVENTO ->
                putEvento((DialogEvento) dialogo);
            case OPCION_USUARIO ->
                putUsuario((DialogUsuario) dialogo);
        }
    }

    public void delete(JDialog dialogo, int tabla) {
        switch (tabla) {
            case OPCION_ARTISTA ->
                deleteArtista((DialogArtista) dialogo);
            case OPCION_EVENTO ->
                deleteEvento((DialogEvento) dialogo);
            case OPCION_ACTUACION ->
                deleteActuacion((DialogActuacion) dialogo);
            case OPCION_USUARIO ->
                deleteUsuario((DialogUsuario) dialogo);
        }
    }

    private void getArtista(DialogArtista dialogo) {
        try {
            if (dialogo.getTxtFldArtistaId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Falta el campo del ID");
                return;
            }

            Artista artista = artistaClient.get(Integer.parseInt(dialogo.getTxtFldArtistaId().getText()));

            if (artista != null) {
                dialogo.dispose();
                DialogResultadoGet drg = new DialogResultadoGet(ventana, false, this);
                drg.setTitle("Artista de ID " + artista.getId());

                drg.getTxtFldResultadoNombre().setText(artista.getNombre());
                drg.getLblResultadoDescLugarHash().setText("Descripción");
                drg.getTxtAreaResultadoDescLugarHash().setText(artista.getDescripcion());
                drg.getLblResultadoFechaSalt().setText("Fecha");
                drg.getTxtFldResultadoFechaSalt().setText(artista.getFechaFormacion());

                if (artista.getImagen() != null) {
                    drg.getBtnResultadoImagen().addActionListener((e) -> {
                        try {
                            verImagen(artista.getImagen(), 400, 300);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(dialogo, "No se ha podido mostrar la imagen", null, JOptionPane.WARNING_MESSAGE);
                        }
                    });
                } else {
                    drg.getBtnResultadoImagen().setText("No hay imagen");
                    drg.getBtnResultadoImagen().setEnabled(false);
                }

                if (artista.getIcono() != null) {
                    drg.getBtnResultadoIcono().addActionListener((e) -> {
                        try {
                            verImagen(artista.getIcono(), 100, 100);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(dialogo, "No se ha podido mostrar el icono", null, JOptionPane.WARNING_MESSAGE);
                        }
                    });
                } else {
                    drg.getBtnResultadoIcono().setText("No hay icono");
                    drg.getBtnResultadoIcono().setEnabled(false);
                }

                drg.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dialogo, "El ID introducido no se corresponde con ningún artista");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al obtener el artista");
        }
    }

    public void buscarArtista(DialogArtista dialogo) {
        try {
            if (dialogo.getTxtFldArtistaId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Falta el campo del ID");
                return;
            }

            Artista artista = artistaClient.get(Integer.parseInt(dialogo.getTxtFldArtistaId().getText()));

            if (artista != null) {
                dialogo.getTxtFldArtistaNombre().setText(artista.getNombre());
                dialogo.getTxtAreaArtistaDesc().setText(artista.getDescripcion());
                dialogo.getFechaArtista().setDate(new SimpleDateFormat("yyyy-MM-dd").parse(artista.getFechaFormacion()));
                dialogo.setImagenB64(artista.getImagen());
                dialogo.setIconoB64(artista.getIcono());
                dialogo.getBtnPutArtistaBorrarImagen().setVisible(true);
                dialogo.getBtnPutArtistaBorrarIcono().setVisible(true);

                if (artista.getImagen() == null || artista.getImagen().isBlank()) {
                    dialogo.getBtnPutArtistaBorrarImagen().setEnabled(false);
                } else {
                    dialogo.getBtnPutArtistaBorrarImagen().setEnabled(true);
                }
                if (artista.getIcono() == null || artista.getIcono().isBlank()) {
                    dialogo.getBtnPutArtistaBorrarIcono().setEnabled(false);
                } else {
                    dialogo.getBtnPutArtistaBorrarIcono().setEnabled(true);
                }

                JOptionPane.showMessageDialog(dialogo, "El artista se ha buscado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El ID introducido no se corresponde con ningún artista");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al buscar el artista");
        }
    }

    private void postArtista(DialogArtista dialogo) {
        try {
            if (dialogo.getTxtFldArtistaNombre().getText().isBlank() || dialogo.getFechaArtista().getDate() == null) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            String desc;
            if (dialogo.getTxtAreaArtistaDesc().getText().isBlank() || dialogo.getTxtAreaArtistaDesc().getText() == null) {
                desc = null;
            } else {
                desc = dialogo.getTxtAreaArtistaDesc().getText();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String fecha = sdf.format(dialogo.getFechaArtista().getDate());

            Artista artista = new Artista(
                    dialogo.getTxtFldArtistaNombre().getText(),
                    desc,
                    fecha,
                    dialogo.getImagenB64(),
                    dialogo.getIconoB64());

            Artista artistaNuevo = artistaClient.post(artista);

            if (artistaNuevo != null) {
                JOptionPane.showMessageDialog(dialogo, "Artista insertado correctamente con ID " + artistaNuevo.getId());
            } else {
                JOptionPane.showMessageDialog(dialogo, "El artista introducido no se ha podido insertar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar el artista");
        }
    }

    private void putArtista(DialogArtista dialogo) {
        try {
            if (dialogo.getTxtFldArtistaId().getText().isBlank() || dialogo.getTxtFldArtistaNombre().getText().isBlank() || dialogo.getFechaArtista().getDate() == null) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldArtistaId().getText());

            String desc;
            if (dialogo.getTxtAreaArtistaDesc().getText().isBlank() || dialogo.getTxtAreaArtistaDesc().getText() == null) {
                desc = null;
            } else {
                desc = dialogo.getTxtAreaArtistaDesc().getText();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String fecha = sdf.format(dialogo.getFechaArtista().getDate());

            Artista artista = new Artista(
                    dialogo.getTxtFldArtistaNombre().getText(),
                    desc,
                    fecha,
                    dialogo.getImagenB64(),
                    dialogo.getIconoB64());

            Artista artistaModificado = artistaClient.put(id, artista);

            if (artistaModificado != null) {
                JOptionPane.showMessageDialog(dialogo, "Artista con ID " + artistaModificado.getId() + " modificado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El artista introducido no se ha podido modificar");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al modificar el artista");
        }
    }

    private void deleteArtista(DialogArtista dialogo) {
        try {
            if (dialogo.getTxtFldArtistaId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldArtistaId().getText());

            boolean borrado = artistaClient.delete(id);
            if (borrado) {
                JOptionPane.showMessageDialog(dialogo, "Artista borrado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El artista no existe");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al borrar el artista");
        }
    }

    private void getEvento(DialogEvento dialogo) {
        try {
            if (dialogo.getTxtFldEventoId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            Evento evento = eventoClient.get(Integer.parseInt(dialogo.getTxtFldEventoId().getText()));

            if (evento != null) {
                dialogo.dispose();
                DialogResultadoGet drg = new DialogResultadoGet(ventana, false, this);
                drg.setTitle("Evento de ID " + evento.getId());

                drg.getTxtFldResultadoNombre().setText(evento.getNombre());

                drg.getLblResultadoDescLugarHash().setText("Lugar");
                if (evento.getLugar() != null) {
                    drg.getTxtAreaResultadoDescLugarHash().setText(evento.getLugar());
                } else {
                    drg.getTxtAreaResultadoDescLugarHash().setText("Lugar sin confirmar");
                }

                drg.getLblResultadoFechaSalt().setText("Fecha");
                if (evento.getFecha() != null) {
                    drg.getTxtFldResultadoFechaSalt().setText(evento.getFecha());
                } else {
                    drg.getTxtFldResultadoFechaSalt().setText("Fecha sin confirmar");
                }

                if (evento.getImagen() != null) {
                    drg.getBtnResultadoImagen().addActionListener((e) -> {
                        try {
                            verImagen(evento.getImagen(), 400, 300);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(dialogo, "No se ha podido mostrar la imagen", null, JOptionPane.WARNING_MESSAGE);
                        }
                    });
                } else {
                    drg.getBtnResultadoImagen().setText("No hay imagen");
                    drg.getBtnResultadoImagen().setEnabled(false);
                }

                if (evento.getIcono() != null) {
                    drg.getBtnResultadoIcono().addActionListener((e) -> {
                        try {
                            verImagen(evento.getIcono(), 100, 100);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(dialogo, "No se ha podido mostrar el icono", null, JOptionPane.WARNING_MESSAGE);
                        }
                    });
                } else {
                    drg.getBtnResultadoIcono().setText("No hay icono");
                    drg.getBtnResultadoIcono().setEnabled(false);
                }
                drg.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dialogo, "El ID introducido no se corresponde con ningún evento");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al modificar el evento");
        }
    }

    public void buscarEvento(DialogEvento dialogo) {
        try {
            if (dialogo.getTxtFldEventoId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Falta el campo del ID");
                return;
            }

            Evento evento = eventoClient.get(Integer.parseInt(dialogo.getTxtFldEventoId().getText()));

            if (evento != null) {
                dialogo.getTxtFldEventoNombre().setText(evento.getNombre());
                dialogo.getTxtFldEventoLugar().setText(evento.getLugar());
                dialogo.getFechaEvento().setDate(new SimpleDateFormat("yyyy-MM-dd").parse(evento.getFecha()));
                dialogo.setImagenB64(evento.getImagen());
                dialogo.setIconoB64(evento.getIcono());
                dialogo.getBtnPutEventoBorrarImagen().setVisible(true);
                dialogo.getBtnPutEventoBorrarIcono().setVisible(true);

                if (evento.getImagen() == null || evento.getImagen().isBlank()) {
                    dialogo.getBtnPutEventoBorrarImagen().setEnabled(false);
                } else {
                    dialogo.getBtnPutEventoBorrarImagen().setEnabled(true);
                }
                if (evento.getIcono() == null || evento.getIcono().isBlank()) {
                    dialogo.getBtnPutEventoBorrarIcono().setEnabled(false);
                } else {
                    dialogo.getBtnPutEventoBorrarIcono().setEnabled(true);
                }

                JOptionPane.showMessageDialog(dialogo, "El evento se ha buscado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El ID introducido no se corresponde con ningún evento");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al buscar el evento");
        }
    }

    private void postEvento(DialogEvento dialogo) {
        try {
            if (dialogo.getTxtFldEventoNombre().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            String fecha = null;
            if (dialogo.getFechaEvento().getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha = sdf.format(dialogo.getFechaEvento().getDate());
            }

            String lugar = dialogo.getTxtFldEventoLugar().getText().isBlank() ? null : dialogo.getTxtFldEventoLugar().getText();

            Evento evento = new Evento(
                    dialogo.getTxtFldEventoNombre().getText(),
                    fecha,
                    lugar,
                    dialogo.getImagenB64(),
                    dialogo.getIconoB64());

            Evento eventoNuevo = eventoClient.post(evento);

            if (eventoNuevo != null) {
                JOptionPane.showMessageDialog(dialogo, "Evento insertado correctamente con ID " + eventoNuevo.getId());
            } else {
                JOptionPane.showMessageDialog(dialogo, "El evento introducido no se ha podido insertar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar el evento");
        }
    }

    private void putEvento(DialogEvento dialogo) {
        try {
            if (dialogo.getTxtFldEventoId().getText().isBlank() || dialogo.getTxtFldEventoNombre().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldEventoId().getText());

            String fecha = null;
            if (dialogo.getFechaEvento().getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha = sdf.format(dialogo.getFechaEvento().getDate());
            }

            String lugar = dialogo.getTxtFldEventoLugar().getText().isBlank() ? null : dialogo.getTxtFldEventoLugar().getText();

            Evento evento = new Evento(
                    dialogo.getTxtFldEventoNombre().getText(),
                    fecha,
                    lugar,
                    dialogo.getImagenB64(),
                    dialogo.getIconoB64());

            Evento eventoModificado = eventoClient.put(id, evento);

            if (eventoModificado != null) {
                JOptionPane.showMessageDialog(dialogo, "Evento con ID " + eventoModificado.getId() + " modificado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El evento introducido no se ha podido insertar");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al modificar el evento");
        }
    }

    private void deleteEvento(DialogEvento dialogo) {
        try {
            if (dialogo.getTxtFldEventoId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldEventoId().getText());

            boolean borrado = eventoClient.delete(id);

            if (borrado) {
                JOptionPane.showMessageDialog(dialogo, "Evento borrado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El evento no existe");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al borrar el evento");
        }
    }

    private void getActuacion(DialogActuacion dialogo) {
        try {
            if (dialogo.getTxtFldActuacionIdArt().getText().isBlank() || dialogo.getTxtFldActuacionIdEve().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int idArtista = Integer.parseInt(dialogo.getTxtFldActuacionIdArt().getText());
            int idEvento = Integer.parseInt(dialogo.getTxtFldActuacionIdEve().getText());

            Artista artista = artistaClient.get(idArtista);
            Evento evento = eventoClient.get(idEvento);

            if (artista == null) {
                JOptionPane.showMessageDialog(dialogo, "El artista de ID introducido no existe");
                return;
            }

            if (evento == null) {
                JOptionPane.showMessageDialog(dialogo, "El evento de ID introducido no existe");
                return;
            }

            if (actuacionClient.get(idArtista, idEvento)) {
                dialogo.dispose();
                DialogResultadoGet drg = new DialogResultadoGet(ventana, false, this);
                drg.setTitle("Actuación en evento " + idEvento + " de artista " + idArtista);

                drg.getLblResultadoNombre().setText("Nombre artista");
                drg.getTxtFldResultadoNombre().setText(artista.getNombre());

                drg.getLblResultadoDescLugarHash().setText("Lugar y fecha");
                if (evento.getLugar() != null) {
                    drg.getTxtAreaResultadoDescLugarHash().append(evento.getLugar());
                } else {
                    drg.getTxtAreaResultadoDescLugarHash().append("Lugar sin confirmar");
                }

                if (evento.getFecha() != null && !evento.getFecha().isBlank()) {
                    drg.getTxtAreaResultadoDescLugarHash().append(", " + evento.getFecha());
                } else {
                    drg.getTxtAreaResultadoDescLugarHash().append(", " + "fecha sin confirmar");
                }

                drg.getLblResultadoFechaSalt().setText("Nombre evento");
                drg.getTxtFldResultadoFechaSalt().setText(evento.getNombre());

                drg.getLblResultadoImagen().setVisible(false);
                drg.getBtnResultadoImagen().setVisible(false);
                drg.getLblResultadoIcono().setVisible(false);
                drg.getBtnResultadoIcono().setVisible(false);
                drg.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dialogo, "No existe esta actuación");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "Los IDs introducido deben ser numéricos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar la actuación");
        }
    }

    private void postActuacion(DialogActuacion dialogo) {
        try {
            if (dialogo.getTxtFldActuacionIdArt().getText().isBlank() || dialogo.getTxtFldActuacionIdEve().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int idArtista = Integer.parseInt(dialogo.getTxtFldActuacionIdArt().getText());
            int idEvento = Integer.parseInt(dialogo.getTxtFldActuacionIdEve().getText());

            Artista artista = artistaClient.get(idArtista);
            Evento evento = eventoClient.get(idEvento);

            if (artista == null) {
                JOptionPane.showMessageDialog(dialogo, "El artista de ID introducido no existe");
                return;
            }

            if (evento == null) {
                JOptionPane.showMessageDialog(dialogo, "El evento de ID introducido no existe");
                return;
            }

            if (!actuacionClient.get(idArtista, idEvento)) {
                if (actuacionClient.post(idArtista, idEvento)) {
                    JOptionPane.showMessageDialog(dialogo, "Actuación de " + artista.getNombre() + " en " + evento.getNombre() + " creada correctamente");
                } else {
                    JOptionPane.showMessageDialog(dialogo, "No se ha podido inscribir a " + artista.getNombre() + " en " + evento.getNombre());
                }
            } else {
                JOptionPane.showMessageDialog(dialogo, artista.getNombre() + " ya actúa en " + evento.getNombre());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "Los IDs introducido deben ser numéricos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar la actuación");
        }
    }

    private void deleteActuacion(DialogActuacion dialogo) {
        try {
            if (dialogo.getTxtFldActuacionIdArt().getText().isBlank() || dialogo.getTxtFldActuacionIdEve().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int idArtista = Integer.parseInt(dialogo.getTxtFldActuacionIdArt().getText());
            int idEvento = Integer.parseInt(dialogo.getTxtFldActuacionIdEve().getText());

            Artista artista = artistaClient.get(idArtista);
            Evento evento = eventoClient.get(idEvento);

            if (artista == null) {
                JOptionPane.showMessageDialog(dialogo, "El artista de ID introducido no existe");
                return;
            }

            if (evento == null) {
                JOptionPane.showMessageDialog(dialogo, "El evento de ID introducido no existe");
                return;
            }

            if (actuacionClient.delete(idArtista, idEvento)) {
                JOptionPane.showMessageDialog(dialogo, "Actuación de " + artista.getNombre() + " en " + evento.getNombre() + " borrada correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, artista.getNombre() + " no actúa en " + evento.getNombre());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "Los IDs introducido deben ser numéricos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar la actuación");
        }
    }

    private void getUsuario(DialogUsuario dialogo) {
        try {
            if (dialogo.getTxtFldUsuarioId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            Usuario usuario = usuarioClient.get(Integer.parseInt(dialogo.getTxtFldUsuarioId().getText()));

            if (usuario != null) {
                dialogo.dispose();
                DialogResultadoGet drg = new DialogResultadoGet(ventana, false, this);
                drg.setTitle("Usuario de ID " + usuario.getId());

                drg.getLblResultadoNombre().setText("Nombre y perfil");

                StringBuilder nombrePerfil = new StringBuilder(usuario.getNombre());
                nombrePerfil.append(" - ");
                nombrePerfil.append(usuario.getPerfil());

                drg.getTxtFldResultadoNombre().setText(nombrePerfil.toString());

                drg.getLblResultadoDescLugarHash().setText("Hash");
                drg.getTxtAreaResultadoDescLugarHash().setText(usuario.getHashContrasenha());
                drg.getLblResultadoFechaSalt().setText("Salt");
                drg.getTxtFldResultadoFechaSalt().setText(usuario.getSalt());

                drg.getLblResultadoImagen().setVisible(false);
                drg.getBtnResultadoImagen().setVisible(false);
                drg.getLblResultadoIcono().setVisible(false);
                drg.getBtnResultadoIcono().setVisible(false);
                drg.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dialogo, "El ID introducido no se corresponde con ningún usuario");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al obtener el usuario");
        }
    }

    private void postUsuario(DialogUsuario dialogo) {
        try {
            if (dialogo.getTxtFldUsuarioNombre().getText().isBlank() || dialogo.getPwdFldContrasenha().getPassword().length < 1) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            String contraseña = new String(dialogo.getPwdFldContrasenha().getPassword());
            String rol = dialogo.getCbUsuarioAdmin().isSelected() ? "admin" : "usuario";

            Usuario usuario = new Usuario(dialogo.getTxtFldUsuarioNombre().getText(), contraseña, rol);

            Usuario nuevoUsuario = usuarioClient.post(usuario);
            if (nuevoUsuario != null) {
                JOptionPane.showMessageDialog(dialogo, "Usuario insertado correctamente con ID " + nuevoUsuario.getId());
            } else {
                JOptionPane.showMessageDialog(dialogo, "El usuario introducido no se ha podido insertar. Prueba con un nombre diferente.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al insertar el usuario");
        }
    }

    private void putUsuario(DialogUsuario dialogo) {
        try {
            if (dialogo.getTxtFldUsuarioId().getText().isBlank() || dialogo.getTxtFldUsuarioNombre().getText().isBlank() || dialogo.getPwdFldContrasenha().getPassword().length < 1) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldUsuarioId().getText());

            String contraseña = new String(dialogo.getPwdFldContrasenha().getPassword());

            String rol = dialogo.getCbUsuarioAdmin().isSelected() ? "admin" : "usuario";

            Usuario usuario = new Usuario(dialogo.getTxtFldUsuarioNombre().getText(), contraseña, rol);

            Usuario usuarioModificado = usuarioClient.put(id, usuario);
            if (usuarioModificado != null) {
                JOptionPane.showMessageDialog(dialogo, "Usuario con ID " + usuarioModificado.getId() + " modificado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El usuario introducido no se ha podido modificar");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al modificar el usuario");
        }
    }

    private void deleteUsuario(DialogUsuario dialogo) {
        try {
            if (dialogo.getTxtFldUsuarioId().getText().isBlank()) {
                JOptionPane.showMessageDialog(dialogo, "Faltan campos obligatorios");
                return;
            }

            int id = Integer.parseInt(dialogo.getTxtFldUsuarioId().getText());

            boolean borrado = usuarioClient.delete(id);
            if (borrado) {
                JOptionPane.showMessageDialog(dialogo, "Usuario borrado correctamente");
            } else {
                JOptionPane.showMessageDialog(dialogo, "El usuario no existe");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialogo, "El ID introducido debe ser numérico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al borrar el usuario");
        }
    }

    public String escogerImagen(JDialog dialogo) {
        JFileChooser jfc = new JFileChooser();
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));

        int resultVal = jfc.showOpenDialog(dialogo);
        if (resultVal == JFileChooser.APPROVE_OPTION) {
            File ficheroSeleccionado = jfc.getSelectedFile();
            try {
                if (Files.probeContentType(ficheroSeleccionado.toPath()).startsWith("image")) {
                    return ficheroImagenABase64(ficheroSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(dialogo, "El fichero seleccionado no tiene contenido de imagen");
                    return null;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialogo, "Ha ocurrido un error al cargar la imagen");
                return null;
            }
        }
        return null;
    }

    public void lanzarDialogInformes() {
        ControladorInformes controladorInformes = new ControladorInformes(propiedades);
        DialogInformes dialogInformes = new DialogInformes(ventana, true, controladorInformes);
        controladorInformes.setDialogo(dialogInformes);
        dialogInformes.setVisible(true);
    }

    public String ficheroImagenABase64(File fichero) throws IOException {
        if (fichero == null) {
            return null;
        }
        return new String(Base64.getEncoder().encode(Files.readAllBytes(fichero.toPath())));
    }

    public void lanzarDialogOpciones() {
        dialogOpciones = new DialogOpciones(ventana, true, this);
        dialogOpciones.getTxtFldUrlServicio().setText(propiedades.getProperty("rest.url"));
        dialogOpciones.getTxtFldPuertoServicio().setText(propiedades.getProperty("rest.port"));
        dialogOpciones.getTxtFldUrlBd().setText(propiedades.getProperty("bd.url"));
        dialogOpciones.getTxtFldUserBd().setText(propiedades.getProperty("bd.user"));
        dialogOpciones.getTxtFldPwdBd().setText(propiedades.getProperty("bd.pwd"));
        dialogOpciones.setVisible(true);
    }

    public void guardarOpciones() {
        propiedades.setProperty("rest.url", dialogOpciones.getTxtFldUrlServicio().getText());
        propiedades.setProperty("rest.port", dialogOpciones.getTxtFldPuertoServicio().getText());
        propiedades.setProperty("bd.url", dialogOpciones.getTxtFldUrlBd().getText());
        propiedades.setProperty("bd.user", dialogOpciones.getTxtFldUserBd().getText());
        propiedades.setProperty("bd.pwd", dialogOpciones.getTxtFldPwdBd().getText());
        if (guardarPropiedades()) {
            dialogOpciones.dispose();
            JOptionPane.showMessageDialog(dialogOpciones, "Cambios guardados correctamente. Es posible que tengas que reiniciar la aplicación para que se efectúen todos.", null, JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dialogOpciones, "No se han podido guardar los cambios", null, JOptionPane.PLAIN_MESSAGE);
        }
        conectar();
    }

    private boolean guardarPropiedades() {
        try (OutputStream os = new FileOutputStream(new File(RUTA_CONFIG))) {
            propiedades.store(os, null);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private void verImagen(String imagen, int ancho, int altura) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(imagen));
        BufferedImage imagenOriginal = ImageIO.read(bais);

        Image img = imagenOriginal.getScaledInstance(ancho, altura, Image.SCALE_SMOOTH);
        BufferedImage imagenEscalada = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = imagenEscalada.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        JDialog dialogImagen = new JDialog();
        dialogImagen.setResizable(false);
        JLabel lblImagen = new JLabel(new ImageIcon(imagenEscalada));
        lblImagen.setPreferredSize(new Dimension(ancho, altura));
        dialogImagen.getContentPane().add(lblImagen, BorderLayout.CENTER);
        dialogImagen.pack();
        dialogImagen.setLocationRelativeTo(null);
        dialogImagen.setVisible(true);
    }
}
