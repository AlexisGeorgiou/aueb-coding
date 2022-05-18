 

public class ArrayUtil {
	public static int[] subarray(int[] array, int startInclusive,int endExclusive) {
		if (array == null) {
			throw new IllegalArgumentException();
		}
		
		if (startInclusive < 0) {
			throw new IllegalArgumentException();
		}
		
		if (endExclusive > array.length) {
			throw new IllegalArgumentException();
		}
		
		int newSize = endExclusive - startInclusive;
		if (newSize <= 0) {
			int[] emptyArray = new int[0];
			return emptyArray;
		}
		
		int[] subarray = new int[newSize];
		System.arraycopy(array, startInclusive, subarray, 0, newSize);
		return subarray;
	}
}
