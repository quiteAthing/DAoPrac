package petts;

public class petDAO {
	String[] info;
	public petDAO(String...infos){
		info=infos;
	}
	
	public void  getDataInside(){
		for(int t=0;t<info.length;t++){
			System.out.println(info[t]);
			}
		}

}
