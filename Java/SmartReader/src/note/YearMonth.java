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

public class YearMonth extends JPanel{
	/*
	 * ���ڿ�������������ʾ���µ��࣬����JPanel��ɹ���
	 * ֮��ᱻ�������������·���������������
	 */
	private int year;
	private int month;
	//��ʾ����
	private JLabel y1;
	private JLabel m1;
	JSpinner showYear;
	JSpinner showMonth;
	YearMonth(){
		//�������£�ͬʱ�������µ�΢����
		//���µ�������ͨ��������µļ�ͷ��΢��
		//�����ں�������Ŷ���
		ymInit();
		add(y1);
		add(showYear);
		//������ѡ�����
		add(m1);
		add(showMonth);
	}
	//���õ�ǰ���µķ���
	//ͬʱ�������µ�΢����,������л�������
	void ymInit(){
		y1 = new JLabel ("��");
		//��ȡ���յ������Ϣ
		year = DateTime.getYear();
		//����JSpinner��ʾ��ǰ���
		showYear = new JSpinner (new SpinnerNumberModel (year,0,10000,1));
		//������ʾ��ʽ
		showYear.setEditor (new JSpinner.NumberEditor(showYear,"0000"));
		//���ô�С
		showYear.setPreferredSize(new Dimension(60,30));
		//���������ֺ�
		y1.setFont(new Font("TimesRomn",Font.BOLD,16));
		m1 = new JLabel("��");
		//�µ�����������ȫ����
		month = DateTime.getMonth();
		showMonth = new JSpinner(new SpinnerNumberModel(month,0,13,1));
		showMonth.setEditor(new JSpinner.NumberEditor(showMonth,"00"));
		showMonth.setPreferredSize(new Dimension(60,30));
		m1.setFont(new Font("TimesRomn",Font.BOLD,16));
		
	}

}
