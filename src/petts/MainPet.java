package petts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPet {

	public static void main(String[] args) {
		//取得連線，並用這個連線建立Dao物件，接下來只需要Dao呼叫相關功能即可
		Dao accessor = new Dao(connect());
		String[] columnNames={"petName" ,"masterName" ,	"birthday" ,"price" ,
				"weight",	"filename"};
		
//		 本程式新增內容的標準做法
//		  Map<String,String> data=new HashMap();
//		  data.put("petName?"," \"  黑白切 \" ");
//		  data.put("masterName?"," \" 滷味 \" ");
//		  data.put("birthday?"," 19450622 ");
//		  data.put("price?"," 399999512 ");
//		  data.put("weight?"," 359.2 ");
//		  data.put("filename?"," \"  pet (2).jpg\" "); File pic=new
//		  File("D:\\daoResource\\dao (2).jpg"); File comment=new
//		  File("D:\\daoResource\\pet (2).txt");
//		  accessor.insert(data,pic,comment);
		 

		
		
//		  修改的標準動作，但是因為模板的關係無法 接受未被指定的字串，需要再研究做法 可以用Regex濾除未指定的字串
//		  Map<String,String> data=new HashMap();
//		  data.put("petName?"," \"  Captain \" ");
//		  data.put("masterName?"," \" Antonio Napkins \" ");
//		  data.put("birthday?"," 20050901 "); data.put("id?","1");
//		  data.put("price?"," 15003 "); data.put("weight?"," 0.98 ");
//		  data.put("filename?"," \"  pet (4).jpg\" "); File pic=new
//		  File("D:\\daoResource\\dao (4).jpg"); File comment=new
//		  File("D:\\daoResource\\pet (4).txt");
//		  accessor.update(data,pic,comment);
		 

//		
//		 刪除的標準做法(這隻程式only) 這邊偷懶只允許用id刪除。基本上只要傳id即可。 連模板也跟其他不一樣。
//		 accessor.delete(7);
//		 accessor.delete(8);
		
		
		
//		 這個程式裡查詢資料的標準做法
		
//		ResultSet rs = accessor.findByPrimaryKey(1);
//
//			try {
//				rs.first();
//				while(true){
//					for(int i =0;i<columnNames.length;i++){
//						System.out.println(rs.getString("petname"));
//						System.out.println(rs.getString("mastername"));
//						System.out.println(rs.getString("birthday"));
//						System.out.println(rs.getString("price"));
//						System.out.println(rs.getString("weight"));
//						System.out.println(rs.getString("filename"));
//					}
//					if(rs.isLast()){break;}
//				
//					
//				}
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		
		
		
//		  取得包含DAO的集合，並將內容印出到主控台
//		  
//		List<petDAO> petlist= accessor.findAll();
//		for(petDAO pd : petlist){
//			pd.getDataInside();
//		}
		

		
	}

	public static Connection connect() {
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/course?useSSL=true&useUnicode=true&characterEncoding=UTF-8",
					"root", "MyNewPass");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
