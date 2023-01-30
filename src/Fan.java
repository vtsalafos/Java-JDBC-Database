import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Fan extends Arxh{

private static final long serialVersionUID = 1L;
private static ArrayList <String> arrangelist = new ArrayList<String>();
private static ArrayList <String> previousgamlist = new ArrayList<String>();
private static ArrayList <String> difflist = new ArrayList<String>();
private static ArrayList <String> capacitylist = new ArrayList<String>();
private static ArrayList <String> offerlist = new ArrayList<String>();



private static String na_fan=null;
private static String supp_team=null;
private static String ticket_category=null;
private static int his_id=0;
private static int gd1;



final JFrame fframe = new JFrame();

public void find_name(int x){
	   
	   
    Connection conn = null;
  	 Statement stmt = null;
  
try {
		Class.forName(JDBC_DRIVER);
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	    stmt = conn.createStatement();
		String sql1;
		sql1 = "SELECT name_fan, id_fan, supporting_team, t_category FROM fans INNER JOIN tickets ON id_fan = kodikos_fan WHERE id_fan = "+x+";";
		ResultSet rs = stmt.executeQuery(sql1);  
		   
		while(rs.next()){
		     na_fan = rs.getString("name_fan");
		     supp_team = rs.getString("supporting_team");
		     his_id = rs.getInt("id_fan");
		     ticket_category = rs.getString("t_category");
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

public void create_fan_frame(){
	
	
		   find_name(ID_FAN);
	
		   
		   fframe.setSize(700, 700);
		   fframe.setLocation(600, 150);
		   fframe.setTitle("LALIGA project - Fan");
		   fframe.setVisible(true);
		   fframe.addWindowListener(new Close_Window());
		   
		   JPanel cpl1 = new JPanel();
		   JPanel cpl2 = new JPanel();
		   JPanel cpl3 = new JPanel();
	 
		   JLabel clb1 = new JLabel("Hello mister "+na_fan+" with id "+his_id+". Choose an option :");
		
		   JButton cb1 = new JButton("Previous Games");
		   JButton cb2 = new JButton("Arranged games");
		   JButton cb3 = new JButton("Goals of your team and your favourite player");
		   JButton cb4 = new JButton("Available seats");
		   JButton cb5 = new JButton("Matches Watched and Offers");
		   JButton cb6 = new JButton("Back");
	 	   
		   
		// LABEL ----------------------------------------------------------------------
		   cpl1.add(clb1);
		   
		// GAMES PLAYED OF YOUR TEAM BUTTON ----------------------------------------------------
		   cpl2.add(cb1);
		   cb1.addActionListener(new ActionListener(){
			   
			   public void actionPerformed(ActionEvent e) {
			   
				   match_info_prev(supp_team);
			       final_panel_1arg(previousgamlist, "t", fframe);
			   
			   }
				   
		   });
		   
		 	
		 // ARRANGED GAMES OF YOUR TEAM BUTTON ----------------------------------------------------------
			cpl2.add(cb2);
			cb2.addActionListener(new ActionListener(){
	   
				public void actionPerformed(ActionEvent e){
		   
					match_info_next(supp_team);
					final_panel_1arg(arrangelist, "t", fframe);
	            }
	   
            });
			
		// GOALS OF TEAM AND PLAYER BUTTON ----------------------------------------------------------
		   cpl2.add(cb3);
		   cb3.addActionListener(new ActionListener(){
	   
				public void actionPerformed(ActionEvent e){
		   	
					team_favplayer_goals(supp_team);
					final_panel_1arg(difflist, "v", fframe);

	            }
	   
	        });
			
		// AVAILABLE SEATS BUTTON ------------------------------------------------------------
		   cpl2.add(cb4);
		   cb4.addActionListener(new ActionListener(){
	   
				public void actionPerformed(ActionEvent e){
		   
					avail_seats(supp_team);
					final_panel_1arg(capacitylist, "v", fframe);
	            
				}
	   
           });
		   
		// MATCHES WATCHED AND OFFERS BUTTON ------------------------------------------------------------
				cpl2.add(cb5);
				cb5.addActionListener(new ActionListener(){
				   
				public void actionPerformed(ActionEvent e){
					mwatched_offers(supp_team);   
					final_panel_1arg(offerlist, "v", fframe);
				}				   
           
			});

			
			// BACK BUTTON ----------------------------------------------------------------
			   cpl2.add(cb6);
			   cb6.addActionListener(new ActionListener(){
				   
				   public void actionPerformed(ActionEvent e){
					   
					   fframe.setVisible(false);
					   fframe.dispose();
					   
					   setVisible(true);
					   setLocation(600,150);
					   setSize(800,800);
					   
				   }
				   
			   });
			   
			   GridLayout gl = new GridLayout(3, 2, 8, 8);
			   cpl2.setLayout(gl);
			   
			   fframe.add(cpl1,BorderLayout.NORTH);
			   fframe.add(cpl2);
			   
			   Load_image(supp_team, fframe, cpl3);
}
					   
			   

	
private void match_info_prev(String y){	  
	
	   previousgamlist.clear();
	   Connection conn1 = null;
	   Statement stmt1 = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		    
			stmt1 = conn1.createStatement();
			String sql1;
			sql1 = "SELECT sides, date_m, score_m, briefing_m FROM matchday WHERE date_m <= '2017-03-17' AND sides LIKE '%"+y+"%';";
			ResultSet rs = stmt1.executeQuery(sql1); 
	
			
			while(rs.next()){
				String str = null;
				String date = rs.getString("date_m");
				String score = rs.getString("score_m");
				String brief = rs.getString("briefing_m");
				String side = rs.getString("sides");
				
				str = date+"\t     "+score+"\t     "+brief+"\t     "+side;
			    previousgamlist.add(str);

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



private void match_info_next(String y){
	   
	   arrangelist.clear();
	   Connection conn1 = null;
	   Statement stmt1 = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt1 = conn1.createStatement();
			String sql1;
			sql1 = "SELECT time_m, date_m, f_name, ref_name, sides FROM matchday INNER JOIN fields ON stadium = id_fields INNER JOIN referres ON match_day = id_match WHERE date_m > '2017-03-17' AND sides LIKE '%"+y+"%';";
			ResultSet rs = stmt1.executeQuery(sql1);  
			   
			int u = 1;
			String str1 = "";
			
			while(rs.next()){
				
				String str = null;
				String time = null;
				String date = null;
				String field = null;
				String side = null;
				String ref = null;
				
				ref = rs.getString("ref_name");
				str1 =  str1+ref+" , ";
				
				if(u%5==0){
				 
				 date = rs.getString("date_m");
				 time = rs.getString("time_m");
				 field = rs.getString("f_name");
				 side = rs.getString("sides");
				 str = date+"\t     "+time+"\t     "+field+"\t     "+str1+"\t     "+side;
				 arrangelist.add(str);
                 str1 = "";
				}
			    
				u++;
				
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



private void team_favplayer_goals(String y){
	   
	    difflist.clear();
		String paiktis=null;
		String str = null;
		Connection conn1 = null;
		Statement stmt1 = null;
		
		try {//try gia GOAL DIFFERENCE 
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
			
		    stmt1 = conn1.createStatement();
			String sql1;
			sql1 = "SELECT team_goal_A, team_goal_D, favorite_player FROM fans INNER JOIN favorite_pl ON code_fan = id_fan INNER JOIN omada ON supporting_team = team_name WHERE "+ID_FAN+" = code_fan";  
			ResultSet rs = stmt1.executeQuery(sql1);  
			   
			str="GOAL DIFFERENCE OF " +y+ " IS : ";
			while(rs.next()){
				
				
				String uper = rs.getString("team_goal_A");
				String kata = rs.getString("team_goal_D");
				int up=Integer.parseInt(uper);
				int kat=Integer.parseInt(kata);
				gd1 = up-kat;
				String gd =Integer.toString(gd1);
				paiktis = rs.getString("favorite_player");
					
				str =str+gd;
				
				}
				
				rs.close();
				stmt1.close() ;
				conn1.close();
			
		    }catch(SQLException se){   
		    	se.printStackTrace();
		    }catch(Exception h){
		        h.printStackTrace();
	     }finally{
		      try{
		         if(stmt1!=null)
		            stmt1.close();
		      }catch(SQLException se2){
		      }
		      try{
		         if(conn1!=null)
		            conn1.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	     }//end try
		
		
		
		
		try {//try gia FAVOURITE PLAYER
			Class.forName(JDBC_DRIVER);
			conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt1 = conn1.createStatement();
			String sql1;
			
			sql1 = "SELECT goal_scored_p FROM podosfairistes WHERE name_player = '"+paiktis+"'";  
			ResultSet rs = stmt1.executeQuery(sql1);  
			   
			str = str +"      GOALS OF YOUR FAVOURITE PLAYER ARE : " ;
			while(rs.next()){
				
				
				String goals = rs.getString("goal_scored_p");
				
				str =str+ goals +"   ---->   "+ paiktis;
				
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

       difflist.add(str);

}



private void avail_seats(String y) {

capacitylist.clear();
String str = null;	
Connection conn1 = null;
Statement stmt1 = null;
String diathesimotita = null;	
	if (ticket_category.equals("DIARKEIAS")) {
		int plithos1;
		try {
		Class.forName(JDBC_DRIVER);
		conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	    stmt1 = conn1.createStatement();
		String sql1;
		sql1 = "SELECT capacity, f_name, date_m FROM fields INNER JOIN matchday ON stadium = id_fields WHERE date_m = '2017-03-24' AND sides LIKE '%"+y+"%';";
		ResultSet rs = stmt1.executeQuery(sql1);  
		   
		str = "Next match is going to be played at ";
		while(rs.next()){
			
			String stadio = rs.getString("f_name");
			diathesimotita = rs.getString("capacity");
			
			
			
			str = str +stadio;
			
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
	
	Connection conn = null;
	Statement stmt = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt = conn.createStatement();
			String sql1;
			sql1 = "SELECT count(*) FROM tickets INNER JOIN fans ON id_fan = kodikos_fan WHERE supporting_team = '"+y+"' GROUP BY t_category HAVING t_category = 'DIARKEIAS'";
			ResultSet rs = stmt.executeQuery(sql1);  
			   
			str = str +" and the available seats are  ";
			while(rs.next()){
				
				String plithos = rs.getString("count(*)");
				int pl = Integer.parseInt(plithos);
				int diathes = Integer.parseInt(diathesimotita);
				plithos1 = diathes - pl;
				
				
				str = str +plithos1;
				
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
      }//end if
	else {
		str = "You don't have a season ticket";
		 }

	capacitylist.add(str);
	
}



private void mwatched_offers(String y) {

offerlist.clear();
String message1 = null;
String message2 = null;
Connection conn1 = null;
Statement stmt1 = null;
int aplaticket = 0;
int i = 1;
	

if (ticket_category.equals("APLO")) {
	try {
		Class.forName(JDBC_DRIVER);
		conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	    stmt1 = conn1.createStatement();
		String sql1;
		sql1 = "SELECT count(*) FROM tickets INNER JOIN fans ON id_fan = kodikos_fan WHERE supporting_team = '"+supp_team+"' AND id_fan = '"+his_id+"' GROUP BY t_category HAVING t_category = 'APLO'";
		ResultSet rs = stmt1.executeQuery(sql1);  
		Random rand = new Random();

		
		
		while(rs.next()){
			
			
			String apla = rs.getString("count(*)");
			aplaticket = Integer.parseInt(apla);
			message1="You have watched " +aplaticket+ " games.";
			if(aplaticket>=6) {
				message1 = message1 +"  You have an offer to buy a season ticket because you have watched more than half games of your favorite team!!!";
			}
			else {
				message1 = message1 +"  You don't have an offer to buy a season ticket";
			}
			}
		String str5 = "";
		for(i=1; i<=aplaticket; i++) {
		int  n = rand.nextInt(11) + 1;
		str5 =  str5+n+" ";
		
		}
		message2 ="The games you have watched are: "+str5;
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
       offerlist.add(message1);
       offerlist.add(message2);
}
	else {
		message1 = "You have an offer to renew your season ticket because you already have one\n";
		offerlist.add(message1);
	}
	
}









}