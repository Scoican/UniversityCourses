bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                         ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;se da un numar de quadword :suma cifrelor numarului multiplilor de 8 din sirul de octeti superiori ai cuv superioare ai dublu cuv inferiaore din specific
    format db '%x %x+1', 0 
    a dd 0x12345678 
    b db 0,1,2,3,4,5 
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push word [a] 
        push dword [a+2] 
        push word [b] 
        push dword format 
        call [printf] 
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
