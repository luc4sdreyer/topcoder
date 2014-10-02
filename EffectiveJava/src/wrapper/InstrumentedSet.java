package wrapper;

import java.util.Collection;
import java.util.Set;

/**
 * @author luc4s_000
 *
 * @param <E>
 */
public class InstrumentedSet<E> extends ForwardingSet<E> {
	private int addCount; 
	
	public InstrumentedSet(Set<E> set) {
		super(set);
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
