#include "Validator.h"

const char * validare(char * nume, char * prenume, float scor)
{
	if (strlen(nume) > 20)
		return "Numele este prea lung";
	if (strlen(prenume) > 20)
		return "Prenumele este prea lung";
	if (scor < 0 || scor>100)
		return "Scor invalid(0-100)";
	return "Datele sunt ok";
}

void testValidator()
{
	assert(strcmp(validare("Iosif", "Mihai", 22),"Datele sunt ok") ==0);
	assert(strcmp(validare("aaaaaaaaaaaaaaaaaaaaa", "CAMELIU", 90), "Numele este prea lung") == 0);
	assert(strcmp(validare("aaaaaaaa", "CAMELIUaaaaaaaaasssssa", 90), "Prenumele este prea lung") == 0);
	assert(strcmp(validare("Ana", "Mihai", 120), "Scor invalid(0-100)") == 0);
}
