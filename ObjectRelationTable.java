import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class ObjectRelationTable implements Serializable {
	private static final long serialVersionUID = 1L;
	private int numCols;
	private int numRows;/*
						 * The number of rows should not be able to be modified
						 * by a user under any circumstances.
						 */
	public ArrayList<LinkedList<Object>> table;

	public ObjectRelationTable(int numCols) {
		this.numCols = numCols;
		numRows = 1;
		table = new ArrayList<LinkedList<Object>>();
	}

	public void push(LinkedList<Object> update) throws InvalidRelationTableUpdateException {
		if (update.size() != numCols)
			/**
			 * If the size of the new row does not equal the number of columns,
			 * it will not follow the organizational guidelines of the data
			 * table.
			 */
			throw new InvalidRelationTableUpdateException(update.size());
		if (table.size() > 0)
			for (int x = 0; x < numCols; ++x)
				if (!(table.get(0).get(x).getClass() == update.get(x).getClass()))
					/**
					 * If any values in the new row are not of the same type
					 * with the corresponding column in the top row, it won't
					 * follow table organizational guidelines.
					 */
					throw new InvalidRelationTableUpdateException(update.get(x).getClass());
		table.add(update);
		numRows++; // The number of rows increase by one.
	}

	// The following method adds a row to a user specified index.
	public void add(int index, LinkedList<Object> update) throws InvalidRelationTableUpdateException {
		if (update.size() != numCols)
			throw new InvalidRelationTableUpdateException(update.size());
		if (table.size() > 0)
			for (int x = 0; x < numCols; ++x)
				if (!(table.get(0).get(x).getClass() == update.get(x).getClass()))
					throw new InvalidRelationTableUpdateException(update.get(x).getClass());
		table.add(index, update);
		numRows++;
	}

	/*
	 * The following method inserts another table at the bottom of the table
	 * that already exists. The columns should still contain objects of the same
	 * type.
	 */
	public void push(ObjectRelationTable other) throws InvalidRelationTableUpdateException {
		for (LinkedList<Object> list : other.table)
			push(list);
	}

	// The following method adds another table at a specified index.
	public void add(int index, ObjectRelationTable other) throws InvalidRelationTableUpdateException {
		for (LinkedList<Object> list : other.table)/**
													 * The index variable must
													 * contain a postfix
													 * increment operator
													 * because as each row of
													 * the table being added is
													 * inserted, the row at the
													 * current insertion index
													 * is replaced.
													 */
			add(index++, list);
	}

	// The following method returns the bottom row of the table.
	public LinkedList<Object> peek() {
		return table.get(table.size() - 1);
	}

	/*
	 * The following method returns the bottom most row of the table, as well as
	 * removing it from the table.
	 */
	public LinkedList<Object> pop() {
		LinkedList<Object> pop = table.get(table.size() - 1);
		table.remove(table.size() - 1);
		numRows--;
		return pop;
	}

	public int cols() {
		return numCols;
	}

	public int rows() {
		return numRows;
	}
}

class InvalidRelationTableUpdateException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRelationTableUpdateException(int rows) {
		super("InvalidRelationTableUpdateException : " + rows);
	}

	public InvalidRelationTableUpdateException(Object mismatchObject) {
		super("InvalidRelationTableUpdateException : " + mismatchObject.toString());
	}
}
