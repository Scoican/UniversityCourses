#pragma once
#include <string>
using namespace std;
class Produs
{
private:
	int id;
	string name;
	string type;
	double price;
public:
	/*
	Default Constructor
	*/
	Produs();
	/*
	Constructor with paramters
	Input:id-int,name,type-string,price-double
	*/
	Produs(int id, string name, string type, double price) :id{ id }, name{ name }, type{ type }, price{ price }{};
	/*
	CopyConstructor
	*/
	Produs(const Produs& ot) {
		this->id = ot.id;
		this->name = ot.name;
		this->price = ot.price;
		this->type = ot.type;
	}
	/*
	Getters
	*/
	int getId() const;
	string getName() const;
	string getType() const;
	double getPrice() const;

	/*
	Setters
	*/
	void setId(int id);
	void setName(string name);
	void setType(string type);
	void setPrice(double price);

	/*
	Operator overloading
	*/
	bool operator==(const Produs& ot) const;
	bool operator!=(const Produs& ot) const;

	/*
	Destructor
	*/
	~Produs();
};
