class ActionCounter:
    """Afirimeni ta3i metriti.

    Oi paragwges ta3eis 8a prepei na ylopoihsoyn tis
    me8odous _condition(self), _action(self)
    """
    
    def advance(self):
        """Ay3isi tis timis tou metriti kata mia monada.

        Otan ikanopoieitai i syn8iki self.condition() == True
        tote lambanetai h energeia self._action()  (dhl. kaleitai i me8odos self._action().)
        """
        self._advance()
        if self._condition():
            self._action()


#--------------------- Askisi 1 -----------------------------

class SimpleCounter(ActionCounter):
    """Ta3h tis opoias ta antikeimena anaparistoun aplous metrites,
    opou i timi self.value einai akeraios ari8mos.

    >>> c = SimpleCounter(97)
    >>> str(c)
    '97'
    >>> c.advance()
    >>> str(c)
    '98'
    >>> c.advance()
    >>> str(c)
    '99'
    >>> c.advance()
    >>> str(c)
    '100'
    >>> c.advance()
    >>> str(c)
    '101'
    """
    def __init__(self, start = 0):
        """Arxikopoihsh aplou metriti.

        start -- arxiki timi (an den do8ei einai 0)
        """
        self.value = start

    def _condition(self):
        """Den lambanetai kapoia energeia."""
        return False

    def _advance(self):
        """H timi au3anei kata ena."""
        self.value += 1

    def __str__(self):
        return str(self.value)


#--------------------- Askisi 2 -----------------------------

class CyclicCounter(SimpleCounter):
    """Ta3i tis opoias ta antikeimena anaparistoun metrites me akeraies times
    0, 1, 2, ..., self.period - 1. H epomenh timi tis self.period - 1 einai 0.

    >>> c = CyclicCounter(100, 97)
    >>> str(c)
    '97'
    >>> c.advance()
    >>> str(c)
    '98'
    >>> c.advance()
    >>> str(c)
    '99'
    >>> c.advance()
    >>> str(c)
    '00'
    >>> c.advance()
    >>> str(c)
    '01'
    >>> c2 = CyclicCounter(12, 8)
    >>> str(c2)
    '08'
    >>> c2.advance()
    >>> str(c2)
    '09'
    >>> c2.advance()
    >>> str(c2)
    '10'
    >>> c2.advance()
    >>> str(c2)
    '11'
    >>> c2.advance()
    >>> str(c2)
    '00'
    """
    def __init__(self, period, start = 0):
        """Arxikopoihsh kyklikou metriti.

        period -- dinei tin timi tis self.period
        start -- arxiki timi (an den do8ei einai 0)
        """
        self.period = period
        SimpleCounter.__init__(self, start)
        
    def _condition(self):
        """H syn8iki energopoihshs einai i ypervasi tis megistis timis."""
        return self.value == self.period

    def _action(self):
        """H energeia pou lambanetai einai o midenismos tis timis tou metriti,
        wste na ginetai anakyklwsi."""
        self.value = 0

    def __str__(self):
        """Epistrefei string me tin timi tou metriti. Xrhsimopoieitai sta8eros ar8mos
        pshfiwn, ane3arthta tis trexousas timis tou metriti. O ari8mos pshfiwn einai o
        mikroteros dynatos wste na min yparxoyn peritta pshfia (me timi 0 panta).
        """
        """GRAPSTE TON KWDIKA SAS EDW."""
        if self.value < 10:
            return '0' + str(self.value) 
        else:   
            return str(self.value)


#--------------------- Askisi 3 -----------------------------

