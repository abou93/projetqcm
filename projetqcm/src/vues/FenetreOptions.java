/*
 * FenetreOptions.java
 *
 * Created on 29 octobre 2008, 17:07
 */

package vues;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import javax.swing.JDialog;
import javax.swing.JOptionPane;

import securite.IOProperties;
import securite.hashPassword;

/**
 *
 * @author  slefort
 */
public class FenetreOptions extends JDialog {
     
	
	private static final long serialVersionUID = 1L;
	/** Creates new form FenetreOptions */
    public FenetreOptions() {

    	initComponents();
        initOption();
        
      //Changer l'icone de la fenetre
		Image icone = Toolkit.getDefaultToolkit().getImage("Images/LogoENI16.PNG");
    	this.setIconImage(icone);
    	
    	this.setTitle("Options");
    	//this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    }

    
    public void initOption(){
    	
		
    	/*****************************************************
 		*		 		Ajout des Listener					 *
 		******************************************************/
    	
		jButtonValide.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				IOProperties ioProperties = new IOProperties();
				if (jPasswordFieldNv.getPassword().length>0)
				{
					if (jPasswordFieldNv.getText().equals(jPasswordFieldNv2.getText()))
					{
						try {
						
							Properties password  = ioProperties.loadProperties("Props/securite.properties");
							hashPassword hash = new hashPassword();
							password.setProperty("motdepasse",hash.getHash(jPasswordFieldNv2.getText()));
							ioProperties.saveProperties(password,"Props/securite.properties", "Fichier password");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
					else JOptionPane.showMessageDialog(FenetreOptions.this, "Erreur dans la saisie du pot de passe","Erreur" ,JOptionPane.ERROR_MESSAGE);
				}	
				
				
				try {
					Properties connexion = ioProperties.loadProperties("Props/connexion.properties");
					connexion.setProperty("adresse",jTextFieldAdresse.getText().trim());
					connexion.setProperty("user", jTextFieldUser.getText().trim());
					connexion.setProperty("password",jTextFieldPassword.getText().trim());
					connexion.setProperty("databasename", jTextFieldNomBase.getText().trim());
					ioProperties.saveProperties(connexion,"Props/connexion.properties", "Fichier connexion");
					
					Properties secur = ioProperties.loadProperties("Props/securite.properties");
					secur.setProperty("mail", jTextFieldMailFormateur.getText().trim());
					ioProperties.saveProperties(secur,"Props/securite.properties", "Fichier securite");
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				FenetreOptions.this.dispose();
				
				fenPrincipale.getFenPrincipale().dispose();
				fenPrincipale f = new fenPrincipale();
				f.setLocationRelativeTo(f.getParent());
				f.setVisible(true);		
				
				
				
				
			}
			
			 
		});
		
		
		jButtonAnnule.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				FenetreOptions.this.dispose();
				
			}
			
		});
		
		
		
		/*****************************************************
 		*		 		Initialisation des composants		 *
 		******************************************************/
		
		jPasswordFieldNv.setText("");
		jPasswordFieldNv2.setText("");
		
		
		IOProperties ioProperties = new IOProperties();
		try {
			
			Properties connexion = ioProperties.loadProperties("Props/connexion.properties");
			jTextFieldAdresse.setText(connexion.getProperty("adresse"));
			jTextFieldUser.setText(connexion.getProperty("user"));
			jTextFieldPassword.setText(connexion.getProperty("password"));
			jTextFieldNomBase.setText(connexion.getProperty("databasename"));
			
			Properties securite = ioProperties.loadProperties("Props/securite.properties");
			jTextFieldMailFormateur.setText(securite.getProperty("mail"));
						
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
    }
    
    
    
     
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextFieldMailFormateur = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordFieldNv2 = new javax.swing.JPasswordField();
        jPasswordFieldNv = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldAdresse = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldUser = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPassword = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldNomBase = new javax.swing.JTextField();
        jButtonValide = new javax.swing.JButton();
        jButtonAnnule = new javax.swing.JButton();

        
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Propri�t�s Formateur"));

        jLabel1.setIcon(new javax.swing.ImageIcon("Images/email.png")); // NOI18N
        jLabel1.setText("Mail formateur :");

        jLabel2.setText("(par d�faut)");

        jLabel3.setIcon(new javax.swing.ImageIcon("Images/key.png")); // NOI18N
        jLabel3.setText("Mot de passe : ");

        jPasswordFieldNv2.setText("jPasswordField1");

        jPasswordFieldNv.setText("jPasswordField2");

        jLabel8.setText("1ere Saisie");

        jLabel9.setText("2eme Saisie");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordFieldNv2)
                    .addComponent(jPasswordFieldNv)
                    .addComponent(jTextFieldMailFormateur, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMailFormateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldNv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Param�trage Base de donn�e"));
        jPanel2.setLayout(new java.awt.GridLayout(5, 2));

        jLabel4.setText("Adresse :");
        jPanel2.add(jLabel4);
        jPanel2.add(jTextFieldAdresse);

        jLabel5.setText("User :");
        jPanel2.add(jLabel5);
        jPanel2.add(jTextFieldUser);

        jLabel6.setText("Password :");
        jPanel2.add(jLabel6);
        jPanel2.add(jTextFieldPassword);

        jLabel7.setText("Nom de DataBase :");
        jPanel2.add(jLabel7);
        jPanel2.add(jTextFieldNomBase);

        jButtonValide.setIcon(new javax.swing.ImageIcon("Images/accept.png")); // NOI18N
        jButtonValide.setText("Valider");
        jPanel2.add(jButtonValide);

        jButtonAnnule.setIcon(new javax.swing.ImageIcon("Images/cross.png")); // NOI18N
        jButtonAnnule.setText("Annuler");
        jPanel2.add(jButtonAnnule);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnule;
    private javax.swing.JButton jButtonValide;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPasswordField jPasswordFieldNv;
    private javax.swing.JPasswordField jPasswordFieldNv2;
    private javax.swing.JTextField jTextFieldAdresse;
    private javax.swing.JTextField jTextFieldMailFormateur;
    private javax.swing.JTextField jTextFieldNomBase;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldUser;
    // End of variables declaration//GEN-END:variables

}
