#include "MovieRepositoryException.h"



ostream& operator<<(ostream& out, const MovieRepositoryException& ex) {
	out << ex.msg;
	return out;
}
