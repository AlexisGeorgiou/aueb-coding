import java.util.*;
import java.io.*;

public class availableList{
	
	private LinkedList<item> moviesGames = new LinkedList<item>();
	Scanner in = new Scanner(System.in);
	String ans;
	
	public void addItem(item m){
		
		moviesGames.add(m);
	
	}
	
	public void removeItem(String m){
		
		for (int i=moviesGames.size()-1; i>=0; i--)
			if (moviesGames.get(i).getName().contains(m)){
				System.out.print(moviesGames.get(i));
				System.out.print("Remove ? (y/n): ");
				ans = in.nextLine();
				if (ans.equals("y")) {
					moviesGames.remove(i);
					System.out.println("Removed ...");
				}
				else System.out.println("Skipped ...");
			}
	}
	
	public void showAll(){
		int i = 0;
		
		while(i <= moviesGames.size()-1){
			System.out.println(i + "." + moviesGames.get(i));
			i+=1;
		}
	}
	
	public Iterator iter(){
		Iterator it;
		return it = moviesGames.iterator();
	}
	
	public item get(int i){
		return moviesGames.get(i);
	}
	
	public int getSize(){
		return moviesGames.size();
	}
	
	public void readFile(){
		
		BufferedReader reader = null;
		item product = null;
		item product1 = null;
		item product2 = null;
		item product3 = null;
		String line;
		//Movie
		boolean ITEM_TYPE = true;
		boolean SUB_TYPE = true;
		boolean TITLE = true;
		boolean YEAR = true;
		boolean CAST = true;
		boolean DIRECTOR = true;
		boolean CATEGORY = true;
		boolean COPIES = true;
		boolean SCREENWRITER = true;
		boolean PRODUCTION = true;
		
		//Game
		boolean COMPANY = true;
		boolean PLATFORM = true;
		//for storage reasons 
		String year = "";
		String title = "";
		
		try{
			reader = new BufferedReader(new FileReader(new File("products.txt")));
            line = reader.readLine();
			while(!(line.trim().equals("ITEM_LIST") | line.trim().equals("item_list"))){
				line = reader.readLine();
			}
			while(!(line.trim().equals("{"))){
				line = reader.readLine();
			}
			while (line != null){
						if (line.trim().equals("ITEM") | line.trim().equals("item")){
							line = reader.readLine();
							while(!(line.trim().startsWith("{"))){
								line = reader.readLine();
							}
							if(line.trim().equals("{")){
								reader.mark(2000);
								line = reader.readLine();
								while( !(line.trim().startsWith("ITEM_TYPE") | line.trim().startsWith("item_type")) & !(line.trim().equals("}"))){
									
									ITEM_TYPE = false;
									
									line = reader.readLine();
								}//item_type
								if (line.trim().startsWith("ITEM_TYPE") | line.trim().startsWith("item_type")){
									
									if (line.trim().substring(10).equals("movie")){
										reader.reset();
										reader.mark(2000);
										line = reader.readLine();
										while (!(line.trim().startsWith("SUB_TYPE") | line.trim().startsWith("sub_type")) & !(line.trim().equals("}"))){
											
									
											SUB_TYPE = false;
											line = reader.readLine();
										}//sub_type
										if (line.trim().startsWith("SUB_TYPE") | line.trim().startsWith("sub_type")){
											SUB_TYPE = true;
											if (line.trim().substring(9).trim().equals("DVD")){
												reader.reset();
												reader.mark(2000);
												line = reader.readLine();
												while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													
													
													YEAR = false;
													
													line = reader.readLine();
												}//year
												if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
													YEAR = true;
													year = line.trim().substring(5).trim();
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														
														
														TITLE = false;
														
														line= reader.readLine();
													}//title
													if (line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
														TITLE = true;
														title = line.trim().substring(6).trim();
														if(year.contains("2019")){
															product = new DVD(true);
														}
														else{
															product = new DVD(false);
														}
														((DVD)product).setYear(year);
														((DVD)product).setTitle(title);
														//Cast
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("CAST") | line.trim().startsWith("cast")) & !(line.trim().equals("}"))){
															
															
															CAST = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("CAST") | line.trim().startsWith("cast")){
															CAST = true;
															((DVD)product).setCast(line.trim().substring(5).trim());
														}
														if( CAST == false){
															((DVD)product).setCast("No information for the cast");
														}
														//Director
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")) & !(line.trim().equals("}"))){
															
															
															DIRECTOR = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")){
															DIRECTOR = true;
															((DVD)product).setDirector(line.trim().substring(9).trim());
														}
														if( DIRECTOR == false){
															((DVD)product).setDirector("No information for the Director");
														}
														//Category
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															
															
															CATEGORY = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
															CATEGORY = true;
															((DVD)product).setCategory(line.trim().substring(9).trim());
														}
														if( CATEGORY == false ){
															((DVD)product).setCategory("No information the category");
														}
														//Copies
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															
															
															COPIES = true;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
															try{
																((DVD)product).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
															}	
															catch(NumberFormatException e){
																((DVD)product).setCopies(1);
															}
																				
														}
														if( COPIES == false){
															((DVD)product).setCopies(1);
														}
														//ScreenWriter
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")) & !(line.trim().equals("}"))){
															
															
															SCREENWRITER = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")){
															SCREENWRITER = true;
															((DVD)product).setScreenWriter(line.trim().substring(13));
														}
														if( SCREENWRITER == false){
															((DVD)product).setScreenWriter("No information for th screenwriter");
														}
														//Production
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")) & !(line.trim().equals("}"))){
															
															
															PRODUCTION = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")){
															PRODUCTION = true;
															((DVD)product).setProduction(line.trim().substring(11).trim());
														}
														if( PRODUCTION == false){
															((DVD)product).setProduction("No information for the production");
														}
														
														moviesGames.add(product);
																		
													}
													if (TITLE == false){
													System.out.println("Title is essential for the creation of a product");
													}
												}
												if(YEAR == false){
													System.out.println(" Year is essential for the creation of a product");
												}
											}// DVD
											else if (line.trim().substring(9).trim().equals("BlueRay")){
												reader.reset();
												reader.mark(2000);
												line = reader.readLine();
												while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													
													
													YEAR = false;
													
													line = reader.readLine();
												}//year
												if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
													YEAR = true;
													year = line.trim().substring(5).trim();
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														
														
														TITLE = false;
														
														line= reader.readLine();
													}//title
													if (line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
														TITLE = true;
														title = line.trim().substring(6).trim();
														product = new blueRay();
														((blueRay)product).setYear(year);
														((blueRay)product).setTitle(title);
														//Cast
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("CAST") | line.trim().startsWith("cast")) & !(line.trim().equals("}"))){
															
															
															CAST = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("CAST") | line.trim().startsWith("cast")){
															CAST = true;
															((blueRay)product).setCast(line.trim().substring(5).trim());
														}
														if( CAST == false){
															((blueRay)product).setCast("No information for the cast");
														}
														//Director
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")) & !(line.trim().equals("}"))){
															
															
															DIRECTOR = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")){
															DIRECTOR = true;
															((blueRay)product).setDirector(line.trim().substring(9).trim());
														}
														if( DIRECTOR == false){
															((blueRay)product).setDirector("No information for the Director");
														}
														//Category
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															
															
															CATEGORY = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
															CATEGORY = true;
															((blueRay)product).setCategory(line.trim().substring(9).trim());
														}
														if( CATEGORY == false ){
															((blueRay)product).setCategory("No information the category");
														}
														//Copies
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															
															
															COPIES = true;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
															try{
																((blueRay)product).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
															}	
															catch(NumberFormatException e){
																((blueRay)product).setCopies(1);
															}
																				
														}
														if( COPIES == false){
															((blueRay)product).setCopies(1);
														}
														//ScreenWriter
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")) & !(line.trim().equals("}"))){
															
															
															SCREENWRITER = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")){
															SCREENWRITER = true;
															((blueRay)product).setScreenWriter(line.trim().substring(13));
														}
														if( SCREENWRITER == false){
															((blueRay)product).setScreenWriter("No information for th screenwriter");
														}
														//Production
														reader.reset();
														reader.mark(2000);
														line = reader.readLine();
														while(!(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")) & !(line.trim().equals("}"))){
															
															
															PRODUCTION = false;
															
															line = reader.readLine();
														}
														if(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")){
															PRODUCTION = true;
															((blueRay)product).setProduction(line.trim().substring(11).trim());
														}
														if( PRODUCTION == false){
															((blueRay)product).setProduction("No information for the production");
														}
														
														moviesGames.add(product);
																		
													}
													if (TITLE == false){
													System.out.println("Title is essential for the creation of a product");
													}
												}
												if(YEAR == false){
													System.out.println(" Year is essential for the creation of a product");
												}	
											}// BlueRay
											else{
												System.out.println("Inappropriate sub_type");	
											}//invalid sub_type
										}//sub_type
										if(SUB_TYPE == false){
											reader.reset();
											reader.mark(2000);
											line = reader.readLine();
											while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													
													
												YEAR = false;
													
												line = reader.readLine();
											}//year
											if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
												YEAR = true;
												year = line.trim().substring(5).trim();
												reader.reset();
												reader.mark(2000);
												line = reader.readLine();
												while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														
														
													TITLE = false;
														
													line= reader.readLine();
												}//title
												if (line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
													TITLE = true;
													title = line.trim().substring(6).trim();
													if(year.contains("2019")){
														product1 = new DVD(true);
													}
													else{
														product1 = new DVD(false);
													}
													
													product2 = new blueRay();
													((DVD)product1).setYear(year);
													((blueRay)product2).setYear(year);
													((DVD)product1).setTitle(title);
													((blueRay)product2).setTitle(title);
													//Cast
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("CAST") | line.trim().startsWith("cast")) & !(line.trim().equals("}"))){
															
															
														CAST = false;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("CAST") | line.trim().startsWith("cast")){
														CAST = true;
														((DVD)product1).setCast(line.trim().substring(5).trim());
														((blueRay)product2).setCast(line.trim().substring(5).trim());
													}
													if( CAST == false){
														((DVD)product1).setCast("No information for the cast");
														((blueRay)product2).setCast("No information for the cast");
													}
													//Director
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")) & !(line.trim().equals("}"))){
															
															
														DIRECTOR = false;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("DIRECTOR") | line.trim().startsWith("director")){
														DIRECTOR = true;
														((DVD)product1).setDirector(line.trim().substring(9).trim());
														((blueRay)product2).setDirector(line.trim().substring(9).trim());
													}
													if( DIRECTOR == false){
														((DVD)product1).setDirector("No information for the Director");
														((blueRay)product2).setDirector("No information for the Director");
													}
													//Category
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															
															
														CATEGORY = false;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
														CATEGORY = true;
														((DVD)product1).setCategory(line.trim().substring(9).trim());
														((blueRay)product2).setCategory(line.trim().substring(9).trim());
													}
													if( CATEGORY == false ){
														((DVD)product1).setCategory("No information the category");
														((blueRay)product2).setCategory("No information the category");
													}
													//Copies
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															
															
														COPIES = true;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
														COPIES = true;
														try{
															((DVD)product1).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
															((blueRay)product2).setCopies(Integer.parseInt(line.trim().substring(7).trim()));						
														}	
														catch(NumberFormatException e){
															((DVD)product1).setCopies(1);
															((blueRay)product2).setCopies(1);
														}
																				
													}
													if( COPIES == false){
														((DVD)product1).setCopies(1);
														((blueRay)product2).setCopies(1);
													}
													//ScreenWriter
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")) & !(line.trim().equals("}"))){
															
															
														SCREENWRITER = false;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("SCREENWRITER") | line.trim().startsWith("screenwriter")){
														SCREENWRITER = true;
														((DVD)product1).setScreenWriter(line.trim().substring(13));
														((blueRay)product2).setScreenWriter(line.trim().substring(13));
													}
													if( SCREENWRITER == false){
														((DVD)product1).setScreenWriter("No information for th screenwriter");
														((blueRay)product2).setScreenWriter("No information for th screenwriter");
													}
													//Production
													reader.reset();
													reader.mark(2000);
													line = reader.readLine();
													while(!(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")) & !(line.trim().equals("}"))){
															
															
														PRODUCTION = false;
															
														line = reader.readLine();
													}
													if(line.trim().startsWith("PRODUCTION") | line.trim().startsWith("production")){
														PRODUCTION = true;
														((DVD)product1).setProduction(line.trim().substring(11).trim());
														((blueRay)product2).setProduction(line.trim().substring(11).trim());
													}
													if( PRODUCTION == false){
														((DVD)product1).setProduction("No information for the production");
														((blueRay)product2).setProduction("No information for the production");
													}
														
													moviesGames.add(product1);
													moviesGames.add(product2);					
												}
												if (TITLE == false){
													System.out.println("Title is essential for the creation of a product");
												}
											}
											if(YEAR == false){
												System.out.println(" Year is essential for the creation of a product");
											}
										}// SUB_TYPE == false
									}//movie
									else if (line.trim().substring(10).equals("game")){
										reader.reset();
										reader.mark(2000);
										reader.readLine();
										while (!(line.trim().startsWith("PLATFORM") | line.trim().startsWith("platform")) & !(line.trim().equals("}"))){
											PLATFORM = false;
											reader.readLine();
										}
										if (line.trim().startsWith("PLATFORM") | line.trim().startsWith("platform")){
											PLATFORM = true;
											if(line.trim().substring(9).trim().equals("PS4")){
												reader.reset();
												reader.mark(2000);
												reader.readLine();
												while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													YEAR = false;
													reader.readLine();
												}
												if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
													YEAR = true;
													year = line.trim().substring(5).trim();
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														TITLE = false;
														reader.readLine();
													}
													if(line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
														TITLE = true;
														title = line.trim().substring(6).trim();
														product = new PlayStation();
														((PlayStation)product).setTitle(title);
														((PlayStation)product).setYear(year);
														//Category
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															CATEGORY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
															CATEGORY = true;
															((PlayStation)product).setCategory(line.trim().substring(8).trim());
														}
														if(CATEGORY == false){
															((PlayStation)product).setCategory("No information for the category");
														}
														//Company
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")) & !(line.trim().equals("}"))){
															COMPANY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")){
															COMPANY = true;
															((PlayStation)product).setCompany(line.trim().substring(8).trim());
														}
														if(COMPANY == false){
															((PlayStation)product).setCompany("No information for the company");
														}
														//Copies
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															COPIES = false; 
															reader.readLine();
														}
														if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
															try{
																((PlayStation)product).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
															}	
															catch(NumberFormatException e){
																((PlayStation)product).setCopies(1);
															}
														}
														if(COPIES == false){
															((PlayStation)product).setCopies(1);
														}
														
														moviesGames.add(product);
													}
													if(TITLE == false){
														System.out.println("Title is essential for the creation of a product");
													}
												}//year
												if(YEAR == false){
													System.out.println("Year is essential for the creation of an object");
												}
											}//playstation
											else if(line.trim().substring(9).trim().equals("Xbox")){
												reader.reset();
												reader.mark(2000);
												reader.readLine();
												while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													YEAR = false;
													reader.readLine();
												}
												if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
													YEAR = true;
													year = line.trim().substring(5).trim();
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														TITLE = false;
														reader.readLine();
													}
													if(line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
														TITLE = true;
														title = line.trim().substring(6).trim();
														product = new Xbox();
														((Xbox)product).setTitle(title);
														((Xbox)product).setYear(year);
														//Category
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															CATEGORY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
															CATEGORY = true;
															((Xbox)product).setCategory(line.trim().substring(8).trim());
														}
														if(CATEGORY == false){
															((Xbox)product).setCategory("No information for the category");
														}
														//Company
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")) & !(line.trim().equals("}"))){
															COMPANY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")){
															COMPANY = true;
															((Xbox)product).setCompany(line.trim().substring(8).trim());
														}
														if(COMPANY == false){
															((Xbox)product).setCompany("No information for the company");
														}
														//Copies
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															COPIES = false; 
															reader.readLine();
														}
														if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
															try{
																((Xbox)product).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
															}	
															catch(NumberFormatException e){
																((Xbox)product).setCopies(1);
															}
														}
														if(COPIES == false){
															((Xbox)product).setCopies(1);
														}
														
														moviesGames.add(product);
													}
													if(TITLE == false){
														System.out.println("Title is essential for the creation of a product");
													}
												}//year
												if(YEAR == false){
													System.out.println("Year is essential for the creation of an object");
												}
											}//Xbox
											else if(line.trim().substring(9).trim().equals("Nintedo")){
												reader.reset();
												reader.mark(2000);
												reader.readLine();
												while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
													YEAR = false;
													reader.readLine();
												}
												if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
													YEAR = true;
													year = line.trim().substring(5).trim();
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
														TITLE = false;
														reader.readLine();
													}
													if(line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
														TITLE = true;
														title = line.trim().substring(6).trim();
														product = new Nintedo();
														((Nintedo)product).setTitle(title);
														((Nintedo)product).setYear(year);
														//Category
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
															CATEGORY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
															CATEGORY = true;
															((Nintedo)product).setCategory(line.trim().substring(8).trim());
														}
														if(CATEGORY == false){
															((Nintedo)product).setCategory("No information for the category");
														}
														//Company
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")) & !(line.trim().equals("}"))){
															COMPANY = false;
															reader.readLine();
														}
														if(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")){
															COMPANY = true;
															((Nintedo)product).setCompany(line.trim().substring(8).trim());
														}
														if(COMPANY == false){
															((Nintedo)product).setCompany("No information for the company");
														}
														//Copies
														reader.reset();
														reader.mark(2000);
														reader.readLine();
														while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
															COPIES = false; 
															reader.readLine();
														}
														if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
															try{
																((Nintedo)product).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
															}	
															catch(NumberFormatException e){
																((Nintedo)product).setCopies(1);
															}
														}
														if(COPIES == false){
															((Nintedo)product).setCopies(1);
														}
														
														moviesGames.add(product);
													}
													if(TITLE == false){
														System.out.println("Title is essential for the creation of a product");
													}
												}//year
												if(YEAR == false){
													System.out.println("Year is essential for the creation of an object");
												}
											}//Nintedo
											
										}//platform
										if(PLATFORM == false){
											reader.reset();
											reader.mark(2000);
											reader.readLine();
											while(!(line.trim().startsWith("YEAR") | line.trim().startsWith("year")) & !(line.trim().equals("}"))){
												YEAR = false;
												reader.readLine();
											}
											if(line.trim().startsWith("YEAR") | line.trim().startsWith("year")){
												YEAR = true;
												year = line.trim().substring(5).trim();
												reader.reset();
												reader.mark(2000);
												reader.readLine();
												while(!(line.trim().startsWith("TITLE") | line.trim().startsWith("title")) & !(line.trim().equals("}"))){
													TITLE = false;
													reader.readLine();
												}
												if(line.trim().startsWith("TITLE") | line.trim().startsWith("title")){
													TITLE = true;
													title = line.trim().substring(6).trim();
													product1 = new Nintedo();
													product2 = new PlayStation();
													product3 = new Xbox();
													((Nintedo)product1).setTitle(title);
													((PlayStation)product2).setTitle(title);
													((Xbox)product3).setTitle(title);
													((Nintedo)product1).setYear(year);
													((PlayStation)product2).setYear(year);
													((Xbox)product3).setYear(year);
													//Category
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")) & !(line.trim().equals("}"))){
														CATEGORY = false;
														reader.readLine();
													}
													if(line.trim().startsWith("CATEGORY") | line.trim().startsWith("category")){
														CATEGORY = true;
														((Nintedo)product1).setCategory(line.trim().substring(8).trim());
														((PlayStation)product2).setCategory(line.trim().substring(8).trim());
														((Xbox)product3).setCategory(line.trim().substring(8).trim());
													}
													if(CATEGORY == false){
														((Nintedo)product1).setCategory("No information for the category");
														((PlayStation)product2).setCategory("No information for the category");
														((Xbox)product3).setCategory("No information for the category");
													}
													//Company
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")) & !(line.trim().equals("}"))){
														COMPANY = false;
														reader.readLine();
													}
													if(line.trim().startsWith("COMPANY") | line.trim().startsWith("company")){
														COMPANY = true;
														((Nintedo)product1).setCompany(line.trim().substring(8).trim());
														((PlayStation)product2).setCompany(line.trim().substring(8).trim());
														((Xbox)product3).setCompany(line.trim().substring(8).trim());
													}
													if(COMPANY == false){
														((Nintedo)product1).setCompany("No information for the company");
														((PlayStation)product2).setCompany("No information for the company");
														((Xbox)product3).setCompany("No information for the company");
													}
													//Copies
													reader.reset();
													reader.mark(2000);
													reader.readLine();
													while(!(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")) & !(line.trim().equals("}"))){
														COPIES = false; 
														reader.readLine();
													}
													if(line.trim().startsWith("COPIES") | line.trim().startsWith("copies")){
															COPIES = true;
														try{
															((Nintedo)product1).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
															((PlayStation)product2).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
															((Xbox)product3).setCopies(Integer.parseInt(line.trim().substring(7).trim()));
																					
														}	
														catch(NumberFormatException e){
															((Nintedo)product1).setCopies(1);
															((PlayStation)product2).setCopies(1);
															((Xbox)product3).setCopies(1);
														}
													}
													if(COPIES == false){
														((Nintedo)product1).setCopies(1);
														((PlayStation)product2).setCopies(1);
														((Xbox)product3).setCopies(1);
													}
														
													moviesGames.add(product);
												}
												if(TITLE == false){
													System.out.println("Title is essential for the creation of a product");
												}
											}//year
											if(YEAR == false){
												System.out.println("Year is essential for the creation of an object");
											}
										}//all three
									}//game
									else{
										System.out.println("invalid item type");
									}//invalid item type
								}//item_type
								if(ITEM_TYPE == false){
									System.out.println(" Item_type is essential for the creation of a product");
								}
							}
						}
				line = reader.readLine();
			}
			reader.close();
		}
		
		catch (IOException e){
			System.err.println("Error Reading File...");
		}
		
		
		
		
		
		
		
		
	}
		
		
	
}