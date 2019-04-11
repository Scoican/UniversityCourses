#pragma once
#include "MovieRepository.h"
#include "Validator.h"
#include <functional>


typedef bool(*ComparingFunction)(const Movie&, const Movie&);

//Service class
class MovieService
{
private:
	MovieRepository& movieRepository;
	MovieValidator& validator;
public:
	//Copy contructor
	MovieService(const MovieService& ot) = delete;
	//Contructor
	MovieService(MovieRepository& movieRepository, MovieValidator& validator)noexcept :movieRepository{ movieRepository }, validator{ validator } {}
	//Function that adds a movie in the repository
	//Input:name,genre,leadingActor-String,launchYear-int
	void addMovie(const string& name, const string& genre, const int launchYear, const string& leadingActor) ;
	//Function that removes a movie in the repository
	//Input:name-String,launchYear-int
	void removeMovie(const string& name, const int launchYear);
	//Function that updates a movie in the repository
	//Input:name,genre,leadingActor-String,launchYear-int
	void updateMovie(const string& name, const string& genre, const int launchYear, const string& leadingActor);
	//Function that finds a movie in the repository
	//Input:name-String,launchYear-int
	//Output:The found Movie
	Movie findMovie(const string& name, const int launchYear) const;
	//Function that returns all the movies
	//Output:movies:Vector
	const Vector<Movie> getAll() const noexcept;

	//Sorting methods
	Vector<Movie> generalSort(ComparingFunction comparingFunction)const;
	Vector<Movie> sortByName() const;
	Vector<Movie> sortByGenre() const;
	Vector<Movie> sortByLaunchYear() const;
	Vector<Movie> sortByLeadingActor() const;

	//Filter methods
	Vector<Movie> generalfilter(function<bool(const Movie&)> fct) const;
	Vector<Movie> filterByYear(int year) const;
	Vector<Movie> filterByName(string& name) const;
};

