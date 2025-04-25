#include <stdio.h>

int main (){
    // unsigned short usi=65535;
    // short si=usi;
    // printf("si的值是%d",si);

    // //
    // int i= 32777;
    // short si_ = i;
    // int j =si_;
    // printf("j的值为%d\nsi_的值为%d",j,si_);
    // printf("si_的机器码0x%02x\n",si_);

    // int n = 785;
    // float n_f = n;
    // unsigned int* p = (unsigned int*)&n_f;  // 将 float 的地址转换为 unsigned int 指针
    // printf("%f,%08x\n", n_f, *p);  // 输出 float 和其二进制表示的十六进制
    // int n_int = n_f;
    // printf("\nn_int的值是%d,机器码是%x",n_int,n_int);
    demo1();
}

void demo1(){
    short y = -9;
    printf("y machine code : %hx",y);
}