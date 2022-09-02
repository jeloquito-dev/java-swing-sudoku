package Interface;

@FunctionalInterface
public interface IdentifierInterface<T> {

    T identify(T q1, T q2, T q3, int value);
}
