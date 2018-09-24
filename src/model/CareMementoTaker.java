package model;

/**
 * Singleton that manages the actual saved ModelMemento.
 *
 * @author Rossolini Andrea
 */
public class CareMementoTaker {

	private static final CareMementoTaker SINGLETON = new CareMementoTaker();
	private ModelMemento memento;

	private CareMementoTaker() {
	}

	/**
	 * @return The instance of CareMementoTaker.
	 */
	public static CareMementoTaker getMementoInstance() {
		return SINGLETON;
	}

	/**
	 * this method sets the ModelMemento to save.
	 * 
	 * @param memento
	 *            ModelMemento to save.
	 */
	public void setMemento(final ModelMemento memento) {
		this.memento = memento;
	}

	/**
	 * @return the ModelMemnto stored.
	 */
	public ModelMemento getMemento() {
		return this.memento;
	}
}
