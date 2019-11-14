#include <stdlib.h>
#include <iostream>
#include <time.h>
#include <chrono>
#include <mpi.h>
#include "TxtOperations.h"
#include "XlsxOperations.h"

void compare(TxtOperations txtFile) {
	//Comparision results
	if (txtFile.fileComparator("sequentialSum.txt", "parallelOptimisedSum.txt")) {
		cout << "True" << endl;
	}
	else {
		throw std::invalid_argument("comparison failed at optimised sum");
	}

	if (txtFile.fileComparator("sequentialSum.txt", "parallelClasificationSum.txt")) {
		cout << "True" << endl;
	}
	else {
		throw std::invalid_argument("comparison failed at classification sum");
	}

	if (txtFile.fileComparator("sequentialMultiplication.txt", "parallelMultiplication.txt")) {
		cout << "True" << endl;
	}
	else {
		throw std::invalid_argument("comparison failed at multiplication");
	}
}

void calculate(int numberOfThreads, int min, int max) {
	TxtOperations txtFile;

	//txtFile.fileGenerator("numbers.txt", 2, min, max);
	vector<BigNumber> numbers = txtFile.readNumbersFromFile("numbers.txt");

	BigNumber firstNumber = numbers.at(0);
	BigNumber secondNumber = numbers.at(1);

	//Sequential sum
	auto startTime = chrono::steady_clock::now();
	BigNumber sequentialSum = firstNumber.addSequential(secondNumber);
	auto endTime = chrono::steady_clock::now();
	txtFile.writeNumberToFile(sequentialSum, "sequentialSum.txt");
	auto diff = endTime - startTime;
	cout << "Sequential sum Time: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	//Parallel sum optimised
	auto startTime1 = chrono::steady_clock::now();
	BigNumber parallelSum = firstNumber.addParallelOptimised(secondNumber, numberOfThreads);
	auto endTime1 = chrono::steady_clock::now();
	txtFile.writeNumberToFile(parallelSum, "parallelOptimisedSum.txt");
	auto diff1 = endTime1 - startTime1;
	cout << "Optimised Parallel sum Time: " << chrono::duration<double, milli>(diff1).count() << " ms" << endl;

	//Parallel sum simple
	auto startTime2 = chrono::steady_clock::now();
	BigNumber parallelCassification = firstNumber.addParallelClassification(secondNumber, numberOfThreads);
	auto endTime2 = chrono::steady_clock::now();
	txtFile.writeNumberToFile(parallelCassification, "parallelClassificationSum.txt");
	auto diff2 = endTime2 - startTime2;
	cout << "Classification Parallel sum Time: " << chrono::duration<double, milli>(diff2).count() << " ms" << endl;

	//Sequential multiplication
	auto startTime3 = chrono::steady_clock::now();
	BigNumber sequentialMultimplication = firstNumber.multiplySequential(secondNumber);
	auto endTime3 = chrono::steady_clock::now();
	txtFile.writeNumberToFile(sequentialMultimplication, "multiplySequential.txt");
	auto diff3 = endTime3 - startTime3;
	cout << "Sequential multiplication Time: " << chrono::duration<double, milli>(diff3).count() << " ms" << endl;

	//Parallel multiplication
	auto startTime4 = chrono::steady_clock::now();
	BigNumber parallelMultimplication = firstNumber.multiplyParallel(secondNumber, numberOfThreads);
	auto endTime4 = chrono::steady_clock::now();
	txtFile.writeNumberToFile(parallelMultimplication, "multiplyParallel.txt");
	auto diff4 = endTime4 - startTime4;
	cout << "Parallel multiplication Time: " << chrono::duration<double, milli>(diff4).count() << " ms" << endl;

	compare(txtFile);
}

void MPI_Calculcate(int min, int max) {
	TxtOperations txtFile;

	txtFile.fileGenerator("numbers.txt", 2, min, max);
	vector<BigNumber> numbers = txtFile.readNumbersFromFile("numbers.txt");

	BigNumber firstNumber = numbers.at(0);
	BigNumber secondNumber = numbers.at(1);

	firstNumber.addMPI(secondNumber);
	txtFile.writeNumberToFile(firstNumber.addSequential(secondNumber), "sequentialSum.txt");
}

int main() {
	srand(static_cast<int>(time(0)));
	//calculate(8, 1000, 1010);
	MPI_Calculcate(1000, 1010);
	return 0;
}