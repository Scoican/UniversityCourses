#pragma once
#include "Package.h"

typedef struct {
	Package packages[200];
	int length;
	int capacity;
}Vector;

Vector createVector();

Package getPackage(Vector* vector, int position);

void swapElem(Vector* vector, int i, int j);

int length(Vector* vector);

int getCapacity(Vector* vector);

int search(Vector* vector, Package* package);

int append(Vector* vector, Package* package);

int del(Vector* vector, Package* package);