%a.Sa se scrie un predicat care transforma o lista intr-o multime, in
%ordinea primei aparitii. Exemplu: [1,2,3,1,2] e transformat in [1,2,3].

%cautareElement(L,E)
%L-lista in care se cauta
%E-elementul cautat
%(i,i)

cautareElement([],_,0).
cautareElement([E|T],E,R):-!,cautareElement(T,E,R1),R is R1+1.
cautareElement([_|T],E,R):-cautareElement(T,E,R).

%elimina(L,E,R)
%L-lista din care se elimina
%E-elementul de eleminat
%R-Rezultatul
%(i,i,o)

elimina([],_,[]).
elimina([E|T],E,R):-!,elimina(T,E,R).
elimina([H|T],E,[H|R1]):-elimina(T,E,R1).

%transformare(L,R)
%L-lista pe care o transformam
%R-multimea rezultata
%(i,o)
trasnforma([], []).
transforma([H|T], [H|R]):-
	cautareElement(T, H, N),
	N > 0,
	elimina(T, H, R1),
	transforma(R1, R),
	!.
transforma([H|T], [H|R]):-
	cautareElement(T, H, N),
	N =:= 0,
	transforma(T, R),
	!.

sterge(_,[],[]).
sterge(E,[H|T],L):-H=E,sterge(E,T,L).
sterge(E,[H|T],[H|L]):-sterge(E,T,L).

multime([],[]).
multime([H|T],[H|L]):-sterge(H,T,T1),multime(T1,L).

%b.Sa se scrie o functie care descompune o lista de numere intr-o lista
%de
%forma [ lista-de-numere-pare lista-de-numere-impare] (deci lista cu doua
%elemente care sunt liste de intregi), si va intoarce si numarul
%elementelor pare si impare.


%lungime(L,R)
%L-lista pe care o numaram
%R-numarul de elemente din R
%(i,o)

lungime([],0).
lungime([_|T],R):-lungime(T,R1),R is R1+1.

%descompunere(L,R1,R2).
%L-lista pe care o descompunem
%R1-lista de elemente pare
%R2-lista de elemente impare
%(i,o,o)

descompunere([],[],[]).
descompunere([H|T],[H|R],R2):-H mod 2=:=0,descompunere(T,R,R2).
descompunere([H|T],R1,[H|R]):-H mod 2=:=1,descompunere(T,R1,R).

%rezultat(L,R,N1,N2)
%L-lista pe care o descompunem
%R-lista cu cele 2 liste
%N1-numarul total de elemente pare
%N2-numarul total de elemente impare
%(i,o,o,o)

rezultat(L,[R1|[R2]],N1,N2):-descompunere(L,R1,R2),
	lungime(R1,N1),
	lungime(R2,N2).





































