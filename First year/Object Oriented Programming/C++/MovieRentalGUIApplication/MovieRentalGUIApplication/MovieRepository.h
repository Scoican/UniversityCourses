#pragma once
#include "Movie.h"
#include "MovieRepositoryException.h"
#include "MyList.h"
#include <vector>
#include <algorithm>

using namespace std;

class MovieRepository
{
protected:
	vector<Movie> movies;
private:
	bool exist(const Movie& movie);
public:
	//Default construtor
	MovieRepository() = default;
	//Copy constructor
	MovieRepository(const MovieRepository& movies) = delete;

	//Function that adds a movie in the vector
	//Input:Movie
	virtual void store(const Movie& movie);
	//Function that deletes a movie in the vector
	//Input:Movie
	virtual void remove(const Movie& movie);
	//Function that updates a movie in the vector
	//Input:Movie
	virtual void update(const Movie& movie);
	//Function that finds a movie in the vector
	//Input:name-String,launchYear-int
	//Output:Movie
	Movie find(const string& name, const int launchYear) const;
	//Function that retruns all the movies in the vector
	//Output:Movies-vector
	const vector<Movie>& getAll() const noexcept;
};
