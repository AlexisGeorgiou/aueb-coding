def kremala(word, hit):
    """
    Παράγει την λέξη με παύλες και τα γράμματα της ανάλογα με την αντίστοιχη 0-1 λέξη
    >>> word = 'alex',
    >>> hit = '0' * len(word)
    >>> kremala(word, hit)
    ' Η λέξη που πρέπει να μαντέψεις είναι: _  _  _  _ '
    
    >>> word = 'alex'
    >>> hit = '1011'
    >>> kremala(word, hit)
    ' Η λέξη που πρέπει να μαντέψεις είναι: a  _  e  x '
    
    """
    a = ' Η λέξη που πρέπει να μαντέψεις είναι:'
    for i in range(0,len(word)):
        if hit[i] == '0':
            a += ' _ '
        elif hit[i] == '1':
            a += ' ' + word[i] + ' '
    return a


def randomword(word_list, used_words):
    """
    Παίρνει μια τυχαία λέξη η οποία δεν έχει ξαναεπιλεχθεί
    >>> word_list = ['a','b']
    >>> used_words = ['a']
    >>> randomword(word_list, used_words)
    'b'
    
    """
    import random
    F = False
    while F == False:
          word = random.choice(word_list)
          if word not in used_words:
              F = True
              return word


class Player:
    def __init__(self, name, n):
        self.name = name
        self.n = n
        self.wins = False
    def get_life(self):
        if self.level == 'easy':
            self.lifes = 8
        elif self.level == 'hard':
            self.lifes = 4
        else:
            self.lifes = 6
        return self.lifes
    def lose_life(self):
        self.lifes -= 1
    def last_round_won(self):
        self.wins = True
    def last_round_lost(self):
        self.wins = False
    def difficulty_level(self, level):
        self.level = level
        return self.level
    def __str__(self):
        return self.name
    
        
figureslisteasy = [
"|---------|\n|          Ο\n|\n|\n|\n|\n|",
"|---------|\n|          Ο\n|           |   \n|\n|\n|\n|",
"|---------|\n|          Ο\n|         / |   \n|           | \n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           | \n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/   \n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|      ## ##\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|      ## ##\n|         fire"
]            

figureslistmedium = [
"|---------|\n|          Ο\n|\n|\n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|\n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           | \n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|      ## ##\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|      ## ##\n|         fire"
]

figureslisthard = [
"|---------|\n|          Ο\n|\n|\n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           | \n|\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|\n|",
"|---------|\n|          Ο\n|         / | \ \n|           |\n|        _/ \_\n|      ## ##\n|         fire"
]


   
np= int(input('Εισάγετε τον αριθμό παιχτών: '))
word_list = ['highfalutin', 'scene', 'men', 'powder', 'snakes', 'wonderful', 'perform', 'command', 'calculator', 'careless', 'mean', 'frog', 'snotty', 'jar', 'uttermost', 'advertisement', 'beneficial',
             'rings', 'needle', 'unit', 'attract', 'inexpensive', 'cars', 'swift', 'hope', 'scratch', 'complex', 'clammy', 'approval', 'request', 'pricey', 'imaginary', 'ubiquitous', 'party', 'muscle',
             'sun', 'roof', 'lopsided', 'hose', 'prepare', 'male', 'sail', 'melt', 'psychotic', 'impossible', 'roll', 'temper', 'insect', 'mass', 'tickle']
used_words = []
letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
players = []
winners = players

for i in range(0, np):
    players.append(Player(input('Εισάγετε το όνομα του παίχτη {0}: '.format(i+1)), i+1))
        

    
while len(winners) != 0:
    if len(winners) == 1 and winners[0].wins == True:
        break
    winners2 = winners.copy()
    for i in winners2:
        i.difficulty_level(input("{}, επιλέχτε επίπεδο δυσκολίας (easy, medium, hard): ".format(str(i))))
        word = randomword(word_list, used_words)
        used_words.append(word)
        hit = '0' * len(word)
        i.get_life()
        print("\n\n{}, έχετε περιθώριο να επιλέξετε μέχρι {} λάθος γράμματα, το {}ο λάθος σας βγάζει εκτός παιχνιδιού.".format(str(i), i.lifes - 1, i.lifes))
        
        while i.lifes > 0:
            print(kremala(word, hit))
            guess = str(input(' Δώσε γράμμα: ')).lower()
            
            while guess not in letters:
                guess = str(input(' Δώσε γράμμα: ')).lower()
                
            if guess in word:
                for j in range(len(word)):
                    if guess == word[j]:
                        hit = hit[:j] + '1' + hit[j+1:]
                    
            else:
                i.lose_life()
                if i.level == 'easy':
                    print(figureslisteasy[7 - i.lifes])
                elif i.level == 'hard':
                    print(figureslisthard[3 - i.lifes])
                else: ## i.level == 'medium': (Default level is medium with 6 lifes)
                    print(figureslistmedium[5 - i.lifes])
                if i.lifes == 0:
                    print("Η λέξη που ψάχναμε ήταν: " + word + ".\n\n")
                    i.last_round_lost()
                    winners.remove(i)
                if i.lifes != 0:
                    print("Έχεις ακόμα {} ζωές.".format(i.lifes))
                  
            if '0' not in hit:
                print("Κέρδισες! Η λέξη ήταν: " + word + ".\n\n")
                i.last_round_won()
                break
try:
    if winners[0].wins == True:
        print('Νικητής παιχνιδιού ο/η: ', str(winners[0]))            
except:
    print('Κανένας Νικητής')   

            
#Bonus 2
"""
Προφανώς ο χρόνος αυξάνεται ανάλογα τον αριθμό των παιχτών,
Το πρόγραμμα ελέγχει για κάθε καινούργια λέξη αν έχει παιχτεί η ιδιά λέξη ξανά, για κάθε γράμμα που δίνει ο παίχτης ελέγχει όλα τα γράμματα της λέξης ενταμένα και σημειώνει την θέση τους αν είναι σωστά. Το επίπεδο δυσκολίας ναι μεν προσθέτει έναν ακόμα έλεγχο αλλά
σε περίπτωση που επιλεχτεί το δύσκολο επίπεδο (με τις λιγότερες ζωές) γλιτώνουμε πολλούς ελέγχους και εντολές αφού θα έπρεπε για κάθε εξτρά ζωή να ελέγχονται όλα τα γράμματα της λέξης. Στο medium επίπεδο η εντολές που εκτελούνται είναι οι ιδίες με προσθήκη τον έλεγχο
του ιδίου του επιπέδου ενώ στο East ο παίχτης έχει παραπάνω ζωές με αποτέλεσμα να εκτελείτε η επανάληψη παραπάνω φορές. Ο "χρόνος" αυτός εξαρτάται από τον αριθμό των γυρών ο οποίος δεν μπορεί να ξεπεράσει τον αριθμό (50//np) λόγω έλλειψης λέξεων.
Επίσης έχουμε και τις εντολές input αριθμού παιχτών, ονομάτων, επιπέδου (σε κάθε γύρο).
Τα παραπάνω ισχύουν για μια σωστή χρήση του πρόγραμμα με βάση την λογική του παιχνιδιού (θα μπορούσαμε για παράδειγμα να δίνουμε συνέχεια το ίδιο γράμμα της λέξης συνέχεια και να μην τελειώσει ποτέ το πρόγραμμα).
Στην χειρότερη περίπτωση παίζουμε και τις 50 λέξεις, με 50 παίχτες να κερδίζουν στην τελευταία τους ζωή στο εύκολο επίπεδο με 8 ζωές.

"""
