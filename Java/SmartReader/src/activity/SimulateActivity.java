package activity;

import java.util.concurrent.TimeUnit;

/*
 *���ڵ����ļ���ȡ��������
 * 2012.12.4
 * @author ����
 * student number 11061105
 */
public class SimulateActivity implements Runnable{
	private volatile int current;
	
	private int target;
	//���췽��
	public SimulateActivity(int t){
		current = 0;
		target = t;
	}
	
	public int getTarget(){
		return target;
	}
	
	public int getCurrent(){
		return current;
	}
	
	public void run(){
		try{
			while(current<target){
//				Thread.sleep(100);
				TimeUnit.MILLISECONDS.sleep(100);
				current++;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
