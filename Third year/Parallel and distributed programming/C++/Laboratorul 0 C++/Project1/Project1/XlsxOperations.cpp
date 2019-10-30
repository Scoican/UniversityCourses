#include "XlsxOperations.h"
#include <fstream>
#include <iostream>

void XlsxOperations::fileGenerator(string fileName, int size, int min, int max)
{
	ofstream myfile(fileName + ".csv", std::ifstream::out | std::ifstream::trunc);
	if (myfile.is_open())
	{
		for (int i = 0; i < size; i++) {
			myfile << randomNumberGenerator(randomGenerator(min, max)) << "," << endl;
		}
		myfile.close();
	}
	else cout << "Unable to open file";
}

bool XlsxOperations::fileComparator(string fileName1, string fileName2)
{
	string line1;
	string line2;
	ifstream file1(fileName1 + ".csv");
	ifstream file2(fileName2 + ".csv");
	if (file1.is_open() && file2.is_open()) {
		{
			while (getline(file1, line1) && getline(file2, line2))
			{
				if (line1.compare(line2) != 0) {
					return false;
				}
			}
			if (getline(file1, line1) && !getline(file2, line2)) {
				return false;
			}

			if (!getline(file1, line1) && getline(file2, line2)) {
				return false;
			}
			file1.close();
			file2.close();
		}
	}
	return true;
}

XlsxOperations::XlsxOperations()
{
}

XlsxOperations::~XlsxOperations()
{
}