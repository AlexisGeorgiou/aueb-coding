import pandas as pd
import networkx as nx

#Read edges file
df = pd.read_csv("Edges.csv")

#Turn it into a graph
G = nx.from_pandas_edgelist(df, source='Source', target='Target', create_using=nx.DiGraph())

#We need to transform our graph into an undirected graph
G2 = G.to_undirected()


count = 0
maximumlength = 0
for clique in nx.find_cliques(G2):
    if len(clique) > maximumlength:
        maximumlength = len(clique)
        print(maximumlength)
        print(clique)
    count +=1
print(count)

