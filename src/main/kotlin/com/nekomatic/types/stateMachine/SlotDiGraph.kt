package com.nekomatic.types.stateMachine

data class SlotDiGraph<VERTEX : Any, SLOT : Any>(val adjMap: Map<VERTEX, Map<SLOT, VERTEX>>) :
    ISlotGraphWithAddConnection<VERTEX, SLOT, SlotDiGraph<VERTEX, SLOT>>,
    ISlotGraphWithSubGraphs<VERTEX, SLOT> {

    @Suppress("unused")
    constructor() : this(mapOf())

    override val vertices: List<VERTEX> by lazy { adjMap.keys.toList() }

    override val connections: List<Connection<VERTEX, SLOT>>  by lazy {
        adjMap.flatMap { (fromV: VERTEX, connections: Map<SLOT, VERTEX>) ->
            connections.map { toV -> Connection(fromV, toV.value, toV.key) }
        }
    }

    override fun addConnection(e: Connection<VERTEX, SLOT>): SlotDiGraph<VERTEX, SLOT> =
        addConnection(e.fromVertex, e.toVertex, e.throughSocket)

    private fun addConnection(fromVertex: VERTEX, toVertex: VERTEX, throughSocket: SLOT): SlotDiGraph<VERTEX, SLOT> {
        val toNeighbours = neighbours(toVertex)
        val fromNeighbours = neighbours(fromVertex) + (throughSocket to toVertex)
        return SlotDiGraph(adjMap + (fromVertex to fromNeighbours) + (toVertex to toNeighbours))
    }

    override fun targetsOf(vertex: VERTEX) = adjMap.getOrDefault(vertex, mapOf()).map { (s, v) ->
        Target(
            v,
            s
        )
    }

    override fun neighbours(vertex: VERTEX): Map<SLOT, VERTEX> = adjMap.getOrDefault(vertex, mapOf())

    override fun subGraph(roots: List<VERTEX>): SlotDiGraph<VERTEX, SLOT> {
        val verticesSet = vertices.toSet()
        val validRoots = roots.filter { verticesSet.contains(it) }

        /**
         * recursively finds all connections starting at vertices from current list
         * then applies the same scan per target of each edge unless it has been already checked
         * @param r List<V> current root's list
         * @param g SlotDiGraph<V, PAYLOAD> immutable graph used as the accumulator of discovered connections
         * @param checked Set<Connection<V, PAYLOAD>> set of found connections
         * @return SlotDiGraph<V, PAYLOAD>
         */
        tailrec fun extract(
            r: List<VERTEX>,
            g: SlotDiGraph<VERTEX, SLOT> = SlotDiGraph(
                mapOf()
            ),
            checked: Set<Connection<VERTEX, SLOT>> = setOf()
        ): SlotDiGraph<VERTEX, SLOT> {

            val connections =
                // find all connections per each provided root
                r.map { from -> this.neighbours(from).map { (s, to) ->
                    Connection(
                        from,
                        to,
                        s
                    )
                } }
                    // transform nested List of List(s) of Connection(s) into List of Connection(s)
                    .flatten()
                    // removed any edge which has already been checked, this may happen in a cyclic graph
                    .filter { !checked.contains(it) }
            return when {
                // no more connections to scan so return the last known state of the graph
                connections.isEmpty() -> g
                else -> {
                    val (newDiGraph, newChecked) =
                        connections
                            // append all connections to the graph and checked set
                            .fold(g to checked) { acc, edge ->
                                acc.first.addConnection(edge) to (checked + edge)
                            }
                    // get root vertices for the next iteration from the targets of scanned connections
                    val newRoots = connections.map { it.toVertex }.distinct()
                    extract(newRoots, newDiGraph, newChecked)
                }
            }
        }
        return when {
            validRoots.isEmpty() -> SlotDiGraph(mapOf())
            else -> extract(validRoots)
        }
    }
}