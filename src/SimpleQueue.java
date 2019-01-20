public class SimpleQueue<T> {

    Node<T> head;
    Node<T> tail;

    public SimpleQueue() {
        head = null;
        tail = null;
    }

    public void offer(T item) {
        if (tail == null) {
            head = new Node<>(item, null);
            tail = head;
        } else {
            tail.setNext(new Node<>(item, null));
            tail = tail.getNext();
        }
    }

    public T poll() {
        if (head == null) {
            return null;
        } else {
            T temp = head.getItem();
            head = head.getNext();
            return temp;
        }
    }

    public T peek() {
        if (head == null) {
            return null;
        } else {
            return head.getItem();
        }
    }

    public void clear() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

}
