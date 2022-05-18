from coordinates import coordinates, distance, coordinates_to_str

#----------------------- Askisi 9 --------------------------

def location(name, cors):
    """Kataskeuazei syn8eto dedomeno topo8esias (location).

    name -- onoma (str)
    cors -- syntetagmenes (typou coordinates)

    Epistrefei dedomeno pou anaparista tin topo8esia me onoma name 
    kai syntetagmenes pou didontai stin cors.
    """
    return (name,cors)



def get_coordinates(loc):
    """Epistrefei tis syntetagmenes mias topo8esias.

    loc -- topo8esia (typou location)
    
    Epistrefei tis syntetagmenes (se dedomeno typou coordinates) tis topo8esias loc.

    >>> loc = location('Monastiraki', coordinates(37.976362, 23.725947))
    >>> coordinates_to_str(get_coordinates(loc))
    '37.976362, 23.725947'
    """
    return loc[1]


def get_name(loc):
    """Epistrefei to onoma mias topo8esias.

    loc -- topo8esia (typou location)
    
    Epistrefei to onoma (str) tis topo8esias loc.

    >>> loc = location('Monastiraki', coordinates(37.976362, 23.725947))
    >>> get_name(loc)
    'Monastiraki'
    """
    return str(loc[0])


def location_to_str(loc):
    """Epistrefei string pou perigrafei mia topo8esia.

    loc -- topo8esia (typou location)
    
    Epistrefei to str pou perigrafei tin topo8esia loc.

    >>> loc = location('Monastiraki', coordinates(37.976362, 23.725947))
    >>> location_to_str(loc)
    'Monastiraki: 37.976362, 23.725947'
    """
    return get_name(loc) + ': ' + coordinates_to_str(get_coordinates(loc))



#------------------------- Askisi 10 -------------------------

def map(loc_list):
    """Kataskeuazei syn8eto dedomeno xarth (map).

    loc_list -- lista me topo8esies (typou location)

    Epistrefei dedomeno pou anaparista gewgrafiko xarth poy apoteleitai apo
    tis topo8esies sti loc_list.
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    ddmap = {}
    for loc in loc_list:
        ddmap[get_name(loc)] = loc
    return ddmap
    
def get_location(name, map):
    """Epistrefei tin topo8esia (typou location) basei onomatos.

    name -- onoma topo8esias (str)
    map -- xarths (map)

    Epistrefei tin topo8esia (dedomeno typou location) me onoma name ston xarth map.
    """
    return map[name]


def nearby_locations(loc, map, radius=1):
    """Epistrefei topo8esies tou xarth pou einai kontines se mia allh.

    loc -- topo8esia (location) opou 8eloume na broume tis kontines
    map -- xarths (map)
    radius -- aktina se xiliometra. Proka8orismenh timh = 1 km

    Epistrefei lista me tis topo8esies (location) tou xarth map pou briskontai se
    apostash mikroterh 'h ish tou radius apo simeio me syntetagmenes cors.

    >>> a = location('A', coordinates(0,0))
    >>> b = location('B', coordinates(1, 2))
    >>> loc = location('X', coordinates(0.1, 0.1))
    >>> m = map([a, b])
    >>> [get_name(loc) for loc in nearby_locations(loc, m, 10)]
    []
    >>> [get_name(loc) for loc in nearby_locations(loc, m, 100)]
    ['A']
    >>> [get_name(loc) for loc in nearby_locations(loc, m, 300)]
    ['A', 'B']
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    new_li = []
    for i in map:
         if distance(get_coordinates(loc), get_coordinates(map[i])) <= radius:
             new_li.append(i)
    return new_li
 
    

def print_nearby_locations(loc, map, radius=1):
    """Emfanizei kontines topo8esies tou xarth.

    loc -- topo8esia (location) opou 8eloume na broume tis kontines
    map -- xarths (map)
    radius -- aktina se xiliometra. Proka8orismenh timh = 1 km

    Emfanizei tis topo8esies (location) tou xarth map pou briskontai se
    apostash mikroterh 'h ish tou radius apo simeio me syntetagmenes cors.

    >>> a = location('A', coordinates(0,0))
    >>> b = location('B', coordinates(1, 2))
    >>> loc = location('X', coordinates(0.1, 0.1))
    >>> m = map([a, b])
    >>> print_nearby_locations(loc, m, 10)
    >>> print_nearby_locations(loc, m, 100)
    A: 0, 0
    >>> print_nearby_locations(loc, m, 300)
    A: 0, 0
    B: 1, 2
    """
    """GRAPSTE TON KWDIKA SAS EDW."""
    for i in map:
         if distance(get_coordinates(loc), get_coordinates(map[i])) <= radius:
            print(location_to_str(map[i]))


