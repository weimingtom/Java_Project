package note;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
//import javax.swing.JComponent;
import javax.swing.JButton; 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
//import javax.swing.JWindow;
import javax.swing.JTree; 
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;


import com.sun.pdfview.FullScreenWindow;
import com.sun.pdfview.OutlineNode;
import com.sun.pdfview.PDFDestination;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFObject;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;
import com.sun.pdfview.PageChangeListener;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.ThumbPanel;
import com.sun.pdfview.action.GoToAction;
import com.sun.pdfview.action.PDFAction;
public class NotePane extends JPanel implements ActionListener{
	/*
	 * �������ɶ���ʼǲ��ֵ���
	 * ʵ�ֶ���ʼǵĵ��뵼��ɾ������
	 * ����JPanelʵ�֣��ֲ��ڽ����Ҳ�
	 */
	//�����ս����ڶ���ʼ�������Ϸ���ʾ
		private int year, month, day;
		//note������д����ʼǲ�����
		private JTextArea note;
		//ʵ�ִ洢ɾ���������ܵİ�ť
		private JButton save, delete, export;
		//���ڹ�������ʼ��������ڵ�hash��
		private Hashtable table;
		//������ʾ���ڵ�JLabel
		//�ú��涨���setDateInfor����������ʾ������
		private JLabel dateInfo;
		//�ļ��������ڱ���ɾ�������Ȳ�����
		private File file;
		private JPanel buttonpane;
		//note�Ĺ��췽�������ڳ�ʼ�������������λ��
		NotePane(){
			//���ñ߿򲼾�
			super(new BorderLayout());
			//��ʼ������ʼ�����ķ������ں��涨��
			noteInit();
			//��ʼ����ť�ķ������ں��涨��
			//��ʼ�����棬ɾ����������ť������
			buttonInit();
			//��Ӱ�ť�¼������ڼ���
			addEvent();
			//�������ӵ�������
			//��������ķֲ�λ��
			add(dateInfo,"North");
			//�������ı��������������
			add(new JScrollPane(note));
			//���ù��ܰ�ť������е�λ��
			add(buttonpane,"South");
		}
		/*
		 * 2012.11.29 debug
		 */
		
		//note�ĳ�ʼ�����������л�������
		//ͬʱʵ�ֶ���ʼǺ�file�����Ĺ�����ʹ�ÿ��Խ��б���ɾ����������
		private void noteInit(){
			//��ȡ���ڣ�������ʾ���ı����Ϸ�
			year = DateTime.getYear();
			month = DateTime.getMonth();
			day = DateTime.getDay();
			//�滻������䣬������ɫ����������
			dateInfo = new JLabel(year + "��" + month + "��" + day +"��",JLabel.CENTER);
			table = new Hashtable();
			//���ڲ����Ķ���ʼ�file
			file = new File("Note.txt");
			//��ʼ��file��
			initFile();
			//����һ���ı��������ڶ���ʼ�ʹ��
			note = new JTextArea();
			//���������ֺ�
			note.setFont(new Font("",0,14));
			//���ù�����
			note.setLineWrap(true);
		}
		/*
		 * ��ʼ����ť����ķ���
		 * �����ı�������ӵ�������
		 */
		private void buttonInit(){
			// TODO
			//������ť
			save = new JButton("����ʼ�");
			delete = new JButton("ɾ���ʼ�");
			export = new JButton("�����ʼ�");
			//����ť���뵽��ť���
			buttonpane = new JPanel();
			buttonpane.add(save);
			buttonpane.add(delete);
			buttonpane.add(export);
		}
		/*
		 * ���ڲ����ļ�����
		 */
		private void initFile(){
			// TODO
			//�ж��ļ��Ƿ����
			if(!file.exists()){
				try{
					//�����
					FileOutputStream out = new FileOutputStream(file);
					ObjectOutputStream objectOut = new ObjectOutputStream(out);
					objectOut.writeObject(table);
					objectOut.close();
					out.close();
				}catch(IOException e){}
			}
		}
		
