package event;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import form.NoticeForm;
import model.IsOpen;
import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 1.�ļ��е���Ϣ��ʽΪ��
 * ��ͬ����֮����'^'�ָ���������������������������ʼʱ�䡢�Ѹ�ϰ���������
 * ��'$'�ָ�
 * 2.������Ϣ�ļ�Ŀ¼�ڵ�һ�α���ʱ���Զ�������
 * C:\Users\Administrator\reciteWords��
 */
public class MainEvent {
	final String sep = File.separator;	
	final String cachePath = System.getProperty("user.dir")+sep+"cache";
	final String filePath = System.getProperty("user.dir")+sep+"cache"+sep+"list.txt";
	
	/**
	 * ���ļ��м�������
	 * @return    ������Ϣ
	 */
	public String loadTask(){
		try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){//�ж��ļ��Ƿ����
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//���ǵ������ʽ
                BufferedReader br = new BufferedReader(read);
                String tasks = br.readLine();                
                read.close();
                if(tasks == null)//�ļ�Ϊ��
                	return "0";
                return tasks;
		    }else{//����ļ�������
		        return "0";
		    }
	    } catch (Exception e) {
	        System.out.println("��ȡ�ļ����ݳ���");
	        e.printStackTrace();
	        return "0";
	    }
	}
		
	/**
	 * �����ļ���Ϣ�������ɶ�Ӧ�б�
	 * @param tasks   ������Ϣ
	 * @return	�����б�
	 */
	public void getTasksInfo(String taskInfo,List<Task> tasks,final List<JLabel> list){
		String each[] = taskInfo.split("&");		
		for(int i = 0;i < each.length;i++){
			String items[] = each[i].split("/");			
			String one = "";			
			for(int j = 0;j < items.length;j++){
				if(j < 2){
					one += items[j] + "  ----  ";
				}else{
					if(items[j].equals("6"))
						one += "�����";
					else
						one += items[j];
				}					
			}			
			//Jlabel��ʼ��������������ɫ
			final JLabel jb = new JLabel(one);			
			jb.setForeground(Color.BLACK);
			
			//�����¼������ģ���AddForm��ߵ��¼�ҲҪ���ģ��豣��һ��
			jb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//�����JLabelʱ	
					if(jb.getForeground() == Color.BLACK || jb.getForeground().equals(Color.BLUE))
						jb.setForeground(Color.red);
					else
						jb.setForeground(Color.BLACK);			
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					//�����JLabel�Ϸ�ʱ
//					jb.setFont(new Font("Dialog",Font.BOLD,12));
					if(jb.getForeground().equals(Color.red))
						return;//��ѡ���򲻱�ɫ
					jb.setForeground(Color.BLUE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					//����뿪JLabel�Ϸ�ʱ
//					jb.setFont(new Font("Dialog",Font.BOLD,12));
					if(jb.getForeground().equals(Color.red))
						return;//��ѡ���򲻱�ɫ
					jb.setForeground(Color.BLACK);
				}
			});			
			list.add(jb);
			Task t = new Task();
			t.setDescription(items[0]);
			t.setLastTime(items[1]);
			t.setSum(Integer.valueOf(items[2]));
			tasks.add(t);
		}
	}
	
	/**
	 * ��������Ϣд���ļ��б���
	 * @param tasks  �����б�
	 */
	public void saveTask(List<Task> tasks){		
        try {
        	File file = new File(filePath);
            if(!file.exists()){//�������򴴽�
            	if(tasks.size() == 0)
        			return;
            	File directory = new File(cachePath);
	            if(!directory.exists())//������Ŀ¼�򴴽�
	            	directory.mkdir();	
            	file.createNewFile();
            }else{//������ڣ����жϵ�ǰ�Ƿ�������û����ɾ�����ļ�
        		if(tasks.size() == 0){
        			file.delete();
        			return;
        		}	
            }       				
    		//����д������
    		StringBuffer content = new StringBuffer();
    		for (Task task : tasks) {
    			content.append(task.getDescription()+"/"+task.getLastTime()
    						+"/"+task.getSum()+"&");
    		}
            
            //�������
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter output = new BufferedWriter(write);
            output.write(content.toString());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	/**
	 * ɾ������
	 * @param tasks ������Ϣ
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void deleteTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//ɾ��ѡ����
			if(list.get(i).getForeground() == Color.red){				
				taskList.remove(list.get(i));
				tasks.remove(i);	
				list.remove(i);
				i--;
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * ����1�θ�ϰ����,�������ϴ�ѧϰ����
	 * @param tasks ������Ϣ
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void reviewTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//��ѡ��������1�θ�ϰ����
			if(list.get(i).getForeground() == Color.red){				
				Task t = tasks.get(i);
				int lastSum = t.getSum();
				if(lastSum == 5){//5�ξ��Ѿ����������					
					return;
				}
				//���¸�ϰ������ѧϰ����				
				t.setSum(lastSum+1);//��ϰ������1
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());	
				t.setLastTime(time);//����ѧϰ����
				//ƴ���µ�labelText
				String LabelText = t.getDescription()+"  ----  "
						+t.getLastTime()+"  ----  ";				
				
				//�����������
				if(lastSum == 4)
					list.get(i).setText(LabelText+"�����");
				else
					list.get(i).setText(LabelText+(lastSum+1));
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * ����1�θ�ϰ����,�������ϴ�ѧϰ����
	 * @param tasks ������Ϣ
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void revokeTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//��ѡ�������1�θ�ϰ����
			if(list.get(i).getForeground() == Color.red){				
				Task t = tasks.get(i);
				int lastSum = t.getSum();
				if(lastSum == 0){					
					return;
				}
				//���¸�ϰ������ѧϰ����				
				t.setSum(lastSum-1);//��ϰ����-1
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());	
				t.setLastTime(time);//����ѧϰ����
				//ƴ���µ�labelText
				String LabelText = t.getDescription()+"  ----  "
						+t.getLastTime()+"  ----  ";			
				list.get(i).setText(LabelText+(lastSum-1));
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * ȡ��ѡ����
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void cancelSelect(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.BLACK);
		}
		taskList.updateUI();
	}
	
	/**
	 * ѡ��ȫ��
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void selectAll(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.red);
		}
		taskList.updateUI();
	}
	
	/**
	 * ѡ��ȫ��
	 * @param list  �����
	 * @param taskList  �����б����
	 */
	public void selectNull(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.BLACK);
		}
		taskList.updateUI();
	}
	
	/**
	 * ������Ҫ��ϰ����
	 * @param list   jlabel�б�
	 * @param taskList   �����б��������(JPanel)
	 * @param tasks   �ܵ������б�
	 * @param today   ���ո�ϰ�����б�
	 * @param tomorrow   ���ո�ϰ�����б�
	 */
	public void getNeedToReview(List<JLabel> list,JPanel taskList,
			List<Task> tasks,List<Task> today,List<Task> tomorrow,IsOpen isOpen){
		//����� today tomorrow  finish�б�
		today.clear();
		tomorrow.clear();		
		//int reviewGap[] = {1,2,4,7,15,25};
		int reviewGap[] = {1,1,2,3,8};//��ϰʱ����
		
		List<Task> finish = new ArrayList<Task>();//��ɵ������б�
		//�������ڸ�ʽ
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE,1);//��������������һ��.����������,������ǰ�ƶ�
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String tmTime = df.format(calendar.getTime());//��������
		String toTime = df.format(new Date());//��������
		try {
			Date toDate = df.parse(toTime);
			Date tmDate = df.parse(tmTime);
			getReview(toDate, reviewGap, tasks, today, finish);	
			getReview(tmDate, reviewGap, tasks, tomorrow, null);
			//���������б��кͽ��������б��е��ظ���ɾ��
			for (int i = 0;i < tomorrow.size();i++) {
				for (Task to : today) {
					if(tomorrow.get(i).equals(to)){
						tomorrow.remove(i);//�����ظ�����ɾ��
						i--;
						break;
					}
				}
			}
			new NoticeForm(tasks,list,taskList,today, tomorrow, finish,isOpen);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	/**
	 * �ж�ĳ�������Ƿ���Ҫ��ϰ,�����㷨
	 * @param d1    ���ջ���������
	 * @param gap  ��ϰ���Ѽ������
	 * @param tasks   �����б�
	 * @param TList   �����б�
	 * @param finish   ����ɵ������б�
	 * @throws ParseException   ת������ʧ��
	 */	 
	private void getReview(Date d1,int[] gap,List<Task> tasks,List<Task> TList,
			List<Task> finish) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long down = 1000*60*60*24;
		long daysGap = 0;
		Date temp = null;	
		for (Task task : tasks) {//�����ҳ���Ҫ��ϰ������
			if(task.getSum() > 4 && finish != null){//�����һ�μ������ߵ�����
				finish.add(task);   
				continue;
			}
			for(int i = 0;i < 5;i++){//��ϰ��������
				if(task.getSum() == i){
					temp = df.parse(task.getLastTime());
					if(d1.compareTo(temp) > 0){//���d1���ڴ���������ʼ����
						daysGap = d1.getTime() - temp.getTime();
						daysGap /= down;//�������
						if(daysGap >= gap[i])//��ֵ���ڻ���ڼ������ʱ����Ҫ��ϰ
							TList.add(task);
					}
					break;
				}											
			}
		}		
	}
}
