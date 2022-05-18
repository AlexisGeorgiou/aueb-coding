from operator import itemgetter
tMax = 500


def bestResponseDynamics(file_name):
    #Open file
    with open("{}.txt".format(file_name),'r') as f:
        #Load content
        content = f.readlines()
        n = int(content[0]) #Number of players
        m = int(content[1]) #Number of choices
        rule = content[2][:-1] #Can be Plurality or Borda
        playerspreferences = []
        for i in range(n):
            playerspreferences += [[int(x) for x in content[i+3].split()]]

        f.close()
    


    #Find Nash equilibrium for each rule
    if rule == "Plurality":
        #Have a list to track which player voted for who
        votedFor = []
        for player in range(n):
            votedFor.append(playerspreferences[player][0])

        
        #Get the first leaderboarder
        leaderboarder = [[-1,-1]]
        for i in range(m):
            #It's a list of lists and we initialize the score of each choice [[1,0],[2,0],...]
            #leaderboarder looks like this [[-1,-1],[1, 0], [2, 0], [3, 0], [4, 0], [5, 0], [6, 0]] #[-1,-1] is a dummy element to line up the position with the choice id
            leaderboarder.append([i+1,0])

        #For every player, increase the votes of their first preference by 1
        for playerspreference in playerspreferences: #playerspreferences looks like this [[1, 5, 3, 2, 4, 6], [2, 6, 1, 3, 4, 5],... ]
            leaderboarder[playerspreference[0]][1] += 1

        
        turns = 0
        equilibriumFound = False
        while turns <= tMax and equilibriumFound == False:
            if tMax == turns:
                print("Voted for: ", votedFor)
                print("Leaderboarder: ", leaderboarder[1:])
                print("Turns: ", tMax)
                return [equilibriumFound, turns]
                break
#Check the game turn by turn
##            print("Turn: ", turns)
##            print("Current votings: ", votedFor)
##            print("Leaderboarder: ", leaderboarder[1:])
            turns += 1
            #Winner has the id of the "current" choice with most votes
            #Finding the most votes then tiebreaking by smaller id
            winnervotes = sorted(leaderboarder, key=itemgetter(1), reverse=True)[0][1]
            winnerid = 100000
            for choice in range(m+1):
                if winnervotes == leaderboarder[choice][1] and leaderboarder[choice][0] <= winnerid:
                    winnerid = leaderboarder[choice][0]

            changedAVote = False
            
            for player in range(n):
                #If we changed any votes during the turn, we have to restart the check from the first player
                if changedAVote == True:
                    break
                
                else:
                    #We check if the player has a reason to change his strategy
                    #For each of the rest preferences of the player we check if it can be a winner
                    for playerpreferenceid in playerspreferences[player]:
                        #Virtually removes the vote to check if there is a better vote
                        leaderboarder[votedFor[player]][1] -= 1
                        #If the preference had lower id than winnerid then it wins on tie so we need the vote count to be strictly lower
                        if playerpreferenceid < winnerid and leaderboarder[playerpreferenceid][1] + 1 < leaderboarder[winnerid][1]:
                            #Restore vote
                            leaderboarder[votedFor[player]][1] += 1
                            continue
                        #If the preference has higher id than winnerid then it can't win on a tie so we need the vote count to be lower or equal
                        elif playerpreferenceid > winnerid and leaderboarder[playerpreferenceid][1] + 1 <= leaderboarder[winnerid][1]:
                            #Restore vote
                            leaderboarder[votedFor[player]][1] += 1
                            continue
                        #The most preferred winnable option is winning, player stops looking for better strategy
                        elif playerpreferenceid == winnerid:
                            leaderboarder[votedFor[player]][1] += 1
                            break
                            
                        #If it can win change the vote
                        else:
                            #The removal of the vote stays as it is
                            changedAVote = True
                            #Vote for the choice we can win
                            votedFor[player] = playerpreferenceid
                            leaderboarder[playerpreferenceid][1] += 1
                            break
                #If nothing changed during the whole turn, we found our equilibrium, we will break the while
                if player == n-1 and changedAVote == False:
                    print("Got equilibrium at turn: ", turns)
                    print("Voted for: ", votedFor)
                    print("Leaderboarder: ", leaderboarder[1:])
                    equilibriumFound = True
                    return [equilibriumFound, turns]
                    break
    if rule == "Borda":
        #Have a list to track which player voted for who
        votedFor = playerspreferences 

        #Get the first leaderboarder
        leaderboarder = [[-1,-1]]
        for i in range(m):
            #It's a list of lists and we initialize the score of each choice [[1,0],[2,0],...]
            #leaderboarder looks like this [[-1,-1],[1, 0], [2, 0], [3, 0], [4, 0], [5, 0], [6, 0]] #[-1,-1] is a dummy element to line up the position with the choice id
            leaderboarder.append([i+1,0])

        #For every player, increase the votes of their first preference by 1
        for playerspreference in playerspreferences: #playerspreferences looks like this [[1, 5, 3, 2, 4, 6], [2, 6, 1, 3, 4, 5],... ]
            for i in range(m):
                #Each choice gets m- i points, the first one will get m points the second one will get m-1 and last will get 1 point
                leaderboarder[playerspreference[i]][1] += m - i
            
        
        turns = 0
        equilibriumFound = False
        while turns <= tMax and equilibriumFound == False:
            if tMax == turns:
                print("Voted for: ", votedFor)
                print("Leaderboarder: ", leaderboarder[1:])
                print("Turns: ", tMax)
                return [equilibriumFound, turns]
                break
