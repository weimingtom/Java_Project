package util;

import com.sun.pdfview.PDFFile;

/*
 * ��װ����ҳ���ķ���
 * ��ֹ���Ϸ�ҳ���ĳ���
 * 
 * 2012.12.3
 * @author ����
 * student number 11061105
 */
public class Pagecontroller {
	public static int checkPage(int pagenum, PDFFile pdffile){
		int rst;
		if(pagenum<0){
			pagenum = 0;
		}
		else if (pagenum >= pdffile.getNumPages()){
			pagenum = pdffile.getNumPages()-1;			
		}
		rst = pagenum;
		return rst;
	}
	
	

}
