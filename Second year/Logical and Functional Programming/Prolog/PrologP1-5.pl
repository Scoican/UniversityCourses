%a.Sa se scrie un predicat care sterge toate aparitiile unui anumit
%atom dintr-o lista.
%b.Definiti un predicat care, dintr-o lista de
%atomi, produce o lista de perechi (atom n), unde atom apare in lista
%initiala de n ori. De ex: numar([1, 2, 1, 2, 1, 3, 1], X) va produce X
%=[[1, 4], [2, 2], [3, 1]].

%eliminare(L,E,R)
%L-lista din care stergem
%E-Elementul sters
%R-Rezultat
%(i,i,o)

eliminare([],_,[]).
eliminare([E|T],E,R):-eliminare(T,E,R).
eliminare([H|T],E,[H|R]):-eliminare(T,E,R).


cautareElement([],_,0).
cautareElement([E|T],E,R):-!,cautareElement(T,E,R1),R is R1+1.
cautareElement([_|T],E,R):-cautareElement(T,E,R).


%numar(L,R)
%L-lista modificata
%R-raspuns


numar([],[]).
numar([H|T],[L|R]):-cautareElement([H|T],H,N),
	L =[H|N],
	eliminare(T,H,R1),
	numar(R1,R).