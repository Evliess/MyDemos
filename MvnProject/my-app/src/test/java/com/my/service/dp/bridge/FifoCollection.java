package com.my.service.dp.bridge;

public interface FifoCollection<T> {
  void offer(T element);

  T poll();
}
