import json
import pandas as pd


#File data is found on a GET request in the below link
#Provided by API of db.ygoprodeck.com (Fetched @February 2022)
#Simply download the data using a browser and the link (data must be placed in same directory)
#https://db.ygoprodeck.com/api/v7/cardinfo.php


#Read File of all yugioh cards data
f = open("cardinfo.php", "r")
stringfile = f.read()
f.close()

#String to JSON
jsonfile = json.loads(stringfile)


data = []

#Appending each card's data into data list
for carddata in jsonfile['data']:
    cardId = carddata['id']
    cardName = carddata['name']
    cardType = carddata['type']
    data.append([cardId, cardName, cardType])
    

#Making a pandas dataframe using the data
df = pd.DataFrame(data, columns = ['ID', 'Name', 'Type'])

#Save file into csv
df.to_csv('Nodes2.csv', index = False)
