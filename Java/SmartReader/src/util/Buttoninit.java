package util;

import javax.swing.JButton;

/*
 * ���ڳ�ʼ��Button���༰����
 * 
 */

public class Buttoninit {
	/*
	 * ��ʼ������
	 * ����Ҫ��ʼ����JButton
	 * ������ʾ����ʾ�ı�
	 * ����Ҫ���õ�ͼƬ����
	 */
	public static void init(JButton jb,String st,String pt){
		//��ʼ�����ð�ť������
		jb.setEnabled(false);
		jb.setToolTipText(st);
		jb.setIcon(CreatecdIcon.add(pt));
	}

}
