/**
 * 
 */
package com.hertz.graph;

import java.util.ArrayList;
import java.util.List;

import com.hertz.entity.CatCatRel;
import com.hertz.graph.CategoryGraph.CategoryGraphManager;
import com.hertz.graph.CategoryGraph.Parent;
import com.hertz.graph.CategoryGraph.SuperviseBy;
import com.hertz.graph.core.BaseRelationship;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.frames.FramedGraph;

/**
 * @author Subba
 *
 */
public class CategoryGraphRelationship extends BaseRelationship {

	public CategoryGraphRelationship(FramedGraph<Graph> framedGraph) {
		super(framedGraph);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Maximum number of recursions allowed while traversing the graph.
	 * Assumption is that there will not be more than 100 supervisor levels in an organization.
	 */
	private static final int MAX_DEPTH_RECURSION = 100;
	
	
	/**
	 * Adds a supervisor relationship between the given categories.
	 * 
	 * @param relData Employee supervisor relationship data
	 */
	public void addParent(CatCatRel relData) {
		if (relData.getCat().getId().intValue() == relData.getCatParent().getId().intValue()) {
			throw new IllegalArgumentException("Child and Parent can't be same");
		}

		if (isAvailable(relData.getCat().getId()) && isAvailable(relData.getCatParent().getId())) {
			CategoryGraphManager sub = get(relData.getCat().getId(), CategoryGraphManager.class);
			CategoryGraphManager sup = get(relData.getCatParent().getId(), CategoryGraphManager.class);
			sub.addParent(sup);
		}
	}
	
	
	/**
	 * Removes a supervisor relationship between the given categories. Throws
	 * illegal argument exception if the supervisor is primary. Must change
	 * primary supervisor before removing the relationship.
	 * 
	 * @param subID Subordinate ID
	 * @param supID Supervisor ID
	 */
	public void removeParent(Integer parID, Integer childID) {

		if (childID == parID) throw new IllegalArgumentException("Subordinate and supervisor can't be same");

		if (isAvailable(childID) && isAvailable(parID)) {
			CategoryGraphManager sub = get(childID, CategoryGraphManager.class);
			CategoryGraphManager sup = get(parID, CategoryGraphManager.class);

			for (Parent suprel : sub.getParents()) {
				if (suprel.getParent().getCategoryId() == sup.getCategoryId()) {
					sub.removeParent(suprel);
					return;
				}
			}
		}
	}
	
	
	/**
	 * Returns a list of supervisors for the given employee. Empty list if none.
	 * 
	 * @param id Employee ID
	 * @return List of supervisors of the employee
	 */
	public List<CategoryGraphManager> getSupervisors(Integer id) {
		List<CategoryGraphManager> result = new ArrayList<CategoryGraphManager>();

		if (isAvailable(id)) {
			CategoryGraphManager node = get(id, CategoryGraphManager.class);

			for (Parent sup : node.getParents()) {
				result.add(sup.getParent());
			}
		}
		return result;
	}
	
	
	/**
	 * Returns a list of subordinates for the given employee. Empty list if none.
	 * 
	 * @param id Employee ID
	 * @return List of subordinates of the employee
	 */
	public List<CategoryGraphManager> getSubordinates(Integer id) {
		List<CategoryGraphManager> result = new ArrayList<CategoryGraphManager>();

		if (isAvailable(id)) {
			CategoryGraphManager node = get(id, CategoryGraphManager.class);

			for (SuperviseBy sup : node.getChilds()) {
				result.add(sup.getChild());
			}
		}

		return result;
	}
	
	
	/**
	 * Returns a flat list of all children and children's children for this employee
	 * 
	 * @param id Employee ID
	 * @return List of descendants of the employee
	 */
	public List<CategoryGraphManager> getDescendants(Integer id, int dist) {
		if (dist == 0) {
			dist = MAX_DEPTH_RECURSION;
		}

		List<CategoryGraphManager> res = new ArrayList<CategoryGraphManager>();

		if (isAvailable(id)) {
			CategoryGraphManager node = get(id, CategoryGraphManager.class);
			getDescendants(node, res, dist);
		}

		return res;
	}

	/**
	 * Internal recursive traversal method. Gets the descendants by traversing
	 * the supervised-by relationship. Optionally filters the returned data for
	 * primary relationships. Traversal is a BFS traversal so that the peers at
	 * the same level are returned together.
	 * 
	 * @param node Current node to examine
	 * @param result Result in which nodes are gathered
	 * @param depth Depth to which the recursion should go
	 */
	private void getDescendants(CategoryGraphManager node, List<CategoryGraphManager> result, int depth) {
		List<CategoryGraphManager> recurse = new ArrayList<CategoryGraphManager>();

		for (SuperviseBy sup : node.getChilds()) {

				if (!result.contains(sup.getChild())) { // Haven't already visited the node
					result.add(sup.getChild());
					recurse.add(sup.getChild());
				}
		}

		if (depth > 1) {
			depth--;
			for (CategoryGraphManager emp : recurse) {
				getDescendants(emp, result, depth);
			}
		}
	}
	
	
	/**
	 * Returns a list of supervisors and supervisor's supervisor for the given employee.
	 * 
	 * @param id Employee ID
	 * @return List of supervisors of the employee till root
	 */
	public List<CategoryGraphManager> getAscendants(Integer id, int dist) {
		if (dist == 0) {
			dist = MAX_DEPTH_RECURSION;
		}

		List<CategoryGraphManager> result = new ArrayList<CategoryGraphManager>();

		if (isAvailable(id)) {
			CategoryGraphManager node = get(id, CategoryGraphManager.class);
			getAscendants(node, result, dist);
		}

		return result;
	}
	
	/**
	 * Internal recursive traversal method. Gets the ascendants by traversing the
	 * supervisor relationship. Optionally filters the returned data for primary
	 * relationships. Traversal is a BFS traversal so that the peers at the same
	 * level are returned together.
	 * 
	 * @param node Current node to examine
	 * @param result Result in which nodes are gathered
	 * @param depth Depth to which the recursion should go
	 * @param primOnly Optional - only primary supervisors
	 */
	private void getAscendants(CategoryGraphManager node, List<CategoryGraphManager> result, int depth) {
		List<CategoryGraphManager> recurse = new ArrayList<CategoryGraphManager>();

		for (Parent sup : node.getParents()) {

				// Haven't already visited the node (e.g. when secondary supervisors share parent)
				if (!result.contains(sup.getParent())) {
					result.add(sup.getParent());
					recurse.add(sup.getParent());
				}
			}

		if (depth > 1) {
			depth--;
			for (CategoryGraphManager emp : recurse) {
				getAscendants(emp, result, depth);
			}
		}
	}

	
	public boolean isAvailable(Integer id) {
		try {
			CategoryGraphManager cat = get(id, CategoryGraphManager.class);

			if (id.equals(cat.getCategoryId())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
