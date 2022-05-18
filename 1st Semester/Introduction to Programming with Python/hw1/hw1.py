from operator import *


#>>>>>>>>>>>>>>>>>>>>> Askhsh 1 <<<<<<<<<<<<<<<

def sum_powers(x1, n1, x2, n2):
    """ Ypologizei x1**n1+x2**n2

    x1,n1,x2,n2 -- ari8moi
    """

    """ Xrhsimopoihste MONO ekfraseis klhshs,
        OXI infix telestes (+,-,...) """

    return add(pow(x1,n1),pow(x2,n2))



#>>>>>>>>>>>>>>>>>>>>> Askhsh 2 <<<<<<<<<<<<<<<

def my_abs(x):
    """ Ypologizei thn apolyth timh tou x

    x -- ari8mos

    >>> my_abs(43.33)
    43.33
    >>> my_abs(-894.3)
    894.3
    """

    """ Xrhsimopoihste MONO ekfraseis klhshs,
        OXI infix telestes (+,-,...) """

    return pow(sqrt(x),2)    #max(x,neg(x))


#>>>>>>>>>>>>>>>>>>>>> Askhsh 3 <<<<<<<<<<<<<<<

def func(s, n):
    """Symplhrwste ta kena wste:

    >>> func('hello',2)
    'Hmmmm hello my hmm friend'
    >>> func('goodbye',3)
    'Hmmmmmm goodbye my hmmm friend'
    """

    return "H" + 2*n* "m" + " " + s + " my h" + n* "m" + " friend"



#>>>>>>>>>>>>>>>>>>>>> Askhsh 4 <<<<<<<<<<<<<<<

def func1(s):
    """Symplhrwste ta kena wste na didetai to
       akolou8o apotelesma

    >>> func1('ni')
    'Mini-Mini'
    >>> func1('kros')
    'Mikros-Mikros'
    """

    return "Mi" + s + "-Mi" + s


#>>>>>>>>>>>>>>>>>>>>> Askhsh 5 <<<<<<<<<<<<<<<

def func2():
    """Symplhrwste ta kena wste na didetai to
       akolou8o apotelesma

    >>> func2()
    Mini-Me-Mini-Me
    """
    print(func1("ni-Me"))



#>>>>>>>>>>>>>>>>>>>>> Askhsh 6 <<<<<<<<<<<<<<<

def func3(s):
    """Symplhrwste ta kena wste na didetai to
       akolou8o apotelesma

    >>> func3(func3('tsouf'))
    '((tsouf-tsouf)-(tsouf-tsouf))'
    """

    return '(' + s + '-' + s + ')'
    




#>>>>>>>>>>>>>>>>>>>>> Askhsh 7 <<<<<<<<<<<<<<<

def digit_sum(x):
    """Ypologizei to a8roisma twn (dekadikwn) pshfiwn tou x

    x -- 8etikos akeraios

    >>> digit_sum(10000)
    1
    >>> digit_sum(615)
    12
    """

    s = 0
    while x != 0:
        s = s + x%10
        x = x//10

    return s



#>>>>>>>>>>>>>>>>>>>>> Askhsh 8 <<<<<<<<<<<<<<<

def keep_summing(x):
    """Ypologizei to a8roisma twn pshfiwn synexws ews
    otou to apotelesma exei ena mono pshfio

    x -- 8etikos akeraios

    >>> keep_summing(3)
    3
    >>> keep_summing(32)
    5
    >>> keep_summing(344)
    2
    >>> keep_summing(999)
    9
    >>> print(keep_summing(999))
    9
    """

    """ GRAPSTE TON KWDIKA SAS APO KATW """
    while x%10 != x:
        x = digit_sum(x) #exei oristi stin askisi 7
    return x


#>>>>>>>>>>>>>>>>>>>>> Askhsh 9 <<<<<<<<<<<<<<<

def draw_number(x):
    """Emfanizei ton xarakthra | x fores

    >>> draw_number(5)
    |||||
    >>> draw_number(0)
    >>> draw_number(-3)
    -|||
    """

    """ GRAPSTE TON KWDIKA SAS APO KATW """
    if x>0:
        x = '|' * x
    elif x<0:
        x = '-' + '|' * -x
    if x !=0:
        print(x)




#>>>>>>>>>>>>>>>>>>>>> Askhsh 10 <<<<<<<<<<<<<<<

def max_digit(x):
    """Ypologizei to megalytero pshfio tou x

    x -- 8etikos akeraios

    >>> max_digit(45874543)
    8
    >>> max_digit(98287334) - max_digit(8)
    1
    """

    """ GRAPSTE TON KWDIKA SAS APO KATW """
    max = 0
    while x > 0 :
        if x%10 > max:
            max = x%10
        x = x//10
    return max




#>>>>>>>>>>>>>>>>>>>>> Askhsh 11 <<<<<<<<<<<<<<<

def count_digits(x, d):
    """Ypologizei to plh8os twn pshfiwn iswn me d

    x -- 8etikos akeraios
    d -- akeraios apo 0 ews kai 9

    >>> count_digits(1000, 0)
    3
    >>> count_digits(12944342, 2)
    2
    >>> count_digits(121,1)+ count_digits(121,2)
    3
    """

    """ GRAPSTE TON KWDIKA SAS APO KATW """
    count = 0
    while x > 0 :
        if d == x%10:
            count = count + 1
        x = x//10
    return count


