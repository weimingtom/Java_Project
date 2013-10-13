import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
class DateTime{
 static Calendar today=Calendar.getInstance();
 static int getYear(){
   return today.get(today.YEAR);
     }
   static int getMonth(){
            return today.get(today.MONTH)+1;  //�����·ݵķ���
        }
   static int getDay(){
            return today.get(today.DATE);//�������ڵķ���
        }  
   static int getMonDay(int year,int month){
        int days=31;
        if(month==4||month==6||month==9||month==11)
            days=30;
        if(month==2)
            if((year%4==0&&year%100!=0)||year%400==0)//�����·ݵ�����
              days=29;
            else
              days=28;
       return days;
  }
}
class YearMonth extends JPanel{
 private int year;
 private int month;
 private JLabel y1;
 private JLabel m1;
    JSpinner showYear;
    JSpinner showMonth;
    YearMonth(){
     ymInit();
     add(y1);
     add(showYear);
        add(m1);//������ѡ�����
        add(showMonth);    
      }
   void ymInit(){
     y1=new JLabel("��");
     year=DateTime.getYear();
     showYear=new JSpinner(new SpinnerNumberModel(year,0,10000,1));
     showYear.setEditor(new JSpinner.NumberEditor(showYear,"0000"));
     showYear.setPreferredSize(new Dimension(60,30));
     y1.setFont(new Font("TimesRomn",Font.BOLD,16));
     m1=new JLabel("��");
     month=DateTime.getMonth();
     showMonth=new JSpinner(new SpinnerNumberModel(month,0,13,1));
     showMonth.setEditor(new JSpinner.NumberEditor(showMonth,"00"));
     showMonth.setPreferredSize(new Dimension(60,30));
     m1.setFont(new Font("TimesRomn",Font.BOLD,16));
     //�˴�������ѡ���΢�����������ж�Ӧ����
                           
   }
} 
class NotePane extends JPanel implements ActionListener{
    private int year,month,day;
    private JTextArea note;
    private JButton save,delete,export;
    private Hashtable table;
    private JLabel dateInfo;
    private File file;
    private JPanel buttonpane;
    NotePane(){
     super(new BorderLayout());
     noteInit();
     buttonInit();
     addEvent();
     add(dateInfo,"North");
     add(new JScrollPane(note));
        add(buttonpane,"South");    
    }
  private void noteInit(){
   year=DateTime.getYear();
   month=DateTime.getMonth();
   day=DateTime.getDay();
   //�滻������䣬������ɫ����������
   dateInfo=new JLabel(year+"��"+month+"��"+day+"��",JLabel.CENTER); 
   table=new Hashtable();
   file=new File("Note.txt");
   initFile();
   note=new JTextArea();
   note.setFont(new Font("",0,14));
   note.setLineWrap(true);
     }
  private void buttonInit(){
     save=new JButton("������־");
     delete=new JButton("ɾ����־");
     export=new JButton("������־");
     //����ť���뵽��ť��壬���ֱ���а�ť���ֵ���������
     buttonpane=new JPanel();
     buttonpane.add(save);
     buttonpane.add(delete);
     buttonpane.add(export);
    
    
   }
  private void addEvent(){
  	//��ť�¼�����
 save.addActionListener(this); 
    delete.addActionListener(this);
    export.addActionListener(this);
  
    }
  private void initFile(){
  if(!file.exists()){
   try{
    FileOutputStream out=new FileOutputStream(file);
    ObjectOutputStream  objectOut=new ObjectOutputStream(out);
    objectOut.writeObject(table);
    objectOut.close();
    out.close();
       }catch(IOException e){} 
       }
     }
  public void actionPerformed(ActionEvent e){
     if(e.getSource()==save)
           save(year,month,day);
     else if(e.getSource()==delete)
           delete(year,month,day);
     else
       export();
     } 
  public void setDateInfo(int y,int m,int d){
  	//�˴���������ı��������������Ϣ
        String s=y+"��"+m+"��"+d+"��";
         dateInfo.setText(s);  
        year=y;
        month=m;
        day=d;   
    } 
  public String getDateKey(){
    String s=""+year;
    if(month<10)s+="/0"+month;
    else s+="/"+month;
    if(day<10)s+="/0"+day;
    else s+="/"+day;
    return s;
   }
  public void refreshContent(int year,int month,int day ){
   String key=this.getDateKey();  
   try{
    FileInputStream in1=new FileInputStream(file);
    ObjectInputStream in2=new ObjectInputStream(in1);
    table=(Hashtable)in2.readObject();
    in1.close();
    in2.close();
   }catch(Exception ee){}
   if(table.containsKey(key))
    note.setText(table.get(key)+"");
   else
    note.setText("");
   }
  public void save(int year,int month,int day){
    String logContent=note.getText();
    String key=this.getDateKey();
    try{
     FileInputStream in1=new FileInputStream(file);
     ObjectInputStream in2=new ObjectInputStream(in1);
     table=(Hashtable)in2.readObject();
     in1.close();
     in2.close();
     table.put(key,logContent);
     FileOutputStream  out=new FileOutputStream(file);
     ObjectOutputStream ObjectOut=new ObjectOutputStream(out);
     ObjectOut.writeObject(table);
     ObjectOut.close();
     out.close();
       }catch(Exception ee){}
         String m=year+"��"+month+"��"+day+"�յ���־�Ѿ�����";//������־
     JOptionPane.showMessageDialog(this,m);
   }
  public void delete(int year,int month,int day){
        String key=this.getDateKey();    
   if(table.containsKey(key)){
    String m="Ҫɾ��"+year+"��"+month+"��"+day+"�յ���־��";
//    JOptionPane.showMessageDialogѯ�ʣ����û�ȷ����ɾ��ȷ�������յ���־
       if(JOptionPane.showConfirmDialog(this,m)==0){
               table.remove(key);
       try{
        FileOutputStream out=new FileOutputStream(file);
        ObjectOutputStream objectOut=new ObjectOutputStream(out);
        objectOut.writeObject(table);
              objectOut.close();
        out.close();
        note.setText("");
        }catch(Exception ee){}                        
                  }
   }else{
     String m=year+"��"+month+"��"+day+"��"+"�޼�¼";
     JOptionPane.showMessageDialog(this,m,"��ʾ",JOptionPane.WARNING_MESSAGE);
     }
   }
  public void export(){
  	//�������ռǵ�����һ���ı��ļ�
      File m;
    JFileChooser n=new JFileChooser();
     n.showSaveDialog(null);
     m=n.getSelectedFile();
  try{
         FileInputStream in1=new FileInputStream(file);
      ObjectInputStream in2=new ObjectInputStream(in1);
         table=(Hashtable) in2.readObject();
      in1.close();
      in2.close();
     }catch(Exception e){}
     TreeMap s=new TreeMap(table);
     Set maping=s.entrySet();
 if(m.exists()){
       m.delete();
    }
 else{
  try{  m.createNewFile();
      }catch(Exception e){}
    }
  for(Iterator i=maping.iterator();i.hasNext();)
   {
  Map.Entry me=(Map.Entry)i.next();
   try{
    FileOutputStream out=new FileOutputStream(m,true);
      BufferedOutputStream bout=new BufferedOutputStream(out);
      DataOutputStream dout=new DataOutputStream(bout);
      dout.writeUTF(me.getKey()+"\r\n\t"+me.getValue()+"\r\n");
      dout.close();
      }catch(Exception e){}
    }
   }
  }
  //����
