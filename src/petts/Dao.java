package petts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class Dao {
	Connection c;
	public Dao(Connection dbConnection){
		c=dbConnection;
	}

	public void create(){
		String SqlC=	getSqlCommand("sqls/create.sql");
		if(SqlC.equals("failed")){return;}
		java.sql.PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(SqlC);
			System.out.println(SqlC);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed on create");
		}

	}
	public void remove(){}
	public void delete(){}
	public void findByPrimaryKey(){}
	public void update(){}
	public List<Dao> findAll(){return null;}
	private String getSqlCommand(String fileName){
		String sqlCommand="";
		File f= new File(fileName);
		try(
				FileReader fis=new FileReader(f);
				BufferedReader bfr =new BufferedReader(fis,256);
				)
		{
			
			while(true)
			{
				String in=bfr.readLine();
				if(in==null){
					break;
				}else{
					sqlCommand+=in;
				}
				
			}
				
				
				
			}catch(IOException ioe){
					ioe.printStackTrace();	
					}
			
		
		return sqlCommand;
	} 
	
}
