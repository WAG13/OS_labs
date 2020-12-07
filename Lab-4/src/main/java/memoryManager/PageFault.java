package memoryManager;
import java.util.*;

public class PageFault {

  /** LRU algorithm with counter */
  public static void replacePage(Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel){
    int min_counter = Integer.MAX_VALUE;
    int outdated_page_index = 0;
    for(int i = 0; i < virtPageNum; i++){
      Page current_page = ( Page ) mem.elementAt(i);
      if ( current_page.physical != -1 ) {
        if (current_page.page_counter < min_counter) {
          min_counter = current_page.page_counter;
          outdated_page_index = i;
        }
      }
    }

    Page outdated_page = ( Page ) mem.elementAt( outdated_page_index );
    Page new_page = ( Page ) mem.elementAt( replacePageNum );
    controlPanel.removePhysicalPage( outdated_page_index );
    new_page.physical = outdated_page.physical;
    controlPanel.addPhysicalPage( new_page.physical , replacePageNum );
    outdated_page.inMemTime = 0;
    outdated_page.lastTouchTime = 0;
    outdated_page.R = 0;
    outdated_page.M = 0;
    outdated_page.physical = -1;
    outdated_page.page_counter = 0;
  }

}