import java.util.*;
import java.io.*;

public class TwoDTree{
	private class TreeNode{
		Point item;
		Rectangle rect; //Imaginary Rectangle that corresponds to item in node
		TreeNode left = null;
		TreeNode right = null;
		TreeNode parent = null;//TIPOTA
		
		TreeNode(Point item, Rectangle rect){
			this.item = item;
			this.rect = rect;
		}//TreeNode Constructor
	}//TreeNode class
	
	private TreeNode head;
	private int treesize = 0;
	
	public TwoDTree(){};
	
	public boolean isEmpty(){
		return (treesize == 0);
	}//isEmpty
	
	public int size(){
		return treesize;
	}//size
	
	public void insert(Point p){ //Recursive insert (Uses private method insertR)
		//This method will also create the imaginary rectangle corresponding to the Point about to be inserted
		int xmin = 0, xmax = 100, ymin = 0, ymax = 100; //Default rectangle coordinates (Will change depending on traversal)
		if (isEmpty()){
			treesize++; //Increase tree size
			head = new TreeNode(p, new Rectangle(xmin, xmax, ymin, ymax)); //Insert node at root if list is empty
			return;
		}
		treesize++; //Increase tree size
		insertR(head, p, true, xmin, xmax, ymin, ymax); //Start recursion
		
		
	}//insert
	
	private void insertR(TreeNode current, Point p,boolean compareX, int xmin, int xmax, int ymin, int ymax){
		
		
		if (p.x() == current.item.x() && p.y() == current.item.y()){// Node found
			System.out.println("Node already in tree");
			treesize--;//Revert to previous size if node already found (Prevents spaghetti code);
			return;
		}
		
		
		if (compareX){ //If we're comparing the point's x coordinate
			if (p.x() < current.item.x()){//Belongs to left subtree
				xmax = current.item.x(); //Decrease xmax
				if(current.left == null){
					current.left = new TreeNode(p, new Rectangle(xmin, xmax, ymin, ymax)); //Inserts Node to left child
					return;
				}//if
				insertR(current.left, p, false, xmin, xmax, ymin, ymax); //Recurse to the right
			}//if
			else{
				xmin = current.item.x(); //Increase xmax
				if(current.right == null){//Belongs to right subtree
					current.right = new TreeNode(p, new Rectangle(xmin, xmax, ymin, ymax)); //Inserts Node to right child
					return;
				}//if
				insertR(current.right, p, false, xmin, xmax, ymin, ymax); //Recurse to the right
			}//else
		}//if
		else{ //If we're comparing the point's y coordinate
			if (p.y() < current.item.y()){ //Belongs to left subtree
				ymax = current.item.y(); //Decrease
				if(current.left == null){
					current.left = new TreeNode(p, new Rectangle(xmin, xmax, ymin, ymax));
					return;
				}//if
				insertR(current.left, p, true, xmin, xmax, ymin, ymax); //Recurse to the left
			}//if
			else{
				ymin = current.item.y(); //Increase ymin
				if(current.right == null){//Belongs to right subtree
					current.right = new TreeNode(p, new Rectangle(xmin, xmax, ymin, ymax));
					return;
				}//if
				insertR(current.right, p, true, xmin, xmax, ymin, ymax); //Recurse to the right
			}//else
		}//else
	}//insertR
	
	public boolean search(Point p) { //Non Recursive search
		int counter = 0; //Used to help us constantly change x and y coordinate comparisons (
		TreeNode current = head; //Node for traversal
		
		if (isEmpty()){
			System.out.println("Tree is empty");
			return false;
		}//if
		
		while (true){ //Since this is not recursive, break statements help us to exit the infinite loop
			
			if (head == null){ //Node not found
				System.out.println("Node not found.");
				return false;
			}//if
			
			if (p.x() == current.item.x() && p.y() == current.item.y()){// Node found
				System.out.println("Node is found.");
				return true;
			}//if
			
			if (counter % 2 == 0){
				// We are in odd loop, we will compare X
				if (p.x() < current.item.x())
					current = current.left; //Again for left subtree
				else
					current = current.right;	 //Again for right subtree
			}//if
			else{
				// We are in even loop, we will compare Y
				if (p.y() < current.item.y())
					current = current.left; //Again for left subtree
				else
					current = current.right; //Again for right subtree
			}//else
			counter++; //Compare other coordinate
		}//while
	}//search
	
