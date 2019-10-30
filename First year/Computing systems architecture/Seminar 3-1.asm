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
    s1 db 1,2,3,4
    ls1 equ $-s1
    s2 db 7,6,5
    ls2 equ $-s2
    d resb ls1+ls2
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;concatenare siruri
        mov edi,0
        mov esi,0
        adaugare:
            mov dl,[s1+edi]
            mov [d+edi],dl
            inc edi
            cmp edi,ls1
            jae concatenare
            jb adaugare
        concatenare:
            mov dl,[s2+esi]
            mov [d+edi],dl
            inc esi
            inc edi
            cmp esi,ls2
            jb concatenare
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
