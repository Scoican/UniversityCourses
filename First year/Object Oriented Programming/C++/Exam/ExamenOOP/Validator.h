#pragma once
#include "Produs.h"
class Validator
{
public:
	/*
	Constructor
	*/
	Validator();
	/*
	Method for checking if all the data in a product is valid
	*/
	void checkProduct(Produs p);
	/*
	destructor
	*/
	~Validator();
};

