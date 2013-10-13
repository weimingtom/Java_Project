package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.FileChooser;
import org.crazyit.editor.IFrameListener;
import org.crazyit.editor.commons.EditFile;
import org.crazyit.editor.commons.WorkSpace;
import org.crazyit.editor.handler.run.JavaRunHandler;
import org.crazyit.editor.handler.save.SaveMediator;
import org.crazyit.editor.tree.TreeCreator;
public class EditorFrame extends JFrame {
	public EditorFrame(String title){
		super(title);
		pack();
	}
	private JTabbedPane tabPane;// ������
	private JDesktopPane desk;//�������Դ�źܶ���ĵ�������
	private Box box;//���ý���
	private JSplitPane editorSplitPane;//�ָ�༭�����ļ���
	private JScrollPane infoPane;
	private JTextArea infoArea;
	private JScrollPane treePane;
    private JSplitPane mainSplitPane;
	private JTree tree;
	private JMenuBar menuBar;
	private JMenu editMenu;
	private JMenu fileMenu;
	private JToolBar toolBar;
	private WorkSpace workSpace;
	private TreeCreator treeCreator;
	
	//������Ҫ���������֮������������ʼ����
	private AddFrame addFrame;
	
	//�ļ�ѡ����
	private FileChooser fileChooser;
	
	//��ǰ���ڱ༭���ļ�����
	private EditFile currentFile;
	
	//���ڼ�����
	private IFrameListener iframeListener;
	
	//���ļ��ļ���
	private List<EditFile> openFiles = new ArrayList<EditFile>();

	//�н��߶���
	private SaveMediator saveMediator;
	
	//����class�ļ��Ĵ�����
	private JavaRunHandler runHandler;
	public void initializeFrame (){//initialize the UI Frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
		desk = new JDesktopPane();
		box = new Box(BoxLayout.Y_AXIS);
		editorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, box, infoPane);
		infoArea = new JTextArea("", 5, 50);
		//������Ϣ��ʾ�����ı���
		infoPane = new JScrollPane(infoArea);
		//��infoArea�ı�����Ϊ����ŵ�infoPane��
		infoArea.setEditable(false);
		//��Ϣ�����ɱ༭
		desk.setBackground(Color.GRAY);
		//����desk�ı�����ɫΪ��ɫ
		box.add(tabPane);
		box.add(desk);
		//Ϊ���������Ӧ���
		editorSplitPane.setDividerSize(3);
		editorSplitPane.setDividerLocation(500);
		add(editorSplitPane);
		pack();//����JFrame��С��
	}

}
