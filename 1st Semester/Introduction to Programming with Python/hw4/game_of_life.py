"""Conway\'s Game of Life."""

#---------------------- Askisi 1 ------------------------
def board(n):
    """Kataskeuastis board (pinaka paixnidiou).

    n -- parametros diastasis pinaka

    Kataskeuazei anaparastasi pinaka paixnidiou me n x n kelia, opou
    kanena keli den einai zwntano.

    Epistrefei apaparastasi pinaka paixnidiou, h opoia einai
    ena le3iko (dict) me n*n stoixeia.
    Ka8e keli antistoixei se ena stoixeio me key to tuple (i,j), opou
    i o ari8mos grammis kai j o ari8mos stilis pou brisketai to keli. 
    (H ari8misi grammwn kai sthlwn einai apo 0 ews n-1. To panw aristera keli
    brisketai sto (0,0).)
    H timi (value) tou stoixeiou einai True 'h False analoga 
    ean to keli einai zwntano 'h oxi.

    Paradeigmata:

    >>> game = board(3)
    >>> len(game)
    9
    >>> game
    {(0, 0): False, (0, 1): False, (0, 2): False, (1, 0): False, (1, 1): False, (1, 2): False, (2, 0): False, (2, 1): False, (2, 2): False}
    >>> game[2,1]
    False
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    ddgame = {}
    for i in range(0,n):
        for j in range(0,n):
            ddgame[(i,j)] = False
    return ddgame
            


#---------------------- Askisi 2 ------------------------
def is_alive(board, p):
    """Elegxei ean ena keli einai zwntano.

    board -- o pinakas paixnidiou opou brisketai to keli
    p -- h 8esh tou keliou.

    To orisma p einai tuple tis morfis (i,j).
    Epistrefei True ean to keli einai zwntano, alliws False.

    Paradeigma:

    >>> is_alive(board(4), (3,2))
    False
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    return board[p]
    


def set_alive(board, p, alive):
    """Topo8etei 'h afairei zwi apo ena keli.

    board -- o pinakas paixnidiou opou brisketai to keli
    p -- h 8esh tou keliou (tuple tis morfis (i,j))
    alive -- logiki timi.

    To keli ginetai zwntano ean h alive einai True. An h alive einai False,
    to keli pe8ainei.

    Paradeigmata:

    >>> game = board(4)
    >>> is_alive(game, (3,2))
    False
    >>> set_alive(game, (3,2), True)
    >>> is_alive(game, (3,2))
    True
    >>> set_alive(game, (3,2), False)
    >>> is_alive(game, (3,2))
    False
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    if p[0] <= get_size(board) - 1 and p[1] <= get_size(board) - 1:
        board[p] = alive

    
def get_size(board):
    """Mege8os pinaka paixnidiou.

    board -- pinakas paixnidiou.

    Epistrefei n ean to board anaparista pinaka paixnidiou n x n.

    Paradeigmata:

    >>> get_size(board(4))
    4
    >>> get_size(board(10))
    10
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    from math import sqrt
    return int(sqrt(len(board)))


#---------------------- Askisi 3 ------------------------
def copy_board(board):
    """Antigrafo pinaka paixnidiou.

    board -- pinakas paixnidiou.

    Epistrefei ena neo pinaka paixnidiou pou einai antigrafo tou board.

    Paradeigmata:

    >>> game = board(10)
    >>> set_alive(game, (4,7), True)
    >>> game2 = copy_board(game)
    >>> game2 is game
    False
    >>> is_alive(game2, (4,7))
    True
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    return board.copy()


#---------------------- Askisi 4 ------------------------
def get_iterator(board):
    """Iterator gia sarwsi stoixeiwn pinaka paixnidiou.

    board -- pinakas paixnidiou.

    Epistrefei iterator pou dinei ta kelia tou board arxizontas apo 
    ta kelia tis grammis 0: (0,0), (0,1), (0,2),..., meta akolou8oun ta 
    kelia tis grammis 1: (1,0), (1,1), (1,2),... ktl. mexri na e3antli8oun
    ola ta kelia. Gia ka8e keli, o iterator epistrefei tin 8esi tou kai
    logiki timi True 'h False analogws ean einai zwntano 'h oxi.

    Paradeigma:

    >>> game = board(3)
    >>> set_alive(game, (2, 1), True)
    >>> for cell in get_iterator(game):
    ...     print(cell)
    ... 
    ((0, 0), False)
    ((0, 1), False)
    ((0, 2), False)
    ((1, 0), False)
    ((1, 1), False)
    ((1, 2), False)
    ((2, 0), False)
    ((2, 1), True)
    ((2, 2), False)
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    li = []
    for key in board:
        li.append((key,board[key]))
    return li
    


#---------------------- Askisi 5 ------------------------
def print_board(board):
    """Emfanizei pinaka paixnidiou.

    board -- pinakas paixnidiou.

    Emfanizei ton pinaka paixnidiou board opou me mauro tetragwno 
    (xaraktiras unicode 11035) dinontai ta zwntana kelia, kai
    me aspro (xaraktiras unicode 11036) ta pe8amena. 
    To panw aristera keli einai auto sti 8esi (0,0).

    Paradeigma:

    >>> game = board(5)
    >>> set_alive(game, (0,0), True)
    >>> set_alive(game, (2,2), True)
    >>> set_alive(game, (4,4), True)
    >>> set_alive(game, (3,4), True)
    >>> set_alive(game, (0,4), True)
    >>> print_board(game)
    ⬛⬜⬜⬜⬛
    ⬜⬜⬜⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    ⬜⬜⬜⬜⬛
    """ 
    """GRAPSTE TON KWDIKA SAS EDW."""   
    n = get_size(board)
    i = 0
    line = ''
    for cell in get_iterator(board):
        if cell[1] == True:
            line += u'\u2b1b'
        elif cell[1] == False:
            line += u'\u2b1c'
        i += 1
        if i % n == 0:
            print(line, end = '\r')
            line = ''
        


