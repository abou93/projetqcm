/*
 * JPanelNouvelleReponse.java
 *
 * Created on 24 octobre 2008, 16:15
 */

package vues;

import java.awt.Font;

import javax.swing.ScrollPaneConstants;


/**
 *
 * @author  slefort
 */
public class JPanelNouvelleReponse extends javax.swing.JPanel {

    /** Creates new form JPanelNouvelleReponse */
    public JPanelNouvelleReponse(Character lettre,String texte,boolean checked) {
        initComponents();
        initPerso(lettre,texte,checked);
    }

    
    private void initPerso(Character lettre,String texte,boolean checked){
    	jTextAreaNouvelleReponse.setFont(new Font("Tahoma",0,11));
    	jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	jCheckBoxReponseVrai.setSelected(checked);
    	jTextAreaNouvelleReponse.setText(texte);
    	
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaNouvelleReponse = new javax.swing.JTextArea();
        jCheckBoxReponseVrai = new javax.swing.JCheckBox();
        jButtonSupprimerReponse = new javax.swing.JButton();

        jTextAreaNouvelleReponse.setColumns(20);
        jTextAreaNouvelleReponse.setRows(5);
        jScrollPane1.setViewportView(jTextAreaNouvelleReponse);

        jButtonSupprimerReponse.setIcon(new javax.swing.ImageIcon("C:\\Images\\delete32.png")); // NOI18N
        jButtonSupprimerReponse.setToolTipText("Supprimer la reponse");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxReponseVrai)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSupprimerReponse, javax.swing.GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxReponseVrai)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonSupprimerReponse, javax.swing.GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSupprimerReponse;
    private javax.swing.JCheckBox jCheckBoxReponseVrai;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaNouvelleReponse;
    // End of variables declaration//GEN-END:variables

    
}
