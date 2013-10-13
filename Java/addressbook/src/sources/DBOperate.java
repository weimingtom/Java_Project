package sources;



import java.util.*;
import java.sql.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class DBOperate
{
	//�����������ַ���
	private static String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	//�������ݿ������ַ���
	private static String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=DB\\LinkMan.ab";
	//�������ݿ����Ӷ�������
	private static Connection con=null;
	//��������������
	private static Statement stat=null;
	//����Ԥ��������������
	private static PreparedStatement psInsert=null;
	//�����������������
	private static ResultSet rs=null;

//	***************** �����͹ر����ݿ����ӷ���*****************
	
	//�õ����ݿ�����(�������Ӷ���)�ķ���
	private static Connection getConnection()
	{
		try
		{
			//������������
			Class.forName(driver);
			//�õ����ݿ�����(��������)
			con=DriverManager.getConnection(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//�������Ӷ���
		return con;
	}
	
	//�ر����ݿ����ӵķ���
	public static void closeCon()
	{
		try
		{
			if(rs!=null)
			{
				//����������Ϊ�գ���رս��������ֵnull
				rs.close();
				rs=null;
			}
			if(stat!=null)
			{
				//���������Ϊ�գ���ر������󲢸�ֵnull
				stat.close();
				stat=null;
			}
			if(con!=null)
			{
				//������Ӳ�Ϊ�գ���ر����Ӳ���ֵnull
				con.close();
				con=null;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
//******************** LoginFrame���õ��ķ��� *****************
	
	//�ж��û���¼����ķ���
	public static boolean check(String user,String pwd)
	{
		//���巵�ز������ͱ���
		boolean flag=false;
		//���Բ����쳣
		try
		{
			//�õ����ݿ⣨����������
			con=DBOperate.getConnection();
			//����SQL������
			stat=con.createStatement();
			//ִ�в�ѯ���õ������
			rs=stat.executeQuery("SELECT Password FROM User WHERE UserName='"+user+"'");
			
			//������α�����һλ
			rs.next();
			//��ѯ���ص����븳ֵ������
			String spwd=rs.getString(1);   //�����1��ָ�ֶδ�0����1�ʹ���ڶ����ֶ�
			//�ж������Ƿ���ȷ
			if(spwd.equals(pwd))
			{
				//����������ȷ
				flag=true;
			}
			else
			{
				//�����������
				flag=false;
			}
		}
		catch(Exception e)
		{
			//��ӡ����쳣��Ϣ
			e.printStackTrace();
			//������κ��쳣�������򶼷���false
			flag=false;
		}
		finally
		{
			//�ر����ݿ�����
			DBOperate.closeCon();
		}
		//�������ղ�ѯ���
		return flag;
	}
	
	//����ע���û��ж��Ƿ��Ѿ������û�
	public static boolean isExist(String sql)
	{
		//���巵�ز������ͱ���
		boolean flag=false;
		//���Բ����쳣
		try
		{
			//�õ����ݿ⣨����������
			con=DBOperate.getConnection();
			//����SQL������
			stat=con.createStatement();
			//ִ�в�ѯ���õ������
			rs=stat.executeQuery(sql);
			
			//������ڣ��򷵻�false
			if(rs.next())
			{
				flag=true;
			}
		}
		catch(Exception e)
		{
			//��ӡ����쳣��Ϣ
			e.printStackTrace();
			//������κ��쳣�������򶼷���false
			flag=false;
		}
		finally
		{
			//�ر����ݿ�����
			DBOperate.closeCon();
		}
		//�������ղ�ѯ���
		return flag;
	}
	
	//ִ��ע��ķ���
	public static int update(String sql)
	{ 
		//�����ѯ�����������ļ�¼��������
		int count=0;
		//���Բ����쳣
		try
		{
			//�õ����ݿ⣨����������
			con=DBOperate.getConnection();
			//����SQL������
			stat=con.createStatement();
			//ִ�в�ѯ���õ������
			count=stat.executeUpdate(sql);
		}
		catch(Exception e)
		{
			//��ӡ����쳣��Ϣ
			e.printStackTrace();
			//ע��ʧ�ܣ�����-1
			count=-1;
		}
		finally
		{
			//�ر����ݿ�����
			DBOperate.closeCon();
		}
		//�������ղ�ѯ���
		return count;
	}

	
//	****************** MainFrame���õ��ķ��� ******************

	//���������õ��ڵ������б���
	public static Vector<String> getNode(String user,String condition)
	{
		//���ݵ�½�û��������õ��ڵ�����
		Vector<String> node=new Vector<String>();
		//���������������ʽ
		String patternStr=";";
		//��������condition�ַ������в��
		String[] scon=condition.split(patternStr);
		try
		{
			//�õ����ݿ�����
			con=getConnection();
			//����������
			stat=con.createStatement();
			if(scon.length==1&&scon[0].equals("uid"))
			{
				//�õ���ǰ�û����ж��ٸ�����
				rs=stat.executeQuery("SELECT DISTINCT pgroup FROM ContactInfo WHERE UserName='"+user.trim()+"'");
			}
			else if(scon.length==1)
			{
				//�õ���ǰ��ϵ�����������Ƭ���б�
				rs=stat.executeQuery("SELECT photoname FROM Photo WHERE pid = "+
				"(SELECT pid FROM ContactInfo WHERE UserName='"+user.trim()+"'AND pname='"+scon[0].trim()+"')");
			}
			else if(scon.length==2)
			{
				//�õ����������ϵ�������б� 			
				rs=stat.executeQuery("SELECT pname FROM ContactInfo WHERE UserName='"
									 +user.trim()+"'AND pgroup='"+scon[1].trim()+"'");
			}
			while(rs.next())
			{
				//��֯��Vector����
			    String s=rs.getString(1);
			    node.add(s);
			} 				
		}
		catch(Exception e)
		{
			//��ӡ�쳣��Ϣ
			e.printStackTrace();
		}
		finally
		{
			//�ر����ݿ�����
			DBOperate.closeCon();
		}
		//���ؽ���б�
		return node;
	}

	public static int delUser(String UserName)//ɾ���û�
	{
		int count=0;//���÷���ֵ
		Vector<String> vpid=new Vector<String>();//���pid�ļ��� һ���û���Ӧ�����ϵ��
		try
		{
			con=DBOperate.getConnection();//�õ����ݿ�����
			stat=con.createStatement();//����������
			rs=stat.executeQuery("SELECT pid FROM ContactInfo WHERE UserName='"+UserName+"'");//�õ�ÿ����ϵ�˵�ID
			while(rs.next())
			{
				String pid=rs.getString(1);//�õ��û��µ�pid				
				vpid.add(pid);//��ӽ���ϵ�˼���
			}
			stat=con.createStatement();//���´���������
			for(String s:vpid)
			{//ѭ��ɾ��ÿ����ϵ�˵����
				stat.executeUpdate("DELETE FROM photo WHERE pid='"+s+"'");
			}
			//����ϵ��ContactInfo����ɾ��ÿ����ϵ��			
			count=stat.executeUpdate("DELETE FROM ContactInfo WHERE UserName='"+UserName+"'");
			//���û�����ɾ���û�
			stat.executeUpdate("DELETE FROM user WHERE UserName='"+UserName+"'");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{DBOperate.closeCon();}//�ر����ݿ�����
		return count;//����ɾ���˶��ٸ���ϵ��
	}	
	

//	********************����**************************
		public static int delGroup(String user,String group)
		{
			int count=0;		
			Vector<String> vpid=new Vector<String>();//һ�������Ӧ�����ϵ��
			try
			{
				con=getConnection();//�õ����ݿ�����
				stat=con.createStatement();//����������
				rs=stat.executeQuery("SELECT pid FROM ContactInfo WHERE pgroup='"+group+"'"
										+"AND UserName='"+user+"'");//�����ݿ���������group�����pid
				while(rs.next())
				{
					String pid=rs.getString(1);//�õ��û��µ�pidѭ��ɾ��photo����pid�µ���Ƭ			
					vpid.add(pid);//��Ӹ÷�������ϵ�����Ƶ�����
				}
				stat=con.createStatement();//����������
				for(String s:vpid)
				{//ѭ��ɾ��ÿ����ϵ�˵����
					stat.executeUpdate("DELETE FROM photo WHERE pid='"+s+"'");
				}
				//����ϵ��ContactInfo����ɾ��ÿ����ϵ��
				count=stat.executeUpdate("DELETE FROM ContactInfo WHERE pgroup='"+group+"'");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return count;//����ɾ����ϵ����Ŀ
		}
//	*******************��ϵ��***********************
		public static String insertPerson(String UserName,Vector<String> pInfo)
		{
			String isPathNull="isNotNull";//��������ͼ���ǲ��ǺϷ���Ĭ�ϲ�Ϊ��
			try{
				con=getConnection();//�õ����ݿ�����
				if(pInfo.get(9).equals("")||pInfo.get(9)==null)
				{//��Ƭ·��Ϊ�գ��򲻲���ͼ��
					psInsert=con.prepareStatement("INSERT into ContactInfo(pid,pname,pgender,page,pnumber,"+
		 										"pemail,pgroup,ppostalcode,padress,UserName)"+
		 										"VALUES(?,?,?,?,?,?,?,?,?,?)");
		 		}
				else
				{//��Ƭ·����Ϊ�գ������ͼ��
					psInsert=con.prepareStatement("INSERT into ContactInfo(pid,pname,pgender,page,pnumber,"+
		 								"pemail,pgroup,ppostalcode,padress,UserName,pphoto)"+
		 								"VALUES(?,?,?,?,?,?,?,?,?,?,?)" );
		 			File f=new File(pInfo.get(9));//��ȡѡȡ��ͼƬ�ļ�
		 			byte[] b=new byte[(int)f.length()];//�����洢ͼƬ���ݵ�����
		 			FileInputStream fin=new FileInputStream(f);
					fin.read(b);fin.close();//��ȡ�ļ�����byte�����в��ر�������
		 			psInsert.setBytes(11,b);//����pphoto����������
				}
				for(int i=0;i<9;i++)
				{//���ù�����Ϣ
					psInsert.setString(i+1,pInfo.get(i));
				}
				psInsert.setString(10,UserName);//�����û�			
				psInsert.execute();psInsert.close();//ִ�и��²��ر����
			}
			catch(FileNotFoundException fnfe){isPathNull="isNull";}//ͼƬ·������
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//�ر����ݿ�����
			return isPathNull;
		}
		public static String updatePerson(String UserName,Vector<String> pInfo)
{
			String isPathNull="isNotNull";//��������path�ǲ��ǺϷ�
			try{
				con=getConnection();
				if(pInfo.get(9).equals("")||pInfo.get(9)==null)
				{//����ʱ�������Ƭ·��Ϊ�գ��򲻸���ͼ��
psInsert=con.prepareStatement("update ContactInfo set pname=?,pgender=?,page=?,pnumber=?,"+
"pemail=?,pgroup=?,ppostalcode=?,padress=?,UserName=? WHERE pid='"+pInfo.get(0).trim()+"'");
				}
				else
				{//�����Ƭ·����Ϊ�գ������ͼ��
psInsert=con.prepareStatement("update ContactInfo set pname=?,pgender=?,page=?,pnumber=?,"+
"pemail=?,pgroup=?,ppostalcode=?,padress=?,UserName=?,pphoto=? WHERE pid='"+pInfo.get(0).trim()+"'");
					File f=new File(pInfo.get(9));//��ȡѡȡ��ͼƬ�ļ�
		 			byte[] b=new byte[(int)f.length()];//�����洢ͼƬ���ݵ�����
		 			FileInputStream fin=new FileInputStream(f);
					fin.read(b);fin.close();//��ȡ�ļ�����byte�����в��ر�������					
		 			psInsert.setBytes(10,b);	 			
				}
				for(int i=1;i<9;i++){//���ù�������Ϣ����
					psInsert.setString(i,pInfo.get(i));
				}			
				psInsert.setString(9,UserName);//�����û�	 			
				psInsert.execute();psInsert.close();//ִ�и��²��ر����
			}
			catch(FileNotFoundException fnfe){isPathNull="isNull";}//·�����Ϸ�	
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//�ر�����
			return isPathNull;
		}
		public static Vector<String> getPerInfo(String sql)//�õ���ϵ����Ϣ
		{
			Vector<String> pInfo=new Vector<String>();
			try
			{
				con=getConnection();//�õ����ݿ�����
				stat=con.createStatement();//����������
				rs=stat.executeQuery(sql);//ִ�в�ѯ 			
				while(rs.next())
				{	
					for(int i=1;i<10;i++)
					{ 	
				    	pInfo.add(rs.getString(i));//����ϵ����Ϣ��ӵ���������
					} 			    
				} 					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally{DBOperate.closeCon();}//�ر����ݿ�����
			return pInfo;//������Ϣ����
		}
		public static Image getPic(String sql)
		{
			Image i=null;//����Image��������
			try
			{
				con=getConnection();//�õ����ݿ�����
				stat=con.createStatement();//����������
				rs=stat.executeQuery(sql);//ִ��SQL���
				while(rs.next())
				{
					byte[] buff=rs.getBytes(1);//�õ�ͼ������
					if(buff!=null)//������ݴ���
					{
						i=(new ImageIcon(buff)).getImage();//ת����ImageIcon����
					} 				
				}		
			}
			catch(Exception e)
			{
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
			finally
			{//�ر����ݿ�����
				DBOperate.closeCon();
			}
			return i;
		}
//	*****************************��  Ƭ****************************
		public static int insertPic(String path,String pid)
		{//flag=0��ʾ�ϴ��ɹ� 1��ʾ�Ҳ����ļ� 2��ʾ�ļ��Ѿ�����
			int flag=0;
			File f=new File(path);//��ȡѡȡ��ͼƬ�ļ�	
			try
			{
				con=getConnection();//�õ����ݿ�����
				psInsert=con.prepareStatement("INSERT into photo VALUES(?,?,?)");
				byte[] b=new byte[(int)f.length()];//�����洢��Ƭ���ݵ�����
				FileInputStream fin=new FileInputStream(f);//
				fin.read(b);fin.close();//��ȡ�ļ�����byte�����в��ر�������			
				psInsert.setString(1,pid);//���ô���Ƭ������ϵ��
				psInsert.setString(2,f.getName());//���ô���Ƭ����
				psInsert.setBytes(3,b);//������Ƭ����
				psInsert.executeUpdate();psInsert.close();//ִ�и��²��ر����							
			}
			catch(FileNotFoundException fnfe){flag=1;}//�Ҳ�����Ƭ�ļ�
			catch(SQLException sqle){flag=2;}//�ļ��Ѿ�����
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//�ر����ݿ�����
			return flag;
		}
		
		public static void main(String[] args)
		{
			System.out.println(DBOperate.delUser("aa"));
		}
}