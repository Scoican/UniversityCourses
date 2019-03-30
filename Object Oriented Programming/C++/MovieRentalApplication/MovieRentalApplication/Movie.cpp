#include "Movie.h"



Movie::Movie(string name, string genre, int launchYear, string leadingActor)
{
	this->name = name;
	this->genre = genre;
	this->launchYear = launchYear;
	this->leadingActor = leadingActor;
}

string Movie::getName() const
{
	return this->name;
}

string Movie::getGenre() const
{
	return this->genre;
}

int Movie::getLaunchYear() const noexcept
{
	return this->launchYear;
}

string Movie::getLeadingActor() const
{
	return this->leadingActor;
}

void Movie::setName(string name)
{
	this->name = name;
}

void Movie::setGenre(string genre)
{
	this->genre = genre;
}

void Movie::setLaunchYear(int launchYear)
{
	this->launchYear = launchYear;
}

void Movie::setLeadingActor(string leadingActor)
{
	this->leadingActor = leadingActor;
}

bool Movie::operator==(const Movie & other) const
{
	return(this->getName() == other.getName() &&
		this->getGenre() == other.getGenre() &&
		this->getLaunchYear() == other.getLaunchYear() &&
		this->getLeadingActor() == other.getLeadingActor());

}

bool Movie::operator!=(const Movie & other) const
{
	return !(*this == other);

}

bool compareByName(const Movie & m1, const Movie & m2)
{
	return m1.getName() < m2.getName();
}

bool compareByGenre(const Movie & m1, const Movie & m2)
{
	return m1.getGenre() < m2.getGenre();
}

bool compareByLaunchYear(const Movie & m1, const Movie & m2)
{
	return m1.getLaunchYear() < m2.getLaunchYear();
}

bool compareByLeadingActor(const Movie & m1, const Movie & m2)
{
	return m1.getLeadingActor() < m2.getLeadingActor();
}

ostream &operator<<(ostream &output, const Movie &movie) {
	output << movie.getName() << "<--->" << movie.getGenre() << "<--->" << movie.getLaunchYear() << "<--->" << movie.getLeadingActor() << endl;
	return output;
}