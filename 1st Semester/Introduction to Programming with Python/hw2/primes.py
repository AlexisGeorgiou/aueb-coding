def isprime(n):
    """Tests primality.

    n -- number to test
    """
    from math import sqrt, floor
    i, last = 2, floor(sqrt(n))
    while i <= last:
        if n % i == 0:
            return False
        i += 1

    return True

