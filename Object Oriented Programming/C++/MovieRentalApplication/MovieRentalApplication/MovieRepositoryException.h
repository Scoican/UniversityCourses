#pragma once
#include <string>

using namespace std;
class MovieRepositoryException
{
private:
	string msg;
public:
	MovieRepositoryException(string msg) :msg{msg}{}
	~MovieRepositoryException() = default;
	friend ostream& operator << (ostream& out, const MovieRepositoryException& ex);

};

ostream& operator<<(ostream& out, const MovieRepositoryException& ex);
