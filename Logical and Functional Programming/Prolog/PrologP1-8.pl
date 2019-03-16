%a.Sa se scrie un predicat care testeaza daca o lista este multime.
%b.Sa se scrie un predicat care elimina primele 3 aparitii ale unui
%element intr-o lista. Daca elementul apare mai putin de 3 ori, se va
%elimina de cate ori apare.



%verificareMultime(L)
%L-lista de verificat
%(i)

verificareMultime([]):-true.
verificareMultime([H|T]):-cautareElement(T,H,R),R>0,fail.
verificareMultime([H|T]):-cautareElement(T,H,R),R=0,verificareMultime(T).

%eliminarePrimaAparitie(L,E,R)
%L-lista din care elimina
%E-elementul pe care il eliminam
%R-rezultat
%(i,i,o)

eliminareNrAparitie([],_,_,[]).
eliminareNrAparitie([H|T],H,N,R):-N>0,N1 is N-1 ,eliminareNrAparitie(T,H,N1,R).
eliminareNrAparitie([H|T],E,N,[H|R]):-eliminareNrAparitie(T,E,N,R).

%eliminare3(L,E,R)
%L-lista din care eliminam
%R-rezultat
%(i,o)

eliminare3([],[]).
eliminare3([H|T],H,R):-
	cautareElement(T,H,N),
	N>=2,
	eliminareNrAparitie([H|T],H,3,R).
eliminare3([H|T],H,R):-
	cautareElement(T,H,N),
	N<2,
	eliminareNrAparitie(T,H,N,R).
eliminare3([H|T],E,[H|R]):-eliminare3(T,E,R).
