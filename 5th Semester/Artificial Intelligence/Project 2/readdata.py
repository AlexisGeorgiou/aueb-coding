import os
import re
import random


def loadData(directory, datalist, sign, DATA_COUNT):
    """Loads data from a txt folder"""
    all_files = os.listdir(directory)
    for file_name in all_files[:DATA_COUNT]:
        try:
            f = open(directory + "/" + file_name, "r", encoding = 'utf-8')
            datalist.append((f.read(), sign))
            f.close()
        except:
            pass


def vectorizingReviews(data, words):
    """Creating a list for each review with 1s and 0s if the Nth word is inside the review"""
    vectors = [[]]
    count = 0
    for reviewtuple in data:
        review = reviewtuple[0]
        sign = reviewtuple[1]
        reviewwords = review.split()
        for reviewword in reviewwords:
            reviewword = re.sub('[^A-Za-z0-9]+', '', reviewword).lower()
        for word in words:
            if reviewwords.__contains__(word): #Review word in dictionary words and equal
                vectors[count].append(1)
            else:
                vectors[count].append(0)
        count += 1
        vectors.append([])
    vectors = vectors[:-1]
    return vectors


def wordFrequency(datalist, frequencylist =[], N = 10, M = 1000):
    """Creating our own frequency word list depending on the reviews we see"""
    #Given a datalist and a list variable, this function loads the frequency word list into the list variable from
    #higher to lower and skipping words outside N:M
    freqdictionary = {}
    for review in datalist:
        string = review[0]
        for word in string.split():
            word = word.lower().strip()
            word = re.sub('[^A-Za-z0-9]+', '', word)
            if word in freqdictionary:
                freqdictionary[word] += 1
            else:
                freqdictionary[word] = 1

    frequencylist = [k for k, v in sorted(freqdictionary.items(), key=lambda item: item[1], reverse = True)]
    frequencylist = frequencylist[N:M]
    return frequencylist


def loadVocab(words, N = 10, M = 1000):
    """Loading the default vocab"""
    #Loads words in a given list
    #Skipping N words and every word after Mth
    f = open("imdb.vocab", "r", encoding = 'utf-8')
    for line in f.readlines()[N:M]:
        line = line.strip()
        try:
            words.append(line)
        except:
            pass
    f.close()



    
