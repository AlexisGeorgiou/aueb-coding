import java.util.*;
import java.io.*;

public class rentalsList{
	
	private LinkedList<rent> rentals = new LinkedList<rent>();
	Scanner in = new Scanner(System.in);
	String ans;

	public void addRent(rent m){
		
		rentals.add(m);
	
	}
	public void removeRent(rent r){
		rentals.remove(rentals.indexOf(r));
	}
	public void removeRent(String m){
		
		for (int i=rentals.size()-1; i>=0; i--)
			if (rentals.get(i).getRentable().getName().contains(m)){
				System.out.print(rentals.get(i));
				System.out.print("Remove ? (y/n): ");
				ans = in.nextLine();
				if (ans.equals("y")) {
					rentals.remove(i);
					System.out.println("Removed ...");
				}
				else System.out.println("Skipped ...");
			}
	}
	
	public rent get(int i){
		return rentals.get(i);
	}
	
	public void showAll(){
		int i = 0;
		
		while(i <= rentals.size()-1){
			System.out.println(i + "." + rentals.get(i));
			i+=1;
		}
	}
	
	public int getSize(){
		return rentals.size();
	}
	
	public boolean isEmpty(){
		if (rentals.size() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void readFile(availableList a){
		
		BufferedReader reader = null;
		rent rental = null;
		int date = 1;
		int month = 1;
		int year;
        String line;
		boolean RENTAL = true;
		
        try {
			reader = new BufferedReader(new FileReader(new File("rentals.txt")));
            line = reader.readLine();
            while (!(line.trim().equals("RENTAL_LIST") | line.trim().equals("rental_list"))){
				line = reader.readLine();
			}
			while(!(line.trim().equals("{"))){
				line = reader.readLine();
			}
			while (line != null) {
						if (line.trim().equals("RENTAL") | line.trim().equals("rental")){
							line = reader.readLine();
							while(!(line.trim().equals("{"))){
								line = reader.readLine();
							}
							if(line.trim().equals("{")){
								line = reader.readLine();
								while(!(line.trim().startsWith("ITEM_TYPE"))){
									line = reader.readLine();
								}
								if (line.trim().substring(10).trim().equals("MOVIE") |  line.trim().substring(10).trim().equals("movie") ){
									line = reader.readLine();
									while(!(line.trim().startsWith("SUB_TYPE"))){
										line = reader.readLine();
									}
									if (line.trim().substring(9).trim().equals("DVD") | line.trim().substring(9).trim().equals("dvd")){
										line = reader.readLine();
										while(!(line.trim().startsWith("TITLE"))){
											line = reader.readLine();
										}
										rental = new rent();
										int i = 0;
										while((i <= a.getSize()-1) & RENTAL == true){
											if (a.get(i).getName().equals(line.trim().substring(6).trim()) & a.get(i) instanceof DVD){
												RENTAL = false;
												rental.setRentable(a.get(i));
												line = reader.readLine();
												while(!(line.trim().startsWith("NAME"))){
													line = reader.readLine();
												}
												rental.setName(line.trim().substring(5).trim());
												line = reader.readLine();
												while(!(line.trim().startsWith("DATE"))){
													line = reader.readLine();
												}
												date = Integer.parseInt(line.trim().substring(7,9).trim());
												if (line.trim().substring(9,11).trim().equals("Jan") | line.trim().substring(10,12).trim().equals("Jan")){
													month = 1;
												}
												else if (line.trim().substring(9,11).trim().equals("Feb") | line.trim().substring(10,12).trim().equals("Feb")){
													month = 2;
												}
												else if (line.trim().substring(9,11).trim().equals("Mar") | line.trim().substring(10,12).trim().equals("Mar")){
													month = 3;
												}
												else if (line.trim().substring(9,11).trim().equals("Apr") | line.trim().substring(10,12).trim().equals("Apr")){
													month = 4;
												}
												else if (line.trim().substring(9,11).trim().equals("May") | line.trim().substring(10,12).trim().equals("May")){
													month = 5;
												}
												else if (line.trim().substring(9,11).trim().equals("Jun") | line.trim().substring(10,12).trim().equals("Jun")){
													month = 6;
												}
												else if (line.trim().substring(9,11).trim().equals("Jul") | line.trim().substring(10,12).trim().equals("Jul")){
													month = 7;
												}
												else if(line.trim().substring(9,11).trim().equals("Aug") | line.trim().substring(10,12).trim().equals("Aug")){
													month = 8;
												}
												else if (line.trim().substring(9,11).trim().equals("Sep") | line.trim().substring(10,12).trim().equals("Sep")){
													month = 9;
												}
												else if (line.trim().substring(9,11).trim().equals("Oct") | line.trim().substring(10,12).trim().equals("Oct")){
													month = 10;
												}
												else if (line.trim().substring(9,11).trim().equals("Nov") | line.trim().substring(10,12).trim().equals("Nov")){
													month = 11;
												}
												else if (line.trim().substring(9,11).trim().equals("Dec") | line.trim().substring(10,12).trim().equals("Dec")){
													month = 12;
												}
												rental.setDate(date, month, 2019 );
												line = reader.readLine();
												while(!(line.trim().startsWith("CODE"))){
													line = reader.readLine();
												}
												rental.setCode(Integer.parseInt(line.trim().substring(5).trim()));
												rentals.add(rental);
											}
											i += 1;
										    
										}
										if (RENTAL == true){
											System.out.println("This rental is about a not available movie or Game");
										}
									}//dvd
									else if (line.trim().substring(9).trim().equals("BLUERAY") | line.trim().substring(9).trim().equals("blueRay") ){
										line = reader.readLine();
										while(!(line.trim().startsWith("TITLE"))){
											line = reader.readLine();
										}
										rental = new rent();
										int i = 0;
										while((i <= a.getSize()-1) & RENTAL == true){
											if (a.get(i).getName().equals(line.trim().substring(6).trim()) & a.get(i) instanceof blueRay){
												RENTAL = false;
												rental.setRentable(a.get(i));
												line = reader.readLine();
												while(!(line.trim().startsWith("NAME"))){
													line = reader.readLine();
												}
												rental.setName(line.trim().substring(5).trim());
												line = reader.readLine();
												while(!(line.trim().startsWith("DATE"))){
													line = reader.readLine();
												}
												date = Integer.parseInt(line.trim().substring(7,9).trim());
												if (line.trim().substring(9,11).trim().equals("Jan") | line.trim().substring(10,12).trim().equals("Jan")){
													month = 1;
												}
												else if (line.trim().substring(9,11).trim().equals("Feb") | line.trim().substring(10,12).trim().equals("Feb")){
													month = 2;
												}
												else if (line.trim().substring(9,11).trim().equals("Mar") | line.trim().substring(10,12).trim().equals("Mar")){
													month = 3;
												}
												else if (line.trim().substring(9,11).trim().equals("Apr") | line.trim().substring(10,12).trim().equals("Apr")){
													month = 4;
												}
												else if (line.trim().substring(9,11).trim().equals("May") | line.trim().substring(10,12).trim().equals("May")){
													month = 5;
												}
												else if (line.trim().substring(9,11).trim().equals("Jun") | line.trim().substring(10,12).trim().equals("Jun")){
													month = 6;
												}
												else if (line.trim().substring(9,11).trim().equals("Jul") | line.trim().substring(10,12).trim().equals("Jul")){
													month = 7;
												}
												else if(line.trim().substring(9,11).trim().equals("Aug") | line.trim().substring(10,12).trim().equals("Aug")){
													month = 8;
												}
												else if (line.trim().substring(9,11).trim().equals("Sep") | line.trim().substring(10,12).trim().equals("Sep")){
													month = 9;
												}
												else if (line.trim().substring(9,11).trim().equals("Oct") | line.trim().substring(10,12).trim().equals("Oct")){
													month = 10;
												}
												else if (line.trim().substring(9,11).trim().equals("Nov") | line.trim().substring(10,12).trim().equals("Nov")){
													month = 11;
												}
												else if (line.trim().substring(9,11).trim().equals("Dec") | line.trim().substring(10,12).trim().equals("Dec")){
													month = 12;
												}
												rental.setDate(date, month, 2019 );
												line = reader.readLine();
												while(!(line.trim().startsWith("CODE"))){
													line = reader.readLine();
												}
												rental.setCode(Integer.parseInt(line.trim().substring(5).trim()));
												rentals.add(rental);
											}
											i += 1;
										    
										}
										if (RENTAL == true){
											System.out.println("This rental is about a not available movie or Game");
										}
									}//blueRay
									else{
										System.out.println("Inappropriate sub type");
									}//wrong type
								}//movie
								else if (line.trim().substring(10).trim() == "GAME" |  line.trim().substring(10).trim() == "game" ){
									reader.mark(70);
									line = reader.readLine();
									while(!(line.trim().startsWith("PLATFORM") | line.trim().startsWith("platform")) & !(line.trim().equals("}"))){
										if(line.trim().startsWith("PLATFORM") | line.trim().startsWith("platform")){
											if (line.trim().substring(8).trim().equals("PS4") | line.trim().substring(8).trim().equals("Playstation") | line.trim().substring(8).trim().equals("PLAYSTATION") ){
												rental = new rent();
												reader.reset();
												line = reader.readLine();
												while(!(line.trim().startsWith("TITLE"))){
													line = reader.readLine();
												}
												int i = 0;
												while((i <= a.getSize()-1) & RENTAL == true){
													if (a.get(i).getName().equals(line.trim().substring(6)) & a.get(i) instanceof PlayStation){
														RENTAL = false;
														rental.setRentable(a.get(i));
														line = reader.readLine();
														while(!(line.trim().startsWith("NAME"))){
															line = reader.readLine();
														}
														rental.setName(line.trim().substring(5).trim());
														line = reader.readLine();
														while(!(line.trim().startsWith("DATE"))){
															line = reader.readLine();
														}
														date = Integer.parseInt(line.trim().substring(7,9).trim());
														if (line.trim().substring(9,11).trim().equals("Jan") | line.trim().substring(10,12).trim().equals("Jan")){
															month = 1;
														}
														else if (line.trim().substring(9,11).trim().equals("Feb") | line.trim().substring(10,12).trim().equals("Feb")){
															month = 2;
														}
														else if (line.trim().substring(9,11).trim().equals("Mar") | line.trim().substring(10,12).trim().equals("Mar")){
															month = 3;
														}
														else if (line.trim().substring(9,11).trim().equals("Apr") | line.trim().substring(10,12).trim().equals("Apr")){
															month = 4;
														}
														else if (line.trim().substring(9,11).trim().equals("May") | line.trim().substring(10,12).trim().equals("May")){
															month = 5;
														}
														else if (line.trim().substring(9,11).trim().equals("Jun") | line.trim().substring(10,12).trim().equals("Jun")){
															month = 6;
														}
														else if (line.trim().substring(9,11).trim().equals("Jul") | line.trim().substring(10,12).trim().equals("Jul")){
															month = 7;
														}
														else if(line.trim().substring(9,11).trim().equals("Aug") | line.trim().substring(10,12).trim().equals("Aug")){
															month = 8;
														}
														else if (line.trim().substring(9,11).trim().equals("Sep") | line.trim().substring(10,12).trim().equals("Sep")){
															month = 9;
														}
														else if (line.trim().substring(9,11).trim().equals("Oct") | line.trim().substring(10,12).trim().equals("Oct")){
															month = 10;
														}
														else if (line.trim().substring(9,11).trim().equals("Nov") | line.trim().substring(10,12).trim().equals("Nov")){
															month = 11;
														}
														else if (line.trim().substring(9,11).trim().equals("Dec") | line.trim().substring(10,12).trim().equals("Dec")){
															month = 12;
														}
														rental.setDate(date, month, 2019 );
														line = reader.readLine();
														while(!(line.trim().startsWith("CODE"))){
															line = reader.readLine();
														}
														rental.setCode(Integer.parseInt(line.trim().substring(5).trim()));
														rentals.add(rental);
													}
													i+=1;
												}
												if (RENTAL == true){
													System.out.println("This rental is about a not available movie or Game");
												}
											}// playstation
											else if (line.trim().substring(8).trim().equals("XBOX") | line.trim().substring(8).trim().equals("Xbox")){
												rental = new rent();
												reader.reset();
												line = reader.readLine();
												while(!(line.trim().startsWith("TITLE"))){
													line = reader.readLine();
												}
												int i = 0;
												while((i <= a.getSize()-1) & RENTAL == true){
													if (a.get(i).getName().equals(line.trim().substring(6)) & a.get(i) instanceof Xbox){
														RENTAL = false;
														rental.setRentable(a.get(i));
														line = reader.readLine();
														while(!(line.trim().startsWith("NAME"))){
															line = reader.readLine();
														}
														rental.setName(line.trim().substring(5).trim());
														line = reader.readLine();
														while(!(line.trim().startsWith("DATE"))){
															line = reader.readLine();
														}
														date = Integer.parseInt(line.trim().substring(7,9).trim());
														if (line.trim().substring(9,11).trim().equals("Jan") | line.trim().substring(10,12).trim().equals("Jan")){
															month = 1;
														}
														else if (line.trim().substring(9,11).trim().equals("Feb") | line.trim().substring(10,12).trim().equals("Feb")){
															month = 2;
														}
														else if (line.trim().substring(9,11).trim().equals("Mar") | line.trim().substring(10,12).trim().equals("Mar")){
															month = 3;
														}
														else if (line.trim().substring(9,11).trim().equals("Apr") | line.trim().substring(10,12).trim().equals("Apr")){
															month = 4;
														}
														else if (line.trim().substring(9,11).trim().equals("May") | line.trim().substring(10,12).trim().equals("May")){
															month = 5;
														}
														else if (line.trim().substring(9,11).trim().equals("Jun") | line.trim().substring(10,12).trim().equals("Jun")){
															month = 6;
														}
														else if (line.trim().substring(9,11).trim().equals("Jul") | line.trim().substring(10,12).trim().equals("Jul")){
															month = 7;
														}
														else if(line.trim().substring(9,11).trim().equals("Aug") | line.trim().substring(10,12).trim().equals("Aug")){
															month = 8;
														}
														else if (line.trim().substring(9,11).trim().equals("Sep") | line.trim().substring(10,12).trim().equals("Sep")){
															month = 9;
														}
														else if (line.trim().substring(9,11).trim().equals("Oct") | line.trim().substring(10,12).trim().equals("Oct")){
															month = 10;
														}
														else if (line.trim().substring(9,11).trim().equals("Nov") | line.trim().substring(10,12).trim().equals("Nov")){
															month = 11;
														}
														else if (line.trim().substring(9,11).trim().equals("Dec") | line.trim().substring(10,12).trim().equals("Dec")){
															month = 12;
														}
														rental.setDate(date, month, 2019 );
														line = reader.readLine();
														while(!(line.trim().startsWith("CODE"))){
															line = reader.readLine();
														}
														rental.setCode(Integer.parseInt(line.trim().substring(5).trim()));
														rentals.add(rental);
													}
													i+=1;
												}
												if (RENTAL == true){
													System.out.println("This rental is about a not available movie or Game");
												}
											}//Xbox
											else if (line.trim().substring(8).trim().equals("Nintedo") | line.trim().substring(8).trim().equals("NINTEDO")){
												rental = new rent();
												reader.reset();
												line = reader.readLine();
												while(!(line.trim().startsWith("TITLE"))){
													line = reader.readLine();
												}
												int i = 0;
												while((i <= a.getSize()-1) & RENTAL == true){
													if (a.get(i).getName().equals(line.trim().substring(6)) & a.get(i) instanceof Nintedo){
														RENTAL = false;
														rental.setRentable(a.get(i));
														line = reader.readLine();
														while(!(line.trim().startsWith("NAME"))){
															line = reader.readLine();
														}
														rental.setName(line.trim().substring(5).trim());
														line = reader.readLine();
														while(!(line.trim().startsWith("DATE"))){
															line = reader.readLine();
														}
														date = Integer.parseInt(line.trim().substring(7,9).trim());
														if (line.trim().substring(9,11).trim().equals("Jan") | line.trim().substring(10,12).trim().equals("Jan")){
															month = 1;
														}
														else if (line.trim().substring(9,11).trim().equals("Feb") | line.trim().substring(10,12).trim().equals("Feb")){
															month = 2;
														}
														else if (line.trim().substring(9,11).trim().equals("Mar") | line.trim().substring(10,12).trim().equals("Mar")){
															month = 3;
														}
														else if (line.trim().substring(9,11).trim().equals("Apr") | line.trim().substring(10,12).trim().equals("Apr")){
															month = 4;
														}
														else if (line.trim().substring(9,11).trim().equals("May") | line.trim().substring(10,12).trim().equals("May")){
															month = 5;
														}
														else if (line.trim().substring(9,11).trim().equals("Jun") | line.trim().substring(10,12).trim().equals("Jun")){
															month = 6;
														}
														else if (line.trim().substring(9,11).trim().equals("Jul") | line.trim().substring(10,12).trim().equals("Jul")){
															month = 7;
														}
														else if(line.trim().substring(9,11).trim().equals("Aug") | line.trim().substring(10,12).trim().equals("Aug")){
															month = 8;
														}
														else if (line.trim().substring(9,11).trim().equals("Sep") | line.trim().substring(10,12).trim().equals("Sep")){
															month = 9;
														}
														else if (line.trim().substring(9,11).trim().equals("Oct") | line.trim().substring(10,12).trim().equals("Oct")){
															month = 10;
														}
														else if (line.trim().substring(9,11).trim().equals("Nov") | line.trim().substring(10,12).trim().equals("Nov")){
															month = 11;
														}
														else if (line.trim().substring(9,11).trim().equals("Dec") | line.trim().substring(10,12).trim().equals("Dec")){
															month = 12;
														}
														rental.setDate(date, month, 2019 );
														line = reader.readLine();
														while(!(line.trim().startsWith("CODE"))){
															line = reader.readLine();
														}
														rental.setCode(Integer.parseInt(line.trim().substring(5).trim()));
														rentals.add(rental);
													}
													i+=1;
												}
												if (RENTAL == true){
													System.out.println("This rental is about a not available movie or Game");
												}
											}//Nintedo
											else{
												System.out.println("Inappropriate platform type");
											}//invalid platform
											
										}
										line = reader.readLine();
									}
								}//games
								else{
									System.out.println("Inappropriate item type");
								}//wrong type
								
							}
						}
					
				
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e) {
            System.out.println	("Error reading line ...");
		}
	}
	
	public void writeFile(){
		FileWriter writer = null;
		String month = "";
		try	{
			writer = new FileWriter(new File("rentals.txt"));
			writer.write("RENTAL_LIST" + "\n" + "{");
			for (rent rental:rentals){
				if(rental.getRentDate().getMonth() == 1){
					month = "January";
				}
				else if(rental.getRentDate().getMonth() == 2){
					month = "February";
				}
				else if(rental.getRentDate().getMonth() == 3){
					month = "March";
				}
				else if(rental.getRentDate().getMonth() == 4){
					month = "April";
				}
				else if(rental.getRentDate().getMonth() == 5){
					month = "May";
				}
				else if(rental.getRentDate().getMonth() == 6){
					month = "June";
				}
				else if(rental.getRentDate().getMonth() == 7){
					month = "July";
				}
				else if(rental.getRentDate().getMonth() == 8){
					month = "August";
				}
				else if(rental.getRentDate().getMonth() == 9){
					month = "September";
				}
				else if(rental.getRentDate().getMonth() == 10){
					month = "Octomber";
				}
				else if(rental.getRentDate().getMonth() == 11){
					month = "November";
				}
				else if(rental.getRentDate().getMonth() == 12){
					month = "December";
				}
				if (rental.getRentable() instanceof DVD) {
					writer.write ("\n" + "\t" + "RENTAL" + "\n" + "\t" + "{" +
								  "\n" + "\t" + "\t" + "ITEM_TYPE" + " movie" +
								  "\n" + "\t" + "\t" + "SUB_TYPE" + " DVD" +
								  "\n" + "\t" + "\t" + "TITLE" + " " + rental.getRentable().getName() +   
								  "\n" + "\t" + "\t" + "NAME" + " " + rental.getPerson() +   
								  "\n" + "\t" + "\t" + "DATE" + " " + "\"" + " " + rental.getRentDate().getDate() + " " + month + " " + rental.getRentDate().getYear() + "\"" + 
								  "\n" + "\t" + "\t" + "CODE" + " " + rental.getRentCode() +
								  "\n" + "\t" + "}");
				}//DVD
				else if (rental.getRentable() instanceof blueRay) {
					writer.write ("\n" + "\t" + "RENTAL" + "\n" + "\t" + "{" +
								  "\n" + "\t" + "\t" + "ITEM_TYPE" + " movie" +
								  "\n" + "\t" + "\t" + "SUB_TYPE" + " blueRay" +
								  "\n" + "\t" + "\t" + "TITLE" + " " +  rental.getRentable().getName() + 
								  "\n" + "\t" + "\t" + "NAME" + " " + rental.getPerson() +  
								  "\n" + "\t" + "\t" + "DATE" + " " + "\"" + " " + rental.getRentDate().getDate() + " " + month + " " + rental.getRentDate().getYear() + "\"" +
								  "\n" + "\t" + "\t" + "CODE" + " " + rental.getRentCode() +
								  "\n" + "\t" + "}");
				}//blueRay
				else if (rental.getRentable() instanceof PlayStation){
					writer.write ("\n" + "\t" + "RENTAL" + "\n" + "\t" + "{" +
								  "\n" + "\t" + "\t" + "ITEM_TYPE" + " game" +
								  "\n" + "\t" + "\t" + "TITLE" + " " + rental.getRentable().getName() + 
								  "\n" + "\t" + "\t" + "SUB_TYPE" + " " + "PS4" +  
								  "\n" + "\t" + "\t" + "NAME" + " " + rental.getPerson() +  
								  "\n" + "\t" + "\t" + "DATE" + " " + "\"" + " " + rental.getRentDate().getDate() + " " + month + " " + rental.getRentDate().getYear() + "\"" + 
								  "\n" + "\t" + "\t" + "CODE" + " " + rental.getRentCode() +
								  "\n" + "\t" + "}");
				}//PS4
				else if (rental.getRentable() instanceof Xbox){
					writer.write ("\n" + "\t" + "RENTAL" + "\n" + "\t" + "{" +
								  "\n" + "\t" + "\t" + "ITEM_TYPE" + " game" +
								  "\n" + "\t" + "\t" + "TITLE" + " " + rental.getRentable().getName() +  
								  "\n" + "\t" + "\t" + "SUB_TYPE" + " " +  "XBOX" +  
								  "\n" + "\t" + "\t" + "NAME" + " " + rental.getPerson() +  
								  "\n" + "\t" + "\t" + "DATE" + " " + "\"" + " " + rental.getRentDate().getDate() + " " + month + " " + rental.getRentDate().getYear() + "\"" + 
								  "\n" + "\t" + "\t" + "CODE" + " " + rental.getRentCode() + 
								  "\n" + "\t" + "}");
				}//Xbox
				else if (rental.getRentable() instanceof Nintedo){
					writer.write ("\n" + "\t" + "RENTAL" + "\n" + "\t" + "{" +
								  "\n" + "\t" + "\t" + "ITEM_TYPE" + " game" +
								  "\n" + "\t" + "\t" + "TITLE" + " " + rental.getRentable().getName() + 
								  "\n" + "\t" + "\t" + "SUB_TYPE" + " " + "Nintedo" + 
								  "\n" + "\t" + "\t" + "NAME" + " " + rental.getPerson() +  
								  "\n" + "\t" + "\t" + "DATE" + " " + "\"" + " " + rental.getRentDate().getDate() + " " + month + " " + rental.getRentDate().getYear() + "\"" + 
								  "\n" + "\t" + "\t" + "CODE" + " " + rental.getRentCode() + 
								  "\n" + "\t" + "}");
				}//Nintedo
			}
			writer.write("\n}");
			writer.close();
				
		}//try
			
		catch (IOException e) {
			System.err.println("Error writing file.");
		}
	}

}