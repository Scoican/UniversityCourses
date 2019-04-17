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

const vector<Movie> MovieService::getAll() const noexcept
{
	return movieRepository.getAll();
}

vector<Movie> MovieService::generalSort(ComparingFunction comparingFunction) const
{
	vector<Movie> vector{ movieRepository.getAll() };	
	sort(vector.begin(), vector.end(), comparingFunction);
	return vector;
}

vector<Movie> MovieService::sortByName() const
{
	return generalSort(compareByName);
}

vector<Movie> MovieService::sortByGenre() const
{
	return generalSort(compareByGenre);
}

vector<Movie> MovieService::sortByLaunchYear() const
{
	return generalSort(compareByLaunchYear);
}

vector<Movie> MovieService::sortByLeadingActor() const
{
	return generalSort(compareByLeadingActor);
}

vector<Movie> MovieService::generalfilter(function<bool(const Movie&)> fct) const
{
	vector<Movie> filtered;
	for (const auto& movie : movieRepository.getAll()) {
		if (fct(movie)) {
			filtered.push_back(movie);
		}
	}
	return filtered;
}

vector<Movie> MovieService::filterByYear(int year) const
{
	return generalfilter([year](const Movie& movie)noexcept {
		return movie.getLaunchYear() >= year; });
}

vector<Movie> MovieService::filterByName(string& name) const
{
	return generalfilter([name](const Movie& movie)noexcept {
		if (movie.getName().find(name) != string::npos) {
			return true;
		}
		else { return false;
		}
	 });
}
