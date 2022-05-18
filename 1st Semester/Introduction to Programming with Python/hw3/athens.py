from coordinates import coordinates
from map import *

athens_map = map([location('AUEB', coordinates(37.994097, 23.732253)), \
                  location('Acropolis', coordinates(37.971584, 23.725912)), \
                  location('Syntagma', coordinates(37.975560, 23.734691)),
                  location('National Garden', coordinates(37.973116, 23.736483)), \
                  location('Monastiraki', coordinates(37.976362, 23.725947))])

my_location = get_location('AUEB', athens_map)
print('My location is: ' + location_to_str(my_location))
print('\nMoving to Syntagma...')

my_location = get_location('Syntagma', athens_map)
print('My location is: ' + location_to_str(my_location))
print('Nearby locations:')
print_nearby_locations(my_location, athens_map)
print('\nMoving to Acropolis...')    

my_location = get_location('Acropolis', athens_map)
print('My location is: ' + location_to_str(my_location))
print('Nearby locations')
print_nearby_locations(my_location, athens_map)

          
