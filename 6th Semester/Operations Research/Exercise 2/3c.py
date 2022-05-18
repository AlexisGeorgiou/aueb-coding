import math
import numpy as np

n = 0
c = 0.8
#e = 2.718281828459
xn = np.array([3,3])

def f(x,y):
    return 2*x + y**2 + math.exp(-(x+y-1))

def d(x,y):
    return np.array([2- math.exp(-x-y+1), 2*y - math.exp(-x-y+1)])

t = 1
xnplus1 = xn + t * d(xn[0],xn[1])
for i in range(100):
    if f(xnplus1[0],xnplus1[1]) <= (f(xn[0],xn[1]) - c * d(xn[0],xn[1]) * (xnplus1-xn)).all():
        print(xnplus1)
        print(i)
        break
    if abs(d(xn[0],xn[1])).all() <= 10**(-5):
        print(xnplus1)
        print(i)
        break
    t = t /2
    xn = xnplus1
    xnplus1 = xn + t * d(xn[0],xn[1])

print(xnplus1)
