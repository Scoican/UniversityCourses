#pragma once
#include <string>
#include "MyList.h"
#include "Movie.h"
using std::string;
using std::ostream;
#include <vector>

class ValidateException {
	vector <string> mesaje;
public:
	ValidateException(const vector<string>& errors) :mesaje{ errors } {}
	string getMsg() {
		string msg = "";
		for (unsigned int i = 0; i < mesaje.size(); i++)
			msg += mesaje[i];
		return msg;
	};
	friend ostream& operator<<(ostream& out, const ValidateException& ex);
};

ostream& operator<<(ostream& out, const ValidateException& ex);

class MovieValidator {
public:
	void validate(const Movie& m);
};
