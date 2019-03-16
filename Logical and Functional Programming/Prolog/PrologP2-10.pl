/**
a) Se da o lista de numere intregi. Se cere sa se adauge in lista dupa
1-ul element, al 3-lea element, al 7-lea elemen, al 15-lea element … o
valoare data e.
b) Se da o lista eterogena, formata din numere intregi
si liste de numere intregi. Lista incepe cu un numar si nu sunt 2
elemente consecutive care sunt liste. Se cere ca in fiecare sublista sa
se adauge dupa 1-ul, al 3- lea, al 7-lea… element valoarea care se
gaseste inainte de sublista in lista eterogena.
De ex: [1, [2, 3], 7,[4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8, 2, 7], 5] => [1, [2, 1, 3], 7, [4,7, 1, 4, 7], 3, 6, [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5].
*/


%a)
%examenAux(L,E,P,C,R)
%L-lista in care adaugam
%E-elementul pe care il adaugam
%P-pozitia curenta
%C-next checkpoint
%D-puterea lui 2
%R-raspuns
%(i,i,i,i,o)

examenAux([],_,_,_,_,[]).
examenAux([H|T],E,P,C,D,[H,E|R]):-
	P=:=C,
	!,
	P1 is P+1,
	C1 is C+2**D,
	D1 is D+1,
	examenAux(T,E,P1,C1,D1,R).
examenAux([H|T],E,P,C,D,[H|R]):-
	P\=C,
	P1 is P+1,
	examenAux(T,E,P1,C,D,R).

%examen(L,E,R)
%L-lista in care adaugam
%E-elementul pe care il adaugam
%R-rezultat
%(i,i,o)

examen(L,E,R):-examenAux(L,E,1,1,1,R).

%b
%examenLista(L,P,R)
%L-lista pe care o modificam
%P-elementul anterior
%R-rezultat
%(i,o)

examenListaAux([],_,[]).
examenListaAux([H|[]],P,[P,H|R]):-
	isList(H),
	!,
	examen(H,P,H1),
	examenListaAux([],H1,R).
examenListaAux([H|[]],P,[P,H|R]):-
	not(isList(H)),
	!,
	examenListaAux([],H,R).
examenListaAux([H|T],P,[P|R]):-
	not(isList(P)),
	isList(H),
	!,
	examen(H,P,H1),
	examenListaAux(T,H1,R).
examenListaAux([H|T],P,[P|R]):-
	isList(P),
	!,
	examenListaAux(T,H,R).
examenListaAux([H|T],P,[P|R]):-
	not(isList(P)),
	not(isList(H)),
	examenListaAux(T,H,R).

examenLista([H|T],R):-examenListaAux(T,H,R).














