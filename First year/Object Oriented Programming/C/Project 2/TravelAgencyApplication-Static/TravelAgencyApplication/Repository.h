#pragma once
#include <stddef.h>
#include "Package.h"
#include "Vector.h"
typedef struct {
	Vector vector;
}Repository;

Repository createRepository();

int add(Repository *repository, Package* package);
int rem(Repository *repository, Package* package);
int update(Repository *repository, Package* package);
int findPosition(Repository *repository, Package* package);
Package* findOne(Repository *repository, int id);
Package* findAll(Repository *repository);

