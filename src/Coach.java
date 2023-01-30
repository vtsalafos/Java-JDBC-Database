import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;


//import javax.swing.event.*;




public class Coach extends Arxh{

	/**
	 * 
	 */
   private static final long serialVersionUID = 1L;
	
	
   private static String co_name = null;
   private static String wo_team = null;
   private static String newplayer = null;
   private static ArrayList <String> namelist = new ArrayList<String>();
   private static ArrayList <String> schedlist = new ArrayList<String>();
   private static ArrayList <String> statlist = new ArrayList<String>();

   final JFrame cframe = new JFrame();
   
	
   public void find_name(int x){
	   
	   
	     Connection conn = null;
	   	 Statement stmt = null;
	   
	try {
			Class.forName(JDBC_DRIVER);
			//System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt = conn.createStatement();
			String sql1;
			sql1 = "SELECT coach_name, working_team FROM coach WHERE id_coach = "+x+";";
			ResultSet rs = stmt.executeQuery(sql1);  
			   
			while(rs.next()){
			     co_name = rs.getString("coach_name");
			     wo_team = rs.getString("working_team");
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
	
   public void create_coach_frame(){
	
	
	   find_name(ID_COACH1);
	
	   
	   
	   
	   cframe.setSize(700, 500);
	   cframe.setLocation(600, 150);
	   cframe.setTitle("LALIGA project - Coach");
	   cframe.setVisible(true);
	   cframe.addWindowListener(new Close_Window());
	 
	  
	   JPanel cpl1 = new JPanel();
	   JPanel cpl2 = new JPanel();
	   JPanel cpl3 = new JPanel();
	   
	   JLabel clb1 = new JLabel("Hello Coach "+co_name+". Choose an option :");
	   
	   JButton cb1 = new JButton("Show the players");
	   JButton cb2 = new JButton("Statistics");
	   JButton cb3 = new JButton("Add new player");
	   JButton cb4 = new JButton("Schedule");
	   JButton cb5 = new JButton("Back");
 	   final JTextField ctxf3 = new JTextField(30);
	   
	   // LABEL ----------------------------------------------------------------------
	   cpl1.add(clb1);
	   // SHOW THE PLAYERS BUTTON ----------------------------------------------------
	   cpl2.add(cb1);
	   cb1.addActionListener(new ActionListener(){
		   
		   public void actionPerformed(ActionEvent e) {
		   
			   
			   sh_players(wo_team);
			   final_panel_1arg(namelist,"v",cframe);
		   }
			   
	   });
	   // STATISTICS BUTTON ----------------------------------------------------------
	   cpl2.add(cb2);
	   cb2.addActionListener(new ActionListener(){
		   
		   public void actionPerformed(ActionEvent e){
			   
			   sh_statistics(wo_team);
			   final_panel_1arg(statlist, "t", cframe);
		   }
		   
	   });
	   // ADD PLAYER BUTTON ----------------------------------------------------------
	   cpl2.add(cb3);
	   cpl2.add(ctxf3);
	   cb3.addActionListener(new ActionListener(){
		   
		   public void actionPerformed(ActionEvent e){
			   
			   newplayer = ctxf3.getText();
			   pl_insert(newplayer);
			   
		   }
		   
		   
	   });
	   // SCHEDULE BUTTON ------------------------------------------------------------
	   cpl2.add(cb4);
	   cb4.addActionListener(new ActionListener(){
		   
		   public void actionPerformed(ActionEvent e){
			   
			   sh_schedule(wo_team);
			   final_panel_1arg(schedlist, "t", cframe);
		   }
		   
	   });
	   // BACK BUTTON ----------------------------------------------------------------
	   cpl2.add(cb5);
	   cb5.addActionListener(new ActionListener(){
		   
		   public void actionPerformed(ActionEvent e){
			   
			   cframe.setVisible(false);
			   cframe.dispose();
			   
			   setVisible(true);
			   setLocation(600,150);
			   setSize(800,800);
			   
		   }
		   
	   });
	   
	   
	   GridLayout gl = new GridLayout(3, 2, 8, 8);
	   cpl2.setLayout(gl);
	   
	   cframe.add(cpl1,BorderLayout.NORTH);
	   cframe.add(cpl2);
	   	 
	   Load_image(wo_team,cframe,cpl3);
	   
   }


   
   private void sh_players(String y){
	   
   namelist.clear();
	   
   Connection conn1 = null;
   Statement stmt1 = null;
	
	try {
		Class.forName(JDBC_DRIVER);
		conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	    stmt1 = conn1.createStatement();
		String sql1;
		sql1 = "SELECT name_player FROM podosfairistes WHERE belong_team = '"+y+"';";
		ResultSet rs = stmt1.executeQuery(sql1);  
		   
		while(rs.next()){
			
			 namelist.add(rs.getString("name_player"));
		
			}
		
		rs.close();
		stmt1.close() ;
		conn1.close();
		
	    }catch(SQLException se){   
	    	se.printStackTrace();
	    }catch(Exception h){
	        h.printStackTrace();
     }finally{
	      //finally block used to close resources
	      try{
	         if(stmt1!=null)
	            stmt1.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn1!=null)
	            conn1.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
     }//end try
   
   
   
   
   
    }
	
   private void sh_statistics(String y){
	   
	       statlist.clear();
	       int s1 = 0;
    	   Connection conn1 = null;
    	   Statement stmt1 = null;
    		
    		try {
    			Class.forName(JDBC_DRIVER);
    			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
    		    
    		    stmt1 = conn1.createStatement();
    			String sql1;
    			sql1 = "SELECT time_m, date_m, score_m, result_m, sides, f_name FROM matchday INNER JOIN fields ON stadium = id_fields WHERE date_m <= '2017-03-17' AND sides LIKE '%"+y+"%';";
    			ResultSet rs = stmt1.executeQuery(sql1);  
    			   
    			while(rs.next()){
    				
    				String str = null;
    				
    				s1++;
    				String id = Integer.toString(s1);
    				String time = rs.getString("time_m");
    				String date = rs.getString("date_m");
    				String score = rs.getString("score_m");
    				String result = rs.getString("result_m");
    				String sides = rs.getString("sides");
    				String f_name = rs.getString("f_name");
    				
    				str = id+"\t    "+time+"\t    "+date+"\t    "+score+"\t    "+result+"\t    "+sides+"\t    "+f_name;
    				statlist.add(str);
    				}
    			
    			rs.close();
    			stmt1.close() ;
    			conn1.close();
    			
    		    }catch(SQLException se){   
    		    	se.printStackTrace();
    		    }catch(Exception h){
    		        h.printStackTrace();
    	     }finally{
    		      //finally block used to close resources
    		      try{
    		         if(stmt1!=null)
    		            stmt1.close();
    		      }catch(SQLException se2){
    		      }// nothing we can do
    		      try{
    		         if(conn1!=null)
    		            conn1.close();
    		      }catch(SQLException se){
    		         se.printStackTrace();
    		      }//end finally try
    	     }//end try
	   
	   
	   
	   
    }
   
   private void sh_schedule(String y){
	   
	   schedlist.clear();
	   int s1 = 11;
	   Connection conn1 = null;
	   Statement stmt1 = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt1 = conn1.createStatement();
			String sql1;
			sql1 = "SELECT time_m, date_m, score_m, result_m, sides, f_name FROM matchday INNER JOIN fields ON stadium = id_fields WHERE date_m > '2017-03-17' AND sides LIKE '%"+y+"%';";
			ResultSet rs = stmt1.executeQuery(sql1);  
			   
			
			while(rs.next()){
				
				String str = null;
				
				s1++;
				String id = Integer.toString(s1);
				String time = rs.getString("time_m");
				String date = rs.getString("date_m");
				String score = rs.getString("score_m");
				String result = rs.getString("result_m");
				String sides = rs.getString("sides");
				String f_name = rs.getString("f_name");
				
				str = id+"\t    "+time+"\t    "+date+"\t    "+score+"\t    "+result+"\t    "+sides+"\t    "+f_name;
				schedlist.add(str);
				}
			
			rs.close();
			stmt1.close() ;
			conn1.close();
			
		    }catch(SQLException se){   
		    	se.printStackTrace();
		    }catch(Exception h){
		        h.printStackTrace();
	     }finally{
		      //finally block used to close resources
		      try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn1!=null)
		            conn1.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	     }//end try
	   
   }
		
   private void pl_insert(String y){
	   
	   Connection conn1 = null;
	   Statement stmt1 = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt1 = conn1.createStatement();
			String sql1;
			sql1 = "INSERT INTO podosfairistes VALUES (null, '"+y+"', 0, 'MESA', 0, '----', '"+wo_team+"');";
			stmt1.executeUpdate(sql1);  

			stmt1.close() ;
			conn1.close();
			
		    }catch(SQLException se){   
		    	se.printStackTrace();
		    }catch(Exception h){
		        h.printStackTrace();
	     }finally{
		      //finally block used to close resources
		      try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn1!=null)
		            conn1.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	     }//end try
	   
	   
	   
   }
   
}   