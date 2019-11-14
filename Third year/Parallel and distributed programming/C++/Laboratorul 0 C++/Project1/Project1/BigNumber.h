#pragma once
#include <vector>
#include <iostream>
#include <mpi.h>
using namespace std;

class BigNumber
{
private:
	/*
	* Values of the number
	*/
	vector<int> digits;

	/**
	 * Method that calculates the sum of a given portion of a big number
	 * @param start - Start position in the vectors
	 * @param end - End position in the vectors
	 * @param no_thread - Current thread number
	 * @param largeNumber - The number with more digits
	 * @param smallNumber - The number with less digits
	 * @param carries - Vector that contains the carry values of the sum
	 */
	void compute(int start, int end, int no_thread, BigNumber& largeNumber, BigNumber& smallNumber, vector<int>& carries);

	/**
	 * Method that calculates the sum of a given portion of two given numbers
	 * @param start - Start position in the vectors
	 * @param end - End position in the vectors
	 * @param no_thread - Current thread number
	 * @param largeNumber - The number with more digits
	 * @param smallNumber - The number with less digits
	 * @param carries - Vector that contains the carry values of the sum
	 */
	void computeAdd(int start, int end, int no_thread, BigNumber& largeNumber, BigNumber& smallNumber, vector<int>& carries);

	/**
	 * Method that calculates the product of a given portion of a big number
	 * @param start - Start position in the vectors
	 * @param end - End position in the vectors
	 * @param largeNumber - The number with more digits
	 * @param smallNumber - The number with less digits
	 * @param result - Vector that contains the result values of the multiplication
	 */
	void computeMultiply(int start, int end, BigNumber & smallNumber, BigNumber & largeNumber, BigNumber & result);

	/**
	 * Method that calculates the carry flag values of an upcoming sum of a portion of two given numbers
	 * @param start - Start position in the vectors
	 * @param end - End position in the vectors
	 * @param no_thread - Current thread number
	 * @param largeNumber - The number with more digits
	 * @param smallNumber - The number with less digits
	 * @param carries - Vector that contains the carry values of the sum
	 */
	void computeClassification(int start, int end, int no_thread, BigNumber &largeNumber, BigNumber &smallNumber, vector<int> &carries);
	void wait(vector<int>& carries);
public:

	/**
	* Constructor
	*/
	BigNumber();

	/**
	* Constructor with parameter
	* @param digits - big number values
	*/
	BigNumber(vector<int> digits);

	/**
	* Constructor with parameter
	* @param size - size of the big number
	*/
	BigNumber(int size);

	/**
	* Copy constructor
	* @param otherNumber - big number
	*/
	BigNumber(const BigNumber &otherNumber);

	/**
	* Destructor
	*/
	~BigNumber();

	/**
	* Method that returns all the big number digit values
	* @return vector<int> - vector with digit values
	*/
	vector<int> getDigits();

	/**
	* Method that returns a digit from a given index in the big number
	* @param index - digit position to be returned
	* @return int - digit at index position
	*/
	int getDigit(int index);

	/**
	* Method that returns the size of the big number
	* @return size of the number
	*/
	int getSize();

	/**
	* Method that changes the size of the number
	* @param size - new value of size
	*/
	void setSize(int size);

	/**
	* Method that changes a digit from a given index in the big number
	* @param index - digit position to be changed
	*/
	void setDigit(int index, int value);

	/**
	* Method that adds a value at the beginning of the number
	* @param value - value to be added
	*/
	void addDigit(int value);

	/**
	* Method that adds a value at a given position in the number and returns the carry value
	* @param position - digit position to be added
	* @param value - value to be added
	* @return int - carry of the sum
	*/
	int addDigit(int position, int value);

	/**
	* Method that reverses the values in the number
	*/
	void reverseNumber();

	void addMPI(BigNumber otherNumber);

	vector<int> getSegment(int start, int end);

	/**
	* Method that adds two BigNumbers together, sequentially
	* @param otherNumber - number to be added to this number
	* @return BigNumber - result of the sum
	*/
	BigNumber addSequential(BigNumber otherNumber);

	/**
	* Method that adds two BigNumbers together using threads and carry flag classification
	* @param otherNumber - number to be added to this number
	* @param numberOfThreads - total number of Threads to be used
	* @return BigNumber - result of the sum
	*/
	BigNumber addParallelClassification(BigNumber otherNumber, int numberOfThreads);

	/**
	* Method that adds two BigNumbers together using threads
	* @param otherNumber - number to be added to this number
	* @param numberOfThreads - total number of Threads to be used
	* @return BigNumber - result of the sum
	*/
	BigNumber addParallelOptimised(BigNumber otherNumber, int numberOfThreads);

	/**
	* Method that prints the values of the number
	*/
	void printBigNumber();

	/**
	* Method that multiplies two BigNumbers together, sequentially
	* @param otherNumber - number to be added to this number
	* @return BigNumber - result of the multiplication
	*/
	BigNumber multiplySequential(BigNumber otherNumber);

	/**
	* Method that multiplies two BigNumbers together using threads
	* @param otherNumber - number to be added to this number
	* @param numberOfThreads - total number of Threads to be used
	* @return BigNumber - result of the multiplication
	*/
	BigNumber multiplyParallel(BigNumber otherNumber, int numberOfThreads);
};
