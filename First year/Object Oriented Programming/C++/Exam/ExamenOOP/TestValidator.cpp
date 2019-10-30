#include "TestValidator.h"

void testValidator() {
	Validator validator;
	Produs p1 = Produs(1, "1", "1", 1.1);
	Produs p2 = Produs(1, "", "1", 1.1);
	Produs p3 = Produs(1, "1", "1", 10000);
	try {
		validator.checkProduct(p2);
		assert(false);
	}
	catch (exception) {
		assert(true);
	}
	try {
		validator.checkProduct(p3);
		assert(false);
	}
	catch (exception) {
		assert(true);
	}
}