	private void nearestNeighborSearchR(TreeNode current, Point p,Point [] minDistancePoint, boolean compareX ){
	//Min Distance point is always size 1 array. Useful Data structure	for Recursion, because it's mutable	
	//compareX helps us keep track of which point coordinate to compare when traversing the 2DTree
	/*The logic of this method is that we first try to find one of the possible closest points to p.
	  That point is most likely located near the nodes where p would be found should it have been in the tree.
	  By doing that, now that we can have access to a possible closest point to p, we can compare the distance
	  of p to other rectangles of the points in the subtree, and can choose not to traverse those subtrees, should
	  the distance be larger than our current minDistance. On Average, this saves time, as we don't have to traverse
	  the whole Tree. It eliminates subtrees.
	*/
		if (current == null)
			return;
		if(minDistancePoint[0].squareDistanceTo(p) > current.item.squareDistanceTo(p))
			minDistancePoint[0] = current.item; //New minimum Distance point
		if (compareX){ //Comparing x coordinate
			if (p.x() < current.item.x()){
				nearestNeighborSearchR(current.left, p, minDistancePoint, false); //Recurse left subtree
				if (current.right != null)
					if (minDistancePoint[0].squareDistanceTo(p) >= current.right.rect.squareDistanceTo(p)) //if there are possible minPoints
						nearestNeighborSearchR(current.right, p, minDistancePoint, false); //Recurse right subtree if there are possible minPoints
			}//if
			else{
				nearestNeighborSearchR(current.right, p, minDistancePoint, false); //Recurse right subtree
				if (current.left != null)
					if (minDistancePoint[0].squareDistanceTo(p) >= current.left.rect.squareDistanceTo(p)) //if there are possible minPoints
						nearestNeighborSearchR(current.left, p, minDistancePoint, false); //Recure left subtree if there are possible minPoints
			}//else
		}//if
		else{ //Comparing y coordinate
			if (p.y() < current.item.y()){
				nearestNeighborSearchR(current.left, p, minDistancePoint, true); //Recurse left subtree
				if (current.right != null)
					if (minDistancePoint[0].squareDistanceTo(p) >= current.right.rect.squareDistanceTo(p)) //if there are possible minPoints
						nearestNeighborSearchR(current.right, p, minDistancePoint,true);//Recurse right subtree if there are possible minPoints
			}//if
			else{
				nearestNeighborSearchR(current.right, p, minDistancePoint, true); //Recurse right subtree
				if (current.left != null)
					if (minDistancePoint[0].squareDistanceTo(p) >= current.left.rect.squareDistanceTo(p)) //if there are possible minPoints
						nearestNeighborSearchR(current.left, p, minDistancePoint, true);//Recure left subtree
			}//else
		}//else
	}//nearestNeighborSearchR

	public Point nearestNeighbor(Point p){
		if (isEmpty())
			return null;
		Point[] minDistancePoint = {head.item}; //Initialize size 1 array with root point
		nearestNeighborSearchR(head, p, minDistancePoint, true);
		
		return minDistancePoint[0];	
	}//nearestNeighbor
	
	private void rangeSearchR(TreeNode current, Rectangle rec, List<Point> points){
		if(rec.intersects(current.rect)){ //If rectangle intersects with current imaginary rectangle
			if(rec.contains(current.item)) //If it contains the point
				points.insertAtFront(current.item); //Add it to list
			if(current.left != null) //If left child exists
				rangeSearchR(current.left, rec, points); //Recurse left subtree
			if(current.right !=null) //If right child exists
				rangeSearchR(current.right, rec, points); //Recurse right subtree
		}//if
	}//rangeSearchR
	
	public List<Point> rangeSearch(Rectangle r){
		List<Point> points = new List<>(); //Point List
		rangeSearchR(head, r, points); //Begin Recursion
		
		return points;
	}//rangeSearch
	
