#include "UI.h"



UI* creeazaUI(Service* srv) {
	UI* ui = (UI*)malloc(sizeof(UI));
	ui->srv = srv;
	return ui;
}

void distrugeUI(UI* ui) {
	distrugeService(ui->srv);
	free(ui);
}

/*
Afiseaza meniul disponibil pentru problema.
Input: -
Output : Meniul este afisat in consola
*/
void printMenu()
{
	printf("\n**********************************************************\n");
	printf("1 - Adauga concurent.\n");
	printf("2 - Afiseaza concurenti.\n");
	printf("3.- Actualizeaza concurent.\n");
	printf("4.- Sterge concurent.\n");
	printf("5.- Concurentii cu un scor mai mic decat o valoare data\n");
	printf("6.- Sortarea dupa nume! \n");
	printf("7.- Sortarea dupa scor! \n");
	printf("8.- Undo! \n");
	printf("0 - to exit.\n");
	printf("**********************************************************\n");
}


int addConcurentUI(UI* ui) {
	char nume[30], prenume[30];
	float scor=0;
	printf("Introduceti numele: ");
	scanf("%29s", nume);
	nume[0] = (char)toupper(nume[0]);
	printf("Introduceti prenumele: ");
	scanf("%29s", prenume);
	prenume[0]=(char)toupper(prenume[0]);
	printf("Introduceti scorul elevului: ");
	scanf("%f", &scor);
	return adaugaService(ui->srv, nume, prenume, scor);
}
float stergereUI(UI* ui) {
	char nume[30], prenume[30];
	printf("Introduceti numele concurentului pe care doriti sa-l stergeti : ");
	scanf("%29s", nume);
	nume[0] = (char)toupper(nume[0]);
	printf("Introduceti prenumele concurentului pe care doriti sa-l stergeti: ");
	scanf("%29s", prenume);
	prenume[0] = (char)toupper(prenume[0]);
	float res;
	res = stergeService(ui->srv, nume, prenume);
	return res;
}
float actualizeazaConcurentUI(UI* ui) {
	char nume[30], prenume[30];
	float scor;
	printf("Introduceti numele concurentului pe care doriti sa-l modificati: ");
	scanf("%29s", nume);
	nume[0] = (char)toupper(nume[0]);
	printf("Introduceti prenumele concurentului pe care doriti sa-l modificati: ");
	scanf("%29s", prenume);
	prenume[0] =(char)toupper(prenume[0]);
	printf("Noul scor:");
	scanf("%f", &scor);

	float res = actualizeazaService(ui->srv, nume, prenume, scor);
	return res;
}

void afiseazaConcurenti(UI* ui) {
	ConcurentRepo* repo = getRepo(ui->srv);
	int lungime = getLungimeRepo(repo);

	if (lungime == 0){
		char* str = "Nu sunt concurenti in lista.";
		printf("%s \n", str);
	}
	else {
		for (int i = 0; i < lungime; i++){
			toString(pozConcurent(repo, i));
			printf("\n");
		}
	}
}

void afisareRepo(ConcurentRepo* repos){
	int len = getLungimeRepo(repos);
	if (len == 0){
		printf("Nu sunt concurenti cu aceasta proprietate.");
	}
	else{
		for (int i = 0; i < len; i++){
			toString(pozConcurent(repos, i));
			printf("\n");
		}
	}
}

void filtrareScor(UI* ui) {
	float score;
	printf("Introduceti scorul maxim: ");
	scanf("%f", &score);
	ConcurentRepo* res = filtreazaDupaScor(ui->srv, score);
	afisareRepo(res);
	distrugeRepo(res);
}

void sorteazaDupaNume(UI* ui) {
	ConcurentRepo* res = sortareNume(ui->srv);
	afisareRepo(res);

}

void sorteazaDupaScor(UI* ui) {
	ConcurentRepo* res = sortareScor(ui->srv);
	afisareRepo(res);
	//afiseazaConcurenti(ui);
}

//Added by scoican
void consoleUndo(UI* ui) {
	if (undo(ui->srv) == 1) {
		printf("No more undo! \n");
	}
}

void startUI(UI* ui) {

	while (1) {
		printMenu();
		int cmd;
		printf("Introduceti comanda:");
		scanf("%d", &cmd);
		switch (cmd) {
		case 1:	{
			int res = addConcurentUI(ui);
			if (res == 1)
				printf("Concurent adaugat cu succes!\n");
			else
				printf("Eroare!Concurentul nu a putut fi adaugat!\n");
			break;
		}
		case 2:{
			afiseazaConcurenti(ui);
			break;
		}
		case 3:{
			float res = actualizeazaConcurentUI(ui);

			if (res != 0)
				printf("Concurent actualizat cu succes!\n");
			else 
				printf("Concurentul nu a fost actualizat. Verificati datele introduse!\n");
			break;
		}
		case 4:{
			float res = stergereUI(ui);
			if (res == 0) 
				printf("Stergerea a esuat!\n");
			else 
				printf("Concurentul a fost sters cu succes!\n");
			break;
		}
		case 5: {
			filtrareScor(ui);
			break;
		}
		case 6: {
			sorteazaDupaNume(ui);
			break;
		}
		case 7: {
			sorteazaDupaScor(ui);
			break;
		}
		case 8: {
			consoleUndo(ui);
			break;
		}
		case 0: {
			break;
		}
		}
		if (cmd == 0)
			break;
	}
}

