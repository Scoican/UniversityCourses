#pragma once
#include "MovieRepository.h"
class MovieFileRepository : public MovieRepository
{
	string file_in, file_out;
public:
	//Override method that adds a movie in the vector and updates the file
	//Input:Movie
	void store(const Movie& movie) override;
	//Override method that deletes a movie from the vector and updates the file
	//Input:Movie
	void remove(const Movie& movie) override;
	//Override method that updates a movie in the vector and updates the file
	//Input:Movie
	void update(const Movie& movie) override;
	/*
	Method that reads the information from a file and loads it in the repository
	*/
	void loadData();
	/*
	Method that rewrites the information in a file
	*/
	void saveData();
	MovieFileRepository(string fisier_in, string fisier_out) :file_in{ fisier_in }, file_out{ fisier_out } {
		loadData();
	}
	~MovieFileRepository() {
		saveData();
	}
};
