import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Arxh extends JFrame implements ActionListener {
	
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//declare the SettingFrame's items
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static String DB_URL = "jdbc:mysql://localhost/LaLiga?autoReconnect=true&useSSL=false";
	  static String USER = "root";
	  static String PASS = "1361998b";
	  static boolean settingchanger=false;
	  static int ID_COACH1 = 0 ;
	  static int ID_REFEREE = 0 ;
	  static int ID_FAN = 0;
	  static int ID_BOSS = 0;
	  //static Arxh app;
	  
	//set the MainFrame
	public static void main(String args[]){
		
		Arxh app = new Arxh();
		
		app.setVisible(true);
		app.setSize(800,800);
		app.setLocation(600,150);
		app.addWindowListener(new Close_Window());
	    
	    
	    
		
	}
	
	//create the MainFrame
	
	public Arxh(){
		
		super("LALIGA project");
	
		//declare the MainFrame's items
		Container cp;
		cp = getContentPane();
	
		JLabel lb1 = new JLabel("Welcome to the official App of the leugue.");
		JLabel lb2 = new JLabel("Select your option : ");
		JButton b1 = new JButton("FAN");
		JButton b2 = new JButton("REFERRE");
		JButton b3 = new JButton("COACH");
		JButton b4 = new JButton("BOSS");
		JButton b5 = new JButton("Settings");
		
		
		
		File file = new File("ImageLaliga.jpg");
        BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(file);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        
		JLabel lb4 = new JLabel(new ImageIcon(img1));
	
		JPanel pl1 = new JPanel();
		JPanel pl2 = new JPanel();
		
		pl2.add(b1);
		b1.addActionListener(new ActionListener() {
			//FAN BUTTON
			public void actionPerformed(ActionEvent e){  
				Fan app5 = new Fan();
				DialogPane_Fan();
				setVisible(false);
				app5.create_fan_frame();
			}
		});
		
		pl2.add(b2);
		b2.addActionListener(new ActionListener(){
			//REFEREE BUTTON
			public void actionPerformed(ActionEvent e){
				Referee app2 = new Referee();
				DialogPane_Ref();
				setVisible(false);
				app2.create_ref_frame();
			}
		});
		
		pl2.add(b3);
		b3.addActionListener(new ActionListener(){
			//COACH BUTTON
			public void actionPerformed(ActionEvent e){		
				Coach app1 = new Coach();
				DialogPane_Coach();
				setVisible(false);
				app1.create_coach_frame();
				
			}

			
		});
		
		pl2.add(b4);
		b4.addActionListener(new ActionListener(){
			//BOSS BUTTON
			public void actionPerformed(ActionEvent e){
				Boss app4 = new Boss();
				DialogPane_Boss();
				setVisible(false);
				app4.create_boss_frame();
			}
		});
		
		pl2.add(b5);
		b5.addActionListener(new ActionListener(){
			//SETTING BUTTON
			public void actionPerformed(ActionEvent e){
				  setVisible(false);
		    	  CreateAndSetSettingFrame(); 	
			}
		});
	    
		
		pl2.add(lb4);
		
	    pl1.add(lb1);
	    pl1.add(lb2);
	    cp.add(pl1,BorderLayout.NORTH);
	   
	    GridLayout gl2 = new GridLayout(3,1,5,6);
		pl2.setLayout(gl2);
		
		cp.add(pl2);
	
	}
	
	//CREATE THE SETTING FRAME

   public void CreateAndSetSettingFrame(){
	   
     //Declare the SettingFrame's items
	   
	  final JFrame frame1 = new JFrame();
		  
      final JTextField DBnamefield = new JTextField(DB_URL); 
	  final JTextField DBuserfield = new JTextField(USER);
	  final JPasswordField DBpasswordfield = new JPasswordField(PASS);
	  JLabel sl1 = new JLabel("DB namefield :");
	  JLabel sl2 = new JLabel("DB userfield :");
	  JLabel sl3 = new JLabel("DB passwordfield :");
	  JButton sb1 = new JButton("OK");  
		  
	//Setting the items
	  
	  GridLayout gl = new GridLayout(4,2);
	  frame1.setLayout(gl);
	  
	  frame1.setSize(800,400);
	  frame1.setLocation(600,300);
	  frame1.setTitle("LALIGA project - DBsettings");
	  frame1.setVisible(true);
	  frame1.addWindowListener(new Close_Window());
	  
	  
	  frame1.add(sl1);
	  frame1.add(DBnamefield);
	  frame1.add(sl2);
	  frame1.add(DBuserfield);
	  frame1.add(sl3);
	  frame1.add(DBpasswordfield);
	  frame1.add(sb1);
	  sb1.addActionListener(new ActionListener (){
		  
		  @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			  
			     frame1.setVisible(false);
     	         frame1.dispose();
     	    
			     DB_URL = DBnamefield.getText();
			     USER = DBuserfield.getText();
			     PASS = DBpasswordfield.getText();
			     settingchanger = true;
			     
			     setVisible(true);  
		  }		 		  
	  });
	  	 
     }
    
    
    public void DialogPane_Coach(){
    	 
    	 String string1; 
         string1=JOptionPane.showInputDialog(this,"Hello Coach. Give your ID:","LALIGA project - Coach",JOptionPane.QUESTION_MESSAGE);
    	 ID_COACH1=Integer.parseInt(string1);
    }
    
    public void DialogPane_Ref(){
     
         String string2;	
    	 string2=JOptionPane.showInputDialog(this,"Hello Referee. Give your ID:","LALIGA project - Referee",JOptionPane.QUESTION_MESSAGE);
         ID_REFEREE=Integer.parseInt(string2);
    }
	
    public void DialogPane_Fan(){
    	
    	String string3;
    	string3=JOptionPane.showInputDialog(this,"Hello Fan. Give your ID:","LALIGA project - Fan",JOptionPane.QUESTION_MESSAGE);
    	ID_FAN=Integer.parseInt(string3);
    }
    
    public void DialogPane_Boss(){
    	
    	String string4;
    	string4=JOptionPane.showInputDialog(this,"Hello Boss. Give your ID:","LALIGA project - Boss",JOptionPane.QUESTION_MESSAGE);
    	ID_BOSS=Integer.parseInt(string4);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
	public void final_panel_1arg(ArrayList<String> x1 , String p, final JFrame r){
		   
		   final JFrame ffr1 = new JFrame();
		   ffr1.setVisible(true);
		   ffr1.setSize(700, 500);
		   ffr1.setLocation(600, 150);
		   ffr1.setDefaultCloseOperation(EXIT_ON_CLOSE);
		   ffr1.setResizable(true);
		   ffr1.setTitle("LALIGA project");
		   
		   r.setVisible(false);
		   r.dispose();
		   
		   JButton ffb1 = new JButton("Back");
		  
		   if(p.equals("v")){
		   
			   final JList<String> list = new JList<String>(x1.toArray(new String[0]));
			
			   list.setLayoutOrientation(JList.VERTICAL);
	    	   JScrollPane scrollPane = new JScrollPane();
	    	   scrollPane.getViewport().add(list);
	    	   ffr1.add(scrollPane, BorderLayout.CENTER);
	   	       
		       ffr1.add(ffb1, BorderLayout.SOUTH);
		       ffb1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				   
				   ffr1.setVisible(false);
				   ffr1.dispose();
				   
				   
				   r.setVisible(true);
				   r.setSize(700, 500);
				   r.setLocation(600,150);
				   
			   }
			   
		   });
		   
	      }else if(p.equals("t")){
		   
	    	   final JList<String> list = new JList<String>(x1.toArray(new String[0]));
	    	   
	    	   list.getCellRenderer();
	   	       
	    	   JScrollPane scrollPane = new JScrollPane();
	   	       scrollPane.getViewport().add(list);
	   	   
	   	       ffr1.add(scrollPane, BorderLayout.CENTER);
	   	       
	   	       ffr1.add(ffb1, BorderLayout.SOUTH);
		       ffb1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				   
				   ffr1.setVisible(false);
				   ffr1.dispose();
				   
				   
				   r.setVisible(true);
				   r.setSize(700, 500);
				   r.setLocation(600,150);
				   
			   }
		   
		     });
	   }

	} 
     






