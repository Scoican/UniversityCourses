#include "TestRepository.h"
#include "Repository.h"
#include <assert.h>
void testRepository() {
	Repository produse = Repository("Text.txt");
	Produs p1 = Produs(1, "1", "1", 1.1);
	assert(produse.getAll().size() == 0);
	assert(produse.find(p1) == false);
	produse.store(p1);
	assert(produse.getAll().size() == 1);
	assert(produse.find(p1) == true);
}