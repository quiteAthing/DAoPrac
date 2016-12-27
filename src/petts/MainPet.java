package petts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.*;
public class MainPet {
		
		
		public static void main(String[] args){
			Dao accessor=new Dao(connect());
			
			
		}
				
		
		public static Connection connect(){
			try{
			Connection  c= DriverManager.getConnection("jdbc:mysql://127.0.0.1/course?useSSL=true&useUnicode=true","root","mysqlFRS");
				return c;
			}catch( SQLException e){
				e.printStackTrace();
				return null;	
			}
			
			
			
		}

}
