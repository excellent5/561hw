__author__ = 'zhanyang'

import copy
import sys


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



class Clause:
    def __init__(self, s):
        index = s.find(' => ')
        if index != -1:
            self.premises = [Fact(premise) for premise in s[:index].split(' ^ ')]
            self.conclusion = Fact(s[index + 4:])
        else:
            self.premises = []
            self.conclusion = Fact(s)



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



class BackwardChaining:
    def __init__(self):
        self.subgoals = []
        self.varcounter = 0

    def isVariable(self, x):
        if len(x) > 0 and x[0] >= 'a' and x[0] <= 'z':
            return True
        return False


    def unify(self, x, y, theta):
        if theta is None:
            return None
        elif x == y:
            return theta
        elif isinstance(x, str) and self.isVariable(x):
            return self.unifyVar(x, y, theta)
        elif isinstance(y, str) and self.isVariable(y):
            return self.unifyVar(y, x, theta)
        elif isinstance(x, list) and isinstance(y, list) and len(x) == len(y):
            return self.unify(x[1:], y[1:], self.unify(x[0], y[0], theta))
        return None

    def unifyVar(self, var, x, theta):
        if var in theta:
            return self.unify(theta[var], x, theta)
        elif x in theta:
            return self.unify(var, theta[x], theta)
        thetabak = theta.copy()
        thetabak[var] = x
        return thetabak

    def standardize(self, lhs, rhs):
        lhs = copy.deepcopy(lhs)
        rhs = copy.deepcopy(rhs)
        for lhs_premise in lhs:
            for i in range(len(lhs_premise.arguments)):
                if self.isVariable(lhs_premise.arguments[i]):
                    lhs_premise.arguments[i] = lhs_premise.arguments[i] + str(self.varcounter)

        for i in range(len(rhs.arguments)):
            if self.isVariable(rhs.arguments[i]):
                rhs.arguments[i] = rhs.arguments[i] + str(self.varcounter)

        self.varcounter += 1
        return (lhs, rhs)

    def ask(self, kb, query):
        return self.bcOR(kb, query, dict())

    def bcOR(self, kb, goal, theta):
        """
        Get one possible unification of goal and return
        :param kb: Type: dict, key: predicate value: list of clauses that has this predicate
        :param goal: Type: Fact, goal fact we want to prove
        :param theta: Type: dict, key: variable string, value: constant string
        """
        if (goal.predicate not in kb) or (goal in self.subgoals):
            return
        self.subgoals.append(goal)
        for candidate in kb[goal.predicate]:
            premises, conclusion = self.standardize(candidate.premises, candidate.conclusion)
            for subst in self.bcAND(kb, premises, self.unify(conclusion.arguments, goal.arguments, theta)):
                self.subgoals.pop()
                yield subst

    def bcAND(self, kb, goals, theta):
        """
        Unify all premises and return the substitution
        :param kb: Type: dict, key: predicate value: list of clauses that has this predicate
        :param goals: Type: fact array, all premises we need to prove
        :param theta:
        :return: substitution
        """
        if theta is None:
            return
        elif len(goals) == 0:
            yield theta
        else:
            first, rest = goals[0], goals[1:]
            for subst1 in self.bcOR(kb, self.subst(theta, first), theta):
                # for subst1 in self.bcOR(kb, self.subst(theta, first), dict()):
                for subst2 in self.bcAND(kb, rest, subst1):
                    yield subst2

    def subst(self, theta, sentence):
        """
        Substitute sentence
        :param theta: Type: dict, key: variable string, value: constant string
        :param sentence: Type: Fact, original sentence
        :return: Type: Fact, substituted sentence
        """
        sentence = copy.deepcopy(sentence)
        for target, substitution in theta.iteritems():
            for i in range(len(sentence.arguments)):
                if sentence.arguments[i] == target:
                    sentence.arguments[i] = substitution
        return sentence



if __name__ == '__main__':
    if len(sys.argv) > 2 and sys.argv[1] == '-i':
        filein = open(sys.argv[2], "r")
        querynum = int(filein.readline().rstrip('\r\n'))
        queries = []
        for i in range(querynum):
            queries.append(filein.readline().rstrip('\r\n'))
        rulenum = int(filein.readline().rstrip('\r\n'))
        kb = KnowledgeBase()
        for i in range(rulenum):
            kb.addNewClause(filein.readline().rstrip('\r\n'))
        filein.close()

        fileout = open("output.txt", "w")
        fileout.close()
        for i in range(querynum):
            fileout = open("output.txt", "a")
            try:
                next(kb.query(queries[i]))
                fileout.write("TRUE\n")
            except StopIteration:
                fileout.write("FALSE\n")
            except Exception, e:
                print e
                fileout.write("FALSE\n")
            fileout.close()