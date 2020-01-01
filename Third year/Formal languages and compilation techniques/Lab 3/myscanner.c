#include<stdio.h>
#include"myscanner.h"

#define STATUS_UNSUCCESSFUL -1
#define STATUS_SUCCESS 0

extern int yylex();
extern int yylineno;
extern char* yytext;

typedef struct __SymbolTableElement
{
	char Name[100];
	int Code;
}SymbolTableElement;

typedef struct __Node
{
	struct __Node* Left;
	struct __Node* Right;
	SymbolTableElement Symbol;
}Node, *NodePointer;

typedef struct __SymbolTable
{
	Node* Root;
	int CurrentSize;
}SymbolTableTree;

typedef struct __FipTableElement
{
	int SymbolCode;
	int Code;
}FipTableElement;

typedef struct __FipTable
{
	FipTableElement FipElems[1000];
	int CurrentSize;
}FipTable;

SymbolTableTree gSymTable;
FipTable gFipTable;

SymbolTableElement CreateSymbol(char* Name, int Code)
{
	SymbolTableElement symbol;
	strcpy(symbol.Name, Name);
	symbol.Code = Code;
	return symbol;
}

NodePointer CreateNode(SymbolTableElement Symbol)
{
	NodePointer newNode = (NodePointer)calloc(1, sizeof(Node));
	newNode->Symbol = Symbol;
	return newNode;
}

int BSTCreate(SymbolTableTree* Tree)
{
	Tree->Root = NULL;
	Tree->CurrentSize = NULL;
}

Node* BSTInsertNode(Node* Root, SymbolTableElement Symbol)
{
	if (NULL == Root)
	{
		Root = CreateNode(Symbol);
		Root->Left = NULL;
		Root->Right = NULL;
	}
	else
	{
		if (strcmp(Root->Symbol.Name, Symbol.Name) > 0)
		{
			Root->Left = BSTInsertNode(Root->Left, Symbol);
		}
		else
		{
			Root->Right = BSTInsertNode(Root->Right, Symbol);
		}
	}
	return Root;
}

void BSTInsertSymbolStructure(SymbolTableElement Symbol)
{
	if (!ExistsSymbol(Symbol.Name))
	{
		gSymTable.Root = BSTInsertNode(gSymTable.Root, Symbol);
		gSymTable.CurrentSize++;
	}
}

Node* BSTFind(Node* Root, SymbolTableElement Symbol)
{
	if (NULL == Root)
	{
		return NULL;
	}
	else
	{
		if (strcmp(Root->Symbol.Name, Symbol.Name) > 0)
		{
			return BSTFind(Root->Left, Symbol);
		}
		else
		{
			if (strcmp(Root->Symbol.Name, Symbol.Name) < 0)
			{
				return BSTFind(Root->Right, Symbol);
			}
		}
	}
	return Root;
}

SymbolTableElement FindSymbol(char* Name)
{
	Node* node;
	SymbolTableElement comparer;
	comparer = CreateSymbol(Name, -1);
	node = BSTFind(gSymTable.Root, comparer);
	return node->Symbol;
}

int ExistsSymbol(char* Name)
{
	Node* node;
	SymbolTableElement comparer;
	comparer = CreateSymbol(Name, -1);
	node = BSTFind(gSymTable.Root, comparer);
	return NULL != node;
}

void InsertSymbolToSymbolTable(char* Name)
{
	BSTInsertSymbolStructure(CreateSymbol(Name, gSymTable.CurrentSize));
}

void BSTInorderPrint(Node* Root)
{
	if (Root)
	{
		BSTInorderPrint(Root->Left);
		printf("Symbol name: %-30s | Symbol code: %d\n", Root->Symbol.Name, Root->Symbol.Code);
		BSTInorderPrint(Root->Right);
	}
}

void PrintSymbolTable()
{
	BSTInorderPrint(gSymTable.Root);
}

FipTableElement CreateFipTableElement(int SymCode, int Code)
{
	FipTableElement element;
	element.SymbolCode = SymCode;
	element.Code = Code;
	return element;
}

void InsertIntoFIPTable(char*Name, int Code)
{
	FipTableElement fipElement;
	if (ExistsSymbol(Name))
	{
		SymbolTableElement symbol = FindSymbol(Name);
		fipElement = CreateFipTableElement(symbol.Code, Code);
		gFipTable.FipElems[gFipTable.CurrentSize] = fipElement;
		gFipTable.CurrentSize++;
	}
	else
	{
		fipElement = CreateFipTableElement(-1, Code);
		gFipTable.FipElems[gFipTable.CurrentSize] = fipElement;
		gFipTable.CurrentSize++;
	}
}

void PrintFipTable()
{
	int index;

	for (index = 0; index < gFipTable.CurrentSize; ++index)
	{
		if (-1 == gFipTable.FipElems[index].SymbolCode)
		{
			printf("Symbol code: -|Fip code: %d\n", gFipTable.FipElems[index].Code);
		}
		else
		{
			printf("Symbol code: %d|Fip code: %d\n", gFipTable.FipElems[index].SymbolCode, gFipTable.FipElems[index].Code);
		}
	}
}

int main(int argc, char *argv[])
{
	int ntoken = yylex();
	BSTCreate(&gSymTable);
	while (ntoken)
	{
		if (32 != ntoken) //32 empty
		{
			if (1 == ntoken || 2 == ntoken)
			{
				InsertSymbolToSymbolTable(yytext);
			}
			InsertIntoFIPTable(yytext, ntoken);
		}
		ntoken = yylex();
	}
	PrintFipTable();

	PrintSymbolTable();

	return 0;
}