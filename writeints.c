int main(){
    int i;
    while(scanf("%d", &i)!=EDF){
        fwrite(&i, sizeof(int), 1, stdout);
    }
}