import os
import pandas as pd

#Creating edges by checking meta deck files


decks = []
path = "decks/"
#Reading the deck files
for file_name in os.listdir(path):
    file_extension = file_name.split(".",1)[1]
    if file_extension == "ydk":
        try:
            with open(path + file_name, encoding="utf8") as file:
                filelines = file.readlines()
            file.close()


            current_deck = []
            for line in filelines:
                line = line.strip()
                if line.isnumeric():
                    current_deck.append(line)

            decks.append(current_deck)
            
        except:
            print("There was an error on ydk file: {}".format(file_name))
        
print("Reading files ended..")

#Adding all edges between cards
#If a card is in the same deck with other card there should be connected
data = []
count = 0

for deck in decks:
    count += 1
    for card1 in deck:
        for card2 in deck:
            #Cards should not connect with itself
            #We also don't have to add same edges multiple times
            if card1 != card2:
                if [card1, card2] not in data and [card2, card1] not in data: #Checking if the edge is already part of the data is increasing the algorithm's complexity
                    data.append([card1, card2])
    print("Finished: Deck {}".format(count))



print("Edges:", len(data))



#Making a pandas dataframe using the data    
df = pd.DataFrame(data, columns = ['Source', 'Target'])

#Save file into csv
df.to_csv('Edges2.csv', index = False)
