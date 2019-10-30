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
    s dw 1,2,3,4
    ls equ $-s
    r dw 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;se da un sir de words, sa se det suma lor
        mov eax,s
        mov edi,0
        mov ebx,r
        add:                    ;mov cx,ls
            mov dx,[eax+edi]    ;mov dx,[eax+edi]
            add bx,dx           ;add bx,dx
            inc edi      
            inc edi            ;inc edi
            cmp edi,ls          ;loop add
            jb add              ;jcxz sfarsit
        ;sau
        ; exit(0)
        ;sfarsit:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
