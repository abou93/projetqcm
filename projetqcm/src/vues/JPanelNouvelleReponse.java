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

	String[] lettres = {"A","B","C","D","E","F","G","H","I","J"};
	
	/** Creates new form JPanelNouvelleReponse */
    public JPanelNouvelleReponse(int num,String texte,boolean checked) {
        initComponents();
        initPerso(lettres[num],texte,checked);
    }

    
    private void initPerso(String lettre,String texte,boolean checked){
    	jTextAreaNouvelleReponse.setFont(new Font("Tahoma",0,11));
    	jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	jCheckBoxReponseVrai.setSelected(checked);
    	jTextAreaNouvelleReponse.setText(texte);
    	jLabel1.setText(lettre);
    	jLabel1.setFont(new Font("Tahoma",Font.BOLD,12));
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
        jLabel1 = new javax.swing.JLabel();

        jTextAreaNouvelleReponse.setColumns(20);
        jTextAreaNouvelleReponse.setRows(5);
        jScrollPane1.setViewportView(jTextAreaNouvelleReponse);

        jButtonSupprimerReponse.setIcon(new javax.swing.ImageIcon("C:\\Images\\delete32.png")); // NOI18N
        jButtonSupprimerReponse.setToolTipText("Supprimer la reponse");

        jLabel1.setText("A");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBoxReponseVrai)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSupprimerReponse, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButtonSupprimerReponse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxReponseVrai)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSupprimerReponse;
    private javax.swing.JCheckBox jCheckBoxReponseVrai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaNouvelleReponse;
    // End of variables declaration//GEN-END:variables

}
