def read_graph(path : str) -> dict[str, dict[str, int]] :
    graph = dict()
    with open(path, "r") as file :
        lines = [line.strip().split(" ") for line in file.readlines()]
        for line in lines[1::]:
            if graph.get(line[0]) is None : graph[line[0]] = {}
            graph[line[0]][line[1]] = int(line[2])
            if graph.get(line[1]) is None : graph[line[1]] = {}
    return graph


def dijkstra(graph : dict[str, dict[str, int]] , root : str, target : str) -> tuple[list[str], int] :
    def find_lowest_cost_node(costs : dict[str, int]) -> str :
        lowest_cost = float("inf")
        lowest_cost_node = None
        for node in costs:
            if costs[node] < lowest_cost and node not in visisted:
                lowest_cost = costs[node]
                lowest_cost_node = node
        return lowest_cost_node
    infinity = float("inf")
    costs : dict[str, int] = {}
    try:
        for node in graph[root]: costs[node] = graph[root][node]
    except:
        print("root is not in graph")
        exit()
    for node in graph:
        if node != root and node not in graph[root]:
            costs[node] = infinity
    parents : dict[str, str] = {"1" : None}
    for node in graph[root]: parents[node] = root
    visisted = []
    node = find_lowest_cost_node(costs)
    while node is not None :
        cost = costs[node]
        neighbors = graph[node]
        for n in neighbors.keys() :
            new_cost = cost + graph[node][n]
            if new_cost < costs[n] :
                costs[n] = new_cost
                parents[n] = node
        visisted.append(node)
        node = find_lowest_cost_node(costs)
    if costs[target] == infinity : return (None, None)
    path : list[str] = []
    cost : int = costs[target]
    node = target
    while node is not None :
        path.append(node)
        node = parents[node]
    return (path, cost)

def test(test_path : str, graph_path : str) -> tuple[int, int] :
    success = failure = 0
    graph = read_graph(graph_path)
    problems = []
    with open(test_path) as file:
        header = file.readline().strip().split(",")
        n, m = int(header[4]), int(header[5])
        for root in range(39, n+1):
            for target in range(root+1, m+1):
                print("testing root = ",root," | target = ",target," ...")
                line = file.readline().strip().split(",")
                path = line[2].split(" <- ")
                cost = line[3]
                result = dijkstra(graph, line[0], line[1])
                if result == (None, None) : result = ("null", "null")
                if result[0] == path and str(result[1]) == cost:
                    success += 1
                    print("Successful")
                else:
                    failure += 1
                    problems.append([root, target, path, cost, result[0], result[1]])
                    print("failed")
    if failure == 0 : print("\ntest was successful")
    else :
        print("\nsomething is wrong\nproblems:\n")
        print("num.\troot\ttarget\ttest_path\ttest_cost\tcorrect_path\tcorrect_cost\n")
        c = 1
        for problem in problems:
            print(c,end="\t")
            for thing in problem: print(thing, end="\t")
            c += 1
        print()
    return (success, failure)

graph_path = "D:\\University\\term 2\OOP\OOP_Project\\assets\graph.txt"
test_path = "D:\\University\\term 2\OOP\OOP_Project\\assets\out.csv"

if __name__ == "__main__" :
    test(test_path, graph_path)