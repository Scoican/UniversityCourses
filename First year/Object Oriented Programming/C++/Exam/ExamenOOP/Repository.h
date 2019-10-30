#pragma once
#include <vector>
#include "Produs.h"
#include <iostream>
#include <algorithm>
#include <fstream>
using namespace std;
class Repository
{
private:
	vector<Produs> elements;
	string readFile;
public:
	/*
	Method that stores a Product in the repository
	Input:el-Product
	*/
	void store(const Produs& el);
	/*
	Method that checks if a Product already exists in the Repository
	Input:el-Product
	Output:True if the element exists,false otherwise
	*/
	bool find(const Produs& el);
	/*
	Methot that returns all the elements in the repository
	Output:elements-vector<Produs>
	*/
	const vector<Produs>& getAll() const noexcept;
	/*
	Method for loading data from the file
	*/
	void loadData();
	/*
	Method for saving data in the file
	*/
	void saveData();
	/*
	Parameter constructor
	Input:readFile-string
	*/
	Repository(string readFile) :readFile{ readFile }{
		this->loadData();
	};
	/*
	Destructor
	*/
	~Repository() {
		saveData();
	}
};
