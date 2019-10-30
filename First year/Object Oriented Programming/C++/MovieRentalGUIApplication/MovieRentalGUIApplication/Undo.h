#pragma once
#include "Movie.h"
#include "MovieRepository.h"

class IUndo {
public:
	virtual void doUndo() = 0;
};

class UndoAdd : public IUndo {
	Movie movie;
	MovieRepository& repo;
public:
	UndoAdd(MovieRepository& r, const  Movie& movie) :repo{ r }, movie{ movie } {}
	void doUndo() override {
		repo.remove(movie);
	}
};

class UndoRemove : public IUndo {
	Movie movie;
	MovieRepository& repo;
public:
	UndoRemove(MovieRepository& r, const  Movie& movie) :repo{ r }, movie{ movie } {}
	void doUndo() override {
		repo.store(movie);
	}
};

class UndoUpdate : public IUndo {
	Movie movie;
	MovieRepository& repo;
public:
	UndoUpdate(MovieRepository& r, const  Movie& movie) :repo{ r }, movie{ movie } {}
	void doUndo() override {
		repo.update(movie);
	}
};
