#pragma once
#include "Vector.h"


Vector createVector()
{
	Vector vector;
	vector.length = 0;
	vector.capacity = 2;
	vector.packages = malloc(sizeof(Package)*vector.capacity);
	return vector;
}

void destroyVector(Vector* vector) {
	for (int i = 0; i < vector->length; i++)
	{
		destroyPackage(&vector->packages[i]);
	}
	free(vector->packages);
	vector->length = 0;
	vector->packages = NULL;
}

void ensureCapacity(Vector* vector) {
	if (vector->length < vector->capacity) {
		return;
	}
	Package* newVector = malloc(sizeof(Package)*(vector->capacity + 2));
	for (int i = 0; i <vector->length; i++)
	{
		newVector[i] = vector->packages[i];
	}
	free(vector->packages);
	vector->packages = newVector;
	vector->capacity += 2;
}


Package getPackage(Vector * vector, int position)
{
	return vector->packages[position];
}

Package copyPackage(Vector * vector, int position)
{
	return createPackage(getId(&vector->packages[position]), getType(&vector->packages[position]), getDestination(&vector->packages[position]), getDate(&vector->packages[position]), getPrice(&vector->packages[position]));
}

void swapElem(Vector * vector, int i, int j)
{
	swap(&vector->packages[i], &vector->packages[j]);
}

int length(Vector * vector)
{
	return vector->length;
}

int getCapacity(Vector * vector)
{
	return vector->capacity;
}

int search(Vector * vector, Package * package)
{
	int i;
	Package p;
	for (i = 0; i < length(vector); i++)
	{
		p = vector->packages[i];
		if (comparePackages(&p, package) == 1)
			return i;
	}
	return -1;
}

void append(Vector * vector, Package * package)
{
	ensureCapacity(vector);
	vector->packages[vector->length] = *package;
	vector->length++;
}

int del(Vector * vector, Package * package)
{
	int i; 
	int position = search(vector, package);
	if (position != -1)
	{
		destroyPackage(package);
		for (i = position; i < length(vector) - 1; i++)
			vector->packages[i] = vector->packages[i + 1];
		vector->length = vector->length - 1;
		return 1;
	}else return 0;
}
