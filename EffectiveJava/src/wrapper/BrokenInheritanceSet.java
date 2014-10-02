package wrapper;

import java.util.Collection;
import java.util.HashSet;

public class BrokenInheritanceSet<E> extends HashSet<E> implements AddCounterSet<E> {
	private static final long serialVersionUID = -3051571411170976824L;
	private int addCount;

	public BrokenInheritanceSet() {
		
	}
	
	public BrokenInheritanceSet(int initCap, float loadFactor) {
		super(initCap, loadFactor);
	}
	
	@Override
	public boolean add(E e) {
		this.addCount++;
		return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		this.addCount += c.size();
		return super.addAll(c);
	}
	
	public int getAddCount() {
		return this.addCount;
	}
}
