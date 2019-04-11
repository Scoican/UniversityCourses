#pragma once
#include "Movie.h"
#include "MovieRepository.h"
#include "MovieRepositoryException.h"
#include <assert.h>

//Tests for Repository
void callTestsRepository();
void testStore();
void testRemove();
void testUpdate();
void testFind();
void testGetAll();