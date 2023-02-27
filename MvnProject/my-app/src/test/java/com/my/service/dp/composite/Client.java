package com.my.service.dp.composite;

public class Client {

  public static void main(String[] args) {
    File root = createTreeOne();
    root.ls();

    File root2 = createTreeTwo();
    root2.ls();
  }

  private static File createTreeOne() {
    File file = new BinaryFile("file0", 100);
    Directory directory = new Directory("dir1");
    directory.addFile(file);
    File file1 = new BinaryFile("file1", 100);
    File file2 = new BinaryFile("file2", 100);
    Directory directory1 = new Directory("dir2");
    directory1.addFile(file1);
    directory1.addFile(file2);
    directory1.addFile(directory);
    return directory1;
  }

  private static File createTreeTwo() {
    return new BinaryFile("File 3", 300);
  }
}
