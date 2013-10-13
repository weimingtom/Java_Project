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
public class MonthPane extends JPanel{
	/*
	 * ����ʵ����������
	 * �ڵ����·ݵ�ʱ����ߵ�������ʾ��ͬ������
	 */
	//������ʾ���µ�����
		//�����޸ĵ��ı�������ʾ�����е�����
		static JTextField showDay[];
		//���ڴ洢��Ӧ���ж����죬�Լ���Ӧ�µ�һ�������ڼ��ı���
		int first,days;
		YearMonth ym;
		String[] week;
		//������ʾ�ܼ���JLabel
		JLabel[] title;
		int year,month,day;
		//���������Ĺ��췽��
		MonthPane(){
			//�������񲼾�
			super(new GridLayout(7,7,3,3));
			//��ʼ���ܼ�������ȵ�
			//�����ں��涨��
			mcInit();
			for(int i=0;i<7;i++)
				add(title[i]);
			for(int i=0;i<42;i++)
				add(showDay[i]);
			//�õ�һ���µ���������Ϣ
			//�ں��涨��
			arrangeNum(year,month);
			
		}
		//��ʼ�������ݼ���JLabel��������Ϣ�ķ���
		void mcInit(){
			//���ռ�������DateTime������ȡ
			year = DateTime.getYear();
			month = DateTime.getMonth();
			day = DateTime.getDay();
			//��������
			String week[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
			title = new JLabel[7];
			//ѭ����ʼ��������ʾ�ܼ���JLabel
			//���ò���
			for(int j=0;j<7;j++){
				title[j] = new JLabel();
				title[j].setText(week[j]);
				title[j].setBorder(BorderFactory.createEmptyBorder());
				title[j].setFont(new Font("",1,18));
			}
			//Ϊ��ĩ���ò�ͬ��ɫ
			//�������ռ���ĩ
			title[0].setForeground(Color.red);
			title[6].setForeground(Color.blue);
			showDay = new JTextField[42];
			for(int i =0; i<42; i++){
				showDay[i] = new JTextField();
				//���ø�ʽ
				showDay[i].setFont(new Font("",0,14));
				//���������ڲ��ܱ��޸�
				//Ӧ����DateTime����������ں��Զ�����
				showDay[i].setEditable(false);
			}
		}
		/*
		 * ��ö�Ӧ������������������ʾ�ķ���
		 * ����DateTime�������õ�����ÿ���µ�����
		 */
		public void arrangeNum(int year, int month){
			days = DateTime.getMonDay(year, month);
			Calendar c = Calendar.getInstance();
			c.set(year,month-1,1);
			//�ҵ����µ�һ�������ڼ�
			first = c.get(Calendar.DAY_OF_WEEK)-1;
			//�ӵ�һ���Ӧ����������ʼ��ʼ�����µ�����
			for(int i=first, n=1;i<first + days;i++,n++){
				showDay[i].setText(""+n);
				//�ԡ����족����������ʾ
				if(n==day){
					showDay[i].setForeground(Color.green);
					showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
				}
				//���������������ͨ����
				else{
					showDay[i].setForeground(Color.black);
					showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
				}
			}
			//�Ա���û�е��������ı����������
			//�Ա������һ���Ժ���ı�������Ϊ��
			for(int i= days+first; i<42;i++)
				showDay[i].setText("");
			//�Ա��µ�һ����ǰ���ı�������Ϊ��
			for(int i= first -1;i>=0;i--)
				showDay[i].setText("");
		}

}
