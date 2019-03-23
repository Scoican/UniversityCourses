#pragma once
#include "Service.h"
#include "Validator.h"
#include <stdio.h>
int run();
void menu();
void readId(int *id);
void readType(char type[40]);
void readDestination(char destination[50]);
void readPrice(double *price);
void readDate(char date[11]);
void consoleAdd(Service *service);
void consoleUpdate(Service *service);
void consoleRem(Service *service);
void consoleFindAll(Service *service);
void consoleSortByPrice(Service *service,int mode);
void consoleSortByDestination(Service *service,int mode);
void consoleFilterByDestination(Service *service);
void consoleFilterByPrice(Service *service);
void consoleFilterByType(Service *service);

