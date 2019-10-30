#include "TestDomain.h"
#include "TestRepository.h"
#include "TestService.h"
#include "TestValidator.h"
#include "TestMyList.h"
#include "TestWishlist.h"
#include "TestFileRepository.h"
#include "Console.h"
#include "WishlistService.h"
#include "MovieFileRepository.h"
#include "MyList.h"
#include <iostream>

int main() {
	callTestsDomain();
	callTestsMyList();
	callTestsRepository();
	callTestsService();
	callTestsValidator();
	callTestsWishlist();
	callTestsFileRepository();
	MovieRepository repository;
	MovieFileRepository fileRepo{ "file.txt","file.txt" };
	MovieValidator validator;
	MovieService service{ fileRepo,validator };
	WishlistService wishlist{ fileRepo };
	Console console{ service,wishlist };
	console.run();
}