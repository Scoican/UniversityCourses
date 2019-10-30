#pragma once
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
typedef struct {
	char* nume;
	char* prenume;
	float scor;
}Concurent;

/*
Functia creeaza un concurent
input: char-nume,prenume
scor-float
output: c- concurent
*/
Concurent* creeazaConcurent(char* nume, char* prenume, float scor);

/*
Se elibereaza memoria ocupata de un concurent
Primeste ca parametru un pointer la un Concurent
*/
void distrugeConcurent(Concurent *c);

//Se face o copie a unui concurent
//c- pointer la un concurent
//returneaza un pointer la un concurent
Concurent* copiazaConcurent(Concurent* c);


//Gettere pentru Nume,Prenume si Scor
//Returneaza valorile diferitelor atribute ale Concurentului
char* getNume(Concurent* p);
char* getPrenume(Concurent* p);
float getScor(Concurent* p);

//Settere pentru Nume,Prenume si Scor
void setNume(Concurent* p, char nume[30]);
void setPrenume(Concurent* p, char prenume[30]);
void setScor(Concurent* p, float scor);

void toString(Concurent * c);

//Functia de test 
//Se testeaza getterele,setterele si functiile care creeaza,distrug si copiaza un concurent
void testConcurent();