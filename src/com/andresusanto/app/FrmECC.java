/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.app;

import com.andresusanto.engine.ECC;
import com.andresusanto.engine.Tools;
import com.andresusanto.object.Coordinate;
import com.andresusanto.object.Curve;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Andre
 */
public class FrmECC extends javax.swing.JFrame {

    /**
     * Creates new form FrmECC
     */
    private ECC ecc;
    private Coordinate base;
    private Curve curve;
    private BigInteger privateKey;
    
    public FrmECC() {
        initComponents();
        privateKey = new BigInteger("123");
        updateECC();
    }

    private void updateECC(){
        curve = new Curve(new BigInteger(txtKurvaA.getText()), new BigInteger(txtKurvaB.getText()), new BigInteger(txtKurvaP.getText()));
        base = new Coordinate(new BigInteger(txtBaseX.getText()), new BigInteger(txtBaseY.getText()));
        ecc = new ECC(curve, base, privateKey);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtKurvaA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKurvaB = new javax.swing.JTextField();
        txtKurvaP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmdApply = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBaseX = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtBaseY = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblPrivate = new javax.swing.JLabel();
        lblPublic = new javax.swing.JLabel();
        cmdGenerate = new javax.swing.JButton();
        cmdSave = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPlain = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCipher = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblProcessingTime = new javax.swing.JLabel();
        cmdDecrypt = new javax.swing.JButton();
        cmdEncrypt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Konfigurasi Kurva Eliptik");

        txtKurvaA.setText("4");

        jLabel2.setText("a");

        jLabel3.setText("b");

        txtKurvaB.setText("5");

        txtKurvaP.setText("98764321261");
        txtKurvaP.setToolTipText("");

        jLabel4.setText("p");

        cmdApply.setText("Apply");
        cmdApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdApplyActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Konfigurasi Base Point");

        jLabel9.setText("x");

        txtBaseX.setText("4");

        jLabel13.setText("y");

        txtBaseY.setText("5");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKurvaA, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKurvaB, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtBaseX, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtBaseY, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKurvaP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(cmdApply)))
                .addContainerGap(318, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKurvaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtKurvaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtKurvaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(36, 36, 36)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBaseX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtBaseY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(37, 37, 37)
                .addComponent(cmdApply)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Konfigurasi Kurva", jPanel1);

        jLabel6.setText("Kunci Privat");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Generator Kunci");

        jLabel7.setText("Kunci Publik");

        lblPrivate.setText("(Silahkan generate)");

        lblPublic.setText("(Silahkan generate)");

        cmdGenerate.setText("Generate");
        cmdGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateActionPerformed(evt);
            }
        });

        cmdSave.setText("Simpan");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 429, Short.MAX_VALUE)
                        .addComponent(cmdGenerate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdSave))
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPublic)
                            .addComponent(lblPrivate))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblPrivate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblPublic))
                .addGap(24, 233, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSave)
                    .addComponent(cmdGenerate))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Generator Kunci", jPanel2);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Plain Text");

        txtPlain.setColumns(20);
        txtPlain.setRows(5);
        jScrollPane1.setViewportView(txtPlain);

        txtCipher.setColumns(20);
        txtCipher.setRows(5);
        jScrollPane2.setViewportView(txtCipher);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Cipher Text");

        jLabel12.setText("Waktu Pemrosesan");

        lblProcessingTime.setText("(belum dimulai)");

        cmdDecrypt.setText("Dekripsi");
        cmdDecrypt.setToolTipText("");
        cmdDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDecryptActionPerformed(evt);
            }
        });

        cmdEncrypt.setText("Enkripsi");
        cmdEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEncryptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblProcessingTime)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdEncrypt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdDecrypt)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblProcessingTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdDecrypt)
                    .addComponent(cmdEncrypt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Enkripsi Dekripsi", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerateActionPerformed
        // TODO add your handling code here:
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        sb.append(random.nextInt(999999));
        sb.append(random.nextInt(999999));
        sb.append(random.nextInt(999999));
        sb.append(random.nextInt(999999));
        
        privateKey = new BigInteger(sb.toString());
        updateECC();
        lblPrivate.setText(sb.toString());
        
        lblPublic.setText(ecc.generatePublic().toString());
    }//GEN-LAST:event_cmdGenerateActionPerformed

    private void cmdApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdApplyActionPerformed
        updateECC();
        JOptionPane.showMessageDialog(null, "Konfigurasi berhasil diperbaharui", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cmdApplyActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        final JFileChooser fc = new JFileChooser();       
        try {
            fc.setFileFilter(new FileNameExtensionFilter("Private Key", "pvt"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                Tools.writeStringToFile(privateKey.toString(), fc.getSelectedFile().getPath());
                
                fc.setFileFilter(new FileNameExtensionFilter("Public Key", "pub"));
                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                    Coordinate publicKey = ecc.generatePublic();
                    String publicContent = publicKey.X.toString() + "\n" + publicKey.Y.toString();
                    Tools.writeStringToFile(publicContent, fc.getSelectedFile().getPath());
                    
                    JOptionPane.showMessageDialog(null, "Kunci berhasil disimpan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terdapat kesalahan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void cmdEncryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEncryptActionPerformed
        final JFileChooser fc = new JFileChooser();     
        try {
            fc.setFileFilter(new FileNameExtensionFilter("Public Key", "pub"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                String publicKeyFC = Tools.readStringFile(fc.getSelectedFile().getPath());
                String publicKeystr[] = publicKeyFC.split("\n");
                Coordinate publicKey = new Coordinate(new BigInteger(publicKeystr[0]), new BigInteger(publicKeystr[1]));
                
                
                fc.setFileFilter(null);
                if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                    String content = Tools.readStringFile(fc.getSelectedFile().getPath());
                    byte[] plain = Files.readAllBytes(Paths.get(fc.getSelectedFile().getPath()));
                    txtPlain.setText(content);
                    
                    StringBuilder sb = new StringBuilder();
                    
                    long timeBefore = System.currentTimeMillis();
                    byte[] encrypted = ecc.encrypt(plain, publicKey);
                    long timeAfter = System.currentTimeMillis();
                    
                    for (byte b : encrypted){
                        sb.append(String.format("%02X ", b));
                    }
                    
                    txtCipher.setText(sb.toString());
                    lblProcessingTime.setText("" + (timeAfter - timeBefore) + " ms");
                    
                    if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                        Files.write(Paths.get(fc.getSelectedFile().getPath()), encrypted);
                    }
                }
                
                
                
            }else{
                JOptionPane.showMessageDialog(null, "Public key diperlukan untuk enkripsi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terdapat kesalahan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdEncryptActionPerformed

    private void cmdDecryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDecryptActionPerformed
        final JFileChooser fc = new JFileChooser();     
        try {
            fc.setFileFilter(new FileNameExtensionFilter("Private Key", "pvt"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                String privateKeystr = Tools.readStringFile(fc.getSelectedFile().getPath());
                privateKey = new BigInteger(privateKeystr);
                updateECC();
                
                fc.setFileFilter(null);
                if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                    byte[] cipher = Files.readAllBytes(Paths.get(fc.getSelectedFile().getPath()));
                    
                    long timeBefore = System.currentTimeMillis();
                    byte[] decrypt = ecc.decrypt(cipher);
                    long timeAfter = System.currentTimeMillis();
                    
                    String content = new String(decrypt);
                    txtPlain.setText(content);
                    
                    StringBuilder sb = new StringBuilder();
                    for (byte b : cipher){
                        sb.append(String.format("%02X ", b));
                    }
                    
                    txtCipher.setText(sb.toString());
                    lblProcessingTime.setText("" + (timeAfter - timeBefore) + " ms");
                    
                    if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                        Files.write(Paths.get(fc.getSelectedFile().getPath()), decrypt);
                    }
                }
                
                
                
            }else{
                JOptionPane.showMessageDialog(null, "Public key diperlukan untuk enkripsi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terdapat kesalahan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdDecryptActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmECC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmECC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmECC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmECC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmECC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdApply;
    private javax.swing.JButton cmdDecrypt;
    private javax.swing.JButton cmdEncrypt;
    private javax.swing.JButton cmdGenerate;
    private javax.swing.JButton cmdSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblPrivate;
    private javax.swing.JLabel lblProcessingTime;
    private javax.swing.JLabel lblPublic;
    private javax.swing.JTextField txtBaseX;
    private javax.swing.JTextField txtBaseY;
    private javax.swing.JTextArea txtCipher;
    private javax.swing.JTextField txtKurvaA;
    private javax.swing.JTextField txtKurvaB;
    private javax.swing.JTextField txtKurvaP;
    private javax.swing.JTextArea txtPlain;
    // End of variables declaration//GEN-END:variables
}
