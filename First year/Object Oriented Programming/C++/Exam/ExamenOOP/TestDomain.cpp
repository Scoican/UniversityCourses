#include "TestDomain.h"
#include <assert.h>
void testProduct() {
	Produs p = Produs(1, "1", "1", 1.1);
	assert(p.getId() == 1);
	assert(p.getName() == "1");
	assert(p.getType() == "1");
	assert(p.getPrice() == 1.1);

	p.setId(2);
	p.setName("2");
	p.setType("2");
	p.setPrice(2.2);

	assert(p.getId() == 2);
	assert(p.getName() == "2");
	assert(p.getType() == "2");
	assert(p.getPrice() == 2.2);

	Produs p2 = Produs(2, "1", "1", 1.1);
	assert(p == p2);
	p.setId(1);
	assert(p != p2);
}