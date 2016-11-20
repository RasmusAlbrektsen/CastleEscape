/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

/**
 * Interface describing a builder object. A builder object is responsible for
 * converting raw data, read from some data source, into fully functional models
 * to be used by the business layer. Builders will not perform any validation of
 * the structure of the data source, and thus they assume that all data is read
 * in the correct order.
 *
 * @author Alex, Kasper
 */
public interface IBuilder {
	
	/**
	 * The name of a data element accepted by this builder.
	 */
	public static final String NAME = "name",
			DESCRIPTION = "description";

	/**
	 * Notify this builder that an element is about to be read.
	 *
	 * @param element the name of the element that has been read
	 */
	public void notifyOfElement(String element);

	/**
	 * Notify this builder that an element is done being read from some data
	 * source. The builder implementation is free to define what processing
	 * should take place, and whether the content parameter is ignored.
	 *
	 * @param element the name of the element that has been read
	 * @param content the content of the element
	 */
	public void processElement(String element, String content);

	/**
	 * Finish processing the raw data in this builder and construct a fully
	 * functional model of the type it is associated with. This method requires
	 * a reference to the level data storage as some objects require references
	 * to other objects to be able to build.
	 * 
	 * @param dataStorage the level data that has been read
	 */
	public void build(LevelDataStorage dataStorage);

	/**
	 * Get the result from this builder. This method should only ever be called
	 * after a call to {@link #build()}, as it will otherwise return null.
	 * Sadly, there is no common type for Items, InspectableObjects and Rooms
	 * other than Object, so the caller of this method must perform typecasting.
	 *
	 * @return the result of this builder
	 */
	public Object getResult();
}
