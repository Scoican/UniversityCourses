#include "MovieFileRepository.h"
#include <fstream>
using namespace std;

void MovieFileRepository::loadData() {
	ifstream in(file_in);
	string name;
	string genre;
	string launchYear;
	string leadingActor;

	if (!in.is_open())throw MovieRepositoryException("Can't open file!");

	while (!in.eof()) {
		std::getline(in, name);
		std::getline(in, genre);
		std::getline(in, launchYear);
		std::getline(in, leadingActor);
		if (in.eof()) {
			break;
		}
		Movie movie = Movie(name, genre, std::stoi(launchYear), leadingActor);
		try {
			movies.push_back(movie);
		}
		catch (MovieRepositoryException&) {
			continue;
		}
	}
	in.close();
}
void MovieFileRepository::store(const Movie& movie) {
	MovieRepository::store(movie);
	saveData();
}

void MovieFileRepository::remove(const Movie& movie) {
	MovieRepository::remove(movie);
	saveData();
}

void MovieFileRepository::update(const Movie& movie) {
	MovieRepository::update(movie);
	saveData();
}

void MovieFileRepository::saveData() {
	ofstream out(file_in);
	if (!out.is_open()) { //verify if the stream is opened
		std::string msg("Unable to open file:");
		throw MovieRepositoryException(msg);
	}
	for (Movie e : movies)
	{
		out << e.getName() << endl << e.getGenre() << endl << e.getLaunchYear() << endl << e.getLeadingActor() << endl;
	}
	out.close();
}


