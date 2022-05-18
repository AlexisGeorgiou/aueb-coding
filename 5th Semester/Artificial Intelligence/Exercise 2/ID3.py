import readdata
import random
import re
from math import log as logM
import copy

DATA_COUNT = 2000
DEFAULT_WORD_DECTIONARY = True
N = 70
M = 90


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
    
def entropyC(digitcount, currentdata, signVector, digit = None):
    ##Calculates entropy for selected property
    ##auxiliary counters for calculating the probability of a word showing up in the data
    onesCounter = 0 
    onesPosCounter = 0
    onesNegCounter = 0
    zerosCounter = 0
    zerosPosCounter = 0
    zerosNegCounter = 0
    ##parsing data
    for data in currentdata:
        sign = signVector[currentdata.index(data)]
        prop = data[digitcount] ##current property
        if prop == 1: ##if in data, increase oneCounters
            onesCounter += 1
            if sign == 1:
                onesPosCounter += 1
            elif sign == -1:
                onesNegCounter += 1
        elif prop == 0:##if not in data increase zeroCounters
            zerosCounter += 1
            if sign == 1:
                zerosPosCounter += 1
            elif sign == -1:
                zerosNegCounter += 1
    if zerosCounter == 0 or onesCounter == 0: ##if either all are found or all are not found
        return 0


    onesPosProb = onesPosCounter/onesCounter
    onesNegProb = onesNegCounter/onesCounter
    zerosPosProb = zerosPosCounter/zerosCounter
    zerosNegProb = zerosNegCounter/zerosCounter
    
    
    HC = 0
    ##Calculate entropy
    if digit == None:
        posCounter = 0
        for sign in signVector:
            if sign == 1:
                posCounter += 1
        posProb = posCounter/len(currentdata)
        negProb = 1 - posProb
        HC = - 1* posProb*logM(posProb,2) - 1* negProb*logM(negProb,2)
        
    elif digit == 1:
        HC = -1* onesPosProb * logM(onesPosProb,2) - 1* onesNegProb * logM(onesNegProb,2)
    elif digit == 0: 
       HC = -1* zerosPosProb * logM(zerosPosProb,2) - 1* zerosNegProb * logM(zerosNegProb,2)
        
    return HC

#print(words)

def informationGain(digitcount , currentdata, signVector):
    ##Calculates information Gain for given property
    HC = entropyC( 0, currentdata, signVector)
    onesCounter = 0
    totalCounter = 0
    for vector in currentdata:
        digit = vector[digitcount]
        if digit == 1:
            onesCounter +=1
        totalCounter += 1
            
    P0 = onesCounter / totalCounter
    P1 = 1 - P0
    return HC - (P0 * entropyC(digitcount, currentdata, signVector, 0)) - (P1 * entropyC(digitcount, currentdata, signVector, 1))
    

##Node class for tree
class Node:
    def __init__(self, data = 0, divider = ''):
        
        self.divider = divider 
        self.left = None #0
        self.right = None #1
        self.data = data

    def setLeft(self, left):
        self.left = left
        
    def setRight(self, right):
        self.right = right
        
    def setData(self, data):
        self.data = data
        
    def setDivider(self, divider):
        self.divider = divider


##parallel list with signs
signVector = []
for reviewtuple in traindata:
    signVector.append(reviewtuple[1])


def ID3(currentdata, properties, signVector, default):
    ##Estimating percentage of reviews in the positive category
    posCounter = 0
    for i in signVector:
        if i == 1:
            posCounter += 1
    negCounter = len(signVector) - posCounter
    totalCounter = posCounter + negCounter
    ##if dataset is empty then return the default category
    if len(currentdata) == 0:
        return Node(default)
    ##if there are no more properties left, return the most probable category
    elif len(properties) == 0:
        if posCounter > negCounter:

            return Node(1)
        else:
            return Node(-1)
    ##if 60% of the data are positive then return positive category
    elif posCounter / totalCounter >= 0.6:
        return Node(1)
    ##if 60% of the data are negative then return negative category
    elif negCounter / totalCounter >= 0.6:
        return Node(-1)

    ##else recursion 
    else:
        ##Calculating information gain for all properties left in the set
        informationGains = []
        digitcount = 0
        for prop in properties:
            informationGains.append(informationGain(digitcount, currentdata, signVector))
            digitcount += 1

        ##selecting property with the best information gain as divider  
        divIndex = informationGains.index(max(informationGains))
        divider = properties[divIndex]
        #print(divider)
            

        ##Creating children trees
        treeNode = Node()
        treeNode.setDivider(divider)
        subTree = Node()

        ##remove divider (best property) from list
        newproperties = list(properties)
        del newproperties[divIndex]

        ##Splitting data between the two sub trees (0s go to the left of the divider, 1s to the right)
        for vi in range(2):
            newData = []
            newSign = []
            for data in currentdata:
                if data[divIndex] == vi:
                    newData.append(data)
                    newSign.append(signVector[currentdata.index(data)])

            
            
            ##find next subtree
            subTree = ID3(newData, newproperties, newSign, default)
            if vi == 0:
                treeNode.setLeft(subTree)
            elif vi == 1:
                treeNode.setRight(subTree)
            subTree = Node()
            
        return treeNode


##calculating ID3 tree
tree = ID3(vectors, words, signVector, 1)          

def ID3Traverse(vector):
    ##Traverses the ID3 tree for given review
    root = tree
    divider = root.divider
    dividerIndex = words.index(divider)
    while root.data == 0:
        digit = vector[dividerIndex]
        if digit == 0:
            root = root.left
        elif digit == 1:
            root = root.right
        divider = root.divider
        if root.data == 0:
            dividerIndex = words.index(divider)

    return root.data


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
print("Accuracy in Train Data: ", accuracy(ID3Traverse, trainDataBoole = True))
print("Accuracy in Test Data: ", accuracy(ID3Traverse, trainDataBoole = False))        

        
        
