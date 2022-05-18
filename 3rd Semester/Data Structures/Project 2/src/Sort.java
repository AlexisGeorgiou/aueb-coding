public class Sort{
	public static void quickSort(int array[], int start, int end){
		if (start < end){ //if array is not unary
			int sliceI = slice(array, start, end);
				
			
			quickSort(array, start, sliceI - 1);//recursive call for right sub-array
			quickSort(array, sliceI + 1, end);//recursive call for left sub-array
		
		}
	}
	public static int slice(int arr[], int start, int end){
			int i = (start - 1); //i is one unit out of array range
			int pivot = arr[end];//The last element of the array is chosen as the pivot
			
			
			 for (int j = start; j < end; j++) {
				if (arr[j] >= pivot) {
					i++;
					//Swaps i and j element because j element is bigger than the pivot
					int swapTemp = arr[i];
					arr[i] = arr[j];
					arr[j] = swapTemp;
				}//if
			}//for
			
			/*Brings pivot to the sorted point (Now every element to the left of the pivot is greater than it
			  and every element to the right of it is lesser than it*/
			int swapTemp = arr[i+1];
			arr[i+1] = arr[end];
			arr[end] = swapTemp;
 
			return i+1;//return next sub-array pointer
	}
	
}