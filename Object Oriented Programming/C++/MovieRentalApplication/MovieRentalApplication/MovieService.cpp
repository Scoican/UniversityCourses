#include "MovieService.h"


void MovieService::addMovie(const string & name, const string & genre, const int launchYear, const string & leadingActor)
{
	Movie movie = Movie(name, genre, launchYear, leadingActor);
	validator.validate(movie);
	movieRepository.store(movie);
}

void MovieService::removeMovie(const string & name, const int launchYear)
{
	Movie movie = findMovie(name, launchYear);
	validator.validate(movie);
	movieRepository.remove(movie);
}

void MovieService::updateMovie(const string & name, const string & genre, const int launchYear, const string & leadingActor)
{
	Movie movie = Movie(name, genre, launchYear, leadingActor);
	validator.validate(movie);
	movieRepository.update(movie);
}

Movie MovieService::findMovie(const string & name, const int launchYear) const
{
	return movieRepository.find(name, launchYear);
}

const Vector<Movie> MovieService::getAll() const noexcept
{
	return movieRepository.getAll();
}

Vector<Movie> MovieService::generalSort(ComparingFunction comparingFunction) const
{
	Vector<Movie> vector{ movieRepository.getAll() };	
	for (int i = 0; i < vector.size()-1; i++)
	{
		for (int j = i+1; j < vector.size(); j++)
		{
			if (comparingFunction(vector[i], vector[j])==false) {
				Movie aux = vector[i];
				vector[i] = vector[j];
				vector[j] = aux;
			}
		}
	}
	return vector;
}

Vector<Movie> MovieService::sortByName() const
{
	return generalSort(compareByName);
}

Vector<Movie> MovieService::sortByGenre() const
{
	return generalSort(compareByGenre);
}

Vector<Movie> MovieService::sortByLaunchYear() const
{
	return generalSort(compareByLaunchYear);
}

Vector<Movie> MovieService::sortByLeadingActor() const
{
	return generalSort(compareByLeadingActor);
}

Vector<Movie> MovieService::generalfilter(function<bool(const Movie&)> fct) const
{
	Vector<Movie> filtered;
	for (const auto& movie : movieRepository.getAll()) {
		if (fct(movie)) {
			filtered.add(movie);
		}
	}
	return filtered;
}

Vector<Movie> MovieService::filterByYear(int year) const
{
	return generalfilter([year](const Movie& movie)noexcept {
		return movie.getLaunchYear() >= year; });
}

Vector<Movie> MovieService::filterByName(string& name) const
{
	return generalfilter([name](const Movie& movie)noexcept {
		if (movie.getName().find(name) != string::npos) {
			return true;
		}
		else { return false;
		}
	 });
}