	public static void main(String[] args){
		TwoDTree tree = new TwoDTree();
		try{
			BufferedReader reader = null; //file reader
			String line;
		
			
			reader = new BufferedReader(new FileReader(new File(args[0]))); //Read file from the beginning
			line = reader.readLine(); //Next Line
			int givenTotalNodes = Integer.parseInt(line.trim()); //Helps us check if given nodes are nodes that appear on file
			int	actualTotalNodes = 0; //Actual number of nodes, will be checked in while loop
			line = reader.readLine(); //Start checking if given nodes are correct
			while (line !=null){ 
			
				line = reader.readLine();
				actualTotalNodes++;
			}//while
			
			if (actualTotalNodes != givenTotalNodes){ //Error: Not the same nodes
				System.out.println("Actual Total Nodes aren't the same as given total nodes at the start of file");
				return;
			}//if
			
			reader = new BufferedReader(new FileReader(new File(args[0]))); //Read file again from the beginning
			int x = 0, y = 0;
			int counter = 1;
			boolean cordNotRead = false;
			boolean cordNotValid = false;
			line = reader.readLine();
			line = reader.readLine();
			while (line != null){
				
				counter++;
				if (line.split("\\s+").length != 2){
					System.out.println("One of the coordinates at line " + counter + " is not given!");
					cordNotRead = true;
				}//if
				else{
					if (x > 100 || x < 0){
						System.out.println("X coordinate is invalid at line  " + counter);
						cordNotValid = true;
					}//if
					else
						x = Integer.parseInt(line.split("\\s+")[0]);
					if (y > 100 || y < 0){
						System.out.println("Y coordinate is invalid at line " + counter);
						cordNotValid = true;
					}//if
					else
						y = Integer.parseInt(line.split("\\s+")[1]);
					if (!cordNotValid)
						tree.insert(new Point(x, y));
				}//else
				line = reader.readLine();
			}//while
			
			if (cordNotRead || cordNotValid)
				return;			
		}//try
		catch(IOException e){
			System.out.println("File Not Found");
			return;
		}//catch
		
		//Now we can start getting User Input
		Scanner in = new Scanner(System.in); //Scanner object
		
		int choice; //User choice
		while (true){
			//Menu
			System.out.println("\n\nChoose an option");
			System.out.println("\t1.   Compute the size of the tree");
			System.out.println("\t2.   Insert a new point");
			System.out.println("\t3.   Search if a given point exists in the tree");
			System.out.println("\t4.   Provide a query rectangle");
			System.out.println("\t5.   Provide a query point");
			System.out.println("\t6.   Exit Program");
			
			System.out.print("Your choice: ");
			choice = in.nextInt(); //Give choice
			
			if (choice == 1)//If choice is 1, print tree size
				System.out.println("The size of the tree is " + tree.size());
			else if (choice == 2 || choice ==  3 || choice == 5){ //Else If choice is either 2, 3 or 5, initialize point
				int x, y;
				System.out.print("Give Point X coordinate: ");
				x = in.nextInt(); //Give x
				System.out.print("Give Point Y coordinate: ");
				y = in.nextInt(); //Give y
				
				if(x <= 100 && x >= 0 && y <= 100 && y >= 0){
					Point userPoint = new Point(x,y);
					
					if (choice == 2) //If choice is 2, insert point
						tree.insert(userPoint);
					else if (choice == 3) //Else if choice is 3, search for point
						tree.search(userPoint);
					else{//Else if choice is 5, serch for nearest point in tree
						Point nearest = tree.nearestNeighbor(userPoint);
						System.out.println("The nearest point in the tree is " + nearest);
						System.out.println("Distance: " + nearest.distanceTo(userPoint));
					}//else
				}//if
				else
					System.out.println("Please choose valid point coordinates (between 0 and 100)");
			}//else if
			else if (choice == 4){ //Else if choice is 5, find points in tree in rectangle
				int xmin, xmax, ymin, ymax;
				System.out.print("Give Point Xmin coordinate: ");
				xmin = in.nextInt(); //Give xmin
				System.out.print("Give Point Xmax coordinate: ");
				xmax = in.nextInt(); //Give xmax
				System.out.print("Give Point Ymin coordinate: ");
				ymin = in.nextInt(); //Give ymin
				System.out.print("Give Point Ymax coordinate: ");
				ymax = in.nextInt(); //Give xmin
				
				System.out.println("List of points contained in given rectangle: " + tree.rangeSearch(new Rectangle(xmin, xmax, ymin, ymax)));
			}//else if
			else if (choice == 6){// Else if choice is 6, Terminate Program
				System.out.println("Terminating Program. Thank you for using!\n");
				break;
			}//else if
			else //Else prompt again
				System.out.println("Please input a valid option");
		}//while
	}//main
}//TwoDTree


