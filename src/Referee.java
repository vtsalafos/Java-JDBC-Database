import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
//import javax.swing.event.*;

public class Referee extends Arxh {
   
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String re_name = null;
	private static String s_date = null;
	private static ArrayList <String> schedlist = new ArrayList<String>();
	
	public void create_ref_frame(){
		
		final JFrame rframe = new JFrame();
		final JTextField rtx1 = new JTextField(); 
		
		   find_name(ID_REFEREE);
		   
		   rframe.setSize(700, 700);
		   rframe.setLocation(600, 150);
		   rframe.setTitle("LALIGA project - Referee");
		   rframe.setVisible(true);
		   rframe.addWindowListener(new Close_Window());
		   
		   JPanel rpl1 = new JPanel();
		   JPanel rpl2 = new JPanel();
		   
		   JLabel rlb1 = new JLabel("Hello Referee,"+re_name+". Choose your option :");
		   
		   JButton rb1 = new JButton("Show Schedule By Date");
		   JButton rb2 = new JButton("All Schedule");
		   JButton rb3 = new JButton("Back");
		   
		   // LABEL ----------------------------------------------------------------------
		   rpl1.add(rlb1);
		   
		   // SEARCHING SCHEDULE BUTTON --------------------------------------------------
		   rpl2.add(rb1);
		   rb1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				 
				   Arxh app11 = new Arxh();
				   s_date = rtx1.getText();
				   search_schedule(s_date);
				   app11.final_panel_1arg(schedlist, "t", rframe);
			   }
			   
		   });
		   rpl2.add(rtx1);
		   // FULL SCHEDULE BUTTON -------------------------------------------------------
		   rpl2.add(rb2);
		   rb2.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				   
				   Arxh app10 = new Arxh();
				   sh_schedule();
				   app10.final_panel_1arg(schedlist, "t", rframe);
				   
			   }
			   
		   });
		   // BACK BUTTON ----------------------------------------------------------------
		   rpl2.add(rb3);
		   rb3.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e){
				   
				   rframe.setVisible(false);
				   rframe.dispose();
				   
				   setVisible(true);
				   setLocation(600,150);
				   setSize(800,800);
				   
			   }
			   
		   });
		   
		   rframe.add(rpl1, BorderLayout.NORTH);
		   
		   GridLayout rgl = new GridLayout(2,2,8,8);
		   rpl2.setLayout(rgl);
		   
		   rframe.add(rpl2);
		
	}
	
	private void find_name(int x){
		   
		   
	     Connection conn = null;
	   	 Statement stmt = null;
	   
	try {
			Class.forName(JDBC_DRIVER);
			//System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt = conn.createStatement();
			String sql1;
			sql1 = "SELECT ref_name FROM referres WHERE id_ref = "+x+";";
			ResultSet rs = stmt.executeQuery(sql1);  
			   
			while(rs.next()){
			     re_name = rs.getString("ref_name");
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
	
	private void sh_schedule(){
		
			 schedlist.clear(); 
		     Connection conn = null;
		   	 Statement stmt = null;
		   
		try {
				Class.forName(JDBC_DRIVER);
			    conn = DriverManager.getConnection(DB_URL,USER,PASS);
			    
			    stmt = conn.createStatement();
				String sql1;
				sql1 = "SELECT ref_name, roloi, time_m, date_m, sides, f_name, city FROM referres INNER JOIN role ON id_ref = code_ref INNER JOIN matchday ON match_day = id_match INNER JOIN fields ON stadium = id_fields  WHERE ref_name = '"+re_name+"';";
				ResultSet rs = stmt.executeQuery(sql1);  
				
				//System.out.println("REF_NAME\t ROLOI\t TIME\t DATE\t SIDES\t FNAME\t CITY\t");
				while(rs.next()){
				    
					 String str = null;
					 String r_name = rs.getString("ref_name");
				     String ro = rs.getString("roloi");
				     String time = rs.getString("time_m");
				     String date = rs.getString("date_m");
				     String sides = rs.getString("sides");
				     String f_name = rs.getString("f_name");
				     String city = rs.getString("city");
				     
				     str = r_name+"\t     "+ro+"\t     "+time+"\t     "+date+"\t     "+sides+"\t     "+f_name+"\t     "+city+"\t";
				     schedlist.add(str);
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
	
	private void search_schedule(String y){
		
		 schedlist.clear();
		 Connection conn = null;
	   	 Statement stmt = null;
	   
	try {
			Class.forName(JDBC_DRIVER);
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt = conn.createStatement();
			String sql1;
			sql1 = "SELECT ref_name, roloi, time_m, date_m, sides, f_name, city FROM referres INNER JOIN role ON id_ref = code_ref INNER JOIN matchday ON match_day = id_match INNER JOIN fields ON stadium = id_fields  WHERE ref_name = '"+re_name+"' AND date_m >= '"+y+"' ;";
			ResultSet rs = stmt.executeQuery(sql1);  
			
			while(rs.next()){
			     
				 String str = null;
				 String r_name = rs.getString("ref_name");
			     String ro = rs.getString("roloi");
			     String time = rs.getString("time_m");
			     String date = rs.getString("date_m");
			     String sides = rs.getString("sides");
			     String f_name = rs.getString("f_name");
			     String city = rs.getString("city");
			     
			     str = r_name+"\t     "+ro+"\t     "+time+"\t     "+date+"\t     "+sides+"\t     "+f_name+"\t     "+city+"\t";
			     schedlist.add(str);
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
	
}
