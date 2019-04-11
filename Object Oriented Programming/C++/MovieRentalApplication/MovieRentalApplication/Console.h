#pragma once
#include "MovieService.h"
#include <iostream>
using namespace std;

class Console
{
private:
	MovieService& service;
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
	void printMenu();
	void testingData();
public:
	Console(MovieService& service) noexcept :service{ service } {}
	Console(const Console& ot) = delete;

	void run();
};

