/**
a) Sa se interclaseze fara pastrarea dublurilor doua liste sortate.
b) Se da o lista eterogena, formata din numere intregi si liste de numere
sortate. Sa se interclaseze fara pastrarea dublurilor toate sublistele. De
ex :
[1, [2, 3], 4, 5, [1, 4, 6], 3, [1, 3, 7, 9, 10], 5, [1, 1, 11], 8] =>
[1, 2, 3, 4, 6, 7, 9, 10, 11].
*/


%a)
%interclasare(L1,L2,R)
%L1,L2-liste de interclasat
%R-rezultat
%(i,i,o)

interclasare([],L2,L2).
interclasare([H|T],L2,R):-apartine(L2,H),!,interclasare(T,L2,R).
interclasare([H|T],L2,R):-not(apartine(L2,H)),
	inserare(L2,H,R1),
	interclasare(T,R1,R).

%b)
%interclasareLista(L,C,R)
%L-lista de interclasat
%C-variabila colectoare
%R-Rezultat
%(i,i,o)

interclasareLista([],C,C).
interclasareLista([H|T],C,R):-isList(H),
	!,
	interclasare(H,C,R1),
	interclasareLista(T,R1,R).
interclasareLista([H|T],C,R):-not(isList(H)),
	interclasareLista(T,C,R).
