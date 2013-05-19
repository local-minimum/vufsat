/**
 * Annotations should be interfaces on extended PrototypeModelObjects!
 *
 */

package model.annotation;

import java.util.*;

import model.modelobject.PrototypeModelObject;
import model.annotation.documentation.AnnotationDocumentation;
import model.topologies.Topology;

/**
 * Annotation defines the interface for all objects annotating sequences or
 * alignments.
 *
 * An alignment instance works as a grouping for unified styling. Thus
 * alignments can be split and unified by moving features between instances.
 */
interface Annotation {

	/**
	 * getAnnotates
	 *
	 * @return
	 * 			HashSet of PrototypeModelObject for which it annotates
	 */
	public HashSet<PrototypeModelObject> getAnnotates();

	/**
	 * isAllowedAnnotation
	 *
	 * Tests if an object can be annotated by the annotation
	 *
	 * @param obj
	 * 			That wants to be annotated
	 * @return
	 * 			boolean (True if allowed)
	 */
	public boolean isAllowedAnnotation(PrototypeModelObject obj);

	/**
	 * getDocumentation
	 * 
	 * Produces reference information about the particular annotation
	 * which also states if user has modified annoation
	 *
	 * @return
	 * 			AnnotationDocumentation
	 */
	public AnnotationDocumentation getDocumentation();

	/**
	 * getFeatures(PrototypeModelObject)
	 *
	 * Produces a list of features that annotates an object
	 * 
	 * @param obj
	 * 			The object annotated
	 * @return
	 * 			The topoligies annotating the object 
	 */
	public ArrayList<Topology> getTopologies(PrototypeModelObject obj);

	/**
	 * getAllFeatures()
	 *
	 * Produces a list of features for all objects annotated
	 *
	 * @return
	 * 			All topologies
	 */
	public ArrayList<Topology> getAllTopologies();

	/**
	 * setAnnotation
	 *
	 * This method should probably be invoked from the constructor
	 *
	 * @param obj
	 * 			The object(s) for the annotation
	 * 			to annotate
	 * @return
	 * 			True if annotation was successful
	 */
	public boolean setAnnotation(PrototypeModelObject... obj);

	/**
	 * setTopology
	 *
	 * In general, for most use cases, the setAnnotation should
	 * invoke this method.
	 *
	 * @param top
	 * 			Topology to be add to the annoation
	 * @return
	 * 			boolean if setting was successful
	 */
	public boolean setTopology(Topology top);
}
