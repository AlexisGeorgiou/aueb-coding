import org.junit.Test;
import org.junit.Assert;

public class ArrayUtilTest3180027 {
	//3180027 Georgiou Alexios- Lazaros
	@Test(expected = IllegalArgumentException.class)
	public void testcase1() {
	int[] my_array = null;
	ArrayUtil.subarray(my_array,0,3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testcase2() {
	int[] my_array = {0};
	ArrayUtil.subarray(my_array,-1,3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testcase3() {
	int[] my_array = { 0,1,2,3 };
	ArrayUtil.subarray(my_array,0,5);
	}

	@Test
	public void testcase4() {
		int[] my_array = { 0,1,2,3 };
		int[] emptyArray = new int[0];
		Assert.assertArrayEquals(ArrayUtil.subarray(my_array,2,1), emptyArray);
	}

	@Test
	public void testcase5() {
	int[] my_array = { 0,1,2,3,4,5 };
	Assert.assertArrayEquals(ArrayUtil.subarray(my_array,1,3), new int[]{1, 2});
	}

}
