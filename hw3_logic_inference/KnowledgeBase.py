__author__ = 'zhanyang'

from BackwardChaining import BackwardChaining
from Clause import Clause
from Fact import Fact


class KnowledgeBase:
    data = dict()

    def addNewClause(self, newstat):
        newclause = Clause(newstat)
        predicate = newclause.conclusion.predicate
        if predicate in self.data:
            self.data[predicate] = self.data[predicate] + [newclause]
        else:
            self.data[predicate] = [newclause]

    def query(self, query):
        return BackwardChaining().ask(self.data, Fact(query))
