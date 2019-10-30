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
    ls equ $-s1
    s2 db 5,6,7,8
    d resb ls
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi,s1
        mov ecx,ls
        mov edi,d
        bucla:
            mov edx,esi
            add edx,ls
            mov eax,esi
            mov ax,[eax+0]
            mov dx,0
            mov bx,2
            div bx
            mov bx,0
            cmp dx,0
            je par
            jne impar 
            impar:
                lodsb
                mov bl,al
                mov esi,edx
                lodsb
                sub esi,ls
                add al,bl
                stosb
                jmp final
            par:
                lodsb
                mov bl,al
                mov esi,edx
                lodsb
                sub esi,ls
                sub al,bl
                stosb
            final:
        loop bucla
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
