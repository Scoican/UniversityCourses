#pragma once
#include <string>
#include "MyList.h"
#include "Movie.h"
using std::string;
using std::ostream;

class ValidateException {
	Vector <string> mesaje;
public:
	ValidateException(const Vector<string>& errors) :mesaje{ errors } {}
	string getMsg() {
		string msg = "";
		for (int i = 0; i < mesaje.size(); i++)
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
