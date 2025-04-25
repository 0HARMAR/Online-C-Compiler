#include <stdio.h>
#include <unistd.h>

int main()
{
    setbuf(stdout,NULL);
    printf("before ,is i will print twice?");
    fork();
    printf("i must be print twice");
}
