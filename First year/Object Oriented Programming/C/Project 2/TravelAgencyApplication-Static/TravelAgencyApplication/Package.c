#include "Package.h"
#include <string.h>

Package createPackage(int id, char type[40], char destination[50], char date[11], double price)
{
	Package package;
	package.id = id;
	strcpy(package.type, type);
	strcpy(package.destination, destination);
	strcpy(package.date, date);
	package.price = price;
	return package;
}

int getId(Package * package)
{
	return package->id;
}

char* getType(Package * package)
{
	return package->type;
}

char* getDestination(Package * package)
{
	return package->destination;
}

char* getDate(Package * package)
{
	return package->date;
}

double getPrice(Package * package)
{
	return package->price;
}

void setType(Package * package, char type[40])
{
	strcpy(package->type, type);
}

void setDestination(Package * package, char destination[50])
{
	strcpy(package->destination, destination);
}

void setDate(Package * package, char date[11])
{
	strcpy(package->date, date);
}

void setPrice(Package * package, double price)
{
	package->price = price;
}

int comparePackages(Package * package1, Package * package2)
{
	if (getId(package1) == getId(package2)) {
		return 1;
	}else return 0;
}

void swap(Package * package1, Package * package2)
{
	Package aux = createPackage(0, "", "", "", 1);
	aux.id = getId(package1);
	strcpy(aux.type, getType(package1));
	strcpy(aux.destination, getDestination(package1));
	strcpy(aux.date, getDate(package1));
	aux.price = getPrice(package1);

	(*package1).id = getId(package2);
	strcpy((*package1).type, getType(package2));
	strcpy((*package1).destination, getDestination(package2));
	strcpy((*package1).date, getDate(package2));
	(*package1).price = getPrice(package2);

	(*package2).id = getId(&aux);
	strcpy((*package2).type, getType(&aux));
	strcpy((*package2).destination, getDestination(&aux));
	strcpy((*package2).date, getDate(&aux));
	(*package2).price = getPrice(&aux);
}
