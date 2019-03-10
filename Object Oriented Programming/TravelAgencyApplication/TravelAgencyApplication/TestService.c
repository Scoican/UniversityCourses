#include "TestService.h"
#include <stdio.h>
void callTestsService() {
	testCreateService();
	testAddPackage();
	testRemPackage();
	testUpdatePackage();
	testFindOnePackage();
	testFindPositionOfPackage();
	testFindAllPackages();
	testGetAll();
	testSortByPrice();
	testSortByDestination();
	testFilterByDestination();
	testFilterByType();
	testFilterByPrice();
}
void testCreateService() {
	Service service = createService();
	Package package1 = createPackage(1, "type", "destination", "date", 200);
	assert(service.repository.vector.capacity == 100);
	assert(service.repository.vector.length == 0);
	add(&service.repository, &package1);
	assert(comparePackages(&service.repository.vector.packages[0], &package1) == 1);
}
void testAddPackage() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == -1);
}
void testRemPackage() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(remPackage(&service, 1) == 1);
	assert(remPackage(&service, 1) == 0);
}
void testUpdatePackage() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(updatePackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(updatePackage(&service, 2, "type", "destination", "date", 200) == 0);
}
void testFindOnePackage() {
	Service service = createService();
	assert(findOnePackage(&service,1) == NULL);
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(comparePackages(findOnePackage(&service, 1),&service.repository.vector.packages[0])==1);
}
void testFindPositionOfPackage() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(findPositionOfPackage(&service, 1) == 0);
}
void testFindAllPackages() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Package* packages= findAllPackages(&service);
	assert(comparePackages(&packages[0], &service.repository.vector.packages[0]) == 1);
	assert(comparePackages(&packages[1], &service.repository.vector.packages[1]) == 1);
}

void testGetAll() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 200) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector = getAll(&service);
	assert(getPackage(&vector, 0).id == 1);
}
void testSortByPrice() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "destination", "date", 100) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector;
	Package* packages = sortByPrice(&vector, &service, 1);
	assert(packages[0].id == 2);
	packages = sortByPrice(&vector, &service, 0);
	assert(packages[0].id == 1);
}
void testSortByDestination() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "xdestination", "date", 100) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector;
	Package* packages = sortByDestination(&vector, &service, 0);
	assert(packages[0].id == 2);
	packages = sortByDestination(&vector, &service, 1);
	assert(packages[0].id == 1);
}
void testFilterByDestination() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "xdestination", "date", 100) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector = createVector();
	Package* packages = filterByDestination(&vector, &service,"destination");
	assert(packages[0].id == 2);
	assert(length(&vector) == 1);
}
void testFilterByType() {
	Service service = createService();
	assert(addPackage(&service, 1, "xtype", "xdestination", "date", 100) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector = createVector();
	Package* packages = filterByType(&vector, &service, "type");
	assert(packages[0].id == 2);
	assert(length(&vector) == 1);
}
void testFilterByPrice() {
	Service service = createService();
	assert(addPackage(&service, 1, "type", "xdestination", "date", 100) == 1);
	assert(addPackage(&service, 2, "type", "destination", "date", 200) == 1);
	Vector vector = createVector();
	Package* packages = filterByPrice(&vector, &service, 200);
	assert(packages[0].id == 2);
	assert(length(&vector) == 1);
}