import readdata
import random
import re
import numpy

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

words = [] #Given vocab
if DEFAULT_WORD_DECTIONARY: #Choosing default/custom dictionary
    readdata.loadVocab(words, N, M)
else:
    words = readdata.wordFrequency(traindata, words, N, M) #Created word freqlist

#Vectorizing reviews  
vectors = readdata.vectorizingReviews(traindata, words)
vectorsTest = readdata.vectorizingReviews(testdata, words)


print("Data loaded")

#Calculating the probabilities needed for Bayes formula
onesposlist = []
onesneglist = []
#Counts the probability of a digit being 1 given the review is positive/negative
#If the word digit is 0 it can be calculated from the above probabilities
for i in range(M-N):
    onesposcounter = 0
    onesnegcounter = 0
    positivecounter = 0
    negativecounter = 0
    traindatacount = 0
    for reviewtuple in traindata:
        sign = reviewtuple[1]
        if sign == 1:
            positivecounter += 1
            if vectors[traindatacount][i] == 1:
                onesposcounter += 1
        if sign == -1:
            negativecounter += 1
            if vectors[traindatacount][i] == 1:
                onesnegcounter += 1
        traindatacount += 1

    onesposlist.append(onesposcounter/positivecounter) #Contains the probability of the review being positive if the word is 1
    onesneglist.append(onesnegcounter/negativecounter) #Contains the probability of the review being negative if the word is 1

##print(onesposlist)
##print(onesneglist)

    



def Bayes(vector):
    digitcount = 0
    posprobvector = []
    negprobvector = []
    #Choosing the probabilities needed for Bayes formula depending of the digit in vectorized review (if the word is included)
    for digit in vector:
        if digit == 1:
            posprobvector.append(onesposlist[digitcount])
            negprobvector.append(onesneglist[digitcount])
        elif digit == 0:
            posprobvector.append(1 - onesposlist[digitcount])
            negprobvector.append(1 - onesneglist[digitcount])

        digitcount += 1
        
    #Estimating the Bayes formula
    pos = 0.5 * numpy.prod(posprobvector)
    neg = 0.5 * numpy.prod(negprobvector)
    if pos > neg:
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
print("Accuracy in Train Data: ", accuracy(Bayes, trainDataBoole = True))
print("Accuracy in Test Data: ", accuracy(Bayes, trainDataBoole = False))
