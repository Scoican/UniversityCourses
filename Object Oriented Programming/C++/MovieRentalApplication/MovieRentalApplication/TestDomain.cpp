#include "TestDomain.h"

void callTestsDomain()
{
	testGetters();
	testSetters();
	testOverloads();
	testComparators();
}

void testGetters()
{
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	assert(movie.getName() == "The Avengers");
	assert(movie.getGenre() == "Action");
	assert(movie.getLaunchYear() == 2010);
	assert(movie.getLeadingActor() == "Groot");
}

void testSetters() {
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	movie.setName("South Park");
	movie.setGenre("Comedy");
	movie.setLeadingActor("Timmy");
	movie.setLaunchYear(1999);
	assert(movie.getName() == "South Park");
	assert(movie.getGenre() == "Comedy");
	assert(movie.getLaunchYear() == 1999);
	assert(movie.getLeadingActor() == "Timmy");
}

void testOverloads() {
	Movie movie1 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie2 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie3 = Movie("The Avengers", "Action", 2010, "Timmy");
	assert(movie1 == movie2);
	assert(movie2 == movie1);
	assert(movie1 != movie3);
	
}

void testComparators() {
	Movie movie2 = Movie("The Avengers", "Action", 2010, "Groot");
	Movie movie3 = Movie("The Avenger", "Comedy", 2011, "Timmy");
	assert(compareByName(movie2, movie3) == false);
	assert(compareByGenre(movie2, movie3) == true);
	assert(compareByLaunchYear(movie2, movie3) == true);
	assert(compareByLeadingActor(movie2, movie3) == true);
}