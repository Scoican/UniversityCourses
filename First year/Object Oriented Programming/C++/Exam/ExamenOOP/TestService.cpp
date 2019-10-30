#include "TestService.h"

void testService() {
	Repository repo{ "Text2.txt" };
	Validator validator;
	Service serv{ repo,validator };
	assert(serv.getAll().size() == 0);
	serv.addProdus(1, "1", "1", 1.1);
	assert(serv.getAll().size() == 1);
	try {
		serv.addProdus(1, "1", "1", 1.1);
		assert(false);
	}
	catch (exception) {
	}
	try {
		serv.addProdus(1, "", "1", 10000.1);
		assert(false);
	}
	catch (exception) {
	}
	assert(serv.findProdus(2) == false);
	assert(serv.findProdus(1) == true);
	serv.addProdus(2, "1", "1", 3.1);
	serv.addProdus(3, "1", "1", 5.1);
	serv.addProdus(4, "1", "1", 2.1);
	vector<Produs> sorted = serv.sortProducts();
	assert(sorted[0].getId() == 3);
	assert(sorted[1].getId() == 2);
	assert(sorted[2].getId() == 4);
	assert(sorted[3].getId() == 1);
}