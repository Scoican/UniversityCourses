%a) Definiti un predicat care determina suma a doua numere scrise in
%reprezentare de lista.
%b) Se da o lista eterogena, formata din numere intregi si liste de
%cifre. %Sa se calculeze suma tuturor numerelor reprezentate de
%subliste. De ex: [1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] =>
%[8, 2, 2].



%A
%suma(L1,L2,B,R)
%L1,L2-numere in reprezentare de lista
%R-raspunsul
%B-bonusul
%(i,o)

suma([],[],0,[]):-!.
suma([],[],1,[1]):-!.

suma([H|T],[],0,[H|R]):-
	suma(T,[],0,R).
suma([H|T],[],1,[H2|R]):-
	H+1>=10,
	H2 is mod(H+1,10),
	suma(T,[],1,R).
suma([H|T],[],1,[H2|R]):-
	H+1<10,
	H2 is H+1,
	suma(T,[],0,R).

suma([],[H|T],0,[H|R]):-
	suma([],T,0,R).
suma([],[H|T],1,[H2|R]):-
	H+1>=10,
	H2 is mod(H+1,10),
	suma([],T,1,R).
suma([],[H|T],1,[H2|R]):-
	H+1<10,
	H2 is H+1,
	suma([],T,0,R).

suma([H1|T1],[H2|T2],0,[H3|R]):-
	H1+H2>=10,
	H3 is mod(H1+H2,10),
	suma(T1,T2,1,R).
suma([H1|T1],[H2|T2],0,[H3|R]):-
	H1+H2<10,
	H3 is mod(H1+H2,10),
	suma(T1,T2,0,R).
suma([H1|T1],[H2|T2],1,[H3|R]):-
	H1+H2+1>=10,
	H3 is mod(H1+H2+1,10),
	suma(T1,T2,1,R).
suma([H1|T1],[H2|T2],1,[H3|R]):-
	H1+H2+1<10,
	H3 is mod(H1+H2+1,10),
	suma(T1,T2,0,R).


%inversareLista(L,R)
%L-lista de inversat
%A-variabila colectoare
%R-raspunsul
%(i,o)

inversareLista([],A,A).
inversareLista([H|T],A,R):-inversareLista(T,[H|A],R).
inversare(L,R):-inversareLista(L,[],R).

%calculSuma(L1,L2,R).
%L1,L2-numere in reprezentare de lista
%R-raspunsul
%(i,i,o)

calculSuma(L1,L2,R3):-inversare(L1,R1),
	inversare(L2,R2),
	suma(R1,R2,0,R),
	inversare(R,R3),!.

isList([_|_]).
isList([]).

%b
%calculSumaLista(L,S,R).
%L-lista de liste
%S-suma actuala
%R-raspuns
%(i,o)

calculSumaLista([],S,S).
calculSumaLista([H|T],S,R):-isList(H),
	calculSuma(H,S,R1),
	calculSumaLista(T,R1,R).
calculSumaLista([H|T],S,R):-not(isList(H)),
	calculSumaLista(T,S,R).