class MonthPane extends JPanel{
 static JTextField showDay[];
 int first,days;
 YearMonth ym;
 String[] week;
 JLabel[] title;
 int year,month,day;
 MonthPane(){
  super(new GridLayout(7,7,3,3));
  mcInit();
  for(int i=0;i<7;i++)
        add(title[i]);
  for(int i=0;i<42;i++)
       add(showDay[i]);
     arrangeNum(year,month);
 }
void mcInit(){
 year=DateTime.getYear();
 //�˴����պ�һ���������DateTime�ķ�����ȡ������������������ʼ����
 month=DateTime.getMonth();
 day=DateTime.getDay();
 String week[]={"��","һ","��","��","��","��","��"};
 title=new JLabel[7];
 for(int j=0;j<7;j++){
  title[j]=new JLabel();
  title[j].setText(week[j]);
  title[j].setBorder(BorderFactory.createEmptyBorder());
  title[j].setFont(new Font("",1,18));
}
 title[0].setForeground(Color.red);
 title[6].setForeground(Color.blue);
 showDay=new JTextField[42];
 for(int i=0;i<42;i++){
  showDay[i]=new JTextField();
  showDay[i].setFont(new Font("",0,14));
  showDay[i].setEditable(false);
 }
}
public void arrangeNum(int year,int month){
	//һ���µ������������������ԣ��豻���
 days=DateTime.getMonDay(year,month);
 Calendar c=Calendar.getInstance();
 c.set(year,month-1,1);
 first=c.get(Calendar.DAY_OF_WEEK)-1;
 for(int i=first,n=1;i<first+days;i++,n++){
  showDay[i].setText(""+n);
  if(n==day){
   showDay[i].setForeground(Color.green);
   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
  }
  else{
   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
   showDay[i].setForeground(Color.black);
  }
 } 
 for(int i=days+first;i<42;i++)
         showDay[i].setText("");
    for(int i=first-1;i>=0;i--)
         showDay[i].setText("");  //showDay�������ı������Ϊ���ַ���
  }
}
class Motto extends JPanel{
  Motto(){
   JLabel L;
   L=new JLabel(new ImageIcon("1.jpg"));//����ͼƬ
      L.setOpaque(false); 
   add(L);
  }  
 }
