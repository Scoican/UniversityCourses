%a)Sa se sorteze o lista cu eliminarea dublurilor. De ex: [4 2 6 2 3 4]
%=>%[2 3 4 6]
%b)Se da o lista eterogena, formata din numere intregi si liste de
%numere. Sa se sorteze fiecare sublista fara pastrarea dublurilor. De
%ex: [1, 2, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] =>
%[1, 2, [1, 4], 3, 6, [1, 3, 7, 9, 10], 5, [1], 7].


%a)
%apartine(L,E)
%L-lista in care cautam elementul
%E-elementul cautat
%(i,i).

apartine([],_):-fail.
apartine([E|_],E):-!.
apartine([H|T],E):-H\=E,apartine(T,E).

%sortareEliminare(L,C,R).
%L-lista de sortat
%C-variabila auxiliara
%R-raspuns
%(i,i,o)

sortareEliminare([],C,C).
sortareEliminare([H|T],C,R):-apartine(C,H),!,sortareEliminare(T,C,R).
sortareEliminare([H|T],C,R):-not(apartine(C,H)),
	inserare(C,H,R1),
	sortareEliminare(T,R1,R).

%b)
%sortareLista(L,R)
%L-lista pe care o sortam
%R-raspuns
%(i,o).

sortareEliminareLista([],[]).
sortareEliminareLista([H|T],[R1|R]):-isList(H),!,
	sortareEliminare(H,[],R1),
	sortareEliminareLista(T,R).
sortareEliminareLista([H|T],[H|R]):-not(isList(H)),
	sortareEliminareLista(T,R).


