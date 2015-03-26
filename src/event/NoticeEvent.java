package event;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 
 */
public class NoticeEvent {
	final String sep = File.separator;	
	final String cachePath = System.getProperty("user.dir")+sep+"cache";
	final String filePath = System.getProperty("user.dir")+sep+"cache"+sep+"start.property";
	
	/**
	 * ����������
	 * @param toJp
	 * @param tmJp
	 * @param fiJp
	 * @param today
	 * @param tomorrow
	 * @param finish
	 */
	public void loadData(JPanel toJp,JPanel tmJp,JPanel fiJp,List<Task> today,
			List<Task> tomorrow,List<Task> finish){
		addTaskToJPanel(today, toJp);
		addTaskToJPanel(tomorrow, tmJp);
		addTaskToJPanel(finish, fiJp);
	}
	
	/**
	 * ��list�е�task��ӵ�JPanel��
	 * @param target   list�б�
	 * @param jp       Jpanel
	 */
	private void addTaskToJPanel(List<Task> target,JPanel jp){
		if(target.size() == 0){//��������Ϊ��
			jp.add(new JLabel("��"));
		}else{
			jp.setLayout(new GridLayout(target.size(),1));
			for (Task task : target) {
				jp.add(new JLabel(combineTask(task)));
			}
		}
	}
	
	/**
	 * ��task�����е�������ϳ�JLabel��ʾ��Text
	 * @param task   task  ����
	 * @return     Text
	 */
	private String combineTask(Task task){
		StringBuffer sb = new StringBuffer();
		sb.append(task.getDescription()+"  --�ϴ�ѧϰ:");
		sb.append(task.getLastTime()+"  --�Ѹ�ϰ: ");
		sb.append(task.getSum());
		return sb.toString();
	}
	
	/**
	 * �����ļ����Ƿ�ر�������ʱÿ��������ʾ����
	 * @return  true ����    false������    
	 */
	public boolean isAutoStart(){
        File file = new File(filePath);
        if(file.exists())//�ж��ļ��Ƿ����
        	return false;
        else
        	return true;           
	}
	
	/**
	 * ��������ÿ�����ѹ���
	 * @param isOpen   true������  false����
	 */
	public void setNotAutoStart(boolean isOpen){
		File file = new File(filePath);		
		try {
			if(isOpen){
				File directory = new File(cachePath);
	            if(!directory.exists())//������Ŀ¼�򴴽�
	            	directory.mkdir();	            
				if(!file.exists())//�������򴴽��ļ�
					file.createNewFile();
			}else{
				if(file.exists())
					file.delete();
			}							
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * ɾ������ɵ�����
	 * @param tasks  ���������б�
	 * @param list   Jlabel�б�
	 * @param taskList    �б�����Jpanel
	 * @param finish     ����ɵ������б�
	 */
	public void deleteFinish(List<Task> tasks,List<JLabel> list,
			JPanel taskList,List<Task> finish){
		for (Task task : finish) {
			int index = tasks.indexOf(task);
			taskList.remove(list.get(index));
			list.remove(index);			
			tasks.remove(task);
		}
		taskList.updateUI();
	}
}
