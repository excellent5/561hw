__author__ = 'zhanyang'


class Fact:
    def __init__(self, s):
        leftparindex = s.find('(')
        self.predicate = s[:leftparindex]
        self.arguments = s[leftparindex + 1:-1].split(',')

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.predicate == other.predicate and self.arguments == other.arguments
        else:
            return False
