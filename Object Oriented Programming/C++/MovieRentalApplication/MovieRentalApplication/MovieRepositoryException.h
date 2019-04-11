#pragma once
#include <string>

using namespace std;

/*
Class for exceptions thrown in Movie Repository
*/
class MovieRepositoryException
{
private:
	string msg;
public:

	//Constructor
	MovieRepositoryException(string msg) :msg{msg}{}
	//Deconstructor
	~MovieRepositoryException() = default;
	//Output overload
	friend ostream& operator << (ostream& out, const MovieRepositoryException& ex);

};

ostream& operator<<(ostream& out, const MovieRepositoryException& ex);
