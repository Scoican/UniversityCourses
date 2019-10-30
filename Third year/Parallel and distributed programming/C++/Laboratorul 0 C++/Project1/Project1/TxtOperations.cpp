#include "TxtOperations.h"
#include <fstream>
#include <iostream>

void TxtOperations::fileGenerator(string fileName, int size, int min, int max)
{
	ofstream myfile(fileName, std::ifstream::out | std::ifstream::trunc);
	if (myfile.is_open())
	{
		for (int i = 0; i < size; i++) {
			myfile << randomNumberGenerator(randomGenerator(min, max)) << endl;
		}
		myfile.close();
	}
	else cout << "Unable to open file";
}

bool TxtOperations::fileComparator(string fileName1, string fileName2)
{
	string line1;
	string line2;
	ifstream file1(fileName1);
	ifstream file2(fileName2);
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

vector<BigNumber> TxtOperations::readNumbersFromFile(string fileName)
{
	vector<BigNumber> bigNumbers = vector<BigNumber>();
	ifstream file(fileName);

	if (file.is_open()) {
		string line;
		for (string line; getline(file, line);) {
			vector<int> arr = vector<int>();
			int digit;
			for (unsigned i = 0; i < line.size(); i++) {
				digit = (int)line[i] - 48;
				arr.push_back(digit);
			}
			bigNumbers.push_back(BigNumber(arr));
			bigNumbers.at(bigNumbers.size() - 1).reverseNumber();
		}
	}
	else {
		cout << "Unable to open file";
	}
	return bigNumbers;
}

void TxtOperations::writeNumberToFile(BigNumber number, string fileName) {
	number.reverseNumber();
	ofstream outfile(fileName);
	for (int i = 0; i < number.getSize(); i++) {
		outfile << number.getDigit(i);
	}
}

TxtOperations::TxtOperations()
{
}

TxtOperations::~TxtOperations()
{
}