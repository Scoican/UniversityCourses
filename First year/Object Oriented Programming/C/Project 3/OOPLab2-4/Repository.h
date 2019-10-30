#pragma once
#include "Concurent.h"
#include "VectorDinamic.h"

typedef struct {
	VectorDinamic* concurenti;
	int lungime;
}ConcurentRepo;

//Functiile de alocare si dealocare a unui ConcurentRepo
ConcurentRepo* creeazaRepo();														//T	
void distrugeRepo(ConcurentRepo* r);												 //T

//returneaza NULL daca nu s-a gasit concurent cu numele si prenumele date
//returneaza adresa concurentului , in caz contrar
Concurent* cauta(ConcurentRepo* r, char* nume, char* prenume);                       //T

//Functia returneaza pozitia pe care se gaseste concurentul cu numele si prenumele
//date ca parametru(pointeri la char)
int cautaPozitie(ConcurentRepo* r, char* nume, char* prenume);                       //T

//adauga un concurent p in repo-ul r
//returneaza 1 daca adaugarea s-a facut cu succes si 0 in caz contrar
int adaugaConcurent(ConcurentRepo* r, Concurent* p);                                 //T

//returneaza lungimea vectorului dinamic din interiorul repository-ului
int getLungimeRepo(ConcurentRepo* r);                                                //T

//Param: r-pointer la un ConcurentRepo
//		 pos-numar intreg  : pos>=0 & pos<lungime_repo
//Returneaza -concurentul aflat pe pozitia pos in repo-ul r 
//           -NULL, in caz contrar
Concurent* pozConcurent(ConcurentRepo* r, int pos);                                  //T

//Functia de stergere a unui concurent din repo pe baza numelui+prenumelui
//r-pointer la ConcurentRepo, nume,prenume-pointer la char
//returneaza 0 daca nu s-a gasit concurent cu numele si prenumele date
//returneaza scorul concurentului care a fost sters , altfel
float stergeConcurent(ConcurentRepo* r, char* nume, char* prenume);					 //T

//Functia actualizeaza scorul unui concurent cu numele=nume si prenumele=prenume
//input:r-ConcurentRepo
//		nume,prenume-char*
//		scor-float
//returneaza vechiul scor al concurentului actualizat
float actualizeaza(ConcurentRepo* r, char* nume, char* prenume, float scor);		//T

//Teste
void testeConcurentRepo();