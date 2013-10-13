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
//����ʼ�ģ��������
public class NoteBook extends JFrame implements ChangeListener,MouseListener{
	/*
	 * ����ʼǹ��ܵ��������
	 * ���л������ò��ṩ����������ö���ʼǹ��ܵĽӿڷ���
	 * 2012.11.26
	 * @ author ����
	 * student number 11061105
	 */
	int year, month, day;
	Calendar c;
	int first,days;
	LeftPane lp;
	NotePane np;
	public NoteBook(){
		//���ñ���������
		super("�ҵĶ���ʼ� v1.0           @author ����            student number 11061105");
		Container con = getContentPane();
		//��ʼ����������������
		//�˷��������涨��
		bookInit();
		//������ӦӰ�����ڵ��¼�������
		//�˷��������涨��
		addEvent();
		//��������������
		con.add(lp,"West");
		con.add(np);
		this.setBounds(150,250,630,400);
		//��֧�ָı��С
		this.setResizable(false);
		this.setVisible(true);
	}
	/*
	 * ��ʼ����弰���ڵķ���
	 */
	void bookInit(){
		c = Calendar.getInstance();
		lp = new LeftPane();
		np = new NotePane();
		//��DateTime������ȡ������
		year = DateTime.getYear();
		month = DateTime.getMonth();
		day = DateTime.getDay();
	}
	/*
	 * �˷�������Ӱ�����ڵ��¼�����
	 * ���΢���������ں��ı���
	 * ΢��������ֵ�ı��¼���ΪChangeEvent
	 */
	void addEvent(){
		//����¼�����
		lp.ym.showMonth.addChangeListener(this);
		lp.ym.showYear.addChangeListener(this);
		for(int i=1; i<42;i++)
			MonthPane.showDay[i].addMouseListener(this);
	}
	
	//΢�������¼�����
	public void stateChanged(ChangeEvent e){
		int y = year;
		//m�����
		//n���·�
		String m = lp.ym.showYear.getValue().toString();
		String n = lp.ym.showMonth.getValue().toString();
		year = Integer.parseInt(m);
		month = Integer.parseInt(n);
		//�·�����һ��
		//�·ݱ�Ϊ1����ݼ�һ
		if(month>12){
			year+=1;
			month = 1;
			lp.ym.showMonth.setValue(new Integer(1));
			lp.ym.showYear.setValue(new Integer(y+1));
		}
		//�Ϸ�����һ��
		//�·ݱ�Ϊ12����ݼ�1
		else if(month<1){
			year-=1;
			month=12;
			lp.ym.showMonth.setValue(new Integer(12));
			lp.ym.showYear.setValue(new Integer(y-1));
		}
		lp.mp.arrangeNum(year, month);
		//ˢ�����
		noteBookRefresh();
				
	}
	/*
	 * �꣬�£����κ�һ�����ݸı䣬����ˢ�¶���ʼ�
	 * ���޸�������������ʾ���ռ�
	 */
	void noteBookRefresh(){
		lp.mp.arrangeNum(year, month);
		np.setDateInfo(year, month, day);
		np.refreshContent(year, month, day);
		
		
		
		
		
		
		
	}
	/*
	 * ��Ӧ���������
	 */
	public void mouseClicked(MouseEvent e){
		JTextField source = (JTextField) e.getSource();
		try{
			day = Integer.parseInt(source.getText());
			noteBookRefresh();
		}catch(Exception ee){}
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	//����ʼǹ��ܵ����
	public static void note(){
		new NoteBook();
	}

}
