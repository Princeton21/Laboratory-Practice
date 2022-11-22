# STEPS TO FOLLOW

After step 3, there may be errors encounterd in Windows OS but works fine will the Ubuntu System perfectly.

1. `gcc -c lib_add.c lib_sub.c -fpic`  
   Creating object file for header file fpic is required to create a shared (dynamic) library which creates position independent code.

2. `gcc *.o -shared -o lib_calc.so`

dynamic library created

3. `gcc -c main.c -o main.o`

object file created

4. `gcc -o main main.o -L. -l_calc`

executable file created

after above command have to run ./main to get the output but if there is error encounterd then run below command

This command will tell the linker where to find the library file.

`` export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.` ``

5. `./main`

then run ./main.