public void Load_image(String x, JFrame y,  JPanel p3){
	   
	   
	   
	   switch (x){
		   
	   case ("Real Madrid FC") :
		   
		     File file1 = new File("realmadrid.jpg");
          BufferedImage teamlogo1 = null;
		     try {
			     teamlogo1 = ImageIO.read(file1);
		     } catch (IOException e1) {
			     e1.printStackTrace();
		     }
		     
		     JLabel blb2 = new JLabel(new ImageIcon(teamlogo1));
		     p3.add(blb2);
		     
		     y.add(p3, BorderLayout.SOUTH);
		     
		   break;
	   
	   case ("Barcelona FC") :
		   
		     File file2 = new File("barca.jpg");
          BufferedImage teamlogo2 = null;
	         try {
		         teamlogo2 = ImageIO.read(file2);
	         } catch (IOException e1) {
		         e1.printStackTrace();
	         }
	     
	         JLabel blb3 = new JLabel(new ImageIcon(teamlogo2));
	         p3.add(blb3);
	     
	         y.add(p3, BorderLayout.SOUTH);
		   
		   break;
	   
	   case ("Sevilla FC") :
		   
		     File file3 = new File("sevilla.jpg");
          BufferedImage teamlogo3 = null;
          try {
	             teamlogo3 = ImageIO.read(file3);
          } catch (IOException e1) {
	             e1.printStackTrace();
          }

          JLabel blb4 = new JLabel(new ImageIcon(teamlogo3));
          p3.add(blb4);

          y.add(p3, BorderLayout.SOUTH);
		   
		   break;
	   
    case ("Valencia FC") :
 	   
		     File file4 = new File("vallencia.jpg");
          BufferedImage teamlogo4 = null;
          try {
              teamlogo4 = ImageIO.read(file4);
          } catch (IOException e1) {
              e1.printStackTrace();
          }

          JLabel blb5 = new JLabel(new ImageIcon(teamlogo4));
          p3.add(blb5);

          y.add(p3, BorderLayout.SOUTH);
		   
		   break;
	   
	   case ("Atletico Madrid FC") :
		   
		     File file5 = new File("athleticmad.jpg");
          BufferedImage teamlogo5 = null;
          try {
              teamlogo5 = ImageIO.read(file5);
          } catch (IOException e1) {
              e1.printStackTrace();
          }

          JLabel blb6 = new JLabel(new ImageIcon(teamlogo5));
          p3.add(blb6);

          y.add(p3, BorderLayout.SOUTH);
		   
		   break;
	   
	   case ("Espanyol FC") :
		   
		     File file6 = new File("espanyol.jpg");
          BufferedImage teamlogo6 = null;
          try {
              teamlogo6 = ImageIO.read(file6);
          } catch (IOException e1) {
              e1.printStackTrace();
          }

          JLabel blb7 = new JLabel(new ImageIcon(teamlogo6));
          p3.add(blb7);

          y.add(p3, BorderLayout.SOUTH);
		   
		   break;
	   
	   case ("Ragio Vallecano FC") :
		   
		   File file7 = new File("ragio.jpg");
        BufferedImage teamlogo7 = null;
        try {
            teamlogo7 = ImageIO.read(file7);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JLabel blb8 = new JLabel(new ImageIcon(teamlogo7));
        p3.add(blb8);

        y.add(p3, BorderLayout.SOUTH);
		   
		  break;
	   
	   case ("Athletic Bilbao FC") :
		   
		   File file8 = new File("athleticbilbao.jpg");
        BufferedImage teamlogo8 = null;
        try {
            teamlogo8 = ImageIO.read(file8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JLabel blb9 = new JLabel(new ImageIcon(teamlogo8));
        p3.add(blb9);

        y.add(p3, BorderLayout.SOUTH);
		   
		  break;
	   
	   default :
		   
		   break;
		   
	   }
}

}









class Close_Window extends WindowAdapter {
	
	public void windowClosing(WindowEvent e){
	System.exit(0);
   }
}



