#include <QtWidgets/QApplication>
#include "Repository.h"
#include "Service.h"
#include "Produs.h"
#include "Interface.h"
#include "Validator.h"
#include "TestDomain.h"
#include "TestRepository.h"
#include "TestValidator.h"
#include "TestService.h"
int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	Repository repo{ "Products.txt" };
	Validator validator;
	Service serv{ repo,validator };
	//testProduct();
	//testRepository();
	//testService();
	//testValidator();
	Interface gui{ serv };
	gui.show();
	return 0;
}