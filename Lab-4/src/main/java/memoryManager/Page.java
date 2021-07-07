package memoryManager;

import java.util.concurrent.atomic.AtomicLong;

public class Page {

  public int id;
  public int physical;
  public byte R;
  public byte M;
  public int inMemTime;
  public int lastTouchTime;
  public long high;
  public long low;

  public long page_counter;
  public static AtomicLong page_table_counter = new AtomicLong(0);

  public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low )
  {
    this.page_counter = 0;

    this.id = id;
    this.physical = physical;
    this.R = R;
    this.M = M;
    this.inMemTime = inMemTime;
    this.lastTouchTime = lastTouchTime;
    this.high = high;
    this.low = low;
  }

}
