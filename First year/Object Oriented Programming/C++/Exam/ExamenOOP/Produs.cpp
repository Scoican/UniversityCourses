#include "Produs.h"

Produs::Produs()
{
}

Produs::~Produs()
{
}

int Produs::getId() const
{
	return this->id;
}

string Produs::getName() const
{
	return this->name;
}

string Produs::getType() const
{
	return this->type;
}

double Produs::getPrice() const
{
	return this->price;
}

void Produs::setId(int id)
{
	this->id = id;
}

void Produs::setName(string name)
{
	this->name = name;
}

void Produs::setType(string type)
{
	this->type = type;
}

void Produs::setPrice(double price)
{
	this->price = price;
}

bool Produs::operator==(const Produs & ot) const
{
	return this->id == ot.id;
}

bool Produs::operator!=(const Produs & ot) const
{
	return !(*this == ot);
}