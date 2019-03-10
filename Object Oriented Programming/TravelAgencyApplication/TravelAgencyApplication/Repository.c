#pragma once
#include"Repository.h"

Repository createRepository() {
	Repository repository;
	repository.vector = createVector()	;
	return repository;
}

int add(Repository *repository, Package* package) {
	return append(&repository->vector, package);
}

int findPosition(Repository *repository, Package* package) {
	int i;
	for (i = 0; i < length(&repository->vector); i++)
	{
		if (comparePackages(package,&repository->vector.packages[i]) == 1)
			return i;
	}
	return -1;
}

Package* findOne(Repository * repository, int id)
{
	int i;
	for (i = 0; i < length(&repository->vector); i++) {
		if (repository->vector.packages[i].id == id) {
			return &repository->vector.packages[i];
		}
	}
	return NULL;
}

int rem(Repository *repository, Package* package) {
	return del(&repository->vector, package);
}

int update(Repository *repository, Package* package) {
	int position = findPosition(repository, package);
	if (position != -1)
	{
		setDate(&repository->vector.packages[position], package->date);
		setDestination(&repository->vector.packages[position], package->destination);
		setType(&repository->vector.packages[position], package->type);
		setPrice(&repository->vector.packages[position], package->price);
		return 1;
	}
	return 0;
}

Package* findAll(Repository *repository) {
	return repository->vector.packages;
}