#include <stdio.h>

int primalitate(int x) {
	if (x < 2 || x>2 && x % 2 == 0) {
		return 0;
	}
	int i;
	for (i = 3; i*i < x; i += 2) {
		if (x&i == 0) {
			return 0;
		}
	}
	return 1;
}

void goldbach() {
	int n = -1;
	printf("Give a number:");
	scanf_s("%d", &n);
	if (n % 2 == 1) {
		printf("Error!\n");
	}
	else {	
	printf("Descompuneri:\n");
		int i;
		for (i = 2; i < n; i++) {
			if (primalitate(i) == 1 && primalitate(n - i) == 1) {
				printf("%d+%d\n", i, n - i);
			}
		}
	}
}

void triunghiPascal() {
	int n = -1;
	printf("Give a number:");
	scanf_s("%d", &n);
	printf("Pascal:\n");
	int a[256][256];
	int i;
	int j;
	for (i = 0; i <= n; i++)
	{
		a[i][i] = 1;
		a[i][0] = 1;
	}
	for (i = 2; i <= n; i++)
		for (j = 1; j <= i - 1; j++)
			a[i][j] = a[i - 1][j] + a[i - 1][j - 1];
	for (i = 0; i <= n; i++)
	{
		for (j = 0; j <= i; j++)
			printf("%d ", a[i][j]);
		printf("\n");
	}
}

int main() {
	
	int cmd = 3;
	printf("1.Goldbach\n");
	printf("2.Pascal\n");
	printf("3.Exit\n");
	while (cmd != 0) {
		printf("Give command:");
		scanf_s("%d", &cmd);
		switch (cmd) {
		case 1:
			goldbach();
			break;
		case 2:
			triunghiPascal();
			break;
		case 0:
			break;
		default:
			printf("Error at input!\n");
			break;
		}
	}
	return 0;
}
