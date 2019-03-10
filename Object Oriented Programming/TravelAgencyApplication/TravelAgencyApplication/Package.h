#pragma once

typedef struct {
	int id;
	char type[40];
	char destination[50];
	char date[11];
	double price;
}Package;

Package createPackage(int id, char type[40], char destination[50], char date[11], double price);

int getId(Package* package);
char* getType(Package* package);
char* getDestination(Package* package);
char* getDate(Package* package);
double getPrice(Package* package);

void setType(Package* package, char type[40]);
void setDestination(Package* package, char destination[50]);
void setDate(Package* package, char date[11]);
void setPrice(Package* package, double price);

int comparePackages(Package* package1, Package* package2);
void swap(Package* package1, Package* package2);