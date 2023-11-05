package Practicals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

public class Practicalfourteen {
    static int pageFrames=0;
    //Least Recently Used(LRU) Page Replacement Algorithm
    static int lru(int referenceString[])
    {
        //This array list will contain all the pages that are currently in memory
        ArrayList<Integer>  pages = new ArrayList<Integer>(pageFrames);
        // This hashmap will store least recently used indexes of the pages
        HashMap<Integer, Integer> indexes = new HashMap<>();
        // Start from initial page
        int page_faults = 0,  n = referenceString.length, curPage;
        for (int i=0; i<n; i++)
        {
            curPage = referenceString[i];
            // Check if the set can hold more pages
            if (pages.size() < pageFrames) {
                // Insert it into set if not already present
                // This represents a page fault
                if (!pages.contains(curPage))
                {
                    pages.add(curPage);
                    // increment page fault count
                    page_faults++;
                    displayPageFrames(pages, page_faults);
                }
                // Store the recently used index of each page
                indexes.put(curPage, i);
            }
            // If the set is full then need to select a page to be replaced
            // The page that is selected for replacement is the least recently used page
            else
            {
                // Check if current page is not already present in the set
                if (!pages.contains(curPage)) {
                    // The page having the lowest value of associated index will be the least recently used page
                    int lru = Integer.MAX_VALUE, pageToBeReplaced =0;
                    int temp;
                    for(int j = 0; j < pages.size(); j++){
                        temp = pages.get(j);
                        if (indexes.get(temp) < lru)
                        {
                            lru = indexes.get(temp);
                            pageToBeReplaced = j;
                        }
                    }
                    indexes.remove(pages.get(pageToBeReplaced));
                    pages.set(pageToBeReplaced, curPage);
                    // Increment page fault count
                    page_faults++;
                    displayPageFrames(pages, page_faults);
                }
                // Update the current page index
                indexes.put(curPage, i);
            }
        }
        return page_faults;
    }

    static void displayPageFrames(ArrayList<Integer> pages, int page_faults){
        System.out.print("At PageFault- " + page_faults  + " :: Pages- ");
        for(int i = 0; i < pages.size(); i++) {
            System.out.print(" " + pages.get(i));
        }
        System.out.print("\n");
    }
    // Driver method
    public static void main(String args[])
    {
        int pages[] = {3, 2, 1, 3, 4, 1, 6, 2, 4, 3, 4, 2, 1, 4, 5, 2, 1, 3, 4};

        pageFrames = 3;
        int pageFaults;
        System.out.println("--- Implementing Least Recently Used Page Replacement Algorithm -----");
        pageFaults = lru(pages);
        System.out.println("Number of page faults = " + pageFaults);
        System.out.print("\n");
    }
}