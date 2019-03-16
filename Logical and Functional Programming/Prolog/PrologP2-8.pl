/**
a) Definiti un predicat care determina succesorul unui numar reprezentat
cifra cu cifra intr-o lista.
De ex: [1 9 3 5 9 9] => [1 9 3 6 0 0]
b) Se da o lista eterogena, formata din numere intregi si liste de cifre.
Pentru fiecare sublista sa se determine succesorul numarului reprezentat
cifra cu cifra de lista respectiva.
De ex:[1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] =>
[1, [2, 4], 4, 5, [6, 8, 0], 10, 11, [1, 2, 1], 6]
*/


%a)
%succesorAux(L,B,R)
%L-numar sub reprezentare de lista
%B-bonus
%R-rezultat
%(i,i,o)

succesorAux([],0,[]).
succesorAux([],1,[1]).
succesorAux([H|T],0,[H|R]):-
	succesorAux(T,0,R).
succesorAux([H|T],1,[H1|R]):-
	H+1>=10,
	!,
	H1 is 0,
	succesorAux(T,1,R).
succesorAux([H|T],1,[H1|R]):-
	H+1<10,
	H1 is H+1,
	succesorAux(T,0,R).

%succesor(L,R)
%L-numarul sub reprezentare de lsita
%R-rezultat
%(i,o)

succesor(L,R):-inversare(L,R1),succesorAux(R1,1,R2),inversare(R2,R).


%b)
%succesorLista(L,R)
%L-lista in care calculam
%R-rezultat
%(i,o)

succesorLista([],[]).
succesorLista([H|T],[H1|R]):-
	isList(H),
	!,
	succesor(H,H1),
	succesorLista(T,R).
succesorLista([H|T],[H|R]):-
	not(isList(H)),
	!,
	succesorLista(T,R).




