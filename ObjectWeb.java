import java.util.ArrayList;

public class ObjectWeb {
	private ArrayList<Object> allObjects;
	private boolean[][] connectsTo;

	public ObjectWeb(ArrayList<Object> allObjects, boolean[][] connectsTo) throws InvalidObjectWebSizeException {
		if (connectsTo.length != allObjects.size() && connectsTo.length > 0)
			/**
			 * The first dimension of the connectsTo boolean array must be of
			 * the same size as the allObjects ArrayList. This is because each
			 * object in the all objects data structure must have a
			 * corresponding array of booleans which match up which other
			 * objects they are connected to.
			 */
			throw new InvalidObjectWebSizeException(
					"The number of objects is not compatible with the number of connections.");
		setAllObjects(allObjects);
		setConnections(connectsTo);
	}

	public void connect(int indexOfConnection, Object joiningObject) {
		allObjects.add(joiningObject);
		int newConnectionBooleanArrayDim1 = connectsTo.length + 1;
		int newConnectionBooleanArrayDim2 = connectsTo[0].length + 1;
		boolean[][] newConnectionBooleanArray = new boolean[newConnectionBooleanArrayDim1][newConnectionBooleanArrayDim2];
		int i = indexOfConnection;
		int j = connectsTo[0].length + 1;
		/**
		 * The variables x and y exist because they represent the corresponding
		 * boolean array coordinate for which the truth values are logically
		 * equivalent.
		 */
		int x = j;
		if (x >= i)
			x++;
		int index = 0;
		int y = 0;
		while (index < i) {
			if (index++ == x)
				continue;
			y++;
		}
		for (int a = 0; a < newConnectionBooleanArray.length; ++a)
			for (int b = 0; b < newConnectionBooleanArray[a].length; ++b) {
				if (a < newConnectionBooleanArray.length - 1 && b < newConnectionBooleanArray.length - 1)
					newConnectionBooleanArray[a][b] = connectsTo[a][b];
				else {
					if ((a == i && b == j) || (a == x && b == y))
						newConnectionBooleanArray[a][b] = true;
					else
						newConnectionBooleanArray[a][b] = false;
				}
			}
		// The private setConnections method should be invoked.
		setConnections(newConnectionBooleanArray, 0);
	}

	public ArrayList<Object> getAllObjects() {
		return allObjects;
	}

	public boolean[][] isConnectedTo() {
		return connectsTo;
	}

	public void setAllObjects(ArrayList<Object> allObjects) {
		this.allObjects = allObjects;
	}

	/**
	 * The following method exists since if the connect method is invoked, there
	 * is no possibility that the new boolean array size will be invalid. This
	 * method does not throw any exceptions and must be declared as private.
	 */
	private void setConnections(boolean[][] connectsTo, int dummy) {
		this.connectsTo = new boolean[connectsTo.length][connectsTo[0].length];
		this.connectsTo = connectsTo.clone();
	}

	public void setConnections(boolean[][] connectsTo) throws InvalidObjectWebSizeException {
		if (connectsTo.length == 0)
			return; /*
					 * An instance of ObjectWeb with the size of zero would
					 * cause the following line to create a negative array
					 * dimension.
					 */
		if (connectsTo.length - connectsTo[0].length != 1)
			/**
			 * Each object must have a corresponding boolean array that matches
			 * up what that particular object connects to. This is why the
			 * second dimension of connectsTo must be a size one fewer than that
			 * of the first dimension, since an object cannot have a connection
			 * with itself.
			 */
			throw new InvalidObjectWebSizeException(
					"The second dimension of the connections boolean array must be a size one less than that of the first dimension.");
		this.connectsTo = new boolean[connectsTo.length][connectsTo[0].length];
		this.connectsTo = connectsTo.clone();
	}
}

class InvalidObjectWebSizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidObjectWebSizeException(String reason) {
		super(reason);
	}
}
