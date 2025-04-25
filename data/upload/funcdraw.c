#include <stdio.h>

#define Y(x) ((x) * (x))  //y = x * x

int main()
{
    float x = -10;

    for(;x<10;x+=0.5)
    {
        printf("%f\n",Y(x));
    }
}