package event;

import java.util.List;

import model.Task;

/**
 * 
 * @author ShawnFoo
 * @version  2.0          2014.7.24
 *
 */
public class AddEvent {
	/**
	 * �ж��Ƿ����ͬ������
	 * @param tasks  �����б�
	 * @param description   ������/����
	 * @return  true����ͬ��   false������
	 */
	public boolean isExisted(List<Task> tasks,String description){
		for (Task task : tasks) {
			if(task.getDescription().equals(description))
				return true;
		}
		return false;
	}
}
