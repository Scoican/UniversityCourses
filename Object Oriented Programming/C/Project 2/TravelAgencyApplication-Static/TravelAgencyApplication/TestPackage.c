#pragma once
#include "TestPackage.h"
#include <stdio.h>
#include <string.h>
void callTestsPackage() {
	testCreatePackage();
	testGetters();
	testSetters();
	testComparePackages();
	testSwap();
}

void testCreatePackage() {
	int id = 1;
	char type[40] = "City break";
	char destination[50] = "Montecarlo";
	char date[11] = "11/12/2012";
	double price = 200;
	Package package = createPackage(id, type, destination, date, price);
	assert(package.id == 1);
	assert(strcmp(package.type, "City break") == 0);
	assert(strcmp(package.destination, "Montecarlo") == 0);
	assert(strcmp(package.date, "11/12/2012") == 0);
	assert(package.price == 200);
}

void testGetters() {
	int id = 1;
	char type[40] = "City break";
	char destination[50] = "Montecarlo";
	char date[11] = "11/12/2012";
	double price = 200;
	Package package = createPackage(id, type, destination, date, price);
	strcmp(getType(&package), type);
	assert(getId(&package)== 1);
	assert(strcmp(getType(&package), "City break") == 0);
	assert(strcmp(getDestination(&package), "Montecarlo") == 0);
	assert(strcmp(getDate(&package), "11/12/2012") == 0);
	assert(getPrice(&package) == 200);
}

void testSetters() {
	int id = 1;
	char type[40] = "";
	char destination[50] = "";
	char date[11] = "";
	double price = 0;
	Package package = createPackage(id, type, destination, date, price);
	setType(&package, "City break");
	setDestination(&package, "Montecarlo");
	setDate(&package, "11/12/2012");
	setPrice(&package, 200);
	assert(getId(&package) == 1);
	assert(strcmp(getType(&package), "City break") == 0);
	assert(strcmp(getDestination(&package), "Montecarlo") == 0);
	assert(strcmp(getDate(&package), "11/12/2012") == 0);
	assert(getPrice(&package) == 200);
}

void testComparePackages() {
	Package package1 = createPackage(1, "type", "destination", "date", 200);
	Package package2 = createPackage(2, "type", "destination", "date", 200);
	assert(comparePackages(&package1, &package2) == 0);
}

void testSwap()
{
	Package package1 = createPackage(1, "type", "destination", "date", 200);
	Package package2 = createPackage(2, "type", "destination", "date", 200);
	swap(&package1, &package2);
	assert(package2.id == 1);
	assert(package1.id == 2);

}
