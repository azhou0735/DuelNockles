package sortomania.contestants;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import sortomania.Contestant;

public class BenjaminDardia extends Contestant 
{

	private final long BIG_NUM = (long) Math.pow(10, 10);
    private ArrayList<Double>[] buckets = getBuckets();
    private final int CUTOFF =  15;   // cutoff to insertion sort
    private final int THRESHOLD = 16;

	
	@Override
	public Color getColor() 
	{
	return Color.BLACK;
	}

	@Override
	public String getSpriteName() 
	{
	return KEN;
	}

	public double sortAndGetMedian(int[] random) 
	{
		int i, m = random[0], exp = 1, n = random.length;
        int[] b = new int[n];
        
        for (i = 1; i < n; i++)
            if (random[i] > m)
                m = random[i];
        
        while (m / exp > 0)
        {
            int[] bucket = new int[10];
 
            for (i = 0; i < n; i++)
                bucket[(random[i] / exp) % 10]++;
            for (i = 1; i < 10; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--)
                b[--bucket[(random[i] / exp) % 10]] = random[i];
            for (i = 0; i < n; i++)
            	random[i] = b[i];
            exp *= 10;        
        }
	if (random.length % 2 == 0) 
	 	{
	 	return ((double)(random[random.length / 2] + random[random.length / 2 - 1]) / 2);
	 	}
	 	else
	 	{
	 	return random[random.length / 2];
	 	}
  
	}
	
	@Override
	public int sortAndGetResultingIndexOf(String[] strings, String toFind) 
	{
	int N = strings.length;
	      int R = 256;
	      int W = 5;
	      String[] aux = new String[N];
	      for (int d = W-1; d >= 0; d--)
	      { // Sort by key-indexed counting on dth char.
	         int[] count = new int[R+1];     // Compute frequency counts.
	         for (int i = 0; i < N; i++)
	             count[strings[i].charAt(d) + 1]++;
	         for (int r = 0; r < R; r++)     // Transform counts to indices.
	            count[r+1] += count[r];
	         for (int i = 0; i < N; i++)     // Distribute.
	            aux[count[strings[i].charAt(d)]++] = strings[i];
	         for (int i = 0; i < N; i++)     // Copy back.
	            strings[i] = aux[i];
	        }
	      
	      return performBinarySearchIterative(strings, toFind, 0, strings.length - 1);
	      
	}
	
