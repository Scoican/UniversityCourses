bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s dw 1234h,5678h
    ls equ ($-s)/2
    sir times ls db 0
    aux db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ebx,s
        mov edi,0
        mov edx,sir
        adj:
            mov al,[ebx+edi*2]
            cbw
            mov cl,7
            idiv cl
            cmp ah,0
            jz adaugare
            jnz adj
            adaugare:
                mov edx+esp,[aux]
                inc esp
            cmp edi,ls
            jb adj
        ;sa se copieze in sirul D,octetii inferiori care sunt multiplii de 7
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
