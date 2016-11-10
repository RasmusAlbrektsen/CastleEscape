package util;

/**
 * Created by Alex on 27/10/2016.
 * Interface for all builder classes used by {@link XMLContentBuilder}
 */
interface IBuilder {
	void setDescription(String description);

	void build();
}
