
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;


public class Boss extends Arxh{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String na_boss=null;
	private static String his_team=null;
	private static ArrayList <String> ticketlist = new ArrayList<String>();

	
	final JFrame bframe = new JFrame();
	
	public void find_name(int x){
		   
   	     Connection conn = null;
	  	 Statement stmt = null;
	  
	try {
			Class.forName(JDBC_DRIVER);
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = conn.createStatement();
			String sql1;
			sql1 = "SELECT name_boss, team_name  FROM proedros INNER JOIN dioikoi ON id_boss = hgeths_id INNER JOIN omada ON team_name = pae_name WHERE id_boss = "+x+";";
			ResultSet rs = stmt.executeQuery(sql1);  
			   
			while(rs.next()){
				
			     na_boss = rs.getString("name_boss");
			     his_team = rs.getString("team_name");
			}
			
			rs.close();
			stmt.close() ;
			conn.close();
			
		    }catch(SQLException se){   
		    	se.printStackTrace();
		    }catch(Exception h){
		        h.printStackTrace();
	   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	   }//end try
	}

	
	public void create_boss_frame(){
		
		   find_name(ID_BOSS);
		   
		   bframe.setSize(700, 700);
		   bframe.setLocation(600, 150);
		   bframe.setTitle("LALIGA project - Boss");
		   bframe.setVisible(true);
		   bframe.addWindowListener(new Close_Window());
		
		   
		   JPanel cpl1 = new JPanel();
		   JPanel cpl2 = new JPanel();
		   JPanel cpl3 = new JPanel();
		   
		   JLabel clb1 = new JLabel("Hello mister " +na_boss+ ". Choose an option :");
		
		   JButton cb1 = new JButton("Income - Tickets sold");
		   JButton cb3 = new JButton("Offer for season tickets");
		   JButton cb4 = new JButton("Offer for normal tickets");
		   JButton cb5 = new JButton("Back");
		   
		// LABEL ----------------------------------------------------------------------
		   cpl1.add(clb1);
		   
		// INCOME ----------------------------------------------------
		   cpl2.add(cb1);
		   cb1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
			   
				   income(his_team);
				   final_panel_1arg(ticketlist, "v", bframe);
			   }
				   
		   });
		   

		   
		// OFFER FOR SEASON TICKET ----------------------------------------------------
		   cpl2.add(cb3);
		   cb3.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				   
				   season_offer(his_team);
				   
			   }
				   
		   });
		   
		// OFFER AT NORMAL TICKET ----------------------------------------------------
		   cpl2.add(cb4);
		   cb4.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				
				  normal_offer(his_team);
				   
			   }
				   
		   });
		   
		// BACK BUTTON ----------------------------------------------------------------
		   cpl2.add(cb5);
		   cb5.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				   
				   bframe.setVisible(false);
				   bframe.dispose();
				   
				   setVisible(true);
				   setLocation(600,150);
				   setSize(800,800);
				   
			   }
			   
		   });
		
		   GridLayout gl = new GridLayout(2, 2, 8, 8);
		   cpl2.setLayout(gl);
		   
		   bframe.add(cpl1,BorderLayout.NORTH);
		   bframe.add(cpl2);
            
		   Load_image(his_team, bframe, cpl3 );
		
	}
	
	 public void DialogPane_SuccessOffer(){
	    	
	    	
	    	JOptionPane.showMessageDialog(this,"Hello Boss. You have made a successfull Offer ");
	    	
	    }
	 
	public void income(String e){

		ticketlist.clear();
		int sumofST = 0;
		int sumofNOR = 0;
		
		 Connection conn = null;
	  	 Statement stmt = null;
	  
	    try {
			Class.forName(JDBC_DRIVER);
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = conn.createStatement();
			String sql1, sql2;
			sql1 = "SELECT COUNT(*) FROM tickets INNER JOIN fans ON kodikos_fan = id_fan WHERE t_category = 'DIARKEIAS' AND supporting_team = '"+e+"' GROUP BY t_category";
			sql2 = "SELECT COUNT(*) FROM tickets INNER JOIN fans ON kodikos_fan = id_fan WHERE t_category = 'APLO' AND supporting_team = '"+e+"' GROUP BY t_category";
			ResultSet rs1 = stmt.executeQuery(sql1);  
			   
			while(rs1.next()){
				
			     String str1 = rs1.getString("COUNT(*)");
			     sumofST = Integer.parseInt(str1);
			     
			}
			
			rs1.close();

			ResultSet rs2 = stmt.executeQuery(sql2);  

			while(rs2.next()){
				
			     String str2 = rs2.getString("COUNT(*)");
			     sumofNOR = Integer.parseInt(str2);
			     
			}
			
			rs2.close();
			stmt.close() ;
			conn.close();
			
		    }catch(SQLException se){   
		    	se.printStackTrace();
		    }catch(Exception h){
		        h.printStackTrace();
	   }finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	   }//end try
		
	    
	    String fstr1 = "The quantity of Season Tickets is : "+ sumofST +" and the quantity of Normal Tickets is : "+sumofNOR;
		String fstr2 = "The Total Profit of Tickets is : "+(sumofST*200+sumofNOR*10);
		ticketlist.add(fstr1);
		ticketlist.add(fstr2);
	}

    public void season_offer(String e){
		
		String namefan = "";
		Choice ch = new Choice();
 
		 //GET all the fields from database and make an array----------------
		 
		 Connection conn = null;
	   	 Statement stmt = null;
	   	 
	   	 try{
	   	      
	   	     Class.forName("com.mysql.jdbc.Driver");
	   	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   	      stmt = conn.createStatement();
	   	      String sql;	  
	   	      sql = "SELECT name_fan FROM fans INNER JOIN tickets ON kodikos_fan = id_fan WHERE supporting_team = '"+e+"' AND t_category = 'DIARKEIAS';";
	   	      ResultSet rs = stmt.executeQuery(sql);   	      
	   	      while(rs.next()){
	   	         //Retrieve by column name
	   	    	 namefan = rs.getString("name_fan");
	   		   	 ch.add(namefan);
	   	    	
	   	      }
	   	      //----------------------------
	   	 //STEP 6: Clean-up environment
	   	      rs.close();
	   	      stmt.close();
	   	      conn.close();
	   	   }catch(SQLException se){
	   	      //Handle errors for JDBC
	   	      se.printStackTrace();
	   	   }catch(Exception e3){
	   	      //Handle errors for Class.forName
	   	      e3.printStackTrace();
	   	   }
		
//-----------------------------------------------------------------------------------	
	   	 
		final JFrame s_offer = new JFrame();
		s_offer.setVisible(true);
		s_offer.setSize(800,400);
		s_offer.setLocation(600,300);
		s_offer.setTitle("LALIGA project - Season Ticket Offer");
		s_offer.addWindowListener(new Close_Window());

		JPanel sopl1 = new JPanel();
		JPanel sopl2 = new JPanel();

		
		JButton sob1 = new JButton("OK");
		JButton sob2 = new JButton("Back");
		JLabel solb1 = new JLabel("Choose the Fan that you want to make an offer for the Season Tickets :");

		sopl1.add(solb1);
		
		sopl2.add(ch);
		
		sopl2.add(sob1);
		sob1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				 
				   DialogPane_SuccessOffer();
	               s_offer.dispose();
			   }
				   
		   });
		
		sopl2.add(sob2);
		sob2.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				 
				   			   
				   s_offer.setVisible(false);
				   s_offer.dispose();
				   
				   bframe.setVisible(true);
				   bframe.setLocation(600,150);
				   bframe.setSize(800,800);
				   
			   }
				   
		   });
		
		   GridLayout sogl = new GridLayout(2, 2, 8, 8);
		   sopl2.setLayout(sogl);
		   
		   s_offer.add(sopl1,BorderLayout.NORTH);
		   s_offer.add(sopl2);
		
	}
	
    public void normal_offer(String e){
		
		String namefan = "";
		Choice ch = new Choice();
 
		 //GET all the fields from database and make an array----------------
		 
		 Connection conn = null;
	   	 Statement stmt = null;
	   	 
	   	 try{
	   	      
	   	     Class.forName("com.mysql.jdbc.Driver");
	   	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   	      stmt = conn.createStatement();
	   	      String sql;	  
	   	      sql = "SELECT name_fan FROM fans INNER JOIN tickets ON kodikos_fan = id_fan WHERE supporting_team = '"+e+"' AND t_category = 'APLO';";
	   	      ResultSet rs = stmt.executeQuery(sql);   	      
	   	      String str = null;
	   	      while(rs.next()){
	   	         //Retrieve by column name
	   	    	  
	   	    	 namefan = rs.getString("name_fan");
	   	    	 if(!namefan.equals(str)){
	   		   	 ch.add(namefan);
	   	    	 str = namefan;
	   	    	 }
	   	    	
	   	      }
	   	      //----------------------------
	   	 //STEP 6: Clean-up environment
	   	      rs.close();
	   	      stmt.close();
	   	      conn.close();
	   	   }catch(SQLException se){
	   	      //Handle errors for JDBC
	   	      se.printStackTrace();
	   	   }catch(Exception e3){
	   	      //Handle errors for Class.forName
	   	      e3.printStackTrace();
	   	   }
		
//-----------------------------------------------------------------------------------	
	   	 
		final JFrame n_offer = new JFrame();
		n_offer.setVisible(true);
		n_offer.setSize(800,400);
		n_offer.setLocation(600,300);
		n_offer.setTitle("LALIGA project - Season Ticket Offer");
		n_offer.addWindowListener(new Close_Window());

		JPanel nopl1 = new JPanel();
		JPanel nopl2 = new JPanel();

		
		JButton nob1 = new JButton("OK");
		JButton nob2 = new JButton("Back");

		JLabel nolb1 = new JLabel("Choose the Fan that you want to make an offer for the Season Tickets :");

		nopl1.add(nolb1);
		
		nopl2.add(ch);
		
		nopl2.add(nob1);
		nob1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				 
				   DialogPane_SuccessOffer();
				   
				   n_offer.setVisible(false);
				
				   
			   }
				   
		   });
		
		
		nopl2.add(nob2);
		nob2.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
				 
				   
				   n_offer.setVisible(false);
				   n_offer.dispose();
				   
				   bframe.setVisible(true);
				   bframe.setLocation(600,150);
				   bframe.setSize(800,800);
				   
			   }
				   
		   });
		
		   GridLayout nogl = new GridLayout(2, 2, 8, 8);
		   nopl2.setLayout(nogl);
		   
		   n_offer.add(nopl1,BorderLayout.NORTH);
		   n_offer.add(nopl2);
		
	}
	
	  
}
