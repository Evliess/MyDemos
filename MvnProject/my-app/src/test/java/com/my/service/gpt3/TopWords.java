package com.my.service.gpt3;


import java.io.*;
import java.util.*;

public class TopWords {
  private static final int BLOCK_SIZE = 2 * 1024 * 1024;  // 字节
  private static final int MAX_THREADS = 4; // 最大线程数
  private static final int TOP_N = 5; // 输出出现次数最多的前N个单词

  private static File tempDir;

  public static void main(String[] args) throws IOException {
    // 创建临时目录
    tempDir = new File(System.getProperty("java.io.tmpdir"), "FindMostFrequentWordsInFile");
    if (tempDir.exists()) {
      deleteTempFiles(tempDir); // 删除旧的临时文件
    } else {
      tempDir.mkdir();
    }

    // 打开文件并分块读取
    FileInputStream fileInputStream = new FileInputStream("d://bigfile.txt");
    byte[] buffer = new byte[BLOCK_SIZE];
    int bytesRead;
    int blockNumber = 0;
    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
      // 创建文件并保存块数据
      File blockFile = new File(tempDir, "block_" + blockNumber);
      blockNumber++;
      FileOutputStream fileOutputStream = new FileOutputStream(blockFile);
      fileOutputStream.write(Arrays.copyOf(buffer, bytesRead));
      fileOutputStream.close();

      // 创建任务并在线程池中执行
      Runnable task = new CountWordsInBlockTask(blockFile);
      new Thread(task).start();
    }
    fileInputStream.close();

    // 等待所有任务完成
    boolean allTasksCompleted = false;
    while (!allTasksCompleted) {
      allTasksCompleted = true;
      for (int i = 0; i < blockNumber; i++) {
        File resultFile = new File(tempDir, "block_" + i + ".sorted");
        if (!resultFile.exists()) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          allTasksCompleted = false;
          break;
        }
      }
    }

    // 合并所有块的计算结果并排序
    TreeMap<String, Integer> globalWordCountMap = new TreeMap<>();
    for (int i = 0; i < blockNumber; i++) {
      File resultFile = new File(tempDir, "block_" + i + ".sorted");
      TreeMap<String, Integer> wordCountMap = readWordCountMapFromFile(resultFile);
      for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
        String word = entry.getKey();
        int count = entry.getValue();
        globalWordCountMap.merge(word, count, Integer::sum);
      }
    }
    List<Map.Entry<String, Integer>> sortedWordCountList = new ArrayList<>(globalWordCountMap.entrySet());
    sortedWordCountList.sort((o1, o2) -> {
      int result = o2.getValue().compareTo(o1.getValue());
      if (result != 0) {
        return result;
      } else {
        return o1.getKey().compareTo(o2.getKey());
      }
    });

    // 输出最常见的TOP_N单词
    for (int i = 0; i < Math.min(TOP_N, sortedWordCountList.size()); i++) {
      Map.Entry<String, Integer> entry = sortedWordCountList.get(i);
      String word = entry.getKey();
      int count = entry.getValue();
      System.out.printf("%s: %d%n", word, count);
    }
    // 删除临时
    deleteTempFiles(tempDir);
  }

  private static void deleteTempFiles(File dir) {
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        deleteTempFiles(file);
      } else {
        file.delete();
      }
    }
    dir.delete();
  }

  private static TreeMap<String, Integer> readWordCountMapFromFile(File file) throws IOException {
    TreeMap<String, Integer> wordCountMap = new TreeMap<>();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      String[] words = line.split("\\s+");
      for (String word : words) {
        Integer count = wordCountMap.get(word);
        if (count == null) {
          count = 0;
        }
        wordCountMap.put(word, count + 1);
      }
    }
    bufferedReader.close();
    return wordCountMap;
  }

  private static class CountWordsInBlockTask implements Runnable {

    private final File blockFile;

    public CountWordsInBlockTask(File blockFile) {
      this.blockFile = blockFile;
    }

    @Override
    public void run() {
      TreeMap<String, Integer> wordCountMap;
      try {
        wordCountMap = readWordCountMapFromFile(blockFile);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
      List<Map.Entry<String, Integer>> sortedWordCountList = new ArrayList<>(wordCountMap.entrySet());
      sortedWordCountList.sort((o1, o2) -> {
        int result = o2.getValue().compareTo(o1.getValue());
        if (result != 0) {
          return result;
        } else {
          return o1.getKey().compareTo(o2.getKey());
        }
      });
      int topN = Math.min(TOP_N, sortedWordCountList.size());
      File resultFile = new File(tempDir, blockFile.getName() + ".sorted");
      try {
        PrintWriter printWriter = new PrintWriter(new FileWriter(resultFile));
        for (int i = 0; i < topN; i++) {
          Map.Entry<String, Integer> entry = sortedWordCountList.get(i);
          String word = entry.getKey();
          int count = entry.getValue();
          printWriter.printf("%s: %d%n", word, count);
        }
        printWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
  }
}

