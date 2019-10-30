#pragma once
#include "Service.h"
#include <ctype.h>

typedef struct {
	Service* srv;
}UI;

//Aloca memorie pentru interfata cu utilizatorul
UI* creeazaUI(Service* srv);

//elibereaza memoria interfetei
void distrugeUI(UI* ui);

//Functia de adauga din cadrul interfetei
//returneaza 1-adaugarea a reusit
//			 0-adaugarea a esuat
int addConcurentUI(UI * ui);

//Functia de stergere
float stergereUI(UI * ui);

//Functia de actualizare
float actualizeazaConcurentUI(UI * ui);

//Functia de afisare a concurentilor 
void afiseazaConcurenti(UI * ui);

//Functia de sortare dupa nume
void sorteazaDupaNume(UI * ui);

//Functia de sortare dupa scor
void sorteazaDupaScor(UI * ui);

void consoleUndo(UI* ui);

void startUI(UI * ui);
