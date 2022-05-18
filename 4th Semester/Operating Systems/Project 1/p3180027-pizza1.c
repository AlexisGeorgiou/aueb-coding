#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include "p3180027-pizza1.h"
#include <stdlib.h>
#include <time.h>


int nCooks = NCOOK;
int nOvens = NOVEN;
int totalTime = 0;
int maxTime = 0;
int maxID = 0;

pthread_mutex_t cookMutex;
pthread_mutex_t ovenMutex;
pthread_cond_t cookCond;
pthread_cond_t ovenCond;

int main(int argc, char** argv){

	int nCust = atoi(argv[1]);
	int seed = atoi(argv[2]);
	srand(seed);
	struct Order orderstruct = {.orderID = 0, .number_of_pizzas = 0};
	pthread_t threads[nCust];
	
	for (int i = 0; i < nCust; i++){
		orderstruct.orderID= i + 1;
		orderstruct.number_of_pizzas = randomNumberBetween(NORDERLOW, NORDERHIGH);
		//printf("Thread %i created.\n", i+1);
		pthread_create(&threads[i], NULL, order, &orderstruct);
		sleep(randomNumberBetween(TORDERLOW, TORDERHIGH));
	}
	
	for (int i = 0; i < nCust; i++){
		pthread_join(threads[i], NULL);
	}

	pthread_mutex_destroy(&cookMutex);
	pthread_cond_destroy(&cookCond);
	pthread_mutex_destroy(&ovenMutex);
	pthread_cond_destroy(&ovenCond);

	printf("Ο μέγιστος χρόνος ολοκλήρωσης παραγγελίας είναι %i στην παραγγελια με αριθμό %i. \n", maxTime, maxID);
	printf("Ο μέσος χρόνος ολοκλήρωσης των παραγγελιών είναι %i.\n", totalTime/nCust);

}


void *order(void *pointer_of_struct){
	struct Order *currentOrder = (struct Order*) pointer_of_struct; 
	//void pointer to struct pointer casting
	int currentOrderID = currentOrder -> orderID;
	int currentPizzas = currentOrder -> number_of_pizzas;
	printf("Λήφθηκε η παραγγελία %i. για %i πίτσες.\n", currentOrderID, currentPizzas);
	
	struct timespec order_start;
	struct timespec order_finish;
	int order_total_time;

	clock_gettime(CLOCK_REALTIME, &order_start);

	//Δέσμευση παρασκευαστή
	pthread_mutex_lock(&cookMutex);
	while (nCooks == 0){
		printf("Η παραγγελία %i δεν βρήκε διαθέσιμο παρασκευαστή, βρίσκεστε σε αναμονή.\n", currentOrderID);
		pthread_cond_wait(&cookCond, &cookMutex);
	}
	nCooks--;
	printf("Δεσμεύτηκε παρασκευαστής για την παραγγελία %i.\n", currentOrderID);
	pthread_mutex_unlock(&cookMutex);
	sleep(currentPizzas * TPREP); // Πρώτα προετοιμάζει όλες τις πίτσες
	printf("Προετοιμάστηκαν %i πίτσες για την παραγγελία %i.\n",currentPizzas, currentOrderID);

	//Δέσμευση φούρνου
	pthread_mutex_lock(&ovenMutex);
	while (nOvens == 0){
		printf("Η παραγγελία %i δεν βρήκε διαθέσιμο φούρνο, βρίσκεστε σε αναμονή.\n", currentOrderID);
		pthread_cond_wait(&ovenCond, &ovenMutex);
	}
	nOvens--;
	printf("Δεσμεύτηκε φούρνος για την παραγγελία %i \n", currentOrderID);
	pthread_mutex_unlock(&ovenMutex);
	sleep(TBAKE); // Έπειτα ψήνει όλες τις πίτσες σε ένα φούρνο ταυτόχρονα

	//Αποδέσμευση παρασκευαστή και φούρνου και 
	//signaling σε μπλοκαρισμένα threads

	pthread_mutex_lock(&cookMutex);
	pthread_cond_signal(&cookCond);
	nCooks++;
	pthread_mutex_unlock(&cookMutex);

	pthread_mutex_lock(&ovenMutex);
	pthread_cond_signal(&ovenCond);
	nOvens++;
	clock_gettime(CLOCK_REALTIME, &order_finish);
	order_total_time = order_finish.tv_sec - order_start.tv_sec;
	totalTime = totalTime + order_total_time;
	if (order_total_time > maxTime){
		maxTime = order_total_time;
		maxID = currentOrderID;
	}
	printf("Η παραγγελία με αριθμό %i  ετοιμάστηκε σε %i λεπτά.\n", currentOrderID, order_total_time);
	pthread_mutex_unlock(&ovenMutex);

	pthread_exit(NULL);

}


int randomNumberBetween(int a, int b){
	return rand() % (b-a+1) + a;
}

