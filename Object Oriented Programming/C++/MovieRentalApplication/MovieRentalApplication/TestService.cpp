#include "TestService.h"


void callTestsService() {
	testAddMovie();
	testRemoveMovie();
	testUpdateMovie();
	testFindMovie();
	testGetAllService();
	testFilters();
	testSorting();
}
void testAddMovie() {
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	assert(service.getAll().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	service.addMovie("The Avengers", "Action", 2010, "Groot");
	assert(service.getAll().size() == 1);
	assert(service.getAll()[0] == movie);
	try {
		service.addMovie("The Avengers", "Action", 2010, "Groot");
	}	catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testRemoveMovie() {
	MovieRepository movies;	
	MovieValidator validator;
	MovieService service{ movies,validator };
	assert(service.getAll().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	service.addMovie("The Avengers", "Action", 2010, "Groot");
	assert(service.getAll().size() == 1);
	service.removeMovie("The Avengers", 2010);
	assert(service.getAll().size() == 0);
	try {
		service.removeMovie("The Avengers",2010);
	}catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testUpdateMovie() {
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	assert(service.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie2 = Movie("The Avengers", "Comedy", 2010, "Hulk");
	service.addMovie("The Avengers", "Action", 2010, "Groot");
	assert(service.getAll()[0].getGenre() == "Action");
	assert(service.getAll()[0].getLeadingActor() == "Groot");
	assert(service.getAll().size() == 1);
	service.updateMovie("The Avengers","Comedy",2010,"Hulk");
	assert(service.getAll()[0].getGenre() == "Comedy");
	assert(service.getAll()[0].getLeadingActor() == "Hulk");
	try {
		service.updateMovie("The avenger", "Action", 2011, "Groot");
	}catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testFindMovie() {
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	assert(service.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	service.addMovie("The Avengers", "Action", 2010, "Groot");
	assert(service.findMovie("The Avengers", 2010) == movie1);
	try {
		Movie foundMovie = service.findMovie("The Avenger", 2011);
	}catch (MovieRepositoryException&) {
		assert(true);
	}
}
void testGetAllService() {
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	assert(service.getAll().size() == 0);
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	service.addMovie("The Avengers", "Action", 2010, "Groot");
	assert(movies.getAll().size() == 1);
	assert(movies.getAll()[0] == movie1);
}

void testFilters()
{
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	service.addMovie("Movie1", "Action", 2011, "Groot");
	service.addMovie("Movie2", "Action", 2012, "Groot");
	service.addMovie("Movie3", "Action", 2003, "Groot");
	service.addMovie("ASd4", "Action", 2004, "Groot");
	string name = "Movie";
	Vector<Movie> vector1 = service.filterByYear(2010);
	Vector<Movie> vector2 = service.filterByName(name);
	assert(vector1.size() == 2);
	assert(vector2.size() == 3);
}

void testSorting()
{
	MovieRepository movies;
	MovieValidator validator;
	MovieService service{ movies,validator };
	service.addMovie("Movie1", "ActionD", 2011, "GrootD");
	service.addMovie("Movie3", "ActionC", 2012, "GrootC");
	service.addMovie("Movie2", "ActionB", 2003, "GrootA");
	service.addMovie("Movie4", "ActionA", 2004, "GrootB");
	Vector<Movie> vector1 = service.sortByLaunchYear();
	Vector<Movie> vector2 = service.sortByName();
	Vector<Movie> vector3 = service.sortByGenre();
	Vector<Movie> vector4 = service.sortByLeadingActor();
	assert(vector1[0].getName() == "Movie2");
	assert(vector2[0].getName() == "Movie1");
	assert(vector3[0].getName() == "Movie4");
	assert(vector4[0].getName() == "Movie2");

}