		/*
		 * 2012.11.26�ڶ����޸�
		 * ��Ӱ�ť���¼���������
		 */
		private void addEvent(){
			//Ϊ���棬ɾ����������ť�����¼�������
			save.addActionListener(this);
			delete.addActionListener(this);
			export.addActionListener(this);
		}
		/*
		 * �жϴ������¼���ʲô�ķ���
		 * ��������Ӧ������Ӧ�û�����
		 * ���õķ����ں��涨��
		 */
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == save)
				//������Ӧ���ڵĶ���ʼ�
				save(year,month,day);
			else if(e.getSource() == delete)
				//ɾ����Ӧ���ڵĶ����ʼ�
				delete(year,month,day);
			else if(e.getSource() == export)
				//������Ӧ���ڵĶ���ʼ�
				export();
		}
		/*
		 * ����������ʾ���ı��������������Ϣ�ķ���
		 */
		public void setDateInfo(int y, int m, int d){
			String s = y+"��"+m+"��"+d+"��";
			dateInfo.setText(s);
			year = y;
			month = m;
			day = d;
		}
		/*
		 * ���ڻ�ȡ���ڵķ���
		 * ����һ����ʽΪyear/month/day���ַ���
		 * ��֮��Զ���ʼǵĲ��������лᱻ�����õ�
		 */
		public String getDateKey(){
			//Ϊʹ���ڸ�ʽͳһ�������·ݻ�������С����λ��������������жϣ����ڷ��ص��ַ��������0
			String s = ""+year;
			if(month<10) s+="/0"+month;
			else s+="/"+month;
			if(day<10) s+="/0"+day;
			else s+="/"+day;
			return s;
		}
		/*
		 * �����ı������ݵķ���
		 * 2012.11.30�Ż�
		 */
		public void refreshContent(int year, int month, int day){
			//����֮ǰ����ķ������õ���ǰ���ڵ��ַ���
			String key = this.getDateKey();
			//��ȡ������������еĻ���
			//table���ڽ�����ʼ������������ڹ���������ţ�������Ӧ���ڵ�����д�뵽note�У�����еĻ���
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				//���ı�������������д��table��
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
			}catch(Exception e){}
			
			//�����������ַ��Ļ����ͽ��ַ�д�����ʼǵ��ı���
			//���û�У��ͼ��������ı�������κ��ַ�
			if(table.containsKey(key))
				note.setText(table.get(key)+"");
			else note.setText("");
		}
		/*
		 * ����������actionPerformed�е��õ���Ӧ����
		 * ������Ӧ�û�����
		 * ��ҪΪ������־��ɾ����־��������־
		 */
		/*
		 * ���������Ѵ��ı���д��Ķ���ʼ�
		 */
		public void save(int year, int month, int day){
			//����������������ַ�
			String logContent = note.getText();
			//������¼����ʼ��������ڵ��ַ���
			String key = this.getDateKey();
			//�������ʼ�
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
				table.put(key, logContent);
				FileOutputStream out = new FileOutputStream(file);
				ObjectOutputStream ObjectOut = new ObjectOutputStream(out);
				ObjectOut.writeObject(table);
				ObjectOut.close();
				out.close();
				
			}catch(Exception e){}
			//���ڷ��ظ��û�һ���ɹ��������Ϣ
			String message = year+"��"+month+"��"+day+"��"+"�Ķ���ʼ��Ѿ�����";
			//����һ���Ի�����ʾ����ɹ�����Ϣ
			JOptionPane.showMessageDialog(this, message);
		}
		/*
		 * ����ʵ��ɾ�������ķ���
		 */
		
		public void delete(int year, int month, int day){
			String key = this.getDateKey();
			//�����Ӧ���ڴ��ڶ���ʼ�
			if(table.containsKey(key)){
				//���û���ʾȷ����Ϣ�����������
				String message = "��ȷ��Ҫɾ��"+year+"��"+month+"��"+day+"��"+"�Ķ���ʼ���";
				//��JOptionPane����ȷ����Ϣ�Ի���
				if(JOptionPane.showConfirmDialog(this,message)==0){
					//ȷ��Ҫɾ������ִ��ɾ������
					//���Hash���ж�Ӧ���ڵ�����
					table.remove(key);
					try{
				        FileOutputStream out=new FileOutputStream(file);
				        ObjectOutputStream objectOut=new ObjectOutputStream(out);
				        objectOut.writeObject(table);
				        objectOut.close();
				        out.close();
				        //�����Ӧ���ı�������
				        note.setText("");
					}catch(Exception e){}
				}
			}
				//���Ҫɾ�������ڸ�����û�ж���ʼǴ��ڣ��򲻱�ִ��ȷ�ϵȲ���
				//ֱ�ӷ��ظ��û�һ��û����Ӧ���ڵĶ���ʼǵ���ʾ
			else{
					String message1 = year+"��"+month+"��"+day+"��"+"û�ж���ʼǿ�ɾ��";
					JOptionPane.showMessageDialog(this, message1,"��ʾ",JOptionPane.WARNING_MESSAGE);
				}
		}
		/*
		 * �����ļ�
		 * �����ж���ʼǵ�����һ���ı��ļ�
		 */
		public void export(){
			File m;
			//ѡ��Ҫ������ļ�
			JFileChooser n = new JFileChooser();
			n.showSaveDialog(null);
			m = n.getSelectedFile();
			try{
				FileInputStream in1 = new FileInputStream(file);
				ObjectInputStream in2 = new ObjectInputStream(in1);
				table = (Hashtable) in2.readObject();
				in1.close();
				in2.close();
				
			}catch(Exception e){}
			TreeMap s = new TreeMap(table);
			Set maping = s.entrySet();
			//���Ҫ�������ļ��Ѿ����ڣ���ɾ��
			if(m.exists()){
				m.delete();
			}
			else {
				//���Ҫ�������ļ������ڣ����½�һ���ļ�
				try{
					m.createNewFile();
				}catch(Exception e){}
			}
			for(Iterator i = maping.iterator();i.hasNext();)
			{
				Map.Entry me = (Map.Entry) i.next();
				//��������ʼ�
				try{
					FileOutputStream out = new FileOutputStream(m,true);
					BufferedOutputStream bout = new BufferedOutputStream(out);
					DataOutputStream dout = new DataOutputStream(bout);
					dout.writeUTF(me.getKey()+"\r\n\t"+me.getValue()+"\r\n");
					dout.close();
				}catch(Exception e){}
			}
		}

}
