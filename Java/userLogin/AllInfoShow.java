package StudentManegeSystem.userLogin;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Robot;
import java.io.IOException;

public class AllInfoShow extends JFrame{
	private JLabel[] title={
			new JLabel("ѧ��"), new JLabel("ѧ������"),new JLabel("�Ա�"),
			new JLabel("����"),new JLabel("��ͥסַ"),new JLabel("��ϵ�绰")
	};
	private InitializeData data=new InitializeData();
	private JButton[]  bt={
			new JButton("ˢ��"),new JButton("����"),new JButton("�������˵�")
	};

	private String username;
	private static boolean infoOnShow=false;
	
	private  ArrayList<DetailedStudentInfo> dsi=new ArrayList<DetailedStudentInfo>();
	private ArrayList<DetailedStudentInfo> infoNow=new ArrayList<DetailedStudentInfo>();
	
	private  ArrayList<Persons> ps=new ArrayList<Persons>(); 
	private JPanel bottonPane=new JPanel();
	private JPanel showPanel=new JPanel();
	private JScrollPane scroll;
	private JTextField text[];
	private boolean removeOr;
	private String info123;
	private int kind;
	private void initialize(){
		dsi=(new InitializeData()).readDetailedData();
		try {
			ps=(new InitializeData()).readData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	private void removeUnrelated(){
		if(removeOr){
			
			if(kind==0){
				Iterator<DetailedStudentInfo> it=dsi.iterator();
				while(it.hasNext()){
					DetailedStudentInfo d=it.next();
					if(!d.getUserRealName().equals(info123)){
						System.out.println(d.getUserRealName()+" "+info123);
						it.remove();
					}
				}
				System.out.println(dsi);
			}
			else if(kind==1){
				Iterator<DetailedStudentInfo> it=dsi.iterator();
				while(it.hasNext()){
					DetailedStudentInfo d=it.next();
					if(!d.getStudentNumber().equals(info123)){
						it.remove();
					}
				}
			}
			else if(kind==2){
				Iterator<DetailedStudentInfo> it=dsi.iterator();
				while(it.hasNext()){
					DetailedStudentInfo d=it.next();
					if(!d.getUserName().equals(info123)){
						it.remove();
					}
				}
			}
		}
	}
	final int HEIGHT=300,LENGTH=800;	
	public AllInfoShow(final String username1,String infom,boolean removeOr,int kind){
		super("������Ϣ��ʾ");
		this.removeOr=removeOr;
		this.username=username1;
		this.info123=infom;
		System.out.println("info:"+info123+"  "+infom);
		this.kind=kind;
		this.initialize();
		this.removeUnrelated();
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(this.LENGTH,this.HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int o=JOptionPane.showConfirmDialog(null, "�Ƿ�ص����˵�?");
				if(o==JOptionPane.YES_OPTION){
					for(Persons p:ps){
						if(p.getUsername().equals(username)){
							new MainMenu(p);
							break;
						}
					}
					dispose();
				}
				else if(o==JOptionPane.NO_OPTION){
					dispose();
				}
			}
		});
		this.setResizable(false);
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	this.setLocation(p.x-this.LENGTH/2,p.y-this.HEIGHT/2);
		text=new JTextField[6*dsi.size()];
		int num=0;
		for(int i=0;i<6*dsi.size();i++){
			if(dsi.get(num).getUserName().equals(username)){
				text[i]=new JTextField(dsi.get(num).getStudentNumber());
				text[i++].setBackground(Color.red);
				text[i]=new JTextField(dsi.get(num).getUserRealName());
				text[i++].setBackground(Color.red);
				text[i]=new JTextField(dsi.get(num).getGender());
				text[i++].setBackground(Color.red);
				text[i]=new JTextField(dsi.get(num).getStudentAge());
				text[i++].setBackground(Color.red);
				text[i]=new JTextField(dsi.get(num).getLivingAdress());
				text[i++].setBackground(Color.red);
				text[i]=new JTextField(dsi.get(num++).getStudentPhoneNumber());
				text[i].setBackground(Color.red);
			}
			else{
			text[i++]=new JTextField(dsi.get(num).getStudentNumber());
			text[i++]=new JTextField(dsi.get(num).getUserRealName());
			text[i++]=new JTextField(dsi.get(num).getGender());
			text[i++]=new JTextField(dsi.get(num).getStudentAge());
			text[i++]=new JTextField(dsi.get(num).getLivingAdress());
			text[i]=new JTextField(dsi.get(num++).getStudentPhoneNumber());
			}
		}
		
		this.showPanel.setLayout(new GridLayout(dsi.size()+1,6));
		for(JLabel l:title){
			showPanel.add(l);
		}
		for(int i=0;i<6*dsi.size();i++){
			text[i].setFont(new Font("times new roman",Font.ITALIC,18));
			text[i].setSize(20,20);
			
			text[i].setEditable(false);
			showPanel.add(text[i]);
		}
		scroll=new JScrollPane(showPanel);
		
		for(int i=0;i<bt.length;i++){
			bottonPane.add(bt[i]);
		}
		add(bottonPane,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
		
		bt[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new AllInfoShow(username,info123,false,3);
				dispose();
			}
		});
		bt[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"���棺����ǴӲ�ѯ���ܽ���ý���ģ������Ҫ������Ϣ�Ļ�����ֱ�ӵ��롰������Ϣ����������ڳ���Ϣ�Ķ�ʧ��");
				int m=JOptionPane.showConfirmDialog(null, "���Ƿ�Ҫ�������µ���Ϣ��");
				if(m==JOptionPane.YES_OPTION){
					for(int i=0;i<dsi.size();i++)
					{
					 DetailedStudentInfo user=new DetailedStudentInfo(); 
				       user.setUserName(dsi.get(i).getUserName());
					   user.setPasswd(dsi.get(i).getUserPasswd());
					   user.setStudentNumber(text[i*6].getText());
					   user.setUserRealName(text[i*6+1].getText());
					   user.setGender(text[i*6+2].getText());
					   user.setStudentAge(text[i*6+3].getText());
					   user.setLivingAdress(text[i*6+4].getText());
					   user.setStudentPhoneNumber(text[i*6+5].getText());
					   infoNow.add(user);
					}
					try {
						data.StoreDetailed(infoNow);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else{}
			}
		});
		bt[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				for(Persons p:ps){
					if(p.getUsername().equals(username)){
						new MainMenu(p);
						break;
					}
				}
				
			}
		});
		
		
		for(int tmp=0;tmp<6*dsi.size();tmp++){
			final int tmp1=tmp;
			final int i=tmp/6;
			text[tmp1].addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount()==2){
						
						String s,s1;
						s=JOptionPane.showInputDialog("����������:");
						
						s1=dsi.get(i).getUserPasswd();
						
						System.out.println(i);
						if(s.equals(dsi.get(i/6).getUserPasswd())){
							Point p=text[tmp1].getLocationOnScreen();
							
							JOptionPane.showMessageDialog(null, "������ȷ~");
							try {
								
								(new Robot()).mouseMove(p.x+text[tmp1].getSize().width/2, p.y+text[tmp1].getSize().height/2);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							text[tmp1].setEditable(true);
						}
						else{
							JOptionPane.showMessageDialog(null, "�Բ�����������������󣬲��ܸ�����Ϣ");
						}
						
						
					}
				}
				
			});
			text[tmp1].addMouseListener(new MouseListener(){
				public void mouseExited(MouseEvent e ){
					text[tmp1].setEditable(false);
					if(tmp1%6==0){
						try{
							int m=Integer.parseInt(text[tmp1].getText());
						}
						catch(Exception e2){
							JOptionPane.showMessageDialog(null, "ѧ�ű���Ϊ������ɣ��ѻָ�Ĭ�����ã�");
							text[tmp1].setText("0000");
						}
					}
					if(tmp1 % 6==3){
						int m;
						try{
							m=Integer.parseInt(text[tmp1].getText());
							if(m<0 || m>100){
								JOptionPane.showMessageDialog(null, "����������䲻�Ϸ����ѻָ�Ĭ�����ã�");
								text[tmp1].setText("18");
								
							}
						}
						catch(Exception e3){
							JOptionPane.showMessageDialog(null, "��������ַ������������Ϊ���֣�");
							text[tmp1].setText("18");
						}
					}
				}
				public void mouseEntered(MouseEvent e){}
				public void mouseClicked(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mousePressed(MouseEvent e){}
			});
			if(tmp1 %6==2){
				text[tmp1].addKeyListener(new KeyAdapter(){
					public void keyReleased(KeyEvent e){
						if(e.getKeyCode()==KeyEvent.VK_F){
							text[tmp1].setText("female");
						}
						else if(e.getKeyCode()==KeyEvent.VK_M){
							text[tmp1].setText("male");
							
						}
						else{
							JOptionPane.showMessageDialog(null, "��������ַ�����������f(Ů)��m(��)");
							text[tmp1].setText("male");
						}
					}
				});
			}
			
		}
		
		}
		
		
		
		
	}

