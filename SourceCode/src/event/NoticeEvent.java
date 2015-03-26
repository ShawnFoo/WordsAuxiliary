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
	 * 面板加载数据
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
	 * 把list中的task添加到JPanel上
	 * @param target   list列表
	 * @param jp       Jpanel
	 */
	private void addTaskToJPanel(List<Task> target,JPanel jp){
		if(target.size() == 0){//今日任务为空
			jp.add(new JLabel("空"));
		}else{
			jp.setLayout(new GridLayout(target.size(),1));
			for (Task task : target) {
				jp.add(new JLabel(combineTask(task)));
			}
		}
	}
	
	/**
	 * 将task对象中的属性组合成JLabel显示的Text
	 * @param task   task  对象
	 * @return     Text
	 */
	private String combineTask(Task task){
		StringBuffer sb = new StringBuffer();
		sb.append(task.getDescription()+"  --上次学习:");
		sb.append(task.getLastTime()+"  --已复习: ");
		sb.append(task.getSum());
		return sb.toString();
	}
	
	/**
	 * 加载文件看是否关闭了启动时每日任务提示功能
	 * @return  true 提醒    false不提醒    
	 */
	public boolean isAutoStart(){
        File file = new File(filePath);
        if(file.exists())//判断文件是否存在
        	return false;
        else
        	return true;           
	}
	
	/**
	 * 设置启动每日提醒功能
	 * @param isOpen   true不提醒  false提醒
	 */
	public void setNotAutoStart(boolean isOpen){
		File file = new File(filePath);		
		try {
			if(isOpen){
				File directory = new File(cachePath);
	            if(!directory.exists())//不存在目录则创建
	            	directory.mkdir();	            
				if(!file.exists())//不存在则创建文件
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
	 * 删除已完成的任务
	 * @param tasks  所有任务列表
	 * @param list   Jlabel列表
	 * @param taskList    列表所在Jpanel
	 * @param finish     已完成的任务列表
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
