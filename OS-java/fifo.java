import java.io.*;
import java.util.Scanner;

class fifo {
    public static void main(String args[]) throws IOException {
        float rat;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of FRAMES :");
        int f = sc.nextInt();
        int fifo[] = new int[f];

        System.out.println("Enter the number of INPUTS :");
        int n = sc.nextInt();
        int input[] = new int[n];

        System.out.println("Enter INPUT: ");
        for (int i = 0; i < n; i++) {
            input[i] = sc.nextInt();
        }

        System.out.println("----------------------");
        for (int i = 0; i < f; i++)
            fifo[i] = -1;
        int Hit = 0;
        int Fault = 0;
        int j = 0;
        boolean check;
        for (int i = 0; i < n; i++) {
            check = false;
            for (int k = 0; k < f; k++)
                if (fifo[k] == input[i]) {
                    check = true;
                    Hit = Hit + 1;
                }
            if (check == false) {
                fifo[j] = input[i];
                j++;
                if (j >= f)
                    j = 0;
                Fault = Fault + 1;
            }
        }
        rat = (float) Hit / (float) n;
        System.out.println("HIT:" + Hit + "  FAULT:" + Fault + "   HIT RATIO:" + rat);
    }
}

/*
First In First Out (FIFO) page replacement algorithm Output:
run:
Enter the number of FRAMES :
3
Enter the number of INPUTS :
12
Enter INPUT:
1
2
3
4
1
2
5
1
2
3
4
5
----------------------
HIT:3  FAULT:9   HIT RATIO:0.25
BUILD SUCCESSFUL (total time: 37 seconds)
*/
