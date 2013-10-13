package StudentManegeSystem.userLogin;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
public class JF_login extends JFrame {
	public static ArrayList< Persons> person=new ArrayList<Persons>();
	
	private JPanel topPanel=new JPanel();
	private JPanel jp2=new JPanel();
	private JPanel jp3=new JPanel();
	private static  JF_login e=new JF_login();
	boolean isTeacher=false;
	
	static boolean show=false;
	private JLabel 
		name=new JLabel("�û���:"),
	    passwd=new JLabel("����:");
	private JTextField jtf1=new JTextField(8);
	

	private JPasswordField jtf2=new JPasswordField(16);
	private JButton login=new JButton("��½");
	private InitializeData data=new InitializeData();
	
	private JButton exit=new JButton("�˳�");
	private JButton register=new JButton("ע��");
	final int LENGTH=280,HEIGHT=180;
	private RichJLabel top=new RichJLabel("��ӭʹ��ѧ����Ϣ����ϵͳ",-2);
	
    public JF_login(){
    	super("�û���¼");
    	
    	try {
			person=(ArrayList<Persons>) data.readData();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	jtf2.addKeyListener(new KeyListener(){
    		public void keyReleased(KeyEvent e){
    		 if(e.getKeyCode()==KeyEvent.VK_ENTER)
    			 checkIn();
    		}
    		public void keyTyped(KeyEvent e){}
    		public void keyPressed(KeyEvent e){}
    	});
    	
    	top.setFont(new Font("����",Font.BOLD,25));
    	top.setHorizontalAlignment(JLabel.CENTER);
    	top.setVerticalAlignment(JLabel.CENTER);
    	login.setFont(new Font("����",Font.BOLD,15));
    	exit.setFont(new Font("����",Font.BOLD,15));
    	topPanel.add(top);
    	jp2.setLayout(new GridLayout(2,2));
    	
    	name.setFont((new Font("����",Font.BOLD,18)));
    	name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	jp2.add(name);
    	jp2.setSize(300,50);
    	jtf1.setToolTipText("�������û�������������ɣ�");
    	jp2.add(jtf1);
    	jtf2.setToolTipText("����������");
    
    	jtf2.addKeyListener(new KeyAdapter(){
    		public void keyTyped(KeyEvent e){
    			
    		}
    	});
  
    	passwd.setFont((new Font("����",Font.BOLD,18)));
    	passwd.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	jp2.add(passwd);
    	jp2.add(jtf2);
    	

    	exit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
					data.Store(person);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			System.exit(0);
    		}
    	});
    	
    	register.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			dispose();
    			show=true;
    			Registery r=new Registery(person);
    			person=r.Registery1();
    			try {
					data.Store(person);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}
    	});
    	
    	login.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			checkIn();
    		}
    	});
    	jp3.setLayout(new FlowLayout());
  
    	jp3.add(register);
    	jp3.add(login);
        jp3.add(exit);
    	setLayout(new BorderLayout());
    	add(top,BorderLayout.NORTH);
    	add(jp2,BorderLayout.CENTER);
    	add(jp3,BorderLayout.SOUTH);
    	
    	
    
    	
    	
    	ImageIcon bg=new ImageIcon("login.jpg");
   	    JLabel backgd=new JLabel(bg);
   	    backgd.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
   	   
   	    this.getLayeredPane().add(backgd, new Integer(Integer.MIN_VALUE));
   	    JPanel jp1=(JPanel)this.getContentPane();
     	jp1.setOpaque(false);
     
     	
    	GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
    	Point p=ge.getCenterPoint();
    	setLocation(p.x-LENGTH/2,p.y-HEIGHT/2);
    	System.out.println(person);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	if(show){
    	final int LENGTH=400,HEIGHT=150;
    	setSize(LENGTH,HEIGHT);
    	setResizable(false);
    	
    	setVisible(true);
    	}
    	
    }
    
    private void checkIn(){
    	
    	boolean idHasFind=false;
    	for(Persons p:person){
    		if(p.getUsername().equals(jtf1.getText())){
    			idHasFind=true;
    			JOptionPane.showMessageDialog(null, "���ҵ��û�����������֤....");
    			if(String.valueOf(p.getPasswd()).equals(String.valueOf(jtf2.getPassword()))){
    				JOptionPane.showMessageDialog(null, "������ȷ~");
    				
    					JOptionPane.showMessageDialog(null, "��½�ɹ�");
    					
    					ArrayList<DetailedStudentInfo> info=(new InitializeData()).readDetailedData();
    					boolean finddata=false;
    					for(DetailedStudentInfo in:info)
    					{
    						if(in.getUserName().equals(p.getUsername())){
    							finddata=true;
    							break;
    						}
    					}
    					if(!finddata){
    						DetailedStudentInfo student=new DetailedStudentInfo();
    						student.setUserName(p.getUsername());
    						student.setPasswd(p.getPasswd());
    						student.setGender("male");
    						student.setStudentAge("18");
    						student.setUserRealName(p.getName());
    						student.setStudentNumber("0000");
    						student.setLivingAdress("china");
    						student.setStudentPhoneNumber("010");
    						info.add(student);
    						try {
								data.StoreDetailed(info);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					}
    					dispose();
    					show=true;
    					new MainMenu(p);
    					try {
							data.Store(person);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					
    			}
    			else
    			{	JOptionPane.showMessageDialog(null, "�������������������������");
    				jtf2.setText("");
    			}
    		}
    	}
		if(!idHasFind)
		{	JOptionPane.showMessageDialog(null, "���û��������ڣ�");
			jtf1.setText("");
			jtf2.setText("");
		}
		
    	
		
    }
    public static void main(String[] args){
    	final int LENGTH=400,HEIGHT=150;
    	System.out.println(person);
    	e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	e.setSize(LENGTH,HEIGHT);
    	e.setResizable(false);
    	e.setBackground(Color.CYAN);
    	e.setVisible(true);
    	new JF_login();
    	
    	
    }
}

