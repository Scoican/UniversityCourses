#pragma once
#include "MovieRepository.h"
#include <functional>

typedef bool(*ComparingFunction)(const Movie&, const Movie&);

class MovieService
{
private:
	MovieRepository& movieRepository;
public:
	MovieService(const MovieService& ot) = delete;
	MovieService(MovieRepository& movieRepository)noexcept :movieRepository{ movieRepository } {}
	void addMovie(const string& name, const string& genre, const int launchYear, const string& leadingActor) ;
	void removeMovie(const string& name, const int launchYear);
	void updateMovie(const string& name, const string& genre, const int launchYear, const string& leadingActor);
	Movie findMovie(const string& name, const int launchYear) const;
	const vector<Movie> getAll() const noexcept;

	vector<Movie> generalSort(ComparingFunction comparingFunction)const;
	vector<Movie> sortByName() const;
	vector<Movie> sortByGenre() const;
	vector<Movie> sortByLaunchYear() const;
	vector<Movie> sortByLeadingActor() const;


	vector<Movie> generalfilter(function<bool(const Movie&)> fct) const;
	vector<Movie> filterByYear(int year) const;
	vector<Movie> filterByName(string& name) const;
};