	@Override
	public double mostlySortAndGetMedian(int[] mostlySorted) 
	{
	//timSort(mostlySorted, mostlySorted.length);
		int n = mostlySorted.length;
        for (int i=1; i<n; ++i)
        {
            int key = mostlySorted[i];
            int j = i-1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && mostlySorted[j] > key)
            {
            	mostlySorted[j+1] = mostlySorted[j];
                j = j-1;
            }
            mostlySorted[j+1] = key;
        }
	if (mostlySorted.length % 2 == 0) 
	 	{
	 	return ((double)(mostlySorted[mostlySorted.length / 2] + mostlySorted[mostlySorted.length / 2 - 1]) / 2);
	 	}
	 	else
	 	{
	 	return mostlySorted[mostlySorted.length / 2];
	 	}
	}

	@Override
	public double sortMultiDim(int[][] grid) 
	{
	double[] sortedGrid = new double[grid.length];
	for (int i = 0; i < sortedGrid.length; i += 1)
	{
	sortedGrid[i] = sortAndGetMedian(grid[i]);
	}
	for (long j = BIG_NUM; j >= 10; j = j / 10) {
        for (int i = 0; i < sortedGrid.length; i++) {
            int index = (int) ((sortedGrid[i] * j) % 10);
            buckets[index].add(sortedGrid[i]);
        }

        /*
        merges all the bucket's into the output array
        and empty the buckets for reuse
         */
        for (int n = 0; n < sortedGrid.length; n++) {
            for (int k = 0; k < buckets.length; k++) {
                for (int h = 0; h < buckets[k].size(); h++) {
                	sortedGrid[n] = buckets[k].get(h);
                    n++;
                }
                buckets[k] = new ArrayList<>();
            }
            }
        }
	if (sortedGrid.length % 2 == 0) 
	 	{
	 	return ((double)(sortedGrid[sortedGrid.length / 2] + sortedGrid[(sortedGrid.length / 2) - 1]) / 2);
	 	}
	 	else
	 	{
	 	return sortedGrid[sortedGrid.length / 2];
	 	}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int sortAndSearch(Comparable[] arr, Comparable toFind) 
	{
		if (arr != null && arr.length > 1) {
            int floor = (int) (Math.floor(Math.log(arr.length) / Math.log(2)));
            innerLoop(0, arr.length, 2 * floor, arr);
            insertionsort(0, arr.length, arr);
        }
	return binarySearch(arr, toFind);
	}
	
	public <T extends Comparable<? super T>> void introSort(T[] arr) {
        if (arr != null && arr.length > 1) {
            int floor = (int) (Math.floor(Math.log(arr.length) / Math.log(2)));
            innerLoop(0, arr.length, 2 * floor, arr);
            insertionsort(0, arr.length, arr);
        }
    }

    /**
     * Sort the array of comparables within the given range of elements.
     * Uses an introspective sort algorithm, so expect O(log(n)) running
     * time.
     *
     * @param  <T>   type of comparable to be sorted.
     * @param  arr   comparables to be sorted.
     * @param  low   low end of range to sort (inclusive).
     * @param  high  high end of range to sort (inclusive).
     */
    public <T extends Comparable<? super T>> void introSort(T[] arr, int low, int high) {
        if (arr != null && arr.length > 1 && low >= 0 && low < high) {
            int floor = (int) (Math.floor(Math.log(high - low) / Math.log(2)));
            innerLoop(low, high, 2 * floor, arr);
            insertionsort(low, high, arr);
        }
    }

    /**
     * A modified quicksort that delegates to heapsort when the depth
     * limit has been reached. Does not sort the array if the range is
     * below the threshold.
     *
     * @param  <T>          type of comparable to be sorted.
     * @param  arr          comparables to be sorted.
     * @param  low          low end of range to sort (inclusive).
     * @param  high         high end of range to sort (inclusive).
     * @param  depth_limit  if zero, will delegate to heapsort.
     */
    private <T extends Comparable<? super T>> void innerLoop(
            int low, int high, int depth_limit, T[] arr) {
        while (high - low > THRESHOLD) {
            if (depth_limit == 0) {
                // perform a basic heap sort
                int n = high - low;
                for (int i = n / 2; i >= 1; i--) {
                    T d = arr[low + i - 1];
                    int j = i;
                    while (j <= n / 2) {
                        int child = 2 * j;
                        if (child < n && arr[low + child - 1].compareTo(arr[low + child]) < 0) {
                            child++;
                        }
                        if (d.compareTo(arr[low + child - 1]) >= 0) {
                            break;
                        }
                        arr[low + j - 1] = arr[low + child - 1];
                        j = child;
                    }
                    arr[low + j - 1] = d;
                }
                for (int i = n; i > 1; i--) {
                    T t = arr[low];
                    arr[low] = arr[low + i - 1];
                    arr[low + i - 1] = t;
                    T d = arr[low + i - 1];
                    int j = 1;
                    int m = i - 1;
                    while (j <= m / 2) {
                        int child = 2 * j;
                        if (child < m && arr[low + child - 1].compareTo(arr[low + child]) < 0) {
                            child++;
                        }
                        if (d.compareTo(arr[low + child - 1]) >= 0) {
                            break;
                        }
                        arr[low + j - 1] = arr[low + child - 1];
                        j = child;
                    }
                    arr[low + j - 1] = d;
                }
                return;
            }
            depth_limit--;
            int p = partition(low, high, medianOf3(low, low + ((high - low) / 2) + 1, high - 1, arr), arr);
            innerLoop(p, high, depth_limit, arr);
            high = p;
        }
    }

    /**
     * Partitions the elements in the given range such that elements
     * less than the pivot appear before those greater than the pivot.
     *
     * @param  <T>   type of comparable to be sorted.
     * @param  low   low end of range to sort (inclusive).
     * @param  high  high end of range to sort (inclusive).
     * @param  x     pivot to compare to.
     * @param  arr   comparables to be sorted.
     * @return  midpoint of partitioned values.
     */
    private <T extends Comparable<? super T>> int partition(int low, int high, T x, T[] arr) {
        int i = low;
        int j = high;
        while (true) {
            while (arr[i].compareTo(x) < 0) {
                i++;
            }
            j--;
            while (x.compareTo(arr[j]) < 0) {
                j--;
            }
            if (i >= j) {
                return i;
            }
            T t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            i++;
        }
    }

    /**
     * Finds the median of three element in the given range.
     *
     * @param  <T>   type of comparable to be sorted.
     * @param  low   low end of range to sort (inclusive).
     * @param  mid   midpoint of the range.
     * @param  high  high end of range to sort (inclusive).
     * @param  arr   comparables to be sorted.
     * @return  the median of three element.
     */
    private <T extends Comparable<? super T>> T medianOf3(int low, int mid, int high, T[] arr) {
        if (arr[mid].compareTo(arr[low]) < 0) {
            if (arr[high].compareTo(arr[mid]) < 0) {
                return arr[mid];
            } else {
                if (arr[high].compareTo(arr[low]) < 0) {
                    return arr[high];
                } else {
                    return arr[low];
                }
            }
        } else {
            if (arr[high].compareTo(arr[mid]) < 0) {
                if (arr[high].compareTo(arr[low]) < 0) {
                    return arr[low];
                } else {
                    return arr[high];
                }
            } else {
                return arr[mid];
            }
        }
    }

    /**
     * A simple insertion sort that operates on the given range.
     *
     * @param  <T>   type of comparable to be sorted.
     * @param  low   low end of range to heapify (inclusive).
     * @param  high  high end of range to sort (inclusive).
     * @param  arr   comparables to be sorted.
     */
    private <T extends Comparable<? super T>> void insertionsort(int low, int high, T[] arr) {
        for (int i = low; i < high; i++) {
            int j = i;
            T t = arr[i];
            while (j != low && t.compareTo(arr[j - 1]) < 0) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = t;
        }
    }
	
	void fourthDimensionSort( int[] a)
    {
        int i, m = a[0], exp = 1, n = a.length;
        int[] b = new int[n];
        
        for (i = 1; i < n; i++)
            if (a[i] > m)
                m = a[i];
        
        while (m / exp > 0)
        {
            int[] bucket = new int[10];
 
            for (i = 0; i < n; i++)
                bucket[(a[i] / exp) % 10]++;
            for (i = 1; i < 10; i++)
                bucket[i] += bucket[i - 1];
            for (i = n - 1; i >= 0; i--)
                b[--bucket[(a[i] / exp) % 10]] = a[i];
            for (i = 0; i < n; i++)
                a[i] = b[i];
            exp *= 10;        
        }
    }    
	 int getMax(int arr[], int n)
	    {
	        int mx = arr[0];
	        for (int i = 1; i < n; i++)
	            if (arr[i] > mx)
	                mx = arr[i];
	        return mx;
	    }

	public <T extends Comparable<T>> int binarySearch(T[] items, T target, int first, int last){

	    if(first > last)
	        return -1; // Base case for unsuccessful search
	    else{
	        int middle = (first + last) / 2; // Next probe index.
	        int compResult = target.compareTo(items[middle]);
	        if(compResult == 0)
	        {
	        	return middle; // Base case for unsuccessful search.
	        }
	    
	        else if (compResult <0)
	            return binarySearch(items, target, first, middle -1);
	        else
	            return binarySearch(items, target, middle + 1, last);
	    }
	}

	public <T extends Comparable<T>> int binarySearch(T[] items, T target){
	    return binarySearch(items, target, 0, items.length -1);
	}
	
	public int performBinarySearchIterative(String[] integerList,
	      String noToSearch, int low, int high) {
	    while (low <= high) {
	      int mid = (low + high) / 2;
	      if (integerList[mid].equals(noToSearch)) {
	        return mid;
	      }  else if (noToSearch.compareTo(integerList[mid]) < 0) {
	        high = mid - 1;
	      } else {
	        low = mid + 1;
	      }
	    }
	    return -1;
	  }
	
	void insertsort(int arr[])
    {
        int n = arr.length;
        for (int i=1; i<n; ++i)
        {
            int key = arr[i];
            int j = i-1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && arr[j] > key)
            {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
    }
	    public void sort1(double[] ar) {
	       /* iterates over the array 10*n times,
	       each iteration in the inside loop it puts the double int a bucket according
	       to it's corresponding digit.
	        */
	        for (long j = BIG_NUM; j >= 10; j = j / 10) {
	            for (int i = 0; i < ar.length; i++) {
	                int index = (int) ((ar[i] * j) % 10);
	                buckets[index].add(ar[i]);
	            }

	            /*
	            merges all the bucket's into the output array
	            and empty the buckets for reuse
	             */
	            for (int n = 0; n < ar.length; n++) {
	                for (int k = 0; k < buckets.length; k++) {
	                    for (int h = 0; h < buckets[k].size(); h++) {
	                        ar[n] = buckets[k].get(h);
	                        n++;
	                    }
	                    buckets[k] = new ArrayList<>();
	                }
	                }
	            }
	        }

	    /**
	     * creates 10 buckets for the sorting
	     *
	     * @return ArrayList<Double>[] with 10 buckets.
	     */
	    public ArrayList<Double>[] getBuckets() {
	        ArrayList<Double>[] al = new ArrayList[10];
	        for (int i = 0; i < al.length; i++) {
	            al[i] = new ArrayList<>();
	        }
	        return al;
	    }
	    
	    
	    
}