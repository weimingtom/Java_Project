package util;

import com.sun.pdfview.PDFFile;

/*
 * ��װ�����򹤾������ص�ǰҳ�뼰��ҳ��ķ���
 */
public class GetPage {
	public static String showPage (int curpage, PDFFile pdffile){
		String st = "("+(curpage +1) + "/" + pdffile.getNumPages()+")";
		return st;
	}

}
