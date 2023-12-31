/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Game;

import java.awt.Window;
import javax.swing.JFrame;

/**
 *
 * @author pavel
 */
    
public class PassLevel extends javax.swing.JFrame {

    /**
     * Creates new form ContinueGameWin
     */
    int level;
    
    public PassLevel(int level) {
        this.level=level;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        JbNextLevel = new javax.swing.JButton();
        JbExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(990, 375));
        getContentPane().setLayout(null);

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        jLabel2.setText("   HA TERMINADO EL NIVEL, DESEAS CONTINUAR?");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(180, 10, 640, 50);

        JbNextLevel.setText("Siguiente nivel");
        JbNextLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbNextLevelActionPerformed(evt);
            }
        });
        getContentPane().add(JbNextLevel);
        JbNextLevel.setBounds(170, 270, 200, 60);

        JbExit.setText("Salir del juego");
        JbExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbExitActionPerformed(evt);
            }
        });
        getContentPane().add(JbExit);
        JbExit.setBounds(640, 270, 210, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\pavel\\OneDrive\\Documents\\NetBeansProjects\\Proyecto3_POO\\Proyecto3_POO\\Images\\battlecitywindow.png")); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 990, 350);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JbNextLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbNextLevelActionPerformed
        
        GameController newWindow = new GameController(level+1);
        this.dispose();
    }//GEN-LAST:event_JbNextLevelActionPerformed

    private void JbExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbExitActionPerformed
            Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                ((JFrame) window).dispose();
            }
        }
        System.out.println("Se salió del juego exitosamente");
    }//GEN-LAST:event_JbExitActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JbExit;
    private javax.swing.JButton JbNextLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
