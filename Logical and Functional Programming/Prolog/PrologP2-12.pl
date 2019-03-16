/**
a) Sa se inlocuiasca toate aparitiile unui element dintr-o lista cu un alt
element.
b) Se da o lista eterogena, formata din numere intregi si liste de numere
intregi. Se cere ca toate aparitiile elementului maxim (dintre valorile
intregi ale listei) sa fie inlocuite in fiecare sublista cu maximul
sublistei respective.
De ex:[1, [2, 5, 7], 4, 5, [1, 4], 3, [1, 3, 5, 8, 5, 4], 5, [5, 9, 1], 2] =>[1, [2, 7, 7], 4, 5, [1, 4], 3, [1, 3, 8, 8, 8, 4], 5, [9, 9, 1], 2]
*/

%a)
%inlocuire(L,E,V,R)
%L-lista in care inlocuim
%E-elementul pe care il inlocuim
%V-valoare cu care inlocuim
%R-rezultat
%(i,i,o)

inlocuire2([],_,_,[]).
inlocuire2([H|T],E,V,[V|R]):-
	H=:=E,
	!,
	inlocuire2(T,E,V,R).
inlocuire2([H|T],E,V,[H|R]):-
	not(H=:=E),
	!,
	inlocuire2(T,E,V,R).

%b)
%cautareMaxim(L,M,R)
%L-lista in care cautam
%M-elementul maxim curent
%R-Rezultat
%(i,i,o)

cautareMaxim2([],M,M).
cautareMaxim2([H|T],M,R):-
	not(isList(H)),
	H>M,
	!,
	cautareMaxim2(T,H,R).
cautareMaxim2([_|T],M,R):-
	cautareMaxim2(T,M,R).

%inlocuire2Lista(L,M,R).
%L-lista in care inlocuim
%M-maximum din lista
%R-rezultat
%(i,o)

inlocuire2ListaAux([],_,[]).
inlocuire2ListaAux([H|T],M,[H1|R]):-
	isList(H),
	!,
	cautareMaxim2(H,0,E),
	inlocuire2(H,M,E,H1),
	inlocuire2ListaAux(T,M,R).
inlocuire2ListaAux([H|T],M,[H|R]):-
	not(isList(H)),
	!,
	inlocuire2ListaAux(T,M,R).

inlocuire2Lista(L,R):-cautareMaxim2(L,0,M),
	inlocuire2ListaAux(L,M,R).
