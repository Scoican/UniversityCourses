#pragma once
#include "Repository.h"
#include "Validator.h"
#include "Sortare.h"


typedef struct {
	ConcurentRepo* repo;
	VectorDinamic* undoList;
}Service;

//Added by Scoican
//Function that undoes an action
//Returns 0 if there are undo actions available,1 otherwise
int undo(Service* srv);

//Functiile de alocare si dealocare a unui Service
Service* creeazaService(ConcurentRepo* repo);									    //T
void distrugeService(Service* srv);													//T

//Functia returneaza un pointer la ConcurentRepo din interiorul srv-Service dat ca parametru
ConcurentRepo * getRepo(Service * srv);

//Functia valideaza nume,prenume,scor
// lungimea(nume)<20  ; lungimea(prenume)<20   ;  0<=scor<=100
//returneaza 1 daca s-a reusit adaugarea si 0 in caz contrar
int adaugaService(Service* srv, char* nume, char* prenume, float scor);				//T

//Functia sterge concurentul cu numele si prenumele din lista de parametrii
//nume,prenume- char*    srv- Service*
//lungime(nume)<20, lungime(prenume)<20
//returneaza:  -scorul concurentului sters daca s-a reusit stergerea
//			   -0, altfel
float stergeService(Service* srv, char* nume, char* prenume);						//T

//Functia actualizeaza scorul unui concurent cu numele=nume si prenumele=prenume
//input:r-ConcurentRepo
//		nume,prenume-char*
//		scor-float
//returneaza: -vechiul scor al concurentului actualizat
//			  -0, daca nu s-a reusit actualizarea
float actualizeazaService(Service * srv, char * nume, char * prenume, float scor);  //T


//Functia de filtrare dupa scor
//Returneaza un pointer la un ConcurentRepo ce contine doar concurentii cu scorul mai mic decat score(float)
ConcurentRepo * filtreazaDupaScor(Service * srv, float score);

//Functia de sortare a concurentilor in ordine alfabetica
ConcurentRepo * sortareNume(Service * srv);

//Functia de sortare a concurentilor in ordinea performantei
ConcurentRepo * sortareScor(Service * srv);

//teste
void testeService();
