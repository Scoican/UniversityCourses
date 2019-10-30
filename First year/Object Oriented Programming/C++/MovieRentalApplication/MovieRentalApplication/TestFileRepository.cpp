#include "TestFileRepository.h"

void callTestsFileRepository() {
	testFileStore();
	testFileRemove();
	testFileUpdate();
}

void testFileStore() {
	MovieFileRepository movies("testFile.txt", "testFile.txt");
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
	movies.remove(movie);
}
void testFileRemove() {
	MovieFileRepository movies("testFile.txt", "testFile.txt");
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
void testFileUpdate() {
	MovieFileRepository movies("testFile.txt", "testFile.txt");
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
	movies.remove(movie2);
}