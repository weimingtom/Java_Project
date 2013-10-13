package StudentManegeSystem.userLogin;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Registery extends JFrame{
	private final int HEIGHT=500,LENGTH=400;
	private JLabel info=new JLabel("��ӭע�ᣡ");
	private JLabel username=new JLabel("�û���*��"),
			userpasswd=new JLabel("����*��"),
			passwdCheck=new JLabel("���ٴ���������*��");
	private JLabel userRealname =new JLabel("����*��");
				  
	private JTextField userNamet=new JTextField(),
	                   userRealNamet=new JTextField();
	private JPasswordField userPasswdt=new JPasswordField(),
							passWdAgaint=new JPasswordField();
	
	private InitializeData data=new InitializeData();
	private JButton regi=new JButton("ע��"),
					exit=new JButton("�˳�ע��");
	private JPanel jp1=new JPanel(),
				   jp2=new JPanel(),
				   jp3=new JPanel(),
				   jp4=new JPanel();
	
	private static ArrayList<Persons> person=new ArrayList<Persons>();
	private boolean check(){
		if(userNamet.getText().equals("")){
			JOptionPane.showMessageDialog(null, "�������û�����");
			
			return false;
		}
		if(String.valueOf(userPasswdt.getPassword()).equals("") ||String.valueOf(passWdAgaint.getPassword()).equals(""))
		{	JOptionPane.showMessageDialog(null,"���������룡");
			return false;
		}
		if(userNamet.getText().length()<4)
		{
			JOptionPane.showMessageDialog(null, "�û�����������4λ��");
			userNamet.setText("");
			return false;
		}
		if(userNamet.getText().length()>8)
		{
			JOptionPane.showMessageDialog(null, "�û������ܳ�����λ��");
			userNamet.setText("");
			return false;
		}
		if(userRealNamet.getText().length()>8){
			JOptionPane.showMessageDialog(null, "��ʵ��������������������");
			return false;
		}
		if(userRealNamet.getText().equals("")){
			JOptionPane.showMessageDialog(null, "�������û���ʵ������");
			return false;
		}
		if(!String.valueOf(userPasswdt.getPassword()).equals(String.valueOf(passWdAgaint.getPassword())))
		{	JOptionPane.showMessageDialog(null, "��������������벻һ�£�����������");
			userPasswdt.setText("");
			passWdAgaint.setText("");
			return false;
		}
		for(Persons p:person){
			   if(p.getName().equals(userRealNamet.getText()))
			   {
				  JOptionPane.showMessageDialog(null, "���Ѿ�ע���������������������");
				  userRealNamet.setText("");
				  return false;
			   }
				if(p.getUsername().equals(userNamet.getText()))
				{	JOptionPane.showMessageDialog(null, "�û����Ѵ��ڣ����������룡");
				    userNamet.setText("");
				    return false;
				}
				
		}
		return true;
	}
	public Registery(ArrayList<Persons> person1){
		this.person=person1;
		
		Font font=new Font("���ķ���",Font.BOLD,18);
		setLayout(new BorderLayout());
		info.setFont(new Font("����",Font.BOLD &Font.ITALIC,25));
		info.setAlignmentX(info.CENTER);
		jp1.add(info);
		add(jp1,BorderLayout.NORTH);
		username.setFont(font);
		userRealname.setFont(font);
		userpasswd.setFont(font);
		passwdCheck.setFont(font);
		userNamet.setToolTipText("�û�����Ҫ����8");
		userRealNamet.setToolTipText("��ʵ������Ҫ����8��");
		jp2.setLayout(new GridLayout(5,2));
		jp2.add(username);
		jp2.add(userNamet);
		jp2.add(userRealname);
		jp2.add(userRealNamet);
		jp2.add(userpasswd);
		jp2.add(userPasswdt);
		jp2.add(passwdCheck);
		jp2.add(passWdAgaint);
		
		
		
		jp4.add(regi);
		jp4.add(exit);
		add(jp2,BorderLayout.CENTER);
		add(jp4,BorderLayout.SOUTH);
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	setLocation(p.x-HEIGHT/2,p.y-LENGTH/2);
    	setSize(LENGTH,HEIGHT);
    	this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    	this.setVisible(true);
	}
	public ArrayList<Persons> Registery1(){
		
		
		regi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(check()){
					final Persons p=new Persons();
					p.setUserName(userNamet.getText());
					p.setPasswd(String.valueOf(userPasswdt.getPassword()));
					p.setName(userRealNamet.getText());
					p.setChinese(0);
					p.setMath(0);
					p.setEnglish(0);
					p.setClassName(null);
					
					
					person.add(p);
					JOptionPane.showMessageDialog(null, "ע��ɹ�");
					try {
						data.Store(person);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					dispose();
					JF_login e1=new JF_login();
					
					final int LENGTH=400,HEIGHT=150;
			    	System.out.println(person);
			    	e1.setDefaultCloseOperation(e1.EXIT_ON_CLOSE);
			    	e1.setSize(LENGTH,HEIGHT);
			    	e1.setResizable(false);
			    	e1.setVisible(true);
					
				}
			}
		});
		
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				new JF_login();
			}
		});
		
		
		if(!this.isVisible())
			return person;
		return person;
		
	}
	

}
