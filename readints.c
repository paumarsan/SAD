#include <stdio.h>

int main(){
    /*
    read ints in binarty format from stdin and write them in textual
    format to stdout
    */
   int i;
    while(fread(&i, sizeof(i), 1, stdin)>0){
        printf("%d\n", i);
    }
    return 0;
}