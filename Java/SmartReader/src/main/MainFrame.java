package main;

import util.CreatecdIcon;
import util.Buttoninit;
import util.Fileoperator;
import util.Pagecontroller;
import util.Buttonactivator;
import util.GetPage;
import util.PrintThread;
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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton; //import javax.swing.JComponent;
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
import javax.swing.JTree; //import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.LookAndFeel;

import note.DateTime;
import note.LeftPane;
import note.MonthPane;
import note.Motto;
import note.NoteBook;
import note.NotePane;
import note.YearMonth;

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


import sun.java.swing.plaf.nimbus.*;
import activity.SimulateActivity;
/*
 * 2012.11.25��һ���޸� 
 * 2012.11.26�ڶ����޸�
 * 2012.12.1 third
 * @author ����
 * student number 11061105
 */

/*
 * ��������������
 */
public class MainFrame extends JFrame implements PageChangeListener, 
TreeSelectionListener,KeyListener{
	//���������ݵ����
	JPanel jpmain = new JPanel();
	//���ö�ȡPDF�ĵ����ݵ����
	PagePanel jp;
	//����ȫ����ʾ���
	FullScreenWindow fullScreen;
	//����ͼ���
	ThumbPanel thumbs;
	
	//�½��ļ���壨����������
	JScrollPane documentScroller = new JScrollPane();
	//PDFRender���л�ȡPDF�ĵ���PDFFile��
	PDFFile pdffile;
	//�û���дҳ����ı���
	JTextField pageField;
	
	//curpage���ڴ浱ǰ��ҳ��
	int curpage = 0;
	//��������ǩ��ť����
	JTabbedPane tabbedPane;
	//���ڻ�ȡ��ٵ�OutlineNode����
	OutlineNode outline = null;
	//��Сҳ��İ�ť
	JButton smallButton;
	//����ȫ���İ�ť
	JButton fullScreenButton;
	//����ҳ���ʽ
	PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();
	//����ļ������ַ���
	String docName;
	
	private JScrollPane contentPanel;

	private JScrollPane thumbscroll;

	private JButton nextButton;

	private JButton backButton;

	//���ڴ洢��ǰҳ��
	private JLabel jl;

	private JButton bigButton;
	private JButton firstButton;
	private JButton lastButton;
	private JButton noteButton;
	//���ڱ�������򿪵�Ŀ¼
	private File prevDirChoice;
	
	//����¼��ı���
	private Timer activityMonitor;
	//���涨��
	private SimulateActivity activity;
	PagePanel fspp;
	//ҳ�湹��������ں������
	PageBuilder pb = new PageBuilder();
	
	//����ȷ��ҳ��λ��
	private Point loc = null;
	//���ڼ�¼�����ʱ��λ��
	private Point tmp = null;
	//��¼ҳ���ǲ��Ǳ���ק
	private boolean isDragged = false;
	
	/*
	 * mainframe���캯��
	 * ���û�������
	 * �����������
	 */
	public MainFrame(){
		try{
			//�������
			UIManager.setLookAndFeel(
//					new sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
					new javax.swing.plaf.metal.MetalLookAndFeel());
		}catch(UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		//���ñ�����ʾ����
		setTitle("SmartReader        @author ����                            student number 11061105");
		//��������С
		setSize(new Dimension(900,700));
		//��֧�ָı�����С
		this.setResizable(false);
		//����ҳ�沼��
		getContentPane().setLayout(null);
		//Ϊ�������������
		//CreateMenuBar()Ϊ�����������ķ���
		//�����������ķ����ں��涨��
		getContentPane().add(CreateMenuBar());
		/*
		 * ������ߵ�����ͼ���
		 */
		//��������ͼ���
		thumbs = new ThumbPanel(null);
		//�ֱ�������ֱ��ˮƽ�Ĺ�����
		//��ֱ������Զ�й�����
		//ˮƽ������Ҫ������
		//�����Ķ��壬Ϊ˽��
		thumbscroll = new JScrollPane(thumbs,
				                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		thumbscroll.getViewport().setView(thumbs);
		//���ñ�����ɫ
		thumbscroll.getViewport().setBackground(Color.gray);
		tabbedPane = new JTabbedPane();
		//Ϊ����ǩ�����ͼ��
		tabbedPane.addTab(null, CreatecdIcon.add("02.gif"),thumbscroll);
		tabbedPane.addTab(null, CreatecdIcon.add("05.gif"),null);
		//���÷��÷�λ
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//�ѱ�ǩ���ӵ������
		getContentPane().add(tabbedPane);
		//���ñ�ǩ���Ĵ�С
		tabbedPane.setBounds(0,63,270,600);
		//��ӹ������������
		getContentPane().add(thumbscroll);
		
		/*
		 * �����м���������
		 */
		//�������ݵ����
		contentPanel = new JScrollPane();
		jp = new PagePanel();
		//�������¼�������
		//JPMouseActionΪAdapter����
		jp.addMouseListener(new JPMouseAction());
		//�����������
		//JPMouseMotionActionΪadapter����
		jp.addMouseMotionListener(new JPMouseMotionAction());
		//�������񲼾�
		jpmain.setLayout(new GridLayout(0,1));
		
		contentPanel.setViewportView(jpmain);
		//�����������λ�����С
		contentPanel.setBounds(270,63,600,600);
		//�������뵽������
		getContentPane().add(contentPanel);
		
		/*
		 * ����������
		 */
		//��ӹ�������������
		getContentPane().add(CreateToolBar());
		//���ùرղ���
		//�ر�ʱ��Ҫ�˳���������
		//��Ϊ������������壬������һ��ģ��
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		/*
		 * ����������������Ļ�м�
		 */
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - getWidth())/2;
		int y = (screen.height - getHeight())/2;
		//����λ��
		setLocation(x,y);
	}
	
	/*
	 * �����������ķ���
	 * ����һ��JMenuBar
	 * 2012.11.26�ڶ����޸�
	 * @author ����
	 * student number 11061105
	 */
	
	public JMenuBar CreateMenuBar(){
		//������󷵻ص�JMenuBar
		final JMenuBar menuBar = new JMenuBar();
		//���ù�����λ���뷶Χ
		menuBar.setBounds(0,0,1000,25);
		//�½�һ�����ļ����İ�ť
		final JMenu newItemMenuItem1 = new JMenu();
		//���ð�ť��ʾ���ı�
		newItemMenuItem1.setText("File(F)");
		
		/*
		 * ����openѡ��
		 */
		//����ť��ӵ���������
		menuBar.add(newItemMenuItem1);
		//�½�File��ť�����˵���ѡ��
		final JMenuItem newItemMenuItem_1 = new JMenuItem();
		//����ѡ����ı�
		newItemMenuItem_1.setText("Open(O)");
		//��ѡ����뵽File��ť�������˵���
		newItemMenuItem1.add(newItemMenuItem_1);
		//��Ӱ�������ѡ����ʱ��ʾ����ʾ����
		newItemMenuItem_1.setToolTipText("Open PDF File");
		//����¼�������
		

		//TODO ��ʽ��Щ��֣��ǵ��޸� 
		newItemMenuItem_1.addActionListener(
				new java.awt.event.ActionListener(){
					//�����¼��������Ӧ�ķ���
					public void actionPerformed(ActionEvent evt){
						//���ô��ļ�����
		
						doOpen();
					}
				});
		/*
		 * ����ҳ������ѡ��
		 */
		//�½�һ����File��ť�����˵���ѡ��
		final JMenuItem newItemMenuItem_2 = new JMenuItem();
		//����ѡ����ʾ���ı�
		newItemMenuItem_2.setText("Page Setup(S)");
		//��ѡ����ӵ������˵���
		newItemMenuItem1.add(newItemMenuItem_2);
		//��Ӱ�������ѡ����ʱ��ʾ����ʾ����
		newItemMenuItem_2.setToolTipText("Set the properties of the page");
		//����¼�������
		//��Ӧ�û�����������Ӧ����
		newItemMenuItem_2.addActionListener(
				new ActionListener(){
					//�����¼��������Ӧ�ķ���
					public void actionPerformed(ActionEvent arg0){
						//����ҳ�����÷���
						//�˷����ں��漯�ж���
						doPageSetup();
					}
				});
		/*
		 * ���ô�ӡѡ��
		 */
		//�½�һ����File��ť�����˵���ѡ��
		final JMenuItem newItemMenuItem_3 = new JMenuItem();
		//����ѡ����ʾ���ı�
		newItemMenuItem_3.setText("Print(P)");
		//��ѡ����ӵ������˵���
		newItemMenuItem1.add(newItemMenuItem_3);
		//��Ӱ�������ѡ����ʱ��ʾ����ʾ����
		newItemMenuItem_3.setToolTipText("Print the file");
		//����¼�������
		//��Ӧ�û�����������Ӧ����
		newItemMenuItem_3.addActionListener(
				new ActionListener(){
					//�����¼��������Ӧ�ķ���
					public void actionPerformed(ActionEvent arg1){
						//����ҳ�����÷���
						//�˷����ں��漯�ж���
						doPrint();
					}
		});
		
		/*
		 * �����˳�ѡ��
		 */
		//�½�һ����File��ť�����˵���ѡ��
		final JMenuItem newItemMenuItem_4 = new JMenuItem();
		//����ѡ����ʾ���ı�
		newItemMenuItem_4.setText("Exit(E)");
		//��ѡ����ӵ������˵���
		newItemMenuItem1.add(newItemMenuItem_4);
		//��Ӱ�������ѡ����ʱ��ʾ����ʾ����
		newItemMenuItem_4.setToolTipText("Exit and close the SmartReader");
		//����¼�������
		//��Ӧ�û�����������Ӧ����
		newItemMenuItem_4.addActionListener(
				new ActionListener(){
					//�����¼��������Ӧ�ķ���
					public void actionPerformed(ActionEvent arg2){
						//����ҳ�����÷���
						//�˷����ں��漯�ж���
						doClose();
						//�رճ���
						System.exit(0);
					}
		});
		return menuBar;
	}
	
	/*
	 * ���嶯��
	 * �Լ������������õ���Ӧ����
	 */
	
	Action firstAction = new AbstractAction(){
		/*
		 * �û������ҳ��ťʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent arg0){
			//��ת����ҳ�ķ���
			//����ͳһ����
			doFirst();
		}
	};
	
	Action lastAction = new AbstractAction(){
		/*
		 * �û����ĩҳ��ťʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent arg0){
			//��ת��βҳ�ķ���
			doLast();
		}
	};
	Action nextAction = new AbstractAction(){
		/*
		 * �û������һҳ��ťʱ�����Ķ���
		 */
		
		public void actionPerformed(ActionEvent evt) {
			//��һҳ�ķ���
			doNext();
		}
	};
	
	Action prevAction = new AbstractAction(){
		/*
		 * �û������һҳʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent evt){
			//��һҳ�ķ���
			doPrev();
		}
		
		
	};
	
	/*
	 * 2012.11.26 debug
	 */
	Action BigAction = new AbstractAction(){
		/*
		 * �û�����Ŵ�ҳ��ʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent evt){
			PDFPage page = pdffile.getPage(curpage);
			//�Ȼ�ȡԭ��ͼƬ�Ŀ�͸�
			Rectangle rect = new Rectangle(0,0,
					(int) page.getBBox().getWidth(),
					(int) page.getBBox().getHeight());
			
			//�����Ŵ��ͼƬ
			Image img = page.getImage(
					//��Ŵ�һ��
					rect.width*2,
					//���Ŵ�һ��
					rect.height*2,
					//clip rect
					rect,
					//Imageobserver����Ϊ��
					null,
					//�ð�ɫ��䱳��
					true,
					//block until drawing is done
					true);
			//����������֮ǰ�Ѿ������ݣ��������
			if(jpmain != null)
				jpmain.removeAll();
			
			/*
			 * ����������֮��ˢ������
			 * �滻Ϊ�Ŵ���ͼƬ
			 * �Ŵ�ͼƬ�������img����
			 * ͬʱ��Ҫ���������������¼�����
			 */
			
			contentPanel.setViewportView(jpmain);
			//�ٴ����ñ߽�
			//����Ľ�ǰλ����
			contentPanel.setBounds(230,60,752,600);
			//������ʱ������
			//JPMouseActionΪAdapter����
			jpmain.addMouseListener(new JPMouseAction());
			//�����������
			//JPMouseMotionActionΪadapter����
			jpmain.addMouseMotionListener(new JPMouseMotionAction());
			//���½�ͼƬ��ӽ����������
			ImageIcon i = new ImageIcon(img);
			JLabel l = new JLabel(i);
			jpmain.add(l);
			/*
			 * ��Ϊjava���÷���
			 * 2012.11.26
			 * @yulang
			 */
			
			//��Ч
			validate();
			//ˢ��
			repaint();
		}
	};
	
	Action SmallAction = new AbstractAction(){
		/*
		 * �û������Сҳ�水ťʱ�Ķ���
		 * ��BigAction��ȫ���ƣ���������������
		 */
		public void actionPerformed(ActionEvent evt){
			PDFPage page = pdffile.getPage(curpage);
			//�Ȼ�ȡԭ��ͼƬ�Ŀ�͸�
			Rectangle rect = new Rectangle(0,0,
					(int) page.getBBox().getWidth(),
					(int) page.getBBox().getHeight());
			
			//������С��ͼƬ
			Image img = page.getImage(
					//��Ŵ�һ��
					rect.width/2,
					//���Ŵ�һ��
					rect.height/2,
					//clip rect
					rect,
					//Imageobserver����Ϊ��
					null,
					//�ð�ɫ��䱳��
					true,
					//block until drawing is done
					true);
			//����������֮ǰ�Ѿ������ݣ��������
			if(jpmain != null)
				jpmain.removeAll();
			
			/*
			 * ����������֮��ˢ������
			 * �滻Ϊ�Ŵ���ͼƬ
			 * ��СͼƬ�������img����
			 * ͬʱ��Ҫ���������������¼�����
			 */
			
			contentPanel.setViewportView(jpmain);
			//�ٴ����ñ߽�
			//����Ľ�ǰλ����
			contentPanel.setBounds(230,60,600,600);
			//������ʱ������
			//JPMouseActionΪAdapter����
			jpmain.addMouseListener(new JPMouseAction());
			//�����������
			//JPMouseMotionActionΪadapter����
			jpmain.addMouseMotionListener(new JPMouseMotionAction());
			//���½�ͼƬ��ӽ����������
			ImageIcon i = new ImageIcon(img);
			JLabel l = new JLabel(i);
			jpmain.add(l);
			/*
			 * ��Ϊjava���÷���
			 * 2012.11.26
			 * @yulang
			 */
			
			//��Ч
			validate();
			//ˢ��
			repaint();
		}
	};
	
	
	Action fullScreenAction = new AbstractAction(){
		/*
		 * �û����ȫ����ťʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent evt){
			//ȫ����ʾ�ķ���
			doFullScreen((evt.getModifiers() & ActionEvent.SHIFT_MASK) != 0);
		}
	};
	
	/*
	 * SmartReader������֮��
	 * ����ʼǹ���
	 */
	
	Action noteAction = new AbstractAction(){
		/*
		 * �û��������ʼǰ�ťʱ�Ķ���
		 */
		
		public void actionPerformed(ActionEvent evt){
			/*
			 * �򿪶���ʼ�ģ��ķ���
			 * ���õ�NoteBook��������������õĽӿڷ���
			 * public static void note()
			 * ���ô˷������Կ�������ʼ�ģ��
			 */
			note();
		}
	};
	
	/*
	 * ���涨��������Ӧ�û��¼�ʱ���õķ���
	 * 2012.12.1 debug
	 */
	
	/*
	 * �򿪶���ʼ�ģ��ķ���
	 * ���õ�NoteBook��������������õĽӿڷ���
	 * public static void note()
	 * ���ô˷������Կ�������ʼ�ģ��
	 */
	public void note(){
		
		NoteBook.note();
	}
	
	/*
	 * ת����ҳ�ķ���
	 */
	public void doFirst(){
		//��ת����0ҳ
		gotoPage(0);
	}
	
	/*
	 * �鿴��һҳ�ķ���
	 */
	public void doNext(){
		//�鿴��ҳ������ǰҳ���һ
		gotoPage(curpage + 1);
		
	}

	/*
	 * �鿴ǰһҳ�ķ���
	 */
	
	public void doPrev(){
		//�鿴ǰһҳ������ǰҳ���1
		gotoPage(curpage -1);
	}
	
	/*
	 * ת��ĩҳ�ķ���
	 */
	
	public void doLast(){
		//��ת�����һ��ҳ��
		//�÷����õ��ļ�����ҳ��
		
		gotoPage(pdffile.getNumPages()-1);
	}
	
	/*
	 * ȫ���Ķ��ķ���
	 */
	
	public void doFullScreen(boolean force){
		//���������������ȫ���Ķ�
		
		setFullScreenMode(fullScreen == null, force);
	}
	
	/*
	 * �����������ķ���
	 * ������λ�ڽ����Ϸ�
	 * ��������Ÿ������ܰ�ť��
	 * ʵ��ҳ��ѡ��ȵȹ���
	 * ����һ��JToolBar����
	 * 
	 * 2012.12.1 debug
	 * @author ����
	 * student number 11061105
	 */
	
	private JToolBar CreateToolBar(){
		//toolbar�����շ���Ҫ���صĶ���
		JToolBar toolBar = new JToolBar();
		//���ù�������λ�ô�С
		toolBar.setBounds(0,23,800,40);
		//���ö�����������λ��
		//���������ܱ��ƶ�
		toolBar.setFloatable(false);
		//���ñ߽�
		toolBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		/*
		 * �����ת��ҳ�İ�ť
		 */
		firstButton = new JButton(firstAction);
		//���÷�װ�ķ�����ʼ����ť
		Buttoninit.init(firstButton, "ת����ҳ", "first.gif");
		
		//����ť���뵽��������
		toolBar.add(firstButton);
		
		/*
		 * ��Ӳ鿴��ҳ�İ�ť
		 */
		nextButton = new JButton(nextAction);
		//���÷�װ�ķ�����ʼ����ť
		Buttoninit.init(nextButton, "��һҳ", "next.gif");
		
		//((Object) nextButton).setHideActionText(true);
		//����ť���뵽��������
		toolBar.add(nextButton);
		
		/*
		 * ��Ӳ鿴��һҳ�İ�ť
		 */
		backButton = new JButton(prevAction);
		//���÷�װ�ķ�����ʼ����ť
		Buttoninit.init(backButton, "��һҳ", "back.gif");
		
		//((Object) backButton).setHideActionText(true);
		//����ť���뵽��������
		toolBar.add(backButton);
		
		/*
		 * ���ת��βҳ�İ�ť
		 */
		
		lastButton = new JButton(lastAction);
		//���÷�װ�ķ�����ʼ����ť
		Buttoninit.init(lastButton, "ת��βҳ", "last.gif");
		//����ť���뵽��������
		toolBar.add(lastButton);
		/*
		 * ����������дҳ����ı���
		 */
		//��ʼ�ı�Ϊ��
		pageField = new JTextField("", 1);
		//�ı�������Ϊ�ɱ༭��
		pageField.setEditable(true);
		//�����ı���߽�
		pageField.setBounds(0,0,500,10);
		//��Ӽ�����
		pageField.addKeyListener(this);
		//��Ӧ�û��¼�
		pageField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//�Ƚ�Ҫת����ҳ���ʼ��Ϊ-1
				int pagenum = -1;
				try{
					//��ҳ�������û������ҳ��
					pagenum = Integer.parseInt(pageField.getText())-1;
				}catch(NumberFormatException nfe){
					
				}
				//�������ҳ�볬���ļ���ҳ�뷶Χ����ת�����һҳ
				if (pagenum >= pdffile.getNumPages()){
					pagenum = pdffile.getNumPages()-1;
				}
				//����ҳ������ǺϷ���������ת
				if (pagenum>=0){
					//�������ҳ�벻Ϊ��ǰҳ������ת
					if (pagenum != curpage ){
						gotoPage(pagenum);
					}
				}
				//���ҳ���������ǷǷ�ҳ�룬���ı�����ʾ��ǰҳ�룬��ִ����ת
				else {
					pageField.setText(String.valueOf(curpage));
				}
			}
		});
		//��ҳ�����ӽ�������
		toolBar.add(pageField);
		jl = new JLabel();
		toolBar.add(jl);
		
		//��ӷָ���
		//����ҳ������ť����
		//����Ϊ��һ�鹦�ܰ�ť
		toolBar.addSeparator();
		//Ϊ�Ŵ�ť��Ӵ������¼�
		//��Ӧ���¼���ǰ�涨���
		bigButton = new JButton(BigAction);
		//��ʼ����ť
		Buttoninit.init(bigButton,"ҳ��Ŵ�","big.gif");
		//��Ӱ�ť��������
		toolBar.add(bigButton);
		
		//Ϊ��С��ť��Ӵ����¼�
		smallButton = new JButton(SmallAction);
		//��ʼ����ť
		Buttoninit.init(smallButton, "ҳ����С", "small.gif");
		//��Ӱ�ť��������
		toolBar.add(smallButton);
		
		//��ӷָ���
		//����ҳ��Ŵ���С���ܽ���
		toolBar.addSeparator();
		
		/*
		 * ����Ϊ��һ�鹦�ܰ�ť
		 */
		
		fullScreenButton = new JButton(fullScreenAction);
		//��ʼ����ť
		Buttoninit.init(fullScreenButton, "ȫ���Ķ�", "fullScreen.gif");
		toolBar.add(fullScreenButton);
		
		/*
		 * �����ʼǰ�ť
		 */
		//Ϊ��ť��Ӵ������¼�
		noteButton = new JButton(noteAction);
		//��ʼ����ť
		Buttoninit.init(noteButton, "����ʼ�", "03.gif");
		//��ӵ���������
		toolBar.add(noteButton);
		//���ش����õĹ�����
		return toolBar;
	}
	
	/*
	 * ���һ���ļ�������
	 * ��ѡ���ļ�ʱֻ��ʾ�ļ��к�pdf�ļ�
	 * �����û�ʹ��
	 * 2012.12.2
	 * @author ����
	 * student number 11061105
	 */
	
	FileFilter pdfFilter = new FileFilter(){
		//�趨��ʾ���ļ�����
		public boolean accept(File f){
			//���ļ��л�����pdf�ļ�����ʾ
			return  f.isDirectory()||f.getName().endsWith(".pdf");
		}
		
		//�������������
		public String getDescription(){
			return "ѡ��һ��pdf�ļ�";
		}
	};
	
		//���ڴ��ļ��ķ���
	/*
	 * ���ļ��İ취�ο��ٶ�
	 * �ļ��ӿڵ���ͷ������ο�����
	 */
		public  void doOpen(){
			try{
				//����Ѿ������ļ����ȹر�ԭ�����ļ�
				if(jp != null){
					doClose();
				}
				//����ѡ���ļ��Ķ���
				JFileChooser fc = new JFileChooser();
				//��Ĭ�ϵ�����򿪵��ļ���
				fc.setCurrentDirectory(prevDirChoice);
				//��֧��ͬʱѡ�����ļ�
				//��Ϊһ��ֻ�ܴ�һ���ļ�
				fc.setMultiSelectionEnabled(false);
				//����ļ�������
				//ֻ��ʾpdf�ļ�
				fc.setFileFilter(pdfFilter);
				//����ѡ���ļ��Ի���
				int returnVal = fc.showOpenDialog(this);
				//���ѡ����ļ��ǺϷ���
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try{
						//��������򿪵�Ŀ¼
						prevDirChoice = fc.getSelectedFile();
						RandomAccessFile raf = new RandomAccessFile(prevDirChoice.getAbsolutePath(),"r");
						//�ҵ��ļ��Ľӿ�
						FileChannel channel = raf.getChannel();
						ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 
								0, channel.size());
						try{
							pdffile = new PDFFile(buf);
						}catch(IOException ioe){
							//���ļ�������֮�䷵�ؽ�������
							return;
						}
						docName = prevDirChoice.getName();
						//���Ķ�����������Ϊ����ȡ���ļ���
						setTitle(docName);
						activityMonitor = new Timer(500, new ActionListener(){
							//�����¼��ķ���
							public void actionPerformed(ActionEvent arg0){
								int current = activity.getCurrent();
								contentPanel.getVerticalScrollBar().setValue(current);
								if(current == activity.getTarget()){
									activityMonitor.stop();
								}
							}
						});
						for(int i =1; i<10;i++){
							contentPanel.setViewportView(jpmain);
							activity = new SimulateActivity(contentPanel
									.getVerticalScrollBar().getMaximum());
							new Thread(activity).start();
							activityMonitor.start();
							PDFPage page = pdffile.getPage(i);
							PagePanel jp2 = new PagePanel();
							jp2.addMouseListener(new JPMouseAction());
							jp2.addMouseMotionListener(new JPMouseMotionAction());
							jpmain.add(jp2);
							validate();
							jp2.showPage(page);
						}
						thumbs = new ThumbPanel(pdffile);
						//��ҳ������
						thumbs.addPageChangeListener(this);
						thumbscroll.getViewport().setView(thumbs);
						//���ñ�����ɫ
						thumbscroll.getViewport().setBackground(Color.gray);
						tabbedPane.setComponentAt(0, thumbscroll);
						try{
							//��ȡ�����ļ��Ĵ��
							outline = pdffile.getOutline();
						}catch(IOException ioe){
							if (outline != null){
								if(outline.getChildCount()>0){
									JTree jt = new JTree(outline);
									jt.setRootVisible(false);
									jt.addTreeSelectionListener(this);
									JScrollPane jsp = new JScrollPane(jt);
									tabbedPane.setComponentAt(1,jsp);
								}
							}
						}
					}catch(Exception ioe){
						ioe.printStackTrace();
					}
					//��ӽ��������
					getContentPane().add(tabbedPane);
					//���ñ߽緶Χ
					tabbedPane.setBounds(0,60,230,607);
					validate();
					jl.setText(GetPage.showPage(curpage, pdffile));
					/*
					 * ���ļ���ʹ���а�ť����
					 */
					Buttonactivator.activator(backButton);
					Buttonactivator.activator(nextButton);
					Buttonactivator.activator(bigButton);
					Buttonactivator.activator(smallButton);
					Buttonactivator.activator(lastButton);
					Buttonactivator.activator(firstButton);
					Buttonactivator.activator(fullScreenButton);
					Buttonactivator.activator(noteButton);
					
					//����ҳ����д����Ϊ����
					pageField.setEditable(true);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		public void gotoPage(int pagenum){
			pagenum = Pagecontroller.checkPage(pagenum, pdffile);
			forceGotoPage(pagenum);
		}
		
		/*
		 * ����ҳ����ת
		 * ��������Ӧ����
		 */
		public void forceGotoPage(int pagenum){
			pagenum = Pagecontroller.checkPage(pagenum, pdffile);
			//����ǰҳ�����ΪҪ��ת��ҳ
			curpage = pagenum;
			
			//����ҳ���ı���
			pageField.setText(String.valueOf(curpage + 1));
			jl.setText(GetPage.showPage(curpage, pdffile));
			
			//��ȡ��ת��ҳ������
			PDFPage pg = pdffile.getPage(pagenum+1);
			//��������Ѿ������ݣ��������
			if(jpmain != null){
				jpmain.removeAll();
			}
			
			//��������
			jpmain.add(jp);
			validate();
			
			//����ȫ�����
			if(fspp != null){
				fspp.showPage(pg);
				//ȫ����ȫ�����Ϊ���뽹��
				fspp.requestFocus();
			}
			//�������ͨ���Ļ�
			else {
				jp.showPage(pg);
				//��ͨ��������뽹��
				jp.requestFocus();
			}
			
			//����thumb���
			thumbs.pageShown(pagenum);
		}
		
		/*
		 * ���ƹر��ļ��ķ���
		 * û�з�װ��Ϊ��Ҫ�����漰mainframe��Ķ���
		 * ��װ��ҪƵ��������
		 */
		
		public void doClose(){
			//�����������ȫ�����
			if (jp != null){
				jpmain.removeAll();
			}
			//��Ϊ����ֹͣ����Ϊ��
			if(thumbs!= null){
				thumbs.stop();
			}
			thumbs = new ThumbPanel(null);
			thumbscroll.getViewport().setView(thumbs);
			pdffile = null;
		}
		
		/*
		 * ���´���pdf�ļ��ĺܶ෽���ο�������
		 */
		/*
		 * ҳ�����÷���
		 * ���õķ�����װ��fileoperator��
		 */
		public void doPageSetup(){
			Fileoperator.setup(pformat);
		}
		
		/*
		 * ��ӡ�ķ���
		 * �����Ľӿ��÷��ο������ϣ�����Book�ࣩ
		 */
		
		public void doPrint(){
			PrinterJob pjob = PrinterJob.getPrinterJob();
			//���Ҫ��ӡ���ļ����ǺϷ���
			if(docName != null && docName.length()!= 0){
				pjob.setJobName(docName);
				Book book = new Book();
				PDFPrintPage pages = new PDFPrintPage(pdffile);
				book.append(pages, pformat,pdffile.getNumPages());
				pjob.setPageable(book);
				if(pjob.printDialog()){
					new PrintThread(pages,pjob).start();
				}
				
			}
			//���û��ѡ��Ϸ��ļ���ӡ�����һ����ʾ�Ի���
			else JOptionPane.showMessageDialog(MainFrame.this, "��ѡ��PDF�ĵ����ӡ");
		}
		
		/*
		 * �˴���ν�������г���
		 */
		public void valueChanged(TreeSelectionEvent e){
			if(e.isAddedPath()){
				OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
				if(node == null){
					return;
				}
				
				try{
					PDFAction action = node.getAction();
					if(action == null){
						return;
					}
					if (action instanceof GoToAction){
						PDFDestination dest = ((GoToAction) action).getDestination();
						if (dest == null){
							return;
						}
						
						PDFObject page = dest.getPage();
						if(page == null){
							return;
						}
						
						int pageNum = pdffile.getPageNumber(page);
						if(pageNum >=0){
							gotoPage(pageNum);
						}
					}
				}catch (IOException ioe){
					ioe.printStackTrace();
				}
		}
		}
		//��Ӧ��궯����˽����
		private final class JPMouseMotionAction extends java.awt.event.MouseMotionAdapter{
			//��������Ϸ��¼�
			//���Ϸ��¼��в��ϼ���λ�ò��ı�λ��
			public void mouseDragged(java.awt.event.MouseEvent e){
				if (isDragged){
					//����������ק���򲻶ϸ���λ��
					loc = new Point (jp.getLocation().x + e.getX() - tmp.x,
							jp.getLocation().y+ e.getY() - tmp.y);
					jp.setLocation(loc);
				}
			}
		}
		/*
		 * ������������˽����
		 * ��������Ƿ��϶����
		 * ���������ʾ��״
		 * 2012.12.3
		 * @author ����
		 * student number 11061105
		 */
		
		private final class JPMouseAction extends java.awt.event.MouseAdapter{
			public void mouseReleased(java.awt.event.MouseEvent e){
				/*
				 * ����ͷŵ�״̬����ҳ���϶�״̬isDraggedΪfalse
				 * ��ҳ��û�б��϶�
				 * ͬʱ�����״Ϊ������״
				 */
				isDragged = false;
				//���������״
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mousePressed(java.awt.event.MouseEvent e){
				/*
				 * �������״̬����ҳ���϶�״̬isDraggedΪtrue
				 * ҳ�洦�ڱ��϶���״̬
				 * ��Ӧ�����᲻��ˢ�µ�ǰҳ��λ��
				 * ͬʱ�������״����Ϊ����
				 */
				
				//��¼����갴�µ�λ��
				tmp = new Point(e.getX(),e.getY());
				//������ק״̬Ϊtrue
				isDragged = true;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		
		/*
		 * ��ȫ����ͼ�ķ���
		 */
		
		public void setFullScreenMode(boolean full, boolean force){
			//full��¼����ȫ���Ƿ�Ϊ��
			//�����Ĳ������fullscreenΪ����fullΪtrue
			if(full && fullScreen == null){
				fullScreenAction.setEnabled(false);
				new Thread(new PerformFullScreenMode(force)).start();
				fullScreenButton.setSelected(true);
			}
			//���ԭ��ȫ�����������
			//�ر��������ȫ�����
			else if(!full && fullScreen != null){
				fullScreen.close();
				fspp = null;
				fullScreen = null;
				gotoPage(curpage);
				fullScreenButton.setSelected(false);
			}
		}
		
		/*
		 * ִ��ȫ����ͼ�ķ���
		 */
		
		class PerformFullScreenMode implements Runnable {
			boolean force;
			
			public PerformFullScreenMode(boolean forcechoice){
				force = forcechoice;
			}
			
			public void run(){
				fspp = new PagePanel();
				fspp.setBackground(Color.CYAN);
				jp.showPage(null);
				//ǳ��ɫ����
				fullScreen = new FullScreenWindow(fspp,force);
				fspp.addKeyListener(MainFrame.this);
				gotoPage(curpage);
				fullScreenAction.setEnabled(true);
			}
		}
		
		/*
		 * �����Ǵ�����̶����ķ���
		 */
		
		public void keyPressed(KeyEvent evt){
			//�жϼ����ý�����ʲô�ַ�
			//���ݼ�����ַ�������Ӧ����Ӧ
			int code = evt.getKeyCode();
			if (code == KeyEvent.VK_LEFT){
				doPrev();
			} else if(code == KeyEvent.VK_RIGHT) {
				doNext();
			} else if(code == KeyEvent.VK_UP){
				doPrev();
			} else if(code == KeyEvent.VK_DOWN){
				doNext();
			} else if(code == KeyEvent.VK_HOME){
				doFirst();
			} else if(code == KeyEvent.VK_END){
				doLast();
			} else if(code == KeyEvent.VK_PAGE_UP){
				doPrev();
			} else if(code == KeyEvent.VK_PAGE_DOWN){
				doNext();
			} else if(code == KeyEvent.VK_SPACE){
				doNext();
			} else if(code ==KeyEvent.VK_ESCAPE){
				//esc�����˳�ȫ����ͼ
				setFullScreenMode(false,false);
			}
		}
		
		/*
		 * ʵ�ֽӿڹ涨�ķ���
		 * û��ʵ������
		 */
		
		public void keyReleased(KeyEvent arg0){
			return;
		}
		
		public void keyTyped(KeyEvent evt){
			char key = evt.getKeyChar();
			if (key>='0'&&key<='9'){
				//���ַ�ת��Ϊ����
				int val = key -'0';
				pb.keyTyped(val);
			}
		}
		
		/*
		 * ���ڴ���ҳ��
		 * �ο����ϴ���
		 */
		public class PageBuilder implements Runnable{
			int value =0;
			long timeout;
			Thread anim;
			static final long TIMEOUT = 500;
			
			// add the digit to the page number and start the timeout thread 
			
			public synchronized void keyTyped(int keyval){
				value = value*10 + keyval;
				timeout = System.currentTimeMillis()+TIMEOUT;
				if (anim == null){
					anim = new Thread(this);
					anim.start();
				}
			}
			
			/*
			 * waits for the timeout, and if time expires, go to the specified
			 * page number
			 */
			
			public void run(){
				long now,then;
				synchronized(this){
					now = System.currentTimeMillis();
					then = timeout;
				}
				
				while(now<then){
					try{
						Thread.sleep(timeout - now);
					}catch(InterruptedException ie){
						
					}
					synchronized(this){
						now = System.currentTimeMillis();
						then = timeout;
					}
				}
				synchronized(this){
					gotoPage(value - 1);
					anim = null;
					value = 0;
				}
			}

		}


	
	/*
	 * ����������
	 */
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

	

}
