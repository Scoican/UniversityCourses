#pragma once
#include <string>
#include <vector>
#include "Movie.h"

/*
Export function that write given data in a HTML or CVS file
@param fName The name of the file in which we write
@param movies Data to be writen in the file
*/
void exportToHTML(const std::string& fName, const std::vector<Movie>& movies);
void exportToCVS(const std::string& fName, const std::vector<Movie>& movies);
