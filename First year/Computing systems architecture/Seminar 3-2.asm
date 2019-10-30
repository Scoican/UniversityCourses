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
    s dw 0A012h,5678h
    ls equ $-s
    nr db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi,1
        mov dx,[nr]
        numarare:
            mov bl,[s+esi]
            cmp bl,0
            jl incrementare
            add esi,2
            cmp esi,ls
            jl numarare
            jg sfarsit
        incrementare:
            inc dx
             add esi,2
            cmp esi,ls
            jl numarare
            jg sfarsit
        sfarsit:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
