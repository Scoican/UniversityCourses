#pragma once
#include <vector>
#include "Observer.h"
using namespace std;
class Observable {
public:
	vector<Observer*> obsss;
	/*
	Notifiy an observer
	*/
	void notify(Observer* obs) {
		obs->update();
	}
	/*
	Notifiy all obsevers
	*/
	void notifyObsss() {
		for (auto o : obsss)
			notify(o);
	}
	/*
	Add a new observer
	*/
	void addObs(Observer *obs) {
		obsss.push_back(obs);
	}
};

