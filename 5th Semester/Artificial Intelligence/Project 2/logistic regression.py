import readdata
import random
import re
import numpy
import math

DATA_COUNT = 5000 #Loads DATA_COUNT txts for each of the 4 folders
DEFAULT_WORD_DECTIONARY = False
N = 50
M = 350
testdata = []
traindata = []

readdata.loadData("test/pos", testdata, 1, DATA_COUNT)
readdata.loadData("test/neg", testdata, -1, DATA_COUNT)
readdata.loadData("train/pos", traindata, 1, DATA_COUNT)
readdata.loadData("train/neg", traindata, -1, DATA_COUNT)

random.shuffle(testdata)
random.shuffle(traindata)

words = []
if DEFAULT_WORD_DECTIONARY: #Choosing default/custom dictionary
    readdata.loadVocab(words, N, M)
else:
    words = readdata.wordFrequency(traindata, words, N, M) #Created word freqlist

#Vectorizing reviews  
vectors = readdata.vectorizingReviews(traindata, words)
vectorsTest = readdata.vectorizingReviews(testdata, words)

print("Data loaded")


def updateWeight(weights, weight, index, h):
    """Updates the weight"""
    newWeight = weight
    sumValue = 0
    sumOfProducts = []
    i = random.choice(range(len(traindata))) #Choosing a random review
    for j in range(len(weights) - 1):
        sumOfProducts.append(weights[j]*vectors[i][j])

    sumOfProducts.append(weights[len(weights) - 1])

    P = 1 / (1 + math.exp(-sum(sumOfProducts)))

    if traindata[i][1] == -1:
        sign = 0
    else:
        sign = 1

    if index == len(weights) - 1:
        sumValue += (sign - P) * 1
    else:
        sumValue += (sign - P) * vectors[i][index]

    newWeight += h * sumValue
    return newWeight

def LogisticRegression(K = 1000, h = 0.2):
    """Builds the weight vector"""
    weights = []
    for i in range(M-N+1): #We added a w0 weight at the end
        weights.append(random.uniform(-1, 1))
    for i in range(K):
        for j in range(len(weights)):
            weights[j] = updateWeight(weights, weights[j], j, h)

    return weights


weights = LogisticRegression()

def LogisticRegressionEvaluate(vector):
    """Calculates the evulation and guesses the review type"""
    sumOfProducts = []
    for j in range(len(weights) - 1):
        sumOfProducts.append(weights[j]*vector[j])
        
    sumOfProducts.append(weights[len(weights) - 1])
    
    evaluation = sum(sumOfProducts)
    if evaluation >= 0:
        return 1
    else:
        return -1
            



def accuracy(function, trainDataBoole = True):
    """Checks the validity of the guess and calculates accuracy, precision, recall and F1 score""" 
    TP = FN = FP = TN = 0
    total = 0
    correct = 0
    if trainDataBoole:
        for vector in vectors:
            if traindata[total][1] == function(vector):
                correct += 1
                if traindata[total][1] == 1:
                    TP += 1
                elif traindata[total][1] == -1:
                    TN += 1
            else:
                #incorrect
                if traindata[total][1] == 1:
                    FP += 1
                elif traindata[total][1] == -1:
                    FN += 1
            total += 1

    else:
        for vector in vectorsTest:
            if testdata[total][1] == function(vector):
                correct += 1
                if testdata[total][1] == 1:
                    TP += 1
                elif testdata[total][1] == -1:
                    TN += 1
            else:
                #incorrect
                if testdata[total][1] == 1:
                    FP += 1
                elif testdata[total][1] == -1:
                    FN += 1
            total += 1

    ACC = correct/total
    PPV = TP / (TP + FP)
    SEN = TP / (TP + FN)
    F1 = 2 * ((PPV * SEN) / (PPV + SEN))
    return ACC, PPV, SEN, F1

print("N: ", N, " M: ", M, " Data Amount: ", 2*DATA_COUNT, " Custom Dictionary: ", not(DEFAULT_WORD_DECTIONARY))
print("Form of results: (accuracy, precision, recall, F1 score)")
print("Accuracy in Train Data: ", accuracy(LogisticRegressionEvaluate, trainDataBoole = True))
print("Accuracy in Test Data: ", accuracy(LogisticRegressionEvaluate, trainDataBoole = False))
