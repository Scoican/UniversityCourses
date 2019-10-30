#include "Validator.h"
#include <string>

using namespace std;

Validator::Validator()
{
}

void Validator::checkProduct(Produs p) {
	string error = "";
	if (p.getName() == "") {
		error = error + "Error at name \n";
	}
	if (p.getPrice() < 1.0 || p.getPrice() > 100.0) {
		error = error + "Error at price \n";
	}
	if (error != "") {
		throw exception("invalid data");
	}
}

Validator::~Validator()
{
}