#Check the game turn by turn
##            print("Turn: ", turns)
##            print("Current votings: ", votedFor)
##            print("Leaderboarder: ", leaderboarder[1:])
            turns += 1
            #Winner has the id of the "current" choice with most votes
            #Finding the most votes then tiebreaking by smaller id
            winnervotes = sorted(leaderboarder, key=itemgetter(1), reverse=True)[0][1]
            winnerid = 100000
            for choice in range(m+1):
                if winnervotes == leaderboarder[choice][1] and leaderboarder[choice][0] <= winnerid:
                    winnerid = leaderboarder[choice][0]

            changedAVote = False
            
            for player in range(n):
                #If we changed any votes during the turn, we have to restart the check from the first player
                if changedAVote == True:
                    break
                
                else:
                    #We check if the player has a reason to change his strategy
                    #For each of the rest preferences of the player we check if it can be a winner
                    for playerpreferenceid in playerspreferences[player]:
                        #Virtually removes the vote to check if there is a better vote
                        for i in range(m):
                            leaderboarder[votedFor[player][i]][1] -= m - i
                        #If the preference had lower id than winnerid then it wins on tie so we need the vote count to be strictly lower
                        if playerpreferenceid < winnerid and leaderboarder[playerpreferenceid][1] + m - 1 < leaderboarder[winnerid][1]:
                            #Restore vote
                            for i in range(m):
                                leaderboarder[votedFor[player][i]][1] += m - i
                            continue
                        #If the preference has higher id than winnerid then it can't win on a tie so we need the vote count to be lower or equal
                        elif playerpreferenceid > winnerid and leaderboarder[playerpreferenceid][1] + m - 1 <= leaderboarder[winnerid][1]:
                            #Restore vote
                            for i in range(m):
                                leaderboarder[votedFor[player][i]][1] += m - i
                            continue
                        #The most preferred winnable option is winning, player stops looking for better strategy
                        elif playerpreferenceid == winnerid:
                            for i in range(m):
                                leaderboarder[votedFor[player][i]][1] += m - i
                            break
                            
                        #If it can win change the vote
                        else:
                            #The removal of the vote stays as it is
                            changedAVote = True
                            #Vote for the choice we can win
                            #We shift the strategy we want to first vote and the strategy that was winning to last place
                            # new_votes = [playerpreferenceid, usedtobefirstchoice, usedtobesecondchoice, ... , winningchoice]
                            new_votes = votedFor[player]
                            new_votes.remove(winnerid)
                            new_votes.remove(playerpreferenceid)
                            new_votes.append(winnerid) #add it in last
                            new_votes.insert(0, playerpreferenceid) #add it in front
                            votedFor[player] = new_votes
                            for i in range(m):
                                leaderboarder[votedFor[player][i]][1] += m - i
                            break
                #If nothing changed during the whole turn, we found our equilibrium, we will break the while
                if player == n-1 and changedAVote == False:
                    print("Got equilibrium at turn: ", turns)
                    print("Voted for: ", votedFor)
                    print("Leaderboarder: ", leaderboarder[1:])
                    equilibriumFound = True
                    return [equilibriumFound, turns]
                    break

#Uncomment print lines both in function and in the below code to see the results file by file

#Plurality
equilibriumturnsum = 0
equilibriumturncount = 0
totalfiles = 360
for i in range(1,totalfiles + 1):
    file_name = "Plurality_{}".format(str(i))
    print("\nRunning file {}".format(file_name))
    print("===================")
    results = bestResponseDynamics(file_name)
    if results[0] == True:
        equilibriumturncount += 1
        equilibriumturnsum += results[1]
        
print("Percentage of equilibrium convergence: ", 100* equilibriumturncount/totalfiles) 
print("Average turns for equilibrium: ", equilibriumturnsum/equilibriumturncount)


#Borda
equilibriumturnsum = 0
equilibriumturncount = 0
totalfiles = 360
for i in range(1,totalfiles + 1):
    file_name = "Borda_{}".format(str(i))
    print("\nRunning file {}".format(file_name))
    print("===================")
    results = bestResponseDynamics(file_name)
    if results[0] == True:
        equilibriumturncount += 1
        equilibriumturnsum += results[1]
        
print("Percentage of equilibrium convergence: ", 100* equilibriumturncount/totalfiles) 
print("Average turns for equilibrium: ", equilibriumturnsum/equilibriumturncount)

