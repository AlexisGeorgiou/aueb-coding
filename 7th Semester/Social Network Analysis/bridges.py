import pandas as pd
import networkx as nx

#Read edges file
df = pd.read_csv("Edges.csv")

#Turn it into a graph
G = nx.from_pandas_edgelist(df, source='Source', target='Target', create_using=nx.DiGraph())

#We need to transform our graph into an undirected graph
G2 = G.to_undirected()

#Prints the list of bridges
print(G2)
print(list(nx.bridges(G2)))
print(list(nx.local_bridges(G2)))

