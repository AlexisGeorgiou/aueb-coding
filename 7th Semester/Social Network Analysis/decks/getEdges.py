import os
import pandas as pd

decks = []
#Reading the deck files
for file_name in os.listdir("../decks"):
    file_extension = file_name.split(".",1)[1]
    if file_extension == "ydk":
        try:
            with open(file_name, encoding="utf8") as file:
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
            data.append([card1, card2])
##    print("Finished: Deck {}".format(count))



print("Edges:", len(data))


###Keep only one time each edge
##setdata = []
##for edge in data:
##    if edge not in setdata:
##        setdata.append(edge)
##        
##print("Unique edges:", len(setdata))


#Making a pandas dataframe using the data    
df = pd.DataFrame(data, columns = ['Node_1', 'Node_2'])

#Save file into csv
df.to_csv('Edges.csv', index = False)
