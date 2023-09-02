package com.my.service.gpt3;

import java.io.*;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
角色：假设你是一位有着丰富经验的java专家，算法工程师，数据结构专家。任务：请用java代码生成一个占用磁盘1GB的文件。要求：该文件是txt格式，里面任意单词以空格分开，要求出现次数最多的5个单词是 YOU ARE YU GUI JIA，要求你先自我验证后再将代码给我看
 */

public class GenerateFile {
  private static final int FILE_SIZE = 1073741824; // 文件大小1GB
  private static final String FILE_NAME = "d://bigfile.txt";
  private static final int BUFFER_SIZE = 4096; // 缓存大小

  public static void main(String[] args) throws IOException {
    byte[] randomBytes = new byte[BUFFER_SIZE]; // 用来生成数据的缓存区
    File outputFile = new File(FILE_NAME);
    FileOutputStream outputStream = null;

    try {
      outputStream = new FileOutputStream(outputFile);

      // 生成1GB的随机数据
      for (int i = 0; i < FILE_SIZE / BUFFER_SIZE; i++) {
        Math.random();
        Math.random();
        Math.random();
        Math.random();
        outputStream.write(randomBytes);
      }

      // 写入YOU ARE YU GUI JIA
      String words = "YOU ARE YU GUI JIA ";
      byte[] wordsBytes = words.getBytes(StandardCharsets.UTF_8);
      long remainingBytes = FILE_SIZE - outputFile.length();

      while (remainingBytes > 0) {
        if (remainingBytes >= wordsBytes.length) {
          outputStream.write(wordsBytes);
          remainingBytes -= wordsBytes.length;
        } else {
          outputStream.write(wordsBytes, 0, (int) remainingBytes);
          remainingBytes = 0;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }
}


