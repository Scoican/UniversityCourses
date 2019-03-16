/**
a) Se da o lista de numere intregi. Se cere sa se scrie de 2 ori in lista
fiecare numar prim.
b) Se da o lista eterogena, formata din numere intregi si liste de numere
intregi. Se cere ca in fiecare sublista sa se scrie de 2 ori fiecare
numar prim.
De ex:[1, [2, 3], 4, 5, [1, 4, 6], 3, [1, 3, 7, 9, 10], 5]
=> [1, [2, 2, 3, 3], 4, 5, [1, 4, 6], 3, [1, 3, 3, 7, 7, 9, 10], 5]
*/


%a)
%divizibil(X,Y)
%X,Y-numere
%(i,i)
divizibil(X,Y) :- 0 is X mod Y, !.
divizibil(X,Y) :- X > Y+1, divizibil(X, Y+1).

%prim(X)
%X-numar de verificat
%(i)
prim(2) :- true,!.
prim(X) :- X < 2,!,false.
prim(X) :- not(divizibil(X, 2)).

%duplicare(L,R)
%L-lista in care duplicam
%R-rezultat
%(i,o)

duplicare([],[]).
duplicare([H|T],[H,H|R]):-
	prim(H),
	!,
	duplicare(T,R).
duplicare([H|T],[H|R]):-
	not(prim(H)),
	duplicare(T,R).

%b)
%duplicareLista(L,R)
%L-lista in care duplicam
%R-rezultat
%(i,o)

duplicareLista([],[]).
duplicareLista([H|T],[R1|R]):-
	isList(H),
	!,
	duplicare(H,R1),
	duplicareLista(T,R).
duplicareLista([H|T],[H|R]):-
	not(isList(H)),
	!,
	duplicareLista(T,R).


