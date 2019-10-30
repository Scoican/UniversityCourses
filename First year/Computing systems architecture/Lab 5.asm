; Codul de mai jos va afisa mesajul ”n=”, apoi va citi de la tastatura valoarea numarului n.
bits 32

global start        

; declararea functiilor externe folosite de program
extern exit, printf, scanf ; adaugam printf si scanf ca functii externa            
import exit msvcrt.dll    
import printf msvcrt.dll    ; indicam asamblorului ca functia printf se gaseste in libraria msvcrt.dll
import scanf msvcrt.dll     ; similar pentru scanf
                          
segment data use32 class=data
	n dd  0
    m dd  0
    rezultat dq 0   ; in aceasta variabila se va stoca valoarea citita de la tastatura
                    ; sirurile de caractere sunt de tip byte
	message1 db "n=", 0
    message2  db "m=", 0    ; sirurile de caractere pentru functiile C trebuie sa se termine cu valoarea 0 (nu caracterul)
	format  db "%d", 0  ; %d <=> un numar decimal (baza 10)
    afisare  db "Produsul este este: %d", 0
segment code use32 class=code
    start:
       
        ; vom apela printf(message) => se va afisa "n="
        ; punem parametrii pe stiva
        push dword message1 ; ! pe stiva se pune adresa string-ului, nu valoarea
        call [printf]      ; apelam functia printf pentru afisare
        add esp, 4*1        ; eliberam parametrii de pe stiva ; 4 = dimensiunea unui dword; 1 = nr de parametri

                                                   
        ; vom apela scanf(format, n) => se va citi un numar in variabila n
        ; punem parametrii pe stiva de la dreapta la stanga
        push dword n       ; ! adresa lui n, nu valoarea
        push dword format
        call [scanf]       ; apelam functia scanf pentru citire
        add esp, 4 * 2     ; eliberam parametrii de pe stiva
                           ; 4 = dimensiunea unui dword; 2 = nr de parametri
                           
        push dword message1 ; ! pe stiva se pune adresa string-ului, nu valoarea
        call [printf]      ; apelam functia printf pentru afisare
        add esp, 4*1       ; eliberam parametrii de pe stiva ; 4 = dimensiunea unui dword; 1 = nr de parametri
        
        push dword m       ; ! adresa lui n, nu valoarea
        push dword format
        call [scanf]       ; apelam functia scanf pentru citire
        add esp, 4 * 2 
        
        mov eax,[n]
        mul dword [m]
        mov [rezultat+0],edx
        mov [rezultat+4],eax
        push dword [rezultat+0]
        push dword [rezultat+4]
        push dword afisare
        call [printf]
        add esp,4*3
        ; exit(0)
        push dword 0      ; punem pe stiva parametrul pentru exit
        call [exit]       ; apelam exit pentru a incheia programul