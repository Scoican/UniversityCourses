#pragma once
#include <vector>
#include "MovieRepository.h"
#include "Movie.h"
class WishlistService{
protected:
	vector <Movie> wishlist;
	MovieRepository& repo;

public:
	WishlistService(MovieRepository &r) :repo{ r } {};
	const bool searchInWishlist(const Movie& m);
	void addWishlist(const string & name, const int launchYear);
	void deleteWishlist() noexcept;
	void populateWishlist(const unsigned& nr);
	vector<Movie> getWishlist();
	~WishlistService() = default;
};

class WishlistException {
	string error;
public:
	WishlistException(string err) : error{ err } {}
	friend ostream& operator<<(ostream& out, const WishlistException& ex);
};

ostream& operator<<(ostream& out, const WishlistException& ex);