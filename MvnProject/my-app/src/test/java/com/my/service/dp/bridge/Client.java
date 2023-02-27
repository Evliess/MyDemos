package com.my.service.dp.bridge;


// java.sql.DriverManager class with the java.sql.Driver interface from a bridge pattern.
public class Client {
  public static void main(String[] args) {
    FifoCollection<Integer> collection = new Queue<>(new SinglyLinkedList<>());
    collection.offer(10);

    collection.offer(20);
    collection.offer(40);

    System.out.println(collection.poll());
    System.out.println(collection.poll());
    System.out.println(collection.poll());
  }
}
