%Sa se scrie un predicat care adauga intr-o lista dupa fiecare element par
%valoarea 1.

%adaugarePar(L,R)
%L-lista in care adaugam
%R-rezultat
%(i,o)

adaugarePar([],R):-!,R=[].
adaugarePar([H|T],R1):-H mod 2 =:= 0,!,adaugarePar(T,R),R1=[H,1|R].
adaugarePar([H|T],R1):-adaugarePar(T,R),R1=[H|R].


%a. Sa se scrie un predicat care intoarce diferenta a doua multimi.

%cautareElement(L,E)
%L-lista in care se cauta
%E-elementul cautat
%(i,i)

cautareElement([],_):-!,fail.
cautareElement([E|_],E):-!.
cautareElement([_|T],E):-cautareElement(T,E).

%diferentaMultimi(L1,L2,R)
%L1-lista 1
%L2-lista 2
%R-rezultat
%(i,i,o)

diferentaMultimi([],_,R):-!,R=[].
diferentaMultimi([H|T],L2,R1):-not(cautareElement(L2,H)),
	diferentaMultimi(T,L2,R),
	R1=[H|R].
%diferentaMultimi(L1,[H|T],R1):-not(cautareElement(L1,H)),
	%diferentaMultimi(L1,T,R),
	%R1=[H|R].
diferentaMultimi([_|T],L2,R1):-diferentaMultimi(T,L2,R),
	R1=R.


%diferenta(L1,L2,R)
%L1-lista 1
%L2-lista 2
%R-rezultat
%(i,i,o)

diferenta(L1,L2,R1):-diferentaMultimi(L1,L2,R2),
	diferentaMultimi(L2,L1,R3),
	append(R2,R3,R1).
