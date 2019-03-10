#pragma once
#include "Vector.h"

Vector createVector()
{
	Vector vector;
	vector.length = 0;
	vector.capacity = 100;
	return vector;
}

Package getPackage(Vector * vector, int position)
{
	return vector->packages[position];
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

int append(Vector * vector, Package * package)
{
	if (vector->length < vector->capacity) {
		vector->packages[vector->length] = *package;
		vector->length++;
		return 1;
	}
	return 0;
}

int del(Vector * vector, Package * package)
{
	int i; 
	int position = search(vector, package);
	if (position != -1)
	{
		for (i = position; i < length(vector) - 1; i++)
			vector->packages[i] = vector->packages[i + 1];
		vector->length = vector->length - 1;
		return 1;
	}else return 0;
}
