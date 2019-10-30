#pragma once
#include "Repository.h"
#include "Validator.h"
using namespace std;
class Service
{
private:
	Repository& repo;
	Validator& validator;
public:
	/*
	Parameter constructor
	Input:repo-Repository of Products,validator-Product Validator
	*/
	Service(Repository& repo,Validator& validator) :repo{ repo }, validator { validator }{};
	/*
	Method for adding a product in the system
	Input:id-int,name,type-string, price-double
	*/
	void addProdus(int id, string name, string type, double price);
	/*
	Method for finding a product in the system
	Input:id-int
	Output:true if product is found,false,otherwise
	*/
	bool findProdus(int id);
	/*
	Method that sorts all products by price
	*/
	vector<Produs> sortProducts();
	/*
	Method that returns all the products in the system
	Output:products-Vector
	*/
	const vector<Produs>& getAll() const noexcept;
	/*
	Destructor
	*/
	~Service();
};
