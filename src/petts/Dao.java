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
		
		//從資料夾中的sql檔取得指令字串
		String cmd = getSqlCommand("sqls/insert.sql");

		//用讀入的datas物件中的值替換掉指定字串
		for (String key : datas.keySet()) {
			cmd = cmd.replace(key, datas.get(key));
		}
		// 取得prepared statement
		try {
			java.sql.PreparedStatement ipstm = c.prepareStatement(cmd);
			
			//如有blob在此處設定，這邊偷懶，沒傳的話會出錯。
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
			//從資料夾中的sql檔取得指令字串
			java.sql.PreparedStatement pstmt=c.prepareStatement(getSqlCommand("sqls/delete.sql"));
			//因為delete.sql只有一個問號，且固定為int 格式，所以只用setInt
			pstmt.setInt(1, key);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public ResultSet findByPrimaryKey(int key) {
		try {
			//從資料夾中取得指令字串
			java.sql.PreparedStatement pstmt = c.prepareStatement(getSqlCommand("sqls/searchByKey.sql"));
			//因為這邊只允許用ID照，所以跟delete一樣，只用setInt()解決
			pstmt.setInt(1, key);
			return pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void update(Map<String, String> datas, File... blobs) {
		//取得sql指令字串
		String cmd = getSqlCommand("sqls/update.sql");
		
		//將指令字串中的部分內容用datas中的值替代
		for (String key : datas.keySet()) {
			cmd = cmd.replace(key, datas.get(key));
		}
		// 取得prepared statement
		try {
			java.sql.PreparedStatement ipstm = c.prepareStatement(cmd);
			//如有blob，在此處設定，檔案由外部傳入。
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
		//事先把columnName寫起來
		String[] columnNames={"petName" ,"masterName" ,	"birthday" ,"price" ,
				"weight",	"filename"};
		List<petDAO> pets=new ArrayList<>();
		try {
			//用取得的sql指令字串產生PreparedStatement物件
			java.sql.PreparedStatement pstmt=c.prepareStatement( getSqlCommand("sqls/findAllByKey.sql"));
			//取得result set
			ResultSet rs =pstmt.executeQuery();
			//將cursor設定到第一個位置
				rs.first();
				while(true){
					
					String[] data =new String[6];
					//提取資料，並將其寫入到String[] data中
					for(int i =0;i<columnNames.length;i++){
						data[i]=rs.getString(columnNames[i]);
					}
					//以data生成新的petDAO物件並加入到集合
					pets.add(new petDAO(data));
					//檢查是否為最後一個，如果是，結束迴圈，如果不是，移到下一個。
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
		//從檔案中讀取字串
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
