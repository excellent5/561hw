__author__ = 'zhanyang'

from Fact import Fact


class Clause:
    def __init__(self, s):
        index = s.find(' => ')
        if index != -1:
            self.premises = [Fact(premise) for premise in s[:index].split(' ^ ')]
            self.conclusion = Fact(s[index + 4:])
        else:
            self.premises = []
            self.conclusion = Fact(s)

