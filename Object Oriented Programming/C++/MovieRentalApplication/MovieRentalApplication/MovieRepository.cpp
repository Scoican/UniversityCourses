#include "MovieRepository.h"

bool MovieRepository::exist(const Movie & movie)
{
	try {
		Movie foundMovie = find(movie.getName(), movie.getLaunchYear());
		return true;
	}
	catch (MovieRepositoryException&) {
		return false;}
}

void MovieRepository::store(const Movie & movie)
{
	if (exist(movie)) {
		throw MovieRepositoryException("This movie already exists in the data base");
	}
	movies.add(movie);
}

void MovieRepository::remove(const Movie & movie)
{
	if (!exist(movie)) {
		throw MovieRepositoryException("This movie doesn't exist in the data base");
	}
	//auto it = find_if(movies.begin(), movies.end(), [&movie](const Movie& el) {return movie == el; });
	movies.erase(movies.find(movie));

}

void MovieRepository::update(const Movie & movie)
{
	if (!exist(movie)) {
		throw MovieRepositoryException("This movie doesn't exist in the data base");
	}
	auto it = find_if(movies.begin(), movies.end(), [&movie](const Movie& el) {return el.getName() == movie.getName() && el.getLaunchYear() == movie.getLaunchYear(); });
	*it = movie;
}

Movie MovieRepository::find(const string & name, const int launchYear) const
{
	for (const Movie& m : movies) {
		if (m.getName() == name && m.getLaunchYear() == launchYear) {
			return m;
		}
	}
	throw MovieRepositoryException("This movie doesn't exist in the data base");
	
}

const Vector<Movie>& MovieRepository::getAll() const noexcept
{
	return this->movies;
}
