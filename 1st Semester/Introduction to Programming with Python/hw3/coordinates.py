from math import cos, pi
_lat_deg = 111
_lon_deg = 111*cos(_lat_deg * pi / 180)


#---------------------- Askisi 7 -----------------------

def coordinates(lon, lat):
    """Kataskeuastis tou dedomenou coordinates (syntetagmenes).

    lon -- gewgrafiko mikos (se moires)
    lat -- gewfrafiko platos (se moires)

    Epistredfei dedomeno coordinates (syntetagmenes).
    """
    return [lon, lat]


def longitude(cors):
    """Gewgrafiko mikos.

    cors -- dedomeno coordinates

    Epistrefei gewgrafiko mikos twn syntetagmenvn cors.

    >>> longitude(coordinates(23.1789, 34.563))
    23.1789
    """
    return cors[0]


def latitude(cors):
    """Gewgrafiko platos.

    cors -- dedomeno coordinates

    Epistrefei gewgrafiko platos twn syntetagmenvn cors.

    >>> latitude(coordinates(23.1789, 34.563))
    34.563
    """
    return cors[1]


#---------------------- Askisi 8 -----------------------

def distance(a, b):
    """Apostasi meta3y syntetagmenwn.

    a -- syntetegmenes simeiou A (typou coordinates)
    b -- syntetegmenes simeiou B (typou coordinates)

    Epistrefei thn apostasi (Manhattan distance) meta3y simeiwn me
    syntetagmenes a kai b.

    >>> distance(coordinates(37.976362, 23.725947), coordinates(37.994097, 23.732253))
    1.4054437699552689
    """
    lon_dist = (a[0] - b[0]) * _lon_deg
    lat_dist = (a[1] - latitude(b)) * _lat_deg
    return abs(lon_dist) + abs(lat_dist)


def coordinates_to_str(cors):
    """Epistrefei tis syntetagmenes se string.

    cors -- syntetagmenes (typou coordinates)
   
    Epistrefei string.

    >>> coordinates_to_str(coordinates(37.976362, 23.725947))
    '37.976362, 23.725947'
    """
    return str(cors[0]) + ', ' + str(cors[1])


