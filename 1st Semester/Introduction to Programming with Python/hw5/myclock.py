class Counter:
    def __init__(self, start = 0):
        self.value = start

    def advance(self):
        self.value = self.value + 1
        return self.value

    def __str__(self):
        return str(self.value)

class CyclicCounter(Counter):
    def __init__(self, period, start = 0):
        self.period = period
        Counter.__init__(self, start)

    def advance(self):
        self.value = (self.value + 1) % self.period
        return self.value

    def __str__(self):
        s = Counter.__str__(self)
        return (2-len(s))*'0' + s

class CascadeCounter(CyclicCounter):
    def __init__(self, next, period, start = 0):
        CyclicCounter.__init__(self, period, start)
        self.next = next

    def advance(self):
        CyclicCounter.advance(self)
        if self.next and self.value == 0:
            self.next.advance()
        return self.value

class Clock(Counter):
    def __init__(self, h, m, s):
        self._h = CyclicCounter(24, h)
        self._m = CascadeCounter(self._h, 60, m)
        self._s = CascadeCounter(self._m, 60, s)

    def advance(self):
        self._s.advance()
        
    def __str__(self):
        return '{0}:{1}:{2}'.format(self._h, \
                                         self._m, \
                                         self._s)
class RomanClock(Clock):
    def __init__(self, h, m, s):
        self._h = RomanCascadeCounter(None, 24, h)
        self._m = RomanCascadeCounter(self._h, 60, m)
        self._s = RomanCascadeCounter(self._m, 60, s)
    
class RomanCascadeCounter(CascadeCounter):
    def __str__(self):
        tens = self.value // 10
        units = self.value % 10
        tens_s = 'L' if tens == 5 else tens*'X'
        if units < 5:
            units_s = units*'I'
        else:
            units_s = 'V' + (units-5)*'I'

        sz = len(tens_s + units_s)
        return '-'*(9-sz) + tens_s + units_s
            

from time import sleep

c = Clock(23, 59, 45)
while True:
    print(c, end = '\r')
    sleep(1)
    c.advance()
