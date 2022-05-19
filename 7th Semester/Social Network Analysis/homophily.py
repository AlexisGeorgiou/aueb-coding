import pandas as pd
import networkx as nx

#Read edges file
df = pd.read_csv("Edges.csv")

#Turn it into a graph
G = nx.from_pandas_edgelist(df, source='Source', target='Target', create_using=nx.DiGraph())

#We need to transform our graph into an undirected graph
G2 = G.to_undirected()

df = pd.read_csv("Nodes2.csv")
dd = df.to_dict('records')
print(dd)
##nx.set_node_attributes(G,)
##print(G2.nodes[1]["Type"])
print(nx.degree_assortativity_coefficient(G2))
