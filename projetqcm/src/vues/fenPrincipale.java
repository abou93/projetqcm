/*
 * fenPrincipale.java
 *
 * Created on 24 octobre 2008, 10:27
 */

package vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import modeles.Promotion;
import modeles.Question;
import modeles.Section;
import modeles.Stagiaire;
import modeles.Test;
import modeles.Type;
import securite.hashPassword;
import vues.JPanelNouvelleReponse;
import controleur.CtrlFormateur;

/**
 *
 * @author  slefort
 */
public class fenPrincipale extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CtrlFormateur ctrl;
    int nbrReponse = 0;
    String[] lettres = {"A","B","C","D","E","F","G","H","I","J"};
	
	/*****************************************************
	*				Constructeur						 *
	******************************************************/
	
    public fenPrincipale() {
    	super();
    	  	
    	ctrl=CtrlFormateur.getCtrlFormateur(); // Recupere l'instance du controleur
    	
    	initComponents(); // Initialise les composants
    	
    	initBarreMenu(); // Initialise la barre de menu
    	initPanelTest(); // Initialise le panel Test
    	initPanelSection(); // Initialise le panel Section
    	initPanelQuestion(); // Initialise le panel Question
    	initJtabbedPane();
    	this.setResizable(false); 
    }


    
    
	/**********************************************************************************************************
    ***********************************************************************************************************
    *** 																			 						***
  	***									Initialisation des panels de l'application							***
  	***					Ajout des listener, initialisation des jTree, jList, jText, etc ...					***
  	***																			 							***
  	***********************************************************************************************************
  	***********************************************************************************************************/
    
    /**********************************************************************************************************
    ***********************************************************************************************************
    *** 																			 						***
   	***									Initialisation du panel tests										***
   	***				Gere tous les aspects liés aux tests ( creation, suppresion, enregistrement				***
   	***				L'inscription des stagiaires est géré dans ce panel	 									***
   	***																			 							***
   	***********************************************************************************************************
   	***********************************************************************************************************/
    
    
    //Rempli la liste des stagiaires inscrits à un test
    private void remplirTreeInscriptions(){
    	Vector<Stagiaire> stag=null;
    	if(ctrl.getTestEnCour()!=null){
    		stag = ctrl.getStagiairesTest(ctrl.getTestEnCour());
    	}
		Vector<Promotion> listePromo = new Vector<Promotion>();
		if(stag!=null && stag.size()>0){
				for(Stagiaire s:stag){
					if(!listePromo.contains(s.getPromotion())){
						listePromo.add(s.getPromotion());
					}
				}
				DefaultMutableTreeNode racineInsc = new DefaultMutableTreeNode("Inscrits");
				jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineInsc));
		    	
				for (Promotion p : listePromo){
		    		DefaultMutableTreeNode sousDossier = new DefaultMutableTreeNode(p);
		    		for (Stagiaire s : stag){
		    			if(s.getPromotion().equals(p)){
		    				sousDossier.add(new DefaultMutableTreeNode(s));
		    			}
		    		}
		    		racineInsc.add(sousDossier);
		    	}
				jTreeListeStagiaireTest.expandPath(jTreeListeStagiaireTest.getPathForRow(0));
		}else{
			DefaultMutableTreeNode racineTest = new DefaultMutableTreeNode("Vide..") ;   	
	    	jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineTest));
		}
	}
    
    //Calcul la date de fin de l'inscription (durée de validité)
    private String calculDate(){
    	Date d = new Date();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		int duree = Integer.parseInt(jSpinnerDureeInscription.getValue().toString());
		calendar.add(Calendar.DATE, duree);
		Date d1 = calendar.getTime();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		return formatDate.format(d1);
    }
    
    private void initPanelTest(){
    	
    	jTextFieldMailFormateur.setText("formateur@eni-ecole");
    	SpinnerNumberModel model = new SpinnerNumberModel(15,0,60,1);
    	jSpinnerDureeInscription.setModel(model);
    	jButtonMotDePasseEleve.setEnabled(false);
    	
    	/***
    	 * Liste des tests
    	 */	
    	ctrl.chargerListeTests();
    	jListTests.setListData(ctrl.getListeTests());
    	
    	//Changement de test sélectionné dans la liste des tests.
    	jListTests.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(jListTests.getSelectedValue()!=null){
					jTextFieldNomTest.setText(((Test)jListTests.getSelectedValue()).getNom());
					jSliderTemps.setValue(((Test)jListTests.getSelectedValue()).getTemps());
					jSliderSeuil.setValue(((Test)jListTests.getSelectedValue()).getSeuil());
					ctrl.setTestEnCour((Test)jListTests.getSelectedValue());
					remplirTreeInscriptions();
				
				}
			}
    	});
    	
    	jListTests.setSelectedIndex(jListTests.getFirstVisibleIndex());
    	remplirTreeInscriptions();
    	
    	//Clic sur Nouveau test
    	jButtonNouveauTest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.nouveauTest();
				jListTests.setListData(ctrl.getListeTests());
				jListTests.setSelectedIndex(jListTests.getLastVisibleIndex());
				jTextFieldNomTest.requestFocusInWindow();
				jTextFieldNomTest.selectAll();
			}
    		
    	});
    	
    	
    	//Clic sur Supprimer Test
    	jButtonSupprimerTest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.supprimerTest((Test)jListTests.getSelectedValue());
				jListTests.setListData(ctrl.getListeTests());
				jListTests.setSelectedIndex(jListTests.getLastVisibleIndex());
			}
    		
    	});
    	
    	
    	//Clic sur Enregistrer Test
    	jButtonEnregistrerTest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Test test = (Test)jListTests.getSelectedValue();
				test.setNom(jTextFieldNomTest.getText().trim());
				test.setSeuil(jSliderSeuil.getValue());
				test.setTemps(jSliderTemps.getValue());
				ctrl.enregistrerTest((Test)jListTests.getSelectedValue());
				jListTests.setListData(ctrl.getListeTests());
			}
    		
    	});
    	
    	
    	//Lister les promotions et les stagiaires de l'ENI
    	DefaultMutableTreeNode racine = new DefaultMutableTreeNode("ENI Ecole") ;
    	for (Promotion p : ctrl.getListePromotions()){
    		DefaultMutableTreeNode sousDossier = new DefaultMutableTreeNode(p);
    		for (Stagiaire s : ctrl.getStagiairesPromo(p)){
    			sousDossier.add(new DefaultMutableTreeNode(s));
    		}
    		racine.add(sousDossier);
    	}
    	
    	//Initialisation des jtree pour l'inscription
    	jTreeListeStagaireEni.setModel(new DefaultTreeModel(racine));
    	//jTreeListeStagaireEni.expandPath(jTreeListeStagaireEni.getPathForRow(1));
    	jTreeListeStagaireEni.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    	jTreeListeStagiaireTest.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    	
    	//Clic sur inscrir un stagiaire au test en cours
    	jButtonIsncrireEleve.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Selectionner la ligne 0 par défaut
				if(jTreeListeStagaireEni.getSelectionCount()==0){
					jTreeListeStagaireEni.setSelectionRow(0);
				}
				
				DefaultMutableTreeNode racineInsc = null;
				TreePath path = jTreeListeStagiaireTest.getPathForRow(0);
				
				if(path.toString().equals("[Vide..]") & !jTreeListeStagaireEni.getLastSelectedPathComponent().toString().equals("ENI Ecole")){
					racineInsc = new DefaultMutableTreeNode("Inscrits");
					jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineInsc));
				}else{
					jTreeListeStagiaireTest.setSelectionRow(0);
					racineInsc = ((DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent());
				}
	
				if(((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject() instanceof Promotion){
					int verif = 0;
					DefaultMutableTreeNode racinePromo = new DefaultMutableTreeNode(((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject());
					Enumeration<DefaultMutableTreeNode> enume = racineInsc.children();
					
					if(enume.hasMoreElements()){
						
						while(enume.hasMoreElements()){
							Promotion p = (Promotion)enume.nextElement().getUserObject();
							if(p.getCode().equals(((Promotion)racinePromo.getUserObject()).getCode())){
								verif++;
							}
						}
						
					}
					
					if(verif==0){
						racineInsc.add(racinePromo);
						Promotion p = (Promotion)racinePromo.getUserObject();
						for(Stagiaire s:p.getListeStagiaires()){
							
							ctrl.inscrirStagiaireTest(s, ctrl.getTestEnCour(), calculDate(), jTextFieldMailFormateur.getText());
							DefaultMutableTreeNode racineStagiaire = new DefaultMutableTreeNode(s);
							racinePromo.add(racineStagiaire);
						}
						
						jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineInsc));
					}
				}else if ((((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject() instanceof Stagiaire)){
					boolean verif=false;
					DefaultMutableTreeNode racinePromo = new DefaultMutableTreeNode(((DefaultMutableTreeNode)((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getParent()).getUserObject());
					DefaultMutableTreeNode racineStagiaire = new DefaultMutableTreeNode(((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject());
					Enumeration<DefaultMutableTreeNode> enume = racineInsc.children();
					
					if(enume.hasMoreElements()){
						
						while(enume.hasMoreElements()){
							DefaultMutableTreeNode node = enume.nextElement();
							Promotion p = (Promotion)node.getUserObject();
							
							if(p.getCode().equals(((Promotion)racinePromo.getUserObject()).getCode())){
								
								if(ctrl.inscrirStagiaireTest((Stagiaire)racineStagiaire.getUserObject(), ctrl.getTestEnCour(),  calculDate(), jTextFieldMailFormateur.getText())){
									node.add(racineStagiaire);
								}
								verif=true;
							}
						}
					}
					if(!verif){
						
						if(ctrl.inscrirStagiaireTest((Stagiaire)racineStagiaire.getUserObject(), ctrl.getTestEnCour(),  calculDate(), jTextFieldMailFormateur.getText())){
							racinePromo.add(racineStagiaire);
							racineInsc.add(racinePromo);
						}
					}
					jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineInsc));
				}
			}
    	});
    	
    	//Clic sur supprimer une inscription
    	jButtonSupIncriptionEleve.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(((DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent()).getUserObject() instanceof Promotion){
					DefaultMutableTreeNode promoSelectionnee = (DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent();
					Enumeration<DefaultMutableTreeNode> enume = promoSelectionnee.children();
					
					while(enume.hasMoreElements()){
						ctrl.supprInscription((Stagiaire)enume.nextElement().getUserObject(), ctrl.getTestEnCour());
					}
					
					((DefaultTreeModel)jTreeListeStagiaireTest.getModel()).removeNodeFromParent((MutableTreeNode)jTreeListeStagiaireTest.getSelectionPath().getLastPathComponent());
					jTreeListeStagiaireTest.setSelectionRow(0);
					
					if(jTreeListeStagiaireTest.getModel().getChildCount(jTreeListeStagiaireTest.getLastSelectedPathComponent())==0){
						DefaultMutableTreeNode racineVide = new DefaultMutableTreeNode("Vide..");
						jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineVide));
					}
					
				}else if (((DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent()).getUserObject() instanceof Stagiaire){
					DefaultMutableTreeNode stagiaireSelectionnee = (DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent();
					TreeNode promoSelectionnee = ((DefaultMutableTreeNode)jTreeListeStagiaireTest.getLastSelectedPathComponent()).getParent();
					
					ctrl.supprInscription((Stagiaire)stagiaireSelectionnee.getUserObject(), ctrl.getTestEnCour());
					
					((DefaultTreeModel)jTreeListeStagiaireTest.getModel()).removeNodeFromParent((MutableTreeNode)jTreeListeStagiaireTest.getSelectionPath().getLastPathComponent());
					jTreeListeStagiaireTest.setSelectionRow(0);
					
					if(promoSelectionnee.getChildCount()==0){
						((DefaultTreeModel)jTreeListeStagiaireTest.getModel()).removeNodeFromParent((DefaultMutableTreeNode)promoSelectionnee);
					}
					
					if(jTreeListeStagiaireTest.getModel().getChildCount(jTreeListeStagiaireTest.getLastSelectedPathComponent())==0){
						DefaultMutableTreeNode racineVide = new DefaultMutableTreeNode("Vide..");
						jTreeListeStagiaireTest.setModel(new DefaultTreeModel(racineVide));
					}
				}
			}
    		
    	});
    	
    	//Gerer le changement de sélection dans le jtree StagiaireEni
    	jTreeListeStagaireEni.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				if(jTreeListeStagaireEni.getSelectionCount()>0){
					if (((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject() instanceof Stagiaire){
						jButtonMotDePasseEleve.setEnabled(true);
					}else{
						jButtonMotDePasseEleve.setEnabled(false);
					}
				}
				
			}
    	});
    	
    	
    	//Clic sur le bouton de changement de mot de passe du stagiaire
    	jButtonMotDePasseEleve.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Stagiaire stagiaire = (Stagiaire)((DefaultMutableTreeNode)jTreeListeStagaireEni.getLastSelectedPathComponent()).getUserObject();
				String nouveauMotDePasse = JOptionPane.showInputDialog(null, "Entrez le nouveau mot de passe de " + stagiaire.toString() + " : ", "Changer le mot de passe", JOptionPane.INFORMATION_MESSAGE);
				hashPassword hash = new hashPassword();
				try {
					nouveauMotDePasse = hash.getHash(nouveauMotDePasse);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				ctrl.changerMotPasseStagiaire(stagiaire,nouveauMotDePasse);				
			}
    		
    	});

    	
    }
    
    
    /**********************************************************************************************************
    ***********************************************************************************************************
    *** 																			 						***
    ***									Initialisation du panel Sections									***
    ***				Gere tous les aspects liés aux sections ( creation, suppresion, enregistrement )		***
    ***				L'affectation des sections aux tests s'effectue dans ce panel							***
    ***																			 							***
    ***********************************************************************************************************
    ***********************************************************************************************************/
 
    private void initPanelSection(){
    	
    	 	
    	/*****************************************************
    	*			Listener du panel section				 *
    	******************************************************/
    		
    	
    	// Listener sur la jList des sections disponible
    	// Mets à jour les zones de textes correspondantes
    	
    	jListSectionDisponible.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Section s = null ;
				if (jListSectionDisponible.getSelectedValue()!=null)
				{
					if (e.getSource()==jListSectionDisponible){
						s = (Section) jListSectionDisponible.getSelectedValue();}
					if (e.getSource()==jListSectionDuTest){
						s = (Section) jListSectionDuTest.getSelectedValue();}
					jTextFieldNomSection.setText(s.getNom());
					jTextFieldNumeroSection.setText(String.valueOf(s.getNumero()));
					jSpinnerNbrQuestionTest.setValue(s.getNbrQuestion());
					ctrl.setSectionEnCour(s);
				}
			}
    		
    	});
    	
    	// Listener sur le bouton d'ajout de section
    	// Ajoute la section dans la liste des sections du test
    	
    	jButtonAjoutSection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Section s = (Section) jListSectionDisponible.getSelectedValue();
				if(s.getQuestions().size()>0)
				{
					ctrl.addSectionListeSectionsParTest(s);
					jListSectionDuTest.setListData(ctrl.getListeSectionsParTest());
					
				}
				else JOptionPane.showMessageDialog(fenPrincipale.this,"La section doit contenir des questions","Erreur affectation",0);
					
			}
    		
    	});
    	
    	// Listener sur le bouton enlever la section
    	// Enleve la section du test (liste et Base de donnée)
    	
    	jButtonEnleverSection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jListSectionDuTest.getSelectedValue()!=null)
				{
					Section s = (Section) jListSectionDuTest.getSelectedValue();
					ctrl.supSectionListeSectionsParTest(s);
					jListSectionDuTest.setListData(ctrl.getListeSectionsParTest());
				}
			}
    		
    	});
    	
    	// Listener sur le bouton nouvelle section
    	// Ajoute la section a la liste
    	
    	jButtonNouveauSection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.nouvelleSection();
				jListSectionDisponible.setListData(ctrl.getListeSection());
				jListSectionDisponible.setSelectedIndex(jListSectionDisponible.getLastVisibleIndex());
			}
    		
    	});

    	// Listener sur le bouton enregistrer la section
    	// Enregistre la section dans la base de donnée
    	
    	jButtonEnregistrerSection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Section s = ctrl.getSectionEnCour();
				s.setNom(jTextFieldNomSection.getText());
				s.setNumero(ctrl.getListeSection().size());
				s.setNbrQuestion((Integer)jSpinnerNbrQuestionTest.getValue());
				ctrl.enregistrerSection(s);
				jListSectionDisponible.setListData(ctrl.getListeSection());
				jListSectionDisponible.setSelectedIndex(jListSectionDisponible.getLastVisibleIndex());
			}
    	});
    	
    	// Listener sur le bouton supprimer la section
    	// Supprime la section dans la base de donnée
    	
    	jButtonSupprimerSection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ctrl.supprimerSection(ctrl.getTestEnCour(), ctrl.getSectionEnCour());
				jListSectionDisponible.setListData(ctrl.getListeSection());
				jListSectionDisponible.setSelectedIndex(jListSectionDisponible.getLastVisibleIndex());
				ctrl.setSectionEnCour(null);
				jTextFieldNomSection.setText("");
				jTextFieldNumeroSection.setText("0");
				
				
			}
    		
    	});
    	
    	/*****************************************************
    	*	Méthodes d'initialisation  du panel Section		 *
    	******************************************************/
    	
    	jTextFieldNumeroSection.setEditable(false);
    	
    	
    	SpinnerNumberModel model = new SpinnerNumberModel(0,0,10,1); 
		jSpinnerNbrQuestionTest.setModel(model);
    	
    	if(ctrl.getTestEnCour()!= null){
    	jTextNomTestPanelSection.setText(ctrl.getTestEnCour().getNom());
    	}
    	
    	ctrl.chargerListeSection();
    	jListSectionDisponible.setListData(ctrl.getListeSection());
    	jListSectionDisponible.setSelectedIndex(jListSectionDisponible.getFirstVisibleIndex());
    	
    	if(ctrl.getTestEnCour()!= null){
    	ctrl.chargerListeSectionsParTest(ctrl.getTestEnCour());
    	jListSectionDuTest.setListData(ctrl.getListeSectionsParTest());
    	}
    		
    }
  
    /**********************************************************************************************************
     **********************************************************************************************************
     *** 																			 						***
     ***									Initialisation du panel Question								***
     ***				Gere tous les aspects liés au question ( creation, suppresion, enregistrement )		***
     ***				L'association des questions a la section en cours s'effectue dans ce panel			***
     ***																			 						***
     **********************************************************************************************************
     **********************************************************************************************************/
 
 	private void initPanelQuestion(){
 	
 		/*****************************************************
 		*		 		Ajout des Listener					 *
 		******************************************************/
 		 		
 		jButtonAjoutDuneReponse.addActionListener(new ActionListener(){
 			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nbrReponse<10){
					jPanelReponse.add(new JPanelNouvelleReponse(lettres[nbrReponse],"",false));
					jPanelReponse.validate();
					jPanelReponse.repaint();
					nbrReponse ++;
				}
			}
 		});
 	
 		jButtonChoixImage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser boite = new JFileChooser();
				boite.setCurrentDirectory(new File("C:\\"));
				boite.setMultiSelectionEnabled(false);
				boite.setFileFilter(new FileNameExtensionFilter("Fichier JPEG","jpg"));
				boite.showOpenDialog(null);
				ctrl.getQuestionEnCour().setCheminImage(boite.getSelectedFile().toString());
			}
 		});
 		
 		
 		jButtonNouvelleQuestion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.getSectionEnCour().addQuestion(new Question());
				jListQuestionDeLaSection.setListData(ctrl.getSectionEnCour().getQuestions());
				jListQuestionDeLaSection.setSelectedIndex(jListQuestionDeLaSection.getLastVisibleIndex());
							
			}
 			
 		});
 		
 		/*****************************************************
 		*		 		Initialisation des composants		 *
 		******************************************************/
 		
 		for(Type type : ctrl.getListeTypes()){
 			jComboBoxListeTypeQuestion.addItem(type);
 		}
 		
 		
 		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Questions Disponibles") ;
    	for (Section s : ctrl.getListeSection()){
    		DefaultMutableTreeNode sousDossier = new DefaultMutableTreeNode(s);
    		for (Question q : ctrl.questionParSection(s)){
    			sousDossier.add(new DefaultMutableTreeNode(q));
    		}
    		racine.add(sousDossier);
    	}
    	jTreeListeQuestionDispo.setModel(new DefaultTreeModel(racine));
    	
 		
    	if(ctrl.getSectionEnCour()!=null){
    		jListQuestionDeLaSection.setListData(ctrl.getSectionEnCour().getQuestions());
    		jListQuestionDeLaSection.setSelectedIndex(jListQuestionDeLaSection.getFirstVisibleIndex());
    		ctrl.setQuestionEnCour((Question)jListQuestionDeLaSection.getSelectedValue());
    		if(ctrl.getQuestionEnCour()!=null){
    			jComboBoxListeTypeQuestion.setSelectedItem(jListQuestionDeLaSection.getSelectedValue()); 		
    			jEditorPaneEnonceQuestion.setText(ctrl.getQuestionEnCour().getEnonce());
    		}
    	}
    	
 		
 		 		
 		if((ctrl.getTestEnCour()!= null)&(ctrl.getSectionEnCour()!=null)){
 			jTextFieldNomSectionPanelQuestion.setText(ctrl.getSectionEnCour().getNom());
 		}
 		
 	
 }
 
      
 	 /*********************************************************************************************************
     **********************************************************************************************************
     *** 																			 						***
     ***									Initialisation de la barre de menu								***
     ***				Ajoute les listeners au jMenuItem													***
     ***																									***
     ***																			 						***
     **********************************************************************************************************
     **********************************************************************************************************/
 	
 	
 	private void initBarreMenu(){

 		
 		jMenuItemQuittez.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenPrincipale.this.dispose();				
			}
    		
    	});
 		
 		
    }
    
 	 /*********************************************************************************************************
     **********************************************************************************************************
     *** 																			 						***
     ***									Initialisation du jTabbedPane									***
     ***				Ajout de listener sur le jtabbedpane												***
     ***				Effectue les mises a jour lors du changement de tab									***
     ***																			 						***
     **********************************************************************************************************
     **********************************************************************************************************/
   
 	private void initJtabbedPane(){
 		
 		jTabbedPanelQcm.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int tab = jTabbedPanelQcm.getSelectedIndex();
				
				switch(tab)
				{
				case 1 :if(ctrl.getTestEnCour()!= null)
						{
			    			jTextNomTestPanelSection.setText(ctrl.getTestEnCour().getNom());
			    		   	ctrl.chargerListeSectionsParTest(ctrl.getTestEnCour());
			    		   	jListSectionDuTest.setListData(ctrl.getListeSectionsParTest());
			    		
			    			
						}
						break;
				case 2 :if((ctrl.getSectionEnCour()!=null))
						{
			 				jTextFieldNomSectionPanelQuestion.setText(ctrl.getSectionEnCour().getNom());
			 				jListQuestionDeLaSection.setListData(ctrl.getSectionEnCour().getQuestions());
							jListQuestionDeLaSection.setSelectedIndex(jListQuestionDeLaSection.getFirstVisibleIndex());
							if ((Question)jListQuestionDeLaSection.getSelectedValue()!=null){
							ctrl.setQuestionEnCour((Question)jListQuestionDeLaSection.getSelectedValue());
					 		jComboBoxListeTypeQuestion.setSelectedItem(jListQuestionDeLaSection.getSelectedValue()); 		
					 		jEditorPaneEnonceQuestion.setText(ctrl.getQuestionEnCour().getEnonce());}
							else jEditorPaneEnonceQuestion.setText("");
			 			}
						
						break;
					
				}
			}
 			
 		});
 	}
 	
 	
 	/**********************************************************************************************************
    ***********************************************************************************************************
    *** 																			 						***
 	***									Initialisation des composants			 							***
 	***									Code generer par NetBeans				 							***
 	***																			 							***
 	***********************************************************************************************************
 	***********************************************************************************************************/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPanelQcm = new javax.swing.JTabbedPane();
        PanelTest = new javax.swing.JPanel();
        labelListeTest = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListTests = new javax.swing.JList();
        jPanelProprietesTest = new javax.swing.JPanel();
        jLabelNomTest = new javax.swing.JLabel();
        jLabelTempsTest = new javax.swing.JLabel();
        jLabelSeuilTest = new javax.swing.JLabel();
        jTextFieldNomTest = new javax.swing.JTextField();
        jTextFieldTempsTest = new javax.swing.JTextField();
        jTextFieldSeuilTest = new javax.swing.JTextField();
        jLabelmin = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSliderTemps = new javax.swing.JSlider();
        jSliderSeuil = new javax.swing.JSlider();
        jButtonSupprimerTest = new javax.swing.JButton();
        jButtonEnregistrerTest = new javax.swing.JButton();
        jButtonNouveauTest = new javax.swing.JButton();
        jButtonImprimerTest = new javax.swing.JButton();
        jPanelInscriptionTest = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeListeStagaireEni = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeListeStagiaireTest = new javax.swing.JTree();
        jButtonIsncrireEleve = new javax.swing.JButton();
        jButtonSupIncriptionEleve = new javax.swing.JButton();
        jLabelListeStagiaireInscritTest = new javax.swing.JLabel();
        jLabelListeStagiaireEni = new javax.swing.JLabel();
        jTextFieldMailFormateur = new javax.swing.JTextField();
        jSpinnerDureeInscription = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonMotDePasseEleve = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        PanelSection = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListSectionDisponible = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNomSection = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNumeroSection = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSpinnerNbrQuestionTest = new javax.swing.JSpinner();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListSectionDuTest = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextNomTestPanelSection = new javax.swing.JTextField();
        jButtonAjoutSection = new javax.swing.JButton();
        jButtonEnleverSection = new javax.swing.JButton();
        jButtonNouveauSection = new javax.swing.JButton();
        jButtonEnregistrerSection = new javax.swing.JButton();
        jButtonSupprimerSection = new javax.swing.JButton();
        PanelQuestion = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jEditorPaneEnonceQuestion = new javax.swing.JEditorPane();
        jComboBoxListeTypeQuestion = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jButtonChoixImage = new javax.swing.JButton();
        jButtonNouvelleQuestion = new javax.swing.JButton();
        jButtonEnregistrerQuestion = new javax.swing.JButton();
        jButtonSupprimmerQuestion = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNomSectionPanelQuestion = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTreeListeQuestionDispo = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jButtonAjoutDuneReponse = new javax.swing.JButton();
        jScrollPaneReponse = new javax.swing.JScrollPane();
        jPanelReponse = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListQuestionDeLaSection = new javax.swing.JList();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButtonAjoutSection1 = new javax.swing.JButton();
        jButtonEnleverSection1 = new javax.swing.JButton();
        MenuQcm = new javax.swing.JMenuBar();
        jMenuMenu = new javax.swing.JMenu();
        jMenuItemQuittez = new javax.swing.JMenuItem();
        jMenuOption = new javax.swing.JMenu();
        jMenuItemOption = new javax.swing.JMenuItem();
        jMenuAide = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion de test QCM");
        setResizable(false);

        PanelTest.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        PanelTest.setForeground(new java.awt.Color(213, 204, 187));

        labelListeTest.setText("Liste des tests :");

        jListTests.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListTests.setToolTipText("Liste des tests existants");
        jScrollPane1.setViewportView(jListTests);

        jPanelProprietesTest.setBorder(javax.swing.BorderFactory.createTitledBorder("Propriétés"));
        jPanelProprietesTest.setToolTipText("Nouveau test");

        jLabelNomTest.setText("Nom :");

        jLabelTempsTest.setText("Temps :");

        jLabelSeuilTest.setText("Seuil :");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jSliderTemps, org.jdesktop.beansbinding.ELProperty.create("${value}"), jTextFieldTempsTest, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jSliderSeuil, org.jdesktop.beansbinding.ELProperty.create("${value}"), jTextFieldSeuilTest, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabelmin.setText("min");

        jLabel1.setText("%");

        jSliderTemps.setMaximum(240);

        jButtonSupprimerTest.setIcon(new javax.swing.ImageIcon("C:\\Images\\Recycle_Bin_Empty32.png")); // NOI18N
        jButtonSupprimerTest.setToolTipText("Supprimer le test");

        jButtonEnregistrerTest.setIcon(new javax.swing.ImageIcon("C:\\Images\\Floppy32.png")); // NOI18N
        jButtonEnregistrerTest.setToolTipText("Sauvegarder le  test");

        jButtonNouveauTest.setIcon(new javax.swing.ImageIcon("C:\\Images\\Add32.png")); // NOI18N

        jButtonImprimerTest.setIcon(new javax.swing.ImageIcon("C:\\Images\\Print32.png")); // NOI18N
        jButtonImprimerTest.setToolTipText("Imprimer le test");

        javax.swing.GroupLayout jPanelProprietesTestLayout = new javax.swing.GroupLayout(jPanelProprietesTest);
        jPanelProprietesTest.setLayout(jPanelProprietesTestLayout);
        jPanelProprietesTestLayout.setHorizontalGroup(
            jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNomTest)
                            .addComponent(jLabelSeuilTest)))
                    .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelTempsTest)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSliderSeuil, 0, 0, Short.MAX_VALUE)
                            .addComponent(jSliderTemps, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(jTextFieldNomTest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                                .addComponent(jTextFieldSeuilTest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                                .addComponent(jTextFieldTempsTest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelmin)))
                        .addGap(195, 195, 195))
                    .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButtonNouveauTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnregistrerTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSupprimerTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                        .addComponent(jButtonImprimerTest)
                        .addContainerGap())))
        );
        jPanelProprietesTestLayout.setVerticalGroup(
            jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNomTest)
                    .addComponent(jTextFieldNomTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelProprietesTestLayout.createSequentialGroup()
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSliderTemps, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelProprietesTestLayout.createSequentialGroup()
                                    .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldTempsTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelmin))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addComponent(jLabelTempsTest, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSliderSeuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSeuilTest)
                            .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldSeuilTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelProprietesTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonEnregistrerTest)
                                .addComponent(jButtonImprimerTest))
                            .addComponent(jButtonNouveauTest)))
                    .addComponent(jButtonSupprimerTest))
                .addContainerGap())
        );

        jPanelInscriptionTest.setBorder(javax.swing.BorderFactory.createTitledBorder("Inscriptions : "));

        jTreeListeStagaireEni.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setViewportView(jTreeListeStagaireEni);

        jTreeListeStagiaireTest.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane3.setViewportView(jTreeListeStagiaireTest);

        jButtonIsncrireEleve.setIcon(new javax.swing.ImageIcon("C:\\Images\\Forward32.png")); // NOI18N
        jButtonIsncrireEleve.setToolTipText("Inscrire le stagiaire");

        jButtonSupIncriptionEleve.setIcon(new javax.swing.ImageIcon("C:\\Images\\Back32.png")); // NOI18N

        jLabelListeStagiaireInscritTest.setText("Stagiaire(s) inscrit(s) au test :");

        jLabelListeStagiaireEni.setText("Liste des stagiaires ENI : ");

        jLabel4.setText("Durée :");

        jLabel5.setText("Mail formateur :");

        jButtonMotDePasseEleve.setIcon(new javax.swing.ImageIcon("C:\\Images\\Key32.png")); // NOI18N
        jButtonMotDePasseEleve.setToolTipText("Modifier le mot de passe du stagiaire");

        jLabel16.setText("Jour(s)");

        javax.swing.GroupLayout jPanelInscriptionTestLayout = new javax.swing.GroupLayout(jPanelInscriptionTest);
        jPanelInscriptionTest.setLayout(jPanelInscriptionTestLayout);
        jPanelInscriptionTestLayout.setHorizontalGroup(
            jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInscriptionTestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonIsncrireEleve)
                            .addComponent(jButtonSupIncriptionEleve, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                    .addComponent(jLabelListeStagiaireEni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelListeStagiaireInscritTest))
                .addGap(34, 34, 34)
                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                        .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                                .addComponent(jSpinnerDureeInscription, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16))
                            .addComponent(jTextFieldMailFormateur, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                        .addComponent(jButtonMotDePasseEleve)
                        .addContainerGap())))
        );
        jPanelInscriptionTestLayout.setVerticalGroup(
            jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelListeStagiaireInscritTest)
                    .addComponent(jLabelListeStagiaireEni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                        .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldMailFormateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jButtonIsncrireEleve, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonSupIncriptionEleve, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelInscriptionTestLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanelInscriptionTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jSpinnerDureeInscription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel16))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jButtonMotDePasseEleve))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelTestLayout = new javax.swing.GroupLayout(PanelTest);
        PanelTest.setLayout(PanelTestLayout);
        PanelTestLayout.setHorizontalGroup(
            PanelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelInscriptionTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelTestLayout.createSequentialGroup()
                        .addGroup(PanelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelListeTest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelProprietesTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelTestLayout.setVerticalGroup(
            PanelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTestLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(PanelTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelTestLayout.createSequentialGroup()
                        .addComponent(labelListeTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelProprietesTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelInscriptionTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPanelQcm.addTab("Tests", PanelTest);

        PanelSection.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jListSectionDisponible.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListSectionDisponible.setToolTipText("Liste des sections disponibles pour le test");
        jScrollPane4.setViewportView(jListSectionDisponible);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Propriétés")));
        jPanel1.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Nom :");
        jPanel1.add(jLabel2);
        jPanel1.add(jTextFieldNomSection);

        jLabel3.setText("Numero :");
        jPanel1.add(jLabel3);
        jPanel1.add(jTextFieldNumeroSection);

        jLabel6.setText("Nombre de question(s)");
        jPanel1.add(jLabel6);
        jPanel1.add(jSpinnerNbrQuestionTest);

        jListSectionDuTest.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListSectionDuTest.setToolTipText("Liste des sections associées au test");
        jScrollPane5.setViewportView(jListSectionDuTest);

        jLabel7.setText("Liste des sections disponibles :");

        jLabel8.setText("Liste des sections du test :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("TEST :");

        jTextNomTestPanelSection.setEditable(false);
        jTextNomTestPanelSection.setFont(new java.awt.Font("Tahoma", 1, 11));

        jButtonAjoutSection.setIcon(new javax.swing.ImageIcon("C:\\Images\\Forward32.png")); // NOI18N
        jButtonAjoutSection.setToolTipText("Ajout d'une section au test");

        jButtonEnleverSection.setIcon(new javax.swing.ImageIcon("C:\\Images\\Back32.png")); // NOI18N
        jButtonEnleverSection.setToolTipText("Enleve la section du test");

        jButtonNouveauSection.setIcon(new javax.swing.ImageIcon("C:\\Images\\Add32.png")); // NOI18N
        jButtonNouveauSection.setToolTipText("Nouvel section pour le test séléctionné");

        jButtonEnregistrerSection.setIcon(new javax.swing.ImageIcon("C:\\Images\\Floppy32.png")); // NOI18N
        jButtonEnregistrerSection.setToolTipText("Enregistrer la section");

        jButtonSupprimerSection.setIcon(new javax.swing.ImageIcon("C:\\Images\\Recycle_Bin_Empty32.png")); // NOI18N
        jButtonSupprimerSection.setToolTipText("Supprimer la section");

        javax.swing.GroupLayout PanelSectionLayout = new javax.swing.GroupLayout(PanelSection);
        PanelSection.setLayout(PanelSectionLayout);
        PanelSectionLayout.setHorizontalGroup(
            PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelSectionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                        .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonAjoutSection)
                            .addComponent(jButtonEnleverSection))
                        .addGap(142, 142, 142)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))
                    .addGroup(PanelSectionLayout.createSequentialGroup()
                        .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelSectionLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelSectionLayout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(jButtonNouveauSection)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonEnregistrerSection)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSupprimerSection)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSectionLayout.createSequentialGroup()
                .addContainerGap(325, Short.MAX_VALUE)
                .addComponent(jTextNomTestPanelSection, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(319, 319, 319))
            .addGroup(PanelSectionLayout.createSequentialGroup()
                .addGap(377, 377, 377)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(386, 386, 386))
        );
        PanelSectionLayout.setVerticalGroup(
            PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSectionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextNomTestPanelSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSectionLayout.createSequentialGroup()
                        .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelSectionLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonEnregistrerSection)
                                    .addComponent(jButtonNouveauSection)
                                    .addComponent(jButtonSupprimerSection))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                .addComponent(jButtonAjoutSection, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonEnleverSection, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))
                    .addGroup(PanelSectionLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTabbedPanelQcm.addTab("Sections", PanelSection);

        PanelQuestion.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Question"));

        jLabel12.setText("Enoncé :");

        jEditorPaneEnonceQuestion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jEditorPaneEnonceQuestion.setToolTipText("Enonce de la question");
        jScrollPane6.setViewportView(jEditorPaneEnonceQuestion);

        jLabel13.setText("Type :");

        jButtonChoixImage.setIcon(new javax.swing.ImageIcon("C:\\Images\\Camera32.png")); // NOI18N
        jButtonChoixImage.setToolTipText("Ajouter un image");

        jButtonNouvelleQuestion.setIcon(new javax.swing.ImageIcon("C:\\Images\\Add32.png")); // NOI18N

        jButtonEnregistrerQuestion.setIcon(new javax.swing.ImageIcon("C:\\Images\\Floppy32.png")); // NOI18N

        jButtonSupprimmerQuestion.setIcon(new javax.swing.ImageIcon("C:\\Images\\Recycle_Bin_Empty32.png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(13, 13, 13)
                            .addComponent(jLabel13)
                            .addGap(18, 18, 18)
                            .addComponent(jComboBoxListeTypeQuestion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButtonNouvelleQuestion)
                            .addGap(26, 26, 26)
                            .addComponent(jButtonEnregistrerQuestion)
                            .addGap(18, 18, 18)
                            .addComponent(jButtonSupprimmerQuestion)
                            .addGap(18, 18, 18)
                            .addComponent(jButtonChoixImage))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBoxListeTypeQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonEnregistrerQuestion)
                    .addComponent(jButtonNouvelleQuestion)
                    .addComponent(jButtonSupprimmerQuestion)
                    .addComponent(jButtonChoixImage))
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("SECTION :");

        jTextFieldNomSectionPanelQuestion.setEditable(false);
        jTextFieldNomSectionPanelQuestion.setFont(new java.awt.Font("Tahoma", 1, 11));

        jTreeListeQuestionDispo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTreeListeQuestionDispo.setToolTipText("Liste des questions disponibles par section");
        //jTreeListeQuestionDispo.setSelectionModel(null);
        jScrollPane7.setViewportView(jTreeListeQuestionDispo);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Réponses"));

        jButtonAjoutDuneReponse.setIcon(new javax.swing.ImageIcon("C:\\Images\\Add32.png")); // NOI18N
        jButtonAjoutDuneReponse.setToolTipText("Ajouter une reponse");

        jPanelReponse.setLayout(new java.awt.GridLayout(10, 0));
        jScrollPaneReponse.setViewportView(jPanelReponse);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAjoutDuneReponse)
                .addContainerGap(319, Short.MAX_VALUE))
            .addComponent(jScrollPaneReponse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButtonAjoutDuneReponse)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneReponse, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        jListQuestionDeLaSection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jListQuestionDeLaSection.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListQuestionDeLaSection.setToolTipText("Liste des questions de la section en cours");
        jScrollPane8.setViewportView(jListQuestionDeLaSection);

        jLabel14.setText("Liste des questions de la section :");

        jLabel15.setText("Liste des questions disponibles :");

        jButtonAjoutSection1.setIcon(new javax.swing.ImageIcon("C:\\Images\\Forward32.png")); // NOI18N
        jButtonAjoutSection1.setToolTipText("Ajout d'une ou plusieurs question(s) dans la section");

        jButtonEnleverSection1.setIcon(new javax.swing.ImageIcon("C:\\Images\\Back32.png")); // NOI18N
        jButtonEnleverSection1.setToolTipText("Enleve la question de la section");

        javax.swing.GroupLayout PanelQuestionLayout = new javax.swing.GroupLayout(PanelQuestion);
        PanelQuestion.setLayout(PanelQuestionLayout);
        PanelQuestionLayout.setHorizontalGroup(
            PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelQuestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelQuestionLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldNomSectionPanelQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(303, 303, 303))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelQuestionLayout.createSequentialGroup()
                        .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelQuestionLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelQuestionLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonEnleverSection1)
                                    .addComponent(jButtonAjoutSection1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        PanelQuestionLayout.setVerticalGroup(
            PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelQuestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNomSectionPanelQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelQuestionLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jButtonAjoutSection1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnleverSection1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelQuestionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(PanelQuestionLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelQuestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jTabbedPanelQcm.addTab("Questions", PanelQuestion);

        jMenuMenu.setText("Menu");

        jMenuItemQuittez.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemQuittez.setText("Quittez");
        jMenuMenu.add(jMenuItemQuittez);

        MenuQcm.add(jMenuMenu);

        jMenuOption.setText("Options");

        jMenuItemOption.setText("Options");
        jMenuOption.add(jMenuItemOption);

        MenuQcm.add(jMenuOption);

        jMenuAide.setText("Aide");

        jMenuItem3.setText("A propos ...");
        jMenuAide.add(jMenuItem3);

        MenuQcm.add(jMenuAide);

        setJMenuBar(MenuQcm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanelQcm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanelQcm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuQcm;
    private javax.swing.JPanel PanelQuestion;
    private javax.swing.JPanel PanelSection;
    private javax.swing.JPanel PanelTest;
    private javax.swing.JButton jButtonAjoutDuneReponse;
    private javax.swing.JButton jButtonAjoutSection;
    private javax.swing.JButton jButtonAjoutSection1;
    private javax.swing.JButton jButtonChoixImage;
    private javax.swing.JButton jButtonEnleverSection;
    private javax.swing.JButton jButtonEnleverSection1;
    private javax.swing.JButton jButtonEnregistrerQuestion;
    private javax.swing.JButton jButtonEnregistrerSection;
    private javax.swing.JButton jButtonEnregistrerTest;
    private javax.swing.JButton jButtonImprimerTest;
    private javax.swing.JButton jButtonIsncrireEleve;
    private javax.swing.JButton jButtonMotDePasseEleve;
    private javax.swing.JButton jButtonNouveauSection;
    private javax.swing.JButton jButtonNouveauTest;
    private javax.swing.JButton jButtonNouvelleQuestion;
    private javax.swing.JButton jButtonSupIncriptionEleve;
    private javax.swing.JButton jButtonSupprimerSection;
    private javax.swing.JButton jButtonSupprimerTest;
    private javax.swing.JButton jButtonSupprimmerQuestion;
    private javax.swing.JComboBox jComboBoxListeTypeQuestion;
    private javax.swing.JEditorPane jEditorPaneEnonceQuestion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelListeStagiaireEni;
    private javax.swing.JLabel jLabelListeStagiaireInscritTest;
    private javax.swing.JLabel jLabelNomTest;
    private javax.swing.JLabel jLabelSeuilTest;
    private javax.swing.JLabel jLabelTempsTest;
    private javax.swing.JLabel jLabelmin;
    private javax.swing.JList jListQuestionDeLaSection;
    private javax.swing.JList jListSectionDisponible;
    private javax.swing.JList jListSectionDuTest;
    private javax.swing.JList jListTests;
    private javax.swing.JMenu jMenuAide;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemOption;
    private javax.swing.JMenuItem jMenuItemQuittez;
    private javax.swing.JMenu jMenuMenu;
    private javax.swing.JMenu jMenuOption;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelInscriptionTest;
    private javax.swing.JPanel jPanelProprietesTest;
    private javax.swing.JPanel jPanelReponse;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPaneReponse;
    private javax.swing.JSlider jSliderSeuil;
    private javax.swing.JSlider jSliderTemps;
    private javax.swing.JSpinner jSpinnerDureeInscription;
    private javax.swing.JSpinner jSpinnerNbrQuestionTest;
    private javax.swing.JTabbedPane jTabbedPanelQcm;
    private javax.swing.JTextField jTextNomTestPanelSection;
    private javax.swing.JTextField jTextFieldMailFormateur;
    private javax.swing.JTextField jTextFieldNomSection;
    private javax.swing.JTextField jTextFieldNomSectionPanelQuestion;
    private javax.swing.JTextField jTextFieldNomTest;
    private javax.swing.JTextField jTextFieldNumeroSection;
    private javax.swing.JTextField jTextFieldSeuilTest;
    private javax.swing.JTextField jTextFieldTempsTest;
    private javax.swing.JTree jTreeListeQuestionDispo;
    private javax.swing.JTree jTreeListeStagaireEni;
    private javax.swing.JTree jTreeListeStagiaireTest;
    private javax.swing.JLabel labelListeTest;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
