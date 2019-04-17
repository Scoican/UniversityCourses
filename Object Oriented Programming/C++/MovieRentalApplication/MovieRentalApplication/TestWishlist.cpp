#include "TestWishlist.h"


void callTestsWishlist() {
	 testAddWishlist();
	 testDeleteWishlist();
	 testPopulateWishlist();
}
void testAddWishlist() {
	MovieRepository movies;
	WishlistService service{ movies};
	assert(service.getWishlist().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	movies.store(movie);
	service.addWishlist("The Avengers", 2010);
	assert(service.getWishlist().size() == 1);
	assert(service.getWishlist()[0] == movie);
	try {
		service.addWishlist("The Avengers", 2010);
	}catch (WishlistException&) {
		assert(true);
	}
}
void testDeleteWishlist() {
	MovieRepository movies;
	WishlistService service{ movies };
	assert(service.getWishlist().size() == 0);
	Movie movie = Movie("The Avengers", "Action", 2010, "Groot");
	movies.store(movie);
	service.addWishlist("The Avengers", 2010);
	assert(service.getWishlist().size() == 1);
	service.deleteWishlist();
	assert(service.getWishlist().size() == 0);
}
void testPopulateWishlist() {
	MovieRepository movies;
	WishlistService service{ movies };
	assert(service.getWishlist().size() == 0);
	Movie movie1 = Movie("The Avengers1", "Action", 2010, "Groot");
	Movie movie2 = Movie("The Avengers2", "Action", 2010, "Groot");
	Movie movie3 = Movie("The Avengers3", "Action", 2010, "Groot");
	Movie movie4 = Movie("The Avengers4", "Action", 2010, "Groot");
	Movie movie5 = Movie("The Avengers5", "Action", 2010, "Groot");
	movies.store(movie1);
	movies.store(movie2);
	movies.store(movie3);
	movies.store(movie4);
	movies.store(movie5);
	service.populateWishlist(3);
	assert(service.getWishlist().size() == 3);
	service.populateWishlist(4);
	assert(service.getWishlist().size() == 4);
	try {
		service.populateWishlist(0);
	}catch (WishlistException&) {
		assert(true);
	}
	try {
		service.populateWishlist(100);
	}catch (WishlistException&) {
		assert(true);
	}

}
