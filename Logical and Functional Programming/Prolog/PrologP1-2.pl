%a.Sa se scrie un predicat care determina cel mai mic multiplu comun al
%elementelor unei liste formate din numere intregi.

%cmmdc(A,B,R)
%A,B,R-Numere intregi
%R-cel mai mare divizor comun dintre A si B
%(i,i,o)

cmmdc(0,B,R):-!,R = B.
cmmdc(A,0,R):-!,R = A.
cmmdc(A,B,R):-A>B,!,C is A mod B,cmmdc(C,B,R).
cmmdc(A,B,R):-A<B,C is B mod A,cmmdc(A,C,R).

%cmmmcLista(L,E,R)
%L-lista pe care o vom calcula
%E-cmmmc de pana acum
%R-rezultat
%(i,i,o)

cmmmcLista([],E,R):-!,R is E.
cmmmcLista([H|T],E,R):-cmmdc(E,H,R1),
	A is E*H,
	B is A/R1,
	cmmmcLista(T,B,R).

%cmmmc(L,R)
%L-lista de calculat
%R-Rezultat
%(i,o)

cmmmc(L,R):-cmmmcLista(L,1,R).


%b.Sa se scrie un predicat care adauga dupa 1-ul, al 2-lea, al 4-lea, al
%8-lea ...element al unei liste o valoare v data.

%inserare(L,P,C,V,R)
%L-lista in care adaugam
%P-Pozitia curenta
%C-Urmatorul pas unde adaugam
%V-valoarea adaugata
%R-Rezultat
%(i,i,i,i,o)

inserare([],_,_,_,R):-R =[].
inserare([H|T],P,C,V,R):-C=:=P,
	!,
	P1 is P+1,
	C1 is C*2,
	inserare(T,P1,C1,V,R1),
	R =[H,V|R1].
inserare([H|T],P,C,V,R):-P1 is P+1,
	inserare(T,P1,C,V,R1),
	R =[H|R1].

inserareValoare(L,V,R):-inserare(L,1,1,V,R).





















