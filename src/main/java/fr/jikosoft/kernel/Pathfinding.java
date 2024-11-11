package fr.jikosoft.kernel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import fr.jikosoft.objects.Cell;
import fr.jikosoft.objects.Map;

public class Pathfinding {
	private final Map map;

	public Pathfinding(Map map) {
		this.map = map;
	}

	public List<Cell> findPath(Cell start, Cell goal) {
		PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
		java.util.Map<Cell, Node> allNodes = new HashMap<>();

		Node startNode = new Node(start, null, 0, heuristic(start, goal));
		openSet.add(startNode);
		allNodes.put(start, startNode);

		while (!openSet.isEmpty()) {
			Node current = openSet.poll();

			if (current.cell == goal) {
				System.out.println("Goal reached. Reconstructing path...");
				return reconstructPath(current);
			}

			for (Cell neighbor : getNeighbors(current.cell)) {
				if (!neighbor.isClickable()) continue;

				double tentativeG = current.g + distance(current.cell, neighbor);
				System.out.println("CellID : " + neighbor.getCellID() + " | tentativeG : " + tentativeG);

				Node neighborNode = allNodes.get(neighbor);
				if (neighborNode == null) {
					neighborNode = new Node(neighbor);
					allNodes.put(neighbor, neighborNode);
				}

				System.out.println("Checking neighbor: " + neighbor.getCellID() + 
                   ", current.g: " + current.g + 
                   ", tentativeG: " + tentativeG + 
                   ", neighborNode.g: " + neighborNode.g);

				if (tentativeG < neighborNode.g) {
					neighborNode.g = tentativeG;
					neighborNode.h = heuristic(neighbor, goal);
					neighborNode.parent = current;

					if (!openSet.contains(neighborNode)) openSet.add(neighborNode);
				}
			}
		}

		System.out.println("No path found.");
		return Collections.emptyList(); // Return an empty path if no path is found
	}

	private List<Cell> reconstructPath(Node node) {
		List<Cell> path = new ArrayList<>();
		while (node != null) {
			System.out.println(node.cell.getCellID());
			path.add(0, node.cell);
			node = node.parent;
		}
		return path;
	}

	private List<Cell> getNeighbors(Cell cell) {
		List<Cell> neighbors = new ArrayList<>();
		int row = cell.getGridY();
		int col = cell.getGridX();

		int[][] directions = {
			{1, 1},   // down-right
			{1, -1},  // up-right
			{0, 1},  // down-left
			{0, -1}, // up-left
			{0, 2},   // vertical up
			{0, -2},  // vertical down
			{1, 0},   // horizontal right
			{-1, 0}   // horizontal left
		};

		for (int[] direction : directions) {
			int newRow = row + direction[1];
			int newCol = col + direction[0];

			if (newRow >= 0 && newRow < map.getHeight() * 2 - 1 && newCol >= 0 && newCol < (newRow % 2 == 0 ? map.getWidth() : map.getWidth() - 1)) {
				Cell neighbor = map.getCellAt(newRow, newCol);
				if (neighbor != null) neighbors.add(neighbor);
			}
		}
		return neighbors;
	}

	private double heuristic(Cell a, Cell b) {
		int dx = Math.abs(a.getGridX() - b.getGridX());
		int dy = Math.abs(a.getGridY() - b.getGridY());

		return dx + dy;
	}
	

	private double distance(Cell a, Cell b) {
		int dx = Math.abs(a.getGridX() - b.getGridX());
		int dy = Math.abs(a.getGridY() - b.getGridY());
		return (dx + dy) == 2 ? 1.414 : 1;
	}

	private static class Node {
		Cell cell;
		Node parent;
		double g;
		double h;

		Node(Cell cell) {
			this.cell = cell;
			this.g = Double.MAX_VALUE;
			this.h = 0;
			this.parent = null;
		}

		Node(Cell cell, Node parent, double g, double h) {
			this.cell = cell;
			this.parent = parent;
			this.g = g;
			this.h = h;
		}

		double getF() {
			return g + h;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Node other = (Node) obj;
			return cell.equals(other.cell);
		}
	
		@Override
		public int hashCode() {return cell.hashCode();}
	}
}
