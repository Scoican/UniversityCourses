#pragma once
#include "Service.h"
#include "Repository.h"
#include <string.h>
Service createService()
{
	Service service;
	service.repository = createRepository();
	return service;
}

void destroyService(Service* service) {
	destroyRepository(&service->repository);
}

int addPackage(Service* service, int id, char* type, char* destination, char* date, double price) {
	Package package = createPackage(id, type, destination, date, price);
	if (findPosition(&service->repository, &package) == -1) {
		add(&service->repository, &package);
		return 1;
	}else {
		destroyPackage(&package);
		return -1;
	}
	
}
int remPackage(Service* service, int id) {
	Package* package = findOne(&service->repository,id);
	if (package != NULL) {
		return rem(&service->repository, package);
	}
	return 0;
}
int updatePackage(Service* service, int id, char* type, char* destination, char* date, double price) {
	Package package = createPackage(id, type, destination, date, price);
	return update(&service->repository, &package);
}
Package* findOnePackage(Service* service, int id) {
	return findOne(&service->repository, id);
}
int findPositionOfPackage(Service* service, int id) {
	return findPosition(&service->repository, findOne(&service->repository,id));
}
Package* findAllPackages(Service* service) {
	return findAll(&service->repository);
}

Vector getAll(Service* service) {
	return service->repository.vector;
}

Package* sortByPrice(Vector * vector, Service  * service, int reverse) {
	int i, j;
	*vector = getAll(service);
	Package package1, package2;
	for (i = 0; i < length(vector) - 1; i++)
	{
		package1 = getPackage(vector, i);
		for (j = i + 1; j < length(vector); j++)
		{
			package2 = getPackage(vector, j);
			if ((reverse == 0) && (getPrice(&package1) > getPrice(&package2)))
			{
				swapElem(vector, i, j);
				package1 = getPackage(vector, i);
			}
			else {
				if ((reverse == 1) && (getPrice(&package1) < getPrice(&package2)))
				{
					swapElem(vector, i, j);
					package1 = getPackage(vector, i);
				}
			}
		}
	}
	return vector->packages;
}

Package* sortByDestination(Vector * vector, Service  * service, int reverse) {
	int i, j;
	*vector = getAll(service);
	Package package1, package2;
	for (i = 0; i < length(vector) - 1; i++)
	{
		package1 = getPackage(vector, i);
		for (j = i + 1; j < length(vector); j++)
		{
			package2 = getPackage(vector, j);
			if ((reverse == 0) && (strcmp(getDestination(&package2),getDestination(&package1)))<0)
			{
				swapElem(vector, i, j);
				package1 = getPackage(vector, i);
			}
			else {
				if ((reverse == 1) && (strcmp(getDestination(&package2), getDestination(&package1)))>0)
				{
					swapElem(vector, i, j);
					package1 = getPackage(vector, i);
				}
			}
		}
	}
	return vector->packages;
}

void filterByDestination(Vector* newVector,Service* service,char* destination) {
	Package package1;
	int i;
	for (i = 0; i < length(&service->repository.vector); i++) {
		package1 = copyPackage(&service->repository.vector,i);
		if (strcmp(getDestination(&package1), destination) == 0) {
			append(newVector, &package1);
		}
		else {
			destroyPackage(&package1);
		}
	}
}

void filterByPrice(Vector* newVector,Service* service,double price) {
	Package package1;
	int i;
	for (i = 0; i < length(&service->repository.vector); i++) {
		package1 = copyPackage(&service->repository.vector, i);
		if (getPrice(&package1) == price) {
			append(newVector, &package1);
		}
		else {
			destroyPackage(&package1);
		}
	}
}

void filterByType(Vector* newVector,Service* service,char* type) {
	Package package1;
	int i;
	for (i = 0; i < length(&service->repository.vector); i++) {
		package1 = copyPackage(&service->repository.vector, i);
		if (strcmp(getType(&package1), type) == 0) {
			append(newVector, &package1);
		}
		else {
			destroyPackage(&package1);
		}
	}
}


