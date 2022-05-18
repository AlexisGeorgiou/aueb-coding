#define NCOOK 6
#define NOVEN 5
#define TORDERLOW 1
#define TORDERHIGH 5
#define NORDERLOW 1
#define NORDERHIGH 5
#define TPREP 1
#define TBAKE 10


void *order(void *pointer_of_struct);

struct Order{
	int orderID;
	int number_of_pizzas;
};
int randomNumberBetween(int a, int b);