package sources;

import java.awt.*;
import javax.swing.*;

public class AboutFrame extends JDialog
{
    ImageIcon icon1=new ImageIcon("images\\about.jpg");
    //��������ͬʱ��ʾ������ͼ��ͼ��ı�ǩ
    JLabel jlabel1=new JLabel("",icon1,SwingConstants.CENTER); 
	
	//���췽���������������ڣ����⣬�Ƿ�Ϊģ̬�Ի���
	public AboutFrame(JFrame owner,String title,boolean Modal)
	{
		super(owner,title,Modal);
		this.setSize(593,426);
		//�ô��ڲ���ʾ������
		this.setUndecorated(true);
		//���ô��ڱ���ɫ
		this.setBackground(Color.PINK);
		
		//��ȡ��Ļ�ߴ�
		Dimension screenSize=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=screenSize.width;
		int srceenHeight=screenSize.height;
		//���ô��ھ�����Ļ����
		setLocation((screenWidth-getWidth())/2+10,(srceenHeight-getHeight())/2+47);
		
		//��ȡ�Ի�������ݴ���
		Container contentPane=this.getContentPane();
		//����jlabel1���������
		jlabel1.setBounds(593,426,this.getWidth()/2,this.getHeight()/2);
		jlabel1.setBackground(Color.PINK);
		//����jlabel1��������ݴ���
		contentPane.add(jlabel1);
		//Ϊ���ڹر���Ӧ�˳��¼�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}