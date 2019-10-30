#include "TestRepository.h"

void callTestsRepository() {
	testStore();
	testRemove();
	testUpdate();
	testFind();
	testGetAll();
}
void testStore() {
	MovieRepository movies;
	assert(movies.getAll().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	movies.store(movie);
	assert(movies.getAll().size() == 1);
	assert(movies.getAll()[0] == movie);
	try {
		movies.store(movie);
	}
	catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testRemove() {
	MovieRepository movies;
	assert(movies.getAll().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	movies.store(movie);
	assert(movies.getAll().size() == 1);
	movies.remove(movie);
	assert(movies.getAll().size() == 0);
	try {
		movies.remove(movie);
	}
	catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testUpdate() {
	MovieRepository movies;
	assert(movies.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie2 = Movie("The Avengers", "Comedy", 2010, "Hulk");
	movies.store(movie1);
	assert(movies.getAll()[0].getGenre() == "Action");
	assert(movies.getAll()[0].getLeadingActor() == "Groot");
	assert(movies.getAll().size() == 1);
	movies.update(movie2);
	assert(movies.getAll()[0].getGenre() == "Comedy");
	assert(movies.getAll()[0].getLeadingActor() == "Hulk");
	try {
		movies.update(Movie("The avenger", "Action", 2011, "Groot"));
	}
	catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testFind() {
	MovieRepository movies;
	assert(movies.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie2 = Movie("The Avenger", "Action", 2011, "Groot");
	movies.store(movie1);
	assert(movies.find("The Avengers", 2010) == movie1);
	try {
		Movie foundMovie = movies.find("The Avenger", 2011);
	}
	catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testGetAll() {
	MovieRepository movies;
	assert(movies.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	movies.store(movie1);
	assert(movies.getAll().size() == 1);
	assert(movies.getAll()[0] == movie1);
}