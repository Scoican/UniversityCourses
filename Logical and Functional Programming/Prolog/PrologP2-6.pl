/**
a) Intr-o lista L sa se inlocuiasca toate aparitiile unui element E cu
 elementele unei alte liste, L1. De ex: inloc([1,2,1,3,1,4],1,[10,11],X)
 va produce X=[10,11,2,10,11,3,10,11,4].
b) Se da o lista eterogena, formata din numere intregi si liste de numere
intregi. In fiecare sublista sa se inlocuiasca toate aparitiile primului
element din sublista cu o lista data. De ex:
[1, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] si [11, 11] =>
[1, [11, 11, 1, 11, 11], 3, 6, [11, 11, 10, 1, 3, 9], 5, [11 11 11 11 11
11], 7]
*/



%a)
%append(L1,L2,R)
%L1-lista pe care o inseram
%L2-lista in care inseram
%R-rezultat

append([],L2,L2).
append([H|T],L2,[H|R]):-append(T,L2,R).

%inlocuire(L,E,L1,R)
%L-lista in care inlocuim
%E-elementul pe care il inlocuim
%L1-lista cu care inlocuim
%R-rezultat
%(i,i,i,o)

inlocuire([],_,_,[]).
inlocuire([H|T],E,L1,[H|R]):-
	H\=E,
	!,
	inlocuire(T,E,L1,R).
inlocuire([H|T],E,L1,R):-
	H=:=E,
	inlocuire(T,E,L1,R1),
	append(L1,R1,R).

%b)
%primul(L,E)
%L-lista in care cautam
%E-primul element
%(i,o)

primul([H|_],H).

%inlocuireSublista(L,L1,R)
%L-lista in care inlocuim
%L1-lista cu care inlocuim
%R-Raspuns
%(i,o)

inlocuireSublista([],_,[]).
inlocuireSublista([H|T],L1,[R1|R]):-
	isList(H),
	!,
	primul(H,E),
	inlocuire(H,E,L1,R1),
	inlocuireSublista(T,L1,R).
inlocuireSublista([H|T],L1,[H|R]):-
	not(isList(H)),
	inlocuireSublista(T,L1,R).









