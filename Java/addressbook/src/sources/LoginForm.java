package sources;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener
{
	//�������
	private JPanel jpanel1=new JPanel();      //�����������
	private JLabel[] arrayJLabel={new JLabel("�û���:"),new JLabel("��  ��:"),new JLabel("")};   //��������������ǩ�ı�ǩ����
	private JTextField userNameJTextField=new JTextField();                                      //�����û����ı���
	private JPasswordField passwordJPasswordField=new JPasswordField();                          //��������������
	private JButton[] arrayJButton={new JButton("��  ¼"),new JButton("ע  ��")};                //��������������ť����
	
	//���캯��
	public LoginForm()
	{	
		//���ô����������
		//���ñ�������ʾͼ��
		Image icon2=Toolkit.getDefaultToolkit().getImage("images\\ico.gif");
		this.setIconImage(icon2);
		//���ô��ڱ���
		this.setTitle("--��  ¼--");
		//���ô��ڲ����϶���С
		this.setResizable(false);
		//���ô��ڴ�С
		this.setSize(350,300);
		//��ȡ��Ļ�ߴ�
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=screenSize.width;
		int srceenHeight=screenSize.height;
		//���ô��ھ�����Ļ����
		setLocation((screenWidth-getWidth())/2,(srceenHeight-getHeight())/2);
		
		//����������������
		
		//������������Ĳ��ֲ���Ϊ��
		jpanel1.setLayout(null);
		//���������������ɫ
		jpanel1.setBackground(Color.WHITE);
		
		//����ѭ����ӱ�ǩ�Ͱ�ť
		for(int i=0;i<2;i++)
		{
			//����2����ǩ��2����ť��λ�ü���С
			arrayJLabel[i].setBounds(65, 164+i*35, 80, 26);
			arrayJButton[i].setBounds(60+i*135, 235, 90, 26);

			//����2����ǩ��������ť���ı���ɫ
			arrayJLabel[i].setForeground(Color.RED);
			arrayJButton[i].setForeground(Color.RED);
			
			//����2����ǩ��������ť������
			arrayJLabel[i].setFont(new Font("����",Font.PLAIN,14));
			arrayJButton[i].setFont(new Font("����",Font.PLAIN,15));
			
			//��2����ǩ��2����ť��ӵ��������
			jpanel1.add(arrayJLabel[i]);
			jpanel1.add(arrayJButton[i]);
			
            //Ϊ��ť��Ӷ����¼�������
			arrayJButton[i].addActionListener(this);
		}
		
		//�����ı������������������ַ���
		userNameJTextField.setColumns(15);
		passwordJPasswordField.setColumns(16);
		
		//�����ı����������λ�ü���С
		userNameJTextField.setBounds(115, 161, 160, 30);
		passwordJPasswordField.setBounds(115, 198, 160, 30);
		//����������������ַ�
		passwordJPasswordField.setEchoChar('*');

		//�����ı�����������ı���ɫ
		userNameJTextField.setForeground(Color.RED);
		passwordJPasswordField.setForeground(Color.RED);
		
		//�����ı��������������
		userNameJTextField.setFont(new Font("����",Font.BOLD,16));
		passwordJPasswordField.setFont(new Font("����",Font.BOLD,16));
		
		//����һ��ͼ�����������ʾ��½����ͼ��
		ImageIcon icon1=new ImageIcon("images\\txl_1.jpg");
		//����ͼ��
		arrayJLabel[2].setIcon(icon1);
		//���ñ�ǩλ�ü��ߴ�
		arrayJLabel[2].setBounds(0,0,350,158);
		
		//���ı�����������ӵ��������
		jpanel1.add(arrayJLabel[2]);
		jpanel1.add(userNameJTextField);
		jpanel1.add(passwordJPasswordField);
		
		//Ϊ�ı�������������¼�������
		userNameJTextField.addActionListener(this);
		passwordJPasswordField.addActionListener(this);
		
		//�����������ӵ����ݴ���
		this.add(jpanel1);
		//Ϊ������ӹر���Ӧ�¼�
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		//��ʾ����
		this.setVisible(true);
	}
	
	//����һ����������������ı������ݣ�����λ���㵽�ı���
	public void userNameClear()
	{
		//����ı�������
		userNameJTextField.setText("");
		//�ı����ý���
		userNameJTextField.requestFocus();
	}
	//����һ�����������������������ݣ�����λ���㵽�����
	public void passwordClear()
	{
		//������������
		passwordJPasswordField.setText("");
		//������ý���
		passwordJPasswordField.requestFocus();
	}

	//@Override      //�����˷���Ϊ��д����
	//ʵ��ActionListener�ӿڵķ�������ʵ�ֶ��¼��ļ�������
	public void actionPerformed(ActionEvent e)
	{
		//�������(���ڵ���DBOperate��ķ�����ʵ�δ���)
		String user=userNameJTextField.getText().toString().trim();                //�û�������
		String pwd=String.valueOf(passwordJPasswordField.getPassword());           //�������
		String sql="";                                                             //SQL��ѯ������
		
		//����¼�Դ���ı���������������Ӧ����
		if(e.getSource()==userNameJTextField)
		{
			//�ý���ת�Ƶ������
			passwordJPasswordField.requestFocus();
		}
		//����¼�Դ�ǵ�¼��ť������������������Ϻ����˻س���ť���������Ӧ����
		else if(e.getSource()==arrayJButton[0]||e.getSource()==passwordJPasswordField)
		{
			//�ж������Ƿ�Ϸ�
			if(user.equals(""))
			{
				//��ʾ���벻�Ϸ�
				JOptionPane.showMessageDialog(this, "�û������벻�Ϸ�!", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				//�ı����ý���
				userNameJTextField.requestFocus();
				//�þ�ɷ�ֹ�û����������Ϊ��һ���Ե���2���Ի���
				return;
			}
			if(pwd.equals(""))
			{
				//��ʾ���벻�Ϸ�
				JOptionPane.showMessageDialog(this, "�������벻�Ϸ�!", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				//������ý���
				passwordJPasswordField.requestFocus();
				return;
			}
			else
			{
				//����DBOperate��ķ��������жϣ��û����������Ƿ�ƥ��
				if(true)
				{
					//��¼�ɹ�����ʾ������,�����ݵ�¼���û�������Ϊʵ��
					MainFrame mainframe=new MainFrame(user);
					mainframe.setVisible(true);
					//�ͷŵ�¼����
					this.dispose();
				}
				else
				{
					//��¼ʧ�ܣ���ʾ�û������������
					JOptionPane.showMessageDialog(null, "�û�������������\n�����Ƿ������ٽ��е�¼!", "ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
					//��������
					this.passwordClear();
					//����ı���
					this.userNameClear();
					return;
				}
			}
		}
		//����¼�Դ��ע�ᰴť�������Ӧ����
		else if(e.getSource()==arrayJButton[1])
		{
			//�ж��ı����Ƿ�Ϊ��
			if(user.equals(""))
			{
				//��ʾ���벻�Ϸ�
				JOptionPane.showMessageDialog(this, "�û�������Ϊ��!", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				//����ı�������
				this.userNameClear();	
				//�ı����ý���
				userNameJTextField.requestFocus();
				//�þ�ɷ�ֹ�û����������Ϊ��һ���Ե���2���Ի���
				return;
			}
			//�ж�������Ƿ�Ϊ��
			else if(pwd.equals(""))
			{
				//��ʾ���벻�Ϸ�
				JOptionPane.showMessageDialog(this, "���벻��Ϊ��!", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				//������������
				this.passwordClear();
				//������ý���
				passwordJPasswordField.requestFocus();
				return;
			}
			//�ж�ע���û��Ƿ����
			else
			{
				//�ж�ע����û��Ƿ��Ѿ�����
				sql="SELECT UserName FROM User WHERE UserName='"+user+"'";
				if(DBOperate.isExist(sql))
				{
					//ע���û��Ѿ����ڣ�������Ӧ��ʾ
					JOptionPane.showMessageDialog(this, "�Բ���ע��ʧ�ܣ�\n���û��Ѿ�����!", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					//������������
					this.passwordClear();
					//����ı�������
					this.userNameClear();
					return;
				}
				else
				{
					//���Բ����쳣
					try
					{
						//��������ִ��ע�����
						sql="INSERT INTO User VALUES('"+user+"','"+pwd+"')";
						if(true)
						{
							//��ʾ�û�ע��ɹ�
							JOptionPane.showMessageDialog(this, "��ϲ��!\nע��ɹ���", "ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					catch(Exception ex)
					{
						//��ӡ������ԭ��
						ex.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	//������
	public static void main(String []args)
	{
		new LoginForm();//������½����
	}
}
