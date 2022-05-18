import java.util.*;
import java.io.*;
public class mainApp{

	public static void main(String args[]){
	
		availableList a = new availableList();
		rentalsList r = new rentalsList();
		
		a.readFile();
		r.readFile(a);
		
		
		Scanner in = new Scanner(System.in);
		
		boolean done=false;
		String ans;
		String ans6;
		DVD ans1 = new DVD("", "", "", "", "", "", "", true);
		blueRay ans2 = new blueRay("", "", "", "", "", "", "");
		PlayStation ans3 = new PlayStation("", "", "", "");
		Xbox ans4 = new Xbox("", "", "", "");
		Nintedo ans5 = new Nintedo("", "", "", "");
		String person;
		rent re = new rent(ans1, "", new date(0,0,0));
		int ans7;
		int ans8;
		int ans9;
		date d;
		
		while(!done){
			System.out.println("\n0.Show all movies" + 
								"\n1.Show all games" + 
								"\n2.Show all rentals" + 
								"\n3. Exit application" + 
								"\n4. Save all changes");
			
			ans = in.nextLine();
			
			if (ans.equals("0")){
				
				System.out.println("DVD or blueRay? (DVD/blueRay) ");
				ans = in.nextLine();
				
				if (ans.equals("DVD")){
					int i = 0;
					while(i <= a.getSize()-1){
						if (a.get(i) instanceof DVD ){
							System.out.println(a.get(i).getName());
						}
						i += 1;
					}
					System.out.println("\nWhich movie? (Type the name)");
					ans = in.nextLine();
					int k = 0;
					while(k <= a.getSize()-1){
						if (a.get(k).getName().equals(ans) & a.get(k) instanceof DVD){
							ans1 = (DVD)a.get(k);
							System.out.println(a.get(k));
						}
						k+=1;
					}
					System.out.println("If you rent a movie on the 7th of May you will get a 50% off discount beacause of our store's birthday (delays not included !)");
					System.out.println("\nDo you want to rent it? (y/n)");
					ans = in.nextLine();
				
					if (ans.equals("y")){
						if (ans1.getAvailCopies() != 0){
							ans1.decrCopies();
							System.out.println("\nGive name and last name");
							person = in.nextLine();
							System.out.println("Give the present date. (only date/ integer)");
							ans7 = in.nextInt();
							System.out.println("Give the present month. ( integer)");
							ans8 = in.nextInt();
							System.out.println("Give the present year. ( integer)");
							ans9 = in.nextInt();
							d = new date(ans7, ans8, ans9);
							re = new rent(ans1, person, d);
							r.addRent(re);
							rent.incrNextNewCode();
							System.out.println("\nAnd it will cost you (without delays and discounts) :" + re.getRentCost());
						}
						else{
							System.out.println("\nThere are no copies left.");
						}
					}
				}
				else{
					int i = 0;
					while(i <= a.getSize()-1){
						if (a.get(i) instanceof blueRay){
							System.out.println(a.get(i).getName());
							
						}
						i+=1;
					}
					System.out.println("\nWhich movie? (Type the name)");
					ans = in.nextLine();
					int k = 0;
					while(k <= a.getSize()-1){
						if (a.get(k).getName().equals(ans) & a.get(k) instanceof blueRay){
							ans2 = (blueRay)a.get(k);
							System.out.println(a.get(k));
						}
						k+=1;
					}
					System.out.println("If you rent a movie on  the 7th of May you will get a 50% off discount beacause of our store's birthday (delays not included !)");
					System.out.println("\nDo you want to rent it? (y/n)");
					ans = in.nextLine();
				
					if (ans.equals("y")){
						if (ans2.getAvailCopies() != 0){
							System.out.println("\nGive name and last name");
							person = in.nextLine();
							System.out.println("Give the present date. (only date/ integer)");
							ans7 = in.nextInt();
							System.out.println("Give the present month. ( integer)");
							ans8 = in.nextInt();
							System.out.println("Give the present year. ( integer)");
							ans9 = in.nextInt();
							d = new date(ans7, ans8, ans9);
							re = new rent(ans2, person, d);
							r.addRent(re);
							rent.incrNextNewCode();
							System.out.println("\nAnd it will cost you (without delays and discounts) :" + re.getRentCost());
						}
						else{
							System.out.println("\nThere are no copies left.");
						}
					}
				}
				
				
			}
			
			else if (ans.equals("1")){
				System.out.println("PlayStation, Xbox or Nintedo? (PlayStation/Xbox/Nintedo) ");
				ans = in.nextLine();
				if (ans.equals("PlayStation")){
					int i = 0;
					while(i <= a.getSize()-1){
						if (a.get(i) instanceof PlayStation ){
							System.out.println(a.get(i).getName());
						}
						i+=1;
					}
					System.out.println("Which game? (Type the name)");
					ans = in.nextLine();
					int k = 0;
					while(k <= a.getSize()-1){
						if (a.get(k).getName().equals(ans) & a.get(k) instanceof PlayStation){
							ans3 = (PlayStation)a.get(k);
							System.out.println(a.get(k));
						}
						k+=1;
					}
					System.out.println("If you rent a movie on  the 7th of May you will get a 50% off discount beacause of our store's birthday (delays not included !)");
					System.out.println("\nDo you want to rent it? (y/n)");
					ans = in.nextLine();
				
					if (ans.equals("y")){
						if (ans3.getAvailCopies() != 0){
							System.out.println("\nGive name and last name");
							person = in.nextLine();
							System.out.println("Give the present date. (only date/ integer)");
							ans7 = in.nextInt();
							System.out.println("Give the present month. ( integer)");
							ans8 = in.nextInt();
							System.out.println("Give the present year. ( integer)");
							ans9 = in.nextInt();
							d = new date(ans7, ans8, ans9);
							re = new rent(ans3, person, d);
							r.addRent(re);
							rent.incrNextNewCode();
							System.out.println("\nAnd it will cost you (without delays and discounts) :" + re.getRentCost());
						}
						else{
							System.out.println("\nThere are no copies left.");
						}
					}
				}
				else if (ans.equals("Xbox")){
					int i = 0;
					while(i <= a.getSize()-1){
						if (a.get(i) instanceof Xbox){
							System.out.println(a.get(i).getName());
						}
						i+=1;
					}
					System.out.println("Which game? (Type the name)");
					ans = in.nextLine();
					int k = 0;
					while(k <= a.getSize()-1){
						if (a.get(k).getName().equals(ans) & a.get(k) instanceof Xbox){
							ans4 = (Xbox)a.get(k);
							System.out.println(a.get(k));
						}
						k+=1;
					}
					System.out.println("If you rent a movie on  the 7th of May you will get a 50% off discount beacause of our store's birthday (delays not included !)");
					System.out.println("\nDo you want to rent it? (y/n)");
					ans = in.nextLine();
				
					if (ans.equals("y")){
						if (ans4.getAvailCopies() != 0){
							System.out.println("\nGive name and last name");
							person = in.nextLine();
							System.out.println("Give the present date. (only date/ integer)");
							ans7 = in.nextInt();
							System.out.println("Give the present month. ( integer)");
							ans8 = in.nextInt();
							System.out.println("Give the present year. ( integer)");
							ans9 = in.nextInt();
							d = new date(ans7, ans8, ans9);
							re = new rent(ans4, person, d);
							r.addRent(re);
							rent.incrNextNewCode();
							System.out.println("\nAnd it will cost you (without delays and discounts) :" + re.getRentCost());
						}
						else{
							System.out.println("\nThere are no copies left.");
						}
					}
				}
				else{
					int i = 0;
					while(i <= a.getSize()-1){
						if (a.get(i) instanceof Nintedo){
							System.out.println(a.get(i).getName());
						}
						i+=1;
					}
					System.out.println("Which game? (Type the name)");
					ans = in.nextLine();
					int k = 0;
					while(k <= a.getSize()-1){
						if (a.get(k).getName().equals(ans) & a.get(k) instanceof Nintedo){
							ans5 = (Nintedo)a.get(k);
							System.out.println(a.get(k));
						}
						k+=1;
					}
					System.out.println("If you rent a movie on  the 7th of May you will get a 50% off discount beacause of our store's birthday (delays not included !)");
					System.out.println("\nDo you want to rent it? (y/n)");
					ans = in.nextLine();
				
					if (ans.equals("y")){
						if (ans5.getAvailCopies() != 0){
							System.out.println("\nGive name and last name");
							person = in.nextLine();
							System.out.println("Give the present date. (only date/ integer)");
							ans7 = in.nextInt();
							System.out.println("Give the present month. ( integer)");
							ans8 = in.nextInt();
							System.out.println("Give the present year. ( integer)");
							ans9 = in.nextInt();
							d = new date(ans7, ans8, ans9);
							re = new rent(ans5, person, d);
							r.addRent(re);
							rent.incrNextNewCode();
							System.out.println("\nAnd it will cost you (without delays and discounts) :" + re.getRentCost());
						}
						else{
							System.out.println("\nThere are no copies left.");
						}
					}
				}
			}
			
			else if (ans.equals("2")){
				if (r.isEmpty()){
					System.out.println("There are no rentals.");
				}
				else{
				r.showAll();
				System.out.println("\nSelect a rental by typing a rent-code :");
				ans7 = in.nextInt();
				int i=0;
				double q = 0;
				while ( i <= r.getSize()-1){
					if (r.get(i).getRentCode() == ans7){
						re = r.get(i);
						System.out.println(re);
					}
					i+=1;
				}
				System.out.println("\nDo you want to bring the movie back? (y/n)");
				in.nextLine();
				ans = in.nextLine();
				if (ans.equals("y")){
						System.out.println("\nGive the present date. (only date, integer)");
						ans7 = in.nextInt();
						System.out.println("\nGive the present month. (integer)");
						ans8 = in.nextInt();
						
						if (ans7 >= re.getRentDate().getDate() & (ans7 - re.getRentDate().getDate() >= re.getRentable().getRentTime())){
							if (re.getRentDate().getDate() == 7 & re.getRentDate().getMonth() == 5){
								q = 0.5;
							}
							else{
								q = 0;
							}
							System.out.println("\nIt will cost you : ");
							System.out.println(re.getRentCost() - q*re.getRentCost() + re.getExtraDayCost()*(ans7 - re.getRentDate().getDate() - re.getRentable().getRentTime() + 30*(ans8 - re.getRentDate().getMonth())));
							r.removeRent(re);
						}
						else if (ans7 - re.getRentDate().getDate() <= re.getRentable().getRentTime()){
							if (re.getRentDate().getDate() == 7 & re.getRentDate().getMonth() == 5){
								q = 0.5;
							}
							else{
								q = 0;
							}
							System.out.println("\nIt will cost you : ");
							System.out.println(re.getRentCost() - q*re.getRentCost() );
							r.removeRent(re);
						}
						else if(ans7 < re.getRentDate().getDate() & (ans7 - re.getRentDate().getDate() >= re.getRentable().getRentTime())) {
							if (re.getRentDate().getDate() == 7 & re.getRentDate().getMonth() == 5){
								q = 0.2;
							}
							else{
								q = 0;
							}
							System.out.println("\nIt will cost you : ");
							System.out.println(re.getRentCost() - q*re.getRentCost() + re.getExtraDayCost()*((30-(re.getRentDate().getDate() - ans7)) + 30*(ans8 - re.getRentDate().getMonth())));
							r.removeRent(re);
						}
						re.getRentable().incrCopies();
				}
				}
					
			}
			else if (ans.equals("3")) {
				try{
					FileWriter writer = new FileWriter(new File("rentals.txt"));
					writer.write(" ");
					writer.close();
				}
				catch (IOException e) {
					System.err.println("Error writing file.");
				}
				
				r.writeFile();
				done = true;
			}
			
			else if (ans.equals("4")){
				try{
					FileWriter writer = new FileWriter(new File("rentals.txt"));
					writer.write(" ");
					writer.close();
				}
				catch (IOException e) {
					System.err.println("Error writing file.");
				}
				
				r.writeFile();
				
			
			}
		}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	}
}
