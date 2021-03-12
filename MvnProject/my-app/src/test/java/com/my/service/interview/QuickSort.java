package com.my.service.interview;

public class QuickSort {
  static int[] array = {17, 21, 4, 7, 6, 18};

  private static int getBasePosition(int array[], int L, int R) {
    int base = array[L];
    int temp, i, j;
    i = L;
    j = R;
    while (i < j) {
      // 1.从右边找到比base小的数
      while (i < j && base <= array[j]) {
        j--;
      }
      // 2.从左边找到比base大的数
      while (i < j && array[i] <= base) {
        i++;
      }
      //交换位置
      if (i < j) {
        System.out.println(array[i] + "<->" + array[j]);
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
    }
    //当i==j时，需要将array[i]的值与array[L]的值进行交换，因此引入变量i,j。如果直接使用L，R，那么当L==R的时候，base在数组中的初始位置L就会丢失。
    temp = array[i];
    array[i] = array[L];
    array[L] = temp;
    return i;
  }

  private static void quickSort(int[] array, int start, int end) {
    if (start > end) {
      return;
    } else {
      //1.找到basePosition
      int basePosition = getBasePosition(array, start, end);
      //2.递归排序base左边的子数组
      quickSort(array, start, basePosition - 1);
      //3.递归排序base右边的子数组
      quickSort(array, basePosition + 1, end);
    }
  }

  public static void main(String[] args) {
    //    getBasePosition(array, 0, array.length - 1);
    quickSort(array, 0, array.length - 1);

    for (int n : array) {
      System.out.print(n + " ");
    }

  }
}
