#pragma once
#include <vector>
#include "MovieRepository.h"
#include "Movie.h"
#include "Export.h"
#include "Observabile.h"
class WishlistService :public Obesevabile {
protected:
	vector <Movie> wishlist;
	MovieRepository& repo;

public:
	//Constructor
	WishlistService(MovieRepository &r) :repo{ r } {};
	//Method that searches a movie in the current wishlist
	//Output:True if movie in list
	//False,otherwise
	const bool searchInWishlist(const Movie& m);
	//Method that adds a new movie to the wishlist
	void addWishlist(const string & name, const int launchYear);
	//Method that empties the wishlist
	void deleteWishlist() noexcept;
	//Method that populates the list with nr random Movies
	void populateWishlist(const unsigned& nr);
	//Method that returns all the movies in the wishlist
	vector<Movie> getWishlist();
	//Decosntructor
	~WishlistService() = default;
};

class WishlistException {
public:
	string error;
	WishlistException(string err) : error{ err } {}
	friend ostream& operator<<(ostream& out, const WishlistException& ex);
};

ostream& operator<<(ostream& out, const WishlistException& ex);