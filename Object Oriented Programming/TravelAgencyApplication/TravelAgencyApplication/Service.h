#pragma once
#include "Repository.h"

typedef struct {
	Repository repository;
}Service;

Service createService();

int addPackage(Service* service, int id, char type[40], char destination[50], char date[11], double price);
int remPackage(Service* service,int id);
int updatePackage(Service* service, int id, char type[40], char destination[50], char date[11], double price);
Package* findOnePackage(Service* service,int id);
int findPositionOfPackage(Service* service,int id);
Vector getAll(Service* service);
Package* findAllPackages(Service* service);
Package* sortByPrice(Vector * vector, Service  * service, int reverse);
Package* sortByDestination(Vector * vector, Service  * service, int reverse);
Package* filterByDestination(Vector* vector,Service* service, char destination[50]);
Package* filterByPrice(Vector* newVector,Service* service, double price);
Package* filterByType(Vector* newVector,Service* service, char type[40]);