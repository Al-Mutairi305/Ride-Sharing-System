package system;

// Generic LinkedList class that will act as a result list for methods that return a list.
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    public void insertAtBack(T data) {

        Node<T> newNode = new Node<T>(data);

        if (head == null) {
            head = tail = newNode;

        } else {
            tail.setNext(newNode);
            tail = tail.getNext();
        }

        size++;
    }

    public void insertAtFront(T data) {

        Node<T> newNode = new Node<T>(data);

        if (head == null) {
            head = tail = newNode;

        } else {
            newNode.setNext(head);
            head = newNode;
        }

        size++;
    }

    // Numbering starts at 1.
    public T remove(int i) throws IllegalArgumentException, NoSuchElementException {

        if (head == null)
            throw new IllegalArgumentException("List is empty!");

        if (i <= 0 || i > size) {
            throw new NoSuchElementException("Invalid index!");
        }
        T result = null;
        Node<T> previous = null;
        Node<T> current = head;

        if (i == 1) {
            result = head.getData();
            head = head.getNext();
            size--;
            return result;
        }

        for (int j = 1; j < i; j++) {
            previous = current;
            current = current.getNext();
        }

        result = current.getData();
        previous.setNext(current.getNext());

        if (i == size)
            tail = previous;
        size--;
        return result;
    }

    public void print() {

        Node<T> current = head;

        for (int i = 1; i <= size; i++) {
            System.out.println(i + ": " + current.getData());
            current = current.getNext();
        }
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }
}