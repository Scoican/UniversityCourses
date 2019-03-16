/**
a) Sa se adauge dupa fiecare element dintr-o lista divizorii elementului.
b) Se da o lista eterogena, formata din numere intregi si liste de numere
intregi. Se cere ca in fiecare sublista sa se adauge dupa fiecare element
divizorii elementului.
De ex:[1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2] =>
[1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4,
1], 2]
*/


%a)
%divizoriAux(N,M,R)
%N-numarul la care cautam divizori
%M-divizor
%R-raspuns
%(i,i,o)

divizoriAux(N,N,[N]).
divizoriAux(N,M,[M|R]) :-
    N > M,
    0 is N mod M,
    M1 is M+1,
    divizoriAux(N,M1,R).
divizoriAux(N,M,R) :-
    N > M,
    not(0 is N mod M),
    M1 is M+1,
    divizoriAux(N,M1,R).

divizori(N,R) :- divizoriAux(N,1,R).

%inserareDivizori(L,C,R)
%L-lista in care inseram
%C-variabila colectoare
%R-raspuns
%(i,i,o)

adaugareFinal([],E,[E]).
adaugareFinal([H|T],E,[H|R]):-adaugareFinal(T,E,R).

inserareDivizori([],C,C).
inserareDivizori([H|T],C,R):-
	divizori(H,R1),
	adaugareFinal(C,H,C1),
	append(C1,R1,C2),
	inserareDivizori(T,C2,R).







