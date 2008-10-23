package test;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class TestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar barreMenu = null;
	private JMenu Menu = null;
	private JMenu Options = null;
	private JMenuItem Quitter = null;
	private JMenuItem MdpFormateur = null;
	private JTabbedPane Onglets = null;

	/**
	 * This is the default constructor
	 */
	public TestFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 500);
		this.setJMenuBar(getBarreMenu());
		this.setContentPane(getJContentPane());
		this.setTitle("Création de QCM");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getOnglets(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes barreMenu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getBarreMenu() {
		if (barreMenu == null) {
			barreMenu = new JMenuBar();
			barreMenu.add(getMenu());
			barreMenu.add(getOptions());
		}
		return barreMenu;
	}

	/**
	 * This method initializes Menu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenu() {
		if (Menu == null) {
			Menu = new JMenu();
			Menu.setText("Menu");
			Menu.add(getQuitter());
		}
		return Menu;
	}

	/**
	 * This method initializes Options	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getOptions() {
		if (Options == null) {
			Options = new JMenu();
			Options.setText("Options");
			Options.add(getMdpFormateur());
		}
		return Options;
	}

	/**
	 * This method initializes Quitter	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getQuitter() {
		if (Quitter == null) {
			Quitter = new JMenuItem();
			Quitter.setText("Quitter");
		}
		return Quitter;
	}

	/**
	 * This method initializes MdpFormateur	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMdpFormateur() {
		if (MdpFormateur == null) {
			MdpFormateur = new JMenuItem();
			MdpFormateur.setText("Mot de passe formateur");
		}
		return MdpFormateur;
	}

	/**
	 * This method initializes Onglets	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getOnglets() {
		if (Onglets == null) {
			Onglets = new JTabbedPane();
			Onglets.add(new JPanel());
			Onglets.setTitleAt(0, "Test");
			Onglets.add(new JPanel());
			Onglets.setTitleAt(1, "Sections");
			Onglets.add(new JPanel());
			Onglets.setTitleAt(2, "Questions");
		}
		return Onglets;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
