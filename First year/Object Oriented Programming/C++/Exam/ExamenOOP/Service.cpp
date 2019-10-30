#include "Service.h"

void Service::addProdus(int id, string name, string type, double price)
{
	if (this->findProdus(id) == true) {
		throw exception("Product already added");
	}
	else {
		Produs p = Produs(id, name, type, price);
		validator.checkProduct(p);
		repo.store(p);
	}
}

bool Service::findProdus(int id)
{
	return repo.find(Produs(id, " ", " ", 0.0));
}

const vector<Produs>& Service::getAll() const noexcept {
	return repo.getAll();
}

vector<Produs> Service::sortProducts()
{
	vector<Produs> elements = repo.getAll();
	sort(elements.begin(), elements.end(), [](const Produs& p1, const Produs& p2) {
		return p1.getPrice() > p2.getPrice();
	});
	return elements;
}

Service::~Service()
{
}