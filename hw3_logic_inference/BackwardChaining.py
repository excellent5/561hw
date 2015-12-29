import copy


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

