import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class pass {
	static Scanner in = new Scanner(System.in);
	static String is[] = { "STOP", "ADD", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT"};
	static String ad[] = { "START", "END", "ORIGIN", "EQU", "LTORG"};
	static String dl[] = { "DC" , "DS" };
	static String cc[] = { "LT", "LE", "EQ", "GT", "GE", "ANY"};
	static int symcounter = 0 ;
	static int litcounter = 0 ;
	//data structures created symbol table, pool table, literal table
	static String sym[][] = new String[100][2];
	static String lit[][] = new String[100][2];
	static String ptab[][] = new String[100][2];
	public static void main(String args[]) throws Exception {

		int locate = 0;
		int litcount = 0;

		//created two file for input and output
		File file = new File("Input1.asm");
		File file1 = new File("output.asm");
		BufferedReader reader = new BufferedReader(new FileReader(file));     //reading the file
		BufferedWriter writer = new BufferedWriter(new FileWriter(file1));    //writing into the file

		String st;       //created a string

		String y, prev = null;
		int stp = 0;
		String ans;
		int k = 0;
		String buffer = "";
		//readline() reads the single line at a time
		while ((st = reader.readLine()) != null) {
			int isflag = 0;
			k++;
			//stringTokenizer breaks the whole string into token
			StringTokenizer splitted = new StringTokenizer(st);
			ans = "";
			//It checks if there is more tokens available.
			while (splitted.hasMoreTokens()) {
				y = splitted.nextToken();

				//checking the token with AD,IS,DL
				if (y.equals("START")) {
					locate = Integer.parseInt(splitted.nextToken());
					ans = "(AD,01)(C," + locate + ")";
					break;
				} else {

					if (searchis(y)) {
						if (y.equals("STOP")) {
							stp = 1;
						}
						ans += "(IS," + Integer.toString(indexis(y)) + ")";
						isflag = 1;
						locate += 1;
					} else if (searchad(y)) {

						if (y.equals("LTORG")) {
							locate += litcount;
							ans = "(AD,05)\n";
							while (litcount > 0) {
								lit[litcounter - litcount][1] = Integer.toString(locate - litcount);
								int len = lit[litcounter - litcount][0].length(); //calculating the literal count -2
								String temp = lit[litcounter - litcount][0].substring(2, len - 1); //assigning address
								ans += "(DL,01)(C," + temp + ")";       //storing into string ans
								litcount--;
								if (litcount != 0)               //check until all literals are assigned address
									ans += "\n";

							}

						}
						//set location counter
						if (y.equals("ORIGIN")) {
							y = splitted.nextToken();
							String[] words = y.split("\\+");
							int location = Integer.parseInt(sym[indexsym(words[0])][1]);
							locate = location + Integer.parseInt(words[1]); //setting the location counter
							ans = "(AD,03)(S," + Integer.toString(indexsym(words[0]) + 1) + ")+" + words[1];
						}
						if (y.equals("END") && litcount != 0) {
							locate += litcount;
							ans = "(AD,02)\n";
							while (litcount > 0) {
								lit[litcounter - litcount][1] = Integer.toString(locate - litcount);
								int len = lit[litcounter - litcount][0].length();
								String temp = lit[litcounter - litcount][0].substring(2, len - 1);
								ans += "(DL,01)(C," + temp + ")\n";
								litcount--;

								if (litcount != 0)
									ans += "\n";
							}

						}
						//assigning address of one symbol to other label.
						if (y.equals("EQU")) {
							int temp = indexsym(splitted.nextToken());
							y = prev;
							sym[indexsym(y)][1] =  sym[temp][1];
							ans = "";
						}

					}
					//declerative statements
					else if (searchdl(y)) {
						if (y.equals("DC")) {
							ans = "";
							//It returns the next token from the StringTokenizer object.
							ans += "(DL,1)(C," + splitted.nextToken() + ")";
						}
						if (y.equals("DS")) {
							ans = "";
							ans += "(DL,2)(C," + splitted.nextToken() + ")";
						}
						locate += 1;
					}

					else {
						prev = y;
						char[] x = y.toCharArray();
						if (x[0] == '=') {
							int z = litcounter;
							ans += "(L," + (z + 1) + ")";
							lit[litcounter++][0] = y;
							litcount++;
						}
						//Adding register into the ans string
						else if (y.equals("AREG")) {
							ans += "(1)";
						} else if (y.equals("BREG")) {
							ans += "(2)";
						} else if (y.equals("CREG")) {
							ans += "(3)";
						} else if (y.equals("DREG")) {
							ans += "(4)";
						}

						else if (searchcc(y)) {
							ans += "(" + Integer.toString(indexcc(y) + 1) + ")";
						} else {
							//if address is assign
							if (!searchsym(y) && isflag == 0 && stp == 0) {
								sym[symcounter][0] = y;
								sym[symcounter++][1] = Integer.toString(locate);
								ans += "(S," + Integer.toString(indexsym(y) + 1) + ")";
								if (splitted.hasMoreTokens())
									ans = "";
							}
							//if instruction has passed on the line then only add the symbol not the address
							else if (!searchsym(y) && isflag == 1 && stp == 0 ) {
								sym[symcounter++][0] = y;
								ans += "(S," + Integer.toString(indexsym(y) + 1) + ")";
							} else if (searchsym(y) && isflag == 0) {
								sym[indexsym(y)][1] = Integer.toString(locate);
								ans += "(S," + Integer.toString(indexsym(y) + 1) + ")";
								if (splitted.hasMoreTokens())
									ans = "";
								prev = y;
							}


							else {
								if (!splitted.hasMoreTokens())
									ans += "(S," + Integer.toString(indexsym(y) + 1) + ")";

								continue;
							}
						}
					}
				}

			}
			ans = ans + "\n";
			buffer += ans;

		}

		System.out.println(buffer + "\n");
		System.out.println("Symbol Table : ");
		for (int i = 0; i < symcounter; i++) {
			System.out.print(sym[i][0] + "\t");
			System.out.println(sym[i][1]);
		}
		System.out.println("---------------");
		System.out.println("Literal Table : ");
		for (int i = 0; i < litcounter; i++) {
			System.out.print(lit[i][0] + "\t");
			System.out.println(lit[i][1]);
		}
		writer.write(buffer);
		reader.close();
		writer.close();

	}


	//search for imperative statemnt
	public static boolean searchis(String s) {
		boolean flag = false;
		int i = 0;
		while (i < 11) {
			if (is[i].equals(s)) {
				flag = true;
				break;
			}
			i++;
		}
		return flag;

	}
	//assebly directive
	public static boolean searchad(String s) {
		boolean flag = false;
		int i = 0;
		while (i < 5) {
			if (ad[i].equals(s)) {
				flag = true;
				break;
			}
			i++;
		}
		return flag;

	}
	//declerative statement
	public static boolean searchdl(String s) {
		boolean flag = false;
		int i = 0;
		while (i < 2) {
			if (dl[i].equals(s)) {
				flag = true;
				break;
			}
			i++;
		}
		return flag;

	}
	public static boolean searchsym(String s) {
		boolean flag = s.equals("BREG") || s.equals("AREG") || s.equals("CREG") || s.equals("DREG") || s.equals(",") || s.equals("LE") || s.equals("LT") || s.equals("ANY") || s.equals("EQ") || s.equals("GT") || s.equals("GE");
		int i = 0;
		while (i < symcounter ) {
			if (sym[i][0].equals(s)) {
				flag = true;
				break;
			}
			i++;
		}
		return flag;
	}
	public static boolean searchcc(String s) {
		boolean flag = false;
		int i = 0;
		while (i < 6) {
			if (cc[i].equals(s)) {
				flag = true;
				break;
			}
			i++;
		}
		return flag;

	}

	public static int indexsym(String s) {
		int c = 0;
		int i = 0;
		while (i < symcounter) {
			if (sym[i][0].equals(s)) {
				c = i;
				break;
			}
			i++;
		}
		return i;
	}
	public static int indexlit(String s) {
		int c = 0;
		int i = 0;
		while (i < litcounter) {
			if (lit[i][0].equals(s)) {
				c = i;
				break;
			}
			i++;
		}
		return i;
	}
	public static int indexis(String s) {
		int i = 0;
		while (i < 11) {
			if (is[i].equals(s)) {
				break;
			}
			i++;
		}
		return i;
	}
	public static int indexad(String s) {
		int i = 0;
		while (i < 5) {
			if (ad[i].equals(s)) {
				break;
			}
			i++;
		}
		return i;
	}

	public static int indexdl(String s) {
		int i = 0;
		while (i < 2) {
			if (dl[i].equals(s)) {
				break;
			}
			i++;
		}
		return i;
	}
	public static int indexcc(String s) {
		int i = 0;
		while (i < 6) {
			if (cc[i].equals(s)) {
				break;
			}
			i++;
		}

		return i;
	}
}