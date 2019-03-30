#pragma once
#include "MovieService.h"
#include <iostream>
using namespace std;

class Console
{
private:
	MovieService& service;
	void addUi();
	void removeUi();
	void updateUi();
	void findUi();
	void getAllUi();
	void filterByYear();
	void filterByName();
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

