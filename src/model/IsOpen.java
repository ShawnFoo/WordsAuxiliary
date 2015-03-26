package model;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.8.1    2.1
 * 限制多开窗体，窗体数量为1
 * 
 */
public class IsOpen {
	private boolean open;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
