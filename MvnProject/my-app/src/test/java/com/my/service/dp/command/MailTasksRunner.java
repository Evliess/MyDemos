package com.my.service.dp.command;


import org.junit.runner.Runner;

import java.util.LinkedList;
import java.util.List;

public class MailTasksRunner implements Runnable {

  private Thread runner;
  private List<Command> pendingCommands;
  private volatile boolean stop;
  private static final MailTasksRunner RUNNER = new MailTasksRunner();

  public static final MailTasksRunner getInstance() {
    return RUNNER;
  }

  private MailTasksRunner() {
    pendingCommands = new LinkedList<>();
    runner = new Thread(this);
    runner.start();
  }

  @Override
  public void run() {
    while (true) {
      Command cmd = null;
      synchronized (pendingCommands) {
        if (pendingCommands.isEmpty()) {
          try {
            pendingCommands.wait();
          } catch (InterruptedException e) {
            System.out.println("Interrupted!");
            if (stop) {
              System.out.println("Runner stopped!");
              return;
            }
          }
        } else {
          cmd = pendingCommands.remove(0);
        }
      }
      if (cmd == null) {
        return;
      }
      cmd.execute();

    }
  }

  public void addCommand(Command command) {
    synchronized (pendingCommands) {
      pendingCommands.add(command);
      pendingCommands.notifyAll();
    }
  }

  public void shutdown() {
    this.stop = true;
    this.runner.interrupt();
  }
}
