package test;

 import java.awt.*;

 /**
 * @author boubavic
 * Ma class splash screen difficile de faire + simple ;o)
 */
 public class SplashTest extends Frame
 {
     Image[] img;
    
     public SplashTest()
     {
         super();
         setSize(640,400);
         setUndecorated( true );
         setFocusable( false );
         setEnabled( false );
         String fileloc = "C:/Images/Splashscreen2.png";
         img = new Image[1];
         img[0] = this.getToolkit().createImage( fileloc );
         try
         {
             MediaTracker mTrack = new MediaTracker( this ); // load les image avan de les afficher  
             for ( int i = 0; i < img.length; i++ )
                 mTrack.addImage( img[ i ], i );
             mTrack.waitForAll();
         } catch( Exception e ) { System.out.println(" setimages e : " + e ); }
     }
    
     public void paint( Graphics g )
     {
         super.paint( g );
         Dimension d = this.getSize();
         g.drawImage( img[0], 0, 0, d.width, d.height, this ); // dessine l image  
     }
    
     static public void main( String args[] )
     {
         try
         {
             GraphicsEnvironment ge = GraphicsEnvironment.
             getLocalGraphicsEnvironment();
             GraphicsDevice[] gs = ge.getScreenDevices();
             GraphicsDevice gd = gs[0];
             GraphicsConfiguration[] gc = gd.getConfigurations();
             Rectangle r = gc[0].getBounds();
             Point pt = new Point( (int)r.width/2, (int)r.height/2 );
             SplashTest sp = new SplashTest();
             Point loc = new Point( pt.x - 320, pt.y - 200 );
             sp.setLocation( loc );
             sp.setVisible(true);
           
             // apres fau metre le splash en parametre a l apli  
             // et c elle ki fait le setvisible (false ) qd elle a fini de bosser  
             // genre ( sur son setvisible( true ) ou a la fin du constructeur ou autre... ;o)  
            
          
             //comme sa on peu pas la lancer plus tot ;o)
            
            
         }
         catch (Exception e)
         {
             System.out.println("enclosing_package.enclosing_method : " + e);
         }
     }
   
}