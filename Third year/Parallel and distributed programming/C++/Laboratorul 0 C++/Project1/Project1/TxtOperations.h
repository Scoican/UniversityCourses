#pragma once
#include "FileOperations.h"
#include <fstream>
#include <iostream>
#include "BigNumber.h"

class TxtOperations : public FileOperations
{
public:
	/**
	 * Method that generates random numbers and writes them in a given file
	 * @param fileName - File in which the numbers will be writen
	 * @param size - How many numbers to be writen
	 * @param min - Minimum size of number length
	 * @param max - Maximum size of number length
	 */
	void fileGenerator(string fileName, int size, int min, int max);

	/**
	 * Method that compares two given files and checks if the data inside it is the same
	 * @param fileName1 - Given file name
	 * @param fileName2 - Given file name
	 * @return  true    - If the containing data in both files are the same
	 *          false   - Otherwise
	 */
	bool fileComparator(string fileName1, string fileName2);

	/**
	 * Method that reads all the numbers in a given file
	 * @param fileName1 - Given file name
	 * @return the read number as a BigNumber
	 */
	vector<BigNumber> readNumbersFromFile(string fileName);

	/**
	 * Method that writes a given number in a given file
	 * @param fileName1 - Given file name
	 * @param number - Given file number
	 */
	void writeNumberToFile(BigNumber number, string fileName);

	/*
	* Constructor
	*/
	TxtOperations();

	/*
	* Destructor
	*/
	~TxtOperations();
};
