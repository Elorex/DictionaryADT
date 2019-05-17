import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;

public class ArrayBasedSortedList<AnyType> {
	private static int MAX_LIST = 3;
	private Comparable[] items;
	private static int numItems;

	public ArrayBasedSortedList() {
		items = new Comparable[MAX_LIST]; 
		numItems = -1;
	}

	void add(Comparable x) {

		if (numItems + 1 == MAX_LIST)
			expand();

		if (numItems == -1) {
			numItems++;
			items[numItems] = x;
		}

		else {
			
			int search = Arrays.binarySearch(items, 0, numItems + 1, x);   //See note below on how function works.
			search = (-(search) - 1);

			for (int i = numItems + 1; i > search; i--) { //Loop over the Array from back to location to be set
				items[i] = items[i - 1]; // Shift item over to make room
			}

			items[search] = x; //input Comparable x
			numItems++; //Increase numItems counter by 1
		}

	}
	
	/* Note on how Arrays.binarySearch works. 
	 * 
	 * private static int binarySearch0(Object[] a, int fromIndex, int toIndex,
                                     Object key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("rawtypes")
            Comparable midVal = (Comparable)a[mid];
            @SuppressWarnings("unchecked")
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
	 * 	 
	 */

	boolean remove(Comparable x) {

		int search = Arrays.binarySearch(items, 0, numItems + 1, x); //See note above

		if (search < 0 || search > numItems + 1) //Check if value is within bounds of Array
			return false;
		else {
			for (int i = search; i < numItems; i++) { //Loop through the Array from located item to end of Array
				items[search] = items[search + 1];  //set Item to next value for each value until Array is finished
			}
			numItems--; //Remove last item in Array
			return true;
		}
	}

	Comparable find(Comparable x) {

		int search = Arrays.binarySearch(items, 0, numItems + 1, x); //See notes above

		if (search < 0 || search > numItems + 1) //Check if value is within bounds of Array
			return null;
		else
			return items[search]; //Return the value of Item searched
	}

	private void expand() {
		int temp = numItems;
		Comparable newArray[] = (Words[]) new Words[2 * MAX_LIST]; // Allocate a new array, twice as long.
		for (int i = 0; i < numItems + 1; i++) // Copy items to the bigger array.
			newArray[i] = this.items[i];
		this.items = newArray; // Replace the too-small array with the new one.
		MAX_LIST *= 2;
		numItems = temp;
	}

	public int getNum() {
		return numItems;
	}

	Comparable get(int i) {
		return items[i];
	}

}
