package petts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.*;

public class MainPet {

	public static void main(String[] args) {
		Dao accessor = new Dao(connect());

		/*
		 * 新增資料的標準動作，注意字串資料要跳脫，不然遇到空白會出錯， 日期格式必須符合SQL字串能讀取的格式 Map<String,String>
		 * data=new HashMap(); data.put("petName?"," \"  Captain \" ");
		 * data.put("masterName?"," \" Antonio Napkins \" ");
		 * data.put("birthday?"," 20050901 "); data.put("price?"," 1999999 ");
		 * data.put("weight?"," 5.2 ");
		 * data.put("filename?"," \"  pet (3).jpg\" "); File pic=new
		 * File("D:\\daoResource\\dao (3).jpg"); File comment=new
		 * File("D:\\daoResource\\pet (3).txt");
		 * accessor.insert(data,pic,comment);
		 */

		/*
		 * 修改的標準動作，但是因為模板的關係無法 接受未被指定的字串，需要再研究做法 可以用Regex濾除未指定的字串
		 * Map<String,String> data=new HashMap();
		 * data.put("petName?"," \"  Captain \" ");
		 * data.put("masterName?"," \" Antonio Napkins \" ");
		 * data.put("birthday?"," 20050901 "); data.put("id?","1");
		 * data.put("price?"," 15003 "); data.put("weight?"," 0.98 ");
		 * data.put("filename?"," \"  pet (4).jpg\" "); File pic=new
		 * File("D:\\daoResource\\dao (4).jpg"); File comment=new
		 * File("D:\\daoResource\\pet (4).txt");
		 * accessor.update(data,pic,comment);
		 */

		/*
		 * 刪除的標準做法(這隻程式only) 這邊偷懶只允許用id刪除。基本上只要傳id即可。 連模板也跟其他不一樣。
		 * accessor.delete(2);
		 */
		
		/*
		 * 查詢的標準做法，這邊只實作用PrimaryKey來找
		 * 
		ResultSet rs = accessor.findByPrimaryKey(1);
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			
			rs.first();
			System.out.println(rs.getString(2));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 */
	}

	public static Connection connect() {
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/course?useSSL=true&useUnicode=true",
					"root", "MyNewPass");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
