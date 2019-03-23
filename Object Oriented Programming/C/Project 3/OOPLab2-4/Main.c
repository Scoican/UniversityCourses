#include "UI.h"
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

void testAll() {
	testConcurent();
	testeVectorDinamic();
	testeConcurentRepo();
	testValidator();
	testeService();
}


void main() {
	testAll();
	
	ConcurentRepo* repo = creeazaRepo();
	Concurent* c1 = creeazaConcurent("Harjoc", "Sergiu", 100);
	Concurent* c2 = creeazaConcurent("Maniu", "Mihai", 85);
	Concurent* c3 = creeazaConcurent("Zoltan", "Gicu", 89);
	adaugaConcurent(repo, c1);
	adaugaConcurent(repo, c2);
	adaugaConcurent(repo, c3);
	Service* service = creeazaService(repo);

	distrugeConcurent(c1);
	distrugeConcurent(c2);
	distrugeConcurent(c3);
	UI* ui = creeazaUI(service);
	startUI(ui);
	distrugeUI(ui);

	_CrtDumpMemoryLeaks();
}