#pragma once
#include <string>
#include <iostream>
using namespace std;

class Movie
{
private:
	string name;
	string genre;
	int launchYear;
	string leadingActor;

public:
	Movie(string name, string genre, int launchYear, string leadingActor);
	//Movie(string name, string genre, int launchYear, string leadingActor):this.name{name},this.genre{genre},this.launchYear{launchYear},this.leadingActor{leadingActor}{}
	Movie(const Movie& other);
	//getters
	string getName() const;
	string getGenre() const;
	int getLaunchYear() const noexcept;
	string getLeadingActor() const;

	//setters
	void setName(string name);
	void setGenre(string genre);
	void setLaunchYear(int launchYear);
	void setLeadingActor(string leadingActor);

	//overload
	bool operator==(const Movie& other) const;
	bool operator!=(const Movie& other) const;
	friend ostream &operator<<(ostream &output, const Movie &movie);
};

bool compareByName(const Movie& m1, const Movie& m2);
bool compareByGenre(const Movie& m1, const Movie& m2);
bool compareByLaunchYear(const Movie& m1, const Movie& m2);
bool compareByLeadingActor(const Movie& m1, const Movie& m2);