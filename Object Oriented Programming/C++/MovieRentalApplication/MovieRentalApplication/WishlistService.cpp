#include "WishlistService.h"


const bool WishlistService::searchInWishlist(const Movie& m) {
	auto it = find_if(wishlist.begin(), wishlist.end(), [&m](const Movie& el) {return el == m; });
	if (it == wishlist.end())
		return false;
	return true;
}

void WishlistService::addWishlist(const string & name, const int launchYear) {
	Movie newMovie = repo.find(name, launchYear);
	if (searchInWishlist(newMovie) == false) {
		wishlist.push_back(newMovie);
	}
	else {
		throw WishlistException("Movie already in the wishlist!");
	}

}

//goleste lista de lucru
void WishlistService::deleteWishlist() noexcept {
	wishlist.clear();
}

//adauga masini random in lista de lucru
void WishlistService::populateWishlist(const unsigned& nr)
{
	deleteWishlist();
	if (repo.getAll().size() - wishlist.size() < nr) throw WishlistException("Not enough movies in the store!");
	if (nr == 0) throw WishlistException("Invalid number of movies!");
	vector<Movie> list = repo.getAll();
	unsigned i = 0;
	while (i < nr) {
		Movie m = list.at(rand() % list.size());
		if (searchInWishlist(m) == false) {
			wishlist.push_back(m);
			i++;
		}
		else {
			continue;
		}
	}
}

vector<Movie> WishlistService::getWishlist() {
	return wishlist;
}

ostream& operator<<(ostream& out, const WishlistException& ex) {
	out << ex.error;
	return out;
}