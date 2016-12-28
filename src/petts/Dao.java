package petts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import petts.petDAO;

public class Dao {
	Connection c;

	public Dao(Connection dbConnection) {
		c = dbConnection;
	}

	public void create() {
		String SqlC = getSqlCommand("sqls/create.sql");
		if (SqlC.equals("failed")) {
			return;
		}
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

	public void insert(Map<String, String> datas, File... blobs) {
		//blobs的順序需要由人類記憶，程式按照順序填入檔案。
		//Map物件為了方便起見在所有key後面都加上問號，以避免跟查詢字串中的欄位名稱混淆

		String cmd = getSqlCommand("sqls/insert.sql");
		for (String key : datas.keySet()) {
			cmd = cmd.replace(key, datas.get(key));
		}
		// 取得prepared statement
		try {
			java.sql.PreparedStatement ipstm = c.prepareStatement(cmd);
			
			if (blobs != null) {
				for (int len = 0; len < blobs.length; len++) {
					int qID=len+1;
					InputStream iStream = new FileInputStream(blobs[len]);
					ipstm.setBlob(qID, iStream, blobs[len].length());
				}
			}
			ipstm.executeUpdate();
		}

		catch (SQLException ioe) {
			ioe.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}


	public void delete(int key) {
		try {
			java.sql.PreparedStatement pstmt=c.prepareStatement(getSqlCommand("sqls/delete.sql"));
			pstmt.setInt(1, key);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public ResultSet findByPrimaryKey(int key) {
		try {
			java.sql.PreparedStatement pstmt = c.prepareStatement(getSqlCommand("sqls/searchByKey.sql"));
			pstmt.setInt(1, key);
			return pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void update(Map<String, String> datas, File... blobs) {
		//blobs的順序需要由人類記憶，程式按照順序填入檔案。
		//Map物件為了方便起見在所有key後面都加上問號，以避免跟查詢字串中的欄位名稱混淆

		String cmd = getSqlCommand("sqls/update.sql");
		for (String key : datas.keySet()) {
			cmd = cmd.replace(key, datas.get(key));
		}
		// 取得prepared statement
		try {
			java.sql.PreparedStatement ipstm = c.prepareStatement(cmd);
			
			if (blobs != null) {
				for (int len = 0; len < blobs.length; len++) {
					int qID=len+1;
					InputStream iStream = new FileInputStream(blobs[len]);
					ipstm.setBlob(qID, iStream, blobs[len].length());
				}
			}
			ipstm.executeUpdate();

		}

		catch (SQLException ioe) {
			ioe.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public List<petDAO> findAll() {
		String[] columnNames={"petName" ,"masterName" ,	"birthday" ,"price" ,
				"weight",	"filename"};
		List<petDAO> pets=new ArrayList<>();
		try {
			java.sql.PreparedStatement pstmt=c.prepareStatement( getSqlCommand("sqls/findAllByKey.sql"));
			ResultSet rs =pstmt.executeQuery();
				rs.first();
				while(true){
					String[] data =new String[6];
					for(int i =0;i<columnNames.length;i++){
						data[i]=rs.getString(columnNames[i]);
					}
					pets.add(new petDAO(data));
					if(rs.isLast()){break;}
					else{rs.next();}
					}
				}
		
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pets;
	}

	private String getSqlCommand(String fileName) {
		String sqlCommand = "";
		File f = new File(fileName);
		try (FileReader fis = new FileReader(f); BufferedReader bfr = new BufferedReader(fis, 256);) {

			while (true) {
				String in = bfr.readLine();
				if (in == null) {
					break;
				} else {
					sqlCommand += in;
				}

			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sqlCommand;
	}

}
