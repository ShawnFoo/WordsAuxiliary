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
	 * 判断是否存在同名任务
	 * @param tasks  任务列表
	 * @param description   任务名/描述
	 * @return  true存在同名   false不存在
	 */
	public boolean isExisted(List<Task> tasks,String description){
		for (Task task : tasks) {
			if(task.getDescription().equals(description))
				return true;
		}
		return false;
	}
}
