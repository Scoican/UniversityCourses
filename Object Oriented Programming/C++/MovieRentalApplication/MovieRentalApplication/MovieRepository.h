#pragma once
#include "Movie.h"
#include "MovieRepositoryException.h"

#include <vector>
#include <algorithm>

using namespace std;

class MovieRepository
{
private:
	vector<Movie> movies;
	bool exist(const Movie& movie);
public:
	MovieRepository() = default;
	MovieRepository(const MovieRepository& movies) = delete;

	void store(const Movie& movie);
	void remove(const Movie& movie);
	void update(const Movie& movie);
	Movie find(const string& name, const int launchYear) const;
	const vector<Movie>& getAll() const noexcept;

};



