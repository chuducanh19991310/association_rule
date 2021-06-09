from mlxtend.frequent_patterns import fpgrowth, association_rules
from mlxtend.preprocessing import TransactionEncoder
import pandas as pd
from csv import reader
from itertools import chain, combinations


def powerset(s):
    return chain.from_iterable(combinations(s, r) for r in range(1, len(s)))


def associationRule(freqItemSet, itemSetList, minConf):
    rules = []
    for itemSet in freqItemSet:
        subsets = powerset(itemSet)
        itemSetSup = getSupport(itemSet, itemSetList)
        for s in subsets:
            confidence = float(itemSetSup / getSupport(s, itemSetList))
            if(confidence > minConf):
                rules.append([set(s), set(itemSet.difference(s)), confidence])
    return rules


def extractDataFromCSVFile(filepath):
    dataset = []
    with open(filepath, 'r') as read_obj:
        csv_reader = reader(read_obj)
        for row in csv_reader:
            # Remove all empty string
            rrow = [e for e in row if e != '']
            dataset.append(rrow)
    return dataset


def extractDataFromTxtFile(filepath):
    dataset = []
    with open(filepath, 'r') as file:
        lines = [line.rstrip('\n') for line in file]
        for line in lines:
            row = [string for string in line.split(
                ' ') if string != '']
            dataset.append(row)
    return dataset


def fpGrowth():
    dataset = extractDataFromTxtFile('data1.txt')
    te = TransactionEncoder()
    te_ary = te.fit(dataset).transform(dataset)
    df = pd.DataFrame(te_ary, columns=te.columns_)
    data = fpgrowth(df, min_support=0.01, use_colnames=True)
    return data


def outputTofFile():
    data = fpGrowth()
    outputItemList('output_item_list_lib.txt', data)
    outputRule('output_rule.txt', data)


def outputItemList(filepath, data):
    dataset = extractDataFromTxtFile('data1.txt')
    total = len(dataset)
    modifiedData = data.to_numpy().tolist()
    with open(filepath, 'w') as f:
        for item in modifiedData:
            itemList = ','.join(sorted(list(item[1])))
            numberOfOccurrences = round(item[0] * total)
            f.write("{} : {}\n".format(
                itemList, numberOfOccurrences))


def outputRule(filepath, data):
    assoc_rules = association_rules(
        data, support_only=True, min_threshold=0.01).to_numpy().tolist()
    with open(filepath, 'w') as f:
        for item in assoc_rules:
            antecedentList = ','.join(sorted(list(item[0])))
            consequentsList = ','.join(sorted(list(item[1])))
            f.write("{} -> {}\n".format(
                antecedentList, consequentsList))


if __name__ == "__main__":
    outputTofFile()


#     # Read data from file
# dataset = []
# # with open('./DataSetA.csv', 'r') as read_obj:
# #     csv_reader = reader(read_obj)
# #     for row in csv_reader:
# #         # Remove all empty string
# #         rrow = [e for e in row if e != '']
# #         print(rrow)
# #         dataset.append(rrow)


# with open('data1.txt', 'r') as file:
#     lines = [line.rstrip('\n') for line in file]
#     for line in lines:
#         row = [string for string in line.split(
#             ' ') if string != '']
#         dataset.append(row)

# te = TransactionEncoder()
# te_ary = te.fit(dataset).transform(dataset)
# df = pd.DataFrame(te_ary, columns=te.columns_)
# total = len(dataset)
# data = fpgrowth(df, min_support=0.01, use_colnames=True)
# modifiedData = data.to_numpy().tolist()


# # Print result for reqset
# with open('output.txt', 'w') as f:
#     for item in modifiedData:
#         itemList = ', '.join(sorted(list(item[1])))
#         numberOfOccurrences = round(item[0] * total)
#         f.write("{}, : {}\n".format(
#             itemList, numberOfOccurrences))

# assoc_rules = association_rules(
#     data, support_only=True, min_threshold=0.01).to_numpy().tolist()

# with open('output_rule.txt', 'w') as f:
#     for item in assoc_rules:
#         antecedentList = ' , '.join(sorted(list(item[0])))
#         consequentsList = ' , '.join(sorted(list(item[1])))
#         f.write("{} -> {}\n".format(
#             antecedentList, consequentsList))