class LeftPane extends JPanel{
  YearMonth ym;
  MonthPane mp;
  Motto mo;
  JPanel lp;
  JSplitPane split;
  LeftPane(){
   super(new BorderLayout());
   ym=new YearMonth();
   mp=new MonthPane();
   mo=new Motto();
   lp=new JPanel(new BorderLayout());
   lp.add(ym,"South");
   lp.add(mp);
   lp.add(mo,"North");
   split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
   this.add(lp);
   this.add(split,"East");
  }
 } 
class NoteBook extends JFrame implements ChangeListener,MouseListener{
 int year,month,day;
 Calendar c;
 int first,days;
 LeftPane lp;
 NotePane np;
public NoteBook(){
 super("�ҵ��������(Ver 3.0)");
 this.setDefaultCloseOperation(3);
 Container con=getContentPane();
 bookInit();
 addEvent();
 con.add(lp,"West");
 con.add(np);
 this.setBounds(150,250,630,400);
 this.setResizable(false);
 this.setVisible(true);
}  
void bookInit(){
 c=Calendar.getInstance();
 lp=new LeftPane();
 np=new NotePane();
 year=DateTime.getYear();
 //�˴��������DateTime�ķ�����ȡ��������������ʼ���ԣ��豻���
 month=DateTime.getMonth();
 day=DateTime.getDay();
}  
void addEvent(){
	//�˴�����Ӱ�����ڵ��¼�����(΢�����������ı���)
	//��ʾ΢��������ֵ�ı��¼�����ChangeEvent
    lp.ym.showMonth.addChangeListener(this);
    lp.ym.showYear.addChangeListener(this);
    for(int i=1;i<42;i++)
      MonthPane.showDay[i].addMouseListener(this);
 }  
public void stateChanged(ChangeEvent e){
	//΢�������¼�����
 int y=year;
    String m=lp.ym.showYear.getValue().toString();                   
 String n=lp.ym.showMonth.getValue().toString();
 year=Integer.parseInt(m);
 month=Integer.parseInt(n);
if(month>12){
  year=year+1;
  month=1;
  lp.ym.showMonth.setValue(new Integer(1));
     lp.ym.showYear.setValue(new Integer(y+1));
  }
else if(month<1){
   year=year-1;
   month=12;
   lp.ym.showMonth.setValue(new Integer(12));
   lp.ym.showYear.setValue(new Integer(y-1));
 } 
 lp.mp.arrangeNum(year,month);
 noteBookRefresh();   
}
void noteBookRefresh(){
	//�ꡢ�¡����κ�һ�����ݸı䣬����ˢ���ռǱ�(������������ʾ���ռ���ʾ)
   lp.mp.arrangeNum(year,month);
   np.setDateInfo(year,month,day);
   np.refreshContent(year,month,day);   
}
public void mouseClicked(MouseEvent e){
 JTextField source=(JTextField)e.getSource();
 try{
  day=Integer.parseInt(source.getText());
  noteBookRefresh();
 }
 catch(Exception ee){}
}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e){}
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}  
public static void main(String arg[]){
 new NoteBook();
 }
}