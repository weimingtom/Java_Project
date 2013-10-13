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


/*
 * 2012.11.26��һ���޸� 
 * 2012.11.29 debug
 * @ author ����
 * student number 11061105
 */
public class DateTime {
	/*
	 * ����ķ���������Ϊ�෽������������ļ̳�����չ
	 */
	//���ڻ�ȡ��ǰ���ڣ��ڶ����ʼǹ�����ʹ�ã�
	static Calendar today = Calendar.getInstance();
	//������ݵķ���
	static int getYear(){
		return today.get(today.YEAR);
	}
	//�����·ݵķ���
	/*
	 * 2012.11.29 debugʱ�޸�
	 */
	static int getMonth(){
		return today.get(today.MONTH)+1;
	}
	//�������ڵķ���
	static int getDay(){
		return today.get(today.DATE);
	}
	//�����·������ķ���
	//��ͬ�·ݶ�Ӧ�Ĳ�ͬ������Ҫ����ʾ������������
	static int getMonDay(int year, int month){
		//������ʼΪ31
		//��Ϊһ��������Ϊ31����·���࣬����������Լ��ٲ�������
		//ʹ����д���Ļ�������ж��Ƿ�Ϊ31����·�
		int days = 31;
		//�ж��ǲ�������Ϊ30����·�
		if (month == 4 || month == 6 || month == 9 || month == 11)
			days = 30;
		//2�µ��жϱȽ��鷳�������ж�
		if(month == 2)
			//��������
			if((year%4 == 0 && year%100 != 0) || year%400 ==0)
				days = 29;
			else
				days = 28;
		return days;
		
	}


}
