package vista.dialog;

import controlador.ControladorPrincipal;
import javax.swing.JTextField;

public class DialogActuacion extends javax.swing.JDialog {

    private final String metodoHttp;
    private final ControladorPrincipal controlador;
    
    public DialogActuacion(java.awt.Frame parent, boolean modal, String metodoHttp, ControladorPrincipal controlador) {
        super(parent, modal);
        initComponents();
        this.metodoHttp = metodoHttp;
        this.controlador = controlador;
        btnMetodo.setText(metodoHttp);
        pack();
    }

    public JTextField getTxtFldActuacionIdArt() {
        return txtFldActuacionIdArt;
    }

    public JTextField getTxtFldActuacionIdEve() {
        return txtFldActuacionIdEve;
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

        lblActuacionIdArt = new javax.swing.JLabel();
        txtFldActuacionIdArt = new javax.swing.JTextField();
        lblActuacionIdEve = new javax.swing.JLabel();
        txtFldActuacionIdEve = new javax.swing.JTextField();
        btnMetodo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión Actuación");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblActuacionIdArt.setText("ID artista (*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblActuacionIdArt, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(txtFldActuacionIdArt, gridBagConstraints);

        lblActuacionIdEve.setText("ID evento (*)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblActuacionIdEve, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(txtFldActuacionIdEve, gridBagConstraints);

        btnMetodo.setText("METODO");
        btnMetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 25, 10, 25);
        getContentPane().add(btnMetodo, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodoActionPerformed
        switch (metodoHttp) {
            case "GET" -> controlador.get(this, controlador.OPCION_ACTUACION);
			case "POST" -> controlador.post(this, controlador.OPCION_ACTUACION);
			case "DELETE" -> controlador.delete(this, controlador.OPCION_ACTUACION);
        }
    }//GEN-LAST:event_btnMetodoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMetodo;
    private javax.swing.JLabel lblActuacionIdArt;
    private javax.swing.JLabel lblActuacionIdEve;
    private javax.swing.JTextField txtFldActuacionIdArt;
    private javax.swing.JTextField txtFldActuacionIdEve;
    // End of variables declaration//GEN-END:variables
}
