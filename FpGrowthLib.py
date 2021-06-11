from mlxtend.frequent_patterns import fpgrowth, association_rules
from mlxtend.preprocessing import TransactionEncoder
import pandas as pd
from csv import reader
from itertools import chain, combinations
import time


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
                ',') if string != '']
            dataset.append(row)
    return dataset




def fpGrowth(data_file, min_sup):
    dataset = extractDataFromTxtFile(data_file)
    # dataset = extractDataFromCSVFile('./DataSetA.csv')
    te = TransactionEncoder()
    te_ary = te.fit(dataset).transform(dataset)
    df = pd.DataFrame(te_ary, columns=te.columns_)
    data = fpgrowth(df, min_support=min_sup, use_colnames=True)
    for idx, row in data.iterrows():
        data.at[idx, "support"] = round(row["support"]*len(dataset))
    return data


def outputItemList(filepath, data):
    modifiedData = data.to_numpy().tolist()
    print(modifiedData)
    with open(filepath, 'w') as f:
        for item in modifiedData:
            itemList = ', '.join(sorted(list(item[1])))
            numberOfOccurrences = round(item[0])
            f.write("{}: {}\n".format(
                itemList, numberOfOccurrences))


def outputRule(filepath, data, min_conf):
    # thang data nay dang chua so thuc nen bi sai so
    # day nhe, bay gio toi dung o
    print(data)
    assoc_rules = association_rules(
        data, metric="confidence", min_threshold=min_conf).to_numpy().tolist()
    with open(filepath, 'w') as f:
        for item in assoc_rules:
            antecedentList = ', '.join(sorted(list(item[0])))
            consequentsList = ', '.join(sorted(list(item[1])))
            f.write("{} -> {}\n".format(
                antecedentList, consequentsList))


def outputTofFile():
    data_file = "/home/chuducanh/association_rule/DataSetA.txt";
    min_sup = 0.02
    min_conf = 0.01
    data = fpGrowth(data_file, min_sup)
    outputItemList('output_item_list_lib.txt', data)
    outputRule('output_rule_lib.txt', data, min_conf)


if __name__ == "__main__":
    start_time = time.time()
    outputTofFile()
    print("--- %s seconds ---" % (time.time() - start_time))


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
