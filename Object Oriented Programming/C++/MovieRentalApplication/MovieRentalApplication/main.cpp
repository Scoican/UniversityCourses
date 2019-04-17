#include "TestDomain.h"
#include "TestRepository.h"
#include "TestService.h"
#include "TestValidator.h"
#include "TestMyList.h"
#include "TestWishlist.h"
#include "Console.h"
#include "WishlistService.h"
#include "MyList.h"
#include <iostream>



int main() {
	callTestsDomain();
	callTestsMyList();
	callTestsRepository();
	callTestsService();
	callTestsValidator();
	callTestsWishlist();
	MovieRepository repository;
	MovieValidator validator;
	MovieService service{ repository,validator };
	WishlistService wishlist{ repository };
	Console console{ service,wishlist };
	console.run();
}