class CyclicCascadeCounter(CyclicCounter):
    """Ta3i tis opoias ta antikeimena anaparistoum kyklikous metrites pou epipleon au3anoun
    ton metriti self.next_counter (kaleitai i me8odos self.next_counter.advance()), 
    otan symbei anakyklwsi.

    >>> c2 = CyclicCascadeCounter(10, None, 5)
    >>> c1 = CyclicCascadeCounter(100, c2, 98)
    >>> str(c2)
    '5'
    >>> str(c1)
    '98'
    >>> c1.advance()
    >>> str(c1)
    '99'
    >>> str(c2)
    '5'
    >>> c1.advance()
    >>> str(c1)
    '00'
    >>> str(c2)
    '6'    
    """
    def __init__(self, period, next_counter = None, start = 0):
        """Arxikopoihsh metriti.

        next_counter -- dinei ton metriti self.next_counter
        period -- dinei tin timi tis self.period
        start -- arxiki timi (an den do8ei einai 0)
        """
        CyclicCounter.__init__(self, period, start)
        self.next_counter = next_counter

    def _action(self):
        """Epipleon tis anakyklwsis, ay3anetai i timi tou metriti self.next_counter."""
        CyclicCounter._action(self)
        if self.next_counter:
            self.next_counter.advance()


class Clock(ActionCounter):
    """Ta3i tis opoias ta antikeimena anaparistoun pshfiako roloi me endei3eis
    wras, leptwn, deuteroleptwn.

    H me8odos self.advance() exei ws apotelesma thn ay3isi kata ena deuterolepto.

    >>> c = Clock(23, 59, 58)
    >>> str(c)
    '23:59:58'
    >>> c.advance()
    >>> print(c)
    23:59:59
    >>> c.advance()
    >>> print(c)
    00:00:00
    >>> c.advance()
    >>> str(c)
    '00:00:01'
    >>> c.advance()
    >>> print(c)
    00:00:02
    """
    def __init__(self, h = 0, m = 0, s = 0):
        """Arxikopoiisi rologiou.

        h -- arxiki endei3h wras (akeraios 0 ews 23)
        m -- arxiki endei3h leptwn (akeraios 0 ews 59)
        s -- arxiki endei3h deuteroleptwn (akeraios 0 ews 59)

        An paraleif8ei kapoia apo tis parapanw times, 8evreitai oti einai 0.
        """
        self._h = CyclicCascadeCounter(24, None, h)
        self._m = CyclicCascadeCounter(60,self._h ,m)
        self._s = CyclicCascadeCounter(60,self._m ,s)

    def _condition(self):
        """Den lambanetai kapoia energeia."""
        return False
    
    def _advance(self):
        """O xronos pou anaparista to roloi ay3anei kata ena deyterolepto."""
        self._s.advance()

    def __str__(self):
        return '{0}:{1}:{2}'.format(self._h, self._m, self._s)



#--------------------- Askisi 4 -----------------------------

class RomanClock(Clock):
    """Roloi me endei3eis me rwmaika noumera.

    >>> c = RomanClock(23, 59, 58)
    >>> str(c)
    '----XXIII:---LVIIII:----LVIII'
    >>> c.advance()
    >>> print(c)
    ----XXIII:---LVIIII:---LVIIII
    >>> c.advance()
    >>> print(c)
    ---------:---------:---------
    >>> c.advance()
    >>> print(c)
    ---------:---------:--------I
    >>> c.advance()
    >>> print(c)
    ---------:---------:-------II
    """
    class CascadeRomanCyclicCounter(CyclicCascadeCounter):
        def __str__(self):
            fifties = self.value // 50
            tens = self.value % 50 // 10
            fives = self.value % 10 // 5
            units = self.value % 5
            num_str = fifties*'L' + tens*'X' + fives*'V' + units*'I'
            return (9-len(num_str))*'-' + num_str

    def __init__(self, h = 0, m = 0, s = 0):
        self._h = RomanClock.CascadeRomanCyclicCounter(24, None, h)
        self._m = RomanClock.CascadeRomanCyclicCounter(60,self._h ,m)
        self._s = RomanClock.CascadeRomanCyclicCounter(60,self._m ,s)
        


#--------------------- Askisi 5 -----------------------------

