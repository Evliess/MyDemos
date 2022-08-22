package com.my.service.dp.bridge;

import com.my.service.dp.objectpool.ObjectPool;

public class SinglyLinkedList<T> implements LinkedList<T> {

  private class Node {
    private Object data;
    private Node next;

    private Node(Object data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

  private int size;
  private Node top;
  private Node last;


  @Override
  public void addFirst(T element) {
    if (top == null) {
      last = top = new Node(element, null);
    } else {
      top = new Node(element, top);
    }
    size++;
  }

  @Override
  public T removeFirst() {
    if (top == null) {
      return null;
    }
    T temp = (T) top.data;
    if (top.next != null) {
      top = top.next;
    } else {
      top = last = null;
    }
    size--;
    return temp;
  }

  @Override
  public void addLast(T element) {
    if (top == null) {
      last = new Node(element, null);
      top = last;
    } else {
      last.next = new Node(element, null);
      last = last.next;
    }
    size++;

  }

  @Override
  public T removeLast() {
    return null;
  }

  @Override
  public int getSize() {
    return 0;
  }
}
