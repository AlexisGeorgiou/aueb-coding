#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include "p3180027-pizza2.h"
#include <stdlib.h>
#include <time.h>


int nCooks = NCOOK;
int nOvens = NOVEN;
int nDeliverers = NDELIVERER;

int totalTime = 0;
int maxTime = 0;
int maxID = 0;
int totalTimeCold = 0;
int maxTimeCold = 0;
int maxIDCold = 0;

pthread_mutex_t cookMutex;
pthread_mutex_t ovenMutex;
pthread_cond_t cookCond;
pthread_cond_t ovenCond;
pthread_mutex_t delivererMutex;
pthread_cond_t delivererCond;
pthread_mutex_t timeMutex;
pthread_cond_t timeCond;


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
	pthread_mutex_destroy(&delivererMutex);
	pthread_cond_destroy(&delivererCond);
	pthread_mutex_destroy(&timeMutex);
	pthread_cond_destroy(&timeCond);

	printf("Ο μέγιστος χρόνος παράδοσης παραγγελίας είναι %i λεπτά στην παραγγελία με αριθμό %i. \n", maxTime, maxID);
	printf("Ο μέσος χρόνος παράδοσης των παραγγελιών είναι %i.\n", totalTime/nCust);
	
	printf("Ο μέγιστος χρόνος κρυώματος παραγγελίας είναι %i στην παραγγελία με αριθμό %i. \n", maxTimeCold, maxIDCold);
	printf("Ο μέσος χρόνος κρυώματος των παραγγελιών είναι %i.\n", totalTimeCold/nCust);
	sleep(600); //Some time to check results
}


void *order(void *pointer_of_struct){
	struct Order *currentOrder = (struct Order*) pointer_of_struct; 
	//void pointer to struct pointer casting
	int currentOrderID = currentOrder -> orderID;
	int currentPizzas = currentOrder -> number_of_pizzas;
	printf("Λήφθηκε η παραγγελία %i. για %i πίτσες.\n", currentOrderID, currentPizzas);
	
	struct timespec order_start;
	struct timespec order_finish;
	struct timespec cold_start;
	struct timespec cold_finish;
	int order_total_time;
	int cold_total_time;
	int delivery_time;

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

	//Αποδέσμευση παρασκευαστή, τις έβαλε και είναι διαθέσιμος

	pthread_mutex_lock(&cookMutex);
	nCooks++;
	pthread_cond_signal(&cookCond);
	pthread_mutex_unlock(&cookMutex);
	sleep(TBAKE); // Οι πίτσες ψήνονται

	clock_gettime(CLOCK_REALTIME, &cold_start); // ψήθηκαν, αρχίζουν να κρυώνουν δεύτερο ρολόι για να μετρήσουμε το κρύωμα (θεωρούμε ότι κρυώνουν ακόμα και μέσα στο φούρνο)



	//Δέσμευση ντελιβερά
	pthread_mutex_lock(&delivererMutex);
	while (nDeliverers == 0){
		printf("Η παραγγελία %i δεν βρήκε διαθέσιμο ντελιβερά, βρίσκεστε σε αναμονή.\n", currentOrderID);
		pthread_cond_wait(&delivererCond, &delivererMutex);
	}
	nDeliverers--;
	printf("Δεσμεύτηκε ντελιβεράς για την παραγγελία %i \n", currentOrderID);
	pthread_mutex_unlock(&delivererMutex);

	//Αποδέσμευση φούρνου και 
	//signaling σε μπλοκαρισμένα threads

	pthread_mutex_lock(&ovenMutex);
	nOvens++;
	pthread_cond_signal(&ovenCond);
	pthread_mutex_unlock(&ovenMutex);

	delivery_time = randomNumberBetween(TLOW, THIGH); // Χρόνος παράδοσης
	sleep(delivery_time); // Παραδόθηκε η παρραγελία
	clock_gettime(CLOCK_REALTIME, &order_finish);
	clock_gettime(CLOCK_REALTIME, &cold_finish);

	 // maxTime and maxID could be accessed in the same time, we don't want that. Same for cold clock.
	order_total_time = order_finish.tv_sec - order_start.tv_sec;
	totalTime = totalTime + order_total_time;
	pthread_mutex_lock(&timeMutex);
	if (order_total_time > maxTime){
		maxTime = order_total_time;
		maxID = currentOrderID;
	}
	pthread_mutex_unlock(&timeMutex);

	cold_total_time = cold_finish.tv_sec - cold_start.tv_sec;
	totalTimeCold = totalTimeCold + cold_total_time;
	pthread_mutex_lock(&timeMutex);
	if (cold_total_time > maxTimeCold){
		maxTimeCold = cold_total_time;
		maxIDCold = currentOrderID;
	}
	printf("Η παραγγελία με αριθμό %i  παραδόθηκε σε %i λεπτά και κρύωνε για %i λεπτά.\n", currentOrderID, order_total_time, cold_total_time);
	pthread_mutex_unlock(&timeMutex);

	sleep(delivery_time);
	pthread_mutex_lock(&delivererMutex);
	nDeliverers++;
	pthread_cond_signal(&delivererCond);
	pthread_mutex_unlock(&delivererMutex);

	pthread_exit(NULL);

}


int randomNumberBetween(int a, int b){
	return rand() % (b-a+1) + a;
}