#---------------------- Askisi 6 ------------------------
def neighbors(p):
    """Geitonika kelia.

    p -- 8esh keliou (tuple tis morfis (i,j)).

    Epistrefei synolo (set) pou periexontai oi 8eseis twn 8 geitonikwn
    keliwn tou p. Sto epistrefomeno synolo den periexetai to keli p.

    Paradeigmata:

    >>> neighbors((3,2))
    {(3, 3), (3, 1), (2, 1), (2, 3), (4, 3), (2, 2), (4, 2), (4, 1)}
    >>> neighbors((0,0))
    {(0, 1), (-1, 1), (-1, 0), (-1, -1), (0, -1), (1, 0), (1, -1), (1, 1)}
    >>> neighbors((0,10))
    {(-1, 9), (1, 10), (-1, 11), (0, 11), (-1, 10), (1, 9), (0, 9), (1, 11)}
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    neig = set()
    for i in range(p[0] - 1, p[0] + 2):
        for j in range(p[1] - 1, p[1] + 2):
            neig.add((i, j))
    neig.remove(p)
    return neig


#---------------------- Askisi 7 ------------------------
def place_blinker(board, p = (0,0)):
    """Topo8etisi blinker.

    board -- pinakas paixnidiou.
    p -- keli topo8etisis (tuple (i,j) me proka8orismeni timi (0,0))

    Topo8etei 3 zwntanous organismous sto board 
    se geitonika kelia tis idias stilis, arxizontas apo ti 8esi p kai
    proxwrontas stis parakatw grammes.

    Paradeigmata:
    
    >>> game = board(5)
    >>> place_blinker(game)
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (1,2))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬜
    >>> place_blinker(game, (4,4))
    >>> print_board(game)
    ⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜
    ⬛⬜⬛⬜⬜
    ⬜⬜⬛⬜⬜
    ⬜⬜⬜⬜⬛
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    x, y = p[0], p[1]
    for i in range(0,3):
        set_alive(board, (x+i, y), True)


def place_glider(board, p = (0,0)):
    """Topo8etisi glider.

    board -- pinakas paixnidiou.
    p -- keli topo8etisis (tuple (i,j) me proka8orismeni timi (0,0))

    Topo8etei 5 zwntanous organismous sto board se sximatismo glider
    arxizontas apo tin topo8esia p, opws fainetai sta parakatw paradeigmata. 
    Simeiwste oti to idio to keli p den einai zwntano.
    
    Paradeigmata:

    >>> game = board(7)
    >>> place_glider(game)
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬜⬜
    >>> place_glider(game, (3,3))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜⬜
    ⬜⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬛⬜⬛⬜
    ⬜⬜⬜⬜⬛⬛⬜
    ⬜⬜⬜⬜⬜⬜⬜
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    x, y = p[0], p[1]
    place_blinker(board, (x, y+2))
    set_alive(board, (x+1, y), True)
    set_alive(board, (x+2, y+1), True)


#---------------------- Askisi 8 ------------------------
def tick(board):
    """Proxwraei to paixnidi kata ena bima stin epomeni genea.

    board -- pinakas paixnidiou.

    Allazei ton pinaka board proxwrontas mia genea, 
    symfwna me tous kanones tou Game of Life.

    Paradeigma:

    >>> game = board(6)
    >>> place_glider(game)
    >>> place_blinker(game, (3,4))
    >>> print_board(game)
    ⬜⬜⬛⬜⬜⬜
    ⬛⬜⬛⬜⬜⬜
    ⬜⬛⬛⬜⬜⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    ⬜⬜⬜⬜⬛⬜
    >>> tick(game)
    >>> print_board(game)
    ⬜⬛⬜⬜⬜⬜
    ⬜⬜⬛⬛⬜⬜
    ⬜⬛⬛⬛⬜⬜
    ⬜⬜⬜⬛⬜⬜
    ⬜⬜⬜⬛⬛⬛
    ⬜⬜⬜⬜⬜⬜
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    board2 = copy_board(board)
    for cell in get_iterator(board2):
        alive_neig = 0
        for neig in neighbors(cell[0]):
            if neig[0] >= 0 and neig[1] >= 0 and neig[0] <= get_size(board2) - 1 and neig[1] <= get_size(board2) - 1:
                if is_alive(board2, neig):
                    alive_neig += 1
        if is_alive(board2, cell[0]):
            if alive_neig <= 1 or alive_neig >= 4:
                set_alive(board, cell[0], False)
        elif is_alive(board2, cell[0]) == False and alive_neig == 3:
             set_alive(board, cell[0], True)



            
            
            
    


#---------------------- Askisi 9 ------------------------

if __name__ == '__main__':
   """Paizei to paixnidi gia mia sygkekrimeni arxiki topo8etisi, gia 
   100 genιes. O pinakas tou paixnidiou emfanizetai se  ka8e bima.
   Afiste toulaxiston 2 kenes grammes anamesa se diadoxikous pinakes.
   """

   # Arxikos pinakas
   game = board(10)
   place_blinker(game, (1,2))
   place_glider(game, (2,4))

   from time import sleep

   """GRAPSTE TON KWDIKA SAS APO KATW."""
   for i in range(0,100):
      print_board(game)
      print('\n\n')
      sleep(1)
      tick(game)

