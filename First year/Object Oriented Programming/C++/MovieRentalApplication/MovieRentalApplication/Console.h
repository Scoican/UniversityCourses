#pragma once
#include "MovieService.h"
#include "WishlistService.h"
#include <iostream>
using namespace std;

class Console
{
private:
	MovieService& service;
	WishlistService& wishlist;
	//Method for adding a Movie
	void addUi();
	//Method for removing a Movie
	void removeUi();
	//Method for updateing a Movie
	void updateUi();
	//Method for finding a Movie
	void findUi();
	//Method for printing all Movies
	void getAllUi();
	//Filter methods
	void filterByYear();
	void filterByName();
	//Sorting methods
	void sortByName();
	void sortByGenre();
	void sortByLaunchYear();
	void sortByLeadingActor();
	void addWishlistUi();
	void deleteWishlistUi();
	void populateWishlistUi();
	void viewWishlistUi();
	void printMenu();
	void testingData();
public:
	Console(MovieService& service, WishlistService& wishlist) noexcept :service{ service }, wishlist{ wishlist } {}
	Console(const Console& ot) = delete;

	void run();
};