class AMPMClock(Clock):
    """Roloi opou i endei3i wrwn einai 1 ews 12 me endei3h prwinis wras (AM) 
    'h meshmerianis (PM).

    >>> c = AMPMClock(23, 59, 58)
    >>> print(c)
    11:59:58 PM
    >>> c.advance()
    >>> str(c)
    '11:59:59 PM'
    >>> c.advance()
    >>> print(c)
    12:00:00 AM
    """
    AM, PM = 0, 1
    class AMPMCounter(CyclicCounter):
        def __init__(self, val):
            CyclicCounter.__init__(self, 2, val)
        def __str__(self):
            return 'AM' if self.value == AMPMClock.AM else 'PM'
        
    def __init__(self, h = 0, m = 0, s = 0):
        """Arxikopoiisi rologiou.

        h -- arxiki endei3h wras (akeraios 0 ews 23)
        m -- arxiki endei3h leptwn (akeraios 0 ews 59)
        s -- arxiki endei3h deuteroleptwn (akeraios 0 ews 59)

        An paraleif8ei kapoia apo tis parapanw times, 8evreitai oti einai 0.
        """
        """GRAPSTE TON KWDIKA SAS APO KATW."""
        Clock.__init__(self, h, m ,s)
        self._h.value = self._h.value % 12
        
        
            

    def __str__(self):
        """GRAPSTE TON KWDIKA SAS APO KATW."""
        AMPMClock.AMPMCounter.__init__(self, not(self._h.value // 12))
        return '{0}:{1}:{2} {3}'.format(self._h , self._m, self._s, AMPMClock.AMPMCounter.__str__(self))


#--------------------- Askisi 6 -----------------------------

class AlarmClock(Clock):
    """3ypnitiri.

    >>> c = AlarmClock(18, 53, 24)
    >>> c.set_alarm(18, 53, 26)
    >>> str(c)
    '18:53:24'
    >>> c.advance()
    >>> str(c)
    '18:53:25'
    >>> c.advance()
    >>> str(c)
    '18:53:26\x07\x07\x07\x07'
    >>> print(c)
    18:53:26
    >>> c.advance()
    >>> str(c)
    '18:53:27'
    >>> c.advance()
    >>> print(c)
    18:53:28
    """
    """GRAPSTE TON KWDIKA SAS APO KATW."""
    
    def __init__(self, h = 0, m = 0, s = 0):
        Clock.__init__(self, h, m, s)
    def set_alarm(self, h = 0, m = 0, s = 0):
        self.alarmh, self.alarmm, self.alarms = h, m, s
    def __str__(self):
        output =  '{0}:{1}:{2}'.format(self._h , self._m, self._s)
        if self.alarmh == self._h.value and self.alarmm == self._m.value and self.alarms == self._s.value:
            output += "\x07\x07\x07\x07"
        return str(output)
        
    
        
#--------------------- Askisi 7 -----------------------------

class Timer(Clock):
    """Antistrofos xronometritis.

    >>> c = Timer(0, 0, 2)
    >>> str(c)
    '00:00:02'
    >>> c.advance()
    >>> str(c)
    '00:00:01'
    >>> c.advance()
    >>> str(c)
    '-00:00:00\x07\x07\x07\x07'
    >>> c.advance()
    >>> str(c)
    '-00:00:01'
    >>> c.advance()
    >>> str(c)
    '-00:00:02'
    """
    """GRAPSTE TON KWDIKA SAS APO KATW."""
    #To programma den metrai anapoda lepta kai ores, mono deuterolepta.
    def __init__(self, h = 0, m = 0, s = 0):
        Clock.__init__(self, h, m ,s)
    def advance(self):
        self._s.value -= 1
    def __str__(self):
        def my_abs(x):
            if abs(x) <= 10:
                return '0' + str(abs(x))
            else:
                return str(abs(x))        
        output = '{0}:{1}:{2}'.format(self._h, self._m, self._s)
        if self._h.value == 0 and self._m.value == 0 and self._s.value == 0:
            output = '-{0}:{1}:{2}\x07\x07\x07\x07'.format(self._h, self._m, self._s)
        if self._h.value < 0 or self._m.value < 0 or self._s.value < 0:
            output = '-{0}:{1}:{2}'.format(my_abs(self._h.value), my_abs(self._m.value), my_abs(self._s.value))
        return output
        
        


if __name__ == "__main__":
    import doctest
    doctest.testmod()
