package dataStructures;

public interface BinaryOperation<E> {
	public E identity();
	public E function(E e1, E e2);
}