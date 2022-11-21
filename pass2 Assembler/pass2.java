import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class pass2{
    public static void main(String[] args) throws IOException{
        // File file = new File("Input1.asm");
		// BufferedReader reader = new BufferedReader(new FileReader(file)); 
        
        // File file1 = new File("output.asm");         //reading the file
		// BufferedWriter writer= new BufferedWriter(new FileWriter(file1));  //reading the file

         BufferedReader b1 = new BufferedReader(new FileReader("C:\\Users\\Ashutosh Raj Gupta\\Desktop\\TE\\LP1 ASSIGNMENT\\SP\\pass2 Assembler\\intermediate.txt"));
	     BufferedReader b2 = new BufferedReader(new FileReader("C:\\Users\\Ashutosh Raj Gupta\\Desktop\\TE\\LP1 ASSIGNMENT\\SP\\pass2 Assembler\\symtbl.txt"));
	     BufferedReader b3 = new BufferedReader(new FileReader("C:\\Users\\Ashutosh Raj Gupta\\Desktop\\TE\\LP1 ASSIGNMENT\\SP\\pass2 Assembler\\littbl.txt"));
	     FileWriter f1 = new FileWriter("C:\\Users\\Ashutosh Raj Gupta\\Desktop\\TE\\LP1 ASSIGNMENT\\SP\\pass2 Assembler\\output.txt");
         //using hashmap for data structures
	     HashMap<Integer, String> symSymbol = new HashMap<Integer, String>();
	     HashMap<Integer, String> litSymbol = new HashMap<Integer, String>();
	     HashMap<Integer, String> litAddr = new HashMap<Integer, String>();

         String s;
	     int symtabPointer=1,littabPointer=1,offset;
         
         //readline reads the first line  from symbol table
         while((s=b2.readLine())!=null){
            String word[]=s.split("\t\t\t");
            symSymbol.put(symtabPointer++,word[1]);
        }
        // reads the first line from literal table
        while((s=b3.readLine())!=null){
            String word[]=s.split("\t\t");
            litSymbol.put(littabPointer,word[0]);
            litAddr.put(littabPointer++,word[1]);
        }
        //reads the first line from intermediate code
        while((s=b1.readLine())!=null){
            if(s.substring(1,6).compareToIgnoreCase("IS,00")==0){
                f1.write("+ 00 0 000\n");
            }

            else if(s.substring(1,3).compareToIgnoreCase("IS")==0){
                f1.write("+ "+s.substring(4,6)+" ");
                if(s.charAt(9)==')'){
                    f1.write(s.charAt(8)+" ");
                    offset=3;
                }
                else{
                    f1.write("0 ");
                    offset=0;
                }
                if(s.charAt(8+offset)=='S')
                    f1.write(symSymbol.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
                else
                     f1.write(litAddr.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
              } 
              else if(s.substring(1,6).compareToIgnoreCase("DL,01")==0){
                String s1=s.substring(10,s.length()-1),s2="";
                for(int i=0;i<3-s1.length();i++)
                    s2+="0";
                s2+=s1;
                f1.write("+ 00 0 "+s2+"\n");
            }
            else{
                f1.write("\n");
            }
    }
    f1.close();
    b1.close();
    b2.close();
    b3.close();

}
}