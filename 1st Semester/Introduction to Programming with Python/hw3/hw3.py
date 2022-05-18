
#--------------------- Askisi 1 -----------------------------

def pick_cherries_onebyone():
    """Emfanizei string pou briskontai se fwliasmenes listes.

    Prepei na exei to akolou8o apotelesma:

    >>> pick_cherries_onebyone()
    cherry1
    cherry2
    cherry3
    cherry4
    Yay!!!
    """
    cherry_field = ['cherry1', ['cherry2', ['cherry3', ['cherry4', ['Yay!!!', None]]]]]

    print(cherry_field[0])
    print(cherry_field[1][0])
    print(cherry_field[1][1][0])
    print(cherry_field[1][1][1][0])
    print(cherry_field[1][1][1][1][0])


    
#--------------------- Askisi 2 -----------------------------

def pick_cherries_iter(field):
    """Emfanizei string pou briskontai se fwliasmenes listes.

    field -- lista me fwliasmena string. Ka8e lista exei dyo stoixeia: to prwto einai
             string kai to deutero einai eite lista ths idias morfhs 'h None. 
             (Opws kai h cherry_field sto swma ths synarthshs pick_cherries_onebyone().

    Paradeigmata:

    >>> cherry_field = ['cherry1', ['cherry2', ['cherry3', ['cherry4', ['Yay!!!', None]]]]]
    >>> pick_cherries_iter(cherry_field)
    cherry1
    cherry2
    cherry3
    cherry4
    Yay!!!
    >>> pick_cherries_iter(['Hello', ['world', None]])
    Hello
    world
    """
    #den tiri akrivos ta kena i lisi
    while field[1] != None:
        print(field[0])
        field = field[1]
    print(field[0])
        


#--------------------- Askisi 3 -----------------------------

def pick_cherries_rec(field):
    """Emfanizei string pou briskontai se fwliasmenes listes.

    field -- lista me fwliasmena string. Ka8e lista exei dyo stoixeia: to prwto einai
             string kai to deutero einai eite lista ths idias morfhs 'h None. 
             (Opws kai h cherry_field sto swma ths synarthshs pick_cherries_onebyone().

    Anadromiki synarthsh: den epitrepetai na xrhsimopoihsete entoles epanalhpshs
    (for, while) 'h list comprehension.

    Paradeigmata:

    >>> cherry_field = ['cherry1', ['cherry2', ['cherry3', ['cherry4', ['Yay!!!', None]]]]]
    >>> pick_cherries_rec(cherry_field)
    cherry1
    cherry2
    cherry3
    cherry4
    Yay!!!
    >>> pick_cherries_rec(['Hello', ['world', None]])
    Hello
    world
    """
    """ GRAPSTE TON KWDIKA SAS APO KATW. XRHSIMOPOIHSTE ANADROMH.
    OXI FOR, WHILE, LIST COMPREHENSIONS."""

    print(field[0])
    if field[1] != None:
        pick_cherries_rec(field[1])

        
#--------------------- Askisi 4 -----------------------------

def pick_cherries_proc(field):
    """Emfanizei string pou briskontai se fwliasmenes listes.

    field -- lista me fwliasmena string. Ka8e lista exei dyo stoixeia: to prwto einai
             string kai to deutero einai eite lista ths idias morfhs 'h None. 
             (Opws kai h cherry_field sto swma ths synarthshs pick_cherries_onebyone().

    Paradeigmata:

    >>> cherry_field = ['cherry1', ['cherry2', ['cherry3', ['cherry4', ['Yay!!!', None]]]]]
    >>> pick_cherries_proc(cherry_field)
    cherry1
    cherry2
    cherry3
    cherry4
    Yay!!!
    >>> pick_cherries_proc(['Hello', ['world', None]])
    Hello
    world
    """
    """ GRAPSTE TON KWDIKA SAS APO KATW. XRHSIMOPOIHSTE ANADROMH.
    OXI FOR, WHILE, LIST COMPREHENSIONS."""

    def flatten(ls):
        flattened_list = []
        while ls[1] != None:
            flattened_list.append(ls[0])
            ls = ls[1]
            
        flattened_list.append(ls[0])
        return flattened_list
        
    [print(x) for x in flatten(field)]
        


#--------------------- Askisi 5 -----------------------------

def korakize(sentence):
    """Metafrazei sta korakistika.

    sentence -- string pou 8a metafrastei
   
    Epistrefei string metafrasmeno sta korakistika.

    Paradeigmata:

    >>> korakize('Kalimera')
    'Kakalikamekaraka'
    >>> korakize('Maria')
    'Makarikaaka'
    >>> korakize('Kalimera Maria')
    'Kakalikamekaraka Makarikaaka'
    >>> korakize('Na zi kanis i na min zi')
    'Naka zika kakanikaska ika naka mikanka zika'
    """
    """GRAPSTE TON KWDIKA SAS APO KATW."""
    output = ''
    for word in sentence.split():
        new_word = ''
        for a in range(0,len(word),2):
            if a == len(word) - 1:
                new_word += word[a] + 'ka'
            else:
                new_word +=  word[a] + word[a+1] + 'ka'
        
        output += new_word + ' '
            
    return output[:len(output)-1]

        
            


#--------------------- Askisi 6 -----------------------------

def dekorakize(sentence):
    """Metafrazei apo korakistika.

    sentence -- string sta korakistika pou 8a metafrastei
   
    Epistrefei string. Einai h antistrofh synarthsh ths korakize.

    Paradeigmata:

    >>> dekorakize('Kakalikamekaraka')
    'Kalimera'
    >>> dekorakize('Makarikaaka')
    'Maria'
    >>> dekorakize('Kakalikamekaraka Makarikaaka')
    'Kalimera Maria'
    >>> dekorakize('Naka zika kakanikaska ika naka mikanka zika')
    'Na zi kanis i na min zi'
    """
    """GRAPSTE TON KWDIKA SAS APO KATW."""
    output = ''
    for word in sentence.split():
        new_word = ''
        for a in range(0,len(word),4):
                if a == len(word) - 3:
                    new_word += word[a]
                else:
                    new_word += word[a] + word[a+1]
        output += new_word + ' '

    return output[:len(output)-1]

