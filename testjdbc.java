package wapp5;
import java.io.BufferedReader;
import java.util.Collections;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class testjdbc {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
		"jdbc:mysql://localhost:3307/Whatsapp?verifyServerCertificate=false&useSSL=false","root","");
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from wapp1"); 
		ArrayList<msg> arr=new ArrayList<msg>();
		int i=0;
		while(rs.next()){
			msg temp = new msg();
			temp.n=rs.getString(1);
			temp.t=rs.getTime(2);
			temp.m=rs.getString(3);
			arr.add(i,temp);
			i++;
		}
		List<msg2> a = ctrank(arr);
		Iterator aItr = a.iterator();
		msg2 tmp = new msg2();
		while(aItr.hasNext()){
			tmp = (msg2) aItr.next();
			System.out.println("SentBy:"+tmp.n+"\nNo.of Msgs:"+tmp.c+"\nRank:"+tmp.r);
		}
	/*	System.out.println("The chat with max maximum msgs is ");*/
		/*stmt=con.createStatement();  
		rs=stmt.executeQuery("SELECT SentBy,COUNT(SentBy) AS Maximum FROM wapp1 WHERE SentBy IS NOT NULL GROUP BY SentBy ORDER BY Maximum DESC;"); 
		while(rs.next()){
			System.out.println(rs.getString(0)+rs.getInt(1));
		}*/
		con.close();
		
	}
	public static List ctrank(ArrayList <msg> arr) throws IOException, ParseException{
		List<msg2> l = new ArrayList<msg2>();
		int count=0,flag=0;
		msg tmp1 = new msg();
		msg tmp2 = new msg();
		msg2 tmp3 = new msg2();
		
		Iterator aItr = arr.iterator();
		while(aItr.hasNext()){
			count = 0;
			msg2 temp = new msg2();
		    tmp1 = (msg) aItr.next();
		 	 Iterator bItr = arr.listIterator(); 
		     while (bItr.hasNext()){
		 	   	 tmp2 = (msg) bItr.next();
		    	// System.out.println(tmp.n);
		    	 if(tmp1.n.equals(tmp2.n))
						count++;
		    	// System.out.println(count);  
		     }
		    temp.n=tmp1.n;
		    temp.t=tmp1.t;
		    temp.m=tmp1.m;
			temp.c=count;
			if(l.isEmpty())
				l.add(temp);
				else{
				flag=0;
				if(l.size()==1&&l.get(0).n.equals(temp.n))
					flag=1;
					//System.out.println("*");}
				else if(l.size()>1){
				//System.out.println(l.size());
				Iterator cItr = l.iterator();
				while(cItr.hasNext()){//b++;
					tmp3=(msg2)cItr.next();
					//System.out.println(temp.n);
					if(temp.n.equals(tmp3.n))
						flag=1;
				}
				}
				if(flag==0){
					//System.out.println(temp.n);
					l.add(temp);}
				}
			}
		Collections.sort(l, new Comparator<msg2>() {
		    public int compare(msg2 a, msg2 b) {
		       return a.c > b.c ? 1 : a.c < b.c ? -1 : 0;
		    }
		});
		int i=1;
			aItr = l.iterator();
			while(aItr.hasNext()){
				tmp3 = (msg2) aItr.next();
				tmp3.r=i++;
			}
		
		return l;
	}
	
}
