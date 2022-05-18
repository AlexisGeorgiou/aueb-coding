from random import randrange, shuffle

#Use this file to create random games txt files
#We have 4 possible n values and 3 possible m values
#Total combinations are 4*3 = 12 so if we want to produce
#30 files for each combination we will need 12*30 = 360 files per rule

rule = input("Input the rule (Plurality/Borda)\n")
numberofgames = int(input("How many files you want to produce?\n"))


for i in range(numberofgames):
    #Create a txt
    with open("{}_{}.txt".format(rule,i+1),"w+") as f:
        f.close()
    #Append random information about the game
    with open("{}_{}.txt".format(rule,i+1), "a") as f:
    #Append n (random int between 5-8
        n = randrange(5,9)
        f.write(str(n)+"\n")
    #Append m (random int between 5-7
        m = randrange(5,8)
        f.write(str(m)+"\n")
    #Append rule (Plurality/Borda)
        f.write(rule+"\n")
    #For every player, randomize his preference of m items
        for i in range(n):
            preferences = list((range(1,m+1)))
            shuffle(preferences)
            preferencesstring = ""
            for preference in preferences:
                preferencesstring += str(preference)
                preferencesstring += " "
            preferencesstring = preferencesstring[:-1]
            f.write(preferencesstring+"\n")
        f.close()
            
                
            
            
            
            
            
        
    
    
