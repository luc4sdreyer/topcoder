package inheritance;

public class Rectangle {
	private int height;
	private int width;
	
	Rectangle(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Rectangle)) {
			return false;
		}
		Rectangle objRect = (Rectangle)obj;
		if (objRect.height != this.height) {
			return false;
		}
		if (objRect.width != this.width) {
			return false;
		}
		return true;
	}
	
	
}
