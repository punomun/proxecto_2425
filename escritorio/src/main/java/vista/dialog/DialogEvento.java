package vista.dialog;

import com.toedter.calendar.JDateChooser;
import controlador.ControladorPrincipal;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DialogEvento extends javax.swing.JDialog {

    private final String metodoHttp;
    private final ControladorPrincipal controlador;

    private String imagenB64;
    private String iconoB64;

    public DialogEvento(java.awt.Frame parent, boolean modal, String metodoHttp, ControladorPrincipal controlador) {
        super(parent, modal);
        initComponents();
        this.metodoHttp = metodoHttp;
        this.controlador = controlador;
        esconderComponentes();
    }

    private void esconderComponentes() {
        btnPutEventoBorrarImagen.setVisible(false);
        btnPutEventoBorrarIcono.setVisible(false);
        btnEventoBuscar.setVisible(false);
        switch (metodoHttp) {
            case "GET", "DELETE" -> {
                lblEventoId.setText(lblEventoId.getText() + " (*)");

                lblEventoNombre.setVisible(false);
                txtFldEventoNombre.setVisible(false);
                lblEventoFecha.setVisible(false);
                fechaEvento.setVisible(false);
                lblEventoLugar.setVisible(false);
                txtFldEventoLugar.setVisible(false);
                lblEventoImagen.setVisible(false);
                btnEventoImg.setVisible(false);
                lblEventoIcono.setVisible(false);
                btnEventoIco.setVisible(false);
            }
            case "POST" -> {
                lblEventoNombre.setText(lblEventoNombre.getText() + " (*)");

                lblEventoId.setVisible(false);
                txtFldEventoId.setVisible(false);
            }
            case "PUT" -> {
                lblEventoId.setText(lblEventoId.getText() + " (*)");
                lblEventoNombre.setText(lblEventoNombre.getText() + " (*)");
                btnEventoBuscar.setVisible(true);
            }
        }
        btnMetodoHttp.setText(metodoHttp);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblEventoId = new javax.swing.JLabel();
        lblEventoNombre = new javax.swing.JLabel();
        lblEventoFecha = new javax.swing.JLabel();
        txtFldEventoId = new javax.swing.JTextField();
        txtFldEventoNombre = new javax.swing.JTextField();
        fechaEvento = new com.toedter.calendar.JDateChooser();
        lblEventoImagen = new javax.swing.JLabel();
        lblEventoIcono = new javax.swing.JLabel();
        btnEventoImg = new javax.swing.JButton();
        btnEventoIco = new javax.swing.JButton();
        btnMetodoHttp = new javax.swing.JButton();
        lblEventoLugar = new javax.swing.JLabel();
        txtFldEventoLugar = new javax.swing.JTextField();
        btnEventoBuscar = new javax.swing.JButton();
        btnPutEventoBorrarImagen = new javax.swing.JButton();
        btnPutEventoBorrarIcono = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión Evento");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblEventoId.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoId, gridBagConstraints);

        lblEventoNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoNombre, gridBagConstraints);

        lblEventoFecha.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoFecha, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(txtFldEventoId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(txtFldEventoNombre, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(fechaEvento, gridBagConstraints);

        lblEventoImagen.setText("Imagen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoImagen, gridBagConstraints);

        lblEventoIcono.setText("Icono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoIcono, gridBagConstraints);

        btnEventoImg.setText("Escoger imagen");
        btnEventoImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoImgActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(btnEventoImg, gridBagConstraints);

        btnEventoIco.setText("Escoger icono");
        btnEventoIco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoIcoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(btnEventoIco, gridBagConstraints);

        btnMetodoHttp.setText("METODO");
        btnMetodoHttp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodoHttpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 10, 50);
        getContentPane().add(btnMetodoHttp, gridBagConstraints);

        lblEventoLugar.setText("Lugar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblEventoLugar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(txtFldEventoLugar, gridBagConstraints);

        btnEventoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ic_busqueda.png"))); // NOI18N
        btnEventoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(btnEventoBuscar, gridBagConstraints);

        btnPutEventoBorrarImagen.setText("Borrar imagen");
        btnPutEventoBorrarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPutEventoBorrarImagenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(btnPutEventoBorrarImagen, gridBagConstraints);

        btnPutEventoBorrarIcono.setText("Borrar icono");
        btnPutEventoBorrarIcono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPutEventoBorrarIconoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(btnPutEventoBorrarIcono, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMetodoHttpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoHttpActionPerformed
        switch (metodoHttp) {
            case "GET" ->
                controlador.get(this, controlador.OPCION_EVENTO);
            case "POST" ->
                controlador.post(this, controlador.OPCION_EVENTO);
            case "PUT" ->
                controlador.put(this, controlador.OPCION_EVENTO);
            default ->
                controlador.delete(this, controlador.OPCION_EVENTO);
        }
    }//GEN-LAST:event_btnMetodoHttpActionPerformed

    private void btnEventoImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoImgActionPerformed
        this.imagenB64 = controlador.escogerImagen(this);
        this.btnPutEventoBorrarImagen.setEnabled(true);
        pack();
    }//GEN-LAST:event_btnEventoImgActionPerformed

    private void btnEventoIcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoIcoActionPerformed
        this.iconoB64 = controlador.escogerImagen(this);
        this.btnPutEventoBorrarImagen.setEnabled(true);
        pack();
    }//GEN-LAST:event_btnEventoIcoActionPerformed

    private void btnEventoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoBuscarActionPerformed
        controlador.buscarEvento(this);
        pack();
    }//GEN-LAST:event_btnEventoBuscarActionPerformed

    private void btnPutEventoBorrarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPutEventoBorrarImagenActionPerformed
        this.imagenB64 = null;
        JOptionPane.showMessageDialog(this, "Imagen borrada");
        this.btnPutEventoBorrarImagen.setEnabled(false);
        pack();
    }//GEN-LAST:event_btnPutEventoBorrarImagenActionPerformed

    private void btnPutEventoBorrarIconoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPutEventoBorrarIconoActionPerformed
        this.iconoB64 = null;
        JOptionPane.showMessageDialog(this, "Icono borrado");
        this.btnPutEventoBorrarImagen.setEnabled(false);
        pack();
    }//GEN-LAST:event_btnPutEventoBorrarIconoActionPerformed

    public String getImagenB64() {
        return imagenB64;
    }

    public void setImagenB64(String imagenB64) {
        this.imagenB64 = imagenB64;
    }

    public String getIconoB64() {
        return iconoB64;
    }

    public void setIconoB64(String iconoB64) {
        this.iconoB64 = iconoB64;
    }

    public JDateChooser getFechaEvento() {
        return fechaEvento;
    }

    public JTextField getTxtFldEventoId() {
        return txtFldEventoId;
    }

    public JTextField getTxtFldEventoLugar() {
        return txtFldEventoLugar;
    }

    public JTextField getTxtFldEventoNombre() {
        return txtFldEventoNombre;
    }

    public JButton getBtnPutEventoBorrarIcono() {
        return btnPutEventoBorrarIcono;
    }

    public JButton getBtnPutEventoBorrarImagen() {
        return btnPutEventoBorrarImagen;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEventoBuscar;
    private javax.swing.JButton btnEventoIco;
    private javax.swing.JButton btnEventoImg;
    private javax.swing.JButton btnMetodoHttp;
    private javax.swing.JButton btnPutEventoBorrarIcono;
    private javax.swing.JButton btnPutEventoBorrarImagen;
    private com.toedter.calendar.JDateChooser fechaEvento;
    private javax.swing.JLabel lblEventoFecha;
    private javax.swing.JLabel lblEventoIcono;
    private javax.swing.JLabel lblEventoId;
    private javax.swing.JLabel lblEventoImagen;
    private javax.swing.JLabel lblEventoLugar;
    private javax.swing.JLabel lblEventoNombre;
    private javax.swing.JTextField txtFldEventoId;
    private javax.swing.JTextField txtFldEventoLugar;
    private javax.swing.JTextField txtFldEventoNombre;
    // End of variables declaration//GEN-END:variables

}
