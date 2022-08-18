package com.my.service.dp.objectpool;

import java.awt.geom.Point2D;

public class Client {
  public static final ObjectPool<Bitmap> bitmapObjectPool = new ObjectPool<>(()-> new Bitmap("Logo.bmp"), 5);
  public static void main(String[] args) {
    Bitmap b1 = bitmapObjectPool.get();
    b1.setLocation(new Point2D.Double(10,10));

    Bitmap b2 = bitmapObjectPool.get();
    b2.setLocation(new Point2D.Double(20,20));

    b1.draw();
    b2.draw();

    bitmapObjectPool.release(b1);
    bitmapObjectPool.release(b2);

  }
}
